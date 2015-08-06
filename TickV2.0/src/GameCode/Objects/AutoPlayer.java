package GameCode.Objects;

import java.util.Random;

public class AutoPlayer extends Player {
	clickAlogorithm ca;
	public static enum level{easy,medium,hard};
	level currentLevel;
	public AutoPlayer(int[] clicks, int turn) {
		super(clicks, turn);
		ca=new clickAlogorithm(clicks);
		currentLevel=level.medium;
	}
	public void setLevel(level l){
		currentLevel=l;
	}
	@Override
	public void setPlayerArea(int [] clicks){
		super.setPlayerArea(clicks);
		ca.setClickArea(clicks);
	}
	
	public void notify(int i) {
		long strt,end=0; 
		strt= System.currentTimeMillis() % 1000;
		setActive(true);
		/*while((end-strt)<150){
			end=System.currentTimeMillis() % 1000;
		}*/
		try{
			Thread.sleep(1000);
		}catch(InterruptedException e){}
		//clicks[i]=oppsite.getTurn();
		
		playerClick(generatedPlace());
	}
	
	public int generatedPlace(){
		int click = 11;
		click=ca.third_click(this.getTurn());
		if(click!=11){ return click;}
		click=ca.third_click(3-this.getTurn());
		if(click!=11){ return click;}
		
		if(currentLevel.equals(level.easy)){
		click=ca.easy2Clicks();
		if(click!=11){ return click;}
		}else if(currentLevel.equals(level.medium)){
			click=ca.medium2Clicks();
			if(click!=11){ return click;}
		}
		else if(currentLevel.equals(level.hard)){
			click=ca.hard2Clicks();
			if(click!=11){ return click;}
		}
		/*
		*/
		return 10;
	}
}
