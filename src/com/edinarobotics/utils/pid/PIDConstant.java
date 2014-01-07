package com.edinarobotics.utils.pid;

/**
 * PIDConstant implements a single container for the PIDF values used
 * by the WPILib PID controllers. It wraps all four of these gains into
 * a more convenient package.
 */
public class PIDConstant {
    private final double P,I,D,F;
    
    /**
     * Constructs a new PIDConstant that stores the given PIDF values.
     * @param p The proportional gain to be stored.
     * @param i The integral gain to be stored.
     * @param d The derivative gain to be stored.
     * @param f The feed-forward gain to be stored.
     */
    public PIDConstant(double p, double i, double d, double f){
        this.P = p;
        this.I = i;
        this.D = d;
        this.F = f;
    }
    
    /**
     * Returns the stored proportional gain.
     * @return The stored proportional gain.
     */
    public double getP(){
        return this.P;
    }
    
    /**
     * Returns the stored integral gain.
     * @return The stored integral gain.
     */
    public double getI(){
        return this.I;
    }
    
    /**
     * Returns the stored derivative gain.
     * @return The stored derivative gain.
     */
    public double getD(){
        return this.D;
    }
    
    /**
     * Returns the stored feed-forward gain.
     * @return The stored feed-forward gain.
     */
    public double getF(){
        return this.F;
    }

    /**
     * Computes an integer hash code for this object.
     * @return An integer hash code for this PIDConstant.
     */
    public int hashCode() {
        //Auto-generate hash code method
        int hash = 7;
        hash = 71 * hash + (int) (Double.doubleToLongBits(this.P) ^ (Double.doubleToLongBits(this.P) >>> 32));
        hash = 71 * hash + (int) (Double.doubleToLongBits(this.I) ^ (Double.doubleToLongBits(this.I) >>> 32));
        hash = 71 * hash + (int) (Double.doubleToLongBits(this.D) ^ (Double.doubleToLongBits(this.D) >>> 32));
        hash = 71 * hash + (int) (Double.doubleToLongBits(this.F) ^ (Double.doubleToLongBits(this.F) >>> 32));
        return hash;
    }
    
    /**
     * Determines whether this PIDConstant is equal to another object.
     * Another object is equal to this PIDConstant if it is also
     * a PIDConstant and if each of its PIDF values are equal to the PIDF
     * values stored in this PIDConstant.
     * @param other The object to be compared to this one for equality.
     * @return {@code true} if the objects are equal as described above,
     * {@code false} otherwise.
     */
    public boolean equals(Object other){
        if(other instanceof PIDConstant){
            PIDConstant otherP = (PIDConstant) other;
            return getP() == otherP.getP() &&
                    getI() == otherP.getI() &&
                    getD() == otherP.getD() &&
                    getF() == otherP.getF();
        }
        return false;
    }
    
    /**
     * Returns a human-readable String representation of this PIDConstant.
     * @return A human-readable representation of this PIDConstant as a String.
     */
    public String toString(){
        return "<PIDConst: "+getP()+", "+getI()+", "+getD()+", "+getF()+">";
    }
}
