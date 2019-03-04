package model;
/**
 * @author sonjaml 2.3.2019
 */
public class Valuuttakone implements Valuuttakone_IF {
	private Valuutta[] valuutat;
	private static ValuuttaAccessObject valuuttaDAO = new ValuuttaAccessObject();
	
	public Valuuttakone() {
		valuuttaDAO.luoIstuntotehdas();
		this.valuutat = valuuttaDAO.readValuutat();
		valuuttaDAO.suljeIstuntotehdas();
	}

	@Override
	public String[] getVaihtoehdot() {
		String[] vaihtoehdot = new String[this.valuutat.length];
		int i = 0;
		for (Valuutta valuutta : this.valuutat) {
			vaihtoehdot[i] = valuutta.getNimi();
			i++;
		}
		return vaihtoehdot;
	}

	@Override
	public double muunna(int mistäIndeksi, int mihinIndeksi, double määrä) {
		return ( määrä / this.valuutat[mistäIndeksi].getVaihtokurssi()) * 
				this.valuutat[mihinIndeksi].getVaihtokurssi();		
	}

}
