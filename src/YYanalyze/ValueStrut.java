package YYanalyze;

public class ValueStrut {
	private String type;
	private String value;
	private int addr;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public int getAddr() {
		return addr;
	}
	public void setAddr(int addr) {
		this.addr = addr;
	}
	public ValueStrut(String value, int addr) {
		this.value = value;
		this.addr = addr;
		
		if(value.matches("\\d+.\\d+")){
			this.type="float";
		}else if(value.matches("\\d")){
			this.type="int";
		}else if(value.matches("\\S")){
			this.type="char";
		}
		
	}
	
	
}
