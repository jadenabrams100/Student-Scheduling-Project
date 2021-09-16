/**
 * 
 */
package edu.ncsu.csc216.wolf_scheduler.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import java.util.Scanner;

import edu.ncsu.csc216.wolf_scheduler.course.Course;

/**
 * The Necessary methods and objects for reading course records from .txt files
 * 
 * @author Jaden Abrams
 *
 */
public class CourseRecordIO {
	
	/**
	 * Reads the contents of course files into memory
	 * @param fileName the file with the course information
	 * @return an ArrayList with all the course information
	 * @throws FileNotFoundException if fileName cannot be found
	 */
	public static ArrayList<Course> readCourseRecords(String fileName) throws FileNotFoundException {
	    Scanner fileReader = new Scanner(new FileInputStream(fileName));
	    ArrayList<Course> courses = new ArrayList<Course>();
	    while (fileReader.hasNextLine()) { 
	        try {
	            Course course = readCourse(fileReader.nextLine()); 
	            
	            boolean duplicate = false;
	            for (int i = 0; i < courses.size(); i++) {
	                
	                Course current = courses.get(i);
	                
	                if (course.getName().equals(current.getName()) &&
	                        course.getSection().equals(current.getSection())) {
	                    
	                    duplicate = true;
	                    break;
	                }
	            }
	            if (!duplicate) {
	                courses.add(course);
	            }
	        } catch (IllegalArgumentException e) {
	            continue;
	        }
	    }
	    fileReader.close();
	    return courses;
	}

	/**
	 * takes in a line from readCourseRecords and converts into a Course object
	 * @param nextLine the next line to read in
	 * @return the course
	 * @throws IllegalArgumentException if the course string is invalid
	 */
	private static Course readCourse(String nextLine) {
		Scanner s = new Scanner(nextLine);
		s.useDelimiter(",");
		try {
			String name, title, section, instructorId, meetingDays;
			int credits, startTime, endTime;
			name = s.next();
			title = s.next();
			section = s.next();
			credits = Integer.parseInt(s.next());
			instructorId = s.next();
			meetingDays = s.next();
			if ("A".equals(meetingDays)) {
				if(s.hasNext()) {
					s.close();
					throw new IllegalArgumentException("Invalid meeting days and time");
				}
				s.close();
				return new Course(name, title, section, credits, instructorId, meetingDays);
			}
			startTime = s.nextInt();
			endTime = s.nextInt();
			if(s.hasNext()) {
				s.close();
				throw new IllegalArgumentException("Invalid Course Record");
			}
			s.close();
			return new Course(name, title, section, credits, instructorId, meetingDays, startTime, endTime);
		}
		catch(Exception e){
			s.close();
			throw new IllegalArgumentException();
			
		}
		
	}

}
