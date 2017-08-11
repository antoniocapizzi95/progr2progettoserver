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
public class SetIndexHandler implements HttpHandler{
    @Override
    public void handle(HttpExchange he) throws IOException {
        if (he.getRequestMethod().equalsIgnoreCase("POST")) {
            try {
                Headers requestHeaders = he.getRequestHeaders();
                //Set<Map.Entry<String, List<String>>> entries = requestHeaders.entrySet();

                int contentLength = Integer.parseInt(requestHeaders.getFirst("Content-length"));
                //System.out.println("" + requestHeaders.getFirst("Content-length"));

                InputStream is = he.getRequestBody();

                byte[] data = new byte[contentLength];
                int length = is.read(data);

                Headers responseHeaders = he.getResponseHeaders();

                he.sendResponseHeaders(HttpURLConnection.HTTP_OK, contentLength);

                OutputStream os = he.getResponseBody();

                os.write(data);
                System.out.print(new String(data)+"\n");
                String dataString = new String(data);
                if(ImportTxt.index1 == 0) {
                    ImportTxt.index1 = Integer.parseInt(dataString);
                } else
                    if(ImportTxt.index2 == 0) {
                    ImportTxt.index2 = Integer.parseInt(dataString);
                    }
                he.close();

            } catch (NumberFormatException | IOException e) {
            }
        }
    }
    
}
