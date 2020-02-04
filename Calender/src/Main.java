import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int year, date, month;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("연도를 입력하세요 : ");
            year = sc.nextInt();
        } 
        while (year < 1900);
        do {
            System.out.println("월을 입력하세요 : ");
            month = sc.nextInt();
        } 
        while (month < 1 || month > 12);
        do {
            System.out.println("일을 입력하세요 : ");
            date = sc.nextInt();
        } 
        while (date < 1 || date > 31);
        
        Day.PrintDay(year, month, date);
	}

}
