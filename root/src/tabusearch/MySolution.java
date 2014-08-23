package tabusearch;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.coinor.opents.*;

import bd.Specialty;


public class MySolution extends SolutionAdapter 
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -6245482892303675375L;
	public Specialty spec;
	public String constraints="";
	public double value = 0;
    public MySolution(){
    	this.value=-2;
    	double[] temp = {this.value};
    	this.setObjectiveValue(temp);
    } // Appease clone()
    
    public MySolution( Specialty spec )
    {
        // Crudely initialize solution
    	this.spec=copyObject(spec);
    	this.value=spec.evaluateSchedule();
    	double[] objvalue= {value};
    	 this.setObjectiveValue(objvalue);
    }   // end constructor
    
    
    public MySolution( MySolution msol )
    {
        // Crudely initialize solution
        this.setSpec(msol.spec);
        this.value=msol.value;
        this.setObjectiveValue(copyObject(msol.getObjectiveValue()));
    }   // end constructor
    
    
    public void setObjectiveValue(){
    	
    	double[] value = { spec.evaluateSchedule() };
    	this.setObjectiveValue(value);
    }
    
    public void setSpec(Specialty spec)
    {
    	this.spec= copyObject(spec);
    }
    
    
    private <T extends Serializable> T copyObject(final T source) {
        if (source == null)
                throw new IllegalArgumentException("source is null");
        final T copy;
        try {
            copy = serializationClone(source);
            } catch (Exception e) {
                // (optional) die gloriously!
            throw new AssertionError("Error copying: " + source, e);
        }    
            return copy;
    }

    private <T extends Serializable> T serializationClone(final T source)
        throws IOException, ClassNotFoundException {
    ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
    ObjectOutputStream outputStream = new ObjectOutputStream(byteStream);

            // 1. serialize the object to the in-memory output stream
        outputStream.writeObject(source);

        ObjectInputStream inputStream = new ObjectInputStream(
            	new ByteArrayInputStream(byteStream.toByteArray()));

        // 2. deserialize the object from the in-memory input stream
        @SuppressWarnings("unchecked")
            final T copy = (T) inputStream.readObject();

        return copy; // NOPMD : v. supra
    }
    
    public Object clone()
    {   
        MySolution copy = (MySolution)super.clone();
        copy.spec = copyObject(this.spec);
        copy.setObjectiveValue(copyObject(this.getObjectiveValue()));
        return copy;
    }   // end clone
    
    
    public String toString()
    {
        StringBuffer s = new StringBuffer();
        this.constraints="";
		for (int i = 0; i < this.spec.getConstraints().size(); i++) {
			if(this.spec.isConstraintSatisfied(this.spec.getConstraints().get(i)))
				{
					this.constraints+="###Constraint type "+this.spec.getConstraints().get(i).getType()+" for Specialty\n";
				}
		}
		for (int i = 0; i < this.spec.getNurses().size(); i++) {
			//System.out.println("Nurse Constraints - "+solution.spec.getNurses().get(i).getConstraints().size());
			for (int k = 0; k < this.spec.getNurses().get(i).getConstraints().size(); k++) {
				if(this.spec.isConstraintSatisfied(this.spec.getNurses().get(i).getConstraints().get(k),this.spec.getNurses().get(i)))
					{
						this.constraints+="###Constraint type "+this.spec.getNurses().get(i).getConstraints().get(k).getType()+" for Nurse "+this.spec.getNurses().get(i).getName()+"\n";
					}
			}
		}
        s.append("### Specialty : " + spec.getName() + "\n");
        s.append("###Solution value: " + getObjectiveValue()[0]+"\n" );
        s.append("###Constraints complied:\n");
        s.append(constraints);
        s.append("##################");
        
        
        return s.toString();
    }   // end toString
    
}   // end class MySolution