package com.example.ohgod.Network;

import com.example.ohgod.Model.WeatherModel;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class OpenApi {
    public String getUrl(String code) {//getUrl(주소지코드)
        BufferedReader br = null;
        try {
        String urlstr = "http://apis.data.go.kr/1360000/VilageFcstMsgService/getLandFcst"
                    + "?serviceKey=8EYCMs2wCpABWcZjFomx94YfV4ulqmaU%2FaQfzUoekjw2vjWoZLOKheNVZQ0fC48N0EUxcqrX6TdkP5LELTL9xA%3D%3D"
                    + "&pageNo=1&numOfRows=10&dataType=XML&regId="+code;
            URL url = new URL(urlstr);
            HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
            urlconnection.setRequestMethod("GET");
            br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(), "UTF-8"));
            String result = "";
            String line;
            while ((line = br.readLine()) != null) {
                result = result + line + "\n";
            }
            return result;
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    public WeatherModel getTodayWeather(String str) { // getWeather(xml)
        //파싱과정
        WeatherModel weatherModel = new WeatherModel();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder;
        Document doc = null;
        try {
            InputSource is = new InputSource(new StringReader(str));
            builder = factory.newDocumentBuilder();
            doc = builder.parse(is);
            XPathFactory xpathFactory = XPathFactory.newInstance();
            XPath xpath = xpathFactory.newXPath();

            //item에 들어있는 정보들만
            XPathExpression expr = xpath.compile("//item");
            NodeList nodeList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

            // 발표시간에 따른 날씨 정보
            NodeList announceTime = nodeList.item(0).getChildNodes();
            Node timeNode = announceTime.item(0);
            String time = timeNode.getTextContent().substring(8);
            int num = Integer.parseInt(time);
            boolean flag = true;
            if (num < 1100 && num >= 500) { // 오전, 오후
                flag = false;
            }
            String percent = "";
            String weather = "";
            for (int i = 0; i < 2; i++) {
                NodeList child = nodeList.item(i).getChildNodes();
                for (int j = 0; j < child.getLength(); j++) {
                    Node node = child.item(j);
                    if (node.getNodeName().equals("rnSt")) { //강수확률
                        percent = node.getTextContent();
                    }
                    else if (node.getNodeName().equals("rnYn")) { //비의 종류
                        weather = node.getTextContent();
                    }
                }
                if(flag == true) {
                    i+=1;
                }
                else if(flag == false && weather != "no") {
                    i+=1;
                }
            }
            int rain = Integer.parseInt(percent);
            if(rain<30) { //비가 오지 않는 경우 or 강수확률이 30퍼 미만인 경우
                weatherModel.setWeather("0","0");
            }
            else { // 비가 오는 경우 종류와 확률을 리턴
                if(weather.equals("1")) {
                    weatherModel.setWeather(percent,"rain");
                }
                else if(weather.equals("2")) {
                    weatherModel.setWeather(percent,"snowrain");
                }
                else if(weather.equals("3")) {
                    weatherModel.setWeather(percent,"snow");
                }
                else if(weather.equals("4")) {
                    weatherModel.setWeather(percent,"rain");
                }
            }
            return weatherModel;
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
}