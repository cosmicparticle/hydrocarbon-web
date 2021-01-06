package cho.carbon.hc.web.dto.ajax;

import com.alibaba.fastjson.JSONObject;

public class JSONObjectResponse extends ResponseJSON{
	private JSONObject jsonObject = new JSONObject();
	public JSONObjectResponse() {
		jsonObject = new JSONObject();
	}
	
	public JSONObject getJsonObject() {
		return jsonObject;
	}

	public JSONObjectResponse setJsonObject(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
		return this;
	}
	
	public JSONObjectResponse put(String key, Object value){
		jsonObject.put(key, value);
		return this;
	}
	
	public void removeProperty(String key){
		jsonObject.remove(key);
	}
	
	public String getStatus() {
		return jsonObject.getString("status");
	}

	public void setStatus(String status) {
		jsonObject.put("status", status);
	}

	@Override
	public String getJson() {
		if(jsonObject == null){
			return null;
		}
		return jsonObject.toString();
	}
}
