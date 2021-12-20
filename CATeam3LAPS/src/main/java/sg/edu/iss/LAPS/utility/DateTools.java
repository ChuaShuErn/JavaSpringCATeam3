package sg.edu.iss.LAPS.utility;

import java.util.Calendar;
import java.util.Date;

public class DateTools {
	
	
	// convert to calendar format
	public static Calendar dateToCalendar(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}
	
	// check if the start day is before the end day
	public static boolean startDateBeforeEndDate(Calendar start, Calendar end) {
		  if(start.getTimeInMillis() <= end.getTimeInMillis()) {   // to use getTimeInMillis for accuracy 
			  return true;
		  }
		  else return false;
	 }
	
	//Remove weekends if leave days is <=14
	  public static float removeWeekends(Calendar start, Calendar end) {
		  float daysWithoutWeekends = 0;
		  do {
			  if (start.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY &&
					  start.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {;
					  daysWithoutWeekends++; }
			  start.add(Calendar.DAY_OF_MONTH, 1);
		  } 
		  while (start.getTimeInMillis() <= end.getTimeInMillis());
		  return daysWithoutWeekends;

	  }
	  
	//Count the weekdays that is a public Holiday
	  // TODO: Add for loop like removeWeekends
	  public static float countWeekdaysPublicHoliday(Calendar start, Calendar end) {
		  float WeekdaysPublicHoliday = 0;

		  if (start.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY 
				  && start.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
					WeekdaysPublicHoliday++;
		  }
		  if(!(end.equals(start))) {
				if (end.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY
					&& end.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
						WeekdaysPublicHoliday++;
					}
		   }
		  return WeekdaysPublicHoliday;  
	  }

}
