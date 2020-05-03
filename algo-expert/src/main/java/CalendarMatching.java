/*
 * Copyright 2020. Androsaces. All rights reserved.
 */

/**
 * @author Andrew Kearney
 */
public class CalendarMatching {
    private static final int MINUTES_PER_HOUR = 60;
    private final String[] mInterviewerCalendar;
    private final String[] mInterviewerDailyBound;
    private final String[] mIntervieweeCalendar;
    private final String[] mIntervieweeDailyBound;
    private final int mMeetingDuration;

    public CalendarMatching(String[] interviewerCalendar, String[] interviewerDailyBound,
                            String[] intervieweeCalendar, String[] intervieweeDailyBound,
                            int meetingDuration) {
        mInterviewerCalendar = validateInput(interviewerCalendar, "Interviewer calendar");
        mInterviewerDailyBound = validateInput(interviewerDailyBound, "Interviewer daily bound");
        mIntervieweeCalendar = validateInput(intervieweeCalendar, "Interviewee calendar");
        mIntervieweeDailyBound = validateInput(intervieweeDailyBound, "Interviewee daily bound");
        mMeetingDuration = meetingDuration;
    }

    public String[] calculate() {

        return new String[0];
    }

    private String[] validateInput(String[] array, String name) {
        return isCorrectFormat(isNullOrEmpty(array, name), name);
    }

    private String[] isCorrectFormat(String[] array, String name) {
        if (array.length % 2 != 0) throw new IllegalArgumentException(name + " must be in pairs");
        else return array;
    }

    private String[] isNullOrEmpty(String[] array, String name) {
        if (array == null || isEmpty(array)) throw new IllegalArgumentException(name + " cannot be null or empty");
        else return array;
    }

    private boolean isEmpty(String[] array) {
        return array.length == 0;
    }

    public static void main(String[] args) {
        String[] interviewerCalendar = new String[]{"09:00", "10:30", "12:00", "13:00", "16:00", "18:00"};
        String[] interviewerDailyBound = new String[]{"09:00", "20:00"};
        String[] intervieweeCalendar = new String[]{"09:00", "10:30", "12:00", "13:00", "16:00", "18:00"};
        String[] intervieweeDailyBound = new String[]{"10:00", "18:30"};

        int meetingDuration = 30;

        String[] output = new CalendarMatching(interviewerCalendar, interviewerDailyBound,
                intervieweeCalendar, intervieweeDailyBound,
                meetingDuration).calculate();
    }

    private static class MeetingPeriod {

    }
}
