package Mongo;

import Hibernate.Conexion;
import Pack.EquiposEntity;
import Pack.JugadoresEntity;
import Pack.PartidosEntity;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by Joakin on 17/02/2017.
 */
public class AccesoMongo {

    MongoClient mongoClient;
    MongoCollection<Document> collection;
    MongoDatabase db;
    Conexion conexion;

    public AccesoMongo(Conexion conexion) {
        this.conexion = conexion;
        try {
            // PASO 1: Conexión al Server de MongoDB Pasandole el host y el
            // puerto
            mongoClient = new MongoClient("localhost", 27017);

            // PASO 2: Conexión a la base de datos
            db = mongoClient.getDatabase("League");
            System.out.println("Conectado a BD MONGO");

        } catch (Exception e) {
            System.out.println("Error leyendo la BD MONGO: " + e.getMessage());
            System.out.println("Fin de la ejecucion del programa");
            System.exit(1);
        }
        //writeTeams(conexion.getEquipos(), conexion.getJugadores());
        writeGames(conexion.getPartidos());

    }

    public void writeTeams(HashMap<Integer, EquiposEntity> equipos, HashMap<Integer, JugadoresEntity> jugadores) {
        System.out.println("WRITING TEAMS INTO MONGO ...");
        try {
            collection = db.getCollection("equipos");
            SortedSet<Integer> keys = new TreeSet<Integer>(equipos.keySet());
            collection.drop();
            for (int key : keys) {
                System.out.println("KEY" + key);
                Document document = new Document();
                document.put("idEquipo", equipos.get(key).getIdEquipo());
                document.put("nombreEquipo", equipos.get(key).getNombre());
                document.put("ciudad", equipos.get(key).getCiudad());
                document.put("puntos", equipos.get(key).getPuntos());
                document.put("web", equipos.get(key).getWeb());
                SortedSet<Integer> keysx = new TreeSet<Integer>(jugadores.keySet());
                ArrayList<Document> arrayListDoc = new ArrayList<>();
                for (int keyx : keysx) {
                    System.out.println("keyx " + keyx + " " + equipos.get(key));
                    System.out.println(jugadores.get(keyx).getEquiposByEquipo().getIdEquipo() == (equipos.get(key).getIdEquipo()));
                    if (jugadores.get(keyx).getEquiposByEquipo().getIdEquipo() == (equipos.get(key).getIdEquipo())) {
                        Document documentDetail = new Document();
                        documentDetail.put("idjugador", jugadores.get(keyx).getIdJugador());
                        documentDetail.put("nombre", jugadores.get(keyx).getNombre());
                        documentDetail.put("altura", jugadores.get(keyx).getAltura());
                        documentDetail.put("apellido", jugadores.get(keyx).getApellido());
                        documentDetail.put("fechaAlta", jugadores.get(keyx).getFechaAlta());
                        documentDetail.put("puesto", jugadores.get(keyx).getPuesto());
                        documentDetail.put("salario", jugadores.get(keyx).getSalario());
                        arrayListDoc.add(documentDetail);
                        System.out.println(document);
                    }

                }
                document.put("jugadores", arrayListDoc);
                collection.insertOne(document);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("DONE");


    }

    public void writeGames(HashMap<Integer, PartidosEntity> partidos) {
        System.out.println("WRITING TEAMS INTO MONGO ...");
        try {
            collection = db.getCollection("partidos");
            SortedSet<Integer> keys = new TreeSet<Integer>(partidos.keySet());
            for (int key : keys) {
                System.out.println("KEY" + key);
                Document document = new Document();
                document.put("idPartidos", partidos.get(key).getIdPartido());
                document.put("local", partidos.get(key).getEquiposByElocal().getNombre());
                document.put("visitante", partidos.get(key).getEquiposByEvisitante().getNombre());
                document.put("resultado", partidos.get(key).getResultado());
                document.put("fecha", partidos.get(key).getFecha());
                document.put("arbitro", partidos.get(key).getArbitro());
                collection.insertOne(document);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
