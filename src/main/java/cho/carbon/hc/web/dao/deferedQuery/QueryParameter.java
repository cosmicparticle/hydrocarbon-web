package cho.carbon.hc.web.dao.deferedQuery;

import org.hibernate.type.Type;

public class QueryParameter {
	private String parameterName;
	private Object value;
	private Type type;
	private boolean isRestrict = true;

	public String getParameterName() {
		return this.parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

	public Object getValue() {
		return this.value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Type getType() {
		return this.type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public boolean isRestrict() {
		return this.isRestrict;
	}

	public void setRestrict(boolean isRestrict) {
		this.isRestrict = isRestrict;
	}
}