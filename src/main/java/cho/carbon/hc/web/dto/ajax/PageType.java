package cho.carbon.hc.web.dto.ajax;

public enum PageType {
	DIALOG("dialog"),
	TAB("tab")
	;
	
	private String type;
	PageType(String type){
		this.type = type;
	}
	
	@Override
	public String toString() {
		return this.type;
	}
}
