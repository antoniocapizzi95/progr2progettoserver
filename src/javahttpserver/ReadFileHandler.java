/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javahttpserver;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

/**
 *
 * @author Antonio
 */
public class ReadFileHandler implements HttpHandler{
    @Override
    public void handle(HttpExchange he) throws IOException {
        String content = "";
        
        TextFile elem = (TextFile) ImportTxt.getElem(ImportTxt.index1 - 1);
        content = elem.content;
        ImportTxt.index1 = 0;
        he.sendResponseHeaders(200, content.length());
        OutputStream os = he.getResponseBody();
        os.write(content.getBytes());
        os.close();
    }
    
}
