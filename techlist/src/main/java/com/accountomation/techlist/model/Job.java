package com.accountomation.techlist.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "job")
public class Job {

	private long id;
	private String invoice;
	private DateMap date;
	private Company company;
	private Technician tech;
	private String custType;
	private double amountScheduled;
	private double amountAddon;
	private List<SaleDetail> saleDetail = new ArrayList<>();
	
	public Job() {
		
	}
	
	public Job(String invoice, DateMap date, Company company, Technician tech, 
			String custType, double amountScheduled, double amountAddon) {
		this.invoice = invoice;
		this.date = date;
		this.company = company;
		this.tech = tech;
		this.custType = custType;
		this.amountScheduled = amountScheduled;
		this.amountAddon = amountAddon;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "job_id")
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "job_invoice")
	public String getInvoice() {
		return invoice;
	}

	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}

	@ManyToOne(targetEntity = DateMap.class)
	@JoinColumn(name = "date_id", nullable = false,
		foreignKey = @ForeignKey(name = "fk_job_date"))
	public DateMap getDate() {
		return date;
	}

	public void setDate(DateMap date) {
		this.date = date;
	}

	@ManyToOne(targetEntity = Company.class)
	@JoinColumn(name = "company_id", nullable = false,
		foreignKey = @ForeignKey(name = "fk_job_company"))
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@ManyToOne(targetEntity = Technician.class)
	@JoinColumn(name = "tech_id", nullable = false,
		foreignKey = @ForeignKey(name = "fk_job_tech"))
	public Technician getTech() {
		return tech;
	}

	public void setTech(Technician tech) {
		this.tech = tech;
	}

	@Column(name = "job_custtype")
	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	@Column(name = "job_scheduled")
	public double getAmountScheduled() {
		return amountScheduled;
	}

	public void setAmountScheduled(double amountScheduled) {
		this.amountScheduled = amountScheduled;
	}

	@Column(name = "job_addon")
	public double getAmountAddon() {
		return amountAddon;
	}

	public void setAmountAddon(double amountAddon) {
		this.amountAddon = amountAddon;
	}

	@OneToMany(mappedBy = "job", cascade = CascadeType.ALL)
	public List<SaleDetail> getSaleDetail() {
		return saleDetail;
	}

	public void setSaleDetail(List<SaleDetail> saleDetail) {
		this.saleDetail = saleDetail;
	}
}
