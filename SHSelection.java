
public class SHSelection {
	private int decisionsignal[] = new int[Constants.datasize];
	private int hashkeylength[][] = new int[Constants.L][Constants.bucketnum];
	private IO io=new IO();
	
	public void radius_selection(String decision_file,SHGeneral shg)
	{
		for(int i=0;i<Constants.datasize;i++)
		{
			decisionsignal[i]=0;
		}
		
		/* Check which Alternate value of Rrank is good for the points */
	    for(int i = 0; i < Constants.Alter - 1; i++) 
	    {
	    	radius_test(i,shg);
	    }
	    
	    for(int i = 0; i < Constants.datasize; i++)
	    {
	        /* If we couldn't find a good Rrank, we assign
	           it be the maximum possible, i.e., the tightest grid */
	        if(decisionsignal[i] == 0)
	        {
	            decisionsignal[i] = 1;
	            shg.decision[i] = Constants.Alter - 1;
	        }
	    }
	    for(int i=0;i<Constants.datasize;i++)
	    {
	    	System.out.println("data"+i+"'s R:"+shg.decision[i]);
	    }
	 // Now store the radius decision and that the decision is avaliable
	    shg.decisionavailable = true;
	    io.diskwrite_int(decision_file, shg.decision, Constants.datasize);
	}

	private void radius_test(int Rrank,SHGeneral shg) 
	{
		System.out.println("SHSelection->radius_test R"+Rrank);
		for (int i = 0; i < Constants.L; i++) 
		{
			for (int j = 0; j < Constants.bucketnum; j++) 
			{
				hashkeylength[i][j]=0;
			}
		}
		
		/* We want to count the number of points hashed to each bucket
	       of each concatenative function, hashkeylength stores this value.*/
	    for(int k = 0; k < Constants.datasize; k++)
	    {
	        if(k % 100000 == 0) 
	        	System.out.println("current hashing data ");
	        // Generate the hashtable for datapoint 'k' with given radius ratio index
	        shg.tableindex(shg.dataproduct[k], Rrank, shg.datahashresult[k]);

	        // Count the number of points in each bucket of each concatenative function
	        for(int i = 0; i < Constants.L; i++) 
	        	hashkeylength[i][shg.datahashresult[k][i] % Constants.bucketnum]++;
	    }
	    
	    int sum = 0;        
	    // Keeps a count of number of points for this current Rrank
	    for(int i = 0; i < Constants.datasize; i++)
	    {
	        /* Compute the number of concatenative functions which have
	           more than the threshold number of points */
	        int sumcount = 0;
	        for(int j = 0; j < Constants.L; j++)
	        {
	            if(hashkeylength[j][shg.datahashresult[i][j] % Constants.bucketnum] >= Constants.thresholdpoint) 
	            sumcount++;
	        }

//	        If the number of concatenative functions passing the thresholdpoints
//	        is greater than the threshhold for concatenative functions, we set
//	        decision to Rrank 
	        if(sumcount >= Constants.thresholdtable)
	        {
	            if(decisionsignal[i] == 0)
	            {
	                sum++;
	                decisionsignal[i] = 1;
	                shg.decision[i] = Rrank;
	            }
	        }
	    }
	    System.out.println("the round of"+Rrank+"is end");
		
	}
}
