//******************************************************************************
//
// File:    Lemoine.java

// Version: 1.0
//
//******************************************************************************


/**
 * Class Lemoine which acts as a container class for the value of n, p and q.
 * Contains methods to make a copy, check max p and do assignments of fields
 * in the object.
 * @author Omkar Kakade
 * @version 26-Sep-2018
 */
public class Lemoine implements Cloneable{
	
	// data members
	public int n;
	public int p;
	public int q;
	
	/**
	 * Construct an uninitialized Lemoine object.
	 */
	public Lemoine()
    {
    }
	
	/**
	 * Constructor for Lemoine with 2 parameters.
	 * @param n The value of n.
	 * @param p The value of p.
	 */
	public Lemoine(int n, int p) {
		this.n =n ;
		this.p =p;
	}

	/**
	 * Construct a new Lemoine that is a deep copy of the given Lemoine.
	 * @param lemo
	 */
	public Lemoine(Lemoine lemo)
    {
    copy (lemo);
    }

	/**
	 * Make this Lemoine be a deep copy of the given Lemoine Object.
	 * @param lemo
	 * @return
	 */
	public Lemoine copy (Lemoine lemo)
    {
    this.n = lemo.n;
    this.p = lemo.p;
    this.q = lemo.q;
    return this;
    }

	/**
	 * Create a clone of this Lemoine.
	 * 
	 * @return Clone
	 * @exception CloneNotSupportedException
	 * 	Thrown to indicate that the clone method in class Object has been called to clone an object,
	 *  but that the object's class does not implement the Cloneable interface
	 */
	public Object clone()
    {
    try
       {
       Lemoine lemo = (Lemoine) super.clone();
       lemo.copy (this);
       return lemo;
       }
    catch (CloneNotSupportedException exc)
       {
       throw new RuntimeException ("Shouldn't happen", exc);
       }
    }

	/**
	 * Method to update the fields of this variable if the given variable
	 * has a higher value of p.
	 * @param lemo
	 */
	public void max(Lemoine lemo)
	{
	
		if (lemo.p > this.p) {
			this.p=lemo.p;
			this.n=lemo.n;
			this.q=lemo.q;

		}	
		
			
	}
	
	/**
	 * Method to find smallest p value for a particular n.
	 * Also finds q and checks if q is Prime.
	 * @param primeObj
	 * @param n
	 */
	public void smallestP(Prime.Iterator primeObj, int n) {
		int localn = n;
		int localp = 0;
		int localq = 0;
		for (int x = 1; x <= n; x ++ ){
			
			// q is prime and odd from the iterator.
			localp = primeObj.next(); 	
			localq = (localn - localp)/2;  
			
			//Check if prime.
			if (Prime.isPrime(localq) == true){
				Assigner(localn, localp, localq);
				break;
			}
			else {
				continue;
			}
			
		}
	}
	
	/**
	 * Assigner method which assigns the value of n,p,q
	 * if the current value of p is higher than previous p
	 * of the object.
	 * 
	 * @param n
	 * @param p
	 * @param q
	 */
	public  void Assigner(int n, int p, int q) {
			if (p> this.p){
				this.n=n;
				this.p=p;
				this.q=q;
				
			}
	
}
}
