package YFanalyze;

public class ANALYZE {
	
	char ch;
	int status;
    
	public ANALYZE(){
	}
	
	public ANALYZE(char ch,int status){
		this.ch=ch;
		this.status=status;
	}
	
	public char getCh() {
		return ch;
	}
	public void setCh(char ch) {
		this.ch = ch;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

	
}
