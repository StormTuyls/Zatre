package persistentie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domein.Speler;

public class SpelerMapper {

    private static final String INSERT_SPELER = "INSERT INTO ID372718_g60.Speler (naam, geboortejaar, aantalSpeelKansen)"
            + "VALUES (?, ?, ?)";
    private static final String UPDATE_SPELER = "UPDATE ID372718_g60.Speler SET aantalSpeelKansen = ? WHERE naam = ? AND geboortejaar = ?";

    public void voegToe(Speler speler) {

        try (
                Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
                PreparedStatement query = conn.prepareStatement(INSERT_SPELER)) {
            query.setString(1, speler.getNaam());
            query.setInt(2, speler.getGeboortejaar());
            query.setInt(3, speler.getAantalSpeelKansen());
            query.executeUpdate();

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<Speler> geefSpelers() {
        List<Speler> spelers = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
                PreparedStatement query = conn.prepareStatement("SELECT * FROM ID372718_g60.Speler");
                ResultSet rs = query.executeQuery()) {

            while (rs.next()) {
                String naam = rs.getString("naam");
                int geboortejaar = rs.getInt("geboortejaar");
                int aantalSpeelkansen= rs.getInt("aantalspeelkansen");
                spelers.add(new Speler(naam, geboortejaar,aantalSpeelkansen));
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return spelers;
    }

    public Speler geefSpeler(String naam, int geboortejaar) {
        Speler speler = null;

        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
                PreparedStatement query = conn.prepareStatement("SELECT * FROM ID372718_g60.Speler WHERE naam = ? AND geboortejaar = ?")) {
            
            try (ResultSet rs = query.executeQuery()) {
                if (rs.next()) {
                    String naamString = rs.getString("naam");
                    int geboortejaarInt = rs.getInt("geboortejaar");

                    speler = new Speler(naamString, geboortejaarInt);
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return speler;
    }

    public void slaKredietOp(Speler speler) {
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
                PreparedStatement query = conn.prepareStatement(UPDATE_SPELER)) {
        	query.setInt(1, speler.getAantalSpeelKansen());
        	query.setString(2, speler.getNaam());
            query.setInt(3, speler.getGeboortejaar());
            query.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
