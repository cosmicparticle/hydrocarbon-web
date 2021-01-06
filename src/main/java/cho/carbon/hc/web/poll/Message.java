package cho.carbon.hc.web.poll;

public class Message {
	public static final Message EMPTY = new Message("", MessageType.INFO, null);
	private final String text;
	private final MessageType type;
	private final long createTime;
	public Message(String text, MessageType type, Integer index) {
		super();
		this.text = text;
		this.type = type;
		this.createTime = System.currentTimeMillis();
	}
	public String getText() {
		return text;
	}
	public MessageType getType() {
		return type;
	}
	
	@Override
	public String toString() {
		return text;
	}
	public long getCreateTime() {
		return createTime;
	}
}
