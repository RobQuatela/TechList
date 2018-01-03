package com.accountomation.techlist.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.accountomation.techlist.model.Job;
import com.accountomation.techlist.model.SaleDetail;
import com.accountomation.techlist.util.HibernateUtil;

public class JobService {

	public static void saveOrUpdate(Job job) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		CriteriaQuery<Job> criteria = session.getCriteriaBuilder().createQuery(Job.class);
		Root<Job> root = criteria.from(Job.class);
		criteria
			.select(root)
			.where(
					session.getCriteriaBuilder().equal(root.get("invoice"), job.getInvoice()),
					session.getCriteriaBuilder().equal(root.get("company"), job.getCompany()),
					session.getCriteriaBuilder().equal(root.get("tech"), job.getTech())
					);
		Query<Job> query = session.createQuery(criteria);
		session.beginTransaction();
		Job dupJob;
		try {
			dupJob = query.getSingleResult();
		} catch(NoResultException e) {
			dupJob = null;
		}
		
		if(dupJob != null) {
			job.setId(dupJob.getId());
			List<SaleDetail> sd = new ArrayList<>();
			sd.addAll(new ArrayList<SaleDetail>(job.getSaleDetail()));
			job.getSaleDetail().clear();
			session.merge(job);
			for(SaleDetail s : sd)
				SaleDetailService.update(s);
		} else
			session.save(job);

		session.getTransaction().commit();
		session.close();
	}
}
