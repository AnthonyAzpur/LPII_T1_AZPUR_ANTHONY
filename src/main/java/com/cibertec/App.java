package com.cibertec;

import com.cibertec.model.*;
import com.cibertec.util.JPAUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            // Insertar datos de prueba si no existen
            tx.begin();

            long countClientes = em.createQuery("SELECT COUNT(c) FROM Cliente c", Long.class).getSingleResult();
            if (countClientes == 0) {
                em.persist(new Cliente("Juan Pérez", "juan@example.com"));
                em.persist(new Cliente("Lucía Gómez", "lucia@example.com"));
                em.persist(new Cliente("Carlos Ramírez", "carlos@example.com"));
            }

            long countPeliculas = em.createQuery("SELECT COUNT(p) FROM Pelicula p", Long.class).getSingleResult();
            if (countPeliculas == 0) {
                em.persist(new Pelicula("Matrix", "Ciencia Ficción", 10));
                em.persist(new Pelicula("Titanic", "Romance", 8));
                em.persist(new Pelicula("Avengers", "Acción", 12));
            }

            tx.commit();

            // Listar clientes
            List<Cliente> clientes = em.createQuery("FROM Cliente", Cliente.class).getResultList();
            System.out.println("Clientes disponibles:");
            for (int i = 0; i < clientes.size(); i++) {
                System.out.println((i + 1) + ". " + clientes.get(i).getNombre());
            }
            System.out.print("Seleccione un cliente (número): ");
            int idxCliente = Integer.parseInt(scanner.nextLine()) - 1;
            if (idxCliente < 0 || idxCliente >= clientes.size()) {
                System.out.println("Cliente inválido.");
                return;
            }
            Cliente clienteSeleccionado = clientes.get(idxCliente);

            // Listar películas
            List<Pelicula> peliculas = em.createQuery("FROM Pelicula", Pelicula.class).getResultList();
            System.out.println("\nPelículas disponibles:");
            for (int i = 0; i < peliculas.size(); i++) {
                Pelicula p = peliculas.get(i);
                System.out.printf("%d. %s (Género: %s) - Stock: %d\n", i + 1, p.getTitulo(), p.getGenero(), p.getStock());
            }
            System.out.print("Seleccione una película (número): ");
            int idxPelicula = Integer.parseInt(scanner.nextLine()) - 1;
            if (idxPelicula < 0 || idxPelicula >= peliculas.size()) {
                System.out.println("Película inválida.");
                return;
            }
            Pelicula peliculaSeleccionada = peliculas.get(idxPelicula);

            // Pedir cantidad
            System.out.print("\nIngrese cantidad a alquilar: ");
            int cantidad = Integer.parseInt(scanner.nextLine());

            if (cantidad <= 0) {
                System.out.println("Cantidad debe ser mayor a 0.");
                return;
            }

            if (cantidad > peliculaSeleccionada.getStock()) {
                System.out.println("Stock insuficiente. Stock actual: " + peliculaSeleccionada.getStock());
                return;
            }

            // Calcular total películas alquiladas 
            int totalPeliculas = cantidad;
            System.out.println("Total de películas a alquilar: " + totalPeliculas);

            // Registrar alquiler y detalle
            tx.begin();

            Alquiler alquiler = new Alquiler();
            alquiler.setCliente(clienteSeleccionado);
            alquiler.setFecha(LocalDate.now());
            alquiler.setEstado(EstadoAlquiler.ACTIVO);
            alquiler.setTotal(totalPeliculas);
            em.persist(alquiler);

            em.flush();  // Importante: forzar que se genere el ID del alquiler antes de crear el detalle

            DetalleAlquiler detalle = new DetalleAlquiler();
            detalle.setAlquiler(alquiler);
            detalle.setPelicula(peliculaSeleccionada);
            detalle.setCantidad(cantidad);
            // Asignar la clave compuesta
            detalle.setId(new DetalleAlquilerId(alquiler.getId(), peliculaSeleccionada.getIdPelicula()));
            em.persist(detalle);

            // Actualizar stock
            peliculaSeleccionada.setStock(peliculaSeleccionada.getStock() - cantidad);
            em.merge(peliculaSeleccionada);

            tx.commit();

            System.out.println("\nAlquiler registrado correctamente para " + clienteSeleccionado.getNombre());
            System.out.println("Película: " + peliculaSeleccionada.getTitulo());
            System.out.println("Cantidad: " + cantidad);
            System.out.println("Stock restante: " + peliculaSeleccionada.getStock());

        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
            System.out.println("Error al registrar el alquiler.");
        } finally {
            em.close();
            JPAUtil.shutdown();
            scanner.close();
            System.out.println("\nAplicación finalizada.");
        }
    }
}
