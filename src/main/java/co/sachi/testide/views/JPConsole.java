/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sachi.testide.views;

import co.sachi.testide.utls.ViewTool;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyListener;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 *
 * @author Sachikia
 */
public class JPConsole implements IPanel{
    private JPanel panel;
    private JScrollPane scrollOutputs;
    private JPanel containerOutputs;
    private JTextField txtInput;
    private int numOutputs;

    public JPConsole(KeyListener k) throws Exception {
        this.panel = new JPanel(new GridBagLayout());
        
        this.containerOutputs = new JPanel(new GridBagLayout());
        this.scrollOutputs = new JScrollPane(this.containerOutputs);
        this.txtInput = new JTextField();
        this.txtInput.setFont(new Font("Serif", Font.PLAIN, 20));
        this.txtInput.addKeyListener(k);
        this.numOutputs = 0;
        
        this.init();
    }
    
    private void init() throws Exception{
        ViewTool.insert(this.panel, this.scrollOutputs,     0, 0, 1, 1, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.CENTER, new Insets(0, 5, 0, 5), 0, 0);
        ViewTool.insert(this.panel, this.txtInput,          0, 1, 1, 0, 1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER, new Insets(0, 5, 0, 5), 0, 10);
    }

    public void appendOutput(List<String> outs) throws Exception{
        int i = 0;
        for(String out: outs){
            JLabel lbl = new JLabel((i++ > 0 ? ">>> " : "")+out);
            lbl.setFont(new Font("Serif", Font.PLAIN, 20));
            lbl.setForeground(out.contains("error") ? Color.RED : Color.BLACK);
            ViewTool.insert(this.containerOutputs, lbl, 0, this.numOutputs++, 1, 0, 1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.PAGE_END, null, 0, 0);
        }
        SwingUtilities.invokeLater(() -> {
                JScrollBar bar = this.scrollOutputs.getVerticalScrollBar();
                bar.setValue(bar.getMaximum());
        });
        this.panel.validate();
    }
    
    @Override
    public JPanel getPanel() {
        return this.panel;
    }
    
    public String getInput(){
        String input = this.txtInput.getText();
        this.txtInput.setText("");
        return input;
    }
}
