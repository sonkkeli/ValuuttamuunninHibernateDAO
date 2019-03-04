package model;

/**
 * @author sonjaml
 */

public interface Valuuttakone_IF {
	public abstract String[] getVaihtoehdot();
	public abstract double muunna(int mistäIndeksi, int mihinIndeksi, double määrä);
}
