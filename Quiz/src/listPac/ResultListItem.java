package listPac;


public class ResultListItem {
	private int resource ; //画像のリソースID
	private String selfAnswer ; //自分の回答
	private String answer ; //問題の答え
	private boolean errata ; //正誤情報
	
	public void setResource(int resource){
		this.resource = resource ;
	}
	
	public int getResource(){
		return resource ;
	}
	
	public void setSelfAnswer(String selfAnswer){
		this.selfAnswer = selfAnswer ;
	}
	
	public String getSelfAnswer(){
		return selfAnswer ;
	}
	
	public void setAnswer(String answer){
		this.answer = answer ;
	}
	
	public String getAnswer(){
		return answer ;
	}
	
	public void setErrata(boolean errata){
		this.errata = errata ;
	}
	
	public boolean getErrata(){
		return errata ;
	}
}
