/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.java;

import controller.FTPServerController;
import dao.ServerDAO;
import dao.XMLFileDAO;
import dao.connections.ConnectionFTP;
import java.util.List;
import model.Server;
import model.XMLFile;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Assert;
import org.junit.Test;
import test.CommonsTest;
import static test.CommonsTest.SERVER_ADDRESS;
import static test.CommonsTest.SERVER_PASSWORD;
import static test.CommonsTest.SERVER_USER;
import static test.CommonsTest.XMLFILE_CONTENT;
import static test.CommonsTest.XMLFILE_NAME;
import dao.connections.ConnectionFTP;
import java.io.IOException;
import java.util.Map;
import org.apache.commons.net.ftp.FTPReply;
import test.CommonsTest;
import static test.CommonsTest.SERVER_USER;
import static test.CommonsTest.SERVER_PASSWORD;
import static test.CommonsTest.SERVER_ADDRESS;

/**
 *
 * @author João Elio
 */
public class FTPServerTest {
    
    @Test
    public void testConnectFTPServer() throws IOException {
        FTPClient ftpClient = ConnectionFTP.connectFTP(SERVER_USER, SERVER_PASSWORD, SERVER_ADDRESS);
        
        Assert.assertEquals(FTPReply.isPositiveCompletion( ftpClient.getReplyCode() ), true);
        
        ftpClient = ConnectionFTP.diconnectFTP(ftpClient, SERVER_ADDRESS);
        
        Assert.assertEquals(ftpClient.isConnected(), false);
    }
    
    @Test
    public void testExistFileXMLFTPServer() throws IOException {
        FTPClient ftpClient = ConnectionFTP.connectFTP(SERVER_USER, SERVER_PASSWORD, SERVER_ADDRESS);
        Assert.assertEquals(FTPReply.isPositiveCompletion( ftpClient.getReplyCode() ), true);
        
        Map<String, String> mapXMLFiles = FTPServerController.findFilesXMLFTPServer(ftpClient);
        Assert.assertEquals(mapXMLFiles.isEmpty(), false);
        
        ftpClient = ConnectionFTP.diconnectFTP(ftpClient, SERVER_ADDRESS);
        Assert.assertEquals(ftpClient.isConnected(), false);
    }
    
    @Test
    public void testSaveFileXMLFTPServerInDB() throws IOException {
        
        Server serverSaved = CommonsTest.saveServerTest(SERVER_USER, SERVER_PASSWORD, SERVER_ADDRESS);
        
        FTPClient ftpClient = ConnectionFTP.connectFTP(SERVER_USER, SERVER_PASSWORD, SERVER_ADDRESS);
        Assert.assertEquals(FTPReply.isPositiveCompletion( ftpClient.getReplyCode() ), true);
        
        Map<String, String> mapXMLFiles = FTPServerController.findFilesXMLFTPServer(ftpClient);
        Assert.assertEquals(mapXMLFiles.isEmpty(), false);
        
        for(String key : mapXMLFiles.keySet()) {
            XMLFile xmlFile = new XMLFile();
            xmlFile.setName(key);
            xmlFile.setContent(mapXMLFiles.get(key));
            xmlFile.setAddressServer(serverSaved.getAddress());

            List<XMLFile> xmlFileSavedRecent = CommonsTest.saveXMLFileTest(xmlFile.getName(), xmlFile.getContent(), xmlFile.getAddressServer());
            
            XMLFileDAO xmlFileDAO = new XMLFileDAO();
            boolean thisXMLFileExists = false;
            List<XMLFile> listXMLFile = xmlFileDAO.findXMLFileByName(xmlFile.getName());
            
            for(XMLFile itemXMLFile : listXMLFile) {
                if(itemXMLFile.getName().equals(xmlFile.getName()) && 
                        itemXMLFile.getContent().equals(xmlFile.getContent()) && 
                        itemXMLFile.getAddressServer().equals(xmlFile.getAddressServer())) {
                    thisXMLFileExists = true;
                    xmlFileDAO.removeXMLFile(itemXMLFile);
                }
            }
            
            Assert.assertEquals(thisXMLFileExists, true);
        }
        
        ServerDAO serverDAO = new ServerDAO();
        serverDAO.removeServer(serverSaved);
        
        ftpClient = ConnectionFTP.diconnectFTP(ftpClient, SERVER_ADDRESS);
        Assert.assertEquals(ftpClient.isConnected(), false);
    }
    
}
