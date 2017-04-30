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

/**
 *
 * @author João Elio
 */
public class ServerTest {
    
    @Test
    public void testCreateServer() {
        
        Server server = ServerController.createServer("inatel", "inatel", "192.168.0.21");
        
        ServerDAO serverDAO = new ServerDAO();
        serverDAO.saveServer(server);
        
        Server serverSaved = serverDAO.findServerByAddress("192.168.0.21");
        
        Assert.assertEquals(server.getUser(), serverSaved.getUser());
        Assert.assertEquals(server.getUser(), serverSaved.getUser());
        Assert.assertEquals(server.getPassword(), serverSaved.getPassword());
    }
}
