/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment3;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Funkcije {
        public static void list(String path){
        File path1 = new File(path);
        if(path1.exists() && path1.isDirectory()){
            String[] lista = path1.list();
            if(lista.length == 0){
                System.out.println("Folder is empty.");
            } else {
                for (String s : lista) {
                System.out.println(s);
                }   
            }
        } else {
            System.out.println("Wrong path entered!");
        }
    }
    
    public static void info(String path){
        File path1 = new File(path);
        if(path1.exists()){
            System.out.println("Name: " + path1.getName());
            System.out.println("Absolute path: " + path1.getAbsolutePath());
            System.out.println("Relative path: " + path1.getPath());
            System.out.println("Size: " + path1.length());
            Path p = Paths.get(path);
            try {
                BasicFileAttributes bfa = Files.readAttributes(p, BasicFileAttributes.class);
                System.out.println("Created: " + time(bfa.creationTime().toMillis()));
            } catch (IOException ex) {
                System.out.println(ex);
            }
            System.out.println("Last Modified: " + time(path1.lastModified()));    
        } else {
            System.out.println("Wrong path entered!");
        }
    }
    
    public static String time(long l){
        Instant instant = Instant.ofEpochMilli(l);
        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd. MMMM yyyy. HH:mm:ss");
        return dateTime.format(dateTimeFormatter);   
    }
    
    public static void createDir(String path){
        File folder = new File(path);
        String s = folder.getName();
        boolean bool = s.indexOf(92)*s.indexOf(47)*s.indexOf(58)*s.indexOf(63)
                            *s.indexOf(42)*s.indexOf(34)*s.indexOf(60)*s.indexOf(62)
                            *s.indexOf(124) < 0;
        try {
            if(!folder.exists()){
                if(bool){
                    makeDir(folder.getParentFile().exists(), folder);
                    System.out.println("Created a folder called " + folder.getName());
                } else {
                    System.out.println("A file name can't contain any of the following characters: ");
                    char[] chars = {92, 47, 58, 63, 42, 34, 60, 62, 124};
                    System.out.println(chars);
                }
            } else {
                System.out.println("Folder called " + folder.getName() + " already exists.");
            }
        } catch (Exception e) {
            System.out.println("Couldn't create a folder called " + folder.getName());
        } 
    }
    
    public static void rename(String of, String nf) {
        File oldFile = new File(of);
        String[] strings = of.split("\\\\+");
        int n = strings.length;
        strings[n-1] = nf;
        StringBuilder sb = new StringBuilder();
        sb.append(strings[0]);
        sb.append("\\");
        for(int i = 1; i <strings.length; i++){
                sb.append("\\");
                sb.append(strings[i]);
            }
        File newFile = new File(sb.toString());
        if(newFile.exists()){
            System.out.println("File with desired name already exists!");
            return;
        }
        if(oldFile.renameTo(newFile)){
            System.out.println("Rename succesful.");
        } else {
            System.out.println("Rename failed!");
        }
    }
    
    public static void copyCut(File f1, File f2, String c){
        try (FileInputStream inStream = new FileInputStream(f1);
                FileOutputStream outStream = new FileOutputStream(f1);) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inStream.read(buffer)) > 0) {  
                outStream.write(buffer, 0, length);
            }
            inStream.close();
            outStream.close();
            if("move".equals(c)){
                f1.delete();
                System.out.println("Moving is successfuly finished.");
            } else {
                System.out.println("Copying is successfuly finished.");
             }
        } catch (IOException e) {
            if("move".equals(c)){
                System.out.println("Moving failed!");
            } else {
                System.out.println("Copying failed!");
            }
        } 
    }
    
    public static void delete(String path){
        File file = new File(path);
        if(file.exists()){
            if(file.isFile()){
                file.delete();
                System.out.println("File successfully deleted!");
            } else {
                deleteDir(file);
                System.out.println("Folder successfully deleted!");
            }
        } else {
            System.out.println("Cannot delete " + file.getName() + " because " + file.getName() + " does not exist on this path.");
        }
    }
    
    public static void deleteDir(File f){
        File[] files = f.listFiles();
        if(files != null){
            for(File f1 : files){
                deleteDir(f1);
            }
        }
        f.delete();
    }

    public static void copyCutDir(File f1, File f2, String c) {
        if(!f2.exists()){
            f2.mkdir();
        }
        String fs[] = f1.list();
        for (String f : fs) {
            copyCutDir(new File(f1, f), new File(f2, f), c);
        }
        if("move".equals(c)){
            deleteDir(f1);
        }
    }
    
    public static void makeDir(boolean b, File f){
        if(b){
            f.mkdir();
        } else {
            f.mkdirs();
        }
    }
}
