package GameCode.GUItypes;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.naming.InitialContext;
import javax.swing.*;

import GameCode.Objects.*;
import GameCode.Objects.GameUmpire.EndState;
import GameCode.Objects.MainClass.state;

public class playPanel extends JPanel implements MouseListener,ActionListener,Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3L;
	state currentState;
	JFrame mainWindow;
	BufferedImage offImage,background,ovalPic,crossPic,backPic;
	Graphics2D offScreen;
	private playerManager pm;
	private Rectangle [] groundSections;
	JPanel nextWonLoss,previousPanel;
	Rectangle playArea;
	JButton backBtn;
	
//	GameUmpire gu;

	public playPanel(Dimension d,JFrame mainP){
		currentState=state.BLOCKED;
		this.setSize(d);
		this.setPreferredSize(d);
		this.setLayout(null);
		mainWindow=mainP;
		this.setIgnoreRepaint(true);
		mainWindow.add(this);
		
		offImage=new BufferedImage(d.width,d.height,BufferedImage.TYPE_INT_RGB );
		offScreen=offImage.createGraphics();
		/*loadImages();*/
		playArea=new Rectangle(this.getWidth()/4, (offImage.getHeight()-offImage.getWidth()/2)/2, offImage.getWidth()/2, offImage.getWidth()/2);
		initGroundSections();
		
	}

	public void setPreviousPanel(JPanel p){
		previousPanel=p;
	}
	public void initComponents(){
		backBtn =new JButton();
		backBtn.addActionListener(this);
		backBtn.setBounds(0,this.getHeight()-20,100,20);
		backBtn.setIcon(new ImageIcon(backPic.getScaledInstance(100, 20, Image.SCALE_SMOOTH)));
		this.add(backBtn);
	}
	
	public void initGroundSections(){
		groundSections=new Rectangle[9];
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				groundSections[3*i+j]=new Rectangle(playArea.x+j*playArea.width/3,playArea.y+i*playArea.height/3,playArea.width/3, playArea.height/3);
			}
		}
	}

	public void drawPlayedArea(){
		//offScreen.drawImage(background.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH),0,0,null);
		int shift=(playArea.width/3-120)/2;
		
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				if(pm.getGroundMap(i*3+j)==1){
					offScreen.drawImage(crossPic.getScaledInstance(120, 120, Image.SCALE_SMOOTH),playArea.width/3*j+playArea.x+shift,playArea.height/3*i+playArea.y+shift,null);
				}
				else if(pm.getGroundMap(i*3+j)==2){
					offScreen.drawImage(ovalPic.getScaledInstance(120, 120, Image.SCALE_SMOOTH),playArea.width/3*j+playArea.x+shift,playArea.height/3*i+playArea.y+shift,null);
				}
				else if(pm.getGroundMap(i*3+j)==3){
					Rectangle redRect=new Rectangle(playArea.width/3*j+playArea.x,playArea.height/3*i+playArea.y,playArea.width/3,playArea.height/3);
					offScreen.setComposite(AlphaComposite.SrcOver.derive(0.4f));
					offScreen.setColor(Color.red);
					offScreen.fill(redRect);
				
					offScreen.setComposite(AlphaComposite.SrcOver);
					offScreen.drawImage(crossPic.getScaledInstance(120, 120, Image.SCALE_SMOOTH),playArea.width/3*j+playArea.x+shift,playArea.height/3*i+playArea.y+shift,null);
				}
				else if(pm.getGroundMap(i*3+j)==6){
					Rectangle redRect=new Rectangle(playArea.width/3*j+playArea.x,playArea.height/3*i+playArea.y,playArea.width/3,playArea.height/3);
					offScreen.setComposite(AlphaComposite.SrcOver.derive(0.4f));
					offScreen.setColor(Color.red);
					offScreen.fill(redRect);
					
					offScreen.setComposite(AlphaComposite.SrcOver);
					offScreen.drawImage(ovalPic.getScaledInstance(120, 120, Image.SCALE_SMOOTH),playArea.width/3*j+playArea.x+shift,playArea.height/3*i+playArea.y+shift,null);
				}
				else{}
			}
		}
	}

	public void setPlyerManger(playerManager pm){
		this.pm=pm;
		//this.gu=new GameUmpire(pm.getPlayGround());
	}

	public void loadImages(){
		try{
			background=ImageIO.read(this.getClass().getResource("/Resources/background.jpg"));
			ovalPic=ImageIO.read(this.getClass().getResource("/Resources/roundPic.gif"));
			crossPic=ImageIO.read(this.getClass().getResource("/Resources/crossPic.gif"));
			backPic=ImageIO.read(this.getClass().getResource("/Resources/backBtn.gif"));
		}catch(IOException e){System.err.print("Faild\n"+e);}
		//offScreen.clearRect(0, 0, offImage.getWidth(), offImage.getHeight());
		//drawBackground();
	}

	//Sample method
	public void paintClick(Point p){
		offScreen.drawImage(ovalPic.getScaledInstance(100, 100, Image.SCALE_SMOOTH), p.x, p.y-26, null);
	}

	public void drawScreen(){
		paintComponents(offScreen);
		drawBackground();
		drawPlayedArea();
		
		Graphics2D screen= (Graphics2D) this.getGraphics();
		screen.drawImage(offImage, 0, 0, this.getWidth(), this.getHeight(),null);
		
		offScreen.clearRect(0, 0, this.getWidth(), this.getHeight());
		//screen.dispose();
		
	}

	@Override
	public void run() {
		loadImages();
		initComponents();
		this.addMouseListener(this);
		currentState= state.RUNNING;
		while(currentState.equals(state.RUNNING)){
			//try{
			EndState endState=pm.getUmpire().chekGameEnd();
			if(endState.equals(EndState.run)){
				drawScreen();
			}else{
				try{Thread.sleep(1500);}
				catch(InterruptedException ee){}
			//	drawScreen();
				nextWonLoss=new winLostDrawPanel(this.size(), mainWindow);
				((winLostDrawPanel)nextWonLoss).changeEndState(endState);
				mainWindow.add(nextWonLoss);
				nextWonLoss.setVisible(true);
				nextWonLoss.enable();
				((winLostDrawPanel)nextWonLoss).setPreviousPanel(this);
				((winLostDrawPanel)nextWonLoss).setPlayerManager(pm);
				new Thread((Runnable)nextWonLoss).start();
				
				
				currentState=state.BLOCKED;
				this.getGraphics().dispose();
				this.disable();
				this.setVisible(false);
				mainWindow.remove(this);
			}
			/*}catch (NullPointerException e) {
				System.out.println("Graphics are disposed");
			}*/
			try{Thread.sleep(5);}
			catch(InterruptedException e){System.err.println("sleepInterupted\n"+e);}
		}
	}
	public void drawBackground(){
		offScreen.drawImage(background.getScaledInstance(offImage.getWidth(), offImage.getHeight(), Image.SCALE_SMOOTH),0,0,null);
		Rectangle playArea=new Rectangle(this.getWidth()/4, (offImage.getHeight()-offImage.getWidth()/2)/2, offImage.getWidth()/2, offImage.getWidth()/2);
		offScreen.setComposite(AlphaComposite.SrcOver.derive(0.4f));
		offScreen.setColor(Color.green);
		offScreen.fill(playArea);
		offScreen.setComposite(AlphaComposite.SrcOver);
		offScreen.setColor(Color.white);
		offScreen.drawRect(playArea.x, playArea.y,(int) playArea.getWidth(), (int)playArea.getHeight());
		offScreen.drawRect(playArea.x+(int)playArea.getWidth()/3, playArea.y,(int) playArea.getWidth()/3, (int)playArea.getHeight());
		offScreen.drawRect(playArea.x, playArea.y+(int)playArea.getHeight()/3,(int) playArea.getWidth(), (int)playArea.getHeight()/3);

		offScreen.drawRect(playArea.x+playArea.width/3, playArea.y+playArea.height/3,(int) playArea.getWidth()/3, (int)playArea.getHeight()/3);	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		//System.out.println("Clicked on = " +arg0.getPoint());
		if((pm.getUser()).getActive()){
			for(int i=0;i<3;i++){
				for(int j=0;j<3;j++){
					if(groundSections[3*i+j].contains(arg0.getPoint())){
						(pm.getUser()).playerClick(3*i+j);
						//drawScreen();
						return;
					}
				}
			}
		}
		//drawScreen();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
