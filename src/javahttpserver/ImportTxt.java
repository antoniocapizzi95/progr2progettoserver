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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import org.apache.commons.io.FileUtils;

public class ImportTxt {

    public static ArrayList list = new ArrayList();
    public static int index1 = 0;
    public static int index2 = 0;

    public static String directory;

    public ImportTxt() {

    }

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
                TextFile elem = new TextFile(name, content);
                ImportTxt.insert(elem);
            }
        }
    }

    public static String setDirectoryPath() throws IOException {
        String path = null;
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("Select the directory to save the files");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //
        // disable the "All files" option.
        //
        chooser.setAcceptAllFileFilterUsed(false);
        //    
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            System.out.println("getCurrentDirectory(): "
                    + chooser.getCurrentDirectory());
            System.out.println("getSelectedFile() : "
                    + chooser.getSelectedFile());
            path = chooser.getSelectedFile().toString();
        } else {
            System.out.println("No Selection ");
        }
        ImportTxt.directory = path;
        return path;
    }

    public static String importJSON(String path) {

        FileReader f = null;
        try {
            f = new FileReader(path);
        } catch (FileNotFoundException ex) {
            //Logger.getLogger(ImportTextFile.class.getName()).log(Level.SEVERE, null, ex);
        }

        BufferedReader b;
        b = new BufferedReader(f);

        String s = null;

        boolean firstLine = true;

        String fileContent = null;

        while (true) {
            String nextLine = "\n";
            try {
                if (firstLine) {
                    s = b.readLine();
                    if (s == null) {
                        break;
                    }
                    fileContent = s;
                    firstLine = false;
                } else {

                    s = b.readLine();
                    if (s == null) {
                        break;
                    }
                    fileContent = fileContent.concat(s);
                }
            } catch (IOException ex) {
                //Logger.getLogger(ImportTextFile.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return fileContent;
    }

}
