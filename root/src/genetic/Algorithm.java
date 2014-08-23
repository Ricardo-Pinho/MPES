package genetic;

import java.util.ArrayList;

import bd.Nurse;
import bd.Specialty;
import logic.Global;
import tabusearch.MyObjectiveFunction;
import tabusearch.MySolution;




public class Algorithm {


    /* Public methods */
    
    // Evolve a population
    public static void evolvePopulation() {
    	MySolution[] pop = Global.solutions;
        MySolution[] newPopulation = new MySolution[pop.length];
        for (int i = 0; i < newPopulation.length; i++) {
			newPopulation[i]= new MySolution();
		}

        // Keep our best individual
        if (Global.elitism) {
            saveIndividual(newPopulation, 0, getBest(pop));
        }

        // Crossover population
        int elitismOffset;
        if (Global.elitism) {
            elitismOffset = 1;
        } else {
            elitismOffset = 0;
        }
        // Loop over the population size and create new individuals with
        // crossover
        for (int i = elitismOffset; i < pop.length; i++) {
            MySolution indiv1 = tournamentSelection(pop);
            MySolution indiv2 = tournamentSelection(pop);
            MySolution newIndiv = crossover(indiv1, indiv2);
            saveIndividual(newPopulation, i, newIndiv);
        }

        // Mutate population
        for (int i = elitismOffset; i < newPopulation.length; i++) {
            mutate(newPopulation[i]);
        }
        MyObjectiveFunction fun = new MyObjectiveFunction();
        for (int i = 0; i < Global.solutions.length; i++) {
			newPopulation[i].setObjectiveValue(fun.evaluate(newPopulation[i], null));
        	Global.solutions[i] = new MySolution(newPopulation[i]);
		}

    }

    private static void saveIndividual(MySolution[] newPopulation, int i, MySolution best) {
		newPopulation[i]=best;
		
	}

	private static MySolution getBest(MySolution[] pop) {
		
        int pos=0;
        int max=0;
        for (int i = 0; i < pop.length; i++) {
        	if(max<pop[i].getObjectiveValue()[0])
        	{
        		pos=i;
        		max=(int) pop[i].getObjectiveValue()[0];
        	}
		}
        return pop[pos];
	}

	// Crossover individuals
    private static MySolution crossover(MySolution indiv1, MySolution indiv2) {
    	MySolution newSol = new MySolution(indiv1);
        // Loop through genes
        for (int i = 0; i < 7; i++) {
        	for (int j = 0; j < 3; j++) {
                if (Math.random() <= Global.uniformRate) {} 
                else {
                	ArrayList<Nurse> nurses = indiv2.spec.getAssignedNurses(i, j);
                    newSol.spec.getSchedule().setAssigment(i, j, nurses);
                    // Crossover
                	}
        	}
        }
        return newSol;
    }

    // Mutate an individual
    private static void mutate(MySolution indiv) {
        // Loop through genes
    	Specialty spec = indiv.spec;
    	for (int i = 0; i < 7; i++) {
        	for (int j = 0; j < 3; j++) {
	            for (int k = 0; k < spec.getSchedule().getAssigment(i,j).size(); k++) {
	        		if (Math.random() <= Global.mutationRate) {
		                // Create random gene
		                for (int h = 0; h < spec.getNurses().size(); h++) {
		                	Nurse nurse2 =spec.getNurses().get(h);
							if(!(spec.getSchedule().isAssigned(i, j, nurse2)))
							{
								spec.getSchedule().removeNurse(i,j,k);
								spec.getSchedule().AssignNurse(i, j, nurse2);
							}
						}
		            }
				}
        	}
        }
    }

    // Select individuals for crossover
    private static MySolution tournamentSelection(MySolution[] pop) {
        // Create a tournament population
    	int tournamentSize = Global.MemNumber;
        MySolution[] tournament = new MySolution[tournamentSize];
        // For each place in the tournament get a random individual
        for (int i = 0; i < tournamentSize; i++) {
            int randomId = (int) (Math.random() * pop.length);
            tournament[i]= pop[randomId];
        }
        // Get the fittest
        MySolution fittest = getBest(tournament);
        return fittest;
    }
}
