package model;

import java.util.List;
import java.util.Iterator;
import java.util.Random;
/**
 * Model of a Gorilla
 * Gorilla's age, move, breeds, kill and die.
 * 
 * Authors Sander Hogewerf & Arnoud Brands
 * @Version 21-1-2014
 */

public class Gorilla extends Animal
{
    // The age at which a gorilla can multiply
    private static final int BREEDING_AGE = 10;
    // The age to which a gorilla can live.
    private static final int MAX_AGE = 150;
    // The likelihood of a gorilla breeding.
    private static final double BREEDING_PROBABILITY = 0.025;  
    // Max of kiddies at each birth.
    private static final int MAX_LITTER_SIZE = 5;
    // The value of days a gorilla can live without food.
    private static final int FOOD_VALUE = 15;    
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    
    private int foodLevel; // Foodlevel of the gorilla.
   
    /**
     * Create a new Gorilla. A Gorilla may be created with age zero (new born Gorilla) 
     * or with a random age.
     * 
     * @param randomAge If true, the gorilla will have a random age.
     * @param field The field currently occupied.
     * @param The location within the field.
     */
    
    public Gorilla(boolean randomAge, Field field, Location location)
    {
        super(field, location);
        
         if(randomAge) {
            setAge(rand.nextInt(MAX_AGE));
            foodLevel = rand.nextInt(FOOD_VALUE);
        }
        else {
            setAge(0);
            foodLevel = FOOD_VALUE;
        }
    } 
    
    public Gorilla(boolean randomAge, Field field, Location location, int foodLevel) {
        super(field, location);
        
        if(randomAge) {
           setAge(rand.nextInt(MAX_AGE));
           foodLevel = rand.nextInt(FOOD_VALUE);
       }
       else {
           setAge(0);
           foodLevel = FOOD_VALUE;
       }
    }
    /**
     * This is what the gorilla does most of the time - it runs around.
     * Sometimes it will breed, eat or die of old age.
     * 
     * @param newGorillas A list to return newly born gorillas
     */
    public void act(List<Actor> newGorrilas)
    {
        incrementAge();
        incrementHunger();
        if(isActive()) {
            giveBirth(newGorrilas);            
            // Move towards a source of food if found.
            Location location = getLocation();
            Location newLocation = findFood(location);
            if(newLocation == null) { 
                // No food found - try to move to a free location.
                newLocation = getField().freeAdjacentLocation(location);
            }
            // See if it was possible to move.
            if(newLocation != null) {
                setLocation(newLocation);
            }
            else {
                // Overcrowding.
                setDead();
            }
        }
    }
    
    public int getMaxAge() {
    	return MAX_AGE;
    }
    
    public void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 0) {
            setDead();
        }
    }
    
    /**
     * Look for rabbits and foxes adjacent to the current location.
     * Only the first live rabbit or fox is eaten.
     * @return Where food was found, or null if it wasn't.
     */
    public Location findFood(Location location)
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if(animal instanceof Rabbit) {
                Rabbit rabbit = (Rabbit) animal;
                if(rabbit.isActive()) { 
                    rabbit.setDead();
                    foodLevel = FOOD_VALUE;
                    return where;
                }
            }
            else if(animal instanceof Fox) {
                Fox fox = (Fox) animal;
                if(fox.isActive()) { 
                    fox.setDead();
                    foodLevel = FOOD_VALUE;
                    return where;
                }
            }
        }
        return null;
    }
    
    
    /**
     * Check whether or not this gorilla is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newGorillas A list to return newly born gorillas.
     */
    private void giveBirth(List<Actor> newGorrilas)
    {
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Gorilla young = new Gorilla(false, field, loc);
            newGorrilas.add(young);
        }
    }    
    
    public int getFoodLevel() {
    	return foodLevel;
    }
    
    public int getBreedingAge() {
    	return BREEDING_AGE;
    }
    
    public double getBreedingProbability() {
    	return BREEDING_PROBABILITY;
    }
    
    public int getMaxLitterSize() {
    	return MAX_LITTER_SIZE;
    }  
}

