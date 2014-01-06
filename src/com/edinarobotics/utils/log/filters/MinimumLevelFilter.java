package com.edinarobotics.utils.log.filters;

import com.edinarobotics.utils.log.Filter;
import com.edinarobotics.utils.log.Level;

/**
 * This filter implementation only passes log events that are at least as
 * severe as a given severity.
 */
public class MinimumLevelFilter implements Filter {
    private Level minimumLevel;
    
    /**
     * Constructs a MinimumLevelFilter that passes only log events that are
     * at least as severe as {@code minimumLevel}.
     * @param minimumLevel The minimum severity for this MinimumLevelFilter.
     */
    public MinimumLevelFilter(Level minimumLevel){
        this.minimumLevel = minimumLevel;
    }
    
    /**
     * Returns the Level object representing the minimum severity accepted
     * by this MinimumLevelFilter.
     * @return The Level object representing the minimum severity accepted by
     * this MinimumLevelFilter.
     */
    public Level getMinimumLevel(){
        return minimumLevel;
    }
    
    /**
     * Determines whether a log event meets this MinimumLevelFilter's criteria.
     * 
     * This method will pass the event if {@code leve} is at least as
     * severe as the minimum severity passed to this Filter's constructor.
     * @param level The level associated with the log event.
     * @param message The message associated with the log event.
     * @param thrown The optional throwable associated with the log event.
     * @return {@code true} if this event is at least as severe as the minimum
     * acceptable severity for this MinimumLevelFilter, {@code false} otherwise.
     */
    public boolean filter(Level level, String message, Throwable thrown){
        return level.compareTo(getMinimumLevel()) >= 0;
    }
    
    /**
     * Returns a hash code value for this MinimumLevelFilter, as required
     * by {@link Object#hashCode()}.
     * @return An {@code int} hash code value for this MinimumLevelFilter.
     */
    public int hashCode() {
        return 9+31*minimumLevel.hashCode();
    }
    
    /**
     * Determines whether another object is equal to this MinimumLevelFilter.
     * An object is equal to this MinimumLevelFilter if it if also a
     * MinimumLevelFilter and has the save threshold severity.
     * @param other The object to be tested for equality against this
     * MinimumLevelFilter.
     * @return {@code true} if the objects are equal as defined above,
     * {@code false} otherwise.
     */
    public boolean equals(Object other){
        if(other instanceof MinimumLevelFilter){
            return ((MinimumLevelFilter)other).getMinimumLevel().equals(getMinimumLevel());
        }
        return false;
    }
    
    /**
     * Returns a String representation of this MinimumLevelFilter.
     * 
     * The String representation of this MinimumLevelFilter is intended
     * to be human-readable.
     * @return A human-readable String representation of this
     * MinimumLevelFilter.
     */
    public String toString() {
        return "<MinimumLevelFilter: "+getMinimumLevel()+">";
    }
}
