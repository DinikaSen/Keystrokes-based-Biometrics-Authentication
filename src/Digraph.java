
public class Digraph {

	private char charOne;
	private char charTwo;
	
	private long latency ;
	
	public Digraph(char charOne, char charTwo)
	{
		this.charOne = charOne ;
		this.charTwo = charTwo;
	}
	
	public Digraph(char char1, char char2, long latency){
		this.charOne = char1 ;
		this.charTwo = char2 ;
		this.latency = latency;
	}
	
	public char getCharOne(){
		return charOne;
	}
	
	public void setCharOne(char c){
		this.charOne = c;
	}
	
	public char getCharTwo(){
		return charTwo;
	}
	
	public void setCharTwo(char c){
		this.charTwo = c;
	}
	
	public long getLatency(){
		return latency;
	}
	
	public void setLatency(long latency){
		this.latency = latency;
	}
}
