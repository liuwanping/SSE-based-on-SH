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
		
		//计算每个数据点欧氏距离的KNN
//		KNN knn =new KNN();
//		int dataknn[][]=new int[Constants.datasize][Constants.K];
//		for(int i=0;i<Constants.datasize;i++)
//		{
//			System.out.println("计算 the knn of"+i);
//			knn.linearScan(data, data[i]);
//			for(int j=0;j<Constants.K;j++)
//			{
//				dataknn[i][j]=knn.knnlist[j];
//			}
//		}
//		System.out.println("开始写knn到文件");
//		io.diskwriteknn_int("dataknn.txt", dataknn);
		
		//为每个data选择合适的半径
		SHGeneral shg=new SHGeneral();
		shg.init(data);
		SHSelection shs=new SHSelection();
		shs.radius_selection("decision.dat",shg);
		
		//建立索引(加密(置换+对称加密)、写入文件)，初始化unique_trapdoor
		SHindex shi=new SHindex();
		shi.index_construct("decision.dat", shg);
		
		
		//执行查询
//		shi.query_execute(Constants.L,data[0], shg,"query_result1");//data[99000]作为查询点
//		shi.query_execute(Constants.L,data[0], shg,"query_result2.txt");//data[99000]作为查询点
//		shi.query_execute(Constants.L,data[0], shg,"query_result3.txt");//data[99000]作为查询点
		
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
//							System.out.print(rand+"、");
//						}
//						System.out.println();
//					}
//					
//				}
//			}
//		}
		//对比LSH
		LSHindex lshi=new LSHindex();
		lshi.lsh_index_construct("decision.dat", shg);
		
		for (int i = 0; i <10; i++) {
			lshi.query_execute(Constants.L,data[i], shg,"lsh_query_result"+i);//data[0]作为查询点
			//lshi.query_execute(Constants.L,data[99999], shg,"lsh_query_result1");//data[99000]作为查询点
		}
		
		
		System.out.println("programme finished");
	}
}
