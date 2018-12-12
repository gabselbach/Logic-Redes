/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerMain;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.Message;

 
public class ServerClient extends Thread {
    private int pontuacao =0;
  Socket serverClient;
  int clientNo;
  int squre;
  int j=1;
  public  Message m;
 public ObjectOutputStream output = null;
       public  ObjectInputStream   input = null;
       private String mem;
   private String resposta="branco";
   private String nick;
  ServerClient(Socket inSocket,int counter){
    serverClient = inSocket;
    clientNo=counter;
  }
  public void run(){
      try {
          output = new ObjectOutputStream(serverClient.getOutputStream());
            input = new ObjectInputStream(serverClient.getInputStream());
      } catch (IOException ex) {
          Logger.getLogger(ServerClient.class.getName()).log(Level.SEVERE, null, ex);
      }
      
      try {
               Message msg = new Message();
          msg.setIdOperation("001");
           output.writeObject(msg);
            output.flush();
       while(serverClient!=null){
      
           
  
     
     m =(Message) input.readObject();
     System.out.println(m.getIdOperation()  );
       if (m.getIdOperation().equals("033")) {
          
             //     clientConnection();
                   
		} else if (m.getIdOperation().equals("031")) {
                    this.nick = m.getMsg();
                }else if (m.getIdOperation().equals("032")) {
                    this.pontuacao =0;
			
		} else if (m.getIdOperation().equals("036")) {
                    System.out.println("Client quer pergunta");
                     this.mem = "Qual a cor do cavalo branco de napoelao";
                   serverQuestionSend();
			 
		
			
		} else if (m.getIdOperation().equals("037")) {
			serverCorrectionSend(m);
			
		} else if (m.getIdOperation().equals("038")) {
			this.mem = "Que cor é a flor de amelia";
                        this.resposta = "rosa";
                         serverQuestionSend();
					
			
		} else if (m.getIdOperation().equals("039")) {
                    
		} else if (m.getIdOperation().equals("042")) {
                    serverScore();
                     serverClient.close();
                     output.close();
                     input.close();
			
		} else {
			
			System.out.println("Operação desconhecida");
			
		}	
      }
    }catch(Exception ex){
      System.out.println(ex);
    }finally{
      System.out.println("Client -" + clientNo + " exit!! ");
    }
      
  
      
}
  public void serverScore() throws IOException{
        Message msg = new Message();
          msg.setIdOperation("014");
          msg.setPontuacao(this.pontuacao);
           output.writeObject(msg);
            output.flush();
  }
  public void serverCorrectionSend(Message m) throws IOException{
      Message msg = new Message();
          msg.setIdOperation("011");
      if(m.getMsg().equals(this.resposta)){
           this.pontuacao++;
               msg.setFlagCorrection(2);
     }else{
         msg.setFlagCorrection(1);
      }
      output.writeObject(msg);
      output.flush();
  }
  	public void serverQuestionSend() throws IOException {
		Message msg = new Message();
		msg.setFlagAnswer(1);	
		msg.setMsg(this.mem);
		msg.setIdOperation("010");
		output.writeObject(msg);
                    output.flush();
	}
}

