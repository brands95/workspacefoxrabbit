package model;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * A simple predator-prey simulator, based on a rectangular field
 * containing rabbits and foxes.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2011.07.31
 */
public class Simulator extends AbstractModel implements Runnable {
    // Constants representing configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 120;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 80;
    // The probability that a fox will be created in any given grid position.
    protected static final double FOX_CREATION_PROBABILITY = 0.03;
    // The probability that a rabbit will be created in any given grid position.
    protected static final double RABBIT_CREATION_PROBABILITY = 0.09;    
    // The probability that a gorilla will be created in any given grid position.
    protected static final double GORILLA_CREATION_PROBABILITY = 0.02; 
    // The probability that a hunter will be created in any given grid position.
    protected static final double HUNTER_CREATION_PROBABILITY = 0.01;
    
    protected static final double GRASS_CREATION_PROBABILITY = 0.1;

    // List of actors in the field.
    private List<Actor> actors;
    // The current state of the field.
    private Field field;
    // The current step of the simulation.
    private int step;
    
    private boolean run;
    
    private FieldStats stats;
    
    /**
     * Construct a simulation field with default size.
     */
    public Simulator() {
    	
    	actors = new ArrayList<Actor>();
        field = new Field(DEFAULT_DEPTH, DEFAULT_WIDTH);
        stats = new FieldStats();
        
        reset();
    }
    
    /**
     * Run the simulation from its current state for a reasonably long period,
     * (4000 steps).
     */
    public void runLongSimulation()
    {
        simulate(4000);
    }
    
    /**
     * Run the simulation from its current state for the given number of steps.
     * Stop before the given number of steps if it ceases to be viable.
     * @param numSteps The number of steps to run for.
     */
    public void simulate(int numSteps)
    {
        for(int step = 1; step <= numSteps && isViable(); step++) {
            simulateOneStep();
        }
    }
    
    /**
     * Run the simulation from its current state for a single step.
     * Iterate over the whole field updating the state of each
     * fox and rabbit.
     */
    public void simulateOneStep()
    {
        step++;

        // Provide space for newborn animals.
        List<Actor> newActors = new ArrayList<Actor>();        
        // Let all rabbits act.
        for(Iterator<Actor> it = actors.iterator(); it.hasNext(); ) {
            Actor actor = it.next();
            actor.act(newActors);
            if(! actor.isActive()) {
                it.remove();
            }
        }
               
        // Add the newly born foxes and rabbits to the main lists.
        actors.addAll(newActors);

        statusUpdate();
    }
        
    /**
     * Reset the simulation to a starting position.
     */
    public void reset()
    {
        step = 0;
        actors.clear();
        populate();
        
        // Show the starting state in the view.
        statusUpdate();
    }
    
    /**
     * Randomly populate the field with foxes and rabbits.
     */
    private void populate()
    {
        Random rand = Randomizer.getRandom();
        field.clear();
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                if(rand.nextDouble() <= HUNTER_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Hunter hunter = new Hunter(field, location);
                    actors.add(hunter);
                }
                else if(rand.nextDouble() <= GORILLA_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Gorilla gorilla = new Gorilla(true, field, location);
                    actors.add(gorilla);
                }
                else if(rand.nextDouble() <= FOX_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Fox fox = new Fox(true, field, location);
                    actors.add(fox);
                }
                else if(rand.nextDouble() <= RABBIT_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Rabbit rabbit = new Rabbit(true, field, location);
                    actors.add(rabbit);
                }
                else if(rand.nextDouble() <= GRASS_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Grass grass = new Grass(field, location);
                    actors.add(grass);
                }
                // else leave the location empty.
            }
        }
    }
    

    public int getStep() {
    	return step;
    }
    
    public FieldStats getFieldStats() {
    	return stats;
    }
    
    public Field getField() {
    	return field;
    }
    
    public boolean isViable() {
    	return stats.isViable(field);
    }
    
    public void start() {
    	new Thread(this).start();
    }
    
    public void stop() {
    	run = false;
    }
    
    @Override
    public void run() {
    	run = true;
    	while(run) {
    		simulateOneStep();
    		try { 
    			Thread.sleep(100);
    		}
    		catch (Exception e) {}
    	}
    }
}
