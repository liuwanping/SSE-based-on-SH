
public class SHGeneral 
{
	private boolean isinit = false;
	private float familyvector[][] = new float[Constants.familysize][Constants.D+1];
	private int hashtableindex[][] = new int[Constants.L][Constants.M];
	public float dataproduct[][] = new float[Constants.datasize][Constants.familysize]; 
	public int decision[] = new int[Constants.datasize]; 
	public boolean decisionavailable = false;
	public int datahashresult[][] = new int[Constants.datasize][Constants.L];  
	private float R[] = new float[Constants.Alter];
	
	public SHGeneral() 
	{
		System.out.println("SHGeneral->SHGeneral");
	    R[0] = Constants.BaseR;
	    for(int i = 1; i < Constants.Alter; i++) R[i] = Constants.c * R[i-1];
	    isinit = false;
	    decisionavailable = false;
	}
	public void init(float data[][])
	{
		if(isinit)
			return;
		isinit = true;
		System.out.println("SHGeneral->init");
		family_generator();                     // Generate random familtyvectors
		generate_hashtableindex();              // Create the concatenation functions by sampling familyvectors
		productcomputer(data);                      // Compute product of data and familyvectors
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
	
	private void familysample(int[] hashtableindex, int familysize, int m) {
		int forchoose[] = new int[familysize];
		for(int i=0;i<familysize;i++)
		{
			forchoose[i] = i;
		}
		int forswap;
		int temp;
		for(int i=0;i<m;i++)
		{
			forswap = MyRandom.in_random(m-i)+i;
			temp=forchoose[i];
			forchoose[i]=forchoose[forswap];
			forchoose[forswap]=forchoose[temp];
			hashtableindex[i]=forchoose[i];
		}
		
		
	}

	private void productcomputer(float data[][]) {
		System.out.println("SHGeneral->productcomputer");
		for(int i = 0; i < Constants.datasize; i++)
	    {
	        for(int j = 0; j < Constants.familysize; j++)
	        {
	            dataproduct[i][j] = MyVector.dotproduct(Constants.D, data[i], familyvector[j]);
	        }
	    }
		System.out.println("SHGeneral->productcomputer--END");
	}
//	shg.dataproduct[k], Rrank, shg.datahashresult[k]
	public void tableindex(float product[], int Rrank, int table[])
	{
	    int familyint[] = new int[Constants.familysize];

	    // Choose the Radius Ratio according to passed index
	    float ratio = R[Rrank];

	    /* Apply the linear classifier corresponding to each function.
	       Linear classifier f() = w.x + w0
	       product = w.x
	       familyvector[i][D] = w0

	       The hash function is a p-stable distribution hash function with
	       the ratio indicating the slot/grid width. Larger the ratio, larger
	       the slot width.
	       */
	    for(int i = 0; i < Constants.familysize; i++)
	    {
	        float temp = product[i];
	        temp /=  ratio;                         // Standard method for LSH
	        temp += familyvector[i][Constants.D];

	        // Cast to an integer
	        familyint[i] = (int)temp;
	    }

	    /* Now we have to compute the value of the hash function for each of
	       the concatenative functions. Now, we know what 'M' random hashes
	       consititute each concatenative function which is stored in hashtableindex.
	       The rest is simple computation */
	    for(int l = 0; l < Constants.L; l++)
	    {
	        table[l] = 0;
	        for(int i = 0; i < Constants.M; i++)
	        {
	            // Start way to concatenate hash family
	            table[l] ^= familyint[hashtableindex[l][i]] + 0x9e3779b9 + (table[l] << 6) + (table[l] >> 2);
				//^= 表示“异或”，两个数的二进制形式异或，相同取0，不同取1
	            //System.out.println("datahashresult:["+l+","+i+":"+table[l]+"]");
	        }
	        System.out.println("table"+l+":"+table[l]);
	    }
	}
	
	
}

	

