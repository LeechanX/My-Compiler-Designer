package TranslateG;

import java.io.*;

public class Translate {
	
	public static String transform(int tag){
		String []tab={"","","","","","","a","b","c","d","e","f","g","h","","","k","l","t","u","s"};
		return tab[tag];
	}
	
	public String translateIt(String cood){
		String[] coodArray=null;
		coodArray=cood.split("\n");
		//System.out.println(coodArray.length);
		String alltoken="";
		for(String item:coodArray){
			try{
				if(Integer.parseInt(item)>0){
					alltoken+=transform(Integer.parseInt(item));
				}else if(item.equals("")){
					
				}
			}catch(Exception e){
				alltoken+=item;
			}
		}
		return alltoken;
	}
	
	@SuppressWarnings("resource")
	public String find_error(int ip){
		String input="result.txt";
		try{
			FileInputStream incode=new FileInputStream(input);
			BufferedReader strcode=new BufferedReader(new InputStreamReader(incode));
			String line="";
			int tag=0;
			int lineNum=0;
			while((line=strcode.readLine())!=null){
				if(line.matches("Line \\d:")){
					lineNum++;
				}else if(line.equals("") ||  line.matches("TIPS :\\S+")){
					
				}else{
					tag++;
					if(tag==ip+1){
						return "Your code ( in line "+lineNum+" ): "+line+" You get wrong!";
					}
				}
			}
		}catch(Exception e){
			
		}
		return "";
	}
	
}
