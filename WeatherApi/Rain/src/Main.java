import java.util.*;

public class Main {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		OpenApi api = new OpenApi();
		Address add = new Address();
		
		String xml = "";
		String weather = "";
		String code = "";
		Scanner sc = new Scanner(System.in);
		System.out.println("�� �Է�: ");
		String si = sc.nextLine();
		System.out.println("�ּ� �Է�: ");
		String address = sc.nextLine();
		
		//�ּҿ� ���� url �޾ƿ���
		code = add.getAddress(si,address);
		xml = api.getUrl(code);
		
		//�������� �޾ƿ���
		if(xml!="0") {
			weather = api.getTodayWeather(xml);
		}
		else {
			System.out.println("����");
		}
		System.out.println(weather);
	}
}
