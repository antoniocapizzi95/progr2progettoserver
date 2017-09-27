/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javahttpserver;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONObject;

/**
 *
 * @author Antonio
 */
public class UploadHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange he) throws IOException {
        if (he.getRequestMethod().equalsIgnoreCase("POST")) {
            try {
                Headers requestHeaders = he.getRequestHeaders();

                int contentLength = Integer.parseInt(requestHeaders.getFirst("Content-length"));

                InputStream is = he.getRequestBody();

                byte[] data = new byte[contentLength];
                int length = is.read(data);

                Headers responseHeaders = he.getResponseHeaders();

                he.sendResponseHeaders(HttpURLConnection.HTTP_OK, contentLength);

                OutputStream os = he.getResponseBody();

                os.write(data);
                System.out.print(new String(data) + "\n");
                String dataString = new String(data);
                JSONObject obj = new JSONObject(dataString);
                TextFile listElem = new TextFile(obj.getString("title"), obj.getString("content"), obj.getString("md5"));
                //System.out.print("Title: " + dataString.substring(2) + "\n");
                ImportTxt.insert(listElem);
                try { //il nuovo file che è stato appena caricato sul server, viene copiato nella directory
                    PrintWriter writer = new PrintWriter(ImportTxt.directory + "/" + listElem.title + ".txt", "UTF-8");
                    writer.println(listElem.content);
                    writer.close();
                } catch (IOException e) {
                    // do something
                    System.out.println(e.getMessage());
                }
                
                he.close();

            } catch (NumberFormatException | IOException e) {
            }
        }

    }

}
