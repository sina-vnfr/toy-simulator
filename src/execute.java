
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author 98902
 */
public class execute {
    
    private File instructs;
    private File Data;
    private Label pclbl;
    private Label aclbl;
    private Label tlbl;
    private TextArea dataMemId;
    //pc
    private int pc=0;
    private String pcs="0000";
    
    //A
    private String As="0000000000000000";
    private int A=0;
    
    //T
    private String Ts="0000000000000000";
    
    //C
    private int C=0;
    
    //Z
    private int Z=1;
    
    private String address="0";
    int i=0;
    private String dataaddress="0";
    
  
    
    public execute(File instructs , File Data  , Label pclbl , Label aclbl , Label tlbl ,  TextArea dataMemId){
        this.instructs = instructs;
        this.Data = Data;
        this.aclbl = aclbl;
        this.pclbl = pclbl;
        this.tlbl = tlbl;
        this.dataMemId = dataMemId;
    }
    
    public void perfectrun() throws FileNotFoundException, IOException{
        
        BufferedReader r = new BufferedReader(new FileReader(instructs));
        String line;
      
        
        while(i<2){
            
            
            pc =0;
             pcs = Integer.toHexString(pc);
               while(pcs.length() < 4){
                   pcs = "0" + pcs;
               }
               
               
            while((line = r.readLine()) != null){
                address = line.substring(0, 4);
                line = line.substring(4);
                line = line.replaceAll("		", "");
                line = line.replaceAll(" ", "");
                
               
                execode(line, address);
            }
            i++;
        }
    }
    
    public void execode(String line , String address) throws IOException{
        String inst = line.substring(0, 4);
        line = line.substring(4);
        
     
           int binary12 = Integer.parseInt(line,2);
            dataaddress = Integer.toHexString(binary12);
             while(dataaddress.length() < 4){
                   dataaddress = "0" + dataaddress;
               }
     
        // JMP
        if(inst.contains("0000") && (address.compareTo(pcs))==0){
           
             
            int binary =Integer.parseInt(line,2);
            pc = binary;
             pcs =Integer.toHexString(binary);
             while(pcs.length() < 4){
                   pcs = "0" + pcs;
               }
                aclbl.setText(As);
                pclbl.setText(pcs);
                tlbl.setText(Ts);
               System.out.println("jmp is execute");
            i=0;
        }
        
        // ADC
        if(inst.contains("0001")&& (address.compareTo(pcs))==0){
            
             String op1s = datamem(Data,dataaddress,"0",0);
             int op1 = Integer.parseInt(op1s, 2);
             A = A + op1;
             As = Integer.toBinaryString(A);
              while(As.length() < 16){
                   As = "0" + As;
               }
            System.out.println("adc is execute");
              
               pc = pc + 1;
             pcs = Integer.toHexString(pc);
               while(pcs.length() < 4){
                   pcs = "0" + pcs;
               }
                aclbl.setText(As);
                pclbl.setText(pcs);
                tlbl.setText(Ts);
                i=0;
        }
        
         // XOR
        if(inst.contains("0010") && (address.compareTo(pcs))==0){
             
            System.out.println("xor is exe");
             String op1s = datamem(Data,dataaddress,"0",0);
             int op1 = Integer.parseInt(op1s, 2);
             A = A ^ op1;
             As = Integer.toBinaryString(A);
              while(As.length() < 16){
                   As = "0" + As;
               }
              if(As.length() > 16){
                  char Cs=As.charAt(16);
                  C = Cs - '0';
              }
            
                pc = pc + 1;
             pcs = Integer.toHexString(pc);
               while(pcs.length() < 4){
                   pcs = "0" + pcs;
               }
                aclbl.setText(As);
                pclbl.setText(pcs);
                tlbl.setText(Ts);
  
                i=0;
        }
        
        // SBC
        if(inst.contains("0011") && (address.compareTo(pcs))==0){
             
             String op1s = datamem(Data,dataaddress,"0",0);
             int op1 = Integer.parseInt(op1s, 2);
             A = A - op1 - C;
             Z=A;
             As = Integer.toBinaryString(A);
              while(As.length() < 16){
                   As = "0" + As;
               }
            
            
            
                pc = pc + 1;
             pcs = Integer.toHexString(pc);
               while(pcs.length() < 4){
                   pcs = "0" + pcs;
               }
                aclbl.setText(As);
                pclbl.setText(pcs);
                tlbl.setText(Ts);
  
                i=0;
        }
        
        // ROR
        if(inst.contains("0100") && (address.compareTo(pcs))==0){
            String lastbit = As.substring(As.length() - 1);
            As = As.substring(0,As.length() - 1);
            As = "0" + As;
             while(As.length() < 16){
                   As = "0" + As;
               }
            C =Integer.parseInt(lastbit);
          
            
                pc = pc + 1;
             pcs = Integer.toHexString(pc);
               while(pcs.length() < 4){
                   pcs = "0" + pcs;
               }
                aclbl.setText(As);
                pclbl.setText(pcs);
                tlbl.setText(Ts);
  
                i=0;
        }
        
        // TAT
        if(inst.contains("0101") && (address.compareTo(pcs))==0){
             
            Ts = As ;
            
                pc = pc + 1;
             pcs = Integer.toHexString(pc);
               while(pcs.length() < 4){
                   pcs = "0" + pcs;
               }
                aclbl.setText(As);
                pclbl.setText(pcs);
                tlbl.setText(Ts);
  
                i=0;
        }
        
        // OR
        if(inst.contains("0110") && (address.compareTo(pcs))==0){
             System.out.println("or is execute");
             String op1s = datamem(Data,dataaddress,"0",0);
             int op1 = Integer.parseInt(op1s, 2);
             A = A | op1;
             As = Integer.toBinaryString(A);
              while(As.length() < 16){
                   As = "0" + As;
               }
            
            
                pc = pc + 1;
             pcs = Integer.toHexString(pc);
               while(pcs.length() < 4){
                   pcs = "0" + pcs;
               }
               aclbl.setText(As);
                pclbl.setText(pcs);
                tlbl.setText(Ts);
  
                i=0;
        }
        
        // AND
        if(inst.contains("1000") && (address.compareTo(pcs))==0){
             
            System.out.println("and is execute");
             String op1s = datamem(Data,dataaddress,"0",0);
             int op1 = Integer.parseInt(op1s, 2);
             A = A & op1;
             As = Integer.toBinaryString(A);
              while(As.length() < 16){
                   As = "0" + As;
               }
            
            
                pc = pc + 1;
             pcs = Integer.toHexString(pc);
               while(pcs.length() < 4){
                   pcs = "0" + pcs;
               }
               aclbl.setText(As);
                pclbl.setText(pcs);
                tlbl.setText(Ts);
  
                i=0;
        }
        
        // LDC
        if(inst.contains("1001") && (address.compareTo(pcs))==0){
             
             String op1s = datamem(Data,dataaddress,"0",0);
             int op1 = Integer.parseInt(op1s, 2);
             A = op1;
             As = Integer.toBinaryString(A);
              while(As.length() < 16){
                   As = "0" + As;
               }
            
             C = 0;
             
                pc = pc + 1;
             pcs = Integer.toHexString(pc);
               while(pcs.length() < 4){
                   pcs = "0" + pcs;
               }
               aclbl.setText(As);
                pclbl.setText(pcs);
                tlbl.setText(Ts);
  
                i=0;
        }
        
        // BCC
        if(inst.contains("1010") && (address.compareTo(pcs))==0){
            
            if(C==0){
             String op1s = datamem(Data,dataaddress,"0",0);
             pc = Integer.parseInt(op1s, 2);
             pcs = Integer.toHexString(pc);
              while(pcs.length() < 16){
                   pcs = "0" + pcs;
               }
            }else{
                 pc = pc + 1;
                 pcs = Integer.toHexString(pc);
                 while(pcs.length() < 4){
                 pcs = "0" + pcs;
               }
            }
            
            
            aclbl.setText(As);
                pclbl.setText(pcs);
                tlbl.setText(Ts);
  
             i=0;
        }
        
        // BNE
        if(inst.contains("1011") && (address.compareTo(pcs))==0){
             
             if(Z!=0){
             String op1s = datamem(Data,dataaddress,"0",0);
             pc = Integer.parseInt(op1s, 2);
             pcs = Integer.toHexString(pc);
              while(pcs.length() < 16){
                   pcs = "0" + pcs;
               }
            }else{
                 pc = pc + 1;
                 pcs = Integer.toHexString(pc);
                 while(pcs.length() < 4){
                 pcs = "0" + pcs;
               }
            }
            
            aclbl.setText(As);
                pclbl.setText(pcs);
                tlbl.setText(Ts);
  
             i=0;
        }
        
        // LDI
        if(inst.contains("1100") && (address.compareTo(pcs))==0){
             
            String dd = Integer.toHexString(A);
              while(dd.length() < 4){
                 dd = "0" + dd;
               }
             String op1s = datamem(Data,dd,"0",0);
             int op1 = Integer.parseInt(op1s, 2);
             A = op1;
             As = Integer.toBinaryString(A);
              while(As.length() < 16){
                   As = "0" + As;
               }
            
            
                pc = pc + 1;
             pcs = Integer.toHexString(pc);
               while(pcs.length() < 4){
                   pcs = "0" + pcs;
               }
               aclbl.setText(As);
                pclbl.setText(pcs);
                tlbl.setText(Ts);
  
                i=0;
        }
        
        // STT
        if(inst.contains("1101") && (address.compareTo(pcs))==0){
             
            
             String dd = Integer.toHexString(A);
              while(dd.length() < 4){
                 dd = "0" + dd;
               }
              datamem(Data,dd,Ts,1);
            
                pc = pc + 1;
             pcs = Integer.toHexString(pc);
               while(pcs.length() < 4){
                   pcs = "0" + pcs;
               }
               aclbl.setText(As);
                pclbl.setText(pcs);
                tlbl.setText(Ts);
  
                i=0;
        }
        
        // LDA
        if(inst.contains("1110") && (address.compareTo(pcs))==0){
             
             String op1s = datamem(Data,dataaddress,"0",0);
             int op1 = Integer.parseInt(op1s, 2);
             A = op1;
             As = Integer.toBinaryString(A);
              while(As.length() < 16){
                   As = "0" + As;
               }
            
                pc = pc + 1;
             pcs = Integer.toHexString(pc);
               while(pcs.length() < 4){
                   pcs = "0" + pcs;
               }
               aclbl.setText(As);
                pclbl.setText(pcs);
                tlbl.setText(Ts);
  
                i=0;
        }
        
        // STA
        if(inst.contains("1111") && (address.compareTo(pcs))==0){
             
              String dd = Integer.toHexString(A);
              while(dd.length() < 4){
                 dd = "0" + dd;
               }
              datamem(Data,dataaddress,As,1);
            
                pc = pc + 1;
             pcs = Integer.toHexString(pc);
               while(pcs.length() < 4){
                   pcs = "0" + pcs;
               }
               aclbl.setText(As);
                pclbl.setText(pcs);
                tlbl.setText(Ts);
  
                i=0;
        }
        
    }
    
    public String datamem(File Data , String address , String data , int wr) throws FileNotFoundException, IOException{
        
         BufferedReader r = new BufferedReader(new FileReader(Data));
         BufferedReader r2 = new BufferedReader(new FileReader(Data));
         String line;
         StringBuilder sb = new StringBuilder();
        //read from Data file
        if(wr == 0){
            
             while ((line = r.readLine()) != null){
                  line = line.replaceAll("      ", "");
                  line = line.replaceAll("		", "");
                  String inst = line.substring(0, 4);
                  if((inst.compareTo(address)) ==0){
                   line = line.substring(4);
                   return line;
                  }
              }
            
        }else{
        //write to Data file    
       
            FileWriter fw = new FileWriter(Data.toPath().toFile() , true);
            //FileWriter fw2 = new FileWriter(Data.toPath().toFile());
             System.out.println("rrrrr");
            BufferedWriter br = new BufferedWriter(fw); 
              while ((line = r.readLine()) != null){
                  line = line.replaceAll(" ", "");
                  line = line.replaceAll("  ", "");
                  String inst = line.substring(0, 4);
                   System.out.println("hhhhh");
                  if((inst.compareTo(address)) ==0){
                       System.out.println("ggggg");
                       
                      while((line = r2.readLine()) != null){
                           String line2 = line.replaceAll(" ", "");
                           line2 = line2.replaceAll("  ", "");
                           String inst1 = line2.substring(0, 4);
                           System.out.println("fffff");
                          if((inst1.compareTo(address)) !=0){
                              sb.append(line);
                              sb.append(System.lineSeparator());
                              
                              System.out.println(sb.toString());
                          }
                      }
                      FileWriter fw2 = new FileWriter(Data.toPath().toFile());
                      fw2.write(sb.toString());
                      fw2.close();
                    br.write(address + "\t\t" + data);
                    br.newLine();
                    br.close();
                    dataMemId.setText(sb.toString()+address+"\t\t"+ data);
                    return "data saved";
                   
                  }
              }
             br.write("\n"+address + "\t\t" + data);
             br.newLine();
             br.close();
             System.out.println("aaaaa");
             StringBuilder content = new StringBuilder();
             while((line = r2.readLine()) != null){
                content.append(line).append("\n");
              }
            dataMemId.setText(content.toString());
            return "data saved";
        }
        return null;
    }
    
}
