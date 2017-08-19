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
		
		//����ÿ�����ݵ�ŷ�Ͼ����KNN
//		KNN knn =new KNN();
//		int dataknn[][]=new int[Constants.datasize][Constants.K];
//		for(int i=0;i<Constants.datasize;i++)
//		{
//			System.out.println("���� the knn of"+i);
//			knn.linearScan(data, data[i]);
//			for(int j=0;j<Constants.K;j++)
//			{
//				dataknn[i][j]=knn.knnlist[j];
//			}
//		}
//		System.out.println("��ʼдknn���ļ�");
//		io.diskwriteknn_int("dataknn.txt", dataknn);
		
		//Ϊÿ��dataѡ����ʵİ뾶
		SHGeneral shg=new SHGeneral();
		shg.init(data);
		SHSelection shs=new SHSelection();
		shs.radius_selection("decision.dat",shg);
		
		//��������(����(�û�+�ԳƼ���)��д���ļ�)
		SHindex shi=new SHindex();
		shi.index_construct("decision.dat", shg);
		
		//ִ�в�ѯ
		shi.query_execute(Constants.L,data[Constants.datasize-1], shg);//data[99000]��Ϊ��ѯ��
		
		System.out.println("programme finished");
	}
}
