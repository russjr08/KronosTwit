/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kronosad.projects.twitter.kronostwit.interfaces;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author Russell
 */
public interface IData {
    
    /**
     * Download required data with this method.
     */
    public void download() throws ParserConfigurationException, IOException, SAXException;
    
}
