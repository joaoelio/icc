package dao.connections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author João Elio
 */
public class ConnectionDB {

    private final String URL_DB = "jdbc:mysql://127.0.0.1:3306/inatel";
    private final String NAME_DB = "root";
    private final String PASSWORD_DB = "root";

    private Connection con;
    private Statement statement;

    public Statement conectDB() {
        try {
            con = DriverManager.getConnection(URL_DB, NAME_DB, PASSWORD_DB);
            statement = con.createStatement();
            System.out.println("Connected to the Database: " + NAME_DB);
        } catch (SQLException e) {
            printError("Error to connect to the Database: " + NAME_DB, e.getMessage());
        }

        return statement;
    }

    public void disconectDB() {
        try {
            statement.close();
            con.close();
            System.out.println("Close connection");
        } catch (SQLException e) {
            printError("Error to close connection", e.getMessage());
        }
    }

    private void printError(String msg, String msgErro) {
        JOptionPane.showMessageDialog(null, msg, "Erro crítico", 0);
        System.err.println(msg);
        System.out.println(msgErro);
        System.exit(0);
    }
}