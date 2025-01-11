package projetSOA.Projet.models;

import java.time.LocalDate;

public class Event {
	private int id ;
	private String name;
	private String description ;
	private String location;
	private String theme;
	private LocalDate date;
	private double price;
	
	public Event() {
	}

	

	public Event(int id, String name, String description, String location, String theme, LocalDate date,
			double price) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.location = location;
		this.theme = theme;
		this.date = date;
		this.price = price;
	}



	public String getLocation() {
		return location;
	}



	public void setLocation(String location) {
		this.location = location;
	}



	public String getTheme() {
		return theme;
	}



	public void setTheme(String theme) {
		this.theme = theme;
	}



	public LocalDate getDate() {
		return date;
	}



	public void setDate(LocalDate date) {
		this.date = date;
	}



	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
}