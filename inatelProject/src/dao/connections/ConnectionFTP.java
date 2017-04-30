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
    
    public Map<String, String> connect(String user, String password, String address) throws SocketException, IOException {
        FTPClient ftp = new FTPClient();
        ftp.connect(address);
        ftp.login(user, password);

        if(FTPReply.isPositiveCompletion( ftp.getReplyCode() )){
            System.out.println("Server FTP connected: " + address);
            ftp.enterLocalPassiveMode();
        }
        
        ftp.changeWorkingDirectory ("xmlFiles");
        
        
        BufferedReader bufferedReader = null;
        String firstLine = null;
        Map<String, String> listXMLFiles = new HashMap<String, String>();
        
        try{
            String[] arq = ftp.listNames();
            if(arq.length > 0) {
                
                System.out.println("\nListing files '.xml':");
                for(String item : arq) {
                    if(item.endsWith(".xml")) {
                        System.out.println(item);
                        InputStream inputStream = ftp.retrieveFileStream(item);
                        bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                        
                        firstLine = org.apache.commons.io.IOUtils.toString(bufferedReader);
                        
                        listXMLFiles.put(item, firstLine);
                        System.out.println("\n\n\n\nContent XML File: \n" + firstLine);
                    }
                }
            }
            System.out.println ("\n\n");
            
            
            
            ftp.logout();
            ftp.disconnect();	      
        }catch (NullPointerException | SocketException e) {
            System.out.println(e.getMessage());
        } finally {
            if (bufferedReader != null) try { bufferedReader.close(); } catch (IOException logOrIgnore) {}
        }
        
        return listXMLFiles;
    }
}
