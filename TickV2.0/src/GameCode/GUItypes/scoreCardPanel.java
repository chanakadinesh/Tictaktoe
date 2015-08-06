package GameCode.GUItypes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneLayout;

import GameCode.Objects.Score_Handler;
import GameCode.Objects.playerManager;
import GameCode.Objects.MainClass.state;

public class scoreCardPanel extends JPanel implements Runnable,ActionListener{
	
	JFrame mainWindow;
	BufferedImage offImage,backGroundPic,backPic,clearPic;
	Graphics2D offScreen;
	state currentState;
	JPanel previousPanel;
	JButton backBtn,clearBtn;
	JScrollPane jsp;
	JTable table;
	playerManager pm;
	String [][] scoreBoard;
	String [] tableNames;
	Score_Handler sh;
	
	public scoreCardPanel(Dimension d,JFrame mianWindow){
		this.setSize(d);
		this.setPreferredSize(d);
		this.mainWindow=mianWindow;
		currentState=state.RUNNING;
		setLayout(null);
		offImage=new BufferedImage(d.width,d.height,BufferedImage.TYPE_INT_RGB );
		offScreen=offImage.createGraphics();
		sh= new Score_Handler();
		this.setIgnoreRepaint(true);
		
		loadImages();
		
		initComonents();
		
	}

	public void initTable(){
		String [][]scor=sh.get_easy_table();
		String [] tableNames={"NAME","WON","LOST","DREW"};
		table=new JTable(scor,tableNames);
		table.setRowHeight(25);
		table.setOpaque(false);
		Font f2 = new Font("Times new roman", Font.PLAIN, 22);
		table.setFont(f2);
		table.setBackground(Color.BLACK);
		table.setForeground(Color.CYAN);
		table.setEnabled(false);
		
		
	}
	
	private void initComonents() {

		backBtn=new JButton();
		backBtn.addActionListener(this);
		backBtn.setBounds(100,4*this.getHeight()/5,180,40);
		backBtn.setIcon(new ImageIcon(backPic.getScaledInstance(180, 40, Image.SCALE_SMOOTH)));
		this.add(backBtn);

		clearBtn=new JButton();
		clearBtn.addActionListener(this);
		clearBtn.setBounds(this.getWidth()-280,4*this.getHeight()/5,180,40);
		clearBtn.setIcon(new ImageIcon(clearPic.getScaledInstance(180, 40, Image.SCALE_SMOOTH)));
		this.add(clearBtn);
		
		
		initTable();
		jsp=new JScrollPane(table);
		jsp.setBounds(100,50,this.getWidth()-200,3*this.getHeight()/5);
		//jsp.setOpaque(false);
		jsp.setVisible(true);
		this.add(jsp);
	}

	public void setPlayerManager(playerManager pm){
		this.pm=pm;
	}
	public void loadImages(){
		try{
			backGroundPic=ImageIO.read(this.getClass().getResource("/Resources/background.jpg"));
			backPic=ImageIO.read(this.getClass().getResource("/Resources/backBtn.gif"));
			clearPic=ImageIO.read(this.getClass().getResource("/Resources/clearBtn.gif"));
			//exitPic=ImageIO.read(new FileInputStream("src/Resources/exitBtn.gif"));
		}catch(IOException e){System.err.print("Faild\n"+e);}
		offScreen.drawImage(backGroundPic.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH),0,0,null);
	}
	
	public void setPreviousPanel(JPanel p){
		previousPanel=p;
	}
	
	public void drawScreen(){
		
		//offScreen.clearRect(0, 0, offImage.getWidth(), offImage.getHeight());
		//offScreen=offImage.createGraphics();
		//offScreen.drawImage(backGroundPic.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH),0,0,null);
		paintComponents(offScreen);
		Graphics2D screen= (Graphics2D) this.getGraphics();
		screen.drawImage(offImage, 0, 0, offImage.getWidth(), offImage.getHeight(),null);
		screen.dispose();
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource()==backBtn){
			mainWindow.add(previousPanel);
			previousPanel.setVisible(true);
			previousPanel.enable();
			//((playPanel)nextPanel).setPlyerManger(pm);
			new Thread((Runnable)previousPanel).start();
			
			currentState=state.BLOCKED;
			this.getGraphics().dispose();
			this.disable();
			this.setVisible(false);
			mainWindow.remove(this);
		}
		else if(arg0.getSource()==clearBtn){
			table.setVisible(false);
			jsp.setVisible(false);
			jsp.remove(table);
			jsp.getGraphics().dispose();
			this.remove(jsp);
			this.remove(table);
			sh.clear_easy_table();
			initTable();
			table.setOpaque(false);
			jsp=new JScrollPane(table);
			jsp.setBounds(100,50,this.getWidth()-200,3*this.getHeight()/5);
			jsp.setOpaque(false);
			jsp.setVisible(true);
			this.add(jsp);
			
		}
		
	}

	@Override
	public void run() {
		currentState=state.RUNNING;
		while(currentState.equals(state.RUNNING)){
			drawScreen();
		try{Thread.sleep(5);}
		catch(InterruptedException e){System.err.println("sleepInterupted\n"+e);}
		}
		
		
	}
}
