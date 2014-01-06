package com.edinarobotics.utils.log.filters;

import com.edinarobotics.utils.log.Filter;
import com.edinarobotics.utils.log.Level;

/**
 * This filter implementation only passes a log event if it meets all
 * the criteria of an array of loggers.
 * Each logger in this group must pass the event in order for it to be
 * approved by this filter implementation.
 */
public class FilterGroup implements Filter{
    private Filter[] filters;
    
    /**
     * Constructs a new FilterGroup which proxies log requests
     * to the Filter objects in {@code filters}.
     * @param filters The array of Filter implementations that must approve
     * the log event in order for this FilterGroup to approve the event.
     */
    public FilterGroup(Filter[] filters){
        this.filters = filters;
    }
    
    /**
     * Returns the array of filters provided to the constructor.
     * @return The array of Filter implementations used by this FilterGroup
     * to check each log event.
     */
    public Filter[] getFilters(){
        return filters;
    }
    
    /**
     * Determines whether this FilterGroup approves a log event for handling.
     * 
     * This FilterGroup will approve a log event only if all filters in this
     * group approve it. This check is short-circuiting; the first
     * handler to fail the log event will result in the overall group failing
     * the event.
     * @param level The severity level associated with the log event.
     * @param message The String message associated with the log event.
     * @param thrown The optional Throwable associated with the log event.
     * @return {@code true} if this FilterGroup approves the log event
     * for handling, {@code false} otherwise.
     */
    public boolean filter(Level level, String message, Throwable thrown){
        for(int i = 0; i < filters.length; i++){
            if(!filters[i].filter(level, message, thrown)){
                return false;
            }
        }
        return true;
    }
    
    /**
     * Returns a hash code value for this FilterGroup as required by
     * {@link Object#hashCode()}.
     * @return An {@code int} hash code value for this FilterGroup.
     */
    public int hashCode(){
        int hash = 1;
        for(int i = 0; i < filters.length; i++){
            hash = 31*hash + filters[i].hashCode();
        }
        return hash;
    }
    
    /**
     * Determines whether an object is equal to this FilterGroup.
     * 
     * An object is equal to this FilterGroup if it is also a FilterGroup
     * and if it contains the same Filter objects in the same order as this
     * FilterGroup.
     * @param other The object to be tested for equality against this
     * FilterGroup.
     * @return {@code true} if the objects are equal as defined above,
     * {@code false} otherwise.
     */
    public boolean equals(Object other){
        if(other instanceof FilterGroup){
            FilterGroup otherGroup = (FilterGroup)other;
            if(otherGroup.getFilters().length != getFilters().length){
                return false;
            }
            for(int i = 0; i < getFilters().length; i++){
                if(!otherGroup.getFilters()[i].equals(getFilters()[i])){
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    
    /**
     * Returns a human-readable String representation of this FilterGroup.
     * 
     * This String representation indicates the nature and ordering of the
     * filters contained by this FilterGroup.
     * @return A String representation of this FilterGroup.
     */
    public String toString(){
        String stringRepr = "<FilterGroup: [";
        for(int i = 0; i < getFilters().length; i++){
            stringRepr += getFilters()[i].toString();
            if(i < (getFilters().length - 1)){
                stringRepr += ",";
            }
            stringRepr += " ";
        }
        stringRepr += "]>";
        return stringRepr;
    }
}

