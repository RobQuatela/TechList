package com.accountomation.techlist.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "redo")
public class Redo {

	private String id;
	private DateMap date;
	private Company company;
	private RedoType type;
	private int days;
	private double amount;
	private Timestamp modified;
	private List<TechnicianRedo> techRedos = new ArrayList<>();
	
	public Redo() {
		
	}
	
	public Redo(String id, DateMap date, Company company, RedoType type, int days, double amount,
			Timestamp modified) {
		this.id = id;
		this.date = date;
		this.company = company;
		this.type = type;
		this.days = days;
		this.amount = amount;
		this.modified = modified;
	}

	@Id
	@Column(name = "redo_id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ManyToOne(targetEntity = DateMap.class)
	@JoinColumn(name = "redo_date", nullable = false,
		foreignKey = @ForeignKey(name = "fk_redo_date"))
	public DateMap getDate() {
		return date;
	}

	public void setDate(DateMap date) {
		this.date = date;
	}

	@ManyToOne(targetEntity = Company.class)
	@JoinColumn(name = "redo_company", nullable = false,
		foreignKey = @ForeignKey(name = "fk_redo_company"))
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@ManyToOne(targetEntity = RedoType.class)
	@JoinColumn(name = "redo_type", nullable = false,
		foreignKey = @ForeignKey(name = "fk_redo_redotype"))
	public RedoType getType() {
		return type;
	}

	public void setType(RedoType type) {
		this.type = type;
	}

	@Column(name = "redo_days")
	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	@Column(name = "redo_amount")
	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Column(name = "redo_modified")
	public Timestamp getModified() {
		return modified;
	}

	public void setModified(Timestamp modified) {
		this.modified = modified;
	}

	@OneToMany(mappedBy = "redo", cascade = CascadeType.ALL)
	public List<TechnicianRedo> getTechRedos() {
		return techRedos;
	}

	public void setTechRedos(List<TechnicianRedo> techRedos) {
		this.techRedos = techRedos;
	}
}
