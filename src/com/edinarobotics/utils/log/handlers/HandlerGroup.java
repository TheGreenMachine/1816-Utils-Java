package com.edinarobotics.utils.log.handlers;

import com.edinarobotics.utils.log.Handler;
import com.edinarobotics.utils.log.LogEvent;

/**
 * The HandlerGroup dispatches log events to multiple Handler
 * implementations.
 */
public class HandlerGroup implements Handler {
    private Handler[] handlers;
    
    /**
     * Constructs a new HandlerGroup which will dispatch to the handlers
     * contained in {@code handlers}.
     * @param handlers The handlers to which this HandlerGroup is to
     * dispatch log events.
     */
    public HandlerGroup(Handler[] handlers){
        this.handlers = handlers;
    }
    
    /**
     * Handles a LogEvent by dispatching it to all the handlers
     * in this group.
     * @param event The LogEvent to be dispatched to the handlers in this
     * group.
     */
    public void handle(LogEvent event){
        for(int i = 0; i < handlers.length; i++){
            handlers[i].handle(event);
        }
    }
    
    /**
     * Returns an array containing the handlers to which this HanderGroup
     * will dispatch all LogEvents.
     * @return The array of handlers to which this HandlerGroup dispatches.
     */
    public Handler[] getHandlers(){
        return handlers;
    }
    
    /**
     * Returns a hash code value for this HandlerGroup as required by
     * {@link Object#hashCode()}.
     * @return An {@code int} hash code value for this object.
     */
    public int hashCode(){
        int hash = 3;
        for(int i = 0; i < handlers.length; i++){
            hash = 31*hash + handlers[i].hashCode();
        }
        return hash;
    }
    
    /**
     * Determines whether an object is equal to this HandlerGroup.
     * 
     * Another object is equal to this HandlerGroup if it is also a HandlerGroup
     * and if it contains equal Handler objects in the same order as this
     * HandlerGroup.
     * @param other The other object to be checked for equality against
     * this HandlerGroup.
     * @return {@code true} if the objects are equal as defined above,
     * {@code false} otherwise.
     */
    public boolean equals(Object other){
        if(other instanceof HandlerGroup){
            HandlerGroup otherHandler = (HandlerGroup)other;
            if(otherHandler.getHandlers().length != getHandlers().length){
                return false;
            }
            for(int i = 0; i < getHandlers().length; i++){
                if(!getHandlers()[i].equals(otherHandler.getHandlers()[i])){
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    
    /**
     * Returns a human-readable String representation of this HandlerGroup.
     * @return A human-readable String representation of this HandlerGroup.
     */
    public String toString(){
        String stringRepr = "<HandlerGroup: [";
        for(int i = 0; i < getHandlers().length; i++){
            stringRepr += getHandlers()[i].toString();
            if(i < (getHandlers().length - 1)){
                stringRepr += ",";
            }
            stringRepr += " ";
        }
        stringRepr += "]>";
        return stringRepr;
    }
}
