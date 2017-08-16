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

	//把每个data的半径写到文件里
	public void diskwrite_int(String decision_file, int[] decision, int datasize) throws IOException 
	{
		System.out.println("write R to file");
		File file = new File(decision_file);
		FileWriter out = new FileWriter(file);
		for(int i=0;i<decision.length;i++)
		{
			out.write(decision[i]+" ");
		}
		out.close();
		System.out.println("write R to file--END");
	}
}
