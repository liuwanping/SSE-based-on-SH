import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


public class LSHindex 
{
	//����
	private TrapdoorAndDataIDs[][] lsh_index=new TrapdoorAndDataIDs[Constants.L][Constants.bucketnum];
	
	//����
	public int querytableresult[]=new int[Constants.L];
		
	//��ѯ���
	public ArrayList<Integer> query_result=new ArrayList<Integer>();
	
	//д���ļ���
	IO io=new IO();
	
	private void init_lsh_index()
	{
		for(int l=0;l<Constants.L;l++)
		{		
				for(int b=0;b<Constants.bucketnum;b++)
				{
					lsh_index[l][b]=new TrapdoorAndDataIDs();	
				}		
		}
	}
	
	//��lsh����
	public void lsh_index_construct(String decision_file,SHGeneral shg) throws IOException
	{
		System.out.println("LSHindex->lsh_index_construct");
		
//		for(int i=0;i<shg.decision.length;i++)
//		{
//			shg.decision[i]=Constants.lsh_R;
//		}
		
		init_lsh_index();
		
		// Compute an initial tableindex for all the datapoints
	    for(int k = 0; k < Constants.datasize; k++)
	    {
	        if(k % 20000 == 0)
	        	System.out.println("current hashing data :"+k);
	        shg.lsh_tableindex(shg.dataproduct[k], Constants.lsh_R, shg.datahashresult[k]);
	    }
	    
	    for(int j = 0; j < Constants.L; j++)
	    {
	        int hashkey;

	        /* We compute the hashvalues of all datapoints for this hashtable and sort
	           it, with points at a higher layer having a larger hashkey */
	        for(int i = 0; i < Constants.datasize; i++)
	        {
	        	long z=0;
	        	//int trapdoor = 0;
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
	            //trapdoor = shg.decision[i] * Constants.bucketnum + hashkey;
	            
	            lsh_index[j][hashkey].trapdoor=Integer.toString(hashkey);
	            lsh_index[j][hashkey].dataids.add(i);
	            
	        }
	         
	    }
	        
	    //�û�������bucket
	    permutation_lsh_index();
	    
	    //����������trapdoor
	    AES.encrypt_lsh_index(lsh_index);
	    
	    //������д���ļ���
	    io.diskwritelsh_index("lsh",lsh_index);
	    System.out.println("LSHindex->index_construct--END");
	}
	//����û�lsh_index��bucket
	private void permutation_lsh_index()
	{
		System.out.println("permutation_lsh_index");
		for(int l=0;l<lsh_index.length;l++)
		{
			
				for(int b=0;b<lsh_index[l].length;b++)
				{
					int random=new Random().nextInt(lsh_index[l].length-1);
					TrapdoorAndDataIDs temp=lsh_index[l][b];
					lsh_index[l][b]=lsh_index[l][random];
					lsh_index[l][random]=temp;
				}
			
		}
		System.out.println("permutation_lsh_index--END");
	}
	
	public void query_execute(int Lused, float[] query,SHGeneral shg,String query_result_file) throws IOException 
	{
		System.out.println("LSHindex->query_execute");
		
		float queryproduct[]=new float[Constants.familysize];
		
		for(int i=0;i<Constants.familysize;i++)
		{
			queryproduct[i]=MyVector.dotproduct(Constants.D, query, shg.familyvector[i]);
		}
		
		shg.lsh_tableindex(queryproduct, Constants.lsh_R, querytableresult);
		
		
		long startTime=System.currentTimeMillis();//��ѯ��ʼʱ��
		
		for(int l=0;l<Lused;l++)
		{
			long z=0;
			int hashkey=0;
			if(querytableresult[l]<0)
			{
				z=querytableresult[l]&0xFFFFFFFFL;
				hashkey=(int) (z%Constants.bucketnum);
			}
			else
			{
				hashkey=querytableresult[l]%Constants.bucketnum;
			}
			
			
			//����ƥ��trapdoor
			for(int b=0;b<lsh_index[l].length;b++)
			{
				if(AES.encrypt_trapdoor(hashkey).equals(lsh_index[l][b].trapdoor))
				{
					for(int dataid:lsh_index[l][b].dataids)
					{
						if(!query_result.contains(dataid))//������dataid��û���뵽���list�мӽ�ȥ
						{
							query_result.add(dataid);
						}						
					}
				}
			}
			
		}
		
		long endTime=System.currentTimeMillis(); //��ѯ����ʱ��
		//�Ѳ�ѯ���д���ļ���
		queryresultwrite(query_result_file);
		System.out.println("��������ʱ�䣺 "+(endTime-startTime)+"ms");
		System.out.println("LSHindex->query_execute--END");
	}
	
	
	
	public void queryresultwrite(String query_result_file) throws IOException
	{
		int result[]=new int[query_result.size()];
		System.out.println("LSH��ѯ�������Ϊ��"+query_result.size());
		
		int i=0;
		for(int dataid:query_result)
		{
			result[i]=dataid;
			i++;
		}
			
		io.diskwrite_int(query_result_file, result);
		
	}
}
