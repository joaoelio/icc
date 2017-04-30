/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import dao.ServerDAO;
import dao.XMLFilesDAO;
import dao.connections.ConnectionFTP;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Server;
import model.XMLFile;

/**
 *
 * @author João Elio
 */
public class Test {
    public static void main(String[] args) throws IOException {
        
        System.out.println("test new inatelProject");
        
        List<Server> listServer = new ArrayList<Server>();
        listServer.add(createServer("inatel", "inatel", "192.168.0.21"));
        listServer.add(createServer("inatel", "inatel", "192.168.0.21"));
        
        for(Server itemServer : listServer) {
            ServerDAO serverDAO = new ServerDAO();
            serverDAO.insertServer(itemServer);
            
            ConnectionFTP connectionFTP = new ConnectionFTP();
            Map<String, String> mapXMLFiles = new HashMap<>();
            mapXMLFiles = connectionFTP.connect(itemServer.getUser(), itemServer.getPassword(), itemServer.getAddress());
            
            for(String key : mapXMLFiles.keySet()) {
                XMLFile xmlFile = new XMLFile();
                xmlFile.setName(key);
                xmlFile.setContent(mapXMLFiles.get(key));

                XMLFilesDAO xmlFileDAO = new XMLFilesDAO();
                xmlFileDAO.insertXMLFile(xmlFile);
            }
            
            System.out.println(serverDAO.findServers(itemServer.getAddress()).get(0).toString());
        }
        //serverDAO.removeServer(server);
    }
    
    public static Server createServer(String user, String password, String address) {
        Server server = new Server();
        server.setUser(user);
        server.setPassword(password);
        server.setAddress(address);
        
        return server;
    }
}
