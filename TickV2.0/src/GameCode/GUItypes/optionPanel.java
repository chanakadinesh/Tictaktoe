package GameCode.GUItypes;

import javax.imageio.ImageIO;
import javax.swing.*;

import GameCode.Objects.AutoPlayer.level;
import GameCode.Objects.playerManager;
import GameCode.Objects.MainClass.state;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.*;
import java.io.*;

public class optionPanel extends JPanel implements Runnable,ActionListener{


	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	private Graphics2D offScreen;
	private BufferedImage offImage,backGround,playPic,scorePic,levelPic,backPic;
	private JFrame mainPanel;
	private JPanel nextPanel,previousPanel;
	private JButton playBtn,scoreBtn,backBtn,levelBtn;
	private state currentState;
	private playerManager pm;
	private JComboBox levelBox;

	public optionPanel(Dimension d,JFrame mainPanel){
	
		currentState=state.BLOCKED;
		this.setSize(d);
		this.setPreferredSize(d);
		this.mainPanel=mainPanel;
		setLayout(null);
		offImage=new BufferedImage(d.width,d.height,BufferedImage.TYPE_INT_RGB );
		offScreen=offImage.createGraphics();
		this.setIgnoreRepaint(true);
		loadImages();
		initComonents();

		nextPanel=new playPanel(d,mainPanel);
	}

	public void setNextPanel(JPanel jp){
		nextPanel=jp;
	}

	private void initComonents() {

		playBtn=new JButton();
		scoreBtn=new JButton();
		levelBtn=new JButton();
		backBtn=new JButton();

		playBtn.addActionListener(this);
		scoreBtn.addActionListener(this);
		levelBtn.addActionListener(this);
		backBtn.addActionListener(this);

		playBtn.setBounds(this.getWidth()/2-90,2*this.getHeight()/5,180,40);
		scoreBtn.setBounds(this.getWidth()/2-90,2*this.getHeight()/5+50,180,40);
		levelBtn.setBounds(this.getWidth()/2-90,2*this.getHeight()/5+100,180,40);
		backBtn.setBounds(this.getWidth()/2-90,2*this.getHeight()/5+150,180,40);

		playBtn.setIcon(new ImageIcon(playPic));
		scoreBtn.setIcon(new ImageIcon(scorePic));
		//levelBtn.setIcon(new ImageIcon(levelPic));
		backBtn.setIcon(new ImageIcon(backPic));

		String[] levelName = { "EASY", "MEDIUM", "HARD"};

		//Create the combo box, select item at index 3.
		levelBox = new JComboBox(levelName);
		levelBox.setSelectedIndex(1);
		Font f2 = new Font("Harrington", Font.BOLD, 30);
		levelBox.setFont(f2);
		levelBox.setAlignmentX(JComboBox.CENTER_ALIGNMENT);
		levelBox.setBackground(Color.black);
		levelBox.setForeground(Color.yellow);
		levelBox.setBounds(this.getWidth()/2-90,2*this.getHeight()/5+100,180,40);
		levelBox.addActionListener(this);
		
		
		//levelBtn.disable();
		this.add(playBtn);
		this.add(scoreBtn);
		//this.add(levelBtn);
		this.add(backBtn);
		this.add(levelBox);

	}

	public void loadImages(){
		try{
			backGround=ImageIO.read(this.getClass().getResource("/Resources/background.jpg"));
			playPic=ImageIO.read(this.getClass().getResource("/Resources/playBtn.gif"));
			scorePic=ImageIO.read(this.getClass().getResource("/Resources/scoreBtn.gif"));
			backPic=ImageIO.read(this.getClass().getResource("/Resources/backBtn.gif"));
			
		}catch(IOException e){System.err.print("Faild\n"+e);}
		
		offScreen.drawImage(backGround.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH),0,0,null);
		//offScreen.drawImage(backGround,150,50,null);
	}

	public void drawScreen(){
		paintComponents(offScreen);
		Graphics2D screen= (Graphics2D) this.getGraphics();
		screen.drawImage(offImage, 0, 0, offImage.getWidth(), offImage.getHeight(),null);
		screen.dispose();
		//offScreen.clearRect(0, 0, offImage.getWidth(), offImage.getHeight());
	}

	@Override
	public void run() {
		//loadImages();
		//initComonents();
		currentState=state.RUNNING;
		while(currentState.equals(state.RUNNING)){
			//drawPanelImage();
			
				drawScreen();
			
			try{Thread.sleep(5);}
			catch(InterruptedException e){System.err.println("sleepInterupted\n"+e);}
		}
	}
	public void setPlyerManger(playerManager pm){
		this.pm=pm;
	}
	
	public void setPreviousPanel(JPanel p){
		previousPanel=p;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getSource()==playBtn){
			//System.out.println("Play pressed");
			mainPanel.add(nextPanel);
			((playPanel)nextPanel).setPlyerManger(pm);
			((playPanel)nextPanel).setPreviousPanel(this);
			new Thread((Runnable)nextPanel).start();
			
			currentState=state.BLOCKED;
			this.getGraphics().dispose();
			this.disable();
			this.setVisible(false);
			mainPanel.remove(this);
		}
		else if(arg0.getSource()==backBtn){
			mainPanel.add(previousPanel);
			previousPanel.setVisible(true);
			previousPanel.enable();
			//((playPanel)nextPanel).setPlyerManger(pm);
			new Thread((Runnable)previousPanel).start();
			
			currentState=state.BLOCKED;
			this.getGraphics().dispose();
			this.disable();
			this.setVisible(false);
			mainPanel.remove(this);
			//mainPanel.getGraphics().dispose();
		}
		else if(arg0.getSource()==scoreBtn){
			JPanel scor=new scoreCardPanel(this.getPreferredSize(), mainPanel);
			((scoreCardPanel)scor).setPreviousPanel(this);
			((scoreCardPanel)scor).setPlayerManager(pm);
			mainPanel.add(scor);
			scor.setVisible(true);
			new Thread((Runnable)scor).start();
			
			currentState=state.BLOCKED;
			this.getGraphics().dispose();
			this.disable();
			this.setVisible(false);
			mainPanel.remove(this);
		
		}
		else if(arg0.getSource()==levelBox){
			pm.setLevel(levelBox.getSelectedIndex());
		}
		else{}
	}

}