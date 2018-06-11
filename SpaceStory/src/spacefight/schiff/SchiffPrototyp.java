package spacefight.schiff;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import spacefight.schiff.systeme.SystemBucht;

public class SchiffPrototyp {

	private String bezeichnung;
	private List<SystemBucht> buchten;
	private double masse;
	private double radius;
	private double huelle;
	private BufferedImage bild;
	
	public SchiffPrototyp(String bezeichnung, List<SystemBucht> buchten, double masse, double radius, double huelle, String bildURL) {
		super();
		this.bezeichnung = bezeichnung;
		this.buchten = buchten;
		this.masse = masse;
		this.radius = radius;
		this.huelle = huelle;
		try {
			this.bild = ImageIO.read(new File(bildURL));
		} catch (IOException e) {
		}
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
	
	public BufferedImage getBild() {
		return bild;
	}
	
}
