package GameCode.Objects;

import java.util.Random;

public class clickAlogorithm {

	private int [] clicks;
	
	public clickAlogorithm(int [] clicks) {
		this.clicks=clicks;
	}
	
public int third_click(int t){
	   if(clicks[0]==t && clicks[1]==t && clicks[2]==0) return 2;
  else if(clicks[1]==t && clicks[2]==t && clicks[0]==0) return 0;
  else if(clicks[3]==t && clicks[4]==t && clicks[5]==0) return 5;
  else if(clicks[4]==t && clicks[5]==t && clicks[3]==0) return 3;
  else if(clicks[6]==t && clicks[7]==t && clicks[8]==0) return 8;
  else if(clicks[7]==t && clicks[8]==t && clicks[6]==0) return 6;
  else if(clicks[0]==t && clicks[4]==t && clicks[8]==0) return 8;
  else if(clicks[4]==t && clicks[8]==t && clicks[0]==0) return 0;
  else if(clicks[2]==t && clicks[3]==t && clicks[6]==0) return 6;
  else if(clicks[6]==t && clicks[4]==t && clicks[2]==0) return 2;
  else if(clicks[0]==t && clicks[2]==t && clicks[1]==0) return 1;
  else if(clicks[3]==t && clicks[5]==t && clicks[4]==0) return 4;
  else if(clicks[6]==t && clicks[8]==t && clicks[7]==0) return 7;
  else if(clicks[0]==t && clicks[6]==t && clicks[3]==0) return 3;
  else if(clicks[1]==t && clicks[7]==t && clicks[4]==0) return 4;
  else if(clicks[2]==t && clicks[8]==t && clicks[5]==0) return 5;
  else if(clicks[3]==t && clicks[6]==t && clicks[0]==0) return 0;
  else if(clicks[4]==t && clicks[7]==t && clicks[1]==0) return 1;
  else if(clicks[5]==t && clicks[8]==t && clicks[2]==0) return 2;
  else if(clicks[0]==t && clicks[3]==t && clicks[6]==0) return 6;
  else if(clicks[1]==t && clicks[4]==t && clicks[7]==0) return 7;
  else if(clicks[2]==t && clicks[5]==t && clicks[8]==0) return 8;
  else if(clicks[0]==t && clicks[8]==t && clicks[4]==0) return 4;
  else if(clicks[2]==t && clicks[6]==t && clicks[4]==0) return 4;
  else{return 11;}
}

public int hard2Clicks(){
	
	if(clicks[4]==0) return 4;
	else if(clicks[1]==0) return 1;
	else if(clicks[5]==0) return 5;
	else if(clicks[7]==0) return 7;
	else if(clicks[3]==0) return 3;
	else if(clicks[0]==0) return 0;
	else if(clicks[2]==0) return 2;
	else if(clicks[6]==0) return 6 ;
	else if(clicks[8]==0) return 8;
	
	else return 11; 
}

public int medium2Clicks(){
	if(clicks[4]==0) return 4;
	else if(clicks[0]==0) return 0;
	else if(clicks[2]==0) return 2;
	else if(clicks[6]==0) return 6 ;
	else if(clicks[8]==0) return 8;
	else if(clicks[1]==0) return 1;
	else if(clicks[5]==0) return 5;
	else if(clicks[7]==0) return 7;
	else if(clicks[3]==0) return 3;
	else return 11;
}
public int easy2Clicks(){
	for(int i=0;i<9;i++){
		int index=new Random().nextInt(9);
		if(clicks[i]==0){return i;}
	}
	for(int i=0;i<9;i++){
		//int index=new Random().nextInt(9);
		if(clicks[i]==0){return i;}
	}
	return 11;
}

public void setClickArea(int[] clicks2) {
	clicks=clicks2;
	
}
		
}
