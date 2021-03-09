package cho.carbon.hc.web.dto.ajax;
/**
 * 
 * <p>Title: NoticeType</p>
 * <p>Description: </p><p>
 * 响应的通知类型
 * </p>
 */
public enum NoticeType {
	ERROR("error"),
	WARNING("warning"),
	SUC("success"),
	INFO("info")
	;
	
	private String noticeType;
	NoticeType(String noticeType){
		this.noticeType = noticeType;
	}
	
	@Override
	public String toString() {
		return noticeType;
	}
}
