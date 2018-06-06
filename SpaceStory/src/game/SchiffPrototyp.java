package game;

import java.util.List;

import spacefight.schiff.SystemBucht;

public class SchiffPrototyp {

	private String bezeichnung;
	private List<SystemBucht> buchten;
	private double masse;
	private double wendigkeit;
	private double radius;
	private double huelle;
	
	public SchiffPrototyp(String bezeichnung, List<SystemBucht> buchten, double masse, double wendigkeit, double radius, double huelle) {
		super();
		this.bezeichnung = bezeichnung;
		this.buchten = buchten;
		this.masse = masse;
		this.wendigkeit = wendigkeit;
		this.radius = radius;
		this.huelle = huelle;
	}
	
	public String getBezeichnung() {
		return bezeichnung;
	}
	
	public List<SystemBucht> getBuchten() {
		return buchten;
	}
	
	public double getHuelle() {
		return huelle;
	}
	
	public double getMasse() {
		return masse;
	}
	
	public double getRadius() {
		return radius;
	}
	
	public double getWendigkeit() {
		return wendigkeit;
	}
	
	
	
}
