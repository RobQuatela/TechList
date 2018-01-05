package com.accountomation.techlist.model;

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
@Table(name = "technicianredo")
public class TechnicianRedo {

	private int id;
	private Redo redo;
	private Technician tech;
	
	public TechnicianRedo() {
		
	}
	
	public TechnicianRedo(Redo redo, Technician tech) {
		this.redo = redo;
		this.tech = tech;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "techredo_id")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@ManyToOne(targetEntity = Redo.class)
	@JoinColumn(name = "techredo_redo", nullable = false,
		foreignKey = @ForeignKey(name = "fk_techredo_redo"))
	public Redo getRedo() {
		return redo;
	}

	public void setRedo(Redo redo) {
		this.redo = redo;
	}

	@ManyToOne(targetEntity = Technician.class)
	@JoinColumn(name = "techredo_tech", nullable = false,
		foreignKey = @ForeignKey(name = "fk_techredo_tech"))
	public Technician getTech() {
		return tech;
	}

	public void setTech(Technician tech) {
		this.tech = tech;
	}
}
