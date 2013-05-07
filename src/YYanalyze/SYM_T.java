package YYanalyze;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SYM_T {
	private String idname;
	private String type;
	private int offset;
	private String value;
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getIdname() {
		return idname;
	}
	public void setIdname(String idname) {
		this.idname = idname;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public SYM_T(){

	}
	public SYM_T(String idname, String type, int offset,String value) {
		this.idname = idname;
		this.type = type;
		this.offset = offset;
		this.value=value;
	}
	public void println(List<SYM_T> symt){
		String result="Sym_table.txt";
		try{
			BufferedWriter output=new BufferedWriter(new FileWriter(result));	
			output.write(String.format("名字       类型      值      位置")+System.getProperty("line.separator"));
			for(int i=0;i<symt.size();i++){
				System.out.println(); output.write
				(String.format(symt.get(i).getIdname()+"         "+symt.get(i).getType()+"       "+symt.get(i).getValue()+"       "+symt.get(i).getOffset())+System.getProperty("line.separator"));
			}
			output.flush();
			output.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}	

	@SuppressWarnings("resource")
	public SYM_T lookup(String idname){
		String inputG="Sym_table.txt";	
		try{
			FileInputStream inG=new FileInputStream(inputG);
			BufferedReader strG=new BufferedReader(new InputStreamReader(inG));
			String line="";
			int linecount=0;
			while((line=strG.readLine())!=null){
				linecount++;
				if(linecount>1){
					Pattern p=Pattern.compile("^(\\S+)(\\s+)([^\\s]+)(\\s+)([^\\s]+)(\\s+)(\\d+)$");
					Matcher m=p.matcher(line);
					try{
						while(m.find()){
							String ite=m.group(1);
							if(ite.equals(idname)){
								String add=m.group(7);
								return new SYM_T(ite,m.group(3),Integer.valueOf(add),m.group(5));
							}
						}
					}catch(Exception e){
					}
				}
			}
		}catch(Exception e){
		}
		return null;
	}
	
	public static void main(String args[]){
	/*	SYM_T os=lookup("c");
		if(os!=null){
			System.out.println(os);
		}else{
			System.out.println("Have No!");
		}*/
	}
}
