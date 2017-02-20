package Hibernate;

import Pack.EquiposEntity;
import Pack.JugadoresEntity;
import Pack.PartidosEntity;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Joakin on 17/02/2017.
 */
public class Conexion {


    public HashMap<Integer, EquiposEntity> getEquipos() {
        SessionFactory sf = new Configuration().configure("sesion.xml").buildSessionFactory();
        Session s = sf.openSession();
        HashMap<Integer, EquiposEntity> hashMap = new HashMap<>();
        Query q = s.createQuery("select equipos from EquiposEntity equipos");
        List results = q.list();
        Iterator iteratorEquipos = results.iterator();
        int i = 0;
        while (iteratorEquipos.hasNext()) {
            EquiposEntity jp = (EquiposEntity) iteratorEquipos.next();
            hashMap.put(jp.getIdEquipo(), jp);
            i++;
        }
        System.out.println(hashMap.size());
        return hashMap;

    }

    public HashMap<Integer, JugadoresEntity> getJugadores() {
        SessionFactory sf = new Configuration().configure("sesion.xml").buildSessionFactory();
        Session s = sf.openSession();
        HashMap<Integer, JugadoresEntity> hashMap = new HashMap<>();
        Query q = s.createQuery("select jugadores from JugadoresEntity jugadores");
        List results = q.list();
        Iterator iteratorEquipos = results.iterator();
        int i = 0;
        while (iteratorEquipos.hasNext()) {
            JugadoresEntity jp = (JugadoresEntity) iteratorEquipos.next();
            hashMap.put(jp.getIdJugador(), jp);
            i++;
        }
        System.out.println(hashMap.size());
        return hashMap;

    }

    public HashMap<Integer, PartidosEntity> getPartidos() {
        SessionFactory sf = new Configuration().configure("sesion.xml").buildSessionFactory();
        Session s = sf.openSession();
        HashMap<Integer, PartidosEntity> hashMap = new HashMap<>();
        Query q = s.createQuery("select partidos from PartidosEntity partidos");
        List results = q.list();
        Iterator iteratorEquipos = results.iterator();
        int i = 0;
        while (iteratorEquipos.hasNext()) {
            PartidosEntity jp = (PartidosEntity) iteratorEquipos.next();
            hashMap.put(jp.getIdPartido(), jp);
            i++;
        }
        System.out.println(hashMap.size());
        return hashMap;

    }
}
