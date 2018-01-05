package com.accountomation.techlist.util;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

import org.hibernate.Session;

import com.accountomation.techlist.model.Company;
import com.accountomation.techlist.model.DateMap;
import com.accountomation.techlist.model.SalesType;

public class PopulateData {

	public static void populate() {
		DateMap date = new DateMap(Date.valueOf(LocalDate.of(2017, 9, 22)));
		ArrayList<Company> companies = new ArrayList<>();
		companies.add(new Company("SSB", "ATLANTA"));
		companies.add(new Company("HOU", "HOUSTON"));
		companies.add(new Company("NJC", "TOMS RIVER"));
		ArrayList<SalesType> salesTypes = new ArrayList<>();
		salesTypes.add(new SalesType("carpt_cln", "Carpet Cleaning"));
		salesTypes.add(new SalesType("prot", "Protection"));
		salesTypes.add(new SalesType("deod", "Deodorizer"));
		salesTypes.add(new SalesType("spot", "Spot Remover"));
		salesTypes.add(new SalesType("rake", "Rakes"));
		salesTypes.add(new SalesType("mat", "Mats"));
		salesTypes.add(new SalesType("odo", "Odor Outs"));
		salesTypes.add(new SalesType("wext", "Water Mitigation"));
		salesTypes.add(new SalesType("crep", "Crep"));
		salesTypes.add(new SalesType("guar", "Guard"));
		salesTypes.add(new SalesType("tile", "Tile Cleaning"));
		salesTypes.add(new SalesType("furn_cln", "Furniture Cleaning"));
		salesTypes.add(new SalesType("other", "Other Services"));
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.save(date);
		for(Company company : companies)
			session.save(company);
		for(SalesType sales : salesTypes)
			session.save(sales);
		session.getTransaction().commit();

	}
}
