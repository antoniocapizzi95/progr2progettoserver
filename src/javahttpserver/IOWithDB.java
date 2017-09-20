/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javahttpserver;

import com.mysql.jdbc.Connection;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JFrame;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Antonio
 */
public class IOWithDB {

    private String connectionString = "jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11194966?user=sql11194966&password=HW4hmP9Upg";
    private Connection connection;

    public IOWithDB() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // gestione dell'eccezione
        }
        connection = (Connection) DriverManager.getConnection(connectionString);
    }

    public void sendFileToDB(TextFile f) throws SQLException, UnsupportedEncodingException, NoSuchAlgorithmException {
        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery("select * from files");
        

        boolean flag = false;
        String hashTitle = DigestUtils.md5Hex(f.title).toUpperCase();
        //String hashContent = DigestUtils.md5Hex(f.content).toUpperCase();
        
        String contentMod = f.content.replace("\n", "");
        contentMod = contentMod.replace("\r", "");
        String hashContent = DigestUtils.md5Hex(contentMod).toUpperCase();
        while (rs.next()) {
            
            String titleDB = rs.getString("Title");
            String contentDB = rs.getString("Content");
            String contentDBMod = contentDB.replace("\n", "");
            contentDBMod = contentDBMod.replace("\r", "");

            String hashTitleDB = DigestUtils.md5Hex(titleDB).toUpperCase();
            String hashContentDB = DigestUtils.md5Hex(contentDBMod).toUpperCase();
            if (hashTitle.equals(hashTitleDB) && hashContent.equals(hashContentDB)) {
                flag = true;
                break;
            }
        }
        if (!flag) {
            PreparedStatement prepared = connection.prepareStatement("insert into files (Title, Content) values (?,?)");
            prepared.setString(1, f.title);
            prepared.setString(2, f.content);
            prepared.executeUpdate();
        }

    }

    public String showFilesOnDB() throws SQLException {
        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery("select * from files");
        String res = "";
        while (rs.next()) {
            res = res + ("ID: " + rs.getString("ID") + " - File Name: " + rs.getString("Title") + "\n");
        }
        return res;
    }

    public void restoreFilesFromDB() throws SQLException, IOException {
        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery("select * from files");
        ImportTxt.list.clear();
        File fi = new File(ImportTxt.directory);
        FileUtils.cleanDirectory(fi);

        while (rs.next()) {
            TextFile f = new TextFile(rs.getString("Title"), rs.getString("Content"));

            ImportTxt.insert(f);

            try {
                PrintWriter writer = new PrintWriter(ImportTxt.directory + "/" + f.title + ".txt", "UTF-8");
                writer.println(f.content);
                writer.close();
            } catch (IOException e) {
                // do something
                System.out.println(e.getMessage());
            }
        }
    }

    public void removeFileFromDB(String toRemove) throws SQLException {
        PreparedStatement prepared = connection.prepareStatement("delete from files where ID=?");
        try {
            int index = Integer.parseInt(toRemove);
            prepared.setString(1, toRemove);
            prepared.executeUpdate();
        } catch (Exception e) {
            JFrame f = new JFrame();
            ErrorDialog ed = new ErrorDialog(f, true, "Invalid value");
            ed.setVisible(true);
        }

    }
}
