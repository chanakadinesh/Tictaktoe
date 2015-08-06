package GameCode.Objects;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;

public class ServerPlayer extends Player implements Runnable{
	public static Socket socket;
	private int port;
	private boolean waitForInput;
	private Message m;
	protected int state;
	Logger logger= Logger.getLogger(ServerPlayer.class);
	
	public ServerPlayer(int[] clicks, int turn,int port) {
		super(clicks, turn);
		this.port=port;
		waitForInput=true;
		m=null;
		state=0;
	}
	public ServerPlayer(int[] clicks, int turn) {
		super(clicks, turn);
		this.port=3128;
		waitForInput=true;
		m=null;
	}
	
	public void changeState(boolean st){
		this.waitForInput=st;
	}
	
	public void setMassage(String s,int i){
		m=new Message(s,i);
	} 
	
	public void playerClick(int i) {
		try{
		if(clicks[i]==0 && getActive()){
			clicks[i]=turn;
			setActive(false);
			oppsite.notify(i);
			setMassage(Integer.toString(turn), i);
			changeState(false);
		}}catch(ArrayIndexOutOfBoundsException e){
			logger.debug(turn+" Player clicked out of bound");
		}
		
	}
	
	public void run(){
		try{
		ServerSocket severSocket=new ServerSocket(port);
		logger.info("Server Started");
		ObjectInputStream oReader;
		ObjectOutputStream oWriter;
		
		socket=severSocket.accept();
		oWriter=new ObjectOutputStream(socket.getOutputStream());
		oWriter.flush();
		oReader=new ObjectInputStream(socket.getInputStream());
		
		logger.info("Client connected");
		state=1;
		while(true){
			try{
				m=(Message)oReader.readObject();
				oppsite.playerClick(m.getMassage());	
			}catch(IOException e){logger.error("Connection error");}
			catch (NullPointerException e) {
				logger.error("REadign error");}
			m=null;
			while(waitForInput);
			try{
				oWriter.writeObject(m);
				oWriter.flush();
				changeState(true);
			}catch(IOException ex){logger.error("Wrrting error");}
			
		}
		}catch(Exception e){
			logger.error("Server error");
			state=-1;
			
		}finally{
			try{socket.close();}
			catch(Exception e){}
		}
		
	}
	
	
}
