
public class KNN 
{
	public int knnlist[]=new int[Constants.K];
	public double distlist[]=new double[Constants.K];
	public double sqrtbound;
	private int tochange;
	private double bound;
	
	public void init() 
	{
	    for (int i = 0; i < Constants.K; i++)
	    {
	        knnlist[i] = -1;
	        distlist[i] = -1;
	    }
	    sqrtbound = Constants.datasize;
	}
	
	public void linearScan(float[][] data,float[] data2)
	{
		init();
	    for (int i = 0; i < Constants.datasize; i++)
	    {
	        addVertex(data, i, data2);
	    }
	}
	
	public void addVertex(float[][] data,int forcheck,float[] data2)
	{
		double dist;
	    dist = distancel2sq(Constants.D,data[forcheck], data2,0);
	    if (knnlist[Constants.K - 1] == -1)
	    {
	        for (int i = 0; i < Constants.K; i++)
	        {
	            if (knnlist[i] == -1)
	            {
	                knnlist[i] = forcheck;// forcheck here is a label for the datapoint
	                distlist[i] = dist;
	                if (i == Constants.K - 1) computeBound();
	                return;
	            }
	        }
	    }
	    if (dist >= bound) return;
	    knnlist[tochange] = forcheck;
	    distlist[tochange] = dist;
	    computeBound();
	}

	private void computeBound()
	{
		bound = distlist[0];
	    tochange = 0;
	    for (int i = 1; i < Constants.K; i++)
	    {
	        if (distlist[i] > bound)
	        {
	            bound = distlist[i];
	            tochange = i;
	        }
	    }
	    sqrtbound = Math.sqrt(bound);
	}
	
	private double distancel2sq(int dim, float[] data, float[] data2, double bound)
	{
		 // to be optimized: use bound to filt
	    float result = 0;
	    for (int i = 0; i < dim; i++)
	    {
	        result += (data[i] - data2[i]) * (data[i] - data2[i]);
	    }
	    if(result < 0)
	    	System.out.println("error negative distance");
	    return result;
	}
			
}
