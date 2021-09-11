package edu.ncsu.csc216.wolf_scheduler.scheduler;


import edu.ncsu.csc216.wolf_scheduler.course.Activity;
import edu.ncsu.csc216.wolf_scheduler.course.ConflictException;
import edu.ncsu.csc216.wolf_scheduler.course.Course;
import edu.ncsu.csc216.wolf_scheduler.course.Event;
import edu.ncsu.csc216.wolf_scheduler.io.ActivityRecordIO;
import edu.ncsu.csc216.wolf_scheduler.io.CourseRecordIO;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Contains the necessary methods and constructors for creating and managing a schedule
 * @author Jaden Abrams
 *
 */
public class WolfScheduler {
	
	/** A list of all courses available. */
	ArrayList<Course> catalog;
	/** The courses a student is currently enrolled in. */
	ArrayList<Activity> schedule;
	/** The title of the schedule.*/
	String title;
	/**
	 * Constructs a WolfScheduler project and fills the catalog with Courses.
	 * @param fileName the file with the courses and Activities.
	 */
	public WolfScheduler(String fileName) {
		schedule = new ArrayList<Activity>();
		title = "My Schedule";
		try {
			catalog = CourseRecordIO.readCourseRecords(fileName);
		}
		catch(IOException e) {
			throw new IllegalArgumentException("Cannot find file");
		}
	}

	/**
	 * generates the course catalog as a 2D array for easy viewing.
	 * @return the course catalog
	 */
	public String[][] getCourseCatalog() {
		String[][] courseCatalog = new String[catalog.size()][3];
		if(catalog.size() == 0) {
			return courseCatalog;
		}
		for(int i = 0; i < catalog.size(); i++) {
			Course c = catalog.get(i);
			courseCatalog[i] = c.getShortDisplayArray();
		}
		return courseCatalog;
	}
	
	/**
	 * returns the schedule in a 2D array.
	 * @return the schedule
	 */
	public String[][] getScheduledActivities() {

		String[][] courseSchedule = new String[schedule.size()][3];
		if(schedule.size() == 0) {
			return new String[0][0];
		}
		for(int i = 0; i < schedule.size(); i++) {
			courseSchedule[i] = schedule.get(i).getShortDisplayArray();
		}
		return courseSchedule;
	}
	
	/**
	 * returns the schedule's title
	 * @return the title
	 */
	public String getScheduleTitle() {
		return title;
	}
	
	/**
	 * returns the full schedule in a 2D array
	 * @return the full schedule
	 */
	public String[][] getFullScheduledActivities() {
		if(schedule.size() == 0) {
			return new String[0][0];
		}
		String[][] courseSchedule = new String[schedule.size()][7];

		for(int i = 0; i < schedule.size(); i++) {
			courseSchedule[i] = schedule.get(i).getLongDisplayArray();
		}
		return courseSchedule;
	}
	
	/**
	 * gets a Course from the catalog
	 * @param name the course name
	 * @param section the course section
	 * @return the Course
	 */
	public Course getCourseFromCatalog(String name, String section) {
		for(int i = 0; i < catalog.size(); i++) {
			if(catalog.get(i).getName().equals(name)
			&& catalog.get(i).getSection().equals(section)) {
				return catalog.get(i);
			}
		}
		return null;
	}

	/**
	 * adds a course in the catalog to the schedule
	 * @param name the course name
	 * @param section the course section
	 * @return whether the course was added to the schedule
	 */
	public boolean addCourseToSchedule(String name, String section) {
		Course courseToAdd = this.getCourseFromCatalog(name, section);
		if(courseToAdd == null) {
			return false;
		}
		for(int i = 0; i < schedule.size(); i++) {
			if(schedule.get(i).isDuplicate(courseToAdd)) {
				throw new IllegalArgumentException("You are already enrolled in " + name);
			}
			try {
				schedule.get(i).checkConflict(courseToAdd);
			}
			catch(ConflictException e) {
				throw new IllegalArgumentException("The course cannot be added due to a conflict.");
			}
		}
		schedule.add(courseToAdd);
		return true;
	}
	

	/**
	 * removes a course from the schedule
	 * @param idx where the activity is
	 * @return whether the course was removed from the schedule
	 */
	public boolean removeActivityFromSchedule(int idx) {
		try {
			schedule.remove(idx);
			return true;
		} catch(IndexOutOfBoundsException e) {
			return false;
		}
	}

	/**
	 * writes the schedule to a text file
	 * @param fileName the name of the file to write to
	 */
	public void exportSchedule(String fileName) {
		try {
			ActivityRecordIO.writeActivityRecords(fileName, schedule);
		}
		catch(IOException e) {
			throw new IllegalArgumentException("The file cannot be saved.");
		}
		
	}

	/**
	 * clears the schedule
	 */
	public void resetSchedule() {
		schedule.clear(); 
		
	}

	/**
	 * sets the schedule title
	 * @param title the title to set
	 */
	public void setScheduleTitle(String title) {
		if(title == null) {
			throw new IllegalArgumentException("Title cannot be null.");
		}
		this.title = title;
		
	}
	/**
	 * Checks if the event already exists adds it to the schedule if it's not on it
	 * @param title the event title
	 * @param meetingDays when the event meets
	 * @param startTime when the event starts
	 * @param endTime when the event ends
	 * @param eventDetails miscellaneous details about the event
	 * @return whether the event was added
	 */
	public boolean addEventToSchedule(String title, String meetingDays, int startTime, int endTime, String eventDetails) {
		Event event = new Event(title, meetingDays, startTime, endTime, eventDetails);
		for(int i = 0; i < schedule.size(); i++) {
			if(event.isDuplicate(schedule.get(i))) {
				throw new IllegalArgumentException("You have already created an event called " + title);
			}
			try {
				schedule.get(i).checkConflict(event);
			}
			catch(ConflictException e) {
				throw new IllegalArgumentException("The event cannot be added due to a conflict.");
			}
		}
		schedule.add(event);
		return true;
	}


	}

