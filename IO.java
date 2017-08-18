import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class IO 
{
	//从文件里读取data
	public void diskReadFloat(String data_file, float data[][]) throws IOException
	{
		File file = new File(data_file); 
		BufferedReader in = new BufferedReader(new FileReader(file));
		String line;  //一行数据
		int row=0;	
		while((line = in.readLine()) != null)//逐行读取，并将每个数组放入到数组中
		{
//		   System.out.println("data"+row+":");
		   String[] temp = line.split("\\ "); 
		   for(int j=0;j<temp.length;j++)
		   {
			   data[row][j] = Float.parseFloat(temp[j]);
//			   System.out.print(data[row][j]+"||");
		   }
//		   System.out.println("------");
		   row++;
		 }
		  in.close();
		
		
	}

	//把一维数组写到文件（把每个data的半径写到文件里、把查询结果写到文件里）
	public void diskwrite_int(String decision_file, int[] decision) throws IOException 
	{
		System.out.println("write R to file");
		File file = new File(decision_file);
		FileWriter out = new FileWriter(file);
		for(int i=0;i<decision.length;i++)
		{
			out.write(decision[i]+"\r\n");
			
		}
		out.close();
		System.out.println("write R to file--END");
	}
	
	//把二维数组写到文件（把每个data的KNN(k=20)写到文件里）
	public void diskwriteknn_int(String knn_file, int knn[][]) throws IOException 
	{
		System.out.println("write data's knn to file");
		File file = new File(knn_file);
		FileWriter out = new FileWriter(file);
		for(int i=0;i<knn.length;i++)
		{
			for(int j=0;j<knn[i].length;j++)
			{
				out.write(knn[i][j]+"\t");
			}
			out.write("\r\n");
		}
		out.close();
		System.out.println("write data's knn to file--END");
	}
	
	//把三维数组写到文件（把索引index写到文件）
	public void diskwriteindex_int(TrapdoorAndDataIDs index[][][]) throws IOException 
	{
		System.out.println("write index to file");
		//File file = new File(index_file);
		//FileWriter out = new FileWriter(file);
		for(int i=0;i<index.length;i++)
		{
			for(int j=0;j<index[i].length;j++)
			{
				String f3=i+"_"+j;
				File file = new File(f3);
				FileWriter out = new FileWriter(file);
				for(int z=0;z<index[i][j].length;z++)
				{
					for(int dataid:index[i][j][z].dataids)
					{
						out.write(dataid+"\t");
					}
					out.write("\r\n");
				}
				out.close();
			}
		}
		
		System.out.println("write index to file--END");
	}
}
