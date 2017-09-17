/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javahttpserver;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

    public void sendFileToDB(TextFile f) throws SQLException {
        PreparedStatement prepared = connection.prepareStatement("insert into files (Title, Content) values (?,?)");
        prepared.setString(1, f.title);
        prepared.setString(2, f.content);
        prepared.executeUpdate();
    }

    public String showFilesOnDB() throws SQLException {
        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery("select * from files");
        String res = "";
        while (rs.next()) {
            res = res + (rs.getString("Title") + " - ");
        }
        return res;
    }

    public void restoreFilesFromDB() throws SQLException {
        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery("select * from files");
        ImportTxt.list.clear();
        TextFile f = new TextFile(null,null);

        while (rs.next()) {
            f.title = rs.getString("Title");
            f.content = rs.getString("Content");
            ImportTxt.insert(f);
        }
    }
    
    public void removeFileFromDB(String toRemove) throws SQLException{
        PreparedStatement prepared = connection.prepareStatement("delete from files where Title=?");
        prepared.setString(1, toRemove);
        prepared.executeUpdate();
    }
}
