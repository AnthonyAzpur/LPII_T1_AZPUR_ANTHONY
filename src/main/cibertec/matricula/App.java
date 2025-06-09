package com.cibertec.matricula;

import java.util.Scanner;

import com.cibertec.matricula.util.JPAUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import org.h2.tools.Server;

import com.cibertec.matricula.model.Curso;
import com.cibertec.matricula.model.Estudiante;
import com.cibertec.matricula.model.Matricula;

/**
 * Hello world!
 */
public class App {
    private static Server h2Server;

private static Server iniciarServidorH2() {
        try {
            Server webServer = Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082").start();
            System.out.println(" H2 Web Console disponible en: http://localhost:8082");
            System.out.println("Conéctate con:");
            System.out.println("JDBC URL: jdbc:h2:mem:matriculaLabDB");
            System.out.println("Usuario: sa");
            System.out.println("Contraseña: (dejar vacío)");
            return webServer;
        } catch (java.sql.SQLException e) {
            System.err.println("Error al iniciar el servidor H2");
            e.printStackTrace();
            return null;
        }
    }

    private static void pausar(Scanner scanner) {
        System.out.print("Presiona ENTER para continuar...");
        scanner.nextLine();
    }

    private static void stopH2Server() {
        if (h2Server != null) {
            h2Server.stop();
        }
    }

    public static void main(String[] args) {
        try {
            // 1. Iniciar el servidor H2 para poder acceder a la consola web
            Server webServer = iniciarServidorH2();
            Scanner scanner = new Scanner(System.in);

EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
    EntityTransaction tx = em.getTransaction();
    
    tx.begin();
    
    // 1. Creamos entidades
    Estudiante estudianteAna = new Estudiante("Ana", "Torres");
    Curso cursoCalculo = new Curso("Cálculo I");
    Curso cursoFisica = new Curso("Física I");

    // Las guardamos para que tengan un ID
    em.persist(estudianteAna);
    em.persist(cursoCalculo);
    em.persist(cursoFisica);

    // 2. Creamos las matrículas (la relación)
    Matricula matricula1 = new Matricula(estudianteAna, cursoCalculo);
    Matricula matricula2 = new Matricula(estudianteAna, cursoFisica);
    
    em.persist(matricula1);
    em.persist(matricula2);
    
    tx.commit();
    System.out.println("Estudiante Ana matriculada en Cálculo y Física.");

    // PAUSA PARA VER LA BD
    pausar(scanner);
    
    // 3. Consulta con JPQL (Java Persistence Query Language)
    // Teoría: JPQL es como SQL, pero en lugar de operar sobre tablas, opera sobre
    // nuestras Entidades (objetos Java). Es más seguro y orientado a objetos.
    // "SELECT c FROM Curso c JOIN c.matriculas m WHERE m.estudiante.id = :estId"
    // Traducción: "Selecciona los Cursos 'c' donde la matrícula 'm' de ese curso
    // corresponda al ID de estudiante que te voy a pasar".
    
    System.out.println("\n--- Buscando los cursos de " + estudianteAna.getNombre() + " usando JPQL ---");
    String jpql = "SELECT m.curso FROM Matricula m WHERE m.estudiante.id = :idEstudiante";
    
    java.util.List<Curso> cursosDeAna = em.createQuery(jpql, Curso.class)
        .setParameter("idEstudiante", estudianteAna.getId())
        .getResultList();

    System.out.println("Cursos encontrados:");
    cursosDeAna.forEach(curso -> System.out.println("- " + curso.getNombre()));

    em.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Cierra la conexión a la BD y el servidor H2
            JPAUtil.shutdown();
            stopH2Server();
            System.out.println("\n>>> APLICACIÓN FINALIZADA <<<");
        }
    }
}