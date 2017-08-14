import java.util.Random;


public class MyRandom 
{
	public static void rand_multi_gaussian(float array[], int n, float variance)
	{
		for(int i=0; i<n; i++)
		{
			array[i] = rand_single_gaussian(variance);
		}
		
	}

	public static float rand_single_gaussian(float variance) 
	{
		Random random = new Random();
		return (float) random.nextGaussian();
	}
	
	public static int in_random(int max)
	{
		Random r=new Random();
		return r.nextInt(max);
	}
}
