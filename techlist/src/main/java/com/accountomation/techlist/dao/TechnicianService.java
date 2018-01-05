package com.accountomation.techlist.dao;

import javax.persistence.NoResultException;

import org.hibernate.Session;

import com.accountomation.techlist.model.Technician;
import com.accountomation.techlist.util.HibernateUtil;

public class TechnicianService {

	public static void saveOrUpdate(Technician tech) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();

		session.saveOrUpdate(tech);
		
		session.getTransaction().commit();
		session.close();
	}
	
	public static void saveOrUpdateRedo(Technician tech) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		Technician techDup;
		try {
			techDup = session.load(Technician.class, tech.getId());
		} catch(NoResultException e) {
			techDup = null;
		}
		
		if(techDup == null)
			session.save(tech);
		
		session.getTransaction().commit();
		session.close();
	}
}
