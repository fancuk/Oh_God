
public class Address {
	public String getAddress(String str1, String str2) {
		String addnum = "";
		String addcode="";
		if(str1.equals("서울")||str1.equals("인천")||str1.equals("경기도")) {
			addnum = "11B10101";
		}
		else if(str1.equals("부산")||str1.equals("울산")||str1.equals("경상남도")) {
			addnum = "11H20201";
		}
		else if(str1.equals("대구")||str1.equals("경상북도")) {
			addnum = "11H10701";
		}
		else if(str1.equals("광주")||str1.equals("전라남도")) {
			addnum = "11F20501";
		}
		else if(str1.equals("전라북도")) {
			addnum = "11F10201";
		}
		else if(str1.equals("대전")||str1.equals("세종")||str1.equals("충청남도")) {
			addnum = "11C20401";
		}
		else if(str1.equals("충청북도")) {
			addnum = "11C10301";
		}
		else if(str1.equals("강원도")) {
			addnum = "11D10101";
		}
		else if(str1.equals("제주도")) {
			addnum = "11G00201";
		}
		return addnum;
	}

}
