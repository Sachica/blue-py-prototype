/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sachi.testide.views;

import co.sachi.testide.utls.ViewTool;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import org.json.JSONObject;

/**
 *
 * @author Sachikia
 */
public class JFIDE {
    private JFrame frame;
    private JPTop panelTop;
    private JPObject panelObjects;
    private JPConsole panelConsole;
    
    public JFIDE(KeyListener k, ActionListener a) throws Exception{
        this.frame = new JFrame("Blue Py Prototype");
        this.frame.setLayout(new GridBagLayout());
        this.frame.setPreferredSize(new Dimension(1300, 400));
        
        this.panelConsole = new JPConsole(k);
        this.panelTop = new JPTop(a);
        
        this.panelObjects = new JPObject();
        
        this.init();
    }
    
    private void init() throws Exception{
        ViewTool.insert(this.frame.getContentPane(), this.panelTop.getPanel(),      0, 0, 1, 0, 1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER, new Insets(5, 5, 5, 0), 0, 0);
        ViewTool.insert(this.frame.getContentPane(), this.panelObjects.getPanel(),  0, 1, 1, 1, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.CENTER, new Insets(0, 5, 5, 2), 0, 0);
        ViewTool.insert(this.frame.getContentPane(), this.panelConsole.getPanel(),  1, 1, 1, 1, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.CENTER, new Insets(0, 2, 5, 5), 0, 0);
        
        this.frame.pack();
        this.frame.setResizable(false);
        this.frame.setLocationRelativeTo(null);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setVisible(true);
    }
    
    public String getInput(){
        return this.panelConsole.getInput();
    }
    
    public JButton getButtonLoad(){
        return this.panelTop.getBtnLoad();
    }
    
    public String getWorkingDir(){
        return this.panelTop.getTxtWorkingDir().getText();
    }
    
    public void appendOutputs(List<String> outs) throws Exception{
        this.panelConsole.appendOutput(outs);
    }
    
    public void repaintObjects(JSONObject json, MouseListener m) throws Exception{
        if (json != null){
            this.panelObjects.repaintObjects(json, m);
        }
    }
}
