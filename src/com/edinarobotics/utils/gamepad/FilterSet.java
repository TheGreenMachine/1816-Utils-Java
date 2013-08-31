package com.edinarobotics.utils.gamepad;

import java.util.Vector;

/**
 * Used to easily apply multiple filters to a GamepadResult.
 */
public class FilterSet implements GamepadFilter{
    private Vector filters;
    public FilterSet(){
        filters = new Vector();
    }
    
    /**
     * Adds a filter to this FilterSet.
     * @param newFilter the filter to add to this FilterSet.
     */
    public void addFilter(GamepadFilter newFilter){
        filters.addElement(newFilter);
    }
    
    /**
     * Filters a GamepadResult through all the filters in this FilterSet.
     * @param toFilter the GamepadResult to filter.
     * @return the filtered GamepadResult.
     */
    public GamepadResult filter(GamepadResult toFilter){
        GamepadResult lastResult = toFilter;
        for(int i=0;i<filters.size();i++){
            lastResult = ((GamepadFilter)filters.elementAt(i))
                    .filter(lastResult);
        }
        return lastResult;
    }
}
