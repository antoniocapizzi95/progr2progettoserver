/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javahttpserver;

/**
 *
 * @author Antonio
 */
import java.util.ArrayList;


public class ImportTxt {
    public static ArrayList list = new ArrayList();
    
    public static void insert(File elem) {
        ImportTxt.list.add(elem);
    }
    
    /*public static void readAll() {
        for(int i=0; i<ImportTxt.list.size();i++) {
            System.out.println("elemento: "+ImportTxt.list.get(i)+"\n");
        }
    }*/

    /*static void insert(File listElem) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/
    
}
