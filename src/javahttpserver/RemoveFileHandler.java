/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javahttpserver;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Antonio
 */
public class RemoveFileHandler implements HttpHandler {

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
                System.out.print(new String(data) + "\n");
                String dataString = new String(data);
                int index = Integer.parseInt(dataString);
                if (index == 0) {
                    File folder = new File("files");
                    File[] listOfFiles = folder.listFiles();

                    for (int i = 0; i < listOfFiles.length; i++) {
                        File file = listOfFiles[i];
                        if (file.isFile() && file.getName().endsWith(".txt")) {
                           file.delete();
                        }
                    }
                    ImportTxt.list.clear();
                } else if (index > 0) {
                    TextFile elem = (TextFile) ImportTxt.list.get(index - 1);
                    String title = elem.title;
                    File file = new File("files/" + title + ".txt");
                    file.delete();
                    ImportTxt.list.remove(index - 1);
                }
                he.close();

            } catch (NumberFormatException | IOException e) {
            }
        }
    }

}
