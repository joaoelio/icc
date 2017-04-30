/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.java;

import dao.ServerDAO;
import dao.XMLFileDAO;
import model.Server;
import model.XMLFile;
import org.junit.Assert;
import org.junit.Test;
import test.CommonsTest;
import static test.CommonsTest.XMLFILE_CONTENT;
import static test.CommonsTest.XMLFILE_NAME;
import static test.CommonsTest.SERVER1_USER;
import static test.CommonsTest.SERVER1_PASSWORD;
import static test.CommonsTest.SERVER1_ADDRESS;

/**
 *
 * @author João Elio
 */
public class XMLFileTest {
    
    @Test
    public void testCreateXMLFile() {
        
        Server serverSaved = CommonsTest.saveServerTest(SERVER1_USER, SERVER1_PASSWORD, SERVER1_ADDRESS);
        
        XMLFile xmlFileSaved = CommonsTest.saveXMLFileTest(XMLFILE_NAME, XMLFILE_CONTENT, serverSaved.getAddress());
        
        Assert.assertEquals(XMLFILE_NAME, xmlFileSaved.getName());
        Assert.assertEquals(XMLFILE_CONTENT, xmlFileSaved.getContent());
        Assert.assertEquals(serverSaved.getAddress(), xmlFileSaved.getAddressServer());

        XMLFileDAO xmlFileDAO = new XMLFileDAO();
        xmlFileDAO.removeXMLFile(xmlFileSaved);

        Assert.assertEquals(xmlFileDAO.findXMLFileById(xmlFileSaved.getId()), null);

        ServerDAO serverDAO = new ServerDAO();
        serverDAO.removeServer(serverSaved);
    }
}
