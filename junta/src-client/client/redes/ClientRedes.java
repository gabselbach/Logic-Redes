/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.redes;

/**
 *
 * @author gabriella
 */

import com.sun.org.apache.xpath.internal.operations.Bool;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import util.Message;
 
import java.net.*;
import java.io.*;
import static java.lang.System.exit;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
public class ClientRedes {
    private Socket socket;
    private ObjectOutputStream output = null;
    private  ObjectInputStream   input = null;
    private String pergunta;
    private String nick;
       Scanner ler = new Scanner(System.in);
    public ClientRedes(){}
  public void roda() throws IOException, ClassNotFoundException{
      String codigo;
        
       
          output = new ObjectOutputStream(socket.getOutputStream());
          input = new  ObjectInputStream(socket.getInputStream()); 
        Message m = (Message) input.readObject();
           if(m.getIdOperation().equals("001")){
                 System.out.println("\nConexão feita \n");
           }
       while(true){
         System.out.println("\nAções do Sistema:  ");
         System.out.println("031 - digitar seu nick ");
         System.out.println("032 - recomeçar o jogo ");
          System.out.println("036 - receber pergunta  ");
           System.out.println("038 - pular pergunta ");
           
             System.out.println("042 - sair ");
            
            try{
                 System.out.println("digite a ação");
           codigo = ler.nextLine();
               
               if (codigo.equals("031")) {
                        clientRequestNick();
                        
                }else if (codigo.equals("032")) {
                        clientRequestRefresh();
			
		} else if (codigo.equals("036")) {
                    clientRequestQuestion();
			
		
			
		} else if (codigo.equals("037")) {
			
			
		} else if (codigo.equals("038")) {
			clientRequestPush();
					
			
		} else if (codigo.equals("039")) {
                    
		} else if (codigo.equals("042")) {
			clientRequestExit();
                         output.close();
                        socket.close();
                        break;
			
		} else {
			
			System.out.println("Operação desconhecida");
			
		}	
            }catch(Exception e){
              System.out.println(e);
            }
      }  
  }
  public void clientRequestRefresh() throws IOException{
      Message m = new Message(); 
         m.setIdOperation("032");       
         output.writeObject(m);
            output.flush();

  }
        	public void clientRequestExit () throws IOException, ClassNotFoundException {
        Message m = new Message(); 
         m.setIdOperation("042");       
         output.writeObject(m);
            output.flush();
             m = (Message) input.readObject();
              System.out.println("\nSua pontuacao  " + this.nick + " : "+ m.getPontuacao());
        };
  	public void clientRequestQuestion () throws IOException, ClassNotFoundException {
            Message m = new Message();
          m.setIdOperation("036");
          output.writeObject(m);
            output.flush();
        
           m = (Message) input.readObject();
           if(m.getIdOperation().equals("010")){
                this.pergunta = m.getMsg();
                System.out.println("Pergunta : "+this.pergunta);
                clientResponde();
                
                
           }
        };
        	
        public void clientResponde() throws IOException, ClassNotFoundException{    
              System.out.print("Resposta :");
             Scanner ler = new Scanner(System.in);
          String r = ler.nextLine();
          if(r.equals("038"))
              clientRequestPush();
          else
            clientRequestCorrection(r); 
            
        }
        public void clientRequestPush() throws IOException, ClassNotFoundException{
               Message m = new Message();
          m.setIdOperation("038");
          output.writeObject(m);
            output.flush();
             m = (Message) input.readObject();
           if(m.getIdOperation().equals("010")){
                this.pergunta = m.getMsg();
                System.out.println("Pergunta : "+this.pergunta);
                clientResponde();
                
                
           }
            
        }
	public void clientRequestCorrection (String resposta) throws IOException, ClassNotFoundException {
        Message m = new Message(); 
         m.setIdOperation("037");
         switch(resposta){
             case "A":
                m.setFlagAnswer(1);
             break;
             case "B":
                m.setFlagAnswer(2);
             break;
             case "C":
                 m.setFlagAnswer(3);
             break;
             case "D":
                 m.setFlagAnswer(4);
             break;
             case "E":
                 m.setFlagAnswer(5);
             break;
             default:
                 m.setFlagAnswer(0);
                 m.setMsg(resposta);
             break;  
         }
         output.writeObject(m);
            output.flush();
             m = (Message) input.readObject();
             if(m.getFlagCorrection()==2){
                 System.out.println("\nresposta correta");
             }else{
                 System.out.println("\nresposta errada");
             }
            
        }
  	public void clientRequestNick () throws IOException {
            System.out.println("digite nick");
              Scanner ler = new Scanner(System.in);
          this.nick = ler.nextLine();
            Message m = new Message(); 
            m.setIdOperation("031");
            m.setMsg(nick);
            output.writeObject(m);
            output.flush();
			
	};
  public static void main(String[] args) throws Exception {
     ClientRedes c = new ClientRedes();
     c.roda();
}
}