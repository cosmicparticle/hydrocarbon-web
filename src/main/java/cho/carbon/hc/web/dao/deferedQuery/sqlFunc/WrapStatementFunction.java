package cho.carbon.hc.web.dao.deferedQuery.sqlFunc;

import cho.carbon.hc.web.dao.deferedQuery.Function;

public class WrapStatementFunction implements Function<String, String> {
	private String prepend;
	private String append;

	public WrapStatementFunction(String prepend, String append) {
		this.prepend = prepend;
		this.append = append;
	}

	public String apply(String sql) {
		String prependStr = this.prepend == null ? "" : this.prepend;
		String appendStr = this.append == null ? "" : this.append;
		return prependStr + sql + appendStr;
	}
}