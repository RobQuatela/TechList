package com.accountomation.techlist.model;

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
@Table(name = "technician")
public class Technician {

	private String id;
	private String name;
	private String role;
	private Company company;
	private List<Job> jobs = new ArrayList<>();
	
	public Technician() {
		
	}
	
	public Technician(String id, String name, String role, Company company) {
		this.id = id;
		this.name = name;
		this.role = role;
		this.company = company;
	}

	@Id
	@Column(name = "tech_id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "tech_name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "tech_role")
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@ManyToOne(targetEntity = Company.class)
	@JoinColumn(name = "co_id", nullable = false,
		foreignKey = @ForeignKey(name = "fk_tech_company"))
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@OneToMany(mappedBy = "tech", cascade = CascadeType.ALL)
	public List<Job> getJobs() {
		return jobs;
	}

	public void setJobs(List<Job> jobs) {
		this.jobs = jobs;
	}
}
