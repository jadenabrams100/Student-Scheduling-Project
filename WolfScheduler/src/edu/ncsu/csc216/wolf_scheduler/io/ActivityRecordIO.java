package edu.ncsu.csc216.wolf_scheduler.io;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

import edu.ncsu.csc216.wolf_scheduler.course.Activity;

/**
 * Contains all the necessary methods for writing activities
 * to text files
 * @author Jaden Abrams
 *
 */
public class ActivityRecordIO {

	/**
	 * Writes the activity records from an ArrayList to a file
	 * @param fileName the name of the file to write to
	 * @param activities the ArrayList with the activity information
	 * @throws IOException if there is trouble writing to the file
	 */
	public static void writeActivityRecords(String fileName, ArrayList<Activity> activities) throws IOException {
		PrintStream fileWriter = new PrintStream(new File(fileName));
	
		for (int i = 0; i < activities.size(); i++) {
		    fileWriter.println(activities.get(i).toString());
		}
	
		fileWriter.close();
	
	}

}
