
package com.kronosad.projects.twitter.kronostwit.checkers;

import com.kronosad.projects.twitter.kronostwit.data.Version;
import com.kronosad.projects.twitter.kronostwit.enums.ReleaseType;
import com.kronosad.projects.twitter.kronostwit.exceptions.VersionSystemException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Russell
 */
public class UpdateInformation {

    private ReleaseType clientReleaseType = ReleaseType.RELEASE_CANDIDATE;
    public double versionNumber = 3.0;
    public double serverVersion;
    public URL buildURL;
    private URL projectApiURL;
    private URL versionsApiURL;

    private Version version;

    private static UpdateInformation instance;

    public UpdateInformation() {
        try {
            this.projectApiURL = new URL("http://api.kronosad.com/common/projects.xml");
            this.versionsApiURL = new URL("http://api.kronosad.com/KronosTwit/versioning/versions.xml");
        } catch (MalformedURLException ex) {
            Logger.getLogger(UpdateInformation.class.getName()).log(Level.SEVERE, null, ex);
        }
        instance = this;
    }

    /**
     * Gets an instance of the Update Information.
     * @return An instance of {@link com.kronosad.projects.twitter.kronostwit.checkers.UpdateInformation}
     */
    public static UpdateInformation getInstance(){
        return instance;
    }
    
    public void check() throws ParserConfigurationException, IOException, SAXException{
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document apiInfo = builder.parse(projectApiURL.openStream());
        
        
        apiInfo.normalize();
        
        System.out.println("----- Start Update Info ------");
        
        NodeList nodeList = apiInfo.getElementsByTagName("project");
        
        for(int i = 0; i < nodeList.getLength(); i++){
            Node node = nodeList.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE){
                Element element = (Element) node;
                if(element.getAttribute("name").equalsIgnoreCase("KronosTwit")){
                    System.out.println("Project Name: " + element.getAttribute("name"));
                    System.out.println("Client Version: " + versionNumber);
                    System.out.println("Server Version: " + element.getAttribute("version"));
                    System.out.println("Client Release Type: " + clientReleaseType);
                    System.out.println("Server Release Type: " + element.getAttribute("release"));
                    System.out.println("Directory on Server: " + element.getAttribute("dir"));
                    serverVersion = Double.parseDouble(element.getAttribute("version"));
//                    buildURL = new URL(element.getAttribute("buildURL"));
                    
                }
                
            }
        }
        
        System.out.println("----- End Update Info ------");

        Document versionInfo = builder.parse(versionsApiURL.openStream());

        versionInfo.normalize();

        System.out.println("----- Start Version List -------");

        NodeList versionsList = versionInfo.getElementsByTagName("version");

        for(int i = 0; i < versionsList.getLength(); i++){
            System.out.println("---------");
            Node node = versionsList.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE){
                Element element = (Element) node;
                System.out.println("Version Number: " + element.getAttribute("number"));
                System.out.println("Download URL :" + element.getAttribute("downloadURL"));
                System.out.println("Needs a Login Reset? " + element.getAttribute("resetLogin"));
                System.out.println("Release Type: " + element.getAttribute("releaseType"));

                if(Double.parseDouble(element.getAttribute("number")) == versionNumber && ReleaseType.valueOf(element.getAttribute("releaseType")) == clientReleaseType){
                    version = new Version(Double.parseDouble(element.getAttribute("number")), element.getAttribute("downloadURL"),
                            element.getAttribute("releaseType"), Boolean.parseBoolean(element.getAttribute("resetLogin")), element.getAttribute("changelog"));
                }
            }
        }
        
        
    }

    /**
     * Gets the current {@link com.kronosad.projects.twitter.kronostwit.data.Version} object associated with the running
     * version of KronosTwit
     * @return An instance of {@link com.kronosad.projects.twitter.kronostwit.data.Version}
     */
    public Version getVersion(){
        if(version != null){
            return version;
        }else{
            throw new VersionSystemException("This Version of KronosTwit does not use the new provisioning system.");
        }
    }
    
}
