package GameCode.Objects;

public class Player {

	protected int turn;
	protected int [] clicks;  
	protected boolean active;
	protected Player oppsite;
	String name;

	public Player(int [] clicks,int turn) {
		this.clicks=clicks;
		this.turn=turn;
		if(turn==1){this.active=true;}
		else{this.active=false;}
		name="user name";
	}
	public void setPlayerArea(int [] clicks){
		this.clicks=clicks;
	}
	public void setUserName(String s){name=s;}
	public String getUserName(){return name;}
	public void setOpposite(Player p){
		this.oppsite=p;
	}
	
	public void playerClick(int i) {
		try{
			if(clicks[i]==0 && getActive()){
				clicks[i]=turn;
				setActive(false);
				//long end=0,strt;
				//strt=System.currentTimeMillis()%1000;
				oppsite.notify(i);
			}
		}catch(ArrayIndexOutOfBoundsException e){
		//	System.out.println("Array index out of bound");
		}

	}

	public void notify(int i) {
		setActive(true);
		clicks[i]=oppsite.getTurn();
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean getActive(){
		return active;
	}

	public int getTurn(){
		return turn;
	}
}

