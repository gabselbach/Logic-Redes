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
  private int i;
  public  Message m;
 public ObjectOutputStream output = null;
       public  ObjectInputStream   input = null;
       private String mem;
   private String resposta="branco";
   private String nick;
   private String q[]= new String[10];
   private String r[] = new String[10];
           private int dificuldade;
  ServerClient(Socket inSocket,int counter){
    serverClient = inSocket;
    clientNo=counter;
      
  }
  
public void loadQuestions () {
		
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
                
                this.q[5]=" A senhora Adelaide tem um galinheiro muito grande e agora pondera começar a vender os ovos na aldeia onde vive. Precisa então de fazer contas à vida e quer saber quantos ovos terá para vender. Ela sabe que uma galinha e meia põem um ovo e meio num dia e meio. Quantos ovos deverão por sete galinhas em seis dias?";
                this.r[5]="28";
                this.q[6] = "Olá! Eu sei que não sabe quem sou, mas nós somos da mesma família: o meu pai é irmão da sua irmã. Consegue adivinhar que parente sou eu: primo, sobrinho, filho, tio ou genro?";
                this.r[6]= "sobrinhp";
                this.q[7] = "Na escola primária da cidade, a professora Alice tem um quebra-cabeças para os meninos do terceiro ano. Para fazer uma pausa das contas de multiplicação, ela distribuiu uma lista com seis palavras: Agate, Agitate, Gates, Stags, Stage, Grate. Depois lançou-lhes um desafio: quais destas palavras são compostas pelas mesmas letras?";
                this.r[7]= "gates e stage";
                this.q[8] ="Três cavalos vão participar na corrida anual da vila. Os cavalos chamam-se Tufão, Trovão e Tornado e os seus proprietários são o senhor Luís, o senhor Baltasar e o senhor Mateus, embora não necessariamente por esta ordem. O Tufão partiu a pata logo no início da corrida e infelizmente teve de desistir da competição. O senhor Mateus tinha um cavalo castanho e branco com três anos. Trovão tinha ganho 20 mil euros. O senhor Baltasar já tinha perdido muito, mas o cavalo esteve muito perto de vencer. O cavalo vencedor desta corrida era preto. Esta foi a primeira corrida onde o cavalo do senhor Luís participou. Como se chama o cavalo que ganhou?";
                this.r[8]= "tornado";
                 this.q[9] = "Toda a gente reparou nas trocas de olhares daquele triângulo amoroso no meio do bar. O Tomás não parava de olhar para a Catarina; e a Catarina não parava de olhar para o Rui. Ora, acontece que Tomás era casado, mas o Rui não. Descubra se há alguma pessoa casada a olhar para uma pessoa não casada neste triângulo amoroso.";
                this.r[9]= "sim";
                	
	}
  public void run(){
       loadQuestions();
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
                    i=0;
			
		}  else if (m.getIdOperation().equals("034")) {
                        this.dificuldade = m.getFlagDifficulty();
                   
		}
                
                else if (m.getIdOperation().equals("036")) {
                    System.out.println("Client quer pergunta");
                    
                     this.mem = q[i];
                   serverQuestionSend();
			 
		
			
		} else if (m.getIdOperation().equals("037")) {
			serverCorrectionSend(m);
			
		} else if (m.getIdOperation().equals("038")) {
                    this.i = this.i+1;
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
      if(m.getMsg().equals(r[i])){
           this.pontuacao++;
               msg.setFlagCorrection(2);
     }else{
         msg.setFlagCorrection(1);
      }
      output.writeObject(msg);
      output.flush();
      i++;
  }
  	public void serverQuestionSend() throws IOException {
		Message msg = new Message();
		msg.setFlagAnswer(1);	
		msg.setMsg(this.q[i]);
		msg.setIdOperation("010");
		output.writeObject(msg);
                    output.flush();
	}
}

