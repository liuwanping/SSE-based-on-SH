import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;



public class SHindex 
{
	//索引
	private TrapdoorAndDataIDs[][][] index=new TrapdoorAndDataIDs[Constants.L][Constants.Alter][Constants.bucketnum];
	
	//陷门
	public int querytableresult[][]=new int[Constants.Alter][Constants.L];
	
	//查询结果
	public ArrayList<Integer> query_result=new ArrayList<Integer>();
	
	//写入文件用
	IO io=new IO();
	

	
	private void init_index()
	{
		for(int l=0;l<Constants.L;l++)
		{
			for(int a=0;a<Constants.Alter;a++)
			{
				for(int b=0;b<Constants.bucketnum;b++)
				{
					index[l][a][b]=new TrapdoorAndDataIDs();				
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
	    
	    //置换索引的bucket
	    permutation_index();
	    
	    //加密索引的trapdoor
	    AES.encrypt_index(index);
	    
	    //把索引写到文件里
	    io.diskwriteindex_int(index);
	    System.out.println("SHindex->index_construct--END");
	}

	//随机置换index的bucket
	public void permutation_index()
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
	
	
	public void query_execute(int Lused, float[] query,SHGeneral shg) throws IOException 
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
				//遍历匹配trapdoor
				for(int b=0;b<index[l][a].length;b++)
				{
					if(AES.encrypt_trapdoor(hashkey).equals(index[l][a][b].trapdoor))
					{
						for(int dataid:index[l][a][b].dataids)
						{
							if(!query_result.contains(dataid))//如果这个dataid还没加入到结果list中加进去
							{
								query_result.add(dataid);
							}						
						}
					}
				}
				
			}
		}		
		//把查询结果写到文件里
		queryresultwrite();
		System.out.println("SHindex->query_execute--END");
	}
	
	
	
	public void queryresultwrite() throws IOException
	{
		int result[]=new int[query_result.size()];
		System.out.println("查询结果个数为："+query_result.size());
		
		int i=0;
		for(int dataid:query_result)
		{
			result[i]=dataid;
			i++;
		}
			
		io.diskwrite_int("query_result.txt", result);
		
	}
}
