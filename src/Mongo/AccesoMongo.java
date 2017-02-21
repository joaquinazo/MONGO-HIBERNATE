package Mongo;

import Hibernate.Conexion;
import Pack.EquiposEntity;
import Pack.JugadoresEntity;
import Pack.PartidosEntity;
import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.sql.Timestamp;
import java.util.*;

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
        //writeGames(conexion.getPartidos());
        int choice;
        Scanner sc = new Scanner(System.in);

        do {
            System.out.println("  ");
            System.out.println("  ");
            System.out.println("  ");
            System.out.println("---------------------------------");
            System.out.println("CRUD:\n 1)Mostrar todos los jugadores de un determinado equipo \n 2)Mostrar el equipo de un determinado jugador\n 3)Mostrar los partidos jugador por un determinado equipo\n 4)Insertar un nuevo jugador en un equipo\n");

            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    getFirstOption();
                    break;
                case 2:
                    secondOption();
                    break;
                case 3:
                    thirdOption();
                    break;
                case 4:
                    FourtOption();
                    break;
            } // end of switch
        } while (choice != 10);

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

    public void getFirstOption() {
      /*  Se muestra un listado con los equipos disponibles y se permite seleccionar uno de ellos
        Se muestra un listado con Nombre, Apellidos y Altura del jugador */
        collection = db.getCollection("equipos");
        MongoCursor<Document> cursor = collection.find().iterator();
        System.out.println("Dime de que equipo quieres ver los jugadores");
        while (cursor.hasNext()) {
            Document rs = cursor.next();
            System.out.println(rs.getInteger("idEquipo") + " " + rs.getString("nombreEquipo"));

        }
        Scanner sc = new Scanner(System.in);
        int team = sc.nextInt();
        Document newDoc = new Document();
        newDoc.put("idEquipo", team);
        MongoCursor<Document> cursor2 = collection.find(newDoc).iterator();
        ArrayList<Document> players = new ArrayList<>();
        while (cursor2.hasNext()) {
            Document rs2 = cursor2.next();
            players = (ArrayList) rs2.get("jugadores");

        }
        for (int i = 0; i < players.size(); i++) {
            System.out.println("Jugador  " + i + ": nombre-->" + players.get(i).getString("nombre") + " " + players.get(i).getString("apellido") + " altura--> " + players.get(i).getDouble("altura"));
        }
    }

    public void secondOption() {
        System.out.println("SECOND OPTION ");
        Scanner sc = new Scanner(System.in);
        System.out.println("Dime el nombre del jugador del equipo que quieras buscar: ");
        String name = sc.nextLine();
        collection = db.getCollection("equipos");
        MongoCursor<Document> cursor = collection.find().iterator();
        while (cursor.hasNext()) {
            Document rs = cursor.next();
            Document newDoc = new Document();
            newDoc.put("idEquipo", rs.getInteger("idEquipo"));

            MongoCursor<Document> cursor2 = collection.find(newDoc).iterator();
            ArrayList<Document> players = new ArrayList<>();
            while (cursor2.hasNext()) {
                Document rs2 = cursor2.next();
                players = (ArrayList) rs2.get("jugadores");

            }
            for (int i = 0; i < players.size(); i++) {
                if (name.equals(players.get(i).getString("nombre"))) {
                    System.out.println(name + " pertenece a : " + rs.getString("nombreEquipo"));
                }
            }

        }

    }

    public void thirdOption() {
        Scanner sc = new Scanner(System.in);
        collection = db.getCollection("equipos");
        MongoCursor<Document> cursor = collection.find().iterator();
        System.out.println("Dime de que equipo quieres ver los partidos (DEBES ESCRIBIR EL EQUIPO SEGUN SE LISTA)");
        HashMap<Integer, String> equipos = new HashMap<>();
        while (cursor.hasNext()) {
            Document rs = cursor.next();
            equipos.put(rs.getInteger("idEquipo"), rs.getString("nombreEquipo"));
            System.out.println(rs.getInteger("idEquipo") + " " + rs.getString("nombreEquipo"));

        }
        int nameInt = sc.nextInt();
        String name = equipos.get(nameInt);
        collection = db.getCollection("partidos");
        cursor = collection.find().iterator();
        System.out.println("PARTIDOS JUGADOS POR " + name);
        while (cursor.hasNext()) {
            Document rs = cursor.next();
            if (rs.getString("local").equals(name)) {
                System.out.println("RIVAL: " + rs.getString("visitante") + " RESULTADO : " + rs.getString("resultado"));
            }
            if (rs.getString("visitante").equals(name)) {
                System.out.println("RIVAL: " + rs.getString("local") + " RESULTADO : " + rs.getString("resultado"));

            }

        }

    }

    public void FourtOption() {
        Scanner sc = new Scanner(System.in);
        collection = db.getCollection("equipos");
        MongoCursor<Document> cursor = collection.find().iterator();
        System.out.println("Dime en que equipo quieres añadir un jugador");
        while (cursor.hasNext()) {
            Document rs = cursor.next();
            System.out.println(rs.getInteger("idEquipo") + " " + rs.getString("nombreEquipo"));

        }
        int team = sc.nextInt();
        Document newDoc = new Document();
        newDoc.put("idEquipo", team);
        MongoCursor<Document> cursor2 = collection.find(newDoc).iterator();
        ArrayList<Document> players = new ArrayList<>();
        while (cursor2.hasNext()) {
            Document rs = cursor2.next();
            players = (ArrayList) rs.get("jugadores");
            Document documentDetail = new Document();
            System.out.println("nombre");
            String nombre = sc.next();
            System.out.println("apellido");
            String ape = sc.next();
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            System.out.println("puesto");
            String puesto = sc.next();
            System.out.println("salary");
            int salary = sc.nextInt();
            System.out.println("altura");
            double altu = sc.nextDouble();
            int id = players.get(players.size() - 1).getInteger("idjugador");
            documentDetail.put("idjugador", id++);
            documentDetail.put("nombre", nombre);
            documentDetail.put("altura", altu);
            documentDetail.put("apellido", ape);
            documentDetail.put("fechaAlta", timestamp);
            documentDetail.put("puesto", puesto);
            documentDetail.put("salario", salary);
            players.add(documentDetail);
            rs.put("jugadores", players);
            collection.replaceOne(newDoc, rs);

        }

    }


}
