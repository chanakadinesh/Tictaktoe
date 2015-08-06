package GameCode.Objects;
import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

import GameCode.GUItypes.*;

public class MainClass {

	public static enum state{RUNNING,BLOCKED};
	public static void main(String[] args) {

		JFrame mainWindow=new JFrame("Tic Tak Tuk (Ghost Version)");  //Main window
		Dimension windowSize=new Dimension(1000,550);	//Dimension of window;
		mainWindow.setPreferredSize(windowSize);
		
		/*playerManager pm=new playerManager();*/
		startPanel sp=new startPanel(windowSize,mainWindow);
		/*sp.setPlyerManger(pm);*/
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		mainWindow.setLocation(dim.width/2-windowSize.width/2, dim.height/2-windowSize.height/2);
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow.setResizable(false);
		//mainWindow.addMouseListener(null);
		mainWindow.pack();
		mainWindow.setVisible(true);
		
		new Thread(sp).start();
		//Score_Handler sc =new Score_Handler();
		//sc.insert_to_easy_table("Chana", "win");
		
	}

}
