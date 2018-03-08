package com.accountomation.techlist.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.accountomation.techlist.model.Redo;
import com.accountomation.techlist.model.TechnicianRedo;
import com.accountomation.techlist.util.HibernateUtil;

public class RedoService {

	public static void save(Redo redo) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		CriteriaQuery<Redo> criteria = session.getCriteriaBuilder().createQuery(Redo.class);
		Root<Redo> root = criteria.from(Redo.class);
		criteria.select(root).where(session.getCriteriaBuilder().equal(root.get("id"), redo.getId()));
		Query<Redo> query = session.createQuery(criteria);
		Redo redoDup;
		
		try {
			redoDup = query.getSingleResult();
		} catch(NoResultException e) {
			redoDup = null;
		}
		
		session.beginTransaction();
		
		if(redoDup != null) {
			List<TechnicianRedo> techRedos = new ArrayList<>();
			techRedos.addAll(new ArrayList<TechnicianRedo>(redo.getTechRedos()));
			redo.getTechRedos().clear();
			session.merge(redo);
			for(TechnicianRedo techRedo : techRedos)
				TechnicianRedoService.update(techRedo);
			session.getTransaction().commit();
		} else {
			List<TechnicianRedo> tr = new ArrayList<>();
			tr.addAll(new ArrayList<TechnicianRedo>(redo.getTechRedos()));
			redo.getTechRedos().clear();
			session.save(redo);
			session.getTransaction().commit();
			//redo.setTechRedos(tr);
			for(TechnicianRedo r : tr) {
				TechnicianRedoService.save(r);
			}
			//session.merge(redo);
		}
		
		//session.getTransaction().commit();
		session.close();
	}
}
