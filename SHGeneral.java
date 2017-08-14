
public class SHGeneral 
{
	private boolean isinit = false;
	private float familyvector[][] = new float[Constants.familysize][Constants.D+1];
	private int hashtableindex[][] = new int[Constants.L][Constants.M];
	
	public void init()
	{
		if(isinit)
			return;
		isinit = true;
		System.out.println("SHGeneral->init");
		family_generator();                     // Generate random familtyvectors
		generate_hashtableindex();              // Create the concatenation functions by sampling familyvectors
		productcomputer();                      // Compute product of data and familyvectors
		System.out.println("SHGeneral->init--END");
		
	}

	private void family_generator()
	{
		System.out.println("SHGeneral->family_generator");
		for(int i=0;i<Constants.familysize;i++)
		{
			MyRandom.rand_multi_gaussian(familyvector[i], Constants.D, 0);
			for (int j = 0; j < Constants.D; j++) //这一步不理解？？？
			{
				familyvector[i][j] =  (float) (familyvector[i][j]/Math.sqrt(Constants.D));
			}
		}
		System.out.println("SHGeneral->family_generator--END");
	}
	
	private void generate_hashtableindex() {
		System.out.println("SHGeneral->generate_hashtableindex");
		for(int i=0; i<Constants.L; i++)
		{
			familysample(hashtableindex[i], Constants.familysize, Constants.M);
		}
		System.out.println("SHGeneral->generate_hashtableindex--END");
		
	}
	
	private void productcomputer() {
		// TODO Auto-generated method stub
		
	}

	
}
