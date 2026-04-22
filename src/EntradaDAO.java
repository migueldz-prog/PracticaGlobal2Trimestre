import java.sql.*;

public class EntradaDAO {

    private Connection conn;
    private ConciertoDAO conciertoDAO;

    public EntradaDAO(Connection conn) {
        this.conn = conn;
        this.conciertoDAO = new ConciertoDAO(conn);
    }

    // INSERTAR
    public void insertar(Entrada entrada) {
        try {
            String sqli = "INSERT INTO ENTRADA (CONCIERTO_ID, COMPRADOR, CANTIDAD, FECHACOMPRA) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sqli);

            ps.setInt(1, entrada.getConcierto().getId());
            //aqui volvemos a llamar al metodo, obtener el dato y usarlo en la sentecia sql en la misma linea de codigo
            ps.setString(2, entrada.getComprador());
            ps.setInt(3, entrada.getCantidad());
            ps.setDate(4, java.sql.Date.valueOf(entrada.getFechaCompra()));
            //combierte el string pedido a formato date que es como esta en la tabla de oracle 

            ps.executeUpdate();
            System.out.println("Entrada registrada");

        }catch(SQLException e ) {
            System.out.println("Error al conectar" + e.getMessage());
        }
    }

    //este metodo listar no necesita que le llegue ningun dato para que funcione
    public void listar() {
        try {
            String sqll = "SELECT * FROM ENTRADA";
            PreparedStatement ps = conn.prepareStatement(sqll);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int conciertoId = rs.getInt("concierto_id");

                //busca el concierto con el id que se ha pasado al metodo
                Concierto concierto = conciertoDAO.buscarPorId(conciertoId);

                System.out.println(
                        "Entrada ID: " + rs.getInt("id") +  //sacamos el dato con el nombre de la columna y el get
                        " Comprador: " + rs.getString("comprador") +
                        " Cantidad: " + rs.getInt("cantidad") +
                        " Lugar: " + concierto.getLugar() +
                        " Artista: " + concierto.getArtista().getNombre()
                );
            }

        }catch(SQLException e ) {
            System.out.println("Error al conectar" + e.getMessage());
        }
    }
}