/**
 * 
 */
package edu.ncsu.csc216.wolf_scheduler.course;

/**
 * Contains the necessary constructors and methods for the
 * new ConflictException
 * @author Jaden Abrams
 *
 */
public class ConflictException extends Exception {

	/** ID used for serialization. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * creates a ConflictException with a custom message
	 * @param message the message for the exception
	 */
	public ConflictException(String message) {
		super(message);
	}
	
	/**
	 * creates a ConflictException with a default message
	 */
	public ConflictException() {
		super("Schedule conflict.");
	}
}
