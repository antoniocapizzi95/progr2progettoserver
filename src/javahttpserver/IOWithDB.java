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

    private String connectionString = "jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11197180?user=sql11197180&password=lxYZmquz97";
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

        while (rs.next()) {

            String hashDB = rs.getString("MD5");

            if (hashDB.equals(f.md5)) {
                flag = true;
                break;
            }
        }
        if (!flag) {
            PreparedStatement prepared = connection.prepareStatement("insert into files (Title, Content, MD5) values (?,?,?)");
            prepared.setString(1, f.title);
            prepared.setString(2, f.content);
            prepared.setString(3, f.md5);
            prepared.executeUpdate();
        }

    }

    public String showFilesOnDB() throws SQLException {
        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery("select * from files");
        String res = "";
        while (rs.next()) {
            res = res + ("ID: " + rs.getString("ID") + " - File Name: " + rs.getString("Title") + " - MD5: " + rs.getString("MD5") + "\n");
        }
        return res;
    }

    public void restoreFilesFromDB() throws SQLException, IOException {
        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery("select * from files");

        while (rs.next()) {
            TextFile fDB = new TextFile(rs.getString("Title"), rs.getString("Content"), rs.getString("MD5"));

            boolean flag = false;

            for (int i = 0; i < ImportTxt.getSize(); i++) {
                TextFile f = (TextFile) ImportTxt.getElem(i);

                if (f.md5.equals(fDB.md5)) {
                    flag = true;
                    break;
                }
            }

            if (!flag) {
                ImportTxt.insert(fDB);
                try {
                    PrintWriter writer = new PrintWriter(ImportTxt.directory + "/" + fDB.title + ".txt", "UTF-8");
                    writer.println(fDB.content);
                    writer.close();
                } catch (IOException e) {
                    // do something
                    System.out.println(e.getMessage());
                }
            }

        }
    }

    public void removeFileFromDB(String toRemove) throws SQLException {
        PreparedStatement prepared = connection.prepareStatement("delete from files where ID=?");
        try {
            prepared.setString(1, toRemove);
            int res = prepared.executeUpdate();
            if (res == 0) {
                JFrame f = new JFrame();
                ErrorDialog ed = new ErrorDialog(f, true, "Invalid value");
                ed.setVisible(true);
            }
        } catch (Exception e) {
            JFrame f = new JFrame();
            ErrorDialog ed = new ErrorDialog(f, true, "Invalid value");
            ed.setVisible(true);
        }

    }
}
