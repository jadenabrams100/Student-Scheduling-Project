/**
 * 
 */
package edu.ncsu.csc216.wolf_scheduler.course;

/**
 * Contains the code to create and operate an Event
 * @author Jaden Abrams
 *
 */
public class Event extends Activity {
	/** The details of the event. */
	private String eventDetails;


	/**
	 * Creates an Event object
	 * @param title the name of the event
	 * @param meetingDays when the event meets
	 * @param startTime when the event starts
	 * @param endTime when the event ends
	 * @param eventDetails other miscellaneous details about the event
	 */
	public Event(String title, String meetingDays, int startTime, int endTime, String eventDetails) {
		super(title, meetingDays, startTime, endTime);
		setEventDetails(eventDetails);
	}

	/**
	 * returns the miscellaneous information for an event
	 * @return the eventDetails
	 */
	public String getEventDetails() {
		return eventDetails;
	}

	/**
	 * Sets the miscellaneous details of an Event. throws
	 * an IllegalArgumentException if eventDetails is null.
	 * @param eventDetails the eventDetails to set
	 * @throws IllegalArgumentException if eventDetails is null
	 */
	public void setEventDetails(String eventDetails) {
		if(eventDetails == null) {
			throw new IllegalArgumentException("Invalid event details.");
		}
		this.eventDetails = eventDetails;
	}

	/**
	 * Creates an array with the basic details about an event
	 * @return the basic details of an event
	 */
	@Override
	public String[] getShortDisplayArray() {
		String[] shortDisplay = {"", "", getTitle(), getMeetingString()};
		return shortDisplay;
	}

	/**
	 * Creates an array with all the information about an event
	 * @return all the details of an event
	 */
	@Override
	public String[] getLongDisplayArray() {
		String[] longDisplay = {"", "", getTitle(), "", "", getMeetingString(), getEventDetails()};
		return longDisplay;
	}
	
	/**
	 * Checks that meetingDays is valid for Event objects and sends variables
	 * to Activity.setMeetingDaysAndTime() for further error checking and assignment.
	 * meeting days are invalid if it contains anything other than M,T,W,H,F,S,U and there are no
	 * duplicate letters
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
		int sat = 0;
		int sun = 0;
		char c;
		for (int i = 0; i < meetingDays.length(); i++) {
			c = meetingDays.charAt(i);
			if (c == 'M') {
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
			}
			else if(c == 'S') {
				if(sat != 0) {
					throw new IllegalArgumentException("Invalid meeting days and times.");
				}
			}
			else if(c == 'U') {
				if(sun != 0) {
					throw new IllegalArgumentException("Invalid meeting days and times.");
				}
			}
			else {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}
		}
		super.setMeetingDaysAndTime(meetingDays, startTime, endTime);
	}

	/**
	 * returns the Event object as a String with commas as delimiters
	 * @return the object as a string
	 */
	@Override
	public String toString() {
		return getTitle() + "," + getMeetingDays() + "," + getStartTime() + "," + getEndTime() + "," + getEventDetails();
	}

	/**
	 * Checks if an Event and another Activity are the same
	 * and have the same title
	 * @return if they are the same
	 */
	@Override
	public boolean isDuplicate(Activity activity) {
		if(getClass() != activity.getClass()) {
			return false;
		}
		Event other = (Event) activity;
		return super.getTitle().equals(other.getTitle());
	}
	
	

}
