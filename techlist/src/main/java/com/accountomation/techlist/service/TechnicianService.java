package com.accountomation.techlist.service;

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
}
