package cho.carbon.hc.web.dto.ajax;

import com.alibaba.fastjson.JSONObject;

import cho.carbon.hc.web.poll.MessagesSequence;

public class PollStatusResponse extends ResponseJSON{
	private JSONObjectResponse json = new JSONObjectResponse();
	
	@Override
	public String getJson() {
		return json.getJson();
	}
	
	public JSONObjectResponse getPrinciple() {
		return json;
	}
	public void put(String key, Object value) {
		json.put(key, value);
	}
	
	public String getUUID() {
		return json.getJsonObject().getString("uuid");
	}
	
	public void setUUID(String uuid) {
		json.put("uuid", uuid);
	}
	
	public Integer getCurrent() {
		return json.getJsonObject().getInteger("current");
	}
	public void setCurrent(Integer currentCount) {
		json.put("current", currentCount);
	}
	public Integer getTotalCount() {
		return json.getJsonObject().getInteger("totalCount");
	}
	public void setTotalCount(Integer totalCount) {
		json.put("totalCount", totalCount);
	}
	
	/**
	 * 工作是否已经完成，与{@link #isBreaked()}是互斥状态
	 * @return
	 */
	public boolean isCompleted() {
		return json.getJsonObject().getBooleanValue("completed");
	}
	/**
	 * 设置工作是否已经完成，与{@link #setBreaked()}是互斥操作
	 */
	public void setCompleted() {
		json.put("completed", true);
	}
	
	/**
	 * 设置工作是否被中断，与{@link #setCompleted()}是互斥操作
	 */
	public void setBreaked() {
		json.put("breaked", true);
	}
	/**
	 * 工作是否被中断，与{@link #isCompleted()}是互斥状态
	 * @return
	 */
	public boolean isBreaked() {
		return json.getJsonObject().getBooleanValue("breaked");
	}
	
	public String getStatusMessage() {
		return json.getJsonObject().getString("statusMsg");
	}
	public void setStatusMessage(String message) {
		json.put("statusMsg", message);
	}
	public void setStatus(String status) {
		json.setStatus(status);
	}
	public String getStatus() {
		return json.getStatus();
	}

	public void setMessageSequence(MessagesSequence messagesSequence) {
		json.put("messageSequence", messagesSequence);
	}
	
	/**
	 * 表示请求正常，但是不代表工作的正常进行
	 */
	public void setSuccessStatus() {
		json.setStatus("suc");
	}

	public void putData(JSONObject responseData) {
		json.getJsonObject().putAll(responseData);
	}
}
