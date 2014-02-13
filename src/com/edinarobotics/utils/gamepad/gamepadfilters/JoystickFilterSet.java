package com.edinarobotics.utils.gamepad.gamepadfilters;

import com.edinarobotics.utils.gamepad.JoystickAxisState;
import java.util.Vector;

/**
 * This class applies multiple other JoystickFilters to a Joystick.
 */
public class JoystickFilterSet {
    private JoystickFilter[] filters;
    
    /**
     * Constructs a new JoystickFilterSet that applies the given filters to the
     * joystick in the order in which they are positioned in the array.
     * @param filters The array of filters to be applied to the joystick.
     */
    public JoystickFilterSet(JoystickFilter[] filters){
        this.filters = new JoystickFilter[filters.length];
        System.arraycopy(filters, 0, this.filters, 0, filters.length);
    }
    
    /**
     * Constructs a new JoystickFilterSet that applies the given filters to the
     * joystick in the order in which they are positioned in the Vector.
     * @param filters The Vector of JoystickFilter objects to be applied to
     * the Joystick.
     */
    public JoystickFilterSet(Vector filters){
        this(convertVectorToFilterArr(filters));
    }
    
    /**
     * An internal function used to convert a Vector of JoystickFilters into
     * an array of JoystickFilter objects.
     * This function is needed be cause a call to {@code this} must be the
     * first line in a constructor.
     * @param filters The Vector of JoystickFilter objects to be converted to
     * an array.
     * @return A new array of JoystickFilter objects created from the given
     * Vector.
     */
    private static JoystickFilter[] convertVectorToFilterArr(Vector filters){
        JoystickFilter[] filtersArr = new JoystickFilter[filters.size()];
        for(int i = 0; i < filters.size(); i++){
            filtersArr[i] = (JoystickFilter)filters.elementAt(i);
        }
        return filtersArr;
    }

    /**
     * Filters the given JoystickAxisState object through the given set of
     * filters. The filters are applied in order.
     * @param toFilter The JoystickAxisState object to be filtered.
     * @return A new JoystickAxisState object representing the result of
     * filtering the input JoystickAxisState object.
     */
    public JoystickAxisState filter(JoystickAxisState toFilter){
        JoystickAxisState current = toFilter;
        for(int i = 0; i < this.filters.length; i++){
            current = this.filters[i].filter(current);
        }
        return current;
    }
}
