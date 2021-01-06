package cho.carbon.hc.web.poll;

import java.util.ArrayList;
import java.util.List;

public class ProgressLogger {

	private List<Message> messages = new ArrayList<>();
	
	
	private void add(String message, MessageType type) {
		synchronized (messages) {
			int index = messages.size();
			messages.add(new Message(message, type, index));
		}
	}
	
	
	public void error(String message) {
		add(message, MessageType.ERROR);
	}

	
	public void info(String message) {
		add(message, MessageType.INFO);
	}
	
	public void success(String message) {
		add(message, MessageType.SUC);
		
	}
	
	public void warn(String message) {
		add(message, MessageType.WARN);
	}
	
	public Message getLastMessage() {
		if(!messages.isEmpty()) {
			return messages.get(messages.size() - 1);
		}else {
			return Message.EMPTY;
		}
	}
	
	/**
	 * 从索引为index后获得剩余的所有消息
	 * @param index
	 * @return
	 */
	public MessagesSequence getMessagesFrom(int index){
		MessagesSequence sequeue = new MessagesSequence();
		if(messages.size() > index) {
			sequeue.setBeginIndex(index + 1);
			int endIndex = messages.size() - 1;
			for (int i = sequeue.getBeginIndex(); i <= endIndex; i++) {
				sequeue.getMessages().add(messages.get(i));
			}
			sequeue.setEndIndex(sequeue.getBeginIndex() + sequeue.getMessages().size() - 1);
		}
		return sequeue;
	}


	public MessagesSequence getMessagesFrom(Integer msgIndex, Integer maxMsgCount) {
		MessagesSequence sequeue = new MessagesSequence();
		if(messages.size() > msgIndex) {
			int endIndex = messages.size() - 1;
			int beginIndex = msgIndex;
			if(maxMsgCount != null && endIndex - beginIndex + 1 >= maxMsgCount) {
				beginIndex = endIndex - maxMsgCount + 1;
			}
			for (int i = beginIndex; i <= endIndex; i++) {
				sequeue.getMessages().addLast(messages.get(i));
			}
			sequeue.setBeginIndex(beginIndex);
			sequeue.setEndIndex(endIndex);
		}
		return sequeue;
	}


	


	
	
	

}
