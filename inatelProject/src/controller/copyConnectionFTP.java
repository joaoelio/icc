/*package dao.connections;

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
 
public class ConnectionFTP {
    
    public Map<String, String> connect(String user, String password, String address) throws SocketException, IOException {
        FTPClient ftpClient = new FTPClient();
        ftpClient.connect(address);
        ftpClient.login(user, password);

        if(FTPReply.isPositiveCompletion( ftpClient.getReplyCode() )){
            System.out.println("Server FTP connected: " + address);
            ftpClient.enterLocalPassiveMode();
        }
        
        ftpClient.changeWorkingDirectory ("xmlFiles");
        
        BufferedReader bufferedReader = null;
        String contentXML = null;
        Map<String, String> listXMLFiles = new HashMap<>();
        
        try{
            String[] allFiles = ftpClient.listNames();
            if(allFiles.length > 0) {
                System.out.println("\nListing files '.xml':");
                
                for(String itemAllFilles : allFiles) {
                    if(itemAllFilles.endsWith(".xml")) {
                        System.out.println(itemAllFilles);
                        InputStream inputStream = ftpClient.retrieveFileStream(itemAllFilles);
                        bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                        contentXML = org.apache.commons.io.IOUtils.toString(bufferedReader);
                        
                        listXMLFiles.put(itemAllFilles, contentXML);
                        //System.out.println("\n\n\n\nContent XML File: \n" + contentXML);
                    }
                }
            }
            
            ftpClient.logout();
            ftpClient.disconnect();	      
        }catch (NullPointerException | SocketException e) {
            System.out.println(e.getMessage());
        } finally {
            if (bufferedReader != null) try { bufferedReader.close(); } catch (IOException logOrIgnore) {}
        }
        
        return listXMLFiles;
    }
}
*/