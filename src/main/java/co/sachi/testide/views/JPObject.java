/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sachi.testide.views;

import co.sachi.testide.utls.ViewTool;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseListener;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.json.JSONObject;

/**
 *
 * @author Sachikia
 */
public class JPObject implements IPanel{
    private JPanel panel;
    private int x = 0;
    private int y = 0;
    
    private JScrollPane scrollObjects;
    private JPanel panelObjects;

    public JPObject() throws Exception {
        this.panel = new JPanel(new GridBagLayout());
        
        this.panelObjects = new JPanel(new GridBagLayout());
        this.scrollObjects = new JScrollPane(this.panelObjects);
        
        this.init();
    }
    
    private void init() throws Exception{
        ViewTool.insert(this.panel, this.scrollObjects, 0, 0, 1, 1, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.CENTER, null, 0, 0);
        this.panel.setBackground(Color.cyan);
    }
    
    public void repaintObjects(JSONObject json, MouseListener m) throws Exception{
        this.panelObjects.removeAll();
        this.x = 0;
        this.y = 0;
        for(String key: json.keySet()){
            JSONObject attr = new JSONObject(json.get(key).toString());
            
            JPBlockObject bo = new JPBlockObject(key, attr);
            bo.addMouseListener(m);
            
            
            if(this.x > 3){
                this.y++;
                this.x = 0;
            }
            ViewTool.insert(this.panelObjects, bo, this.x++, this.y, 1, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, null, 15, 25);
        }
        this.panelObjects.revalidate();
        this.panelObjects.repaint();
    }
    
    @Override
    public JPanel getPanel() {
        return this.panel;
    }
}

