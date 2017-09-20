/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javahttpserver;

import com.mysql.jdbc.Connection;
import java.io.IOException;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpServer;
import java.io.File;
import java.nio.file.Path;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JFrame;
import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONObject;

/**
 *
 * @author Antonio
 */
public class JavaHTTPServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, SQLException {
        // TODO code application logic here

       
        String param = null;
        String portParam = null;
        String address = null;
        String path = null;
        try {
            param = ImportTxt.importJSON("param.json");
            JSONObject obj = new JSONObject(param);
            address = obj.getString("address");
            portParam = obj.getString("port");
            path = obj.getString("path");
            path = path.replace("*", "\\");
            ImportTxt.directory = path;
        } catch (Exception e) {
            JFrame f = new JFrame();
            CreateJSONDialog cjd = new CreateJSONDialog(f, true);
            cjd.setVisible(true);
            portParam = cjd.getPort();
            address = cjd.getAddress();
            path = cjd.getPath();
            ImportTxt.directory = path;
        }

        int port = 0;
        try {
            port = Integer.parseInt(portParam);
        }
        catch(Exception e) {
            JFrame frame = new JFrame();
            ErrorDialog ed = new ErrorDialog(frame,true,"The value of port is invalid");
            ed.setVisible(true);
        }

        File dir = new File(ImportTxt.directory);
        /*if (!dir.exists()) {
            dir.mkdir();
        }*/
        ImportTxt.addFromDir(dir.toString());

        HttpServer server = HttpServer.create(new InetSocketAddress(address,port), 0);
        server.createContext("/upload", new UploadHandler());
        server.createContext("/getSR", new SimilarityHandler());
        server.createContext("/getFUSituation", new FUSituationHandler());
        server.createContext("/getNumber", new GetNumberHandler());
        server.createContext("/setIndex", new SetIndexHandler());
        server.createContext("/removeFile", new RemoveFileHandler());
        server.createContext("/setAlg", new SetAlgHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
        ServerStatus servstat = new ServerStatus();
        servstat.setVisible(true);
    }

}
