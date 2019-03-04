package model;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.*;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.*;

/**
 * @author sonjaml 4.3.2019
 */

public class ValuuttaAccessObject implements ValuuttaDAO_IF {	
	static SessionFactory istuntotehdas;

	@Override
	public boolean createValuutta(Valuutta valuutta) {
		
		Session istunto = istuntotehdas.openSession();
		Transaction transaktio = null;
		boolean onnistuiko = false;

		try{
			transaktio = istunto.beginTransaction();
			Valuutta v = new Valuutta(valuutta.getTunnus(), valuutta.getVaihtokurssi(), valuutta.getNimi());
			istunto.saveOrUpdate(v);
			transaktio.commit();
			onnistuiko = true;
			
		} catch(Exception e){
			if (transaktio!=null) transaktio.rollback();
			throw e;
			
		} finally{
			istunto.close();
		}
		return onnistuiko;
	}

	@Override
	public Valuutta readValuutta(String tunnus) {
		
		Session istunto = istuntotehdas.openSession();
		Transaction transaktio = null;
		Valuutta val = new Valuutta();

		try{
			istunto = istuntotehdas.openSession();
			transaktio = istunto.beginTransaction();
			istunto.load(val, tunnus);		
			transaktio.commit();
			
		} catch(Exception e){
			if (transaktio!=null) transaktio.rollback();
			throw e;
			
		} finally{
			istunto.close();
		}
		
		return val;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Valuutta[] readValuutat() {
		
		Session istunto = istuntotehdas.openSession();
		Transaction transaktio = null;
		List<Valuutta> valuutat = new ArrayList<>();
		try{
			transaktio = istunto.beginTransaction();
			valuutat = istunto.createQuery( "from Valuutta" ).list();
			transaktio.commit();
			
		} catch (Exception e){
			if (transaktio!=null) transaktio.rollback();
			throw e;
			
		} finally{
			istunto.close();
		}
		
		// muunnetaan Valuutta[] muotoon, jotta yhteensopiva ohjelman muiden luokkien kanssa
		Valuutta[] valuutatPalautus = new Valuutta[valuutat.size()];
		int i = 0;
		for (Valuutta val : valuutat) {
			valuutatPalautus[i] = val;
			i++;
		}
		
		return valuutatPalautus;
	}

	@Override
	public boolean updateValuutta(Valuutta valuutta) {
		
		Session istunto = istuntotehdas.openSession();
		Transaction transaktio = null;
		boolean onnistuiko = false;

		try{
			istunto = istuntotehdas.openSession();
			transaktio = istunto.beginTransaction();
			Valuutta v = (Valuutta)istunto.get(Valuutta.class, valuutta.getTunnus());
			if (v != null) {
				v.setVaihtokurssi(valuutta.getVaihtokurssi());
				v.setNimi(valuutta.getNimi());
			} else {
				System.out.println("Ei löytynyt päivitettävää!");
			}
			transaktio.commit();
			onnistuiko = true;
			
		} catch(Exception e){
			if (transaktio!=null) transaktio.rollback();
			throw e;
			
		} finally{
			istunto.close();			
		}
		
		return onnistuiko;
	}

	@Override
	public boolean deleteValuutta(String tunnus) {
		
		Session istunto = istuntotehdas.openSession();
		Transaction transaktio = null;
		boolean onnistuiko = false;

		try{
			istunto = istuntotehdas.openSession();
			transaktio = istunto.beginTransaction();
			Valuutta v = (Valuutta)istunto.get(Valuutta.class, tunnus);
			if (v != null) {
				istunto.delete(v);
			} else {
				System.out.println("Ei löytynyt poistettavaa!");
			}
			transaktio.commit();
			onnistuiko = true;
			
		} catch(Exception e){
			if (transaktio != null) transaktio.rollback();
			throw e;
			
		} finally{
			istunto.close();
		}
		
		return onnistuiko;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void luoIstuntotehdas() {
		// Alkutoimet: SessionFactoryn luonti (raskasta, vain kerran tekstimainissa).
		
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
		try{
			istuntotehdas = new MetadataSources(registry).buildMetadata().buildSessionFactory();
		} catch(Exception e) {
			System.out.println("Pieleen meni!");
			StandardServiceRegistryBuilder.destroy( registry );
			e.printStackTrace();
		} finally {
			System.out.println("Istuntotehtaan luominen onnistui.");
		}
	}
	
	public void suljeIstuntotehdas() {
		// ohjelman lopetus
		istuntotehdas.close();
	}
}
