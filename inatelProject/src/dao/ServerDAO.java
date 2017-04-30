package dao;

import dao.connections.ConnectionDB;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.Server;

/**
 *
 * @author João Elio
 */
public class ServerDAO {
    ConnectionDB b = new ConnectionDB();

    public void saveServer(Server server){
        Statement comando = b.conectDB();
        
        Server serverSaved = findServerByAddress(server.getAddress());

        if(serverSaved == null) {
            try {
                comando.executeUpdate("INSERT INTO T_SERVER (user, password, address) VALUES('" + 
                        server.getUser() + "', '" + 
                        server.getPassword() + "', '" + 
                        server.getAddress() + "');");
                System.out.println("Server inserted with success!");
            } catch (SQLException e) {
                printError("Error to insert Server.", e.getMessage());
            } finally {
                b.disconectDB();
            }
        } else {
            printError("Error to insert Server. This address already exists!", "");
        }
    }
    
    public List<Server> findAllServers() {
        Statement comando = b.conectDB();
        List<Server> listServer = new ArrayList<>();
        ResultSet resultSet;

        try {
            resultSet = comando.executeQuery("SELECT * FROM T_SERVER;");

            while (resultSet.next()) {
                Server serverTemp = new Server();
                serverTemp.setId(resultSet.getInt("id"));
                serverTemp.setUser(resultSet.getString("user"));
                serverTemp.setPassword(resultSet.getString("password"));
                serverTemp.setAddress(resultSet.getString("address"));
                listServer.add(serverTemp);
            }

            return listServer;
        } catch (SQLException e) {
            printError("Error to get All Servers.", e.getMessage());
            return null;
        }
    }
    
    public Server findServerByAddress(String address) {
        Statement comando = b.conectDB();
        ResultSet rs = null;

        try {
            rs = comando.executeQuery("SELECT * FROM T_SERVER WHERE ADDRESS LIKE '" + address + "';");

            if(rs != null) {
                while (rs.next()) {
                    Server server = new Server();
                    server.setId(rs.getInt("id"));
                    server.setUser(rs.getString("user"));
                    server.setPassword(rs.getString("password"));
                    server.setAddress(rs.getString("address"));
                    
                    return server;
                }
            }

            return null;
        } catch (SQLException e) {
            printError("Error to find Server with address: " + address, e.getMessage());
            return null;
        }
    }
    
    public void removeServer(Server server){
        Statement comando = b.conectDB();
        Server serverSaved = findServerByAddress(server.getAddress());

        if(serverSaved != null) {
            try {
                comando.executeUpdate("DELETE FROM T_SERVER WHERE ID = '" + serverSaved.getId() + "';");
                System.out.println("Server with id: " + serverSaved.getId() + " removed");
            } catch (SQLException e) {
                printError("Error to delete Server with id: " + serverSaved.getId(), e.getMessage());
            } finally {
                b.disconectDB();
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
