package dao.connections;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

/**
 *
 * @author João Elio
 */
public class ConnectionFTP {
    
    public static FTPClient connectFTP(String user, String password, String address) throws SocketException, IOException {
        FTPClient ftpClient = new FTPClient();
        ftpClient.connect(address);
        ftpClient.login(user, password);

        if(FTPReply.isPositiveCompletion( ftpClient.getReplyCode() )){
            System.out.println("\nServer FTP connected: " + address);
            ftpClient.enterLocalPassiveMode();
        }
        
        ftpClient.changeWorkingDirectory ("xmlFiles");
        
        return ftpClient;
    }

    public static FTPClient diconnectFTP(FTPClient ftpClient, String address) throws IOException {
        ftpClient.logout();
        ftpClient.disconnect();
        System.out.println("Server FTP disconnected: " + address);
        
        return ftpClient;
    }
}
