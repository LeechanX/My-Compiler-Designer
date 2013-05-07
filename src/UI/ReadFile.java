package UI;

import java.io.*;

public class ReadFile {

	@SuppressWarnings("resource")
	public String readCFresult() throws IOException{
		String input="result_token.txt";

		FileInputStream incode=new FileInputStream(input);
		BufferedReader strcode=new BufferedReader(new InputStreamReader(incode));	
		String line="";
		String result="";
		while((line=strcode.readLine())!=null){
			result+=line+'\n';
		}
		return result;
	}
}
