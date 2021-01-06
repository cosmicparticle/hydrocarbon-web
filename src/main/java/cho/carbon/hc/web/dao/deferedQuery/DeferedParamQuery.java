package cho.carbon.hc.web.dao.deferedQuery;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.type.Type;
import org.springframework.util.Assert;

public class DeferedParamQuery {
	private String queryString;
	private List<String> conditions = new ArrayList();
	private LinkedHashMap<String, QueryParameter> paramMap = new LinkedHashMap();
	private ResultTransformer resultTransformer;
	private LinkedHashMap<String, DeferedParamSnippet> snippettMap = new LinkedHashMap();

	public DeferedParamQuery(String queryString) {
		this.queryString = queryString;
	}

	public DeferedParamQuery appendCondition(String conditionString) {
		this.conditions.add(conditionString);
		return this;
	}

	public DeferedParamQuery setParam(String key, Object value, Type type, boolean isRestrict) {
		QueryParameter parameter = null;
		if (value instanceof QueryParameter) {
			parameter = (QueryParameter) value;
		} else {
			parameter = new QueryParameter();
			parameter.setValue(value);
			parameter.setType(type);
			parameter.setRestrict(isRestrict);
		}

		parameter.setParameterName(key);
		this.paramMap.put(key, parameter);
		return this;
	}

	public DeferedParamQuery setParam(String key, Object value) {
		return this.setParam(key, value, true);
	}

	public DeferedParamQuery setParam(String key, Object value, boolean isRestrict) {
		return this.setParam(key, value, (Type) null, isRestrict);
	}

	public DeferedParamQuery setParam(String key, Object value, Type type) {
		return this.setParam(key, value, type, true);
	}

	public DeferedParamQuery clearParam() {
		this.paramMap.clear();
		return this;
	}

	public DeferedParamQuery resetQueryString(String queryString) {
		this.queryString = queryString;
		return this;
	}

	private <T extends Query> T createQuery(Class<T> queryClass, Session session, boolean addWhere,
			Function<String, String> resetSQLFunc) {
		String sql = this.queryString;
		String funcResult;
		String prefix;
		if (this.conditions != null && this.conditions.size() > 0) {
			funcResult = "";

			String trimSQL;
			for (Iterator var8 = this.conditions.iterator(); var8
					.hasNext(); funcResult = funcResult + " " + trimSQL + " ") {
				trimSQL = (String) var8.next();
			}

			if (addWhere) {
				trimSQL = funcResult.trim();
				int trimLength = trimSQL.length();
				if (trimLength >= 3) {
					prefix = trimSQL.substring(0, 3);
					if (prefix.equalsIgnoreCase("or ")) {
						funcResult = " where " + trimSQL.substring(3);
					} else if (trimLength >= 4) {
						prefix = trimSQL.substring(0, 4);
						if (prefix.equalsIgnoreCase("and ")) {
							funcResult = " where " + trimSQL.substring(4);
						}
					}
				}
			}

			sql = sql + funcResult;
		}

		Iterator var13 = this.snippettMap.entrySet().iterator();

		while (var13.hasNext()) {
			Entry<String, DeferedParamSnippet> snippetEntry = (Entry) var13.next();
			String regex = "@" + (String) snippetEntry.getKey();
			DeferedParamSnippet snippet = (DeferedParamSnippet) snippetEntry.getValue();
			String replacement = "";
			if (snippet != null) {
				replacement = snippet.getSnippet();
				if (!snippet.isEmpty() && snippet.getPrependWhenNotEmpty() != null) {
					replacement = snippet.getPrependWhenNotEmpty() + replacement;
				}
			}

			if (sql.contains(regex)) {
				sql = sql.replaceAll(regex, replacement);
			}
		}

		if (resetSQLFunc != null) {
			funcResult = (String) resetSQLFunc.apply(sql);
			if (funcResult == null) {
				return null;
			}

			sql = funcResult;
		}

		funcResult = null;
		Object query;
		if (SQLQuery.class.equals(queryClass)) {
			query = session.createSQLQuery(sql);
		} else {
			if (!Query.class.equals(queryClass)) {
				throw new RuntimeException("不支持的类");
			}

			query = session.createQuery(sql);
		}

		String[] parameterNames = ((Query) query).getNamedParameters();
		Set<String> parameterNameSet = new HashSet();
		String[] var12 = parameterNames;
		int var11 = parameterNames.length;

		for (int var22 = 0; var22 < var11; ++var22) {
			prefix = var12[var22];
			parameterNameSet.add(prefix);
		}

		Iterator var24 = this.paramMap.entrySet().iterator();

		while (true) {
			while (true) {
				while (true) {
					QueryParameter param;
					do {
						if (!var24.hasNext()) {
							return (T) query;
						}

						Entry<String, QueryParameter> entry = (Entry) var24.next();
						param = (QueryParameter) entry.getValue();
					} while (!param.isRestrict() && !parameterNameSet.contains(param.getParameterName()));

					Object value = param.getValue();
					if (param.getType() == null) {
						if (param.isRestrict() && value == null) {
							throw new RuntimeException("参数[" + param.getParameterName() + "]不能为null");
						}

						if (value instanceof Collection) {
							((Query) query).setParameterList(param.getParameterName(), (Collection) value);
						} else if (value != null && value.getClass().isArray()) {
							((Query) query).setParameterList(param.getParameterName(), (Object[]) value);
						} else {
							((Query) query).setParameter(param.getParameterName(), param.getValue());
						}
					} else if (value instanceof Collection) {
						((Query) query).setParameterList(param.getParameterName(), (Collection) value, param.getType());
					} else if (value != null && value.getClass().isArray()) {
						((Query) query).setParameterList(param.getParameterName(), (Object[]) value, param.getType());
					} else {
						((Query) query).setParameter(param.getParameterName(), value, param.getType());
					}
				}
			}
		}
	}

	public SQLQuery createSQLQuery(Session session, boolean addWhere, Function<String, String> resetSQLFunc) {
		return (SQLQuery) this.createQuery(SQLQuery.class, session, addWhere, resetSQLFunc);
	}

	public Query createQuery(Session session, boolean addWhere, Function<String, String> resetSQLFunc) {
		return this.createQuery(Query.class, session, addWhere, resetSQLFunc);
	}

	public <T> void setResultSetter(ResultSetter<T> resultSetter) {
		this.resultTransformer = new ResultTransformer() {
			private static final long serialVersionUID = -1880685803872109376L;

			@Override
			public Object transformTuple(Object[] tuple, String[] aliases) {
				return resultSetter.transform(tuple, aliases);
			}

			@Override
			public List transformList(List collection) {
				return new ArrayList(collection);
			}
		};
	}

	public void setResultTransformer(ResultTransformer resultTransformer) {
		this.resultTransformer = resultTransformer;
	}

	public ResultTransformer getResultTransformer() {
		return this.resultTransformer;
	}

	public String getQueryString() {
		return this.queryString;
	}

	public DeferedParamQuery setSnippet(String snippetName, String snippet) {
		Assert.hasText(snippetName);
		DeferedParamSnippet s = new DeferedParamSnippet(snippetName, snippet);
		this.snippettMap.put(snippetName, s);
		return this;
	}

	public DeferedParamQuery removeSnippet(String snippetName) {
		this.snippettMap.remove(snippetName);
		return this;
	}

	public DeferedParamSnippet createSnippet(String snippetName, String prepend) {
		DeferedParamSnippet snippet = new DeferedParamSnippet(snippetName);
		if (prepend != null) {
			snippet.setPrependWhenNotEmpty(prepend);
		}

		this.snippettMap.put(snippetName, snippet);
		return snippet;
	}

	public ConditionSnippet createConditionSnippet(String snippetName) {
		ConditionSnippet snippet = new ConditionSnippet(snippetName);
		snippet.setPrependWhenNotEmpty("where");
		this.snippettMap.put(snippetName, snippet);
		return snippet;
	}
}