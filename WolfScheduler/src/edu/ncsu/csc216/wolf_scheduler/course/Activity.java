package edu.ncsu.csc216.wolf_scheduler.course;

import java.util.Objects;

/**
 * Contains all the information to create an Activity and
 * dictate the composition and function of various types of activities.
 * @author Jaden Abrams
 *
 */
public abstract class Activity implements Conflict {

	/** Upper limit for the hour part of how long a Course is. */
	private static final int UPPER_HOUR = 24;
	/** Upper part for the minute of how long a Course is. */
	private static final int UPPER_MINUTE = 60;
	/** Course's title. */
	private String title;
	/** Course's meeting days */
	private String meetingDays;
	/** Course's starting time */
	private int startTime;
	/** Course's ending time */
	private int endTime;
	
	/**
	 * Constructor for an Activity, guiding the compostion of Course and Event
	 * @param title the name of the activity
	 * @param meetingDays on what days the activities happen
	 * @param startTime when the activity starts
	 * @param endTime then the activity ends
	 */
    public Activity(String title, String meetingDays, int startTime, int endTime) {
        super();
        setTitle(title);
        setMeetingDaysAndTime(meetingDays, startTime, endTime);
    }

	/**
	 * Returns the title.
	 * 
	 * @return the name
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title. A title is invalid if it is null or is empty
	 * 
	 * @param title the title to set
	 * @throws IllegalArgumentException if the title is invalid
	 */
	public void setTitle(String title) {
		if (title == null) {
			throw new IllegalArgumentException("Invalid title.");
		}
		if (title.length() == 0) {
			throw new IllegalArgumentException("Invalid title.");
		}
		this.title = title;
	}

	/**
	 * Returns the days the Activity meets.
	 * 
	 * @return the day
	 */
	public String getMeetingDays() {
		return meetingDays;
	}

	/**
	 * Sets when the activity meets. meetingDays is invalid
	 * if it is null or has a length or zero. startTime and endTime are invalid
	 * if they aren't in military time or if the the start time is after the end time.
	 * @param meetingDays the days the activity meets
	 * @param startTime   when the activity starts in military time
	 * @param endTime     when the activity ends in military time
	 * @throws IllegalArgumentException if either meetingDays, startTime, or endTime
	 *                                  are invalid.
	 */
	public void setMeetingDaysAndTime(String meetingDays, int startTime, int endTime) {
		if (meetingDays == null) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		if (meetingDays.length() == 0) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		if (startTime / 100 >= UPPER_HOUR || startTime / 100 < 0) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		if (startTime % 100 >= UPPER_MINUTE || startTime % 100 < 0) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		if (endTime / 100 >= UPPER_HOUR || endTime / 100 < 0) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		if (endTime % 100 >= UPPER_MINUTE || endTime % 100 < 0) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		if (startTime > endTime) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		if ("A".equals(meetingDays) && (startTime != 0 || endTime != 0)) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		this.meetingDays = meetingDays;
		this.startTime = startTime;
		this.endTime = endTime;
	
	}

	/**
	 * Returns when the Activity starts.
	 * 
	 * @return the start time
	 */
	public int getStartTime() {
		return startTime;
	}

	/**
	 * Returns when the Activity ends.
	 * 
	 * @return the end time
	 */
	public int getEndTime() {
		return endTime;
	}
	
	/**
	 * Create an array to use to populate the rows of the catalog and schedule
	 * @return information to display in catalog
	 */
	public abstract String[] getShortDisplayArray();
	/**
	 * Create an array to display the final schedule.
	 * @return the full information for the activities
	 */
	public abstract String[] getLongDisplayArray();

	/**
	 * Generates a string with the Activity's meeting days as well as its meeting times
	 * in standard 12-hour format
	 * 
	 * @return The meeting date and time in String form
	 */
	public String getMeetingString() {
		String result = "";
		if ("A".equals(meetingDays)) {
			return "Arranged";
		}
		result = meetingDays + " ";
		int startHour = startTime / 100;
		int startMinute = startTime % 100;
		int endHour = endTime / 100;
		int endMinute = endTime % 100;
	
		if (startHour < 13) {
			if (startHour == 0) {
				result = result + "12";
			} else {
				result = result + startHour;
			}
			result = result + ":" + startMinute;
	
			if (startMinute == 0) {
				result = result + "0";
			}
			if (startHour == 12) {
				result = result + "PM-";
			} else {
				result = result + "AM-";
			}
	
		} else {
			startHour = startHour - 12;
			result = result + startHour + ":" + startMinute;
			if(startMinute == 0) {
				result = result + "0";
			}
			result = result + "PM-";
		}
	
		if (endHour < 13) {
			if (endHour == 0) {
				result = result + "12";
			} else {
				result = result + endHour;
			}
			result = result + ":" + endMinute;
			if (endMinute % 10 == 0) {
				result = result + "0";
			}
			if (endHour == 12) {
				result = result + "PM";
			} else {
				result = result + "AM";
			}
	
		} else {
			endHour = endHour - 12;
			result = result + endHour + ":" + endMinute;
			if(endMinute == 0) {
				result = result + "0";
			}
			result = result + "PM";
		}
		return result;
	}
	/**
	 * Checks if 2 activities have conflicting times (ie they happen at the same time)
	 * The method checks through the meeting days to see if they share a common meeting day.
	 * It then goes through each minute of the day to see if both events are happening at that
	 * specific time. If they are at that time, the method throws a ConflictException.
	 * @throws ConflictException if the activities have conflicting activities
	 */
	@Override
	public void checkConflict(Activity possibleConflictingActivity) throws ConflictException {
		String firstMeetingDays = this.getMeetingDays();
		String secondMeetingDays = possibleConflictingActivity.getMeetingDays();
		String[] allDays = {"U", "M", "T", "W", "H", "F", "S"};
		for(int i = 0; i < allDays.length; i++) {
			if(firstMeetingDays.contains(allDays[i])
					&& secondMeetingDays.contains(allDays[i])) {
				for(int min = 0; min < 2400; min++) {
					if((this.getStartTime() <= min && this.getEndTime() >= min)
							&& (possibleConflictingActivity.getStartTime() <= min && possibleConflictingActivity.getEndTime() >= min)) {
						throw new ConflictException();
					}
						
				}
			}
		}
		
	}

	/**
	 * Checks if 2 activities are the same
	 * @param activity the activity to compare
	 * @return whether they are duplicates
	 */
	public abstract boolean isDuplicate(Activity activity);

	@Override
	public int hashCode() {
		return Objects.hash(endTime, meetingDays, startTime, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Activity other = (Activity) obj;
		return endTime == other.endTime && Objects.equals(meetingDays, other.meetingDays)
				&& startTime == other.startTime && Objects.equals(title, other.title);
	}

}