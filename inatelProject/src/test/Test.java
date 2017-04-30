/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import controller.ServerController;
import dao.ServerDAO;
import dao.XMLFileDAO;
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
        
        List<Server> listServer = new ArrayList<>();
        listServer.add(ServerController.createServer("inatel", "inatel", "192.168.0.21"));
        listServer.add(ServerController.createServer("inatel", "inatel", "192.168.0.21"));
        
        for(Server itemServer : listServer) {
            ServerDAO serverDAO = new ServerDAO();
            serverDAO.saveServer(itemServer);
            
            ConnectionFTP connectionFTP = new ConnectionFTP();
            Map<String, String> mapXMLFiles = new HashMap<>();
            mapXMLFiles = connectionFTP.connect(itemServer.getUser(), itemServer.getPassword(), itemServer.getAddress());
            
            for(String key : mapXMLFiles.keySet()) {
                XMLFile xmlFile = new XMLFile();
                xmlFile.setName(key);
                xmlFile.setContent(mapXMLFiles.get(key));

                XMLFileDAO xmlFileDAO = new XMLFileDAO();
                xmlFileDAO.saveXMLFile(xmlFile);
            }
            
            System.out.println(serverDAO.findServerByAddress(itemServer.getAddress()).toString());
        }
        //serverDAO.removeServer(server);
    }
}
