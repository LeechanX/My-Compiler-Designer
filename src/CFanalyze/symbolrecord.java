package CFanalyze;

public class symbolrecord {
	// men for keyword,opera,jiefu,
	public String symbol_name;
	public int tag;
	
	public symbolrecord(){}
	
	public symbolrecord(String symbol_name,int tag){
		this.symbol_name=symbol_name;
		this.tag=tag;
	}
	
	public String getSymbol_name() {
		return symbol_name;
	}
	public void setSymbol_name(String symbol_name) {
		this.symbol_name = symbol_name;
	}
	public int getTag() {
		return tag;
	}
	public void setTag(int tag) {
		this.tag = tag;
	}
	
	public static symbolrecord[] newSymbolRecord(){
		symbolrecord [] record=new symbolrecord[48];
		String chars[]={"int","float","char","boolean","string","void","while","return","true","false","scanner","println",
			"if","else","main","+", "-","*", "=", "<", ">",  "!", "?","(", ")", "[", "]", "{", "}", ";", ",","/","==","&","|",">=","<=","!=",":"};
		for(int a=0;a<39;a++){
			record[a]=new symbolrecord(chars[a],a+6);
		}
		return record;
	}
	
	public static int isKeyWord(String str){
		symbolrecord[] record=newSymbolRecord();
		for(int i=0;i<15;i++){
			if(str.equals(record[i].symbol_name)){
				return record[i].tag;
			}
		}
		return -1;
	}

	public int isOper(char ch){
		String str = String.valueOf(ch);
		symbolrecord[] record=newSymbolRecord();
		for(int i=15;i<39;i++){
			if(str.equals(record[i].symbol_name)){
				return record[i].tag;
			}
		}
		return -1;
	}
	
	public int isOper2(String ch){
		symbolrecord[] record=newSymbolRecord();
		for(int i=14;i<39;i++){
			if(ch.equals(record[i].symbol_name)){
				return record[i].tag;
			}
		}
		return -1;
	}
	public static void main(String agrs[]){
		System.out.println(isKeyWord("true"));
	}
}


