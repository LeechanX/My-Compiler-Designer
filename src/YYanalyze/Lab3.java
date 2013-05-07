package YYanalyze;

import java.io.BufferedReader;
import HBproduce.HBgenerate;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;
import java.util.Stack;
import java.util.regex.*;
import java.util.Iterator;

import CFanalyze.value_table;

public class Lab3 {

	static List <String> huseG=new ArrayList<String>(); 

	static SYM_T imp=new SYM_T();

	@SuppressWarnings("resource")
	public static void getUse(){
		String use="usewhat.txt";
		List <Integer> usew=new ArrayList<Integer>();
		try{
			FileInputStream inU=new FileInputStream(use);
			BufferedReader strU=new BufferedReader(new InputStreamReader(inU));
			String line="";
			while((line=strU.readLine())!=null){
				int xt=Integer.valueOf(line);
				usew.add(xt);
			}
		}catch(Exception e){
		}

		String inputG="myG.txt";
		for(int ie : usew){
			try{
				FileInputStream inG=new FileInputStream(inputG);
				BufferedReader strG=new BufferedReader(new InputStreamReader(inG));
				String line="";

				int linecount=0;
				while((line=strG.readLine())!=null){
					linecount++;
					if(linecount==ie){
						huseG.add(line);
						//break;
					}
				}

			}catch(Exception e){
			}
		}
	}

	@SuppressWarnings("resource")
	public static List<String> getallid(){
		List <String> idName=new ArrayList<String>();
		String inputG="tmpSym_table.txt";	
		try{
			FileInputStream inG=new FileInputStream(inputG);
			BufferedReader strG=new BufferedReader(new InputStreamReader(inG));
			String line="";
			int linecount=0;
			while((line=strG.readLine())!=null){
				linecount++;
				if(linecount>1){
					Pattern p=Pattern.compile("^(\\d+)(\\s+)([^\\s]+)(\\s+)\\S*$");
					Matcher m=p.matcher(line);
					try{
						while(m.find()){
							idName.add(m.group(3));
						}
					}catch(Exception e){
						break;
					}
				}
			}
		}catch(Exception e){

		}
		return idName;
	}

	@SuppressWarnings("resource")
	public static List<value_table> getallvalue(){
		List <value_table> valuelist=new ArrayList<value_table>();
		String inputG="tmpVal_table.txt";	
		try{
			FileInputStream inG=new FileInputStream(inputG);
			BufferedReader strG=new BufferedReader(new InputStreamReader(inG));
			String line="";
			while((line=strG.readLine())!=null){
				Pattern p=Pattern.compile("^(\\d+)(\\s+)([^\\s]+)(\\s+)([^\\s]+)$");
				Matcher m=p.matcher(line);
				try{
					while(m.find()){
						value_table vt=new value_table(Integer.valueOf(m.group(1)),m.group(3),m.group(5));
						valuelist.add(vt);
					}
				}catch(Exception e){

				}
			}
		}catch(Exception e){

		}
		return valuelist;
	}

	public static void showAllUse() throws IOException{
		int offset=0;
		
		List <String> allidname=getallid();

		int NameTag=0;

		List <value_table> allvalue=getallvalue();
		int ValueTag=0;
		Stack <TD> stack=new Stack<TD>();
		Stack <SYM_T> stack_tmp=new Stack<SYM_T>();
		
		Stack <SYM_T> bool_stack=new Stack<SYM_T>();

		int le=huseG.size();
		List <SYM_T> symt=new ArrayList<SYM_T>();

		String[] copyuseG=new String[le];
		int ji=0;
		while(!huseG.get(ji).equals("A->B")){
			copyuseG[ji]=huseG.get(ji);
			ji++;
		}
		copyuseG[ji]="A->B";

		int flag=1;
		for(int k=0;k<copyuseG.length;k++){ 
			if(k>=copyuseG.length){break;}
			else if(copyuseG[k].length()==0){
				break;
			}
			String left=String.valueOf(copyuseG[k].charAt(0));
			String right=copyuseG[k].substring(3,copyuseG[k].length());
			if(right.length()>1){
				if(right.charAt(1)=='-'||right.charAt(1)=='+'||right.charAt(1)=='*'||right.charAt(1)=='/'){		
					if(k+1<le){
						String temLeft=left;
						left="t"+Integer.toString(flag);
						copyuseG[k]=left+"->"+right;
						flag++;
						if(copyuseG[k+1].substring(copyuseG[k+1].length()-1,copyuseG[k+1].length()).equals(temLeft)){
							copyuseG[k+1]=copyuseG[k+1].substring(0,copyuseG[k+1].length()-1)+left;
						}
					}	
				}
			}
		}

		for(int j=0;j<copyuseG.length;j++){
			String item=copyuseG[j];
			if(item.equals("E->Gy;")){
				char ty=copyuseG[j-1].charAt(3);
				if(ty=='a'){
					symt=enter(allidname.get(NameTag),"int",offset,"null",symt);
					offset+=4;
				}else if(ty=='b'){
					symt=enter(allidname.get(NameTag),"float",offset,"null",symt);
					offset+=8;
				}else if(ty=='c'){
					symt=enter(allidname.get(NameTag),"char",offset,"null",symt);
					offset+=2;
				}else if(ty=='d'){
					symt=enter(allidname.get(NameTag),"boolean",offset,"null",symt);
					offset+=1;
				}else if(ty=='e'){
					symt=enter(allidname.get(NameTag),"string",offset,"null",symt);
					offset+=10;
				}
				NameTag++;
			}else if(item.equals("E->Gy[z];")){
				String big=allvalue.get(ValueTag).getValue();
				int ibig=0;
				if(big.length()==1){
					ibig=big.charAt(0)-48;
				}else{
					ibig=Integer.valueOf(big);
				}
				char ty=copyuseG[j-1].charAt(3);
				if(ty=='a'){
					symt=enter(allidname.get(NameTag),"int",offset,"null",symt);
					offset+=4*ibig;
				}else if(ty=='b'){
					symt=enter(allidname.get(NameTag),"float",offset,"null",symt);
					offset+=8*ibig;
				}else if(ty=='c'){
					symt=enter(allidname.get(NameTag),"char",offset,"null",symt);
					offset+=2*ibig;
				}else if(ty=='d'){
					symt=enter(allidname.get(NameTag),"boolean",offset,"null",symt);
					offset+=ibig;
				}else if(ty=='e'){
					symt=enter(allidname.get(NameTag),"string",offset,"null",symt);
					offset+=ibig*10;
				}
				NameTag++;
				ValueTag++;
			}
		}
		imp.println(symt);
		/*for(TD L : stack){
			System.out.println(L.left.getIdname()+" "+L.right.getIdname()+" "+L.right.getOffset());
		}*/
		for(int j=0;j<copyuseG.length;j++){
			String item=copyuseG[j];
			if(item.equals("T->y")||item.equals("T->y[z]")){
				int time=1;
				SYM_T left=new SYM_T();
				SYM_T right=new SYM_T();
				if(item.equals("T->y")){	
					String idname=allidname.get(NameTag);
					NameTag++;
					right=right.lookup(idname);
					//System.out.println("得到y的属性如下："+right.getIdname()+" "+right.getOffset()+" "+right.getValue()+" "+right.getType());
					left.setOffset(right.getOffset());
					left.setType(right.getType());
					left.setValue(right.getValue());
					left.setIdname("T");
					stack.push(new TD(left,right));
					/*System.out.println("入栈内容如下："+left.getIdname()+"_>"+right.getIdname());
					System.out.println("现在栈里有：");
					for(TD se : stack){
						System.out.println(se.left.getIdname()+" "+se.right.getIdname());
					}*/
				}else if(item.equals("T->y[z]")){
					String idna=allidname.get(NameTag);
					NameTag++;
					String big=allvalue.get(ValueTag).getValue();
					ValueTag++;
					int ibig=Integer.valueOf(big);
					right=right.lookup(idna);
					int arrayoffset=right.getOffset();
					//System.out.println("得到y的属性如下："+right.getIdname()+" "+right.getOffset()+" "+right.getValue()+" "+right.getType());
					if(right.getType().equals("int")){
						arrayoffset=arrayoffset+4*ibig;
					}else if(right.getType().equals("float")){
						arrayoffset=arrayoffset+8*ibig;
					}else if(right.getType().equals("char")){
						arrayoffset=arrayoffset+2*ibig;
					}
					right.setOffset(arrayoffset);
					left.setOffset(right.getOffset());
					left.setType(right.getType());
					left.setValue(right.getValue());
					left.setIdname("T");
					stack.push(new TD(left,right));
					/*System.out.println("入栈内容如下："+left.getIdname()+"_>"+right.getIdname());
					System.out.println("现在栈里有：");
					for(TD se : stack){
						System.out.println(se.left.getIdname()+" "+se.right.getIdname());
					}*/
				}

				while(true){
					item=copyuseG[j+time];
					if(item.equals("X->z")){
						SYM_T lefte=new SYM_T();
						SYM_T righte=new SYM_T();
						righte.setIdname("");
						String val=allvalue.get(ValueTag).getValue();
						String newtype=allvalue.get(ValueTag).getType();
						righte.setType(newtype);
						ValueTag++;
						righte.setValue(val);
						righte.setOffset(-1);
						lefte.setType(newtype);
						lefte.setOffset(-1);
						lefte.setIdname("X");
						lefte.setValue(right.getValue());
						stack.push(new TD(lefte,righte));
						/*System.out.println("入栈内容如下："+lefte.getIdname()+"_>"+righte.getValue());
						System.out.println("现在栈里有：");
						for(TD se : stack){
							System.out.println(se.left.getIdname()+" "+se.right.getIdname());
						}*/
					}else if(item.equals("X->@")){
						SYM_T lefte=new SYM_T();
						SYM_T righte=new SYM_T();
						righte.setIdname("");
						String val=allvalue.get(ValueTag).getValue();
						String newtype=allvalue.get(ValueTag).getType();
						righte.setType(newtype);
						ValueTag++;
						righte.setValue(val);
						righte.setOffset(-1);
						lefte.setType(newtype);
						lefte.setOffset(-1);
						lefte.setIdname("X");
						lefte.setValue(right.getValue());
						stack.push(new TD(lefte,righte));
						/*System.out.println("入栈内容如下："+lefte.getIdname()+"_>"+righte.getValue());
						System.out.println("现在栈里有：");
						for(TD se : stack){
							System.out.println(se.left.getIdname()+" "+se.right.getIdname());
						}*/
					}else if(item.equals("X->x")){
						SYM_T lefte=new SYM_T();
						SYM_T righte=new SYM_T();
						righte.setIdname("");
						String val=allvalue.get(ValueTag).getValue();
						String newtype=allvalue.get(ValueTag).getType();
						righte.setType(newtype);
						ValueTag++;
						righte.setValue(val);
						righte.setOffset(-1);
						lefte.setType(newtype);
						lefte.setOffset(-1);
						lefte.setIdname("X");
						lefte.setValue(right.getValue());
						stack.push(new TD(lefte,righte));
						/*System.out.println("入栈内容如下："+lefte.getIdname()+"_>"+righte.getValue());
						System.out.println("现在栈里有：");
						for(TD se : stack){
							System.out.println(se.left.getIdname()+" "+se.right.getIdname());
						}*/
					}else if(item.equals("T->y")){
						SYM_T lefte=new SYM_T();
						SYM_T righte=new SYM_T();
						String idna=allidname.get(NameTag);
						NameTag++;
						righte=righte.lookup(idna);
						//System.out.println("得到y的属性如下："+righte.getIdname()+" "+righte.getOffset()+" "+righte.getValue()+" "+righte.getType());
						lefte.setOffset(righte.getOffset());
						lefte.setType(righte.getType());
						lefte.setValue(righte.getValue());
						lefte.setIdname("T");
						stack.push(new TD(lefte,righte));
						/*System.out.println("入栈内容如下："+left.getIdname()+"_>"+right.getIdname());
						System.out.println("现在栈里有：");
						for(TD se : stack){
							System.out.println(se.left.getIdname()+" "+se.right.getIdname());
						}*/
					}else if(item.equals("T->y[z]")){
						SYM_T lefte=new SYM_T();
						SYM_T righte=new SYM_T();
						String idna=allidname.get(NameTag);
						NameTag++;
						String big=allvalue.get(ValueTag).getValue();
						ValueTag++;
						int ibig=Integer.valueOf(big);
						righte=righte.lookup(idna);
						int arrayoffset=righte.getOffset();
						//System.out.println("得到y的属性如下："+righte.getIdname()+" "+righte.getOffset()+" "+righte.getValue()+" "+righte.getType());
						if(righte.getType().equals("int")){
							arrayoffset=arrayoffset+4*ibig;
						}else if(righte.getType().equals("float")){
							arrayoffset=arrayoffset+8*ibig;
						}else if(righte.getType().equals("char")){
							arrayoffset=arrayoffset+2*ibig;
						}
						righte.setOffset(arrayoffset);
						lefte.setOffset(righte.getOffset());
						lefte.setType(righte.getType());
						lefte.setValue(righte.getValue());
						lefte.setIdname("T");
						stack.push(new TD(lefte,righte));
						/*System.out.println("入栈内容如下："+left.getIdname()+"_>"+right.getIdname());
						System.out.println("现在栈里有：");
						for(TD se : stack){
							System.out.println(se.left.getIdname()+" "+se.right.getIdname());
						}*/
					}
					else{
						String left_name=item.substring(0,item.indexOf("->"));
						String right_name=item.substring(item.indexOf("->")+2, item.length());
						if(right_name.equals("T=S")){
							SYM_T extend=new SYM_T();
							SYM_T newleft=new SYM_T();
							
							if("S".equals(stack.peek().left.getIdname())){
								extend=stack.peek().right;
								stack.pop();
							}
							if("T".equals(stack.peek().left.getIdname())){
								newleft=stack.peek().right;
								stack.pop();
							}

							if(newleft.getIdname()==null && newleft.getType()==null){
								newleft=stack_tmp.peek();
								stack_tmp.pop();
							}

							System.out.println("右部的类型是"+extend.getType()+"，想把值付给左部，类型为"+newleft.getType());
							if(!extend.getType().equals(newleft.getType())){
								System.out.println("不合法的赋值！");
								return;
							}
							//System.out.println("将写入四元式的表达式如下：");
							if(extend.getOffset()==-2){
								//System.out.println("= "+" #"+extend.getIdname()+" "+" #  "+newleft.getOffset() );
								gencode("=","#"+extend.getIdname(),"#",String.valueOf(newleft.getOffset()));
							}else if(extend.getOffset()==-1){
								//System.out.println("= "+" "+extend.getValue()+" "+" #  "+newleft.getOffset() );
								gencode("=","$"+extend.getValue(),"#",String.valueOf(newleft.getOffset()));
  							}else{
								//System.out.println("= "+" "+extend.getOffset()+" "+" #  "+newleft.getOffset() );
  								gencode("=",String.valueOf(extend.getOffset()),"#",String.valueOf(newleft.getOffset()));
  							}

							break;
						}else{
							if(right_name.equals("(S)"))right_name="S";
							//System.out.println("现在右部是"+right_name);
							if(!stack.empty()&&right_name.equals(stack.peek().left.getIdname())){
								//System.out.println("而栈顶是"+stack.peek().left.getIdname());
								SYM_T extend=new SYM_T();
								//=stack.peek().right;
								extend.setIdname(stack.peek().right.getIdname());
								extend.setOffset(stack.peek().right.getOffset());
								extend.setType(stack.peek().right.getType());
								extend.setValue(stack.peek().right.getValue());
								SYM_T newleft=new SYM_T();
								newleft.setIdname(left_name);
								newleft.setOffset(stack.peek().left.getOffset());
								newleft.setType(stack.peek().left.getType());
								newleft.setValue(stack.peek().left.getValue());
								stack.pop();
								stack.push(new TD(newleft,extend));
								//System.out.println("入栈元素："+newleft.getIdname()+"_>"+extend.getIdname());
							}else if(right_name.length()>1&& (right_name.charAt(1)=='+'||right_name.charAt(1)=='-'||right_name.charAt(1)=='*'||right_name.charAt(1)=='/')){
								//System.out.println("现在栈里有：");
								/*for(TD se : stack){
									System.out.println(se.left.getIdname()+" "+se.right.getIdname());
								}*/
								String arg1=String.valueOf(right_name.charAt(0));
								String arg2=right_name.substring(2,right_name.length());
								SYM_T TDarg1=new SYM_T();
								SYM_T TDarg2=new SYM_T();
								if(!stack.empty()&&arg2.equals(stack.peek().left.getIdname())){
									TDarg2=stack.peek().right;
									stack.pop();
								}else if(arg2.equals(stack_tmp.peek().getIdname())){
									TDarg2=stack_tmp.peek();
									stack_tmp.pop();
								}
								if(!stack.empty()&&arg1.equals(stack.peek().left.getIdname())){
									TDarg1=stack.peek().right;
									stack.pop();
								}else if(arg1.equals(stack_tmp.peek().getIdname())){
									TDarg1=stack_tmp.peek();
									stack_tmp.pop();
								}
								if(TDarg1.getIdname()==null && TDarg1.getType()==null){

									TDarg1=stack_tmp.peek();
									stack_tmp.pop();
									stack.pop();
								}
								/*System.out.println("现在栈里有：");
								for(TD se : stack){
									System.out.println(se.left.getIdname()+" "+se.right.getIdname());
								}*/
								///////////////TYPE MATCHER!!!!!!!!!!!!!!!!!!!!!!
								SYM_T Result=new SYM_T();
								Result.setIdname(left_name);
								Result.setOffset(-2);
								System.out.println("第一个参数类型"+TDarg1.getType()+"，第二个参数类型"+TDarg2.getType());
								if(right_name.charAt(1)=='*'||right_name.charAt(1)=='/'){
									if(TDarg1.getType().equals("char")||TDarg2.getType().equals("char")){
										System.out.println("不允许字符参与乘除操作！");
										return;
									}else if(TDarg1.getType().equals("int")&&TDarg2.getType().equals("int")){
										Result.setType("int");
									}else{
										Result.setType("float");
									}
								}else if(right_name.charAt(1)=='+'||right_name.charAt(1)=='-'){
									if(TDarg1.getType().equals("char")&&TDarg2.getType().equals("char")){
										Result.setType("char");
									}else{
										if(TDarg1.getType().equals("char")||TDarg2.getType().equals("char")){
											System.out.println("字符只能和字符进行加法减法");
											return;
										}else if(TDarg1.getType().equals("float")||TDarg2.getType().equals("float")){
											Result.setType("float");
										}else{
											Result.setType("int");
										}
									}
								}
								Result.setValue("null");
								stack_tmp.push(Result);
								stack.push(new TD(Result,Result));
								String a,b;
								a=String.valueOf(TDarg1.getOffset());
								if(TDarg1.getOffset()==-2){
									a=TDarg1.getIdname();
								}else if(TDarg1.getOffset()==-1){
									a="$"+TDarg1.getValue();
								}

								b=String.valueOf(TDarg2.getOffset());
								if(TDarg2.getOffset()==-2){
									b="#"+TDarg2.getIdname();
								}else if(TDarg2.getOffset()==-1){
									b="$"+TDarg2.getValue();
								}
								//System.out.println("中间变量产生式为:"+right_name.charAt(1)+" "+a+" "+b+" "+Result.getIdname());
								gencode(String.valueOf(right_name.charAt(1)),a,b,Result.getIdname());
								
							}
						}
					}
					time++;
				}
				j=j+time;	
			}else if(item.equals("I->z")||item.equals("I->y")){
				SYM_T container=new SYM_T();
				if(item.endsWith("y")){
					String idna=allidname.get(NameTag);
					NameTag++;
					container=container.lookup(idna);
					bool_stack.push(container);
				}else{
					container.setIdname("");
					String val=allvalue.get(ValueTag).getValue();
					String newtype=allvalue.get(ValueTag).getType();
					container.setType(newtype);
					ValueTag++;
					container.setValue(val);
					container.setOffset(-1);
					bool_stack.push(container);
				}
			}else if(item.startsWith("H->I")){
				SYM_T bool2=bool_stack.pop();
				SYM_T bool1=bool_stack.pop();
			//	System.out.println("四元式是：");
				String op="";
				String a1="",a2="";
				
				if(item.length()==6)op=item.substring(4,5);
				else if(item.length()==7)op=item.substring(4,6);
				if(bool1.getIdname().length()==0){
					a1="$"+bool1.getValue();
				}else{
					a1=String.valueOf(bool1.getOffset());
				}
				if(bool2.getIdname().length()==0){
					a2="$"+bool2.getValue();
				}else{
					a2=String.valueOf(bool2.getOffset());
				}
				
				if(bool2.getType().equals("char")&& !bool1.getType().equals("char")){
					System.out.println("字符不可以和别的类型做对比！");
					return;
				}else if(bool1.getType().equals("char")&& !bool2.getType().equals("char")){
					System.out.println("字符不可以和别的类型做对比！");
					return;
				}else if(!bool1.getType().equals("char")&& !bool2.getType().equals("char")){
					
				}
				
				//if(bool2.getIdname().length()==0)
				//System.out.println("if "+bool1.getIdname()+" "+op+"#"+bool2.getValue()+" goto _");
					gencode("j"+op,a1,a2,"_");
					gencode("j","_","_","_");
				/*else
					//System.out.println("if "+bool1.getIdname()+" "+op+" "+bool2.getIdname()+" goto _");
					gencode("j"+op,bool1.getIdname(),"#"+bool2.getValue(),"_");
				*///System.out.println("goto _");
			}else if(item.equals("J->:")){
				//System.out.println("Start:");
				gencode("Start","","","");
			}else if(item.equals("C->;")){
				//System.out.println("End:");
				gencode("End","","","");
			}else if(item.equals("Y->::")){
				//System.out.println("End:");
				gencode("Startif","","","");
			}else if(item.equals("F->y")||item.equals("F->y[z]")){
				if(item.equals("F->y")){
				if(copyuseG[j+1].equals("Z->l(F)")){
					String idna=allidname.get(NameTag);
					NameTag++;
					SYM_T print=new SYM_T();
					print=print.lookup(idna);
					gencode("print",String.valueOf(print.getOffset()),"_","_");
				}
				}else{
					if(copyuseG[j+1].equals("Z->l(F)")){
						String idna=allidname.get(NameTag);
						NameTag++;
						String big=allvalue.get(ValueTag).getValue();
						int ibig=Integer.valueOf(big);
						ValueTag++;
						SYM_T print=new SYM_T();
						print=print.lookup(idna);
						int arrayoffset=print.getOffset();
						if(print.getType().equals("int")){
							arrayoffset=arrayoffset+4*ibig;
						}else if(print.getType().equals("float")){
							arrayoffset=arrayoffset+8*ibig;
						}else if(print.getType().equals("char")){
							arrayoffset=arrayoffset+2*ibig;
						}
						gencode("print",String.valueOf(arrayoffset),"_","_");
					}	
				}
			}else if(item.equals("Z->k(y)")||item.equals("Z->k(y[z])")){
				if(item.equals("Z->k(y)")){
					SYM_T inp=new SYM_T();
					String idna=allidname.get(NameTag);
					NameTag++;
					inp=inp.lookup(idna);
					gencode("scanner",String.valueOf(inp.getOffset()),"_","_");
				}else{
					String idna=allidname.get(NameTag);
					NameTag++;
					String big=allvalue.get(ValueTag).getValue();
					int ibig=Integer.valueOf(big);
					ValueTag++;
					SYM_T inp=new SYM_T();
					inp=inp.lookup(idna);
					int arrayoffset=inp.getOffset();
					if(inp.getType().equals("int")){
						arrayoffset=arrayoffset+4*ibig;
					}else if(inp.getType().equals("float")){
						arrayoffset=arrayoffset+8*ibig;
					}else if(inp.getType().equals("char")){
						arrayoffset=arrayoffset+2*ibig;
					}
					gencode("scanner",String.valueOf(arrayoffset),"_","_");
				}
			}
		}
		backpath();
		backpath_else();
		backpath_end();
		clear();
		/*System.out.println("=========================BEGIN=====================================");
		printall();
		System.out.println("=========================END=======================================");*/
		System.out.println("=========================HBSTART===================================");
		HBgenerate hb=new HBgenerate();
		hb.checkDoor(Quatlist);
		hb.makeHB(Quatlist);
		System.out.println("=========================HBEND=====================================");
	}

	public static List <SYM_T>  enter(String idname,String idtype,int offset,String value,List <SYM_T> symt){
		symt.add(new SYM_T(idname,idtype,offset,value));
		return symt;
	}
	public static void main(String args[]) throws IOException{
		getUse();
		showAllUse();
	}
	
	static int gencodenum=0;
	
	public static List <QuaternionDetail> Quatlist=new ArrayList <QuaternionDetail> ();
	
	public static void gencode(String op,String arg1,String arg2,String res){
		Quaternion qua=new Quaternion(op,arg1,arg2,res);
		if(!op.startsWith("Start")){
			gencodenum++;
			Quatlist.add(new QuaternionDetail(qua,gencodenum));
		}else{
			int addr=gencodenum+1;
			Quatlist.add(new QuaternionDetail(qua,addr));
		}
	}
	
	public static void printall() throws IOException{
		
		String outText="Quaternion.txt";
		BufferedWriter output=new BufferedWriter(new FileWriter(outText));
		
		for(QuaternionDetail ite : Quatlist){
			System.out.println(ite.getAddr()+" "+ite.getQuaternion().getOp()+" "+ite.getQuaternion().getArg1()+" "+ite.getQuaternion().getArg2()
					+" "+ite.getQuaternion().getResult());
			output.write(String.format(ite.getAddr()+" "+ite.getQuaternion().getOp()+" "+ite.getQuaternion().getArg1()+" "+ite.getQuaternion().getArg2()
					+" "+ite.getQuaternion().getResult())+System.getProperty("line.separator"));
		}
		output.flush();
		output.close();
		
	}
	
	@SuppressWarnings("resource")
	public String readOut() throws IOException{
		String input="Quaternion.txt";
		FileInputStream incode=new FileInputStream(input);
		BufferedReader strcode=new BufferedReader(new InputStreamReader(incode));
		String line="";
		String res="";
		while((line=strcode.readLine())!=null){
			res+=line+"\n";
		}
		return res;
	}
	
	public static void backpath(){
		for(int j=0;j<Quatlist.size();j++){
			if(Quatlist.get(j).getQuaternion().getOp().equals("Start")){
				Quatlist.get(j-2).getQuaternion().setResult(String.valueOf(Quatlist.get(j).getAddr()));
			}else if(Quatlist.get(j).getQuaternion().getOp().equals("Startif")){
				if(Quatlist.get(j-2).getQuaternion().getOp().startsWith("j")&&Quatlist.get(j-1).getQuaternion().getOp().startsWith("j")){
					Quatlist.get(j-2).getQuaternion().setResult(String.valueOf(Quatlist.get(j).getAddr()));
				}else{
					for(int k=j-1;k>=0;k--){
						if(Quatlist.get(k).getQuaternion().getOp().equals("Startif")){
							if(Quatlist.get(k-2).getQuaternion().getOp().startsWith("j")&&Quatlist.get(k-1).getQuaternion().getOp().startsWith("j")){
								Quatlist.get(k-1).getQuaternion().setResult(String.valueOf(Quatlist.get(j).getAddr()));
							}
						}
					}
				}
			}
		}
	}
	
	public static void backpath_else(){   // while else
		Stack <String> stack_while = new Stack <String> ();
		for(int j=0;j<Quatlist.size();j++){
			if(Quatlist.get(j).getQuaternion().getOp().startsWith("j")){
				if(Quatlist.get(j+1).getQuaternion().getOp().startsWith("j")){
					if(Quatlist.get(j+2).getQuaternion().getOp().equals("Start")){
						stack_while.push("Start");
						for(int k=j+3;k<Quatlist.size();k++){
							if(Quatlist.get(k).getQuaternion().getOp().startsWith("Start")){
								stack_while.push(Quatlist.get(k).getQuaternion().getOp());
							}else if(Quatlist.get(k).getQuaternion().getOp().equals("End")){
								stack_while.pop();
								if(stack_while.empty()){
									Quatlist.get(j+1).getQuaternion().setResult(String.valueOf(Quatlist.get(k).getAddr()));
									Quatlist.get(k).getQuaternion().setArg1("_");
									Quatlist.get(k).getQuaternion().setArg2("_");
									Quatlist.get(k).getQuaternion().setOp("j");
									Quatlist.get(k).getQuaternion().setResult(String.valueOf(Quatlist.get(k).getAddr()+1));
									break;
								}
							}
						}
					}
				}
			}
		}
	}
	
	public static void backpath_end(){
		Stack <String> stack_ifend = new Stack <String> ();
		// deal with if end   to make if end & else end 
		for(int j=0;j<Quatlist.size();j++){
			if(Quatlist.get(j).getQuaternion().getOp().equals("End")&&Quatlist.get(j+1).getQuaternion().getOp().equals("Startif")){
				for(int k=j+1;k<Quatlist.size();k++){
					if(Quatlist.get(k).getQuaternion().getOp().equals("Startif")){
						stack_ifend.push("Startif");
					}else if(Quatlist.get(k).getQuaternion().getOp().equals("End")){
						stack_ifend.pop();
						if(stack_ifend.empty()){
							Quatlist.get(k).getQuaternion().setArg1("_");
							Quatlist.get(k).getQuaternion().setArg2("_");
							Quatlist.get(k).getQuaternion().setResult(String.valueOf(Quatlist.get(k).getAddr()+1));
							Quatlist.get(k).getQuaternion().setOp("j");
							Quatlist.get(j).getQuaternion().setArg1("_");
							Quatlist.get(j).getQuaternion().setArg2("_");
							Quatlist.get(j).getQuaternion().setResult(String.valueOf(Quatlist.get(k).getAddr()+1));
							Quatlist.get(j).getQuaternion().setOp("j");
						}
					}
				}
			}
		}
	}
	
	public static void clear(){
		//int i=0;
		Iterator <QuaternionDetail> iterator = Quatlist.iterator();
		while(iterator.hasNext()){
			QuaternionDetail qit=iterator.next();
			if(qit.getQuaternion().getOp().startsWith("Start")){
				iterator.remove();
			}
		}
		
	}
}
