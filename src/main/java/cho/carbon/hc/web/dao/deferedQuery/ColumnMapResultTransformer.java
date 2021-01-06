package cho.carbon.hc.web.dao.deferedQuery;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import org.hibernate.transform.ResultTransformer;

public abstract class ColumnMapResultTransformer<T> implements ResultTransformer {
	static final long serialVersionUID = -4789768421017913745L;

	private MapperFunction<T> func = new MapperFunction<T>() {
		public T build(SimpleMapWrapper mapWrapper) {
			return ColumnMapResultTransformer.this.build(mapWrapper);
		}
	};

	protected abstract T build(SimpleMapWrapper var1);

	public T transformTuple(Object[] tuple, String[] aliases) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();

		for (int i = 0; i < aliases.length; ++i) {
			map.put(aliases[i].toLowerCase(), tuple[i]);
		}

		return this.func.apply(map);
	}

	public List transformList(List collection) {
		return new ArrayList(collection);
	}
}