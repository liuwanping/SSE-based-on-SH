import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class IO 
{
	//���ļ����ȡdata
	public void diskReadFloat(String data_file, float data[][]) throws IOException
	{
		File file = new File(data_file); 
		BufferedReader in = new BufferedReader(new FileReader(file));
		String line;  //һ������
		int row=0;	
		while((line = in.readLine()) != null)//���ж�ȡ������ÿ��������뵽������
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

	//��һά����д���ļ�����ÿ��data�İ뾶д���ļ���Ѳ�ѯ���д���ļ��
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
	
	//�Ѷ�ά����д���ļ�����ÿ��data��KNN(k=20)д���ļ��
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
	
	//����ά����д���ļ���������indexд���ļ���
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
