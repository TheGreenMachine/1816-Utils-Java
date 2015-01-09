package com.edinarobotics.utils.gamepad.gamepadfilters;

import java.util.List;
import com.edinarobotics.utils.gamepad.GamepadAxisState;

/**
 * This class applies multiple other GamepadFilters to a Gamepad.
 */
public class GamepadFilterSet implements GamepadFilter{
    private GamepadFilter[] filters;
    
    /**
     * Constructs a new GamepadFilterSet that applies the given filters to the
     * gamepad in the order in which they are positioned in the array.
     * @param filters The array of filters to be applied to the gamepad.
     */
    public GamepadFilterSet(GamepadFilter[] filters){
        this.filters = new GamepadFilter[filters.length];
        System.arraycopy(filters, 0, this.filters, 0, filters.length);
    }
    
    /**
     * Constructs a new GamepadFilterSet that applies the given filters to the
     * gamepad in the order in which they are positioned in the Vector.
     * @param filters The Vector of GamepadFilter objects to be applied to
     * the Gamepad.
     */
    public GamepadFilterSet(List<GamepadFilterSet> filters){
        this((GamepadFilter[])filters.toArray());
    }
   
    /**
     * Filters the given GamepadAxisState object through the given set of
     * filters. The filters are applied in order.
     * @param toFilter The GamepadAxisState object to be filtered.
     * @return A new GamepadAxisState object representing the result of
     * filtering the input GamepadAxisState object.
     */
    public GamepadAxisState filter(GamepadAxisState toFilter){
        GamepadAxisState current = toFilter;
        for(int i = 0; i < this.filters.length; i++){
            current = this.filters[i].filter(current);
        }
        return current;
    }
}
