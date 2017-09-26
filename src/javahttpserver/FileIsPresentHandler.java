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
public class FileIsPresentHandler implements HttpHandler {
    
    private static String lastMD5 = null;

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

                //os.write(data);
                System.out.print(new String(data) + "\n");
                String dataString = new String(data);
                dataString = dataString.replace("\n","");
                dataString = dataString.replace("\r","");
                FileIsPresentHandler.lastMD5 = dataString;
                boolean isPresent = false;
                for(int i=0; i<ImportTxt.list.size(); i++) {
                    TextFile f = (TextFile) ImportTxt.list.get(i);
                    if(dataString.equals(f.md5)) {
                        isPresent = true;
                        break;
                    }
                }
                if(isPresent) os.write(1);
                else os.write(0);
                he.close();

            } catch (NumberFormatException | IOException e) {
            }
        }
    }

    public static String getLastMD5() {
        return lastMD5;
    }

    public static void setLastMD5(String lastMD5) {
        FileIsPresentHandler.lastMD5 = lastMD5;
    }

}
