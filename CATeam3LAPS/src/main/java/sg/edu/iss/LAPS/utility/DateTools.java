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
	  public static float countWeekdaysPublicHoliday(Calendar start, Calendar end) {
		  float WeekdaysPublicHoliday = 0;  		  
		  // situation1: the start day and the end day are both in weekdays;
		  // situation2: the start day is in weekdays;
		  // situation3: the end day is in weekdays;
		  
		  if((start.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && start.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) 
				  && (end.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && end.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY)  ) {
			  WeekdaysPublicHoliday = end.get(Calendar.DATE) - start.get(Calendar.DATE)+1;
		  }
		  else if((start.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && start.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) 
				  && (end.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || end.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)) {
			  while(start.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) {
				  start.add(Calendar.DATE, 1);
				  WeekdaysPublicHoliday++;
			  }
		  }
		  else if((start.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || start.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) 
				  && (end.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY || end.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY)) {
			  while(end.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
				  end.add(Calendar.DATE, -1);
				  WeekdaysPublicHoliday++;
			  }
		  } 
		  return WeekdaysPublicHoliday;
	  }

}
