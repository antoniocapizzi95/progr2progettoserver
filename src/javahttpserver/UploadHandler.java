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

/**
 *
 * @author Antonio
 */
public class UploadHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange he) throws IOException {
        /*String response = "This is the response";
        t.sendResponseHeaders(200, response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();*/

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
                System.out.print(new String(data) + "\n");
                String dataString = new String(data);
                if ("t".equals(dataString.substring(0, 1))) {
                    TextFile listElem = new TextFile(dataString.substring(2), null,null);
                    System.out.print("Title: " + dataString.substring(2) + "\n");
                    ImportTxt.insert(listElem);
                }
                if ("c".equals(dataString.substring(0, 1))) {
                    int index = ImportTxt.list.size() - 1;
                    TextFile listElem = (TextFile) ImportTxt.list.get(index);
                    listElem.content = dataString.substring(2);
                    listElem.md5 = FileIsPresentHandler.getLastMD5();
                    System.out.print("Content: " + dataString.substring(2) + "\n");
                    ImportTxt.list.remove(index);
                    ImportTxt.list.add(index, listElem);
                    try {
                        PrintWriter writer = new PrintWriter(ImportTxt.directory+"/"+listElem.title+".txt", "UTF-8");
                        writer.println(listElem.content);
                        writer.close();
                    } catch (IOException e) {
                        // do something
                        System.out.println(e.getMessage());
                    }

                }
                he.close();

            } catch (NumberFormatException | IOException e) {
            }
        }

    }

}
