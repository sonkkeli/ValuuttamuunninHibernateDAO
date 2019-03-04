 
import java.util.Scanner;

import model.*;

/**
 * @author sonjaml 2.3.2019
 * 
 * Tätä luokkaa käytetään valuuttatietokannan ylläpitoon.
 */

public class TekstiMain {
	static ValuuttaAccessObject valuuttaDAO = new ValuuttaAccessObject();
	static Scanner scanner = new Scanner(System.in);

	public static void listaaValuutat() {
		Valuutta[] valuutat = valuuttaDAO.readValuutat();
		for (Valuutta val : valuutat) {
			System.out.println(val);
		}
	}

	public static void lisääValuutta() {	
		System.out.println("Syötä tunnus:");
		String tunnus = scanner.nextLine();
		System.out.println("Syötä valuuttakurssi:");
		Double kurssi = Double.parseDouble(scanner.nextLine());
		System.out.println("Syötä nimi:");
		String nimi = scanner.nextLine();
		
		valuuttaDAO.createValuutta(new Valuutta(tunnus,kurssi,nimi));
	}

	public static void päivitäValuutta() {
		System.out.println("Syötä päivitettävän valuutan tunnus: ");
		String tunnus = scanner.nextLine();
		// haetaan alkuperäiset arvot, joita muutetaan tarvittaessa
		Valuutta tarkasteltavaValuutta = valuuttaDAO.readValuutta(tunnus);
		
		System.out.println("Syötä kirjaintunnus: V (valuuttakurssi) / N (nimi) / M (molemmat).");
		String mitaPaivitetaan = scanner.nextLine();
		
		if (mitaPaivitetaan.equals("V")) {
			System.out.println("Syötä uusi valuuttakurssi (käytä pistettä erottimena).");
			Double vaihtokurssi = Double.parseDouble(scanner.nextLine());
			tarkasteltavaValuutta.setVaihtokurssi(vaihtokurssi);
			
		} else if (mitaPaivitetaan.equals("N")) {
			System.out.println("Syötä uusi nimi: ");
			String nimi = scanner.nextLine();
			tarkasteltavaValuutta.setNimi(nimi);
			
		} else if (mitaPaivitetaan.equals("M")) {
			System.out.println("Syötä uusi valuuttakurssi (käytä pistettä erottimena): ");
			Double vaihtokurssi = Double.parseDouble(scanner.nextLine());
			tarkasteltavaValuutta.setVaihtokurssi(vaihtokurssi);
			System.out.println("Syötä uusi nimi: ");
			String nimi = scanner.nextLine();
			tarkasteltavaValuutta.setNimi(nimi);
			
		} else {
			System.out.println("Päivitys epäonnistui, koska et osaa kirjoittaa.");
		}
		
		valuuttaDAO.updateValuutta(tarkasteltavaValuutta);
	}

	public static void poistaValuutta() {
		System.out.println("Syötä poistettavan valuutan tunnus:");
		String tunnus = scanner.nextLine();
		valuuttaDAO.deleteValuutta(tunnus);
	}

	public static void main(String[] args) {
		
		valuuttaDAO.luoIstuntotehdas();
		
		char valinta;
		final char CREATE = 'C', READ = 'R', UPDATE = 'U', DELETE = 'D', QUIT = 'Q';

		String vaihtoehdot = "\nC: Lisää uusi valuutta tietokantaan\n" + 
				"R: Listaa tietokannassa olevien valuuttojen tiedot\n" + 
				"U: Päivitä valuutan vaihtokurssi tietokantaan\n" + 
				"D: Poista valuutta tietokannasta\n" + 
				"Q: Lopetus\n" + 
				"Valintasi:";
		do {
			System.out.println(vaihtoehdot);
			valinta = (scanner.nextLine().toUpperCase()).charAt(0);
			switch (valinta) {
			case READ:
				listaaValuutat();
				break;
			case UPDATE:				
				päivitäValuutta();
				break;
			case DELETE:
				poistaValuutta();
				break;
			case CREATE:				
				lisääValuutta();
				break;
			}
		} while (valinta != QUIT);
		
		valuuttaDAO.suljeIstuntotehdas();
	}	
}
