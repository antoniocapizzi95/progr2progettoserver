/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javahttpserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpServer;
import java.io.File;

/**
 *
 * @author Antonio
 */
public class JavaHTTPServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        File dir = new File("files");
        if(!dir.exists()) {
            dir.mkdir();
        }
        ImportTxt.addFromDir(dir.toString());

        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/upload", new UploadHandler());
        server.createContext("/getSR", new SimilarityHandler());
        server.createContext("/getFUSituation", new FUSituationHandler());
        server.createContext("/getNumber", new GetNumberHandler());
        server.createContext("/setIndex", new SetIndexHandler());
        server.createContext("/removeFile", new RemoveFileHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

}
