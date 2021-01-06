package cho.carbon.hc.web.poll;

import java.util.LinkedList;

public class MessagesSequence {
	private LinkedList<Message> messages = new LinkedList<>();
	private int beginIndex;
	private int endIndex;
	
	MessagesSequence(){
		
	}
	
	public LinkedList<Message> getMessages() {
		return messages;
	}
	public int getBeginIndex() {
		return beginIndex;
	}
	void setBeginIndex(int beginIndex) {
		this.beginIndex = beginIndex;
	}
	public int getEndIndex() {
		return endIndex;
	}
	void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}
	
	
	
	
}
