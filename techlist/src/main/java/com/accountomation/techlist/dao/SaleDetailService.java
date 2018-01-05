package com.accountomation.techlist.dao;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.accountomation.techlist.model.SaleDetail;
import com.accountomation.techlist.util.HibernateUtil;

public class SaleDetailService {

	public static void update(SaleDetail sd) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		CriteriaQuery<SaleDetail> criteria = session.getCriteriaBuilder().createQuery(SaleDetail.class);
		Root<SaleDetail> root = criteria.from(SaleDetail.class);
		criteria
			.select(root)
			.where(
					session.getCriteriaBuilder().equal(root.get("job"), sd.getJob()),
					session.getCriteriaBuilder().equal(root.get("type"), sd.getType())
					);
		Query<SaleDetail> query = session.createQuery(criteria);
		
		SaleDetail dupDetail;
		
		try {
			dupDetail = query.getSingleResult();
		} catch(NoResultException e) {
			dupDetail = null;
		}
		
		session.beginTransaction();
		
		if(dupDetail != null) {
			sd.setId(dupDetail.getId());
			session.merge(sd);
		}
		
		session.getTransaction().commit();
		session.close();
	}
}
