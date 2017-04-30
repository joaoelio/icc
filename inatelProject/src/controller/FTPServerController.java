/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClient;

/**
 *
 * @author João Elio
 */
public class FTPServerController {
    
    public static Map<String, String> findFilesXMLFTPServer(FTPClient ftpClient) throws UnsupportedEncodingException, IOException {
        BufferedReader bufferedReader = null;
        String contentXML = null;
        Map<String, String> mapXMLFiles = new HashMap<>();
        
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
                        
                        mapXMLFiles.put(itemAllFilles, contentXML);
                        //System.out.println("\n\n\n\nContent XML File: \n" + contentXML);
                    }
                }
            }      
        }catch (NullPointerException | SocketException e) {
            System.out.println(e.getMessage());
        } finally {
            if (bufferedReader != null) try { bufferedReader.close(); } catch (IOException logOrIgnore) {}
        }
        
        return mapXMLFiles;
    }
}
