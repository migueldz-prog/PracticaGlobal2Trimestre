import java.sql.*;

public class ArtistaDAO {

    private Connection conn;

    public ArtistaDAO(Connection conn) {
        this.conn = conn;
    }

    public void insertar(Artista a) {
        try {
            String sql = "INSERT INTO ARTISTA (NOMBRE, GENEROMUSICAL, PAISORIGEN) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, a.getNombre());
            ps.setString(2, a.getGeneroMusical());
            ps.setString(3, a.getPaisOrigen());

            ps.executeUpdate();
            System.out.println("Artista insertado");

        }catch(SQLException e ) {
            System.out.println("Error al conectar" + e.getMessage());
        }
    }

    public void eliminar(int id) {
        try {
            String sqlE = "DELETE FROM ARTISTA WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sqlE);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Artista eliminado");
        }catch(SQLException e ) {
            System.out.println("Error al conectar" + e.getMessage());
        }
    }

    public void listar() {
        try {
            String sqll = "SELECT * FROM ARTISTA";
            PreparedStatement ps = conn.prepareStatement(sqll);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println(
                "ID: " + rs.getInt("id") +
                " Nombre: " + rs.getString("nombre") +
                " Género: " + rs.getString("generoMusical") +
                " País: " + rs.getString("paisOrigen"));
            }
        }catch(SQLException e ) {
            System.out.println("Error al conectar" + e.getMessage());
        }
    }

    public Artista buscarPorId(int id) {
        Artista artista = null;

        try {
            String sql = "SELECT * FROM ARTISTA WHERE ID = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                artista = new Artista(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("generoMusical"),
                        rs.getString("paisOrigen")
                );
            }

        }catch(SQLException e ) {
            System.out.println("Error al conectar" + e.getMessage());
        }

        return artista;
    }
}