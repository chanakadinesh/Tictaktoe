package GameCode.Objects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;
public class Score_Handler {
	Logger logger = Logger.getLogger(Score_Handler.class);
	private String[][] get_score(String table_name) {
		
		String[][] score=new String[25][4];
		try {
			Connection mycon=DriverManager.getConnection("jdbc:mysql://localhost/tictacnew","root","");
			
			Statement mystet=mycon.createStatement();
			
			String table_select="select * from "+table_name;
			ResultSet myre=mystet.executeQuery(table_select);
			
			int count=0;
			
			while(myre.next()){
				
				score[count][0]=myre.getString("Name");
				score[count][1]=(String)myre.getString("Win");
				score[count][2]=(String)myre.getString("Lose");
				score[count][3]=(String)myre.getString("Drow");
			
				count++;
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
			//e.printStackTrace();
			logger.error(" Cannot getScore from the database");
		}
		return score;
	}
	private void clear(String table){
		try {
			Connection mycon = DriverManager.getConnection("jdbc:mysql://localhost/tictacnew","root","");
			Statement mystet=mycon.createStatement();
			String sql="delete from "+table+";";
			
			mystet.executeUpdate(sql);
			System.out.println("Delete complete");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error(" Cannot clear the database");
			//e.printStackTrace();
		}
		
		
	}
	private void insert(String table,String name,String status){
		try {
			name=name.toLowerCase();
			Connection mycon = DriverManager.getConnection("jdbc:mysql://localhost/tictacnew","root","");
			Statement mystet=mycon.createStatement();

			ResultSet r=mystet.executeQuery("SELECT * FROM `"+table+"` WHERE `Name`='"+name+"'");
			
                if(r.next())
                {
                	if(status.equalsIgnoreCase("win")){
                		int count=r.getInt("Win")+1;
                		mystet.executeUpdate("update "+table+" set Win ='"+count+"' where `Name`='"+name+"'");
                	}
                	else if(status.equalsIgnoreCase("lose")){
                		int count=r.getInt("Lose")+1;
                		mystet.executeUpdate("update "+table+" set Lose ='"+count+"' where `Name`='"+name+"'");
                	}
                	else if(status.equalsIgnoreCase("drow")){
                		int count=r.getInt("Drow")+1;
                		mystet.executeUpdate("update "+table+" set Drow ='"+count+"' where `Name`='"+name+"'");
                	}
           
                } 
               
                else{
                	if(status.equalsIgnoreCase("win")){
                		String sql="insert into "+table
        					+"  (Name,Win,Lose,Drow)"
        					+" values ('"+name+"','1','0','0')";
                		mystet.executeUpdate(sql);
                	}
                	else if(status.equalsIgnoreCase("lose")){
                		String sql="insert into "+table
        					+"  (Name,Win,Lose,Drow)"
        					+" values ('"+name+"','0','1','0')";
                		mystet.executeUpdate(sql);
                	}
                	else if(status.equalsIgnoreCase("drow")){
                		String sql="insert into "+table
        					+"  (Name,Win,Lose,Drow)"
        					+" values ('"+name+"','0','0','1')";
                		mystet.executeUpdate(sql);
                	}
                }
                
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
			logger.error(" Cannot insert to the database");
		}
		
	}

	public String[][] get_easy_table(){
		return get_score("easy_score");
	}
	public String[][] get_medium_table(){
		return get_score("medium_score");
	}	
	public String[][] get_hard_table(){
		return get_score("hard_score");
	}

	public void clear_easy_table(){
		clear("easy_score");
	}
	public void clear_medium_table(){
		clear("medium_score");
	}
	public void clear_hard_table(){
		clear("hard_score");
	}

	public void insert_to_easy_table(String name,String status){
		insert("easy_score",name,status);
	}
	public void insert_to_medium_table(String name,String status){
		insert("medium_score",name,status);
	}
	public void insert_to_hard_table(String name,String status){
		insert("hard_score",name,status);
	}
	
}
