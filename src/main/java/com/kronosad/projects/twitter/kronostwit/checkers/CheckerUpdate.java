
package com.kronosad.projects.twitter.kronostwit.checkers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
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
public class CheckerUpdate {
    
    public String releaseType = "Alpha";
    public double version = 2.5;
    public double serverVersion;
    public URL buildURL;
    private URL apiURL;

    public CheckerUpdate() {
        try {
            this.apiURL = new URL("http://api.kronosad.com/common/projects.xml");
        } catch (MalformedURLException ex) {
            Logger.getLogger(CheckerUpdate.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void check() throws ParserConfigurationException, IOException, SAXException{
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document apiInfo = builder.parse(apiURL.openStream());
        
        
        apiInfo.normalize();
        
        System.out.println("----- Start Update Info ------");
        
        NodeList nodeList = apiInfo.getElementsByTagName("project");
        
        for(int i = 0; i < nodeList.getLength(); i++){
            Node node = nodeList.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE){
                Element element = (Element) node;
                if(element.getAttribute("name").equalsIgnoreCase("KronosTwit")){
                    System.out.println("Project Name: " + element.getAttribute("name"));
                    System.out.println("Client Version: " + version);
                    System.out.println("Server Version: " + element.getAttribute("version"));
                    System.out.println("Client Release Type: " + releaseType);
                    System.out.println("Server Release Type: " + element.getAttribute("release"));
                    System.out.println("Directory on Server: " + element.getAttribute("dir"));
                    serverVersion = Double.parseDouble(element.getAttribute("version"));
                    buildURL = new URL(element.getAttribute("buildURL"));
                    
                }
                
            }
        }
        
        System.out.println("----- End Update Info ------");
        
        
    }
    
}
