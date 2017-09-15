
public class Constants 
{
	static final public int D = 54;
	static final public int datasize = 100000;
	static final public int familysize = 100;
	static final public int L = 50;
	static final public int M = 20;
	static final public int Alter = 10;
	static final public int bucketnum = 9973;
	static final public float BaseR = 20;
	static final public float c = (float) 1.2;
	static final public int thresholdpoint = (int)(3 * datasize/bucketnum);
	static final public float thresholdtable = (int)(0.4 * L); 
	static final public int K=90;
	static final public int lsh_R=50;
	static final public int calPoint=10;
	//static final public int b = 2;
	//static final public int R = 30;
	//static final public int tableSize = 40;//一个table对应一个ＬＳＨ，即一个ａ向量，所以familysize=tablesize
	//static final public int K = 20;
	//static final public int M = 20;
	
//	const int D = 54;
//	const int K = 20;
//	const int L = 50;
//	const int M = 20;
//	const int datasize = 100000;
//	const int querysize = 100;
//	const int familysize = 100;
//	//selective hashing specific
//	const int Alter = 10;                                       /**< Alternate versions of the hash function for differnt R values */
//	const float BaseR = 20;                                     /**< The hash functions are (p, q, k, R * k) sesitive */
//	const float c = 1.2;                                        /**< Each Alter version has a c^i * R tolerance approximation */
//	const int bucketnum = 9973;                                 /**< Number of buckets */
//	const int thresholdpoint = (int)(3 * datasize/bucketnum);   /**< # of points in a bucket for it to qualify for a radius test */
//	const float thresholdtable = (int)(0.4 * L);                /**< # of tables for a radius to suceed */
//	const float ETRatio = 0.3;
//	const int Eperturbations = 3;      
}
