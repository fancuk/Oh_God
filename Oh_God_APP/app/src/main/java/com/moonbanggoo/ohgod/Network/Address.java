package com.moonbanggoo.ohgod.Network;
public class Address {
    public String getAddressCode(String str1) { //무슨도, 무슨시
        String addcode="";
        if(str1.equals("서울")){
            addcode = "11B10101";
        }
        else if(str1.equals("인천")) {
            addcode = "11B20201";
        }
        else if(str1.equals("경기")) {
            addcode = "11B20601";
        }
        else if(str1.equals("부산")){
            addcode = "11H20201";
        }
        else if(str1.equals("울산")) {
            addcode = "11H20101";
        }
        else if(str1.equals("경남")) {
            addcode = "11H20301";
        }
        else if(str1.equals("대구")){
            addcode = "11H10701";
        }
        else if(str1.equals("경북")) {
            addcode = "11H10501";
        }
        else if(str1.equals("광주")) {
            addcode = "11F20501";
        }
        else if(str1.equals("전남")) {
            addcode = "21F20801";
        }
        else if(str1.equals("전북")) {
            addcode = "11F10201";
        }
        else if(str1.equals("대전")) {
            addcode = "11C20401";
        }
        else if(str1.equals("세종")) {
            addcode = "11C20404";
        }
        else if(str1.equals("충남")) {
            addcode= "11C20201";
        }
        else if(str1.equals("충북")) {
            addcode = "11C10301";
        }
        else if(str1.equals("강원")) {
            addcode = "11D10101";
        }
        else if(str1.equals("제주")) {
            addcode = "11G00201";
        }
        return addcode;
    }
}

