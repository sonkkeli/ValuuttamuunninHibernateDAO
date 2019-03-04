package model;

public interface ValuuttaDAO_IF {
	public abstract boolean createValuutta(Valuutta valuutta);
	public abstract Valuutta readValuutta(String tunnus);
	public abstract Valuutta[] readValuutat();
	public abstract boolean updateValuutta(Valuutta valuutta);
	public abstract boolean deleteValuutta(String tunnus);
}
