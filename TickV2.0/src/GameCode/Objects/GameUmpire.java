package GameCode.Objects;

import org.apache.log4j.Logger;


//Here we decide weather game is won or lost and record them in a database;

public class GameUmpire {
	Player user;
	Score_Handler sh;
	public static enum EndState{win,lose,draw,run};
	int [] groundMap;
	EndState endState;
	Logger logger = Logger.getLogger(GameUmpire.class);
	public GameUmpire() {
		//groundMap=ground;
		endState=EndState.run;
		sh=new Score_Handler();
	}
	
	public void initGround(int [] ground){
		groundMap=ground;
		endState=EndState.run;
	}
	
	public void setUser(Player p){user=p;}
	public EndState chekGameEnd(){

		int won=0;
		for(int i=0;i<3;i++){
			if((groundMap[i*3]==groundMap[i*3+1])&&(groundMap[i*3]==groundMap[i*3+2])&&(groundMap[i*3]!=0)){
				won=groundMap[i*3]*3;
				break;
			}
			else if((groundMap[i]==groundMap[i+3])&&(groundMap[i]==groundMap[i+6])&&(groundMap[i]!=0)){
				won=groundMap[i]*3;
				break;
			}
		}
		if(won==0){
			if((groundMap[0]==groundMap[4])&&(groundMap[0]==groundMap[8])&&(groundMap[0]!=0)){
				won=groundMap[0]*3;
			}
			else if((groundMap[2]==groundMap[4])&&(groundMap[2]==groundMap[6])&&(groundMap[2]!=0)){
				won=groundMap[2]*3;
			}
		}
		if(won/3==user.getTurn()){
			endState=EndState.win;
		}
		else if(won==0){
			if(checkEnd()){
				endState=EndState.draw;
			}
			else{
				endState=EndState.run;
			}
		}
		else{
			endState=EndState.lose;
		}
		try{
		updateDatabase();
		}
		catch(NullPointerException E){logger.error("Database error");}
		
		logger.debug("End state= "+endState);
		return endState;

	}
	
	public void updateDatabase(){
		if(endState.equals(EndState.win)){
			sh.insert_to_easy_table(user.getUserName(),"win");
		}
		else if(endState.equals(EndState.lose)){
			sh.insert_to_easy_table(user.getUserName(),"lose");
		}
		else if(endState.equals(EndState.draw)){
			sh.insert_to_easy_table(user.getUserName(),"drow");
		}
		else{}
	}
	
	public boolean checkEnd(){
		boolean finish=true;
		for(int state:groundMap){
			if(state==0){
				finish=false;
				break;
			}
		}
		return finish;
	}
	
}


