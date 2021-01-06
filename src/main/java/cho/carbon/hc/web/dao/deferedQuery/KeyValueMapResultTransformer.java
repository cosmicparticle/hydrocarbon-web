package cho.carbon.hc.web.dao.deferedQuery;

import java.util.HashMap;
import java.util.Map;

public class KeyValueMapResultTransformer<K, V> extends ColumnMapResultTransformer<Void> {
	private static final long serialVersionUID = 757592956506859643L;
	private final Map<K, V> map;
	private final Function<SimpleMapWrapper, K> keyGetter;
	private final Function<SimpleMapWrapper, V> valueGetter;

	public static <K, V> KeyValueMapResultTransformer<K, V> build(Map<K, V> map,
			Function<SimpleMapWrapper, K> keyGetter, Function<SimpleMapWrapper, V> valueGetter) {
		return new KeyValueMapResultTransformer(map, keyGetter, valueGetter);
	}

	public static <K, V> KeyValueMapResultTransformer<K, V> build(Function<SimpleMapWrapper, K> keyGetter,
			Function<SimpleMapWrapper, V> valueGetter) {
		Map<K, V> map = new HashMap();
		return build(map, keyGetter, valueGetter);
	}

	private KeyValueMapResultTransformer(Map<K, V> map, Function<SimpleMapWrapper, K> keyGetter,
			Function<SimpleMapWrapper, V> valueGetter) {
		this.map = map;
		this.keyGetter = keyGetter;
		this.valueGetter = valueGetter;
	}

	protected Void build(SimpleMapWrapper mapWrapper) {
		K key = this.keyGetter.apply(mapWrapper);
		if (key != null) {
			this.map.put(key, this.valueGetter.apply(mapWrapper));
		}

		return null;
	}

	public Map<K, V> getMap() {
		return this.map;
	}
}