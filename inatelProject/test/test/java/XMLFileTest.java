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
import java.util.List;
import model.Server;
import model.XMLFile;
import org.junit.Assert;
import org.junit.Test;
import test.CommonsTest;
import static test.CommonsTest.SERVER_ADDRESS;
import static test.CommonsTest.SERVER_PASSWORD;
import static test.CommonsTest.SERVER_USER;
import static test.CommonsTest.XMLFILE_CONTENT;
import static test.CommonsTest.XMLFILE_NAME;

/**
 *
 * @author João Elio
 */
public class XMLFileTest {
    
    @Test
    public void testCreateXMLFile() {
        
        Server serverSaved = CommonsTest.saveServerTest(SERVER_USER, SERVER_PASSWORD, SERVER_ADDRESS);
        
        List<XMLFile> listXMLFileSaved = CommonsTest.saveXMLFileTest(XMLFILE_NAME, XMLFILE_CONTENT, serverSaved.getAddress());
        
        for(XMLFile itemXMLFileSaved : listXMLFileSaved) {
            Assert.assertEquals(XMLFILE_NAME, itemXMLFileSaved.getName());
            Assert.assertEquals(XMLFILE_CONTENT, itemXMLFileSaved.getContent());
            Assert.assertEquals(serverSaved.getAddress(), itemXMLFileSaved.getAddressServer());

            XMLFileDAO xmlFileDAO = new XMLFileDAO();
            xmlFileDAO.removeXMLFile(itemXMLFileSaved);

            Assert.assertEquals(xmlFileDAO.findXMLFileByName(itemXMLFileSaved.getName()).isEmpty(), true);
        }
        
        ServerDAO serverDAO = new ServerDAO();
        serverDAO.removeServer(serverSaved);
    }
}
