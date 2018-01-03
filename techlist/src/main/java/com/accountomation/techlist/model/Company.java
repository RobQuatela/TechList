package com.accountomation.techlist.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "company")
public class Company {

	private String id;
	private String name;
	private List<Technician> techs = new ArrayList<>();
	private List<Job> jobs = new ArrayList<>();
	
	public Company() {
		
	}
	
	public Company(String id) {
		this.id = id;
	}
	
	public Company(String id, String name) {
		this.id = id;
		this.name = name;
	}

	@Id
	@Column(name = "co_id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "co_name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
	public List<Technician> getTechs() {
		return techs;
	}

	public void setTechs(List<Technician> techs) {
		this.techs = techs;
	}

	@OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
	public List<Job> getJobs() {
		return jobs;
	}

	public void setJobs(List<Job> jobs) {
		this.jobs = jobs;
	}
}
