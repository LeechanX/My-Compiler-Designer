package YFanalyze;


/*
 * This is First (X)  X is a ch
 * */



public class first {
/*	static String [][]ps=new String[8][2];*/
	static String [][]ps=new String[1][2];
	
	public static String[][] getPs() {
		return ps;
	}

	public static void setPs(String[][] ps) {
		first.ps = ps;
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
			//System.out.println("1");
			itsfirstch=itsFirst(ch);
			//System.out.println(itsfirstch);
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
	
	public String getFirsts(String set){   //  get First Set (String)
		String result="";
		
		//for(int i=0;i<set.length();i++){
	
			result+=getFirst(set.charAt(0));
		//}
	
		return result;
	}
	
	public static String itsFirst(char ch){
	
		String res = "";
		for(int i=0;i<ps.length;i++){
			if(ps[i][0].charAt(0)==ch){
				if(!ps[i][1].equals(""))
			    res+=ps[i][1].charAt(0);
				else
					res+='#';
			}
		}
		return res;
	}
	
	public static void main(String args[]){
		
		/*ps[0][0]="E";ps[0][1]="TA";ps[1][0]="A";ps[1][1]="+TA";
		ps[2][0]="A";ps[2][1]="#";ps[3][0]="T";ps[3][1]="FB";
		ps[4][0]="B";ps[4][1]="*FB";ps[5][0]="B";ps[5][1]="#";
		ps[6][0]="F";ps[6][1]="(E)";ps[7][0]="F";ps[7][1]="a";
		*/
		ps[0][0]="A";ps[0][1]="";/*ps[1][0]="S";ps[1][1]="L=R";
		ps[2][0]="S";ps[2][1]="R";ps[3][0]="L";ps[3][1]="*R";
		ps[4][0]="L";ps[4][1]="i";ps[5][0]="R";ps[5][1]="L";*/
		System.out.println(getFirst('A'));
		
		//System.out.println(getFirsts("R"));
 	}
	
}
