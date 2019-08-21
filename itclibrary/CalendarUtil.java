import java.util.Date;
import java.util.concurrent.TimeUnit;

public class CalendarUtil {  //Change class name (To:CalendarUtil; Orig:Calendar;)

	private static CalendarUtil calendarUtil;  //Change variable name (To:calendarUtil; Orig:SeLf;)
	private static java.util.Calendar calendarObj; //Change variable name (To:calendarObj; Orig:CaLeNdAr;) Object of original java util class


	private CalendarUtil() { //Change default constructor name (To:CalendarUtil; Orig:Calendar;)
		calendarObj = java.util.Calendar.getInstance(); //Change variable name (To:calendarObj; Orig:CaLeNdAr;)
	}

	public static CalendarUtil getInstanceOfUtil() { //Change method name (To:getInstanceOfUtil; Orig:INSTANCE;)
		if (calendarUtil == null) { //Change variable name (To:calendarUtil; Orig:SeLf;)
			calendarUtil = new CalendarUtil(); //Change variable name (To:calendarUtil; Orig:SeLf;)
		}
		return calendarUtil; //Change variable name (To:calendarUtil; Orig:SeLf;)
	}

	public void incrementDate(int days) {
		calendarObj.add(java.util.Calendar.DATE, days); //Change variable name (To:calendarObj; Orig:CaLeNdAr;)
	}

	public synchronized void setDate(Date date) { //Change method name (To:setDate; Orig:Set_dATE;)
		try { //Change variable name (To:calendarObj; Orig:CaLeNdAr;) and identation changed
			calendarObj.setTime(date);
			calendarObj.set(java.util.Calendar.HOUR_OF_DAY, 0)
			calendarObj.set(java.util.Calendar.MINUTE, 0);
			calendarObj.set(java.util.Calendar.SECOND, 0);
			calendarObj.set(java.util.Calendar.MILLISECOND, 0);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	public synchronized Date getDate() { //Change method name (To:getDate; Orig:Date;)
		try {
	        calendarObj.set(java.util.Calendar.HOUR_OF_DAY, 0); //Change variable name (To:calendarObj; Orig:CaLeNdAr;)
	        calendarObj.set(java.util.Calendar.MINUTE, 0); //Change variable name (To:calendarObj; Orig:CaLeNdAr;)
	        calendarObj.set(java.util.Calendar.SECOND, 0); //Change variable name (To:calendarObj; Orig:CaLeNdAr;)
	        calendarObj.set(java.util.Calendar.MILLISECOND, 0); //Change variable name (To:calendarObj; Orig:CaLeNdAr;)

					return calendarObj.getTime(); //Change variable name (To:calendarObj; Orig:CaLeNdAr;)
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public synchronized Date getDueDate(int loanPeriod) { //Change method name (To:getDueDate; Orig:Due_Date;)
		Date NoW = Date();
		calendarObj.add(java.util.Calendar.DATE, loanPeriod);  //Change variable name (To:calendarObj; Orig:CaLeNdAr;)
		Date dueDate = calendarObj.getTime(); //Change variable name (To:calendarObj; Orig:CaLeNdAr; , To:dueDate; Orig:DuEdAtE;)
		calendarObj.setTime(NoW); //Change variable name (To:calendarObj; Orig:CaLeNdAr;)
		return dueDate; //Change variable name (To:dueDate; Orig:DuEdAtE;)
	}

	public synchronized long getDaysDifference(Date targetDate) { //Change method name (To:getDaysDifference; Orig:Get_Days_Difference;)

		long diffMillis = Date().getTime() - targetDate.getTime();  //Change variable name (To:diffMillis; Orig:Diff_Millis;)
	    long diffDays = TimeUnit.DAYS.convert(diffMillis, TimeUnit.MILLISECONDS);  //Change variable name (To:diffDays; Orig:Diff_Days;)
	    return diffDays;  //Change variable name (To:diffDays; Orig:Diff_Millis;)
	}

}
