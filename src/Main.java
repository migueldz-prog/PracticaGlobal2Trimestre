import java.sql.*;
import java.util.*;


public class Main {
    public static void main(String[] args) {

        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String usuario = "RIBERA";
        String contraseña = "ribera";

        Scanner sc = new Scanner(System.in);       //necesitamos el scanner asiq le creamos
        int opcion; //creamos la variable opcion para usar el switch
        Connection conexion = null;  //con esto podemos usar fuera del try la variable de conexion

        try { conexion = DriverManager.getConnection(url, usuario, contraseña);
            ArtistaDAO artistaDAO = new ArtistaDAO(conexion);
            ConciertoDAO conciertoDAO = new ConciertoDAO(conexion);
            EntradaDAO entradaDAO = new EntradaDAO(conexion);
            //aqui hemos creado los objetos de las clases DAO para usarlos en el main

            do {
                System.out.println("===Elige una opcion===");
                System.out.println("1. Añadir un artista");       //mostrarmos el menu
                System.out.println("2. Añadir un concierto");
                System.out.println("3. Eliminar un artista");
                System.out.println("4. Eliminar un concierto");
                System.out.println("5. Listar artistas");
                System.out.println("6. Listar conciertos");
                System.out.println("7. Registrar entrada");
                System.out.println("8. Listar entradas");
                System.out.println("0. Salir");

                opcion = sc.nextInt();
                sc.nextLine();


                switch (opcion) {

                    case 1: //anadimos artista, pedimos datos

                        System.out.println("Nombre:");
                        String nombre = sc.nextLine();

                        System.out.println("Genero musical:");
                        String genero = sc.nextLine();

                        System.out.println("Pais:");
                        String pais = sc.nextLine();

                        Artista artista = new Artista(0, nombre, genero, pais);
                        //creo el objeto artista y el new llama al constructor de artista

                        artistaDAO.insertar(artista);
                        //y paso ese objeto al metodo insertar de la clase DAO para que lo inserte
                        break;

                    case 2:
                        //anadimos concierto, pedimos los datos
                        System.out.println("ID del artista:");
                        int idArtista = sc.nextInt();
                        sc.nextLine();

                        Artista art = artistaDAO.buscarPorId(idArtista);
                        //se llama al metodo buscarPorId de la clase artistaDAO para ver si existe el artista

                        if (art == null) {
                            System.out.println("El artista no existe");//mensaje que se imprime si no se encuentra
                            break;
                        }

                        System.out.println("Fecha (YYYY-MM-DD):");
                        String fecha = sc.nextLine();

                        System.out.println("Lugar:");
                        String lugar = sc.nextLine();

                        System.out.println("Precio:");
                        double precio = sc.nextDouble();
                        sc.nextLine(); //por valor numerico

                        Concierto concierto = new Concierto(0, art, fecha, lugar, precio);
                        //creo el objeto artista y llamo al constructor de la clase concierto
                        conciertoDAO.insertar(concierto);
                        //paso el objeto, creado con las variables rellenadas, al metodo insertar para que asi sea en la tabla
                        break;

                    case 3:
                        //quitar artista
                        System.out.println("ID del artista a eliminar:");
                        int idEliminarArtista = sc.nextInt(); //recogemos el id por el scanner
                        artistaDAO.eliminar(idEliminarArtista); //le pasamos el id al metodo eliminar para que ejecute el DELETE
                        break;

                    case 4:
                        //quitar concierto
                        System.out.println("ID del concierto a eliminar:");
                        int idEliminarConcierto = sc.nextInt();
                        conciertoDAO.eliminar(idEliminarConcierto);
                        break;

                    case 5:
                        //del objeto creado antes artistaDAO llamo a su metodo listar para sacar la select con la info
                        artistaDAO.listar();
                        break;

                    case 6:

                        conciertoDAO.listar();
                        break;

                    case 7:
                        System.out.println("ID del concierto:");
                        int idConcierto = sc.nextInt();
                        //pedimos de que concierto queremos la entrada y comprobamos que existe
                        sc.nextLine();

                        Concierto conc = conciertoDAO.buscarPorId(idConcierto);

                        if (conc == null) {
                            System.out.println("El concierto no existe");
                            break;
                        }

                        System.out.println("Comprador:");
                        String comprador = sc.nextLine();

                        System.out.println("Cantidad:");
                        int cantidad = sc.nextInt();
                        sc.nextLine();

                        System.out.println("Fecha de compra (YYYY-MM-DD):");
                        String fechaCompra = sc.nextLine();

                        Entrada entrada = new Entrada(0, conc, comprador, cantidad, fechaCompra);
                        entradaDAO.insertar(entrada);
                        break;

                    case 8:
                        entradaDAO.listar();
                        break;

                    default:
                        System.out.println("Opcion no valida");
                }

            } while (opcion != 0);


        }catch(SQLException e ) {
            System.out.println("Error al conectar" + e.getMessage());
        }
    }
}