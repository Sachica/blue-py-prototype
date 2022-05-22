/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sachi.testide.views;

import co.sachi.testide.utls.ViewTool;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Sachikia
 */
public class JPTop implements IPanel{
    private JPanel panel;
    private JTextField txtWorkingDir;
    private JButton btnLoad;
    private String lastDir;
    private String workingDir;

    public JPTop(ActionListener a) throws Exception {
        this.panel = new JPanel(new GridBagLayout());
        
        this.txtWorkingDir = new JTextField();
        this.txtWorkingDir.setEditable(false);
        this.workingDir = null;
        this.btnLoad = new JButton("Load");
        this.btnLoad.addActionListener(a);
        this.btnLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                askForFile();
            }
        });
        
        this.init();
    }
    
    private void init() throws Exception{
        this.txtWorkingDir.setFont(new Font("Serif", Font.PLAIN, 18));
        ViewTool.insert(this.panel, this.txtWorkingDir, 0, 0, 1, 1, 1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.LINE_START, null, 10, 0);
        ViewTool.insert(this.panel, this.btnLoad,       1, 0, 1, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_END, null, 0, 0);
    }
    
    private void askForFile(){
        JFileChooser chooser = new JFileChooser();
        if(this.lastDir != null) chooser.setCurrentDirectory(new File(this.lastDir));
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.showOpenDialog(null);
        
        this.workingDir = chooser.getSelectedFile() != null ? chooser.getSelectedFile().getAbsolutePath() : this.workingDir;
        this.lastDir = chooser.getCurrentDirectory().getAbsolutePath();
        this.txtWorkingDir.setText(workingDir);
    }

    @Override
    public JPanel getPanel() {
        return this.panel;
    }

    public JTextField getTxtWorkingDir() {
        return txtWorkingDir;
    }

    public JButton getBtnLoad() {
        return btnLoad;
    }
}
