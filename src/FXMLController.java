/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * FXML Controller class
 *
 * @author 98902
 */
public class FXMLController implements Initializable {

    
    File instructMem;
    File dataMem;
    
    
    /**
     * Initializes the controller class.
     */
    @FXML
    private TextArea instructTextArea;
    @FXML
    private TextArea dataMemId;
    
    @FXML
    private Label pclbl;
    @FXML
    private Label aclbl;
    @FXML
    private Label tlbl;
    
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        pclbl.setText("0000");
    }    
    
    
    
    
      @FXML
    private void dataMem(ActionEvent event) {
          JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "JPG & GIF Images", "jpg", "gif");
        chooser.setFileFilter(filter);
       
        int returnVal = chooser.showOpenDialog(null);
        dataMem = chooser.getSelectedFile();
        
         try{
        BufferedReader r = new BufferedReader(new FileReader(dataMem));
        
        String line;
        StringBuilder content = new StringBuilder();
        
        while((line = r.readLine()) != null){
            content.append(line).append("\n");
        }
        
        dataMemId.setText(content.toString());
        dataMemId.setEditable(false);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    
    
    
    
       @FXML
    private void instructionMem(ActionEvent event) throws FileNotFoundException {
          JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "JPG & GIF Images", "jpg", "gif");
        chooser.setFileFilter(filter);
       
        int returnVal = chooser.showOpenDialog(null);
        instructMem = chooser.getSelectedFile();
        
        try{
        BufferedReader r = new BufferedReader(new FileReader(instructMem));
        
        String line;
        StringBuilder content = new StringBuilder();
        
        while((line = r.readLine()) != null){
            content.append(line).append("\n");
        }
        
        instructTextArea.setText(content.toString());
        instructTextArea.setEditable(false);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    
    
     int u=0;
    
       @FXML
    private void reset(ActionEvent event) {
        pclbl.setText("0000");
        aclbl.setText("0000000000000000");
        tlbl.setText("0000000000000000");
        u=0;
    }
    
    
    
    
   
    BufferedReader r;
    execute e;
    String line2;
    
       @FXML
    private void lineRun(ActionEvent event) throws FileNotFoundException, IOException {
        if(u==0){
          r = new BufferedReader(new FileReader(instructMem));
          e = new execute(instructMem ,dataMem ,pclbl , aclbl , tlbl ,  dataMemId );
          u=1;
        }
         String line;
         String pclbls;
         if(u==1){
             while((line = r.readLine()) != null){
                 line = line.replaceAll("		", "");
                 line = line.replaceAll(" ", "");
                 pclbls = pclbl.getText();
                 line = line.substring(4);
                 e.execode(line,pclbls );
                 if(pclbls.compareTo(pclbl.getText()) != 0){
                     break;
                 }
                 
             }
         }
      
    }
    
    
    
    
    
    
       @FXML
    private void run(ActionEvent event) {
        if(instructMem !=null && dataMem !=null){
            try {
                execute e = new execute(instructMem ,dataMem ,pclbl , aclbl , tlbl , dataMemId );
                e.perfectrun();
            } catch (IOException ex) {
                Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else{
            System.out.println("plz chose file");
        }
    }

    
}
