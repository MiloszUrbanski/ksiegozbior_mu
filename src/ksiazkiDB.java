import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ksiazkiDB {
    private static final String URL = "jdbc:mysql://localhost:3306/ksiegozbior";
    private static final String USER = "root";
    private static final String PASSWORD = "haslohaslo";


    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public List<Ksiazka> getAllKsiazki() {
        List<Ksiazka> lista = new ArrayList<>();
        String sql = "SELECT id, tytul, autor, rok_wydania FROM ksiazki ORDER BY id";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String tytul = rs.getString("tytul");
                String autor = rs.getString("autor");
                int rokWydania = rs.getInt("rok_wydania");
                lista.add(new Ksiazka(id, tytul, autor, rokWydania));
            }

        } catch (SQLException e) {
            System.err.println("Błąd odczytu danych:");
            e.printStackTrace();
        }
        return lista;
    }

    public boolean addKsiazka(Ksiazka ksiazka) {
        String sql = "INSERT INTO ksiazki (tytul, autor, rok_wydania) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, ksiazka.getTytul());
            pstmt.setString(2, ksiazka.getAutor());
            pstmt.setInt(3, ksiazka.getRokWydania());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Błąd dodawania książki:");
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteKsiazka(int id) {
        String sql = "DELETE FROM ksiazki WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Błąd usuwania książki:");
            e.printStackTrace();
            return false;
        }
    }
    public boolean updateKsiazka(Ksiazka ksiazka) {
        String sql = "UPDATE ksiazki SET tytul = ?, autor = ?, rok_wydania = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, ksiazka.getTytul());
            pstmt.setString(2, ksiazka.getAutor());
            pstmt.setInt(3, ksiazka.getRokWydania());
            pstmt.setInt(4, ksiazka.getId());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Błąd aktualizacji książki:");
            e.printStackTrace();
            return false;
        }
    }
}