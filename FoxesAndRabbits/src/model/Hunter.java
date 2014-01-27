package model;
import java.util.List;
import java.util.Iterator;

/**
 * A simple model of a hunter.
 * Foxes age, move, eat rabbits, and die.
 * 
 * @author Malcolm Kindermans
 * @version 2008.03.30
 */
public class Hunter implements Actor
{
	// Whether the hunter is active or not.
    private boolean alive;
    // The animal's field.
    private Field field;
    // The animal's position in the field.
    private Location location;
    // aantal keer dat een jager kan schieten.
    private static final int BULLETS = 1;

    /**
     * Create a hunter.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Hunter(Field field, Location location)
    {
    	alive = true;
        this.field = field;
        setLocation(location);
    }
    
    /**
     * Place the hunter at the new location in the given field.
     * @param newLocation The hunter's new location.
     */
    public void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }
    
    /**
     * Return the hunter's location.
     * @return The hunter's location.
     */
    public Location getLocation()
    {
        return location;
    }
    
    /**
     * Return the hunter's field.
     * @return The hunter's field.
     */
    public Field getField()
    {
        return field;
    }
    
    /**
     * This is what the hunter does most of the time: it hunts for
     * foxes and gorillas.
     * @param field The field currently occupied.
     */
    public void act(List<Actor> newHunters)
    {
        if(isActive()) {           
            // Move towards a source of food if found.
            Location location = getLocation();
            shoot(BULLETS, location);
            Location newLocation = getField().freeAdjacentLocation(location);
            // See if it was possible to move.
            if(newLocation != null) {
                setLocation(newLocation);
            }
            else {
                setLocation(location);
            }
        }
    }
    
    /**
     * Indicate that the hunter is no longer active.
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
     * Check whether the hunter is alive or not.
     * @return true if the hunter is still alive.
     */
    public boolean isActive()
    {
        return alive;
    }
    
    /**
     * Vertel de jager waar hij moet schieten en hoe vaak.
     * @param bullets Hoe vaak een jager kan schieten
     * @param location Waar de jager zelf op dit moment zich bevindt
     */
    public void shoot(int bullets, Location location)
    {
    	Field field = getField();
    	List<Location> randomLocations = field.getRandomLocations(bullets, getLocation());
    	Iterator<Location> it = randomLocations.iterator();
    	int shotsFired = 0;
    	while(it.hasNext() && shotsFired <= bullets) {
    		Location where = it.next();
    		Object animal = field.getObjectAt(where);
    		if(animal instanceof Rabbit) {
    			Rabbit rabbit = (Rabbit) animal;
    			if(rabbit.isActive()) {
    				rabbit.setDead();
    				shotsFired++;
    			}
    		}
    		else if(animal instanceof Gorilla) {
    			Gorilla gorilla = (Gorilla) animal;
    			if(gorilla.isActive()) {
    				gorilla.setDead();
    				shotsFired++;
    			}
    		}
    		else if(animal instanceof Fox) {
    			Fox fox = (Fox) animal;
    			if(fox.isActive()) {
    				fox.setDead();
    				shotsFired++;
    			}
    		}
    	}
    }
    
    /**
     * Tell the hunter to look for foxes and wolfes adjacent to its current location.
     * Only the first live fox or wolf is shot.
     * @param location Where in the field it is located.
     * @return Where pray was found, or null if it wasn't.
     */
    public Location findFood(Location location)
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if(animal instanceof Rabbit){
            	Rabbit rabbit = (Rabbit) animal;
            	if(rabbit.isActive()) {
            		rabbit.setDead();
            		// Remove the dead rabbit from the field.
            		return where;            		
            	}
            }
            else if(animal instanceof Fox) {
                Fox fox = (Fox) animal;
                if(fox.isActive()) { 
                    fox.setDead();
                    // Remove the dead fox from the field.
                    return where;
                }
            }
            else if(animal instanceof Gorilla) {
            	Gorilla gorilla = (Gorilla) animal;
            	if(gorilla.isActive()) {
            		gorilla.setDead();
            		// Remove the dead gorilla from the field.
            		return where;
            	}
            }
        }
        return null;
    }
    
}
