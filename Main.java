import java.io.IOException;


public class Main 
{
	public static void main(String[] args) throws IOException 
	{
		
		//���ݼ�
		float data[][] = new float[Constants.dataSize][Constants.D+1];
		
		//���ļ�������ݼ�
		IO io = new IO();
		String fileName="E:/3-Tech/leetcode/SSE-based-on-SH/src/dataset/input.data";
		io.diskReadFloat(fileName,data);
		System.out.println("data read from disk");
		
		//�������������������������浽�ļ���������������
		SHGeneral shg=new SHGeneral();
		shg.init();
		
	}
}
