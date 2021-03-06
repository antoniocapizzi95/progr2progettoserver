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
        String response = null;
        if(ImportTxt.getSize() <= 1) {
            response = "Error: Upload at least 2 files";
        } else {
            TextFile firstElem = (TextFile) ImportTxt.getElem(ImportTxt.index1 - 1);
            TextFile secondElem = (TextFile) ImportTxt.getElem(ImportTxt.index2 - 1);
            double result= 0.0;
            switch(SimilarityCheck.algToUse) {
                case "jaro": {
                    result = SimilarityCheck.compareStrings(firstElem.content, secondElem.content);
                    result = result * 100.0;
                    break;
                }
                case "lev": {
                    result = SimilarityCheck.similarity(firstElem.content, secondElem.content);
                    result = result * 100.0;
                    result = Math.round(result);
                    break;
                }
            }
            response = Double.toString(result);
            ImportTxt.index1 = 0;
            ImportTxt.index2 = 0;
        }
        
        he.sendResponseHeaders(200, response.length());
        OutputStream os = he.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
    
}
