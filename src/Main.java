import Hibernate.Conexion;
import Mongo.AccesoMongo;

/**
 * Created by Joakin on 17/02/2017.
 */
public class Main {
    public static void main(String[] args) {
        Conexion conexion = new Conexion();
        conexion.getEquipos();
        conexion.getJugadores();
        conexion.getPartidos();
        AccesoMongo mongo = new AccesoMongo(conexion);


    }

}
