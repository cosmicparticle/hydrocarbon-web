package cho.carbon.hc.web.dao.deferedQuery;

import java.util.Date;
import java.util.Map;

import cho.carbon.bond.utils.FormatUtils;

public class SimpleMapWrapper {
	Map<String, Object> map;

	public SimpleMapWrapper(Map<String, Object> map) {
		this.map = map;
	}

	public Object get(String column) {
		return this.map.get(column);
	}

	public Object get(String column, Class<?> formatClass) {
		return FormatUtils.toClass(formatClass, this.map.get(column));
	}

	public Map<String, Object> getMap() {
		return this.map;
	}

	public String getString(String column) {
		return FormatUtils.toString(this.map.get(column));
	}

	public Long getLong(String column) {
		return FormatUtils.toLong(this.map.get(column));
	}

	public Integer getInteger(String column) {
		return FormatUtils.toInteger(this.map.get(column));
	}

	public Double toDouble(String column) {
		return FormatUtils.toDouble(this.map.get(column));
	}

	public Date getDate(String column) {
		return (Date) this.get(column);
	}
}