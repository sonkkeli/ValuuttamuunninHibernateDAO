package model;

import javax.persistence.*;

/**
 * @author sonjaml 4.3.2019
 */

@Entity
@Table(name="valuutta")
public class Valuutta {
	
	@Id
	@Column(name ="tunnus")
	private String tunnus;
	
	@Column(name ="valuuttakurssi")
	private double vaihtokurssi;
	
	@Column(name ="nimi")
	private String nimi;
	
	public Valuutta(String tunnus, double vaihtokurssi, String nimi) {
		super();
		this.tunnus = tunnus;
		this.vaihtokurssi = vaihtokurssi;
		this.nimi = nimi;
	}
	
	public Valuutta() {
		super();
	}
	
	public void setTunnus(String tunnus) {
		this.tunnus = tunnus;
	}
	
	public void setNimi(String nimi) {
		this.nimi = nimi;
	}

	public void setVaihtokurssi(double vaihtokurssi) {
		this.vaihtokurssi = vaihtokurssi;
	}
	
	public String getTunnus() {
		return this.tunnus;
	}

	
	public String getNimi() {
		return this.nimi;
	}

	public double getVaihtokurssi() {
		return this.vaihtokurssi;
	}

	@Override
	public String toString() {
		return "Valuutan tiedot: " + tunnus + ", " + vaihtokurssi + ", " + nimi;
	}

}
