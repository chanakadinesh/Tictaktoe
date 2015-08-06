package GameCode.GUItypes;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.multi.MultiButtonUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.*;
import java.io.*;

import GameCode.Objects.MainClass.state;
import GameCode.Objects.*;

public class startPanel extends JPanel implements Runnable,ActionListener{
	
	private Graphics2D offScreen;
	private BufferedImage offImage,backGround,singlePic,multiPic,nameLablePic,exitPic,practicePic,GameName;
	JButton singleBtn,mulitBtn,exitBtn,practiceBtn;
	JLabel enterName;
	JTextField nameSpace;
	JFrame mainPanel;
	JPanel nextPanel1,nextPanel2;
	state CurrentState;
	playerManager pm;
	
	public startPanel(Dimension d,JFrame mPanel){
		CurrentState=state.RUNNING;
		mainPanel=mPanel;
		this.setSize(d);
		this.setPreferredSize(d);
		setLayout(null);
		offImage=new BufferedImage(d.width,d.height,BufferedImage.TYPE_INT_RGB );
		offScreen=offImage.createGraphics();
		this.setIgnoreRepaint(true);
		loadImages();
		initComonents();
		mainPanel.add(this);
		this.setOpaque(true);
		pm=new playerManager();
		nextPanel1 = new optionPanel(d,mainPanel);
		nextPanel2 = new multiPanel(d,mainPanel);
	}
	
	public void setPlyerManger(playerManager pm){
		this.pm=pm;
	}
	
	public void initComonents(){
		
		singleBtn=new JButton();
		singleBtn.addActionListener(this);
		mulitBtn=new JButton();
		singleBtn.setBounds(this.getWidth()/2-90,2*this.getHeight()/3-10,180,40);
		mulitBtn.setBounds(this.getWidth()/2-90,2*this.getHeight()/3+40,180,40);
		singleBtn.setIcon(new ImageIcon(singlePic));
		mulitBtn.setIcon(new ImageIcon(multiPic));
		mulitBtn.addActionListener(this);
		this.add(singleBtn);
		this.add(mulitBtn);
		/*enterName=new JLabel();
		enterName.setBounds(this.getWidth()/2-200,4*this.getHeight()/7-30,180,40);
		enterName.setIcon(new ImageIcon(nameLablePic));
		this.add(enterName);*/
		
		exitBtn=new JButton();
		exitBtn.addActionListener(this);
		exitBtn.setBounds(20,2*this.getHeight()/3+100,130,30);
		exitBtn.setIcon(new ImageIcon(exitPic.getScaledInstance(130, 30, Image.SCALE_SMOOTH)));
		this.add(exitBtn);
		
		practiceBtn=new JButton();
		practiceBtn.addActionListener(this);
		practiceBtn.setBounds(this.getWidth()/2-90,2*this.getHeight()/3+90,180,40);
		practiceBtn.setIcon(new ImageIcon(practicePic.getScaledInstance(180, 40, Image.SCALE_SMOOTH)));
		this.add(practiceBtn);
		
		JLabel lable=new JLabel();
		lable.setBounds(this.getWidth()/2-350,10,700,200);
		lable.setIcon(new ImageIcon(GameName.getScaledInstance(700,200,Image.SCALE_SMOOTH)));
		this.add(lable);
		
		nameSpace=new JTextField("Enter Name");
		nameSpace.setBackground(Color.BLACK);
		nameSpace.setForeground(Color.yellow);
		Font f2 = new Font("Times new roman", Font.PLAIN, 30);
		nameSpace.setFont(f2);
		nameSpace.setHorizontalAlignment(JTextField.CENTER);
		nameSpace.setBounds(this.getWidth()/2-110,4*this.getHeight()/7-40,220,40);
		nameSpace.selectAll();
		this.add(nameSpace);
		
	}
	
	public void loadImages(){
		try{
			backGround=ImageIO.read(getClass().getResource("/Resources/background.jpg"));
			singlePic=ImageIO.read(getClass().getResource("/Resources/singlebtn.gif"));
			multiPic=ImageIO.read(this.getClass().getResource("/Resources/multiBtn.gif"));
			exitPic=ImageIO.read(this.getClass().getResource("/Resources/exitBtn.gif"));
			practicePic=ImageIO.read(this.getClass().getResource("/Resources/practicePic.gif"));
			GameName=ImageIO.read(this.getClass().getResource("/Resources/GameName.gif"));
		}catch(IOException e){System.err.print("Faild\n"+e);}
		offScreen.drawImage(backGround.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH),0,0,null);
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
		CurrentState=state.RUNNING;
		while(CurrentState.equals(state.RUNNING)){
			drawScreen();
		try{Thread.sleep(5);}
		catch(InterruptedException e){System.err.println("sleepInterupted\n"+e);}
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource()==singleBtn){
			pm.setUserName(nameSpace.getText());
			pm.setOppo_AutoPlayer();
			//System.out.println("Single pressed");
			mainPanel.add(nextPanel1);
			((optionPanel)nextPanel1).setPreviousPanel(this);
			((optionPanel)nextPanel1).setPlyerManger(pm);
			nextPanel1.enable();
			nextPanel1.setVisible(true);
			new Thread((Runnable)nextPanel1).start();
			
			CurrentState=state.BLOCKED;
			this.getGraphics().dispose();
			this.disable();
			this.setVisible(false);
			mainPanel.remove(this);
		}
		else if(arg0.getSource()==mulitBtn){
			//pm.setOppo_serverPlayer(port);;
		//	System.out.println("Multi pressed");
			mainPanel.add(nextPanel2);
			((multiPanel)nextPanel2).setPreviousPanel(this);
			((multiPanel)nextPanel2).setPlyerManger(pm);
			nextPanel2.enable();
			nextPanel2.setVisible(true);
			new Thread((Runnable)nextPanel2).start();
			
			CurrentState=state.BLOCKED;
			this.getGraphics().dispose();
			this.disable();
			this.setVisible(false);
		}
		else if(arg0.getSource()==exitBtn){
			
			if(JOptionPane.OK_OPTION==JOptionPane.showConfirmDialog(null,
                    "Do you really want to exit",
                    "Exit Comfrimation", 
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE)){ 
			
			CurrentState=state.BLOCKED;
			this.setVisible(false);
			mainPanel.dispose();
			}
		}
		else if(arg0.getSource()==practiceBtn){
			pm.setUserName(nameSpace.getText());
			pm.set_practicePlayer();
			JPanel nextPanel=new playPanel(this.getPreferredSize(), mainPanel);
			mainPanel.add(nextPanel);
			((playPanel)nextPanel).setPlyerManger(pm);
			//((playPanel)nextPanel).setPreviousPanel(this);
			nextPanel.setVisible(true);
			new Thread((Runnable)nextPanel).start();
			
			CurrentState=state.BLOCKED;
			this.getGraphics().dispose();
			this.disable();
			this.setVisible(false);
			mainPanel.remove(this);
		}
		else{}
		
	}

	
}
