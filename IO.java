import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class IO 
{
	//���ļ����ȡdata
	public void diskReadFloat(String fileName, float data[][]) throws IOException
	{
		File file = new File(fileName); 
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
}
