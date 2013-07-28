
package com.kronosad.projects.twitter.kronostwit.data;

import com.kronosad.projects.twitter.kronostwit.interfaces.IData;
import com.kronosad.projects.twitter.kronostwit.user.KronosUser;
import com.kronosad.projects.twitter.kronostwit.user.UserRegistry;
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
public class DataUsers implements IData {
    
    
    private URL apiURL;
    
    public DataUsers(){
        try {
            apiURL = new URL("http://api.kronosad.com/KronosTwit/data/users.xml");
        } catch (MalformedURLException ex) {
            Logger.getLogger(DataUsers.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void download() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document apiInfo = builder.parse(apiURL.openStream());
        
        apiInfo.normalize();
        
        NodeList nodeList = apiInfo.getElementsByTagName("user");
        
        for(int i = 0; i < nodeList.getLength(); i++){
            Node node = nodeList.item(i);
            
            if(node.getNodeType() == Node.ELEMENT_NODE){
                Element element = (Element) node;
                
                UserRegistry.addUser(new KronosUser(element.getAttribute("name"),
                        Boolean.parseBoolean(element.getAttribute("betaUser")), element.getAttribute("tag"),
                        Boolean.parseBoolean(element.getAttribute("banned"))));
                
            }
        }
        
        
    
    }
    
}
