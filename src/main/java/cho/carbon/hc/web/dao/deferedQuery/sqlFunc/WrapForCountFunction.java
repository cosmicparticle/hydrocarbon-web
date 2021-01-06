package cho.carbon.hc.web.dao.deferedQuery.sqlFunc;

import org.springframework.util.StringUtils;

import cho.carbon.hc.web.dao.deferedQuery.Function;

public class WrapForCountFunction implements Function<String, String> {
	public String apply(String statement) {
		String trimedStatement = StringUtils.trimWhitespace(statement);
		return trimedStatement.toUpperCase().startsWith("FROM ")
				? "select count(*) " + statement
				: "select count(*) from (" + statement + ") as for__count";
	}
}