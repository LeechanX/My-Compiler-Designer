package CFanalyze;

import java.util.ArrayList;
import java.io.*;

public class sym_table {

	int position;
	int type;
	String sym_name;
	
	public sym_table(){

	}
	public sym_table(int position,int type,String sym_name){
		this.position=position;
		this.type=type;
		this.sym_name=sym_name;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getSym_name() {
		return sym_name;
	}
	public void setSym_name(String sym_name) {
		this.sym_name = sym_name;
	}
	
	public void println(ArrayList<sym_table> tablelist){
		String result="tmpSym_table.txt";
		try{
		BufferedWriter output=new BufferedWriter(new FileWriter(result));	
		output.write(String.format("位置    名字       类型")+System.getProperty("line.separator"));
		for(int i=0;i<tablelist.size();i++){
			System.out.println(); output.write
			 (String.format(tablelist.get(i).position+"         "+tablelist.get(i).sym_name+"       "+tablelist.get(i).type)+System.getProperty("line.separator"));
		}
		output.write(String.format("-------------------------------------------------------")+System.getProperty("line.separator"));			
		output.flush();
		output.close();
		}catch(Exception e){
			e.printStackTrace();
		}

	}	
}