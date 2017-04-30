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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.net.ftp.FTPClient;

/**
 *
 * @author João Elio
 */
public class FTPServerController {
    
    public static List<String> findFilesXMLFTPServer(FTPClient ftpClient) throws UnsupportedEncodingException, IOException {

        List<String> listFileXML = new ArrayList<>();
        Map<String, String> mapXMLFiles = new HashMap<>();
        
        try{
            String[] allFiles = ftpClient.listNames();
            
            if(allFiles.length > 0) {
                System.out.println("Listing files '.xml':");
                
                for(String itemAllFilles : allFiles) {
                    if(itemAllFilles.endsWith(".xml")) {
                        listFileXML.add(itemAllFilles);
                        System.out.println("    " + itemAllFilles);
                    }
                }
            } else {
                System.out.println("No exists File .xml in this FTP Server!");
            }
        }catch (NullPointerException | SocketException e) {
            System.out.println(e.getMessage());
        }
        
        return listFileXML;
    }
    
    public static String getFileFTPServer(FTPClient ftpClient, String file) throws IOException {
        
        BufferedReader bufferedReader = null;
        String contentXML = null;
        
        InputStream inputStream = ftpClient.retrieveFileStream(file);
        
        if(inputStream != null) {
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            contentXML = org.apache.commons.io.IOUtils.toString(bufferedReader);
        }
        
        return contentXML;
    }
}
