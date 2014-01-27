package model;

import java.util.List;

/**
 * Write a description of interface Actor here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public interface Actor
{
     /**
     * Make this actor act - that is: make it do
     * whatever it wants/needs to do.
     * @param newAnimals A list to receive newly born actors.
     */
    void act(List<Actor> newActors);
    /**
     * Check whether the actor is alive or not.
     * @return true if the actor is still alive.
     */
    boolean isActive();   
}

