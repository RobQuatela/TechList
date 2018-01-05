package com.accountomation.techlist.dao;

import org.hibernate.Session;

import com.accountomation.techlist.model.RedoType;
import com.accountomation.techlist.util.HibernateUtil;

public class RedoTypeService {

	public static void saveOrUpdate(RedoType type) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.saveOrUpdate(type);
		session.getTransaction().commit();
		session.close();
	}
}
