/*
 * Program obavlja fukncije file managera.
 * Od funkcionalnosti poseduje:
 *      - pregled foldera
 *      - prikaz informacija
 *      - kreiranje foldera
 *      - promena imena foldera/fajlova
 *      - kopiranje foldera/fajlova
 *      - premestanje foldera/fajlova
 *      - brisanje foldera/fajlova
 */
package assignment3;

import static assignment3.Funkcije.*;
import java.io.*;
import java.util.Scanner;

public class FileManager {
    public static void main(String[] args) {
        String commands = "Available commands: \nLIST\nINFO\nCREATE_DIR\nRENAME\nCOPY\nMOVE\nDELETE\nQUIT\n";
        System.out.print(commands);
        Scanner s = new Scanner(System.in);
        String message = "Enter a desired command: ";
        OUTER:
        while (true) {
            System.out.println(message);
            String str = s.nextLine().toLowerCase().trim();
            if (null == str) {
                System.out.println("Command you entered, doesn't exist!");
            } else if("help".equals(str)){
                System.out.println(commands);
                message = "Enter a desired command:";
            } else {
                switch (str) {
                    case "list":
                        System.out.println("Enter folder's path: ");
                        list(s.nextLine().trim());
                        break;
                    case "info":
                        System.out.println("Enter desired file/folder's path: ");
                        info(s.nextLine().trim());
                        break;
                    case "create_dir":
                        System.out.println("Enter new folder's path: ");
                        createDir(s.nextLine().trim());
                        break;
                    case "rename":
                        {
                            System.out.println("Enter path of file/folder you want to rename: ");
                            String s1 = s.nextLine().trim();
                            File f = new File(s1);
                            if(!f.exists()){
                                System.out.println("File doesn't exists!");
                                return;
                            }
                            System.out.println("Enter new name of file/folder: ");
                            String s2 = s.nextLine().trim();
                            rename(s1, s2);
                            break;
                        }
                    case "copy":
                    case "move":
                        {
                            System.out.println("Enter path of file/folder you would like to copy/move: ");
                            String s1 = s.nextLine().trim();
                            System.out.println("Enter destination path: ");
                            String s2 = s.nextLine().trim();
                            File f1 = new File(s1);
                            File f2 = new File(s2);
                            if(f1.isDirectory()){
                                try{
                                    copyCutDir(f1, f2, str);
                                    if("move".equals(str)){
                                        System.out.println("Moving is successfuly finished.");
                                    } else {
                                        System.out.println("Copying is successfuly finished.");
                                    }
                                } catch(Exception e) {
                                    if("move".equals(str)){
                                        System.out.println("Moving failed!");
                                    } else {
                                        System.out.println("Copying failed!");
                                    }
                                }
                            } else {
                                copyCut(f1, f2, str);
                            }
                            break;
                        }
                    case "delete":
                        System.out.println("Enter paht of file/folder you want to delete: ");
                        delete(s.nextLine().trim());
                        break;
                    case "quit":
                        System.out.println("You closed File Manager.");
                        break OUTER;
                    default:
                        System.out.println("Command you entered, doesn't exist!");
                        break;
                }
                message = "Enter a desired command (HELP for available commands): ";
            }
        }
    }
    

}
