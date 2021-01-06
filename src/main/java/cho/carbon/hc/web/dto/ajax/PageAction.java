package cho.carbon.hc.web.dto.ajax;

public enum PageAction {
	REFRESH("refresh"),
	REDIRECT("redirect"),
	CLOSE("close")
	;
	
	private String val;
	PageAction(String value){
		this.val = value;
	}
	@Override
	public String toString() {
		return val;
	}
}
