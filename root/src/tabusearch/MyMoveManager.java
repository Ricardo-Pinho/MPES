package tabusearch;

import org.coinor.opents.*;

import bd.Nurse;


public class MyMoveManager implements MoveManager
{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 7962795734033840302L;

	public Move[] getAllMoves( Solution solution )
    {   
        int move1no = ((MySolution)solution).spec.getNumberofAssigns()*20;
        int move2no = ((MySolution)solution).spec.getNumberofAssigns()*((MySolution)solution).spec.getNurses().size();
        int movetotal = move1no+move2no;
        Move[] buffer = new Move[ movetotal ];
        int nextBufferPos = 0;

        // primeiro tipo de movimento: trocar turnos de uma enfermeira por outra
        
        for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 3; j++) {
				for (int k = 0; k < ((MySolution)solution).spec.getSchedule().getAssigment(i,j).size(); k++) {
					Nurse nurse= ((MySolution)solution).spec.getSchedule().getAssigment(i,j).get(k);
					for (int l = 0; l < 7; l++) {
						for (int h = 0; h < 3; h++) {
							if(((MySolution)solution).spec.getDaysAssign().get(l).get(h)>0)
							{
								Nurse nurse2=((MySolution)solution).spec.getSchedule().getAssigment(l, h).get(0);
								if(!(((MySolution)solution).spec.getSchedule().isAssigned(l,h,nurse)) && !(((MySolution)solution).spec.getSchedule().isAssigned(i,j,nurse2)))
								{
									int[] shift1 = new int[2];
									int[] shift2 = new int[2];
									shift1[0]=i;
									shift1[1]=j;
									shift2[0]=l;
									shift2[1]=h;
									//System.out.println("Buffer - "+nextBufferPos);
									//System.out.println("Nurse 1 - "+nurse.getName());
									//System.out.println("Nurse 2 - "+nurse2.getName());
									buffer[nextBufferPos++] = new MySwapMove(nurse,nurse2,shift1,shift2);
								}
							}
						}
					}
				}
			}
		}
         
        //segundo movimento: substituir uma enfermeira por outra num turno
        for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 3; j++) {
				for (int k = 0; k < ((MySolution)solution).spec.getSchedule().getAssigment(i,j).size(); k++) {
					Nurse nurse= ((MySolution)solution).spec.getSchedule().getAssigment(i,j).get(k);
					for (int l = 0; l < ((MySolution)solution).spec.getNurses().size(); l++) {
						Nurse nurse2=((MySolution)solution).spec.getNurses().get(l);
						if(!(((MySolution)solution).spec.getSchedule().isAssigned(i,j,nurse2)) && !(nurse.getName().equals(nurse2.getName())))
								{
									int[] shift1 = new int[2];
									shift1[0]=i;
									shift1[1]=j;
									//System.out.println("Buffer - "+nextBufferPos);
									//System.out.println("Nurse 1 - "+nurse.getName());
									//System.out.println("Nurse 2 - "+nurse2.getName());
									buffer[nextBufferPos++] = new MySwapMove(nurse,nurse2,shift1);
								}
						}
					}
				}
			}

        
        
        // Trim buffer
        Move[] moves = new Move[ nextBufferPos];
        System.arraycopy( buffer, 0, moves, 0, nextBufferPos );
        
        return moves;
    }   // end getAllMoves
    
    /*public int Factorial(int n)
    {
    	int factorial = n;
        for(int i =(n - 1); i > 1; i--)
        {
                factorial = factorial * i;
        }
        System.out.println("facto is "+factorial);
    	return factorial;
    }*/
    
}   // end class MyMoveManager