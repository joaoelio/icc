/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.Server;

/**
 *
 * @author João Elio
 */
public class ServerController {
    
    public static Server createServer(String user, String password, String address) {
        model.Server server = new model.Server();
        server.setUser(user);
        server.setPassword(password);
        server.setAddress(address);
        
        return server;
    }
}
