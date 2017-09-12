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
		
		//��������(����(�û�+�ԳƼ���)��д���ļ�)����ʼ��unique_trapdoor
		SHindex shi=new SHindex();
		shi.index_construct("decision.dat", shg);
		
		
		//ִ�в�ѯ
//		shi.query_execute(Constants.L,data[0], shg,"query_result1");//data[99000]��Ϊ��ѯ��
//		shi.query_execute(Constants.L,data[0], shg,"query_result2.txt");//data[99000]��Ϊ��ѯ��
//		shi.query_execute(Constants.L,data[0], shg,"query_result3.txt");//data[99000]��Ϊ��ѯ��
		
		for (int i = 0; i <10; i++) {
			shi.query_execute(Constants.L,data[i], shg,"query_result"+i);
		}
		
		
		//print some server2's unique_trapdoor
//		System.out.println("server2's unique_trapdoor:");
//		for (int i = 0; i < shi.unique_trapdoor.length; i++) {
//			for (int j = 0; j < shi.unique_trapdoor[i].length; j++) {
//				for (int j2 = 0; j2 < shi.unique_trapdoor[i][j].length; j2++) {
//					if (!shi.unique_trapdoor[i][j][j2].randomList.isEmpty()) {
//						for(int rand:shi.unique_trapdoor[i][j][j2].randomList)
//						{ 
//							System.out.print(rand+"��");
//						}
//						System.out.println();
//					}
//					
//				}
//			}
//		}
		//�Ա�LSH
		LSHindex lshi=new LSHindex();
		lshi.lsh_index_construct("decision.dat", shg);
		
		for (int i = 0; i <10; i++) {
			lshi.query_execute(Constants.L,data[i], shg,"lsh_query_result"+i);//data[0]��Ϊ��ѯ��
			//lshi.query_execute(Constants.L,data[99999], shg,"lsh_query_result1");//data[99000]��Ϊ��ѯ��
		}
		
		
		System.out.println("programme finished");
	}
}
