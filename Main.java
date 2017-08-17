import java.io.IOException;


public class Main 
{
	public static void main(String[] args) throws IOException 
	{
		
		//���ݼ�
		float data[][] = new float[Constants.datasize][Constants.D+1];
		
		//���ļ�������ݼ�
		IO io = new IO();
		String fileName="E:/3-Tech/leetcode/SSE-based-on-SH/src/dataset/dataset.data";
		io.diskReadFloat(fileName,data);
		System.out.println("data read from disk");
		
		//Ϊÿ��dataѡ����ʵİ뾶
		SHGeneral shg=new SHGeneral();
		shg.init(data);
		SHSelection shs=new SHSelection();
		shs.radius_selection("decision.dat",shg);
		
		//��������
		SHindex shi=new SHindex();
		shi.index_construct("decision.dat", shg);
		
		//test KNN
//		KNN knn =new KNN();
//		for(int i=0;i<Constants.datasize;i++)
//		{
//			knn.linearScan(data, data[i]);
//			System.out.println(i+"'s knn:");
//			for(int j=0;j<Constants.K;j++)
//			{
//				System.out.print("{"+knn.knnlist[j]+","+knn.distlist[j]+"}");
//			}
//			System.out.println();
//		}
		
		//��������
		
		//������д���ļ�
		
		//ִ�в�ѯ
		shi.query_execute(Constants.L,data[99000], shg);//data[99000]��Ϊ��ѯ��
	}
}
