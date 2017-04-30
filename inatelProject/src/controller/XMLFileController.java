/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.XMLFile;

/**
 *
 * @author João Elio
 */
public class XMLFileController {
    
    public static XMLFile createXMLFile(String name, String content, String addressServer) {
        XMLFile xmlFile = new XMLFile();
        xmlFile.setName(name);
        xmlFile.setContent(content);
        xmlFile.setAddressServer(addressServer);
        
        return xmlFile;
    }
}
