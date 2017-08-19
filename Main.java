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
		
		//建立索引(加密(置换+对称加密)、写入文件)
		SHindex shi=new SHindex();
		shi.index_construct("decision.dat", shg);
		
		//执行查询
		shi.query_execute(Constants.L,data[Constants.datasize-1], shg);//data[99000]作为查询点
		
		System.out.println("programme finished");
	}
}
