package HBproduce;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import YYanalyze.QuaternionDetail;

public class HBgenerate {

	List <Integer> doorlist = new ArrayList <Integer> ();
	
	public static String HBres="";

	HashMap <Integer,String> doorMap = new HashMap <Integer,String>();

	public void checkDoor(List <QuaternionDetail> QuaterList){
		int number=1;
		for(QuaternionDetail QuaterItem : QuaterList){
			if(QuaterItem.getQuaternion().getOp().startsWith("j")){
				int where=Integer.valueOf(QuaterItem.getQuaternion().getResult());
				if(!doorMap.containsKey(where)){
					doorMap.put(where, ".L"+String.valueOf(number));
					number++;
				}
			}
		}
	}

	public void makeHB(List <QuaternionDetail> QuaterList){

         System.out.println(".section .bss");
         HBres+=".section .bss\n";
         System.out.println(".comm	T0,	4\n.comm	T1,	4\n.comm	T2,	4\n.comm	T3,	4\n.comm	T4,	4\n.comm	T5,	4\n.comm	T6,	4\n.comm	T7,	4\n.comm	T8,	4");
         HBres+=".comm	T0,	4\n.comm	T1,	4\n.comm	T2,	4\n.comm	T3,	4\n.comm	T4,	4\n.comm	T5,	4\n.comm	T6,	4\n.comm	T7,	4\n.comm	T8,	4\n";
         System.out.println(".section .text");
         HBres+=".section .text\n";
         System.out.println(".global main\nmain:");
         HBres+=".global main\nmain:\n";
         System.out.println(" .cfi_startproc\n"+
                            "        pushq   %rbp\n"+
                            "        .cfi_def_cfa_offset 16\n"+
                            "        .cfi_offset 6, -16\n"+
                            "        movq    %rsp, %rbp\n"+
                            "        .cfi_def_cfa_register 6\n");
         HBres+=" .cfi_startproc\n"+
                 "        pushq   %rbp\n"+
                 "        .cfi_def_cfa_offset 16\n"+
                 "        .cfi_offset 6, -16\n"+
                 "        movq    %rsp, %rbp\n"+
                 "        .cfi_def_cfa_register 6\n";
		for(QuaternionDetail QuaterItem : QuaterList){
			if(doorMap.containsKey(QuaterItem.getAddr())){
				System.out.println(doorMap.get(QuaterItem.getAddr()));
				HBres+=doorMap.get(QuaterItem.getAddr())+"\n";
			}
			String oper=QuaterItem.getQuaternion().getOp();

			if(oper.equals("+")){
				if(QuaterItem.getQuaternion().getArg1().startsWith("$")&&
						QuaterItem.getQuaternion().getArg2().startsWith("$")){   // e.g: 1+2
				    System.out.println("        mov %eax,"+QuaterItem.getQuaternion().getArg1());
				    System.out.println("        add %eax,"+QuaterItem.getQuaternion().getArg2());
				    HBres+="        mov %eax,"+QuaterItem.getQuaternion().getArg1()+"\n";
				    HBres+="        add %eax,"+QuaterItem.getQuaternion().getArg2()+"\n";
				}else if(QuaterItem.getQuaternion().getArg1().matches("^t?\\d+$")&&
						QuaterItem.getQuaternion().getArg2().startsWith("$")){  // e.g: a+3 , t1+3
					if(QuaterItem.getQuaternion().getArg1().matches("^\\d+$")){
						System.out.println("        mov %eax,["+QuaterItem.getQuaternion().getArg1()+"]");
						HBres+="        mov %eax,["+QuaterItem.getQuaternion().getArg1()+"]\n";
					}else{
						String which=QuaterItem.getQuaternion().getArg1();
						which="T"+which.substring(1,which.length());
						System.out.println("        mov %eax,"+which+"");
						HBres+="        mov %eax,"+which+"\n";
					}
					System.out.println("        add %eax,"+QuaterItem.getQuaternion().getArg2());
					HBres+="        add %eax,"+QuaterItem.getQuaternion().getArg2()+"\n";
				}else if(QuaterItem.getQuaternion().getArg2().matches("^t?\\d+$")&&
						QuaterItem.getQuaternion().getArg1().startsWith("$")){    // e.g: 3+a
					if(QuaterItem.getQuaternion().getArg2().matches("^\\d+$")){
						System.out.println("        mov %eax,["+QuaterItem.getQuaternion().getArg2()+"]");
						HBres+="        mov %eax,["+QuaterItem.getQuaternion().getArg2()+"]\n";
					}else{
						String which=QuaterItem.getQuaternion().getArg2();
						which="T"+which.substring(1,which.length());
						System.out.println("        mov %eax,"+which+"");
						HBres+="        mov %eax,"+which+"\n";
					}
					System.out.println("        add %eax,"+QuaterItem.getQuaternion().getArg1());
					HBres+="        add %eax,"+QuaterItem.getQuaternion().getArg1()+"\n";
				}else if(QuaterItem.getQuaternion().getArg2().matches("^t?\\d+$")&&
						QuaterItem.getQuaternion().getArg1().matches("^t?\\d+$")){    // e.g: a+b
					if(QuaterItem.getQuaternion().getArg1().matches("^\\d+$")){
						System.out.println("        mov %eax,["+QuaterItem.getQuaternion().getArg1()+"]");
						HBres+="        mov %eax,["+QuaterItem.getQuaternion().getArg1()+"]\n";
					}else{
						String which=QuaterItem.getQuaternion().getArg1();
						which="T"+which.substring(1,which.length());
						System.out.println("        mov %eax,"+which+"");
						HBres+="        mov %eax,"+which+"\n";
					}
					if(QuaterItem.getQuaternion().getArg2().matches("^\\d+$")){
						System.out.println("        mov %ebx,["+QuaterItem.getQuaternion().getArg2()+"]");
						HBres+="        mov %ebx,["+QuaterItem.getQuaternion().getArg2()+"]\n";
					}else{
						String which=QuaterItem.getQuaternion().getArg2();
						which="T"+which.substring(1,which.length());
						System.out.println("        mov %ebx,"+which+"");
						HBres+="        mov %ebx,"+which+"\n";
					}
					System.out.println("        add %eax,%ebx");
					HBres+="        add %eax,%ebx\n";
				}
				String re=QuaterItem.getQuaternion().getResult();
				re="T"+re.substring(1,re.length());
				System.out.println("        mov "+re+",%eax");
				HBres+="        mov "+re+",%eax\n";
			}else if(oper.equals("-")){
				if(QuaterItem.getQuaternion().getArg1().startsWith("$")&&
						QuaterItem.getQuaternion().getArg2().startsWith("$")){   // e.g: 1+2
				    System.out.println("        mov %eax,"+QuaterItem.getQuaternion().getArg1());
				    System.out.println("        sub %eax,"+QuaterItem.getQuaternion().getArg2());
				    HBres+="        mov %eax,"+QuaterItem.getQuaternion().getArg1()+"\n";
				    HBres+="        sub %eax,"+QuaterItem.getQuaternion().getArg2()+"\n";
				}else if(QuaterItem.getQuaternion().getArg1().matches("^t?\\d+$")&&
						QuaterItem.getQuaternion().getArg2().startsWith("$")){  // e.g: a+3 , t1+3
					if(QuaterItem.getQuaternion().getArg1().matches("^\\d+$")){
						System.out.println("        mov %eax,["+QuaterItem.getQuaternion().getArg1()+"]");
						HBres+="        mov %eax,["+QuaterItem.getQuaternion().getArg1()+"]\n";
					}else{
						String which=QuaterItem.getQuaternion().getArg1();
						which="T"+which.substring(1,which.length());
						System.out.println("        mov %eax,"+which+"");
						HBres+="        mov %eax,"+which+"\n";
					}
					System.out.println("        sub %eax,"+QuaterItem.getQuaternion().getArg2());
					HBres+="        sub %eax,"+QuaterItem.getQuaternion().getArg2()+"\n";
				}else if(QuaterItem.getQuaternion().getArg2().matches("^t?\\d+$")&&
						QuaterItem.getQuaternion().getArg1().startsWith("$")){    // e.g: 3+a
					if(QuaterItem.getQuaternion().getArg2().matches("^\\d+$")){
						System.out.println("        mov %eax,["+QuaterItem.getQuaternion().getArg2()+"]");
						HBres+="        mov %eax,["+QuaterItem.getQuaternion().getArg2()+"]\n";
					}else{
						String which=QuaterItem.getQuaternion().getArg2();
						which="T"+which.substring(1,which.length());
						System.out.println("        mov %eax,"+which+"");
						HBres+="        mov %eax,"+which+"\n";
					}
					System.out.println("        sub %eax,"+QuaterItem.getQuaternion().getArg1());
					HBres+="        sub %eax,"+QuaterItem.getQuaternion().getArg1()+"\n";
				}else if(QuaterItem.getQuaternion().getArg2().matches("^t?\\d+$")&&
						QuaterItem.getQuaternion().getArg1().matches("^t?\\d+$")){    // e.g: a+b
					if(QuaterItem.getQuaternion().getArg1().matches("^\\d+$")){
						System.out.println("        mov %eax,["+QuaterItem.getQuaternion().getArg1()+"]");
						HBres+="        mov %eax,["+QuaterItem.getQuaternion().getArg1()+"]\n";
					}else{
						String which=QuaterItem.getQuaternion().getArg1();
						which="T"+which.substring(1,which.length());
						System.out.println("        mov %eax,"+which+"");
						HBres+="        mov %eax,"+which+"\n";
					}
					if(QuaterItem.getQuaternion().getArg2().matches("^\\d+$")){
						System.out.println("        mov %ebx,["+QuaterItem.getQuaternion().getArg2()+"]");
						HBres+="        mov %ebx,["+QuaterItem.getQuaternion().getArg2()+"]\n";
					}else{
						String which=QuaterItem.getQuaternion().getArg2();
						which="T"+which.substring(1,which.length());
						System.out.println("        mov %ebx,"+which+"");
						HBres+="        mov %ebx,"+which+"\n";
					}
					System.out.println("        sub %eax,%ebx");
					HBres+="        sub %eax,%ebx\n";
				}
				String re=QuaterItem.getQuaternion().getResult();
				re="T"+re.substring(1,re.length());
				System.out.println("        mov "+re+",%eax");
				HBres+="        mov "+re+",%eax\n";
			}else if(oper.equals("*")){
				if(QuaterItem.getQuaternion().getArg1().startsWith("$")&&
						QuaterItem.getQuaternion().getArg2().startsWith("$")){   // e.g: 1+2
				    System.out.println("        mov %eax,"+QuaterItem.getQuaternion().getArg1());
				    System.out.println("        mul %eax,"+QuaterItem.getQuaternion().getArg2());
				    HBres+="        mov %eax,"+QuaterItem.getQuaternion().getArg1()+"\n";
				    HBres+="        mul %eax,"+QuaterItem.getQuaternion().getArg2()+"\n";
				}else if(QuaterItem.getQuaternion().getArg1().matches("^t?\\d+$")&&
						QuaterItem.getQuaternion().getArg2().startsWith("$")){  // e.g: a+3 , t1+3
					if(QuaterItem.getQuaternion().getArg1().matches("^\\d+$")){
						System.out.println("        mov %eax,["+QuaterItem.getQuaternion().getArg1()+"]");
						HBres+="        mov %eax,["+QuaterItem.getQuaternion().getArg1()+"]\n";
					}else{
						String which=QuaterItem.getQuaternion().getArg1();
						which="T"+which.substring(1,which.length());
						System.out.println("        mov %eax,"+which+"");
						HBres+="        mov %eax,"+which+"\n";
					}
					System.out.println("        mul %eax,"+QuaterItem.getQuaternion().getArg2());
					HBres+="        mul %eax,"+QuaterItem.getQuaternion().getArg2()+"\n";
				}else if(QuaterItem.getQuaternion().getArg2().matches("^t?\\d+$")&&
						QuaterItem.getQuaternion().getArg1().startsWith("$")){    // e.g: 3+a
					if(QuaterItem.getQuaternion().getArg2().matches("^\\d+$")){
						System.out.println("        mov %eax,["+QuaterItem.getQuaternion().getArg2()+"]");
						HBres+="        mov %eax,["+QuaterItem.getQuaternion().getArg2()+"]\n";
					}else{
						String which=QuaterItem.getQuaternion().getArg2();
						which="T"+which.substring(1,which.length());
						System.out.println("        mov %eax,"+which+"");
						HBres+="        mov %eax,"+which+"\n";
					}
					System.out.println("        mul %eax,"+QuaterItem.getQuaternion().getArg1());
					HBres+="        mul %eax,"+QuaterItem.getQuaternion().getArg1()+"\n";
				}else if(QuaterItem.getQuaternion().getArg2().matches("^t?\\d+$")&&
						QuaterItem.getQuaternion().getArg1().matches("^t?\\d+$")){    // e.g: a+b
					if(QuaterItem.getQuaternion().getArg1().matches("^\\d+$")){
						System.out.println("        mov %eax,["+QuaterItem.getQuaternion().getArg1()+"]");
						HBres+="        mov %eax,["+QuaterItem.getQuaternion().getArg1()+"]"+"\n";
					}else{
						String which=QuaterItem.getQuaternion().getArg1();
						which="T"+which.substring(1,which.length());
						System.out.println("        mov %eax,"+which+"");
						HBres+="        mov %eax,"+which+"\n";
					}
					if(QuaterItem.getQuaternion().getArg2().matches("^\\d+$")){
						System.out.println("        mov %ebx,["+QuaterItem.getQuaternion().getArg2()+"]");
						HBres+="        mov %ebx,["+QuaterItem.getQuaternion().getArg2()+"]\n";
					}else{
						String which=QuaterItem.getQuaternion().getArg2();
						which="T"+which.substring(1,which.length());
						System.out.println("        mov %ebx,"+which+"");
						HBres+="        mov %ebx,"+which+"\n";
					}
					System.out.println("        mul %eax,%ebx");
					HBres+="        mul %eax,%ebx\n";
				}
				String re=QuaterItem.getQuaternion().getResult();
				re="T"+re.substring(1,re.length());
				System.out.println("        mov "+re+",%eax");
				HBres+="        mov "+re+",%eax\n";
			}else if(oper.equals("/")){
				if(QuaterItem.getQuaternion().getArg1().startsWith("$")&&
						QuaterItem.getQuaternion().getArg2().startsWith("$")){   // e.g: 1+2
				    System.out.println("        mov %eax,"+QuaterItem.getQuaternion().getArg1());
				    System.out.println("        div %eax,"+QuaterItem.getQuaternion().getArg2());
				    HBres+="        mov %eax,"+QuaterItem.getQuaternion().getArg1()+"\n";
				    HBres+="        div %eax,"+QuaterItem.getQuaternion().getArg2()+"\n";
				}else if(QuaterItem.getQuaternion().getArg1().matches("^t?\\d+$")&&
						QuaterItem.getQuaternion().getArg2().startsWith("$")){  // e.g: a+3 , t1+3
					if(QuaterItem.getQuaternion().getArg1().matches("^\\d+$")){
						System.out.println("        mov %eax,["+QuaterItem.getQuaternion().getArg1()+"]");
						HBres+="        mov %eax,["+QuaterItem.getQuaternion().getArg1()+"]\n";
					}else{
						String which=QuaterItem.getQuaternion().getArg1();
						which="T"+which.substring(1,which.length());
						System.out.println("        mov %eax,"+which+"");
						HBres+="        mov %eax,"+which+"\n";
					}
					System.out.println("        div %eax,"+QuaterItem.getQuaternion().getArg2());
					HBres+="        div %eax,"+QuaterItem.getQuaternion().getArg2()+"\n";
				}else if(QuaterItem.getQuaternion().getArg2().matches("^t?\\d+$")&&
						QuaterItem.getQuaternion().getArg1().startsWith("$")){    // e.g: 3+a
					if(QuaterItem.getQuaternion().getArg2().matches("^\\d+$")){
						System.out.println("        mov %eax,["+QuaterItem.getQuaternion().getArg2()+"]");
						HBres+="        mov %eax,["+QuaterItem.getQuaternion().getArg2()+"]\n";
					}else{
						String which=QuaterItem.getQuaternion().getArg2();
						which="T"+which.substring(1,which.length());
						System.out.println("        mov %eax,"+which+"");
						HBres+="        mov %eax,"+which+"\n";
					}
					System.out.println("        div %eax,"+QuaterItem.getQuaternion().getArg1());
					HBres+="        div %eax,"+QuaterItem.getQuaternion().getArg1()+"\n";
				}else if(QuaterItem.getQuaternion().getArg2().matches("^t?\\d+$")&&
						QuaterItem.getQuaternion().getArg1().matches("^t?\\d+$")){    // e.g: a+b
					if(QuaterItem.getQuaternion().getArg1().matches("^\\d+$")){
						System.out.println("        mov %eax,["+QuaterItem.getQuaternion().getArg1()+"]");
						HBres+="        mov %eax,["+QuaterItem.getQuaternion().getArg1()+"]\n";
					}else{
						String which=QuaterItem.getQuaternion().getArg1();
						which="T"+which.substring(1,which.length());
						System.out.println("        mov %eax,"+which+"");
						HBres+="        mov %eax,"+which+"\n";
					}
					if(QuaterItem.getQuaternion().getArg2().matches("^\\d+$")){
						System.out.println("        mov %ebx,["+QuaterItem.getQuaternion().getArg2()+"]");
						HBres+="        mov %ebx,["+QuaterItem.getQuaternion().getArg2()+"]\n";
					}else{
						String which=QuaterItem.getQuaternion().getArg2();
						which="T"+which.substring(1,which.length());
						System.out.println("        mov %ebx,"+which+"");
						HBres+="        mov %ebx,"+which+"\n";
					}
					System.out.println("        div %eax,%ebx");
					HBres+="        div %eax,%ebx\n";
				}
				String re=QuaterItem.getQuaternion().getResult();
				re="T"+re.substring(1,re.length());
				System.out.println("        mov "+re+",%eax");
				HBres+="        mov "+re+",%eax\n";
			}else if(oper.equals("=")){
				if(QuaterItem.getQuaternion().getArg1().startsWith("$")){  // 立即数传数;
					String res=QuaterItem.getQuaternion().getResult();
					System.out.println("        mov ["+res+"],"+QuaterItem.getQuaternion().getArg1());
					HBres+="        mov ["+res+"],"+QuaterItem.getQuaternion().getArg1()+"\n";
				}else if(QuaterItem.getQuaternion().getArg1().matches("^\\d+$")){   // 变量传数
					System.out.println("        mov %eax,["+QuaterItem.getQuaternion().getArg1()+"]");
					HBres+="        mov %eax,["+QuaterItem.getQuaternion().getArg1()+"]\n";
					String res=QuaterItem.getQuaternion().getResult();
					System.out.println("        mov ["+res+"],%eax");
					HBres+="        mov ["+res+"],%eax\n";
				}else if(QuaterItem.getQuaternion().getArg1().matches("^t\\d+$")){
					String which=QuaterItem.getQuaternion().getArg1();
					which="T"+which.substring(1,which.length());
					System.out.println("        mov %eax,"+which);
					HBres+="        mov %eax,"+which+"\n";
					String res=QuaterItem.getQuaternion().getResult();
					System.out.println("        mov ["+res+"],%eax");
					HBres+="        mov ["+res+"],%eax\n";
				}
			}else if(oper.startsWith("j")){
				int fromdoor=Integer.valueOf(QuaterItem.getQuaternion().getResult());
				String todoor=doorMap.get(fromdoor);
				if(oper.length()==1){
					System.out.println("        jmp "+todoor);//No Condition Jump
					HBres+="        jmp "+todoor+"\n";
				}else if(oper.length()>=2){    //  > , <  , <= , >= , != , ?=
					if(QuaterItem.getQuaternion().getArg1().startsWith("$")&&
							QuaterItem.getQuaternion().getArg2().startsWith("$")){   // e.g: 1<2
						System.out.println("        cmp "+QuaterItem.getQuaternion().getArg1()+","+QuaterItem.getQuaternion().getArg2());
					    HBres+="        cmp "+QuaterItem.getQuaternion().getArg1()+","+QuaterItem.getQuaternion().getArg2()+"\n";
					}else if(QuaterItem.getQuaternion().getArg1().matches("^\\d+$")&&
							QuaterItem.getQuaternion().getArg2().startsWith("$")){  // e.g: a<3
						System.out.println("        mov %eax,["+QuaterItem.getQuaternion().getArg1()+"]");
						HBres+="        mov %eax,["+QuaterItem.getQuaternion().getArg1()+"]\n";
						System.out.println("        cmp %eax,"+QuaterItem.getQuaternion().getArg2());
						HBres+="        cmp %eax,"+QuaterItem.getQuaternion().getArg2()+"\n";
					}else if(QuaterItem.getQuaternion().getArg2().matches("^\\d+$")&&
							QuaterItem.getQuaternion().getArg1().startsWith("$")){    // e.g: 3<a
						System.out.println("        mov %eax,["+QuaterItem.getQuaternion().getArg2()+"]");
						HBres+="        mov %eax,["+QuaterItem.getQuaternion().getArg2()+"]\n";
						System.out.println("        cmp %eax,"+QuaterItem.getQuaternion().getArg1());
						HBres+="        cmp %eax,"+QuaterItem.getQuaternion().getArg1()+"\n";
					}else if(QuaterItem.getQuaternion().getArg2().matches("^\\d+$")&&
							QuaterItem.getQuaternion().getArg1().matches("^\\d+$")){    // e.g: a<b
						System.out.println("        mov %eax,["+QuaterItem.getQuaternion().getArg1()+"]");
						HBres+="        mov %eax,["+QuaterItem.getQuaternion().getArg1()+"]\n";
						System.out.println("        mov %ebx,["+QuaterItem.getQuaternion().getArg2()+"]");
						HBres+="        mov %ebx,["+QuaterItem.getQuaternion().getArg2()+"]\n";
						System.out.println("        cmp %eax,%ebx");
						HBres+="        cmp %eax,%ebx\n";
					}
					if(oper.length()==2&&oper.charAt(1)=='<'){
						System.out.println("        jl "+todoor);
						HBres+="        jl "+todoor+"\n";
					}else if(oper.length()==2&&oper.charAt(1)=='>'){
						System.out.println("        jg "+todoor);
						HBres+="        jg "+todoor+"\n";
					}else if(oper.length()==3&&oper.indexOf("<=")!=-1){
						System.out.println("        jng "+todoor);
						HBres+="        jng "+todoor+"\n";
					}else if(oper.length()==3&&oper.indexOf(">=")!=-1){
						System.out.println("        jnl "+todoor);
						HBres+="        jnl "+todoor+"\n";
					}else if(oper.length()==3&&oper.indexOf("!=")!=-1){
						System.out.println("        jne "+todoor);
						HBres+="        jne "+todoor+"\n";
					}else if(oper.length()==3&&oper.indexOf("?=")!=-1){
						System.out.println("        je "+todoor);
						HBres+="        je "+todoor+"\n";
					}
				}
			}else if(oper.equals("print")){
				System.out.println("        movl ["+QuaterItem.getQuaternion().getArg1()+"],%eax");
				HBres+="        movl ["+QuaterItem.getQuaternion().getArg1()+"],%eax\n";
				System.out.println("        movl %eax, %esi");
				HBres+="        movl %eax, %esi\n";
				System.out.println("        movl $0, %eax");
				HBres+="        movl $0, %eax\n";
				System.out.println("        call printf");
				HBres+="        call printf\n";
			}else if(oper.equals("scanner")){
				System.out.println("        movl $0,%eax");
				HBres+="        movl $0,%eax\n";
				System.out.println("        call __isoc99_scanf");
				HBres+="        call __isoc99_scanf\n";
				System.out.println("        movl %eax,["+QuaterItem.getQuaternion().getArg1()+"]");
				HBres+="        movl %eax,["+QuaterItem.getQuaternion().getArg1()+"]\n";
			}
		}
		System.out.println("        leave");
		System.out.println("        popq    %rbp\n"+
                           "        .cfi_def_cfa 7, 8\n"+
                           "        ret\n"+
                           "        .cfi_endproc\n");
		HBres+="        leave\n";
		HBres+="        popq    %rbp\n"+
                "        .cfi_def_cfa 7, 8\n"+
                "        ret\n"+
                "        .cfi_endproc\n";
	}

}