package com.accountomation.techlist.dao;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.accountomation.techlist.model.SalesType;
import com.accountomation.techlist.util.HibernateUtil;

public class SalesTypeService {

	public static SalesType retrieve(String id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		CriteriaQuery<SalesType> criteria = session.getCriteriaBuilder().createQuery(SalesType.class);
		Root<SalesType> root = criteria.from(SalesType.class);
		criteria
			.select(root)
			.where(session.getCriteriaBuilder().equal(root.get("id"), id));
		Query<SalesType> query = session.createQuery(criteria);
		SalesType salesType = query.getSingleResult();
		session.close();
		
		return salesType;
	}
}
