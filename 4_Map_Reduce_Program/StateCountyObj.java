//******************************************************************************
//
// File:    StateCountyObj.java

// Version: 1.0
//
//******************************************************************************


import edu.rit.io.Streamable;
import edu.rit.io.InStream;
import edu.rit.io.OutStream;
import java.io.IOException;


/**
 * Class StateCountObj encapsulates a State Name and County Name.
 * @author omkar
 * @version 03-Dec-2018
 */
public class StateCountyObj implements Streamable {
	
	//Class fields.
	private String stateName;
	private String countyName;
	
	/**
	 * getter method for returning stateName.
	 * @return stateName
	 */
	public String getStateName() {
		return stateName;
	}
	
	/**
	 * setter method for setting stateName.
	 * @param stateName
	 */
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	/**
	 * getter method for returning countyName.
	 * @return countyName
	 */
	public String getCountyName() {
		return countyName;
	}

	/**
	 * setter method for setting stateName.
	 * @param countyName
	 */
	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	
	
	/**
	 * Default constructor.
	 */
	public StateCountyObj()
	{
		stateName = null;
		countyName = null;
	}
	
	/**
	 * Constructor with stateName and countyName.
	 * @param stateName
	 * @param countyName
	 */
	public StateCountyObj(String stateName, String countyName) {
		this.stateName = stateName;
		this.countyName = countyName;
	}
	
	/**
	 * Returns a hashcode for the state name and county name combination.
	 * @return hashCodeLocal
	 */
	public int hashCode() {

		int hashCodeLocal = this.stateName.hashCode() + this.countyName.hashCode();;
		
		return hashCodeLocal;
	}
	
	/**
	 * Determine if this StateCountyObj is equal to the given object.
	 * 
	 * @param obj
	 * @return True if object is equal.
	 */
	public boolean equals (Object obj) {

		return (obj instanceof StateCountyObj) &&
		         (this.stateName.equals(((StateCountyObj)obj).stateName)) &&
		         (this.countyName.equals(((StateCountyObj)obj).countyName));

	}
	
	/**
	 * readIn Out method for Object Streaming.
	 * @throws IOException
	 */
	@Override
	public void readIn(InStream in) throws IOException {
		
		stateName = in.readString();
		countyName = in.readString();
		
	}

	/**
	 * write Out method for Object Streaming.
	 * @throws IOException
	 */
	@Override
	public void writeOut(OutStream out) throws IOException {
		
		out.writeString(stateName);
		out.writeString(countyName);
	}


}
