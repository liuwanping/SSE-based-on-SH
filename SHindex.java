import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;



public class SHindex 
{
	//����
	private TrapdoorAndDataIDs[][][] index=new TrapdoorAndDataIDs[Constants.L][Constants.Alter][Constants.bucketnum];
	
	//����
	public int querytableresult[][]=new int[Constants.Alter][Constants.L];
	
	//��ѯ���
	public ArrayList<Integer> query_result=new ArrayList<Integer>();
	
	//д���ļ���
	IO io=new IO();
	
	//�����list
	public TrapdoorAndRandomlList unique_trapdoor[][][]=new TrapdoorAndRandomlList[Constants.L][Constants.Alter][Constants.bucketnum];
	
	

	//�����������
	Random random=new Random();
	
	private void init_index()
	{
		for(int l=0;l<Constants.L;l++)
		{
			for(int a=0;a<Constants.Alter;a++)
			{
				for(int b=0;b<Constants.bucketnum;b++)
				{
					index[l][a][b]=new TrapdoorAndDataIDs();	
					unique_trapdoor[l][a][b]=new TrapdoorAndRandomlList();
				}
			}
		}
	}
	
	public void index_construct(String decision_file,SHGeneral shg) throws IOException
	{
		System.out.println("SHindex->index_construct");
		
		init_index();
		
		// Compute an initial tableindex for all the datapoints
	    for(int k = 0; k < Constants.datasize; k++)
	    {
	        if(k % 20000 == 0)
	        	System.out.println("current hashing data :"+k);
	        shg.tableindex(shg.dataproduct[k], shg.decision[k], shg.datahashresult[k]);
	    }
	    for(int j = 0; j < Constants.L; j++)
	    {
	        int hashkey;

	        /* We compute the hashvalues of all datapoints for this hashtable and sort
	           it, with points at a higher layer having a larger hashkey */
	        for(int i = 0; i < Constants.datasize; i++)
	        {
	        	long z=0;
	        	int trapdoor = 0;
	        	if(shg.datahashresult[i][j]<0)
	        	{
	        		z=shg.datahashresult[i][j]&0xFFFFFFFFL;
	        		// the (hashresult % bucketnum) gives us the bucket index of a point for a given hashtable
		            hashkey = (int) (z % Constants.bucketnum);
	        		
	        	}
	        	else
	        	{
	        		// the (hashresult % bucketnum) gives us the bucket index of a point for a given hashtable
		            hashkey = shg.datahashresult[i][j] % Constants.bucketnum;
	        	}
	       
	            // The multiplicative factor ensures that we get the proper layer to which this point belongs to
	            trapdoor = shg.decision[i] * Constants.bucketnum + hashkey;
	            
	            index[j][trapdoor/Constants.bucketnum][hashkey].trapdoor=Integer.toString(hashkey);
	            index[j][trapdoor/Constants.bucketnum][hashkey].dataids.add(i);
	            
	        }
	         
	    }
	    
	    //��ʼ��unique_trapdoor
	    init_unique_trapdoor();
	    
	    //�û�������bucket
	    permutation_index();
	    
	    //����������trapdoor
	    AES.encrypt_index(index);
	    
	    //������д���ļ���
	    io.diskwriteindex_int(index);
	    System.out.println("SHindex->index_construct--END");
	}

	private void init_unique_trapdoor()
	{
		for (int l = 0; l < unique_trapdoor.length; l++) 
		{
			for (int a = 0; a < unique_trapdoor[l].length; a++) 
			{
				for (int b = 0; b < unique_trapdoor[l][a].length; b++) 
				{
					unique_trapdoor[l][a][b].trapdoor=index[l][a][b].trapdoor;
				}
			}
		}
	}
	//����û�index��bucket
	private void permutation_index()
	{
		System.out.println("permutation_index");
		for(int l=0;l<index.length;l++)
		{
			for(int a=0;a<index[l].length;a++)
			{
				for(int b=0;b<index[l][a].length;b++)
				{
					int random=new Random().nextInt(index[l][a].length-1);
					TrapdoorAndDataIDs temp=index[l][a][b];
					index[l][a][b]=index[l][a][random];
					index[l][a][random]=temp;
				}
			}
		}
		System.out.println("permutation_index--END");
	}
	
	
	public void query_execute(int Lused, float[] query,SHGeneral shg,String query_result_file) throws IOException 
	{
		System.out.println("SHindex->query_execute");
		float queryproduct[]=new float[Constants.familysize];
		for(int i=0;i<Constants.familysize;i++)
		{
			queryproduct[i]=MyVector.dotproduct(Constants.D, query, shg.familyvector[i]);
		}
		for(int i=0;i<Constants.Alter;i++)
		{
			shg.tableindex(queryproduct, i, querytableresult[i]);
		}
		for(int a=0;a<Constants.Alter;a++)
		{
			for(int l=0;l<Lused;l++)
			{
				long z=0;
				int hashkey=0;
				if(querytableresult[a][l]<0)
				{
					z=querytableresult[a][l]&0xFFFFFFFFL;
					hashkey=(int) (z%Constants.bucketnum);
				}
				else
				{
					hashkey=querytableresult[a][l]%Constants.bucketnum;
				}
				int randomSum=0;
				for(int r:unique_trapdoor[l][a][hashkey].randomList)
				{
					randomSum=randomSum+r;
				}
				
				//����ƥ��trapdoor
				for(int b=0;b<index[l][a].length;b++)
				{
					if(AES.encrypt_trapdoor(hashkey+randomSum).equals(index[l][a][b].trapdoor))
					{
						for(int dataid:index[l][a][b].dataids)
						{
							if(!query_result.contains(dataid))//������dataid��û���뵽���list�мӽ�ȥ
							{
								query_result.add(dataid);
							}						
						}
						int random_num=random.nextInt();
						unique_trapdoor[l][a][hashkey].randomList.add(random_num);//��server2��unique_trapdoor��list�����һ�������
						index[l][a][b].trapdoor=AES.encrypt_trapdoor(hashkey+randomSum+random_num);//����server1��������trapdoor
					}
				}
				
			}
		}		
		//�Ѳ�ѯ���д���ļ���
		queryresultwrite(query_result_file );
		System.out.println("SHindex->query_execute--END");
	}
	
	
	
	public void queryresultwrite(String query_result_file) throws IOException
	{
		int result[]=new int[query_result.size()];
		System.out.println("��ѯ�������Ϊ��"+query_result.size());
		
		int i=0;
		for(int dataid:query_result)
		{
			result[i]=dataid;
			i++;
		}
			
		io.diskwrite_int(query_result_file, result);
		
	}
}
