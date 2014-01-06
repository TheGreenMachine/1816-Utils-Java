package com.edinarobotics.utils.log.handlers;

import com.edinarobotics.utils.log.Formatter;
import com.edinarobotics.utils.log.Handler;
import com.edinarobotics.utils.log.LogEvent;
import com.edinarobotics.utils.log.formatters.PrintFormatter;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * The PrintHandler formats LogEvents and prints them to an OutputStream.
 */
public class PrintHandler implements Handler {
    private PrintStream outputStream;
    private Formatter formatter;
    
    /**
     * Constructs a new PrintHandler which prints to the given OutputStream
     * and formats LogEvent objects using a {@link PrintFormatter}
     * instance.
     * @param outputStream The OutputStream to which this PrintHandler is to
     * print.
     */
    public PrintHandler(OutputStream outputStream){
        this(outputStream, new PrintFormatter());
    }
    
    /**
     * Constructs a new PrintHandler which prints to the given OutputStream
     * and formats LogEvent objects using the given Formatter.
     * @param outputStream The OutputStream to which this PrintHandler
     * is to print.
     * @param formatter The Formatter to be used to format the LogEvent
     * objects for printing.
     */
    public PrintHandler(OutputStream outputStream, Formatter formatter){
        this.outputStream = new PrintStream(outputStream);
        this.formatter = formatter;
    }
    
    /**
     * Handles a LogEvent by formatting it and printing it to the given
     * OutputStream.
     * @param event The LogEvent to be printed to the given OutputStream.
     */
    public void handle(LogEvent event){
        outputStream.println(formatter.format(event));
    }
    
    /**
     * Returns a String representation of this PrintHandler.
     * 
     * This String representation is intended to be human-readable.
     * @return A human-readable String representation of this PrintHandler.
     */
    public String toString(){
        return "<PrintHandler: "+outputStream.toString()+", "+formatter.toString()+">";
    }
}
