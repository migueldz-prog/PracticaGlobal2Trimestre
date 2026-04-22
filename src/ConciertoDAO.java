import java.sql.*;
import java.util.ArrayList;

public class ConciertoDAO {

    private Connection conn;
    private ArtistaDAO artistaDAO;
    //declaramos aqui la conexion y el artista para poderlos usar en todos los metodos de la clase

    public ConciertoDAO(Connection conn) {
        this.conn = conn;
        this.artistaDAO = new ArtistaDAO(conn);
    }


    public void insertar(Concierto concierto) {
        try {
            String sqli = "INSERT INTO CONCIERTO (ARTISTA_ID, FECHA, LUGAR, PRECIOENTRADA) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sqli);

            Artista artista = concierto.getArtista();  // saco el artista
            int idArtista = artista.getId();           // saco el id de este
            ps.setInt(1, idArtista);      // y lo meto en la sentencia --> ?

            //que es lo mismo que ponerlo asi pero mas resumido, preguntado a la IA
            ps.setDate(2, java.sql.Date.valueOf(concierto.getFecha()));
            ps.setString(3, concierto.getLugar());
            ps.setDouble(4, concierto.getPrecioEntrada());

            ps.executeUpdate();
            System.out.println("Concierto insertado"); //mensaje que confirma que ha funcionado

        }catch(SQLException e ) {
            System.out.println("Error al conectar" + e.getMessage());
        }
    }

    public void eliminar(int id) { //necesitamos el id para que funcione
        try {
            String sqle = "DELETE FROM CONCIERTO WHERE ID = ?";
            PreparedStatement ps = conn.prepareStatement(sqle);
            ps.setInt(1, id);

            ps.executeUpdate();
            System.out.println("Concierto eliminado");

        }catch(SQLException e ) {
            System.out.println("Error al conectar" + e.getMessage());
        }
    }

    public void listar() {
        try {
            String sqll = "SELECT * FROM CONCIERTO";
            PreparedStatement ps = conn.prepareStatement(sqll);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                //para listar todos usamos el bucle
                int id = rs.getInt("id");
                int artistaId = rs.getInt("artista_id");

                Artista artista = artistaDAO.buscarPorId(artistaId);

                String nombreArtista;

                if (artista != null) {
                    nombreArtista = artista.getNombre();
                } else {
                    nombreArtista = "Desconocido";
                }
                System.out.println(
                        "ID: " + id +  //hallamos los resultados de la select y les ponemos en el print directamente
                        " Artista: " + nombreArtista +
                        " Fecha: " + rs.getString("fecha") +
                        " Lugar: " + rs.getString("lugar") +
                        " Precio: " + rs.getDouble("precioEntrada")
                );
            }

        }catch(SQLException e ) {
            System.out.println("Error al conectar" + e.getMessage());
        }
    }


    // necesitamos buscarporid para  la entrada de los conciertos
    public Concierto buscarPorId(int idBuscar) {
        Concierto concierto = null; // se pone =null a la variable tipo concierto porque si no java daria error al intentar usar una variable que no esta inicializada a mingun valor

        try {
            String sql = "SELECT * FROM CONCIERTO WHERE ID = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idBuscar);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int artistaId = rs.getInt("artista_id");
                //sacamos el resultado de la sentencia y lo guardamos en artistaId para pasarselo al metodo y que busque el artista
                Artista artista = artistaDAO.buscarPorId(artistaId);

                concierto = new Concierto(
                        rs.getInt("id"),
                        artista,
                        rs.getString("fecha"),
                        rs.getString("lugar"),
                        rs.getDouble("precioEntrada")
                );
            }

        }catch(SQLException e ) {
            System.out.println("Error al conectar" + e.getMessage());
        }

        return concierto;
    }
}
