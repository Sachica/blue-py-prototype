/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sachi.testide.services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Sachikia
 */
public class InteractiveShell {

    private Process process;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;
    private ConfigureShell config;
    private List<String> buffer = new ArrayList<>();

    public InteractiveShell() throws IOException {
        this.config = new ConfigureShell();
    }
    
    public void reset() throws IOException{
        if(this.isRunning()){
            this.process.destroyForcibly();
            this.bufferedReader.close();
            this.bufferedWriter.close();
        }
    }

    public boolean isRunning(){
        return this.process != null;
    }
    
    public void initShell(String workingPath) throws FileNotFoundException, IOException, Exception {
        this.reset();
        File file = this.config.getRunFileFor(workingPath);
        this.process = new ProcessBuilder("python", file.getAbsolutePath()).start();
        this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
        this.bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        this.listen();
    }

    public void write(String command) throws IOException {
        this.buffer.add(command);
        bufferedWriter.write(command + "\nprint('key')\n");
        bufferedWriter.flush();
    }

    private void listen() {
        new Thread(() -> {
            try {
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    buffer.add(line);
                }
            } catch (IOException e) {
            }
        }).start();
    }

    public List<String> getBuffer() {
        if (this.buffer.isEmpty() || !this.buffer.get(this.buffer.size() - 1).equals("key")) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException err) {
            }
            this.getBuffer();
        }
        this.buffer.remove("key");
        return this.buffer;
    }

    public void clearBuffer() {
        this.buffer.clear();
    }
}
