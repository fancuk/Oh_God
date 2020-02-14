package com.moonbanggoo.ohgod.Network;

import com.moonbanggoo.ohgod.Model.TempModel;

import java.io.StringReader;
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


public class Temperature {

    public TempModel getTomorrowTemp(String str) { //str -> openapi gettodayweather과 동일한 값
        TempModel tempModel = new TempModel();
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

            // item에 들어있는 정보들만
            XPathExpression expr = xpath.compile("//item");
            NodeList nodeList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            String tmax = "";
            String tmin = "";

            for (int i = 1; i <= 2; i++) {
                NodeList child = nodeList.item(i).getChildNodes();
                for (int j = 0; j < child.getLength(); j++) {
                    Node node = child.item(j);
                    if (node.getNodeName().equals("ta")) {
                        if (i == 1)
                            tmin = node.getTextContent();
                        else if (i == 2)
                            tmax = node.getTextContent();
                    }
                }
            }
            tempModel.setTemp(tmax, tmin);
            return tempModel;

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
}
