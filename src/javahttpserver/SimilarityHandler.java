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
public class SimilarityHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange he) throws IOException {
        File firstElem = (File) ImportTxt.list.get(0);
        File secondElem = (File) ImportTxt.list.get(1);
        double result = SimilarityCheck.compareStrings(firstElem.content, secondElem.content);
        String response = Double.toString(result * 100.0);
        he.sendResponseHeaders(200, response.length());
        OutputStream os = he.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
    
}
