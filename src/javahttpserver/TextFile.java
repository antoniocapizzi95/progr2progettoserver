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
public class TextFile {
    public TextFile(String t, String c) {
        this.title = t;
        this.content = c;
    }
    
    public String title;
    public String content;

    TextFile(String nameoffolder) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
