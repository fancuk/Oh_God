
public class Address {
	public String getAddressCode(String str1, String str2) {  
		String addcode="";
		if(str1.equals("서울")){
			addcode = "11B10101";
		}
		else if(str1.equals("인천")) {
			addcode = "11B20201";
		}
		else if(str1.equals("경기도")) {
			addcode = "11B20601";
		}
		else if(str1.equals("부산")){ 
			addcode = "11H20201";
		}
		else if(str1.equals("울산")) {
			addcode = "11H20101";
		}
		else if(str1.equals("경상남도")) {
			addcode = "11H20301";
		}
		else if(str1.equals("대구")){
			addcode = "11H10701";
		}
		else if(str1.equals("경상북도")) {
			addcode = "11H10501";
		}
		else if(str1.equals("광주")) {
			addcode = "11F20501";
		}
		else if(str1.equals("전라남도")) {
			addcode = "21F20801";
		}
		else if(str1.equals("전라북도")) {
			addcode = "11F10201";
		}
		else if(str1.equals("대전")) {
		}
		else if(str1.equals("세종")) {
			addcode = "11C20404";
		}
		else if(str1.equals("충청남도")) {
			addcode= "11C20201";
		}
		else if(str1.equals("충청북도")) {
			addcode = "11C10301";
		}
		else if(str1.equals("강원도")) {
			addcode = "11D10101";
		}
		else if(str1.equals("제주도")) {
			addcode = "11G00201";
		}
		return addcode;
	}
}
