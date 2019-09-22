package secure.coding.chapter05.obj.obj02;
//package secure.obj.obj02;
//
//import java.util.Calendar;
//import java.util.Date;
//
///**
// * @category Non-compliant code  
// *
// */
//public class CalendarSubclass extends Calendar {
//	private static final long serialVersionUID = 5056874815186128297L;
//
//	@Override
//	public boolean after(Object when) {
//		// correctly calls Calendar.compareTo()
//		if (when instanceof Calendar && super.compareTo((Calendar) when) == 0) {
//			return true;
//		}
//		return super.after(when);
//	}
//
//	@Override
//	public int compareTo(Calendar anotherCalendar) {
//		return compareDays(this.getFirstDayOfWeek(), anotherCalendar.getFirstDayOfWeek());
//	}
//
//	private int compareDays(int currentFirstDayOfWeek, int anotherFirstDayOfWeek) {
//		return (currentFirstDayOfWeek > anotherFirstDayOfWeek) ? 1
//				: (currentFirstDayOfWeek == anotherFirstDayOfWeek) ? 0 : -1;
//	}
//
//	public static void main(String[] args) {
//		CalendarSubclass cs1 = new CalendarSubclass();
//
//		cs1.setTime(new Date());
//		// Date of last Sunday (before now)
//		cs1.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
//		// Wed Dec 31 19:00:00 EST 1969
//		CalendarSubclass cs2 = new CalendarSubclass();
//		// expected to print true
//		System.out.println(cs1.after(cs2));
//	}
//	
//	// Implementation of other Calendar abstract methods
//	@Override
//	public void add(int field, int amount) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	protected void computeFields() {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	protected void computeTime() {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public int getGreatestMinimum(int field) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public int getLeastMaximum(int field) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public int getMaximum(int field) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public int getMinimum(int field) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public void roll(int field, boolean up) {
//		// TODO Auto-generated method stub
//		
//	}
//}