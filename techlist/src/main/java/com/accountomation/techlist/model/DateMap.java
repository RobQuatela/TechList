package com.accountomation.techlist.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "datemap")
public class DateMap {

	private Date id;
	private String day;
	private int week;
	private String month;
	private int year;
	private List<Job> jobs = new ArrayList<>();
	
	public DateMap() {
		
	}
	
	public DateMap(Date id) {
		this.id = id;
	}

	@Id
	@Column(name = "date_id")
	public Date getId() {
		return id;
	}

	public void setId(Date id) {
		this.id = id;
	}

	@Column(name = "date_day")
	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	@Column(name = "date_week")
	public int getWeek() {
		return week;
	}

	public void setWeek(int week) {
		this.week = week;
	}

	@Column(name = "date_month")
	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	@Column(name = "date_year")
	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	@OneToMany(mappedBy = "date", cascade = CascadeType.ALL)
	public List<Job> getJobs() {
		return jobs;
	}

	public void setJobs(List<Job> jobs) {
		this.jobs = jobs;
	}
	
	public static int daysBetween(DateMap dateRedo, DateMap dateOrigin) {
		return (int)((dateRedo.getId().getTime() - dateOrigin.getId().getTime()) / (1000 * 60 * 60 * 24));
	}
}
