package cho.carbon.hc.web.dao.deferedQuery;

import java.util.Map;

public abstract class MapperFunction<T> implements Function<Map<String, Object>, T> {
	public final T apply(Map<String, Object> input) {
		SimpleMapWrapper mapWrapper = new SimpleMapWrapper(input);
		return this.build(mapWrapper);
	}

	public abstract T build(SimpleMapWrapper var1);
}