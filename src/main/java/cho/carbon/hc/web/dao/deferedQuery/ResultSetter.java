package cho.carbon.hc.web.dao.deferedQuery;

import java.util.LinkedHashMap;
import java.util.Map;

public class ResultSetter<T> {
	private Function<Map<String, Object>, T> setFunc;

	public ResultSetter(Function<Map<String, Object>, T> func) {
		this.setFunc = func;
	}

	public ResultSetter(MapperFunction<T> func) {
		this.setFunc = func;
	}

	public T transform(Object[] tuple, String[] aliases) {
		LinkedHashMap<String, Object> map = new LinkedHashMap();

		for (int i = 0; i < aliases.length; ++i) {
			map.put(aliases[i].toLowerCase(), tuple[i]);
		}

		return this.setFunc.apply(map);
	}
}