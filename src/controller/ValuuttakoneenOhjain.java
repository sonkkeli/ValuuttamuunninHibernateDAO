package controller;

import model.Valuuttakone_IF;
import view.ValuuttakoneenGUI_IF;
/**
 * @author sonjaml 20.2.2019
 */
public class ValuuttakoneenOhjain implements ValuuttakoneenOhjain_IF {
	private ValuuttakoneenGUI_IF gui;
	private Valuuttakone_IF valuuttakone;
	
	public ValuuttakoneenOhjain (ValuuttakoneenGUI_IF gui, Valuuttakone_IF valuuttakone) {
		this.gui = gui;
		this.valuuttakone = valuuttakone;
	}

	@Override
	public void muunnos() {
		gui.setTulos(valuuttakone.muunna(gui.getLähtöIndeksi(), gui.getKohdeIndeksi(), gui.getMäärä()));
	}

	@Override
	public String[] getValuutat() {
		return this.valuuttakone.getVaihtoehdot();
	}

}
