package cho.carbon.hc.web.dto.ajax;

import com.alibaba.fastjson.JSONArray;

public class JsonArrayResponse extends ResponseJSON{

	private JSONArray jArray;
	
	public JsonArrayResponse() {
		this.jArray = new JSONArray();
	}
	
	public JSONArray getJsonArray(){
		return jArray;
	}
	
	public JsonArrayResponse setJsonArray(JSONArray jsonArray){
		this.jArray = jsonArray;
		return this;
	}
	
	public JsonArrayResponse remove(Integer index){
		this.jArray.remove(index);
		return this;
	}
	
	public JsonArrayResponse add(Object ele){
		this.jArray.add(ele);
		return this;
	}
	
	public JsonArrayResponse add(int index, Object ele){
		this.jArray.add(index, ele);
		return this;
	}
	
	@Override
	public String getJson() {
		if(jArray == null){
			return null;
		}else{
			return jArray.toJSONString();
		}
	}

}
