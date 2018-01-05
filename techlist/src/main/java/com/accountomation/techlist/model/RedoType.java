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
@Table(name = "redotype")
public class RedoType {

	private String id;
	private String name;
	private List<Redo> redos = new ArrayList<>();
	
	public RedoType() {
		
	}
	
	public RedoType(String id, String name) {
		this.id = id;
		this.name = name;
	}

	@Id
	@Column(name = "redotype_id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "redotype_name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(mappedBy = "type", cascade = CascadeType.ALL)
	public List<Redo> getRedos() {
		return redos;
	}

	public void setRedos(List<Redo> redos) {
		this.redos = redos;
	}
}
