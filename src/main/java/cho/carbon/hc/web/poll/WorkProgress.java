package cho.carbon.hc.web.poll;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.alibaba.fastjson.JSONObject;

import cho.carbon.bond.utils.TextUtils;

public class WorkProgress {
	private String uuid;
	private boolean completed = false;
	private boolean breaked = false;
	private Exception globalException;
	private ProgressLogger logger = new ProgressLogger();
	private JSONObject responseObject = new JSONObject();
	private Map<String, Object> dataMap = new HashMap<>();
	private Set<Integer> failedIndexs = new TreeSet<Integer>();
	//进度总数
	private int total = 0;
	//当前进度值
	private int current = 0;
	
	//最后一次标记的时间
	private long lastVeniTime;
	
	//最后一次执行子工作的开始时间
	private long lastItemStartTime = 0;
	//最后一次执行子工作的所用时间
	private long lastItemInterval = 0;
	
	public WorkProgress() {
		this(TextUtils.uuid());
	}
	
	public WorkProgress(String uuid) {
		this.uuid = uuid;
	}
	
	/**
	 * 工作完成
	 */
	public void setCompleted() {
		completed = true;
	}
	
	/**
	 * 工作是否完成，与{@link #isBreaked()}是互斥状态
	 * @return
	 */
	public boolean isCompleted() {
		return completed;
	}

	
	/**
	 * 工作中断
	 */
	public void setBreaked() {
		breaked = true;
	}
	
	
	/**
	 * 工作是否中断，与{@link #isCompleted()}是互斥状态
	 * @return
	 */
	public boolean isBreaked() {
		return breaked;
	}

	/**
	 * 获得日志记录对象
	 * @return
	 */
	public ProgressLogger getLogger() {
		return logger;
	}
	
	/**
	 * 全局错误，
	 * 仅在{@linkplain ProgressPollableThread #doWork(WorkProgress) doWork}
	 * 方法抛出异常时有值
	 * @param globalException
	 */
	public Exception getGlobalException() {
		return globalException;
	}
	
	/**
	 * 设置全局错误，
	 * 仅在{@linkplain ProgressPollableThread #doWork(WorkProgress) doWork}
	 * 方法抛出异常时有值
	 * @param globalException
	 */
	void setGlobalException(Exception globalException) {
		this.globalException = globalException;
	}

	/**
	 * 获得进度总数
	 * @return
	 */
	public int getTotal() {
		return total;
	}

	/**
	 * 设置进度总数
	 * @param total
	 */
	public void setTotal(int total) {
		this.total = total;
	}

	/**
	 * 获得当前进度
	 * @return
	 */
	public int getCurrent() {
		return current;
	}

	/**
	 * 设置当前进度
	 * @param current
	 */
	public void setCurrent(int current) {
		this.current = current;
	}
	
	public Float getRatio() {
		if(total != 0) {
			return ((float)current / total);
		}
		return 0f;
	}
	
	public void veni() {
		this.lastVeniTime = System.currentTimeMillis();
	}

	public long getLastVeniTime() {
		return lastVeniTime;
	}
	
	public long getLastVeniInterval() {
		return System.currentTimeMillis() - lastVeniTime;
	}

	public long getLastItemInterval() {
		return lastItemInterval;
	}

	public void setLastItemInterval(long lastItemInterval) {
		this.lastItemInterval = lastItemInterval;
	}
	
	public WorkProgress startItemTimer(){
		lastItemStartTime = System.currentTimeMillis();
		return this;
	}
	
	public WorkProgress endItemTimer(){
		lastItemInterval = System.currentTimeMillis() - lastItemStartTime;
		return this;
	}

	public String getUUID() {
		return uuid;
	}

	public void appendMessage(String message) {
		getLogger().info(message);
	}

	public String getLastMessage() {
		return getLogger().getLastMessage().toString();
	}

	public void setResponseData(String key, Object val) {
		responseObject.put(key, val);
	}

	public JSONObject getResponseData() {
		return responseObject;
	}

	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public String getUuid() {
		return uuid;
	}

	public Set<Integer> getFailedIndexs() {
		return failedIndexs;
	}
}
