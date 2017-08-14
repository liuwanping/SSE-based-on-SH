
public class MyVector {
	public static float dotproduct(int dim,float data[],float vector[])
	{
		float result = 0;
	    for (int i = 0; i < dim; i++)
	    {
	        result += data[i] * vector[i];
	    }
	    return result;
		
	}
}
