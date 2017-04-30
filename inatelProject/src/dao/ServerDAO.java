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

    public void insertServer(Server server){
        Statement comando = b.conectDB();

        try {
            comando.executeUpdate("INSERT INTO T_SERVER (user, password, address) VALUES('" + 
                    server.getUser() + "', '" + 
                    server.getPassword() + "', '" + 
                    server.getAddress() + "');");
            System.out.println("Server inserted with success!");
        } catch (SQLException e) {
            printError("Erro ao inserir Pessoa", e.getMessage());
        } finally {
            b.disconectDB();
        }
    }
    
    public List<Server> findServers(String address) {
        Statement comando = b.conectDB();
        List<Server> resultados = new ArrayList<Server>();
        ResultSet rs;

        try {
            rs = comando.executeQuery("SELECT * FROM T_SERVER WHERE ADDRESS LIKE '" + address + "';");

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
    
    public void removeServer(Server server){
        Statement comando = b.conectDB();
        List<Server> serverList = findServers(server.getAddress());

        if(!serverList.isEmpty()) {
            for (Server item : serverList) {
                try {
                    comando.executeUpdate("DELETE FROM T_SERVER WHERE ID = '" + item.getId() + "';");
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
