
public class Address {
	public String getAddress(String str1, String str2) {
		String addnum = "";
		String addcode="";
		if(str1.equals("����")||str1.equals("��õ")||str1.equals("��⵵")) {
			addnum = "11B10101";
		}
		else if(str1.equals("�λ�")||str1.equals("���")||str1.equals("��󳲵�")) {
			addnum = "11H20201";
		}
		else if(str1.equals("�뱸")||str1.equals("���ϵ�")) {
			addnum = "11H10701";
		}
		else if(str1.equals("����")||str1.equals("���󳲵�")) {
			addnum = "11F20501";
		}
		else if(str1.equals("����ϵ�")) {
			addnum = "11F10201";
		}
		else if(str1.equals("����")||str1.equals("����")||str1.equals("��û����")) {
			addnum = "11C20401";
		}
		else if(str1.equals("��û�ϵ�")) {
			addnum = "11C10301";
		}
		else if(str1.equals("������")) {
			addnum = "11D10101";
		}
		else if(str1.equals("���ֵ�")) {
			addnum = "11G00201";
		}
		return addnum;
	}

}
