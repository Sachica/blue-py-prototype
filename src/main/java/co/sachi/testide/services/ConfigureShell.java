/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sachi.testide.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Sachikia
 */
public class ConfigureShell {

    private final String WORK_FOLDER = "build/";
    private final String FILE_RUN = "main.py";
    private final String FILE_SHELL = "shell.py";
    private final ClassLoader LOADER = getClass().getClassLoader();

    private File runFile;
    private File shellFile;
    private String workingDirectory;

    private void verifyFiles() throws IOException, Exception {
        URL url = LOADER.getResource(WORK_FOLDER);
        if (url == null) {
            throw new Exception("Build folder deleted");
        }

        this.shellFile = new File(url.getFile() + FILE_SHELL);
        if (!this.shellFile.exists()) {
            throw new Exception("Shell file deleted");
        }

        this.runFile = new File(url.getFile() + FILE_RUN);
        if (this.runFile.exists()) {
            this.runFile.delete();
        }
        this.runFile.createNewFile();
    }

    private void getFiles(List<File> files, String currentDirectory) {
        File dir = new File(currentDirectory);

        String folders[] = dir.list((File current, String name) -> new File(current, name).isDirectory());
        if (folders.length > 0) {
            for (String folder : folders) {
                this.getFiles(files, currentDirectory + "\\" + folder);
            }
        }
        File filesFolder[] = dir.listFiles((File dir1, String name) -> name.toLowerCase().endsWith(".py"));
        for (File fileFolder : filesFolder) {
            files.add(fileFolder);
        }
    }

    private void unifiedFiles() throws FileNotFoundException, IOException {
        List<File> files = new ArrayList<>();
        this.getFiles(files, workingDirectory);

        PrintWriter pw = new PrintWriter(this.runFile);
        
        for (File f : files) {
            this.appendFile(pw, f);
        }
        
        this.appendFile(pw, this.shellFile);
        
        pw.close();
    }

    private void appendFile(PrintWriter pw, File file) throws FileNotFoundException, IOException {
        BufferedReader bf = new BufferedReader(new FileReader(file));
        String line = "";
        while ((line = bf.readLine()) != null) {
            pw.println(line);
        }
        bf.close();
    }

    public File getRunFileFor(String workingDirectory) throws Exception {
        this.workingDirectory = workingDirectory;
        this.verifyFiles();
        this.unifiedFiles();
        return this.runFile;
    }
}
