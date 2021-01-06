package cho.carbon.hc.web.dao.deferedQuery;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import javax.persistence.Column;
import org.hibernate.transform.ResultTransformer;
import org.springframework.beans.BeanUtils;

import cho.carbon.hc.spring.binder.ClassPropertyComposite;
import cho.carbon.hc.spring.binder.FieldRefectUtils;

public class HibernateRefrectResultTransformer<T> implements ResultTransformer {
	private static final long serialVersionUID = 4246893639652328864L;
	private ColumnMapResultTransformer<T> cTrans;
	private static Map<Class<?>, HibernateRefrectResultTransformer<?>> instanceMap = new HashMap();

	HibernateRefrectResultTransformer(Class<T> clazz) {
		FieldRefectUtils<T> refectUtils = new FieldRefectUtils(clazz, (composite1) -> {
			ClassPropertyComposite composite=(ClassPropertyComposite)composite1;
			Column anno = composite.getFieldAnno(Column.class);
			if (anno != null) {
				String columnName = anno.name();
				if (columnName != null) {
					return columnName.toLowerCase();
				}
			}

			return composite.getFieldName().toLowerCase();
		});
		this.cTrans = new ColumnMapResultTransformer<T>() {
			private static final long serialVersionUID = -305475977693720378L;

			protected T build(SimpleMapWrapper mapWrapper) {
				T obj = BeanUtils.instantiate(clazz);
				if (obj != null) {
					refectUtils.iterateField((fieldName, composite) -> {
						try {
							composite.setValue(obj, mapWrapper.get(fieldName));
						} catch (Exception var5) {
							var5.printStackTrace();
						}

					});
				}

				return obj;
			}
		};
	}

	public T transformTuple(Object[] tuple, String[] aliases) {
		return this.cTrans.transformTuple(tuple, aliases);
	}

	public List transformList(List collection) {
		return this.cTrans.transformList(collection);
	}

	public T build(SimpleMapWrapper mapWrapper) {
		return this.cTrans.build(mapWrapper);
	}

	public static <C> HibernateRefrectResultTransformer<C> getInstance(Class<C> clazz) {
		Map var1 = instanceMap;
		synchronized (instanceMap) {
			HibernateRefrectResultTransformer<C> value = (HibernateRefrectResultTransformer<C>) instanceMap.get(clazz);
			if (value == null) {
				value = new HibernateRefrectResultTransformer<C>(clazz);
				instanceMap.put(clazz, value);
			}

			return value;
		}
	}
}