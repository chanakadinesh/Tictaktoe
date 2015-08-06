package GameCode.Objects;

import org.apache.log4j.Logger;

import GameCode.Objects.AutoPlayer.level;



public class playerManager {
	private volatile Player user,opposite;
	private int [] playGround;
	private int state;
	private String userName;
	private GameUmpire gu;
	Logger logger=Logger.getLogger(playerManager.class);
	public playerManager() {
		userName="enter name";
		gu=new GameUmpire();	
		gu.setUser(user);
		initPlayerManager();
	}
	
	public void initPlayerManager(){
		playGround=new int[9];
		for(int i=0;i<9;i++){playGround[i]=0;}
		//gu=new GameUmpire(playGround);
		gu.initGround(playGround);
	}
	
	public void updatePlayArea(){
		initPlayerManager();
		if(opposite instanceof AutoPlayer){
			System.out.println("Auto player");
			user.setActive(true);
			opposite.setActive(false);
		}
		user.setPlayerArea(playGround);
		opposite.setPlayerArea(playGround);
	}
	
	public GameUmpire getUmpire(){return gu;}
	
	
	public int [] getPlayGround(){
		return playGround;
	}
	
	public int getGroundMap(int i){
		return playGround[i];
	}
	
	public synchronized Player getUser(){
		return user;
	}
	
	public void setLevel(int i){
		if(opposite instanceof AutoPlayer){
		if(i==0){((AutoPlayer) opposite).setLevel(level.easy);}
		else if(i==1){((AutoPlayer) opposite).setLevel(level.medium);}
		else{((AutoPlayer) opposite).setLevel(level.hard);}
		}
		logger.info("level= "+i);
		
	}
	
	public void setOppo_AutoPlayer(){
		user=new Player(playGround, 1);
		opposite=new AutoPlayer(playGround, 2);
		user.oppsite=opposite;
		opposite.oppsite=user;
		user.name=userName;
		gu.setUser(user);
	}
	public void setUser_clientPlayer(String IP,int port){
		user=new ClientPlayer(playGround, 1,IP,port);
		new Thread((Runnable) user).start();
		opposite=new Player(playGround,2);
		opposite.setOpposite(user);
		user.oppsite=opposite;
		while(((ClientPlayer)user).state==0);
		this.state=((ClientPlayer)user).state;
		//pg.setOppositePlayer(opposite);
		//status.setText(" Client Connected");
		user.name=userName;
		gu.setUser(user);
	}
	public void setUser_serverPlayer(int port){
		user=new ServerPlayer(playGround, 2,port);
		new Thread((Runnable) user).start();
		opposite=new Player(playGround,1);
		opposite.setOpposite(user);
		user.oppsite=opposite;
		while(((ServerPlayer)user).state==0);
		this.state=((ServerPlayer)user).state;
		//status.setText(" Server Started");
		user.name=userName;
		gu.setUser(user);
	}
	
	public void setUserName(String name){
		//while(state==0);
		userName=name;
		
	}
	
	public void set_practicePlayer(){
		user=new Player(playGround, 1);
		opposite=new manualOppositePlayer(playGround, 2);
		user.oppsite=opposite;
		opposite.oppsite=user;
		user.name=userName;
		gu.setUser(user);
		
	}
	
	public int getState(){
		return state;
	}
}
