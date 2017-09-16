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
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.commons.io.FileUtils;

public class ImportTxt {

    public static ArrayList list = new ArrayList();
    public static int index1 = 0;
    public static int index2 = 0;

    public static void insert(TextFile elem) {
        ImportTxt.list.add(elem);
    }

    public static void addFromDir(String path) throws IOException {
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            File file = listOfFiles[i];
            if (file.isFile() && file.getName().endsWith(".txt")) {
                String name = file.getName();
                name = name.replace(".txt", "");
                String content = FileUtils.readFileToString(file);
                TextFile elem = new TextFile(name,content);
                ImportTxt.insert(elem);
            }
        }
    }

}
