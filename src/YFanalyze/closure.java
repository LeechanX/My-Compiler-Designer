package YFanalyze;

import java.util.*;
import java.util.regex.*;
import java.io.*;
import YYanalyze.Lab3;

public class closure {

	static Lab3 newt=new Lab3();
	
	static String []ps=readG();
	static String [][]psforfirst=transformGforFirst();
	//static first first=new first();

	public String LRtext;
	public char expected;
	public String getLRtext() {
		return LRtext;
	}
	public void setLRtext(String lRtext) {
		LRtext = lRtext;
	}
	public char getExpected() {
		return expected;
	}
	public void setExpected(char expected) {
		this.expected = expected;
	}

	public closure(){

	}

	public closure(String str,char end){
		this.LRtext=str;
		this.expected=end;
	}

	static List <List <closure>> Ilist = new ArrayList<List <closure>>();

	static List <GOTABLE> GoList = new ArrayList<GOTABLE>();

	@SuppressWarnings("resource")
	public static String[] readG(){
		String inputG="myG.txt";	
		try{
			FileInputStream inG=new FileInputStream(inputG);
			BufferedReader strG=new BufferedReader(new InputStreamReader(inG));
			String line="";
			int linecount=0;
			while((line=strG.readLine())!=null){
				linecount++;
			}
			String [] Gline=new String[linecount];
			linecount=0;
			inG=new FileInputStream(inputG);
			strG=new BufferedReader(new InputStreamReader(inG));
			while((line=strG.readLine())!=null){
				Gline[linecount]=line;
				linecount++;
			}
			return Gline;
		}catch(Exception e){
			return null;
		}
	}

	public static String[][] transformGforFirst(){
		String []ls=readG();
		String [][]fs=new String[ls.length][2];
		int i=0;
		for(String lsi:ls){
			fs[i][0]=lsi.substring(0,lsi.indexOf("->"));
			fs[i][1]=lsi.substring(lsi.indexOf("->")+2,lsi.length());
			i++;

		}
		return fs;
	}

	public static List <closure> closureset(List <closure> initlist,closure clo){

		if(initlist.size()==0){
			initlist.add(clo);
		}

		String A="",a="",B="",b="",e="";

		String Exp="^(\\w){1}->([^.]*).([^.]{0,1})([^.]*)$";

		Pattern p=Pattern.compile(Exp);
		Matcher m=p.matcher(clo.LRtext);
		try{
			while(m.find()){
				A=m.group(1);
				a=m.group(2);
				B=m.group(3);
				b=m.group(4);
				if(a==null){
					a="";
				}
				if(B==null){
					B="";
				}
				if(b==null){
					b="";
				}
			}

			List<String> alist=findn(B.charAt(0));

			e=getFirsts(b+clo.expected);

			for(int k=0;k<e.length();k++){
				for(int i=0;i<alist.size();i++){
					closure clouse2;
					if(alist.get(i).equals("")){
						clouse2=new closure(A+"->."+alist.get(i),e.charAt(k));
					}else{
						clouse2=new closure(B+"->."+alist.get(i),e.charAt(k));
					}

					if(!isExist(initlist,clouse2)){
						initlist.add(clouse2);
						closureset(initlist,clouse2);
					}else{
					}
				}
			}
			return initlist;
		}catch(Exception e2){
			return initlist;
		}
	}

	public static List<String> findn(char head){
		List <String> nl=new ArrayList<String>();
		for(int i=1;i<ps.length;i++){
			if(ps[i].charAt(0)==head){
				nl.add(ps[i].substring(3, ps[i].length()));
			}
		}
		return nl;
	}

	/*
	 * the Following is GO function 
	 * */

	public static List <closure> GO(List <closure> li,char ch){
		// get . char ,this char
		List <closure> sonList=new ArrayList<closure>();
		for(int i=0;i<li.size();i++){
			if(li.get(i).LRtext.indexOf('.')+1!=li.get(i).LRtext.length()){
				if(li.get(i).LRtext.charAt(li.get(i).LRtext.indexOf('.')+1)==ch){
					StringBuffer buffer=new StringBuffer(li.get(i).LRtext);
					buffer.setCharAt(li.get(i).LRtext.indexOf('.'), li.get(i).LRtext.charAt(li.get(i).LRtext.indexOf('.')+1));
					buffer.setCharAt(li.get(i).LRtext.indexOf('.')+1, '.');
					String bufferS=buffer.toString();
					closure clo=new closure(bufferS,li.get(i).expected);
					if(!sonList.contains(clo))
						sonList.add(clo);
				}
			}
		}

		int len=sonList.size();
		for(int k=0;k<len;k++){
			//System.out.println(sonList.get(k).LRtext+","+sonList.get(k).expected);
			sonList=closureset(sonList,sonList.get(k));
		}
		return sonList;
	}


	public static boolean isExist(List<closure> list,closure clo){
		for(int i=0;i<list.size();i++){
			if(list.get(i).LRtext.equals(clo.LRtext) && list.get(i).expected==clo.expected){
				return true;
			}
		}
		return false;
	}

	/*
	 * end of GO*/

	public String YFmain(String testsh) throws IOException{

		first.setPs(psforfirst);
		String firstStr="";

		String action_result="action_result.txt";
		String goto_result="goto_result.txt";

		BufferedWriter action_output=new BufferedWriter(new FileWriter(action_result));
		BufferedWriter goto_output=new BufferedWriter(new FileWriter(goto_result));

		firstStr=ps[0].substring(0, ps[0].indexOf("->")+2)+'.'+ps[0].substring(ps[0].indexOf("->")+2,ps[0].length());
		List <closure> list1 = new ArrayList<closure>();

		list1=closureset(list1,new closure(firstStr,'#'));

		Ilist.add(list1);
		GoList.add(new GOTABLE(list1,0,-1,' '));

		getAlllist(new GOTABLE(list1,0,-1,' '));

		List <ACTION_TABLE> lis=createtable(ps,GoList);
		for(int wa=0;wa<lis.size();wa++){
			action_output.write(String.format(GoList.get(wa).name+": "));
			action_output.write(String.format(lis.get(wa).ch+"")+System.getProperty("line.separator"));
			action_output.write(String.format(lis.get(wa).value+"")+System.getProperty("line.separator"));
		}
		/*System.out.println("+++++++++++++GOTO_TABLE IS AS FOLLOWED:+++++++++++++++++++++++++++++++");*/
		List <GOTO_TABLE> lis2=createtable_goto(ps,GoList);
		/*System.out.println(lis2.size()+" lis.size!!!!!!!!");*/
		for(int wao=0;wao<lis2.size();wao++){
			goto_output.write(String.format(GoList.get(wao).name+": "));
			goto_output.write(String.format(lis2.get(wao).ch+"")+System.getProperty("line.separator"));
			goto_output.write(String.format(lis2.get(wao).value+"")+System.getProperty("line.separator"));
		}
		
		action_output.flush();
		action_output.close();
		goto_output.flush();
		goto_output.close();
		return LRanalyze(testsh,lis,lis2);
	}

	static int count=0;	
	public static void getAlllist(GOTABLE firstlist){
		Stack <Character> stack = new Stack <Character> ();
		for(int i=0;i<firstlist.Iitem.size();i++){
			if(firstlist.Iitem.get(i).LRtext.indexOf('.')+1!=firstlist.Iitem.get(i).LRtext.length()){
				if(!stack.contains(firstlist.Iitem.get(i).LRtext.charAt(firstlist.Iitem.get(i).LRtext.indexOf('.')+1))){
					stack.push(firstlist.Iitem.get(i).LRtext.charAt(firstlist.Iitem.get(i).LRtext.indexOf('.')+1));
				}
			}
		}
		int len=stack.size();
		for(int j=0;j<len;j++){
			char tmp=stack.pop();
			List <closure> list = new ArrayList <closure> ();
			list=GO(firstlist.Iitem,tmp);
			if(!ifinIlist(list)){
				count++;
				int name=count;
				Ilist.add(list);
				GoList.add(new GOTABLE(list,name,firstlist.name,tmp));
				getAlllist(new GOTABLE(list,name,firstlist.name,tmp));
			}
		}
	}

	public static boolean ifinIlist(List<closure> testlist){
		for(int i=0;i<Ilist.size();i++){
			if(equals(Ilist.get(i),testlist)){
				return true;
			}
		}
		return false;
	}

	public static int ifinIlist_findname(List<closure> testlist){
		for(int i=0;i<GoList.size();i++){
			if(equals(GoList.get(i).Iitem,testlist)){
				return GoList.get(i).name;
			}
		}
		return -1;
	}

	public static boolean equals(List<closure> listone,List<closure> listtwo){
		if(listone.size()!=listtwo.size())
			return false;
		else{
			for(int i=0;i<listtwo.size();i++){
				if(!isExist(listone,listtwo.get(i))){
					return false;
				}
			}
			return true;
		}
	}

	/*
	 *   Construct the action table
	 * */
	public static List <ACTION_TABLE> createtable(String ps[],List <GOTABLE> GoList){

		List <ACTION_TABLE> actionlist=new ArrayList <ACTION_TABLE> (); 

		String str="";
		for(String item : ps){
			item=item.substring(0,1)+item.substring(3, item.length());
			str+=item;
		}
		str=str.substring(1, str.length());

		Stack <Character> stack = new Stack <Character> ();

		for(int i=0;i<str.length();i++){
			if(!stack.contains(str.charAt(i)))
				stack.push(str.charAt(i));
		}	
		stack.push('#');

		for(int j=0;j<GoList.size();j++){
			//every row:
			ACTION_TABLE action=new ACTION_TABLE();
			for(char stackitem : stack){
				if(!(stackitem<='Z' && stackitem >='A')){
					int key=ifinIlist_findname(GO(GoList.get(j).Iitem,stackitem));
					if(key>=0){
						action.ch.add(stackitem);
						action.value.add("S"+Integer.toString(key));
					}else{
						int hit=condition2(GoList.get(j).Iitem,stackitem);
						if(hit>-1){
							if(hit==1){
								action.ch.add(stackitem);
								action.value.add("acc");
							}else{
								action.ch.add(stackitem);
								action.value.add("r"+Integer.valueOf(hit));
							}
						}
						else{
							action.ch.add(stackitem);
							action.value.add("error");
						}
					}
				}
			}
			actionlist.add(action);
		}
		return actionlist;
	}

	/*
	 *   Construct the action table
	 * */
	public static List <GOTO_TABLE> createtable_goto(String ps[],List <GOTABLE> GoList){
		List <GOTO_TABLE> gotolist = new ArrayList<GOTO_TABLE>(); 

		String str="";
		for(String item : ps){
			item=item.substring(0,1)+item.substring(3, item.length());
			str+=item;
		}
		str=str.substring(1, str.length());

		Stack <Character> stack = new Stack <Character> ();

		for(int i=0;i<str.length();i++){
			if(!stack.contains(str.charAt(i)))
				stack.push(str.charAt(i));
		}	

		for(int j=0;j<GoList.size();j++){
			//every row:
			GOTO_TABLE gotos = new GOTO_TABLE();
			for(char stackitem : stack){
				if(stackitem<='Z' && stackitem >='A'){
					int key=ifinIlist_findname(GO(GoList.get(j).Iitem,stackitem));
					if(key>=0){
						gotos.ch.add(stackitem);
						gotos.value.add(Integer.toString(key));
					}else{
						gotos.ch.add(stackitem);
						gotos.value.add("error");						
					}
				}
			}
			gotolist.add(gotos);
		}
		return gotolist;
	}

	public static int condition2(List<closure> list,char ch){

		for(closure item:list){
			if(item.expected==ch){
				if(item.LRtext.indexOf('.')==item.LRtext.length()-1){
					String used=item.LRtext.substring(0,item.LRtext.indexOf('.'))+item.LRtext.substring(item.LRtext.indexOf('.')+1,item.LRtext.length());
					int fran=ifinG(readG(),used);
					if(fran>-1){
						return fran;
					}
				}
			}
		}
		return -1;
	}

	public static int ifinG(String[] ps,String str){
		for(int i=0;i<ps.length;i++){
			if(ps[i].equals(str)){
				return i+1; 
			}
		}
		return -1;
	}

	public static boolean exits(char ch,String str){
		for(int i=0;i<str.length();i++){
			if(ch==str.charAt(i)){
				return true;
			}
		}
		return false;
	}

	public static boolean isV(char ch){
		if (ch>='A' && ch <='Z')
			return true;		
		return false;
	}
	public static String getFirst(char ch){   // get First Set (ch)
		String result="";
		String itsfirstch="";
		if(isV(ch)){
			itsfirstch=itsFirst(ch);
			for(int i=0;i<itsfirstch.length();i++){
				if(!exits(itsfirstch.charAt(i),result))
					result+=getFirst(itsfirstch.charAt(i));
			}
		}
		else{/* it is T*/
			if(!exits(ch,result))
				result+=String.valueOf(ch);		
			//System.out.println("2");
		}
		return result;
	}

	public static String getFirsts(String set){   //  get First Set (String)
		String result="";
		result+=getFirst(set.charAt(0));
		return result;
	}

	public static String itsFirst(char ch){

		String res = "";
		for(int i=0;i<psforfirst.length;i++){
			if(psforfirst[i][0].charAt(0)==ch){
				if(psforfirst[i][1].equals("")){
					res+='#';
				}else{
					if(psforfirst[i][1].charAt(0)!=ch){
						res+=psforfirst[i][1].charAt(0);
					}
				}
			}
		}
		return res;
	}
	///end of first  thing
	/*
	 *    This is new job
	 * */
	static TranslateG.Translate tran=new TranslateG.Translate();
	
	@SuppressWarnings("resource")
	public static String LRanalyze(String test,List <ACTION_TABLE> actiontab,List <GOTO_TABLE> gototab) throws IOException{
		System.out.println("-------------------Here   we   come   in    analyze-----------------------");
	
		String output_an="out_analyze.txt";
		String outsr="usewhat.txt";
		BufferedWriter output=new BufferedWriter(new FileWriter(output_an));
		BufferedWriter output2=new BufferedWriter(new FileWriter(outsr));
	
		Stack <Character> chStack = new Stack <Character> ();
		Stack <Integer> statusStack = new Stack <Integer> ();
		chStack.push('#');
		statusStack.push(0);
		String tempStr=test+"#";
		int ip=0;
		String tmpuse;
		while(true){
			output.write(String.format("Status Stack now have : "+putsStack(statusStack))+System.getProperty("line.separator"));
			output.write(String.format("Char Stack now have : "+putsStack(chStack))+System.getProperty("line.separator"));
			int topstatus=statusStack.peek();
			if((tmpuse=find_in_action(actiontab,topstatus,tempStr.charAt(ip)))!=null){
				if(tmpuse.charAt(0)=='S'){
					int stmt=0; 
					if(tmpuse.length()==2){
						stmt=tmpuse.charAt(1)-48;
					}
					else if(tmpuse.length()>2){
						stmt=Integer.valueOf(tmpuse.substring(1,tmpuse.length()));
					}
 
					output.write(String.format("Use Action->"+tmpuse)+System.getProperty("line.separator"));
					statusStack.push(stmt);
					chStack.push(tempStr.charAt(ip));
					output.write(String.format("Status Stack push "+tempStr.charAt(ip))+System.getProperty("line.separator"));
					output.write(String.format("Char Stack push : "+tempStr.charAt(ip))+System.getProperty("line.separator"));
					
					ip++;
				}else if(tmpuse.charAt(0)=='r'){
					int linenum=0;
					if(tmpuse.length()==2){
						linenum=tmpuse.charAt(1)-48;
					}
					else if(tmpuse.length()>2){
						linenum=Integer.valueOf(tmpuse.substring(1,tmpuse.length()));
					}
					//newt.getUse(linenum);
					output2.write(String.format(""+linenum)+System.getProperty("line.separator"));
					int length=ps[linenum-1].length()-3;
					for(int k=0;k<length;k++){
						chStack.pop();
						statusStack.pop();
					}
					output.write(String.format("Status and Char Stack pop "+length+" element(s)")+System.getProperty("line.separator"));
					int topstatus2=statusStack.peek();
					chStack.push(ps[linenum-1].charAt(0));
					String tmpStr=find_in_goto(gototab,topstatus2,ps[linenum-1].charAt(0));
					if(tmpStr!=null){
						statusStack.push(Integer.parseInt(tmpStr));
						output.write(String.format("Status Stack push "+Integer.parseInt(tmpStr))+System.getProperty("line.separator"));
					}
					output.write(String.format("Use Goto->"+tmpuse)+System.getProperty("line.separator"));
				}else if(tmpuse.equals("acc")){
					System.out.println("Have been accepted!");
					output.write(String.format("Now Accepted!")+System.getProperty("line.separator"));
					output2.write(String.format("1")+System.getProperty("line.separator"));
					output.flush();
					output.close();
					output2.flush();
					output2.close();
					//newt.showAllUse();
					return "Have been accepted!";
				}else if(tmpuse.equals("error")){
					System.out.println("Error in "+tempStr.charAt(ip)+" character!");
					output.write(String.format("Error!")+System.getProperty("line.separator"));
					output.flush();
					output.close();
					if(tempStr.charAt(ip)=='#')
						return "Error in the end!";
					return "Error !\n"+tran.find_error(ip);
				}
			}
		}
		

	}

	public static String find_in_action(List <ACTION_TABLE> actiontab,int status,char ch){
		//actiontab.get(0).value
		List <Character> templist=actiontab.get(status).ch;
		int ret=0;
		String res="";
		for(Character item:templist){
			if(item==ch){
				res = actiontab.get(status).value.get(ret);
				return res;
			}
			ret++;
		}
		return null;
	}
	public static String find_in_goto(List <GOTO_TABLE> gototab,int status,char ch){
		//actiontab.get(0).value
		List <Character> templist=gototab.get(status).ch;
		int ret=0;
		for(Character item:templist){
			if(item==ch){
				return gototab.get(status).value.get(ret);
			}
			ret++;
		}
		//System.out.println(res+"~~~~~~~~~~~~~~~~~~~~");
		return null;
	}	

	public static void printstack(Stack<?> stack){
		int len=stack.size();
		for(int i=0;i<len;i++){
			//System.out.println(stack.peek());
			stack.pop();
		}
	}
	
	public static String putsStack(Stack<?> stack){
		String things="";
		for(Object item:stack){
			things+=item.toString();
		}
		return things;
	}
}





