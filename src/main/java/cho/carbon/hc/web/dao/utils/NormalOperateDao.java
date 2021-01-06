package cho.carbon.hc.web.dao.utils;

import java.util.Set;

public interface NormalOperateDao {
	Long save(Object var1);

	void update(Object var1);

	<T> T get(Class<T> var1, Long var2);

	int remove(Class<?> var1, Set<Long> var2);

	void remove(Object var1);

	void evict(Object var1);

	<T> T load(Class<T> var1, Long var2);

	void clear();
}