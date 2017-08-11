package javahttpserver;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Antonio
 */
public class FUSituationHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange he) throws IOException {
        
        String filesTitle = "";
        for(int i=0; i<ImportTxt.list.size(); i++) {
            File elem = (File) ImportTxt.list.get(i);
            String indexString = Integer.toString(i+1);
            filesTitle = filesTitle + indexString+")"+elem.title+" - ";
            
        }
        
        he.sendResponseHeaders(200, filesTitle.length());
        OutputStream os = he.getResponseBody();
        os.write(filesTitle.getBytes());
        os.close();
    }

}
