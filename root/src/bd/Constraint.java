package bd;

import java.io.Serializable;

public class Constraint implements Serializable {
	
	/**
	 * Soft Constraints
	 * Type index:
	 * 0 - Max Assigns (Specialty)
	 * 1 - All day break start with assigned shift at night - 3
	 * 2 - Time service (Nurse)
	 * 3 - Max Assigns (Nurse) - 10
	 * 4 - Min Consecutive Days (Nurse) - 1
	 * 5 - Max Consecutive Days (Nurse) - 5
	 * - - Min free Consecutive Days (Nurse)
	 * - - Max free Consecutive Days (Nurse) 
	 * 6 - Max Assigns per Day (Nurse) - 5
	 * 7 - Number of Break Days (Nurse) - 5
	 */
	private static final long serialVersionUID = -5544868744010800690L;
	//private String description; //descricao da constraint
	//private boolean active; //se a constraint estï¿½ a ser considerada ou nï¿½o
	private int type;//tipo de constraint. Isto serve para identificar que constraint ï¿½. Necessario definir os tipos
	private int value; // valor a ter em conta na restrição
	
	
	public Constraint(int type, int value)
	{
		//this.setDescription(desc);
		//this.setActive(active);
		this.setType(type);
		this.setValue(value);
	}


	/*public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public boolean isActive() {
		return active;
	}


	public void setActive(boolean active) {
		this.active = active;
	}*/


	public int getType() {
		return type;
	}


	public void setType(int type) {
		this.type = type;
	}


	public int getValue() {
		return value;
	}


	public void setValue(int value) {
		this.value = value;
	}
}
