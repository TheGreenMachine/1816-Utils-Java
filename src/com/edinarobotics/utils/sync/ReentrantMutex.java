package com.edinarobotics.utils.sync;

/**
 * Implements a simple mutex in the style of Java's ReentrantLock. Only a single
 * thread my possess this mutex at a time, but the mutex can be reentered. The
 * same thread can acquire the lock multiple times.
 */
public class ReentrantMutex {
    private final Object lock;
    private boolean held;
    private Thread holdingThread;
    private int holdCount;
    
    /**
     * Constructs a new ReentrantMutex. The new mutex is created unlocked.
     */
    public ReentrantMutex(){
        //Create a new unlocked mutex
        lock = new Object();
        held = false;
        holdingThread = null;
        holdCount = 0;
    }
    
    /**
     * Checks if this mutex is currently held by any thread.
     * @return {@code true} if the mutex is currently held, {@code false}
     * otherwise.
     */
    public boolean isHeld(){
        //Return held state
        return held;
    }
    
    /**
     * Acquires the mutex for the calling thread, blocks until this operation
     * succeeds or until the calling thread is interrupted.
     * @throws InterruptedException If the calling thread is interrupted
     * while waiting to acquire the mutex.
     */
    public void acquire() throws InterruptedException {
        synchronized(lock){
            //Synchronize on lock object to avoid concurrent modification
            if(!held || (held && holdingThread == Thread.currentThread())){
                //The lock is not held or we are the holding thread
                //Either acquire the lock or reenter (increment locking count)
                holdCount++;
                holdingThread = Thread.currentThread();
                held = true;
            }
            else{
                //Lock is held by someone else, wait for release
                lock.wait();
            }
        }
    }
    
    /**
     * Acquires the mutex for the calling thread, blocks until this operation
     * succeeds, until the calling thread is interrupted or until the
     * timeout has elapsed. This function does not throw an exception if
     * the timeout elapses, check if the lock was aquired with
     * {@link #isHoldingThread(java.lang.Thread)}.
     * @throws InterruptedException If the calling thread is interrupted
     * while waiting to acquire the mutex.
     */
    public void acquire(long timeout) throws InterruptedException {
        synchronized(lock){
            //Synchronize on lock object to avoid concurrent modification
            if(!held || (held && holdingThread == Thread.currentThread())){
                //The lock is not held or we are the holding thread
                //Either acquire the lock or reenter (increment locking count)
                holdCount++;
                holdingThread = Thread.currentThread();
                held = true;
            }
            else{
                //Lock is held by someone else, wait for release with timeout
                lock.wait(timeout);
            }
        }
    }
    
    /**
     * Checks whether the specified thread is currently holding the lock.
     * @param thread The Thread object to be checked for ownership of the lock.
     * @return {@code true} if the given Thread is holding the mutex,
     * {@code false} otherwise.
     */
    public boolean isHoldingThread(Thread thread){
        synchronized(lock){
            //Synchronize on lock object to avoid concurrent modification
            //Check if given thread is holding the lock
            return held && holdCount > 0 && holdingThread == thread;
        }
    }
    
    /**
     * Releases one level of the calling thread's hold on this mutex.
     * Once the final level of locking is released, the mutex becomes
     * available for other threads to acquire.
     * @throws IllegalStateException If the calling thread does not hold
     * the mutex.
     */
    public void release() {
        synchronized(lock){
            //Synchronize on lock object to avoid concurrent modification
            if(!held || (held && holdingThread != Thread.currentThread())){
                //Lock is not held or held by someone else
                //We cannot release such a lock
                throw new IllegalStateException("Cannot release non-held mutex");
            }
            else{
                //We hold the lock, decrement the hold count
                holdCount--;
                if(holdCount <= 0){
                    //Lock is fully released, unlock the mutex and notify
                    //a waiting thread.
                    held = false;
                    holdCount = 0;
                    holdingThread = null;
                    lock.notify();
                }
            }
        }
    }
}
