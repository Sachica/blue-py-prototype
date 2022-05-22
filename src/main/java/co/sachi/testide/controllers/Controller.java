/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sachi.testide.controllers;

import co.sachi.testide.services.InteractiveShell;
import co.sachi.testide.services.ReloaderObjects;
import co.sachi.testide.views.IBlockObject;
import co.sachi.testide.views.JFIDE;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 *
 * @author Sachikia
 */
public class Controller implements KeyListener, ActionListener, MouseListener {

    private JFIDE ide;
    private InteractiveShell shell;
    private ReloaderObjects reloader;

    public Controller() throws Exception {
        this.ide = new JFIDE(this, this);
        this.shell = new InteractiveShell();
        this.reloader = new ReloaderObjects();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.ide.getButtonLoad()) {
            try {
                if(!this.ide.getWorkingDir().isEmpty()){
                    this.shell.initShell(this.ide.getWorkingDir());
                    JOptionPane.showMessageDialog(null, "Your shell is ready!", "Information", JOptionPane.INFORMATION_MESSAGE);
                }else{
                    JOptionPane.showMessageDialog(null, "Please select a workspace", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (FileNotFoundException err) {
                err.printStackTrace();
            } catch (IOException err) {
                err.printStackTrace();
            } catch (Exception err) {
                err.printStackTrace();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (this.shell.isRunning()) {
                try {
                    this.shell.write(this.ide.getInput());
                    this.ide.appendOutputs(this.shell.getBuffer());
                    this.shell.clearBuffer();
                    this.ide.repaintObjects(this.reloader.getObjects(shell), this);
                    this.shell.clearBuffer();
                } catch (Exception err) {
                    err.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please first load your workspace", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() instanceof IBlockObject) {
            ((IBlockObject) e.getSource()).showDialog();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
