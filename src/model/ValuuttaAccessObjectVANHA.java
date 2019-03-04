package model;

import java.sql.*;

/**
 * @author sonjaml 2.3.2019
 */

public class ValuuttaAccessObjectVANHA implements ValuuttaDAO_IF {
	private int maara;
	
	public ValuuttaAccessObjectVANHA() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.err.println("JDBC-ajurin lataus epäonnistui");
			System.exit(-1); // lopetus heti virheen vuoksi
		}
	}
	
	public static Connection luoYhteys() {
		Connection conn = null;		
		String URL = "jdbc:mysql://localhost/valuuttaDB";
		String USERNAME = "olso";
		String PASSWORD = "olso";
		
		try {
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Yhteys epäonnistui.");
		}
		return conn;
	}

	@Override
	public boolean createValuutta(Valuutta valuutta) {
		
		if (loytyykoJoTietokannasta(valuutta)) {
			return false;
		}
		
		Connection conn = luoYhteys();
		try {			
			PreparedStatement stmt = conn.prepareStatement(
					"INSERT INTO Valuutta(tunnus,valuuttakurssi,nimi) VALUES (?,?,?);");
			stmt.setString(1, valuutta.getTunnus());
			stmt.setDouble(2, valuutta.getVaihtokurssi());
			stmt.setString(3, valuutta.getNimi());
	        stmt.executeUpdate();
	        conn.close();
	        return true;
	        
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}		
	}

	@Override
	public Valuutta readValuutta(String tunnus) {
		Connection conn = luoYhteys();
		
		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Valuutta WHERE tunnus = ?");
			stmt.setString(1, tunnus);
			ResultSet rs = stmt.executeQuery();
			
			if(!rs.next()) {
	            return null;
	        }
			
			Valuutta valuutta = new Valuutta(rs.getString("tunnus"), 
					rs.getDouble("valuuttakurssi"), rs.getString("nimi"));
			
			stmt.close();
			rs.close();
			conn.close();
			return valuutta;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}		
	}

	@Override
	public Valuutta[] readValuutat() {	
		rivienMaara();
		Valuutta[] valuutat = new Valuutta[this.maara];
		if (valuutat.length == 0) {
			return null;
		}
		
		Connection conn = luoYhteys();
		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Valuutta;");
			ResultSet rs = stmt.executeQuery();
			
			int i = 0;
			while (rs.next()) {
				Valuutta valuutta = new Valuutta(rs.getString("tunnus"), 
						rs.getDouble("valuuttakurssi"), rs.getString("nimi"));
				valuutat[i] = valuutta;
				i++;
			}
			
			stmt.close();
			rs.close();
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return valuutat;
	}	

	@Override
	public boolean updateValuutta(Valuutta valuutta) {
		Connection conn = luoYhteys();
		try {
			PreparedStatement stmt = conn.prepareStatement(
					"UPDATE Valuutta SET valuuttakurssi = ?, nimi = ? WHERE tunnus = ?; ");
			stmt.setDouble(1, valuutta.getVaihtokurssi());
			stmt.setString(2, valuutta.getNimi());			
			stmt.setString(3, valuutta.getTunnus());
	        stmt.executeUpdate();
	        conn.close();
	        return true;
	        
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteValuutta(String tunnus) {
		Connection conn = luoYhteys();
		try {
			PreparedStatement stmt = conn.prepareStatement("DELETE FROM Valuutta WHERE tunnus = ?; ");
			stmt.setString(1, tunnus);
	        stmt.executeUpdate();
	        conn.close();
	        return true;
	        
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}	
	}
	
	private void rivienMaara() {
		Connection conn = luoYhteys();
		
		try {
			PreparedStatement stmt = conn.prepareStatement(
					"SELECT COUNT(*) AS 'maara' FROM Valuutta;");
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				this.maara = rs.getInt("maara");
			}		
			
			stmt.close();
			rs.close();
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private boolean loytyykoJoTietokannasta(Valuutta val) {
		
		Valuutta[] valuutat = readValuutat();
		
		for (Valuutta valuutta : valuutat) {
			if (valuutta.getTunnus().equals(val.getTunnus())) {
				return true;
			}
		}
		return false;
		
	}
}
