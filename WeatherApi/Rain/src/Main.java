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
		System.out.println("시 입력: ");
		String si = sc.nextLine();
		System.out.println("주소 입력: ");
		String address = sc.nextLine();
		
		//주소에 따른 url 받아오기
		code = add.getAddress(si,address);
		xml = api.getUrl(code);
		
		//날씨정보 받아오기
		if(xml!="0") {
			weather = api.getTodayWeather(xml);
		}
		else {
			System.out.println("오류");
		}
		System.out.println(weather);
	}
}
