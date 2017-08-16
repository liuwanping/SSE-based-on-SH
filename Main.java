import java.io.IOException;


public class Main 
{
	public static void main(String[] args) throws IOException 
	{
		
		//数据集
		float data[][] = new float[Constants.datasize][Constants.D+1];
		
		//从文件里读数据集
		IO io = new IO();
		String fileName="E:/3-Tech/leetcode/SSE-based-on-SH/src/dataset/dataset.data";
		io.diskReadFloat(fileName,data);
		System.out.println("data read from disk");
		
		//建索引，加密索引，把索引存到文件（传给服务器）
		SHGeneral shg=new SHGeneral();
		shg.init(data);
		SHSelection shs=new SHSelection();
		shs.radius_selection("decision.dat",shg);
		
	}
}
