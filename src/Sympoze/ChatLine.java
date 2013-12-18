package Sympoze;



import java.sql.Timestamp;

public class ChatLine {

	private String username;
	private String textMsg;
	private Timestamp insertDate;
	
	public ChatLine(String userName, String message) {	
		username = userName;
		textMsg = message;
	}

	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getTextMsg() {
		return textMsg;
	}
	
	public void setTextMsg(String textMsg) {
		this.textMsg = textMsg;
	}

	public Timestamp getInsertDate() {
		return insertDate;
	}
}


