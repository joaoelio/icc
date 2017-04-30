/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.java;

import controller.ServerController;
import controller.XMLFileController;
import dao.ServerDAO;
import dao.XMLFileDAO;
import model.Server;
import model.XMLFile;
import org.junit.Test;

/**
 *
 * @author João Elio
 */
public class XMLFileTest {
    
    @Test
    public void testCreateXMLFile() {
        
        Server server = createServer();
        
        XMLFile xmlFile = new XMLFile();
        xmlFile = XMLFileController.createXMLFile("xmlFile01", "contentXML", server.getAddress());
        
        XMLFileDAO xmlFileDAO = new XMLFileDAO();
        xmlFileDAO.saveXMLFile(xmlFile);
    }
    
    private Server createServer() {
        Server server = ServerController.createServer("inatel", "inatel", "192.168.0.21");
        
        ServerDAO serverDAO = new ServerDAO();
        serverDAO.saveServer(server);
        
        Server serverSaved = serverDAO.findServerByAddress("192.168.0.21");
        
        return serverSaved;
    }
}
