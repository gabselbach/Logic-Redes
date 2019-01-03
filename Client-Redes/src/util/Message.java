package util;

import java.io.Serializable;

public class Message implements Serializable{
     private static final long serialVersionUID = 1L;
    private String operacao;
    private String idOperation = new String("000");
	private int flagError = 0;
	private int flagDifficulty = 0;
	private int flagCorrection = 0;
	private int flagAnswer = 0;
        private String msg = new String();
        private int pontuacao;
        private String nick = new String("");
    
    /* 
    chave : Object
    */
    ;
    public Message(){
        
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }
 
    public Message(String operacao)
    {
       this.operacao = operacao;
       
    }
     public int getPontuacao() {
		return this.pontuacao;
	}

	public void setPontuacao(int p) {
		this.pontuacao = p;
	}
	public String getIdOperation() {
		return idOperation;
	}

	public void setIdOperation(String idOperation) {
		this.idOperation = idOperation;
	}
        
	public int getFlagError() {
		return flagError;
	}

	public void setFlagError(int flagError) {
		this.flagError = flagError;
	}

	public int getFlagDifficulty() {
		return flagDifficulty;
	}

	public void setFlagDifficulty(int flagDifficulty) {
		this.flagDifficulty = flagDifficulty;
	}

	public int getFlagCorrection() {
		return flagCorrection;
	}

	public void setFlagCorrection(int flagCorrection) {
		this.flagCorrection = flagCorrection;
	}
        
	public int getFlagAnswer() {
		return flagAnswer;
	}

	public void setFlagAnswer(int flagAnswer) {
		this.flagAnswer = flagAnswer;
	}
        	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

    public String getOperacao()
    {
        return operacao;
    }
    
      
    @Override
    public String toString()
    {
        String m = "Operacao: "+ operacao;
       
       
        return m;
    }

    public void getIdOperation(String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
