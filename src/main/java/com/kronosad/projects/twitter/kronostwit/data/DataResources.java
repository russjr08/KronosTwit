/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kronosad.projects.twitter.kronostwit.data;

import com.kronosad.projects.twitter.kronostwit.gui.helpers.ResourceDownloader;
import com.kronosad.projects.twitter.kronostwit.interfaces.IData;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Russell
 */
public class DataResources implements IData{
    private URL apiURL;
    
    public DataResources(){
        System.out.println("Starting Download of Resources XML");
        try {
            apiURL = new URL("http://api.kronosad.com/KronosTwit/resources/resources.xml");
        } catch (MalformedURLException ex) {
            Logger.getLogger(DataResources.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void download() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document apiInfo = builder.parse(apiURL.openStream());
        
        apiInfo.normalize();
        
        NodeList nList = apiInfo.getElementsByTagName("resource");
        System.out.println("--- Start Resource Info ---");
        for(int i = 0; i < nList.getLength(); i++){
            Node node = nList.item(i);
            System.out.println("-----");
            if(node.getNodeType() == Node.ELEMENT_NODE){
                Element element = (Element)node;
                System.out.println("Resource Name: " + element.getAttribute("name"));
                System.out.println("Resource MD5: " + element.getAttribute("md5"));
                System.out.println("Resource URL: " + element.getAttribute("url"));
                System.out.println("----");
                
                if(element.getAttribute("ignoreUpdate").equalsIgnoreCase("true")){
                    ResourceDownloader.resources.add(new Resource(element.getAttribute("name"), element.getAttribute("md5"), element.getAttribute("url"), true));

                }else{
                    ResourceDownloader.resources.add(new Resource(element.getAttribute("name"), element.getAttribute("md5"), element.getAttribute("url")));

                }
            }
        }
    }
    
}
