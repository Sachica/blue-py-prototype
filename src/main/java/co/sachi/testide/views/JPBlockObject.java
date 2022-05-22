/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sachi.testide.views;

import co.sachi.testide.utls.ViewTool;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import org.json.JSONObject;

/**
 *
 * @author Sachikia
 */
public class JPBlockObject extends JPanel implements IBlockObject {

    private JSONObject attrInfo;
    private JLabel lblName;
    private JLabel lblClass;

    public JPBlockObject(String key, JSONObject attrInfo) {
        super(new GridLayout(2, 1));

        this.attrInfo = attrInfo;
        this.lblName = new JLabel(key);
        this.lblName.setFocusable(false);
        this.lblClass = new JLabel("<< " + this.attrInfo.get("type").toString() + " >>");
        this.lblClass.setFocusable(false);

        this.init();
    }

    private void init() {
        this.customLabel(this.lblName, Color.WHITE, Color.RED, 20, false);
        this.customLabel(this.lblClass, Color.WHITE, Color.RED, 14, false);

        super.add(this.lblName);
        super.add(this.lblClass);

        super.setBackground(Color.red);
        super.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void customLabel(JLabel lbl, Color foreground, Color background, int fontSize, boolean border) {
        lbl.setOpaque(true);
        lbl.setForeground(foreground);
        lbl.setBackground(background);
        lbl.setFont(new Font("Serif", Font.PLAIN, fontSize));
        lbl.setHorizontalAlignment(SwingConstants.CENTER);
        if(border){
            lbl.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        }
    }

    @Override
    public void showDialog() {
        JPanel panel = new JPanel(new GridBagLayout());
        int i = 0;
        JPanel panelInfo = new JPanel(new GridLayout(1, 3));
        JLabel lblKey = new JLabel("Name");
        JLabel lblVal = new JLabel("Value");
        JLabel lblType = new JLabel("Type");
              
        this.customLabel(lblKey, Color.BLACK, Color.LIGHT_GRAY, 14, true);
        this.customLabel(lblVal, Color.BLACK, Color.LIGHT_GRAY, 14, true);
        this.customLabel(lblType, Color.BLACK, Color.LIGHT_GRAY, 14, true);
        
        panelInfo.add(lblKey);
        panelInfo.add(lblVal);
        panelInfo.add(lblType);
        try{
            ViewTool.insert(panel, panelInfo, 0, i++, 1, 0, 1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER, null, 0, 0);
        }catch(Exception e){
            //nunca ocurre
        }
        for(String key: this.attrInfo.keySet()){
            panelInfo = new JPanel(new GridLayout(1, 3));
            lblKey = new JLabel(key);
            lblVal = new JLabel(this.attrInfo.get(key).toString());
            lblType = new JLabel(this.attrInfo.get(key).getClass().getCanonicalName());            
            this.customLabel(lblKey, Color.BLACK, Color.WHITE, 14, true);
            this.customLabel(lblVal, Color.BLACK, Color.WHITE, 14, true);
            this.customLabel(lblType, Color.BLACK, Color.WHITE, 14, true);
            
            panelInfo.add(lblKey);
            panelInfo.add(lblVal);
            panelInfo.add(lblType);
            
            try{
                ViewTool.insert(panel, panelInfo, 0, i++, 1, 0, 1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER, null, 0, 0);
            }catch(Exception e){
                //nunca ocurre
            }
        }
        JOptionPane.showMessageDialog(null, panel, "Information", JOptionPane.INFORMATION_MESSAGE);
    }
}

