package cho.carbon.hc.web.dao.utils;

import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.type.StandardBasicTypes;

public class NormalOperateDaoImpl implements NormalOperateDao {
	@Resource
	SessionFactory sFactory;

	public void evict(Object pojo) {
		this.sFactory.getCurrentSession().evict(pojo);
	}

	public Long save(Object pojo) {
		return (Long) this.sFactory.getCurrentSession().save(pojo);
	}

	public void update(Object pojo) {
		this.sFactory.getCurrentSession().update(pojo);
	}

	public <T> T get(Class<T> pojoClass, Long id) {
		return this.sFactory.getCurrentSession().get(pojoClass, id);
	}

	public <T> T load(Class<T> pojoClass, Long id) {
		return this.sFactory.getCurrentSession().load(pojoClass, id);
	}

	public int remove(Class<?> pojoClass, Set<Long> pojoId) {
		if (pojoClass != null && pojoId != null && !pojoId.isEmpty()) {
			String hql = "delete from " + pojoClass.getName() + " p where p.id in (:ids)";
			Query query = this.sFactory.getCurrentSession().createQuery(hql);
			query.setParameterList("ids", pojoId, StandardBasicTypes.LONG);
			return query.executeUpdate();
		} else {
			return 0;
		}
	}

	public void remove(Object pojo) {
		this.sFactory.getCurrentSession().delete(pojo);
	}

	public void clear() {
		this.sFactory.getCurrentSession().clear();
	}
}