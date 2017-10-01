/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javahttpserver;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;

/**
 *
 * @author Antonio
 */
public class GetNumberHandler implements HttpHandler{
    
    @Override
    public void handle(HttpExchange he) throws IOException {
        int numberOfFiles = ImportTxt.getSize();
        String result = Integer.toString(numberOfFiles);
        he.sendResponseHeaders(200, result.length());
        OutputStream os = he.getResponseBody();
        os.write(result.getBytes());
        os.close();
    }
}
