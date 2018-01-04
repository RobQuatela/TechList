package com.accountomation.techlist.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "saledetail")
public class SaleDetail {

	private long id;
	private SalesType type;
	private Job job;
	private double amount;
	
	public SaleDetail() {
		
	}
	
	public SaleDetail(SalesType type, Job job, double amount) {
		this.type = type;
		this.job = job;
		this.amount = amount;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "saledetail_id")
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@ManyToOne(targetEntity = SalesType.class)
	@JoinColumn(name = "salestype_id", nullable = false,
		foreignKey = @ForeignKey(name = "fk_salesdetail_salestype"))
	public SalesType getType() {
		return type;
	}

	public void setType(SalesType type) {
		this.type = type;
	}

	@ManyToOne(targetEntity = Job.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "job_id", nullable = false,
		foreignKey = @ForeignKey(name = "fk_salesdetail_job"))
	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	@Column(name = "salesdetail_amount")
	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
}
