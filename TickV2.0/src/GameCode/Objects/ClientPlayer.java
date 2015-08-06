package GameCode.Objects;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import org.apache.log4j.Logger;

public class ClientPlayer extends Player implements Runnable{
	
	private String IP;
	private int port;
	private Message m;
	private boolean WaitForClick;
	protected int state;
	Logger logger= Logger.getLogger(ClientPlayer.class);
	public ClientPlayer(int[] clicks, int turn,String ip,int port) {
		super(clicks, turn);
		IP=ip;
		this.port=port;
		m=null;
		WaitForClick=true;
		state=0;
	}
	public void playerClick(int i) {
		try{
		if(clicks[i]==0 && getActive()){
			clicks[i]=turn;
			setActive(false);
			oppsite.notify(i);
			setMassage(i);
			changeState(false);
		}}catch(ArrayIndexOutOfBoundsException e){
			logger.debug(turn+" Player clicked out of bound");
		}
		
	}
	private void setMassage(int i) {
		m = new Message(Integer.toString(turn),i);
	}
	private void changeState(boolean b) {
		WaitForClick=b;
		
	}
	public void run(){
		
		Socket socket;

		ObjectInputStream oReader;
		ObjectOutputStream oWriter;
		try{
			
			socket = new Socket(IP,port);
			
			oWriter= new ObjectOutputStream(socket.getOutputStream());
			oWriter.flush();
			oReader= new ObjectInputStream(socket.getInputStream());

			logger.info("Connection established...!\n");
			state=1;
		}catch(Exception ex){
			logger.error("Connenction failed");
			state=-1;
			return;
		}
		
		
		while(true ){
			try{
				
				while(WaitForClick);
				
				oWriter.writeObject(m);
				oWriter.flush();
				changeState(true);
				
				 m= (Message)oReader.readObject();
				 oppsite.playerClick(m.getMassage());
				//System.out.println("Server -> " + string );
				
			}catch(IOException ex){
				logger.error("Connection error");
				state=-1;
				return;
			}
			catch (ClassNotFoundException e) {
				logger.error("Class error");
			}catch (NullPointerException e) {
				logger.error("Reading error");
			}
			
			
		}
	}
	
}

