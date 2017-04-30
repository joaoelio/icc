/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.connections.ConnectionDB;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.Server;
import model.XMLFile;

/**
 *
 * @author João Elio
 */
public class XMLFilesDAO {
    ConnectionDB b = new ConnectionDB();

    public void insertXMLFile(XMLFile xmlFiles){
        Statement comando = b.conectDB();

        try {
            comando.executeUpdate("INSERT INTO T_XML_FILE (name, content) VALUES('" + 
                    xmlFiles.getName() + "', '" + 
                    xmlFiles.getContent() + "');");
            System.out.println("XML File inserted with success!");
        } catch (SQLException e) {
            printError("Error to insert XML File.", e.getMessage());
        } finally {
            b.disconectDB();
        }
    }
    
    public List<Server> findXMLFiles() {
        Statement comando = b.conectDB();
        List<Server> resultados = new ArrayList<Server>();
        ResultSet rs;

        try {
            rs = comando.executeQuery("SELECT * FROM T_XML_FILE;");

            while (rs.next()) {
                Server temp = new Server();
                temp.setId(rs.getInt("id"));
                temp.setUser(rs.getString("user"));
                temp.setPassword(rs.getString("password"));
                temp.setAddress(rs.getString("address"));
                resultados.add(temp);
            }

            return resultados;
        } catch (SQLException e) {
            printError("Erro ao buscar pessoa", e.getMessage());
            return null;
        }
   }
    
    public List<Server> findXMLFileByName(String name) {
        Statement comando = b.conectDB();
        List<Server> resultados = new ArrayList<Server>();
        ResultSet rs;

        try {
            rs = comando.executeQuery("SELECT * FROM T_XML_FILE WHERE ADDRESS LIKE '" + name + "';");

            while (rs.next()) {
                Server temp = new Server();
                temp.setId(rs.getInt("id"));
                temp.setUser(rs.getString("user"));
                temp.setPassword(rs.getString("password"));
                temp.setAddress(rs.getString("address"));
                resultados.add(temp);
            }

            return resultados;
        } catch (SQLException e) {
            printError("Erro ao buscar pessoa", e.getMessage());
            return null;
        }
   }
    
    public void removeXMLFile(XMLFile xmlFile){
        Statement comando = b.conectDB();
        List<Server> serverList = findXMLFileByName(xmlFile.getName());

        if(!serverList.isEmpty()) {
            for (Server item : serverList) {
                try {
                    comando.executeUpdate("DELETE FROM T_XML_FILE WHERE ID = '" + item.getId() + "';");
                    System.out.println("Server with id: " + item.getId() + " removed");
                } catch (SQLException e) {
                    printError("Error to delete Server with id: " + item.getId(), e.getMessage());
                } finally {
                    b.disconectDB();
                }
            }
        }
    }
    
    private void printError(String msg, String msgErro) {
        JOptionPane.showMessageDialog(null, msg, "Erro crítico", 0);
        System.err.println(msg);
        System.out.println(msgErro);
        System.exit(0);
   }
}
