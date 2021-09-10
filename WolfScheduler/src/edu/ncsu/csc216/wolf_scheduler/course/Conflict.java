/**
 * 
 */
package edu.ncsu.csc216.wolf_scheduler.course;

/**
 * An interface requiring child classes to have methods for resolving 
 * schedule conflicts
 * @author Jaden Abrams
 *
 */
public interface Conflict {
	
	/**
	 * checks if 2 activities have conflicting meeting times
	 * @param possibleConflictingActivity the activity that might conflict with the schedule
	 * @throws ConflictException if there is a conflict
	 */
	void checkConflict(Activity possibleConflictingActivity) throws ConflictException;
}
