/**
 * 
 */
package edu.ncsu.csc216.wolf_scheduler.course;

import java.util.Objects;

/**
 * The necessary fields and operations for the Course object
 * 
 * @author Jaden Abrams
 *
 */
public class Course extends Activity {

	/** The shortest a Course's name can be. */
	private static final int MIN_NAME_LENGTH = 5;
	/** The longest a Course's name can be. */
	private static final int MAX_NAME_LENGTH = 8;
	/** the least amount of letters a Course's name can have. */
	private static final int MIN_LETTER_COUNT = 1;
	/** the maximum amount of letters a Course's name can have. */
	private static final int MAX_LETTER_COUNT = 4;
	/** how many digits there are supposed to be in a course name */
	private static final int DIGIT_COUNT = 3;
	/** the length of a section number for a course. */
	private static final int SECTION_LENGTH = 3;
	/** Maximum number of credits a Course can be worth. */
	private static final int MAX_CREDITS = 5;
	/** Minimum number of credits a Course can be worth. */
	private static final int MIN_CREDITS = 1;
	/** Course's name. */
	private String name;
	/** Course's section. */
	private String section;
	/** Course's credit hours */
	private int credits;
	/** Course's instructor */
	private String instructorId;
	/**
	 * Constructs a Course object with values for all fields.
	 * 
	 * @param name         name of Course
	 * @param title        title of Course
	 * @param section      section of Course
	 * @param credits      credit hours for Course
	 * @param instructorId instructor's unity id
	 * @param meetingDays  meeting days for Course as series of chars
	 * @param startTime    start time for Course
	 * @param endTime      end time for Course
	 */
    public Course(String name, String title, String section, int credits, String instructorId, String meetingDays,
            int startTime, int endTime) {
        super(title, meetingDays, startTime, endTime);
        setName(name);
        setSection(section);
        setCredits(credits);
        setInstructorId(instructorId);
    }

	/**
	 * Constructor for Course when time is not given
	 * 
	 * @param name         name of Course
	 * @param title        title of Course
	 * @param section      section of Course
	 * @param credits      credit hours for Course
	 * @param instructorId instructor's unity id
	 * @param meetingDays  meeting days for Course as series of chars
	 */
	public Course(String name, String title, String section, int credits, String instructorId, String meetingDays) {
		this(name, title, section, credits, instructorId, meetingDays, 0, 0);
	}

	/**
	 * Returns the Course's name.
	 * 
	 * @return the name to set
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the Course's name.
	 * 
	 * @param name the name of the Course
	 */
	private void setName(String name) {
		if (name == null) {
			throw new IllegalArgumentException("Invalid course name.");
		}
		if (name.length() == 0) {
			throw new IllegalArgumentException("Invalid course name.");
		}
		if (name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH) {
			throw new IllegalArgumentException("Invalid course name.");
		}
		int numNums = 0;
		int numChars = 0;
		boolean space = false;
		for (int i = 0; i < name.length(); i++) {
			char c = name.charAt(i);
			if (!space) {
				if (Character.isLetter(c)) {
					numChars++;
				} else if (c == ' ') {
					space = true;
				} else {
					throw new IllegalArgumentException("Invalid course name.");
				}
			} else {
				if (Character.isDigit(c)) {
					numNums++;
				} else {
					throw new IllegalArgumentException("Invalid course name.");
				}
			}
		}
		if (numChars < MIN_LETTER_COUNT || numChars > MAX_LETTER_COUNT) {
			throw new IllegalArgumentException("Invalid course name.");
		}
		if (numNums != DIGIT_COUNT) {
			throw new IllegalArgumentException("Invalid course name.");
		}
		this.name = name;
	}

	/**
	 * Returns the Course's section.
	 * 
	 * @return the section
	 */
	public String getSection() {
		return section;
	}

	/**
	 * Sets the Course's section. A section is not valid if it is null, empty, not 3
	 * characters in length, or is not only digits. If it is not valid, an
	 * IllegalArgumentException is thrown.
	 * 
	 * @param section to set
	 * @throws IllegalArgumentException if the section is invalid
	 */
	public void setSection(String section) {
		if (section == null) {
			throw new IllegalArgumentException("Invalid section.");
		}
		if (section.length() != SECTION_LENGTH) {
			throw new IllegalArgumentException("Invalid section.");
		}
		char c;
		for (int i = 0; i < section.length(); i++) {
			c = section.charAt(i);
			if (!Character.isDigit(c)) {
				throw new IllegalArgumentException("Invalid section.");
			}
		}
		this.section = section;
	}

	/**
	 * Returns how many credits the Course is worth.
	 * 
	 * @return the credits
	 */
	public int getCredits() {
		return credits;
	}

	/**
	 * Sets how many credit hours the Course is worth. Credit hour values are
	 * invalid if they are less than one or more than five. if the values are
	 * invalid, an IllegalArgumentException is thrown.
	 * 
	 * @param credits to set
	 * @throws IllegalArgumentException if credits is invalid
	 */
	public void setCredits(int credits) {
		if (credits < MIN_CREDITS || credits > MAX_CREDITS) {
			throw new IllegalArgumentException("Invalid credits.");
		}
		this.credits = credits;
	}

	/**
	 * Gets the ID for the Course's instructor.
	 * 
	 * @return the ID
	 */
	public String getInstructorId() {
		return instructorId;
	}

	/**
	 * Sets the ID for the Course's instructor. An ID is in invalid if it is empty
	 * or null. An invalid ID causes an IllegalArgumentException
	 * 
	 * @param instructorId the instructor's ID
	 * @throws IllegalArgumentException if the ID is invalid
	 */
	public void setInstructorId(String instructorId) {
		if (instructorId == null) {
			throw new IllegalArgumentException("Invalid instructor id.");
		}
		if (instructorId.length() == 0) {
			throw new IllegalArgumentException("Invalid instructor id.");
		}
		this.instructorId = instructorId;
	}
	
	/**
	 * Checks that meetingDays is valid for Course objects and sends variables
	 * to Activity.setMeetingDaysAndTime() for further error checking and assignment.
	 * meeting days are invalid if it contains anything other than M,T,W,H,F and there are no
	 * duplicate letters or if meetingDays is longer than one and has the letter A
	 * @param meetingDays the days the event meets
	 * @param startTime when the event starts
	 * @param endTime when the event ends
	 * @throws IllegalArgumentException if meetingDays is invalid
	 */
	@Override
	public void setMeetingDaysAndTime(String meetingDays, int startTime, int endTime) {
		if (meetingDays == null) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		if (meetingDays.length() == 0) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		int mon = 0;
		int tues = 0;
		int wed = 0;
		int thurs = 0;
		int fri = 0;
		char c;
		for (int i = 0; i < meetingDays.length(); i++) {
			c = meetingDays.charAt(i);
			if (c == 'A') {
				if (meetingDays.length() != 1) {
					throw new IllegalArgumentException("Invalid meeting days and times.");
				}
			} else if (c == 'M') {
				if (mon != 0) {
					throw new IllegalArgumentException("Invalid meeting days and times.");
				}
				mon++;
			} else if (c == 'T') {
				if (tues != 0) {
					throw new IllegalArgumentException("Invalid meeting days and times.");
				}
				tues++;
			} else if (c == 'W') {
				if (wed != 0) {
					throw new IllegalArgumentException("Invalid meeting days and times.");
				}
				wed++;
			} else if (c == 'H') {
				if (thurs != 0) {
					throw new IllegalArgumentException("Invalid meeting days and times.");
				}
				thurs++;
			} else if (c == 'F') {
				if (fri != 0) {
					throw new IllegalArgumentException("Invalid meeting days and times.");
				}
				fri++;
			} else {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}
		}
		super.setMeetingDaysAndTime(meetingDays, startTime, endTime);
	}
	/**
	 * creates and returns an array with the basic information of a course
	 * @return an array for a course
	 */
	public String[] getShortDisplayArray() {
		String[] shortDisplay = {name, section, getTitle(), getMeetingString()};
		return shortDisplay;
	}
	
	/**
	 * creates and returns an array with all the information for a course
	 * @return the full information
	 */
	public String[] getLongDisplayArray() {
		String[] longDisplay = {name, section, getTitle(), "" + credits, instructorId, getMeetingString(), ""};
		return longDisplay;
	}
	/**
	 * Generates a hashCode for Course using all fields.
	 * 
	 * @return hashCode for Course
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(credits, instructorId, name, section);
		return result;
	}

	/**
	 * Compares a given object to this object for equality on all fields.
	 * 
	 * @param obj the Object to compare
	 * @return true if the objects are the same on all fields.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		return credits == other.credits && Objects.equals(instructorId, other.instructorId)
				&& Objects.equals(name, other.name) && Objects.equals(section, other.section);
	}

	/**
	 * Returns a comma separated value String of all Course fields.
	 * 
	 * @return String representation of Course
	 */
	@Override
	public String toString() {
		if ("A".equals(getMeetingDays())) {
			return name + "," + getTitle() + "," + section + "," + credits + "," + instructorId + "," + getMeetingDays();
		}
		return name + "," + getTitle() + "," + section + "," + credits + "," + instructorId + "," + getMeetingDays() + ","
				+ getStartTime() + "," + getEndTime();
	}
	
	/**
	 * checks if a course and another activity are the same
	 * and have the same title
	 * @return if the 2 are the same
	 */
	@Override
	public boolean isDuplicate(Activity activity) {
		if(getClass() != activity.getClass()) {
			return false;
		}
		Course other = (Course) activity;
		return this.getName().equals(other.getName());

	}

}
