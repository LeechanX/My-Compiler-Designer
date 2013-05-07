package CFanalyze;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;

public class value_table {
	private int position;
	private String value;
	private String type;
	
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public value_table(){
		
	}
	public value_table(int position,String value,String type) {
		this.position = position;
		this.value = value;
		this.type=type;
	}
	public void println(ArrayList<value_table> tablelist){
		String result="tmpVal_table.txt";
		try{
		BufferedWriter output=new BufferedWriter(new FileWriter(result));	
		for(int i=0;i<tablelist.size();i++){
			System.out.println(); output.write
			 (String.format(tablelist.get(i).getPosition()+"    "+tablelist.get(i).getValue()+"     "+tablelist.get(i).getType()+System.getProperty("line.separator")));
		}
		output.flush();
		output.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}	
}
