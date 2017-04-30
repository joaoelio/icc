/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import controller.ServerController;
import controller.XMLFileController;
import dao.ServerDAO;
import dao.XMLFileDAO;
import java.util.List;
import model.Server;
import model.XMLFile;

/**
 *
 * @author João Elio
 */
public class CommonsTest {
    
    public static final String SERVER1_USER = "inatel";
    public static final String SERVER1_PASSWORD = "inatel";
    public static final String SERVER1_ADDRESS = "192.168.0.21";
    public static final String SERVER2_USER = "inatel";
    public static final String SERVER2_PASSWORD = "inatel";
    public static final String SERVER2_ADDRESS = "192.168.0.21";
    
    public static final String XMLFILE_NAME = "xmlFile01";
    public static final String XMLFILE_CONTENT = "test XML";
    
    public static final String FTP_SERVER_ADDRESS = "";
    
    public static Server saveServerTest(String user, String password, String address) {
        Server server = ServerController.createServer(user, password, address);
        
        ServerDAO serverDAO = new ServerDAO();
        serverDAO.saveServer(server);
        
        Server serverSaved = serverDAO.findServerByAddress(SERVER1_ADDRESS);
        
        return serverSaved;
    }
    
    public static XMLFile saveXMLFileTest(String nameXMLFile, String contentXMLFile, String serverAddress) {
        
        XMLFile xmlFile = new XMLFile();
        xmlFile = XMLFileController.createXMLFile(nameXMLFile, contentXMLFile, serverAddress);
        
        XMLFileDAO xmlFileDAO = new XMLFileDAO();
        xmlFileDAO.saveXMLFile(xmlFile);
        
        List<XMLFile> listXMLFileSaved = xmlFileDAO.findXMLFileByName(xmlFile.getName());
        XMLFile xmlFileSaved = null;
        
        for(XMLFile itemXMLFile : listXMLFileSaved) {
            if(itemXMLFile.getName().equals(xmlFile.getName()) && 
                        itemXMLFile.getContent().equals(xmlFile.getContent()) && 
                        itemXMLFile.getAddressServer().equals(xmlFile.getAddressServer())) {
                xmlFileSaved = itemXMLFile;
            }
        }
        
        return xmlFileSaved;
    }
    
}
