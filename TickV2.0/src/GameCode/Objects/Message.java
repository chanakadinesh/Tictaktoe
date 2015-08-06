package GameCode.Objects;

import java.awt.event.MouseEvent;
import java.io.Serializable;


public class Message implements Serializable{
	private static final long serialVersionUID = 2L;
	private String state;
	private int message;

	public  Message(String state,int mes) {
		this.state=state;
		message=mes;		
	}

	public int getMassage(){
		return message;
	}

	public String getState(){
		return state;
	}

}
