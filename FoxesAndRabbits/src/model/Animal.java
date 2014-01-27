package model;

import java.util.List;
import java.util.Random;

/**
 * A class representing shared characteristics of animals.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2011.07.31
 */
public abstract class Animal implements Actor
{
    // Whether the animal is alive or not.
    private boolean alive;
    // The animal's field.
    private Field field;
    // The animal's position in the field.
    private Location location;
    // The animal's age.
    private int age;
    // Radom number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    
    /**
     * Create a new animal at location in field.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Animal(Field field, Location location)
    {
        alive = true;
        this.field = field;
        setLocation(location);
    }
    
    /**
     * Check whether the animal is alive or not.
     * @return true if the animal is still alive.
     */
    public boolean isActive()
    {
        return alive;
    }

    /**
     * Indicate that the animal is no longer alive.
     * It is removed from the field.
     */
    public void setDead()
    {
        alive = false;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }

    /**
     * Return the animal's location.
     * @return The animal's location.
     */
    public Location getLocation()
    {
        return location;
    }    
    
    /**
     * Return the animal's field.
     * @return The animal's field.
     */    
    public Field getField() {
    	return field;
    }
    
    /**
     * Place the animal at the new location in the given field.
     * @param newLocation The animal's new location.
     */
    public void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }
    
    public boolean canBreed() {
    	return age >= getBreedingAge();
    }
    
    public void setAge(int age) {
    	this.age = age;
    }
    
    public int getAge() {
    	return age;
    }
    
    public void incrementAge() {
    	setAge(getAge() + 1);
    	if(getAge() > getMaxAge()) {
    		setDead();
    	}
    }
    
    public int breed() {
    	int births = 0;
    	if(canBreed() && rand.nextDouble() <= getBreedingProbability()) {
    		births = rand.nextInt(getMaxLitterSize()) + 1;
    	}
    	return births;
    }
    
    abstract public int getBreedingAge();
    
    abstract public double getBreedingProbability();
    
    abstract public void act(List<Actor> newActors);
    
    abstract public int getMaxAge();
    
    abstract public int getMaxLitterSize();
    
}
