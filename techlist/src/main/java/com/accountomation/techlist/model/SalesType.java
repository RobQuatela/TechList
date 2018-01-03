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
@Table(name = "salestype")
public class SalesType {

	private String id;
	private String name;
	private List<SaleDetail> saleDetails = new ArrayList<>();
	
	public SalesType() {
		
	}
	
	public SalesType(String name) {
		this.name = name;
	}
	
	public SalesType(String id, String name) {
		this.id = id;
		this.name = name;
	}

	@Id
	@Column(name = "salestype_id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "salestype_name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(mappedBy = "type", cascade = CascadeType.ALL)
	public List<SaleDetail> getSaleDetails() {
		return saleDetails;
	}

	public void setSaleDetails(List<SaleDetail> saleDetails) {
		this.saleDetails = saleDetails;
	}
}
