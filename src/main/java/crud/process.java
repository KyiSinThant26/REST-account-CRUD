/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crud;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Mike Chang
 */
public class process {
    public DefaultTableModel getmodel(){
         DefaultTableModel model= new DefaultTableModel();
       model=(DefaultTableModel)(accpage.jtablee.getModel());
       return model;
    }
            
    public void processRead(){
         DefaultTableModel model=getmodel();
         String listStr = ToDBServer.read("/account");
        try {
            List<Map<String, Object>> list = new ObjectMapper().readValue(listStr, List.class);
            for (Map<String,Object> map : list) {
            {
            	String name = map.get("name").toString();	
            	String username = map.get("username").toString();
            	String email = map.get("email").toString();
            	String dob = map.get("dob").toString();
            	//String id = map.get("id").toString();
            	model.addRow(new Object[] {name, username, email, dob});
                
            }
            }
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
        
       
        
    }
    public void processCreate(String name,String username,String email,String dob){
        //adding data to table//
       DefaultTableModel model=getmodel();
       model.addRow(new Object[]{name,username,email,dob});
       
     
        //add data to db//
        Map<String,Object> createlist=new HashMap<String,Object>();
      createlist.put("name", name);
       createlist.put("username", username);
       createlist.put("email", email);
       createlist.put("dob", dob);
       
       ToDBServer.create("/account", createlist);
        
    }
    public void refresh(){
        
        DefaultTableModel model=getmodel();
        model.fireTableDataChanged();
        int rowCount = model.getRowCount();
//Remove rows one by one from the end of the table
for (int i = rowCount - 1; i >= 0; i--) {
    model.removeRow(i);
}
        processRead();
    }
    
  
    
}
