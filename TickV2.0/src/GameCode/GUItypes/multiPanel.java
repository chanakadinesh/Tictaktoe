package GameCode.GUItypes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import GameCode.Objects.ServerPlayer;
import GameCode.Objects.playerManager;
import GameCode.Objects.MainClass.state;

public class multiPanel extends JPanel implements ActionListener,Runnable{
	state currentState;
	JFrame mainPanel;
	BufferedImage offImage,backGroundPic,serverPic,clientPic,backPic,playPic;
	Graphics2D offScreen;
	JButton server,client,backBtn,playBtn;
	JLabel serverPortlable,clientIPlable,clientPortlable,stateLable;
	JTextField serverPortText,clientIPText,clientPortText;
	playerManager pm;
	JPanel nextPanel,previousPanel;

	public multiPanel(Dimension d,JFrame mainPanel){
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
		nextPanel=new playPanel(d, mainPanel);
	}

	public void drawScreen(){
		//offScreen.drawImage(backGroundPic.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH),0,0,null);
		Graphics2D screen= (Graphics2D) this.getGraphics();
		screen.drawImage(offImage, 0, 0, offImage.getWidth(), offImage.getHeight(),null);
		paintComponents(offScreen);
		//offScreen.clearRect(0, 0, offImage.getWidth(), offImage.getHeight());
		screen.dispose();
	}
	private void initComonents() {
		// TODO Auto-generated method stub
		serverPortlable=new JLabel("Port");
		clientIPlable=new JLabel("IP");
		clientPortlable=new JLabel("Port");

		serverPortText=new JTextField();
		clientIPText=new JTextField();
		clientPortText=new JTextField();


		clientIPlable.setBounds(50,2*this.getHeight()/5,60,40);
		clientIPText.setBounds(170,2*this.getHeight()/5,180,40);

		clientPortlable.setBounds(this.getWidth()-320,2*this.getHeight()/5,60,40);
		clientPortText.setBounds(this.getWidth()-230,2*this.getHeight()/5,180,40);

		serverPortlable.setBounds(50,2*this.getHeight()/5+60,60,40);
		serverPortText.setBounds(170,2*this.getHeight()/5+60,180,40);

		Font f2 = new Font("Times new roman", Font.PLAIN, 30);
		clientIPlable.setFont(f2);
		serverPortlable.setFont(f2);
		serverPortText.setFont(f2);
		clientIPText.setFont(f2);
		clientPortlable.setFont(f2);
		clientPortText.setFont(f2);

		stateLable = new JLabel();
		stateLable.setBounds(this.getWidth()/2-200,2*this.getHeight()/5+150,400,40);

		client =new JButton();
		client.addActionListener(this);
		client.setBounds(80,2*this.getHeight()/5+120,180,40);
		client.setIcon(new ImageIcon(clientPic));

		backBtn =new JButton();
		backBtn.addActionListener(this);
		backBtn.setBounds(20,2*this.getHeight()/5+270,100,20);
		backBtn.setIcon(new ImageIcon(backPic.getScaledInstance(100, 20, Image.SCALE_SMOOTH)));

		playBtn =new JButton();
		playBtn.addActionListener(this);
		playBtn.setBounds(this.getWidth()/2-90,2*this.getHeight()/5+250,180,40);
		playBtn.setIcon(new ImageIcon(playPic));
		
		JTextArea jtf=new JTextArea();
		String s="If a server has established press loginServer"
				+ "\nOtherwise press create a server\n"
				+ "After both connected Play button will display";
		jtf.setText(s);
		jtf.setBounds(30,20,this.getWidth()-60,60);
		jtf.setEditable(false);
		//jtf.setEnabled(false);
		jtf.setFocusable(false);
		jtf.setBackground(Color.black);
		jtf.setForeground(Color.YELLOW);
		this.add(jtf);

		server =new JButton();
		server.addActionListener(this);
		server.setBounds(this.getWidth()-260,2*this.getHeight()/5+120,180,40);
		server.setIcon(new ImageIcon(serverPic));

		serverPortText.setText("3128");
		clientPortText.setText("3128");
		clientIPText.setText("localhost");

		serverPortText.setBackground(Color.black);
		serverPortText.setForeground(Color.YELLOW);
		clientPortText.setBackground(Color.black);
		clientPortText.setForeground(Color.YELLOW);
		clientIPText.setBackground(Color.black);
		clientIPText.setForeground(Color.YELLOW);
		this.add(clientIPText);
		this.add(clientIPlable);
		this.add(clientPortText);
		this.add(clientPortlable);
		this.add(serverPortText);
		this.add(serverPortlable);
		this.add(server);
		this.add(client);
		this.add(backBtn);
		//this.add(playBtn);
		//stateLable.setText("Waiting for Connection");
		stateLable.setForeground(Color.white);
		stateLable.setHorizontalAlignment(JLabel.CENTER);
		//stateLable.setOpaque(false);
		
		this.add(stateLable);
		

	}
	private void loadImages() {
		try{
			backGroundPic=ImageIO.read(this.getClass().getResource("/Resources/background.jpg"));
			serverPic=ImageIO.read(this.getClass().getResource("/Resources/severcreateBtn.gif"));
			clientPic=ImageIO.read(this.getClass().getResource("/Resources/severloginBtn.gif"));
			backPic=ImageIO.read(this.getClass().getResource("/Resources/backBtn.gif"));
			playPic=ImageIO.read(this.getClass().getResource("/Resources/playBtn.gif"));
		}catch(IOException e){System.err.print("Faild\n"+e);}
		offScreen.drawImage(backGroundPic.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH),0,0,null);
	}

	public void setPlyerManger(playerManager pm){
		this.pm=pm;
	}
	@Override
	public void run() {
		currentState=state.RUNNING;
		while(currentState.equals(state.RUNNING)){
			//drawPanelImage();
			drawScreen();

			try{Thread.sleep(5);}
			catch(InterruptedException e){System.err.println("sleepInterupted\n"+e);}
		}
	}

	public void goNextPanel(){
		System.out.println("Play pressed");
		mainPanel.add(nextPanel);
		((playPanel)nextPanel).setPlyerManger(pm);
		new Thread((Runnable)nextPanel).start();
		currentState=state.BLOCKED;
		this.getGraphics().dispose();
		this.disable();
		this.setVisible(false);
		mainPanel.remove(this);
		mainPanel.getGraphics().dispose();
	}
	public void setPreviousPanel(JPanel p){
		previousPanel=p;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource()==server){
			//stateLable.getGraphics().dispose();
			pm.setUser_serverPlayer(Integer.parseInt(serverPortText.getText()));
			if(pm.getState()==1){stateLable.setText("Connection established-server connected");
			this.add(playBtn);
			}
			else{stateLable.setText("Connection failed");}
			//stateLable.repaint();
		}
		else if(arg0.getSource()==client){
			//stateLable.getGraphics().dispose();
			pm.setUser_clientPlayer(clientIPText.getText(),Integer.parseInt(clientPortText.getText()));
			if(pm.getState()==1){stateLable.setText("Connection established-server connected");
			this.add(playBtn);
			}
			else{stateLable.setText("Connection failed");}
			//stateLable.repaint();
		}
		else if(arg0.getSource()==playBtn && playBtn.isEnabled()){
			goNextPanel();
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
			mainPanel.getGraphics().dispose();
		}
		else{}
	}

}


