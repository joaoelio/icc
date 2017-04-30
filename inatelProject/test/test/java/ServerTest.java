/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.java;

import controller.ServerController;
import dao.ServerDAO;
import model.Server;
import org.junit.Assert;
import org.junit.Test;
import test.CommonsTest;
import static test.CommonsTest.SERVER_ADDRESS;
import static test.CommonsTest.SERVER_PASSWORD;
import static test.CommonsTest.SERVER_USER;

/**
 *
 * @author João Elio
 */
public class ServerTest {
    
    @Test
    public void testCreateServer() {
        
        Server serverSaved = CommonsTest.saveServerTest(SERVER_USER, SERVER_PASSWORD, SERVER_ADDRESS);
        
        Assert.assertEquals(SERVER_USER, serverSaved.getUser());
        Assert.assertEquals(SERVER_PASSWORD, serverSaved.getPassword());
        Assert.assertEquals(SERVER_ADDRESS, serverSaved.getAddress());
        
        ServerDAO serverDAO = new ServerDAO();
        serverDAO.removeServer(serverSaved);
        
        Assert.assertEquals(serverDAO.findServerByAddress(serverSaved.getAddress()), null);
    }
}
