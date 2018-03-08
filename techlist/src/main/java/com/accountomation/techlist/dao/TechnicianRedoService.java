package com.accountomation.techlist.dao;


import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.accountomation.techlist.model.TechnicianRedo;
import com.accountomation.techlist.util.HibernateUtil;

public class TechnicianRedoService {

	public static void update(TechnicianRedo redo) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		CriteriaQuery<TechnicianRedo> criteria = session.getCriteriaBuilder().createQuery(TechnicianRedo.class);
		Root<TechnicianRedo> root = criteria.from(TechnicianRedo.class);
		criteria
			.select(root)
			.where(
					session.getCriteriaBuilder().equal(root.get("redo"), redo.getRedo()),
					session.getCriteriaBuilder().equal(root.get("tech"), redo.getTech())
					);
		Query<TechnicianRedo> query = session.createQuery(criteria);
		TechnicianRedo redoDup;
		
		try {
			redoDup = query.getSingleResult();
		} catch(NoResultException e) {
			redoDup = null;
		}
		
		session.beginTransaction();
		
		if(redoDup != null) {
			redo.setId(redoDup.getId());
			session.merge(redo);
		}
		
		session.getTransaction().commit();
		session.close();
			
		
	}
	
	public static void save(TechnicianRedo redo) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.save(redo);
		session.getTransaction().commit();
		session.close();
	}
}
