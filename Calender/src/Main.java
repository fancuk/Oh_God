import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int year, date, month;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("������ �Է��ϼ��� : ");
            year = sc.nextInt();
        } 
        while (year < 1900);
        do {
            System.out.println("���� �Է��ϼ��� : ");
            month = sc.nextInt();
        } 
        while (month < 1 || month > 12);
        do {
            System.out.println("���� �Է��ϼ��� : ");
            date = sc.nextInt();
        } 
        while (date < 1 || date > 31);
        
        Day.PrintDay(year, month, date);
	}

}
