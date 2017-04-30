/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.java;

import controller.FTPServerController;
import controller.ServerController;
import dao.ServerDAO;
import dao.XMLFileDAO;
import java.util.List;
import model.Server;
import model.XMLFile;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Assert;
import org.junit.Test;
import dao.connections.ConnectionFTP;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import org.apache.commons.net.ftp.FTPReply;
import test.CommonsTest;
import static test.CommonsTest.SERVER1_USER;
import static test.CommonsTest.SERVER1_PASSWORD;
import static test.CommonsTest.SERVER1_ADDRESS;
import static test.CommonsTest.SERVER2_USER;
import static test.CommonsTest.SERVER2_PASSWORD;
import static test.CommonsTest.SERVER2_ADDRESS;

/**
 *
 * @author João Elio
 */
public class FTPServerTest {
    
    //Testing only connection with the FTP Server
    @Test
    public void testConnectFTPServer() throws IOException {
        FTPClient ftpClient = ConnectionFTP.connectFTP(SERVER1_USER, SERVER1_PASSWORD, SERVER1_ADDRESS);
        
        Assert.assertEquals(FTPReply.isPositiveCompletion( ftpClient.getReplyCode() ), true);
        
        ftpClient = ConnectionFTP.diconnectFTP(ftpClient, SERVER1_ADDRESS);
        
        Assert.assertEquals(ftpClient.isConnected(), false);
    }
    
    //Testing if exists files .xml in FTP Server
    @Test
    public void testExistFileXMLFTPServer() throws IOException {
        FTPClient ftpClient = ConnectionFTP.connectFTP(SERVER1_USER, SERVER1_PASSWORD, SERVER1_ADDRESS);
        Assert.assertEquals(FTPReply.isPositiveCompletion( ftpClient.getReplyCode() ), true);
        
        List<String> listXMLFiles = FTPServerController.findFilesXMLFTPServer(ftpClient);
        Assert.assertEquals(listXMLFiles.isEmpty(), false);
        
        ftpClient = ConnectionFTP.diconnectFTP(ftpClient, SERVER1_ADDRESS);
        Assert.assertEquals(ftpClient.isConnected(), false);
    }
    
    //Test save File .xml of FTP Server in Database
    @Test
    public void testSaveFileXMLFTPServerInDB() throws IOException {
        
        Server serverSaved = CommonsTest.saveServerTest(SERVER1_USER, SERVER1_PASSWORD, SERVER1_ADDRESS);
        
        FTPClient ftpClient = ConnectionFTP.connectFTP(serverSaved.getUser(), serverSaved.getPassword(), serverSaved.getAddress());
        Assert.assertEquals(FTPReply.isPositiveCompletion( ftpClient.getReplyCode() ), true);
        
        List<String> listNameFilesXML = FTPServerController.findFilesXMLFTPServer(ftpClient);
        
        for(String itemNameFileXML : listNameFilesXML) {
            ftpClient = ConnectionFTP.diconnectFTP(ftpClient, serverSaved.getAddress());
            ftpClient = ConnectionFTP.connectFTP(serverSaved.getUser(), serverSaved.getPassword(), serverSaved.getAddress());
            
            String contentFileXML = FTPServerController.getFileFTPServer(ftpClient, itemNameFileXML);

            XMLFile xmlFileSavedRecent = CommonsTest.saveXMLFileTest(itemNameFileXML, contentFileXML, serverSaved.getAddress());

            XMLFileDAO xmlFileDAO = new XMLFileDAO();
            xmlFileDAO.removeXMLFile(xmlFileSavedRecent);

            Assert.assertEquals(xmlFileDAO.findXMLFileById(xmlFileSavedRecent.getId()), null);
        }
        
        ServerDAO serverDAO = new ServerDAO();
        serverDAO.removeServer(serverSaved);
        
        Assert.assertEquals(serverDAO.findServerByAddress(serverSaved.getAddress()), null);
        
        ftpClient = ConnectionFTP.diconnectFTP(ftpClient, SERVER1_ADDRESS);
        Assert.assertEquals(ftpClient.isConnected(), false);
    }
    
    //Test save File .xml of TWO FTP Servers in Database
    @Test
    public void testSaveFileXMLFTPServersInDB() throws IOException {
        
        List<Server> listServer = new ArrayList<>();
        listServer.add(ServerController.createServer(SERVER1_USER, SERVER1_PASSWORD, SERVER1_ADDRESS));
        listServer.add(ServerController.createServer(SERVER2_USER, SERVER2_PASSWORD, SERVER2_ADDRESS));

        for(Server itemServer : listServer) {
            Server serverSaved = CommonsTest.saveServerTest(
                    itemServer.getUser(), itemServer.getPassword(), itemServer.getAddress());
            FTPClient ftpClient = ConnectionFTP.connectFTP(
                    serverSaved.getUser(), serverSaved.getPassword(), serverSaved.getAddress());
            Assert.assertEquals(FTPReply.isPositiveCompletion( ftpClient.getReplyCode() ), true);

            List<String> listNameFilesXML = FTPServerController.findFilesXMLFTPServer(ftpClient);
        
            for(String itemNameFileXML : listNameFilesXML) {
                ftpClient = ConnectionFTP.diconnectFTP(ftpClient, serverSaved.getAddress());
                ftpClient = ConnectionFTP.connectFTP(serverSaved.getUser(), serverSaved.getPassword(), serverSaved.getAddress());

                String contentFileXML = FTPServerController.getFileFTPServer(ftpClient, itemNameFileXML);

                XMLFile xmlFileSavedRecent = CommonsTest.saveXMLFileTest(itemNameFileXML, contentFileXML, serverSaved.getAddress());

                XMLFileDAO xmlFileDAO = new XMLFileDAO();
                xmlFileDAO.removeXMLFile(xmlFileSavedRecent);

                Assert.assertEquals(xmlFileDAO.findXMLFileById(xmlFileSavedRecent.getId()), null);
            }

            ServerDAO serverDAO = new ServerDAO();
            serverDAO.removeServer(serverSaved);

            Assert.assertEquals(serverDAO.findServerByAddress(serverSaved.getAddress()), null);

            ftpClient = ConnectionFTP.diconnectFTP(ftpClient, SERVER1_ADDRESS);
            Assert.assertEquals(ftpClient.isConnected(), false);
        }
    }
}
