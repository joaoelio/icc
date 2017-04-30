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
import model.XMLFile;

/**
 *
 * @author João Elio
 */
public class XMLFileDAO {
    ConnectionDB b = new ConnectionDB();

    public void saveXMLFile(XMLFile xmlFile){
        Statement statement = b.conectDB();

        try {
            statement.executeUpdate("INSERT INTO T_XML_FILE (name, content, addressServer) VALUES('" + 
                    xmlFile.getName() + "', '" + 
                    xmlFile.getContent() + "', '" +
                    xmlFile.getAddressServer() + "');");
            System.out.println("XML File with Name: " + xmlFile.getName() + " inserted with success!");
        } catch (SQLException e) {
            printError("Error to insert XML File with Name: " + xmlFile.getName(), e.getMessage());
        } finally {
            b.disconectDB();
        }
    }
    
    public List<XMLFile> findAllXMLFiles() {
        Statement statement = b.conectDB();
        List<XMLFile> listXMLFile = new ArrayList<>();
        ResultSet resultSet;

        try {
            resultSet = statement.executeQuery("SELECT * FROM T_XML_FILE;");

            while (resultSet.next()) {
                XMLFile xmlFileTemp = new XMLFile();
                xmlFileTemp.setId(resultSet.getInt("id"));
                xmlFileTemp.setName(resultSet.getString("name"));
                xmlFileTemp.setContent(resultSet.getString("content"));
                xmlFileTemp.setAddressServer(resultSet.getString("addressServer"));
                listXMLFile.add(xmlFileTemp);
            }

            return listXMLFile;
        } catch (SQLException e) {
            printError("Error to find All XML Files.", e.getMessage());
            return null;
        }
   }
    
    public List<XMLFile> findXMLFileByName(String name) {
        Statement statement = b.conectDB();
        List<XMLFile> listXMLFile = new ArrayList<>();
        ResultSet rs;

        try {
            rs = statement.executeQuery("SELECT * FROM T_XML_FILE WHERE NAME LIKE '" + name + "';");

            while (rs.next()) {
                XMLFile xmlFileTemp = new XMLFile();
                xmlFileTemp.setId(rs.getInt("id"));
                xmlFileTemp.setName(rs.getString("name"));
                xmlFileTemp.setContent(rs.getString("content"));
                xmlFileTemp.setAddressServer(rs.getString("addressServer"));
                listXMLFile.add(xmlFileTemp);
            }

            return listXMLFile;
        } catch (SQLException e) {
            printError("Error to Find XML File with Name: " + name, e.getMessage());
            return null;
        }
    }
    
    public XMLFile findXMLFileById(int id) {
        Statement statement = b.conectDB();
        List<XMLFile> listXMLFile = new ArrayList<>();
        ResultSet rs;

        try {
            rs = statement.executeQuery("SELECT * FROM T_XML_FILE WHERE ID LIKE '" + id + "';");

            while (rs.next()) {
                XMLFile xmlFileTemp = new XMLFile();
                xmlFileTemp.setId(rs.getInt("id"));
                xmlFileTemp.setName(rs.getString("name"));
                xmlFileTemp.setContent(rs.getString("content"));
                xmlFileTemp.setAddressServer(rs.getString("addressServer"));
                listXMLFile.add(xmlFileTemp);
            }

            return listXMLFile.get(0);
        } catch (SQLException e) {
            printError("Error to Find XML File with Id: " + id, e.getMessage());
            return null;
        }
    }
    
    
    public void removeXMLFile(XMLFile xmlFile){
        Statement statement = b.conectDB();
        XMLFile xmlFileSaved = findXMLFileById(xmlFile.getId());

        if(xmlFileSaved != null) {
            try {
                statement.executeUpdate("DELETE FROM T_XML_FILE WHERE ID = '" + xmlFileSaved.getId() + "';");
                System.out.println("XML File with Name: " + xmlFileSaved.getName() + " removed with success!");
            } catch (SQLException e) {
                printError("Error to remove XML File with name: " + xmlFileSaved.getName(), e.getMessage());
            } finally {
                b.disconectDB();
            }
        } else {
            System.out.println("XML File with Name: " + xmlFileSaved.getName() + " not found!");
        }
    }
    
    private void printError(String msg, String msgErro) {
        JOptionPane.showMessageDialog(null, msg, "Error", 0);
        System.err.println(msg);
        System.out.println(msgErro);
        System.exit(0);
   }
}
