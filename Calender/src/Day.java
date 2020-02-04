
public class Day {
	public static String PrintDay(int year, int month, int day) {
        char dayOfWeek = DayofDate(year, month, day);
        String nowYear = Integer.toString(year);
        String nowMonth=Integer.toString(month);
        String nowDay = Integer.toString(day);
        String nowDayOfWeek = String.valueOf(dayOfWeek);
        return nowYear + "�� " + nowMonth + "�� " + nowDay + "���� " + nowDayOfWeek + "�����Դϴ�.";
        
    }
    public static int MonthDays(int year, int month) {
        int dayOfMonth = 0;
        //������ ����
        Boolean isLeapYear = ((year % 4 == 0) && (year % 100 !=0)) || year % 400 == 0;
        //31�� �� ����
        Boolean is31 = (month == 1) || (month == 3) || (month == 5) || (month == 7) || (month == 8) || (month == 10) || (month == 12);
        
        if (isLeapYear && month == 2) {
            dayOfMonth = 29;
        } 
        else if (!isLeapYear && month == 2) {
            dayOfMonth = 28;
        } 
        else if (is31) {
            dayOfMonth = 31;
        } 
        else {
            dayOfMonth = 30;
        }
        return dayOfMonth;
    }
    public static char DayofDate(int year, int month, int day) {
        char dayOfWeek = 0;
        int totalDay = 0;
        for(int i = 1900; i <= year; i++) {
            if (i < year) {
                for(int j = 1; j <= 12; j++) {
                    totalDay += MonthDays(i, j);
                }
            } 
            else {
                for(int j = 1; j < month; j++) {
                    totalDay += MonthDays(i, j);
                }
            }
        }
        totalDay += day;
        switch (totalDay % 7) {
        case 0:
            dayOfWeek = '��';
            break;
        case 1:
            dayOfWeek = '��';
            break;
        case 2:
            dayOfWeek = 'ȭ';
            break;
        case 3:
            dayOfWeek = '��';
            break;    
        case 4:
            dayOfWeek = '��';
            break;
        case 5:
            dayOfWeek = '��';
            break;
        case 6:
            dayOfWeek = '��';
            break;    
        }
        return dayOfWeek;
    }
}
