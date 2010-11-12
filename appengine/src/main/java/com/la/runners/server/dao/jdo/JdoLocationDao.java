package com.la.runners.server.dao.jdo;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.appengine.commons.dao.BaseDao;
import org.appengine.commons.dao.jdo.BaseDaoImpl;

import com.la.runners.shared.Location;

public class JdoLocationDao extends BaseDaoImpl<Location> implements
		BaseDao<Location> {

	private static final String RUN_ID = "runId";

	public JdoLocationDao() {
		super(Location.class);
	}

	public Location detach(Location l) {
		return detach(l);
	}

	public List<Location> search(final Long id) {
		return query(RUN_ID, id);
	}

	public void deleteByRunId(Long id) {
		deleteByProperty("runId", "Long", id);
	}

	private List<Location> query(final String property, final Long id) {
		return executeQuery(new QueryPersonalizer(Location.class) {
			@Override
			public void get(Query q) {
				super.get(q);
				q.setFilter(property + " == " + id);
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean deleteByProperty(String propertyName, String propertyType, Object propertyValue) {
		PersistenceManager pm = getPM();
		Query q = pm.newQuery(Location.class);
		q.setFilter(propertyName + " == propertyParam");
		q.declareParameters(propertyType + " propertyParam");
		List<Location> result = (List<Location>) q.execute(propertyValue);
		if (result != null && !result.isEmpty()) {
			for(Location l : result) {
				pm.deletePersistent(l);
			}
			pm.close();
			return true;
		} else {
			pm.close();
			return false;
		}
	}

}
