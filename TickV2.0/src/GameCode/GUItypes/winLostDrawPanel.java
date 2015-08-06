package GameCode.GUItypes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.*;

import org.apache.log4j.Logger;

import GameCode.Objects.GameUmpire.EndState;
import GameCode.Objects.MainClass.state;
import GameCode.Objects.playerManager;

import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;

public class winLostDrawPanel extends JPanel implements Runnable, ActionListener {

	private EndState endState;
	private state currentState;
	private JFrame mainWindow;
	private BufferedImage offImage,lostPic,wonPic,tryBtnPic,exitPic;
	private Graphics2D offScreen;
	private JPanel previousPanel;
	private JButton tryBtn,exitBtn;
	playerManager pm;
	Logger logger= Logger.getLogger(winLostDrawPanel.class);
	public winLostDrawPanel(Dimension d, JFrame mainWindow){

		endState=EndState.win;
		currentState=state.BLOCKED;
		this.setSize(d);
		this.setPreferredSize(d);
		this.mainWindow=mainWindow;
		
		setLayout(null);
		offImage=new BufferedImage(d.width,d.height,BufferedImage.TYPE_INT_RGB );
		offScreen=offImage.createGraphics();
		this.setIgnoreRepaint(true);
		/*//loadImages();
		initComponents();*/
		
	//	nextPanel=new optionPanel(d,mainWindow);
	}
	
	public void setPreviousPanel(JPanel p){
		previousPanel=p;
	}
	
	public void setPlayerManager(playerManager p){
		this.pm=p;
	}

	public void initComponents(){
		tryBtn=new JButton();
		tryBtn.addActionListener(this);
		tryBtn.setBounds(this.getWidth()/2-90,2*this.getHeight()/3,180,40);
		tryBtn.setIcon(new ImageIcon(tryBtnPic.getScaledInstance(180, 40, Image.SCALE_SMOOTH)));
		this.add(tryBtn);
		

		exitBtn=new JButton();
		exitBtn.addActionListener(this);
		exitBtn.setBounds(this.getWidth()/2-90,2*this.getHeight()/3+60,180,40);
		exitBtn.setIcon(new ImageIcon(exitPic.getScaledInstance(180, 40, Image.SCALE_SMOOTH)));
		this.add(exitBtn);
	}
	
	public void drawScreen(){
		
		Graphics2D screen= (Graphics2D) this.getGraphics();
		screen.drawImage(offImage, 0, 0, offImage.getWidth(), offImage.getHeight(),null);
		paintComponents(offScreen);
		//offScreen.clearRect(0, 0, offImage.getWidth(), offImage.getHeight());
		screen.dispose();
	}
	
	@Override
	public void run() {
		loadImages();
		initComponents();
		currentState=state.RUNNING;
		
		if(endState.equals(EndState.win)){
			offScreen.drawImage(wonPic.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH),0,0,null);
		}
		else{offScreen.drawImage(lostPic.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH),0,0,null);}
		while(currentState.equals(state.RUNNING)){
			drawScreen();
		try{Thread.sleep(5);}
		catch(InterruptedException e){System.err.println("sleepInterupted\n"+e);}
		}
		
	}
	
	public void changeEndState(EndState end){
		endState=end;
	}
	
	public void loadImages(){
		try{
			wonPic=ImageIO.read(this.getClass().getResource("/Resources/backgroundWon.gif"));
			lostPic=ImageIO.read(this.getClass().getResource("/Resources/backgroundLost.gif"));
			tryBtnPic=ImageIO.read(this.getClass().getResource("/Resources/tryBtn.gif"));
			exitPic=ImageIO.read(this.getClass().getResource("/Resources/exitBtn.gif"));
			//levelPic=ImageIO.read(new FileInputStream("src/Resources/levelBtn.gif"));
		}catch(IOException e){System.err.print("Faild\n"+e);}
		//offScreen.drawImage(backGround.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH),0,0,null);
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource()==exitBtn){
			JPanel p=new startPanel(getPreferredSize(), mainWindow);
			mainWindow.add(p);
			new Thread((Runnable)p).start();
			
			currentState=state.BLOCKED;
			this.getGraphics().dispose();
			this.disable();
			this.setVisible(false);
			mainWindow.remove(this);
		}
		else if(arg0.getSource()==tryBtn){
			
			pm.updatePlayArea();
			mainWindow.add(previousPanel);
			previousPanel.setVisible(true);
			previousPanel.enable();
			
			new Thread((Runnable)previousPanel).start();
			
			
			currentState=state.BLOCKED;
			//this.getGraphics().dispose();
			this.disable();
			this.setVisible(false);
			mainWindow.remove(this);
		}
		
	}

}
