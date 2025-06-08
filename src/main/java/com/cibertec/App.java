package com.cibertec;

import java.util.Scanner;

import com.cibertec.model.Cliente;
import com.cibertec.model.Pelicula;
import com.cibertec.util.JPAUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import org.h2.tools.Server;

public class App {

    private static Server h2Server;

    private static Server iniciarServidorH2() {
        try {
            Server webServer = Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082").start();
            System.out.println("H2 Web Console disponible en: http://localhost:8082");
            System.out.println("JDBC URL: jdbc:h2:mem:peliculasDB");
            System.out.println("Usuario: sa | Contraseña: (vacía)");
            return webServer;
        } catch (java.sql.SQLException e) {
            System.err.println("Error al iniciar H2 Console");
            e.printStackTrace();
            return null;
        }
    }

    private static void pausar(Scanner scanner) {
        System.out.print("Presiona ENTER para continuar... y crear ejemplo ");
        scanner.nextLine();
    }

    private static void stopH2Server() {
        if (h2Server != null) {
            h2Server.stop();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            h2Server = iniciarServidorH2();

            EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
            EntityTransaction tx = em.getTransaction();

            // CREATE
            tx.begin();
            Cliente cliente = new Cliente("Juan Perez", "juan@example.com");
            Pelicula pelicula = new Pelicula("Matrix", "Ciencia Ficción", 10); // ojo con el constructor
            em.persist(cliente);
            em.persist(pelicula);
            tx.commit();
            System.out.println("Cliente y Película creados.");
            pausar(scanner);

            // READ
            Cliente cLeido = em.find(Cliente.class, cliente.getId());
            Pelicula pLeida = em.find(Pelicula.class, pelicula.getIdPelicula());
            System.out.println("Cliente leído: " + cLeido);
            System.out.println("Película leída: " + pLeida);
            pausar(scanner);

            

          
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JPAUtil.shutdown();
            stopH2Server();
            System.out.println(">>> APLICACIÓN FINALIZADA <<<");
            scanner.close();
        }
    }
}
