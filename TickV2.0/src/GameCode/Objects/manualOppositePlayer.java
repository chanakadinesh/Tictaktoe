package GameCode.Objects;

public class manualOppositePlayer extends Player {

	public manualOppositePlayer(int[] clicks, int turn) {
		super(clicks, turn);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void notify(int i){
		oppsite.setActive(true);
		//playerClick(10);
		this.setActive(false);
		int a=oppsite.turn;
		oppsite.turn=this.turn;
		this.turn=a;
	}
}
