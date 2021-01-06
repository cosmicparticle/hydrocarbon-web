package cho.carbon.hc.web.dto.ajax;

public abstract class ResponseJSON {
	public abstract String getJson();
	
	@Override
	public String toString() {
		return getJson();
	}
}
