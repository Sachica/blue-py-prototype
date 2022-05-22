/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sachi.testide.services;

import java.io.IOException;
import java.util.List;
import org.json.JSONObject;

/**
 *
 * @author Sachikia
 */
public class ReloaderObjects {
    
    public JSONObject getObjects(InteractiveShell shell) throws IOException{
        shell.write("get_classes(locals())");
        List<String> outs = shell.getBuffer();
        
        //remove key
        outs.remove(0);
        
        //Solo habra uno
        JSONObject obj = null;
        for(String json: outs){
            obj = new JSONObject(json);
        }
        
        return obj;
    }
}
