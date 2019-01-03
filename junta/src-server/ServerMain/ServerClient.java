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
   private String q[];
   private String r[];
           private int dificuldade;
;  ServerClient(Socket inSocket,int counter){
    serverClient = inSocket;
    clientNo=counter;
      // loadQuestions();
  }
/*public void loadQuestions () {
		
		this.q[0] = "Era uma vez a história de quatro homens: João, José, Jacinto e Joel. Os quatro eram construtores de barcos e em quatro dias conseguiam construir quatro embarcações. Quanto tempo demoraria um dos quatro homens para construir um único barco?\n";
		this.r[0] = "4";
		this.q[1] = "Olá! Eu sei que não sabe quem sou, mas nós somos da mesma família: o meu pai é irmão da sua irmã. Consegue adivinhar que parente sou eu: primo, sobrinho, filho, tio ou genro?\n";
		this.r[1] = "12";
		this.q[2] = "Há um grave problema com o relógio da torre da aldeia: por um motivo ainda não compreendido pela população, o relógio para um minuto a cada dez minutos. Consegue adivinhar quanto tempo demora o ponteiro dos minutos a dar uma volta completa ao relógio?\n";
		this.r[2] = "65";
		this.q[3] = "Quando a Rita chegou a casa na última sexta-feira decidiu tirar uma selfie no espelho da sala para publicar no Facebook. Mas a fotografia tinha uma particularidade: é que a Rita queria deixar uma mensagem oculta às amigas, por isso pôs-se à frente do espelho com um cartaz nas mãos que tinha as seguintes palavras: “MAN TOOT DEED”. Quais destas palavras se lê da mesma forma se lêssemos o cartaz diretamente?";
		this.r[3] = "TOOT";
		this.q[4] = "P: O Bruno morava numa casa para a qual só se podia entrar a partir de uma única porta e de cinco janelas. No dia em que Bruno foi cortar o cabelo, certificou-se de que não havia ninguém no interior da casa e saiu. Quando voltou, de cabelo consideravelmente mais curto e pintado de loiro, encontrou um ladrão dentro de casa. Mas a porta não tinha sido forçada e as janelas estavam intactas. Quando questionado pela polícia, o ladrão jurou a pés juntos que não tinha entrado na casa com recurso a uma chave mestra. Como pode então ter entrado na casa?\n";
		this.r[4] = "Pela porta que Bruno não trancou.";
		
	}*/
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
        if (m.getIdOperation().equals("031")) {
                    this.nick = m.getMsg();
                }else if (m.getIdOperation().equals("032")) {
                    this.pontuacao =0;
			
		}  else if (m.getIdOperation().equals("034")) {
                        this.dificuldade = m.getFlagDifficulty();
                   
		}
                
                else if (m.getIdOperation().equals("036")) {
                    System.out.println("Client quer pergunta");
                     this.mem = q[this.dificuldade];
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

