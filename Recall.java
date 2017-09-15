import java.io.IOException;
import java.util.ArrayList;


public class Recall 
{
	IO io=new IO();
	int dataknn[][]=new int[Constants.datasize][Constants.K];
	
	
	public double recall_compute(String resultfile) throws IOException
	{
		double samecount[]=new double[Constants.calPoint];
		double recall[]=new double[Constants.calPoint];
		double sum_recall=0;
		double average_recall=0;
		
		//把每个点实际knn存到数组里
		io.diskReadInt("E:/3-Tech/leetcode/SSE-based-on-SH/dataknn", dataknn);
				
		//基于sh的sse计算出来的最近邻
		for(int i=0;i<Constants.calPoint;i++)
		{
			ArrayList<Integer> hasingknn=new ArrayList<Integer>();
			io.diskReadKNN("E:/3-Tech/leetcode/SSE-based-on-SH/"+resultfile+i, hasingknn);
			for(int j=0;j<Constants.K;j++)
			{
				if(hasingknn.contains(dataknn[i][j]))
					samecount[i]++;
			}
			System.out.println("samecount:"+samecount[i]);
			recall[i]=samecount[i]/(double)Constants.K;
		}
		
		for(int i=0;i<Constants.calPoint;i++)
		{
			System.out.print(recall[i]+",");
			sum_recall=sum_recall+recall[i];
		}
		System.out.println();
		average_recall=sum_recall/Constants.calPoint;
		
		return average_recall;
	}
}