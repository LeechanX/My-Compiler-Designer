package CFanalyze;

import java.util.ArrayList;
import java.util.Stack;
import java.io.*;

public class CFanalyze {

	static symbolrecord symbolrecord=new symbolrecord();
	
	static sym_table sym=new sym_table();
	static value_table val=new value_table();

	public static Boolean isAlpha(char ch) {  
		return ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || ch == '_');  
	}  

	public static Boolean isDigit(char ch) {  
		return (ch >= '0' && ch <= '9');  
	}  

	public static boolean isRightDig(String str){// if this is number
		return (str.matches("-?[0-9]+")||str.matches("-?[0-9]+.[0-9]+")
				||str.matches("-?[1-9]+e([+-]?([0-9]+.)?[0-9]+)")||
				str.matches("-?[0-9]+.[0-9]+e(-?([0-9]+.)?[0-9]+)")
				); 
	} 

	public static boolean isKey(String str){
		return true;
	}

	@SuppressWarnings({ "resource", "static-access" })
	public void CFmain(String yourcode) {

		/******************************************************************************/
		String temps="tmpCode.txt";
		String input="tmpCode.txt";
		String result="result.txt";
		String result_token="result_token.txt";
		
		Stack<String> stack1=new Stack<String>();   //  put in {,if } pop
		Stack<String> stack2=new Stack<String>();   //  put in (,if ) pop
		Stack<String> stack3=new Stack<String>();   //  put in [,if ] pop
		try{
			BufferedWriter outputtmp=new BufferedWriter(new FileWriter(temps));
			outputtmp.write(String.format(yourcode));
			outputtmp.flush();
			outputtmp.close();
			FileInputStream incode=new FileInputStream(input);
			BufferedReader strcode=new BufferedReader(new InputStreamReader(incode));	
			BufferedWriter output=new BufferedWriter(new FileWriter(result));	
			BufferedWriter output_token=new BufferedWriter(new FileWriter(result_token));
			String line="";
			int count=1;
			boolean ifNote=false;
			int begin=0,end=0;
			String note="";

			while((line=strcode.readLine())!=null){
				System.out.println("Line "+count+" :");
				count++;
				output.write(String.format("Line "+count+":")+System.getProperty("line.separator"));

				if(line.matches("^\\s*$")){
					System.out.println("空行！");
					output.write(String.format("空行！")+System.getProperty("line.separator"));
				}

				if(!ifNote){
					begin=line.indexOf("/*");
					ifNote=false;
					end=line.indexOf("*/");
					if(begin>-1){
						if(end>-1){
							note=line.substring(begin, end+2);
							line=line.substring(0, begin)+line.substring(end+2,line.length());
							System.out.println("注释是："+note);
							output.write(String.format("注释是："+note)+System.getProperty("line.separator"));
							note="";
							ifNote=false;
						}else{
							ifNote=true;
							note=line.substring(begin,line.length());
							line=line.substring(0, begin);
						}	    
					}
				}else if(ifNote){
					end=line.indexOf("*/");
					if(end>-1){
						ifNote=false;
						note+=line.substring(0,end+2);
						line=line.substring(end+2,line.length());
						System.out.println("注释是："+note);
						output.write(String.format("注释是："+note)+System.getProperty("line.separator"));
						note="";
					}
					else{
						note+=line;
						continue;
					}
				}

					
				char[] strLine=line.toCharArray();

				for(int i=0;i<strLine.length;i++){
					String tmpToken="";
					char ch=strLine[i];
					if(isAlpha(ch)){
						while((isAlpha(ch)||isDigit(ch))&&ch!='\0'){
							tmpToken+=ch;
							i++;
							if(i==strLine.length){
								break;
							}
							ch=strLine[i];
						}// Have gotten chars;
						int pos=symbolrecord.isKeyWord(tmpToken);
						if(pos>=0 && pos!=14 && pos!=15){  //suggest it is key word 
							System.out.println(tmpToken+"   <"+pos+",--->");           	
							output.write(String.format(tmpToken+"   <"+pos+",--->")+System.getProperty("line.separator"));
							output_token.write(String.format(String.valueOf(pos))+System.getProperty("line.separator"));
						}else if(pos==14 || pos==15){
							if(pos==14){
								System.out.println(tmpToken+"   <"+pos+",布尔常量 1>");           	
								output.write(String.format(tmpToken+"   <"+pos+",布尔常量 1>")+System.getProperty("line.separator"));	
								output_token.write(String.format("i")+System.getProperty("line.separator"));
							}else{
								System.out.println(tmpToken+"   <"+pos+",布尔常量 0");           	
								output.write(String.format(tmpToken+"   <"+pos+",布尔常量 0")+System.getProperty("line.separator"));
								output_token.write(String.format("j")+System.getProperty("line.separator"));
							}
						}
						else{//suggest it is biaoshifu
							//if(tablelist.isEmpty() || !isExist(tmpToken)){
								sym_table new_sym=new sym_table(tablelist.size(),1,tmpToken);
								tablelist.add(new_sym); 
								System.out.println(tmpToken+"    <1,"+(tablelist.size()-1)+">");
								output.write(String.format(tmpToken+"    <1,"+(tablelist.size()-1)+">")+System.getProperty("line.separator"));
								output_token.write(String.format("y")+System.getProperty("line.separator"));
								/*}else{
								System.out.println("TIPS :    标识符重复了!");
								System.out.println(tmpToken+"    <1,"+(tablelist.size()-1)+">");
								output.write(String.format(tmpToken+"    <1,"+(tablelist.size()-1)+">")+System.getProperty("line.separator"));
								output_token.write(String.format("y")+System.getProperty("line.separator"));
							}*/
						}
						i--;
						tmpToken="";
					}else if(isDigit(ch)){//Del with number
						while(ch!='\0'&&(isDigit(ch)||ch=='.'||
								ch=='e'||(ch=='-'&&strLine[i-1]=='e')||(ch=='+'&&strLine[i-1]=='e'))){
							tmpToken+=ch;
							i++;
							if(i==strLine.length){
								break;
							}
							ch=strLine[i];
						}
						if(isRightDig(tmpToken)){
							if(tmpToken.indexOf(".")==-1&&tmpToken.indexOf("e")==-1 ){
								System.out.println(tmpToken+"     <2,整数>");
								valuelist.add(new value_table(valuelist.size(),tmpToken,"int"));
								output.write(String.format(tmpToken+"     <2,整数>")+System.getProperty("line.separator"));								
								output_token.write(String.format("z")+System.getProperty("line.separator"));
							}else if(tmpToken.indexOf("e")>-1||tmpToken.indexOf(".")>-1 ){
								System.out.println(tmpToken+"     <3,浮点数>");
								valuelist.add(new value_table(valuelist.size(),tmpToken,"float"));
								output.write(String.format(tmpToken+"     <3,浮点数>")+System.getProperty("line.separator"));
								output_token.write(String.format("x")+System.getProperty("line.separator"));
							}
						}else{
							System.out.println("ERROR: "+tmpToken+" 错误的数字输入！");
							//output.write(String.format("ERROR: "+tmpToken+" 错误的数字输入！")+System.getProperty("line.separator"));
						}
						i--;
						tmpToken="";
					}else if(ch=='\''){//del with char_const
						boolean RightChar=false;
						for(int x=i+1;x<strLine.length;x++){
							ch=strLine[x];
							if(ch=='\''){
								RightChar=true;
								break;
							}      
							tmpToken+=ch;
							i=x+1;
						}
						if(RightChar){
							if(tmpToken.length()>1 ||tmpToken.length()==0){
								System.out.println("ERROR： 字符长度只能是1!");
								//output.write(String.format("ERROR： 字符长度只能是1!")+System.getProperty("line.separator"));
							}else{
								System.out.println(tmpToken+"     <4,字符常量>");
								valuelist.add(new value_table(valuelist.size(),tmpToken,"char"));
								//output.write(String.format(tmpToken+"     <4,字符常量>")+System.getProperty("line.separator"));
								output_token.write(String.format("@")+System.getProperty("line.separator"));
							}
						}else{
							System.out.println("字符符号不封闭->"+tmpToken);
							//output.write(String.format("字符符号不封闭->"+tmpToken)+System.getProperty("line.separator"));
						}
					}else if(ch=='"'){//del with 
						boolean RightChars=false;
						for(int x=i+1;x<strLine.length;x++){
							ch=strLine[x];
							if(ch=='"'){
								RightChars=true;
								break;
							}      
							tmpToken+=ch;
							i=x+1;
						}
						if(RightChars){
							System.out.println("\""+tmpToken+"\""+"     <5,字符串常量>");
							//valuelist.add(new value_table(valuelist.size(),tmpToken));
							output.write(String.format("\""+tmpToken+"\""+"     <5,字符串常量>")+System.getProperty("line.separator"));
							output_token.write(String.format(String.valueOf("$"))+System.getProperty("line.separator"));
						}else{
							System.out.println("字符串不封闭->"+tmpToken);
							//output.write(String.format("字符串不封闭->"+tmpToken)+System.getProperty("line.separator"));
						}
						tmpToken="";
					}else if(ch=='/'){//del with / and /* and //
						tmpToken+=ch;
						i++;
						if(i==strLine.length){
							break;
						}
						ch=strLine[i];
						if(ch!='/' && ch!='*'){//suggest it is divide /
							if(ch=='='){//  /=
								tmpToken+=ch;
								System.out.println(tmpToken+"     <"+symbolrecord.isOper2(tmpToken)+",--->");
								output.write(String.format(tmpToken+"      <"+symbolrecord.isOper2(tmpToken)+",--->")+System.getProperty("line.separator"));
								//output_token.write(String.format(String.valueOf(symbolrecord.isOper2(tmpToken)))+System.getProperty("line.separator"));							
  							}else{//  /
								i--;
								System.out.println(tmpToken+"     <"+symbolrecord.isOper('/')+",--->");
								output.write(String.format(tmpToken+"     <"+symbolrecord.isOper('/')+",--->")+System.getProperty("line.separator"));								
								output_token.write(String.format("/")+System.getProperty("line.separator"));
   							}
							//！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！//

						}else{// // or /*
							if(ch=='/'){
								i++;
								System.out.println(line.substring(i-2)+"   是注释");
								//output.write(String.format(line.substring(i-2)+"   是注释")+System.getProperty("line.separator"));
								i=strLine.length;
							}
						}
						tmpToken="";
					}
					else if(symbolrecord.isOper(ch)>0){//del with oper & jie fu
						if((ch=='+'||ch=='-'||ch=='*'||ch=='-'||ch=='='||ch=='<'||
								ch=='<'||ch=='>'||ch=='!'||ch=='?')&&(strLine[i+1]=='=')){
							tmpToken+=ch;
							i++;
							if(i>=strLine.length){
								break;
							}
							ch=strLine[i]; 
							tmpToken+=ch;
							System.out.println(tmpToken+"      <"+symbolrecord.isOper2(tmpToken)+",操作码>");
							output.write(String.format(tmpToken+"         <"+symbolrecord.isOper2(tmpToken)+",操作码>")+System.getProperty("line.separator"));
							output_token.write(String.format(tmpToken+System.getProperty("line.separator")));
						}else if((ch=='+'||ch=='-'||ch=='&'||ch=='|')&&strLine[i+1]==ch){
							tmpToken+=ch;
							i++;
							if(i>=strLine.length){
								break;
							}
							ch=strLine[i]; 
							tmpToken+=ch;
							System.out.println(tmpToken+"          <"+symbolrecord.isOper2(tmpToken)+",操作码>");
							output.write(String.format(tmpToken+"         <"+symbolrecord.isOper2(tmpToken)+",操作码>")+System.getProperty("line.separator"));
							output_token.write(String.format(tmpToken+System.getProperty("line.separator")));
						}else if(ch=='{'){
							tmpToken+=ch;
							stack1.push(String.valueOf(ch));
							System.out.println(tmpToken+"         <"+symbolrecord.isOper(ch)+",操作码>");
							output.write(String.format(tmpToken+"         <"+symbolrecord.isOper(ch)+",操作码>")+System.getProperty("line.separator"));
							output_token.write(String.format(tmpToken+System.getProperty("line.separator")));
						}else if(ch=='}'){
							tmpToken+=ch;
							if(!stack1.empty()){
								stack1.pop();
								System.out.println(tmpToken+"           <"+symbolrecord.isOper(ch)+",操作码>");
								output.write(String.format(tmpToken+"        <"+symbolrecord.isOper(ch)+",操作码>")+System.getProperty("line.separator"));
								output_token.write(String.format(tmpToken+System.getProperty("line.separator")));
							}else if(stack1.empty()){
								System.out.println("ERROR ：这个符号或之前的这个没有起始！ "+ch);
								//output.write(String.format("ERROR ：这个符号没有起始！ "+ch)+System.getProperty("line.separator"));
							}
						}
						else if(ch=='('){
							tmpToken+=ch;
							stack2.push(String.valueOf(ch));
							System.out.println(tmpToken+"        <"+symbolrecord.isOper(ch)+",操作码>");
							output.write(String.format(tmpToken+"         <"+symbolrecord.isOper(ch)+",操作码>")+System.getProperty("line.separator"));
							output_token.write(String.format(tmpToken+System.getProperty("line.separator")));
						}else if(ch==')'){
							tmpToken+=ch;
							if(!stack2.empty()){
								stack2.pop();
								System.out.println(tmpToken+"         <"+symbolrecord.isOper(ch)+",操作码>");
								output.write(String.format(tmpToken+"         <"+symbolrecord.isOper(ch)+",操作码>")+System.getProperty("line.separator"));
								output_token.write(String.format(tmpToken+System.getProperty("line.separator")));
 							}else if(stack2.empty()){
								System.out.println("ERROR ：这个符号或之前的这个没有起始！ "+ch);
								//output.write(String.format("ERROR ：这个符号没有起始！ "+ch)+System.getProperty("line.separator"));
							}
						}
						else if(ch=='['){
							tmpToken+=ch;
							stack3.push(String.valueOf(ch));
							System.out.println(tmpToken+"        <"+symbolrecord.isOper(ch)+",操作码>");
							output.write(String.format(tmpToken+"         <"+symbolrecord.isOper(ch)+",操作码>")+System.getProperty("line.separator"));
							output_token.write(String.format(tmpToken+System.getProperty("line.separator")));
						}else if(ch==']'){
							tmpToken+=ch;
							if(!stack3.empty()){
								stack3.pop();
								System.out.println(tmpToken+"         <"+symbolrecord.isOper(ch)+",操作码>");
								output.write(String.format(tmpToken+"         <"+symbolrecord.isOper(ch)+",操作码>")+System.getProperty("line.separator"));
								output_token.write(String.format(tmpToken+System.getProperty("line.separator")));
							}else if(stack3.empty()){
								System.out.println("ERROR ：这个符号或之前的这个没有起始！ "+ch);
								//output.write(String.format("ERROR ：这个符号没有起始！ "+ch)+System.getProperty("line.separator"));
							}
						}
						else{
							tmpToken+=ch;
							System.out.println(tmpToken+"         <"+symbolrecord.isOper(ch)+",操作码>");
							output.write(String.format(tmpToken+"         <"+symbolrecord.isOper(ch)+",操作码>")+System.getProperty("line.separator"));
							output_token.write(String.format(tmpToken+System.getProperty("line.separator")));
						}
					}
					else
					{  
						if(ch != ' ' && ch != '\t')  
						{  
							System.out.printf("%-10c ERROR:\n",ch);
							output.write(String.format(""));
						}  
					}
				}
				output.write(System.getProperty("line.separator"));
			}			
			if(!stack1.empty()){
				while(!stack1.empty()){
					String top=stack1.pop();
					System.out.println("第"+stack1.size()+"个{符号没有结束符 "+top);
				}
			}
			if(!stack2.empty()){
				while(!stack2.empty()){
					String top=stack2.pop();
					System.out.println("第"+stack2.size()+"个（符号没有结束符 "+top);
				}
			}
			if(!stack3.empty()){
				while(!stack3.empty()){
					String top=stack3.pop();
					System.out.println("第"+stack2.size()+"个（符号没有结束符 "+top);
				}
			}
			
			System.out.println("-------------------------------------------------------\r\n位置 名字 类型");
			for(int nu=0;nu<tablelist.size();nu++){
				System.out.println(tablelist.get(nu).position+" "+tablelist.get(nu).sym_name+" "+tablelist.get(nu).type);
			}
			sym.println(tablelist);
			val.println(valuelist);
			output.flush();
			output_token.flush();
			output.close();
			output_token.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		/******************************************************************************/
	}
	static ArrayList<sym_table> tablelist=new ArrayList<sym_table>();
	static ArrayList<value_table> valuelist=new ArrayList<value_table>();

	public static boolean isExist(String str){
		for(int i = 0;i < tablelist.size(); i ++){
			if(tablelist.get(i).sym_name.equals(str)){
				return true;
			}
		}
		return false;
	}
}
