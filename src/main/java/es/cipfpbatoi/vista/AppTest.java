package es.cipfpbatoi.vista;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import es.cipfpbatoi.modelo.Category;
import es.cipfpbatoi.modelo.Film;
import es.cipfpbatoi.modelo.Rating;
import es.cipfpbatoi.modelo.Rental;
import es.cipfpbatoi.modelo.Store;
import es.cipfpbatoi.servicio.FilmService;

public class AppTest {
	static FilmService gesPelis;

	public static void main(String[] args) {

		gesPelis = null;
		try {
			gesPelis = new FilmService();
		} catch (SQLException e) {
			System.out.println("Error estableciendo conexión " + e.getMessage());
			System.exit(0);
		}

		// PRIMERA ACCION. Cargar categorías para mostrar al usuario
		System.out.println("\u001B[34m ** PRIMERA ACCION ** \u001B[0m");
		List<Category> categorias = null;
		try {
			categorias = gesPelis.dameCategorias();
			for (Category cat : categorias) {
				System.out.println(cat);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}

		// SEGUNDA ACCIÓN. Suponemos que el usuario elige la categoria 1: Action.
		System.out.println("\u001B[34m ** SEGUNDA ACCION ** \u001B[0m");
		Category cat1 = categorias.get(0);
		List<Film> pelis = null;
		try {
			pelis = gesPelis.buscaPeliculasPorCategoria(cat1);
			for (Film f : pelis) {
				System.out.println(f);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		// TERCERA ACCIÓN. El usuario elige la película 1 (ACADEMY DINOSAUR) y la
		// modifica
		System.out.println("\u001B[34m ** TERCERA ACCION ** \u001B[0m");
		Film peli1 = null;
		if (pelis != null) {
			peli1 = pelis.get(0);

			// Modificación de algunos de sus campos
			peli1.setTitle(peli1.getTitle() + " - m");
			peli1.setReplacement_cost(peli1.getReplacement_cost() * 1.1f); // valor original: 20.99f
			peli1.setRating(Rating.G); // valor original PG
			try {
				gesPelis.modificaPelicula(peli1);
				System.out.println("Pelicula modifica " + peli1);

			} catch (Exception e) {
				System.out.println(e.getMessage());
				try {
					gesPelis.refrescaPelicula(peli1);
				} catch (Exception e1) {
					System.out.println(e1.getMessage());
				}
			}

			// También se le añade una categoría a esta pelicula
			try {
				gesPelis.anyadeCategoria(peli1, cat1);
				System.out.println("Añadida categoria " + cat1 + " a la pelicula " + peli1);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

		// CUARTA ACCIÓN. El usuario decide registrar una nueva película en el primer
		// almacén.
		System.out.println("\u001B[34m ** CUARTA ACCION ** \u001B[0m");
		List<Store> almacenes = null;
		try {
			almacenes = gesPelis.dameAlmacenes();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
		
		Store almacen1 = almacenes.get(0);
		Film peliNueva = new Film("TERROR EN BATOI", 1, (byte) 5, 1.99f, (short) 120, 4.99f, Rating.NC17,
				"Trailers,Deleted Scenes");
		try {
			gesPelis.altaInventarioPelicula(peliNueva, almacen1);
			System.out.println("Pelicula insertada y dada de alta en inventario");

			// QUINTA ACCIÓN. El usuario decide añadir dos categorías (Action y Animation) a
			// esta pelicula
			System.out.println("\u001B[34m ** QUINTA ACCION ** \u001B[0m");
			gesPelis.anyadeCategoria(peliNueva, cat1);
			System.out.println("Añadida categoria " + cat1 + " a la pelicula " + peliNueva);
			gesPelis.anyadeCategoria(peliNueva, categorias.get(1));
			System.out.println("Añadida categoria " + categorias.get(1) + " a la pelicula " + peliNueva);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		// SEXTA ACCIÓN. El usuario decide alquilar la pelicula1 (19 - AMADEUS HOLY) en
		// el almacén 1.
		// Hay disponibilidad
		System.out.println("\u001B[34m ** SEXTA ACCION ** \u001B[0m");
		Rental alquiler = null;
		if (peli1 != null) {
			try {
				System.out.println("Intentando alquilar la peli: " + peli1);
				alquiler = gesPelis.alquilaPelicula(peli1, almacen1);
				System.out.println("Pelicula alquilada con éxito");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

		// SEPTIMA ACCIÓN. El usuario decide alquilar la pelicula 2 (38 - ARK RIDGEMONT)
		// en el almacén 1.
		// No está disponible.
		System.out.println("\u001B[34m ** SEPTIMA ACCION ** \u001B[0m");
		if (pelis != null) {
			try {
				Film peli2 = pelis.get(3);
				System.out.println("Intentado alquilar la peli " + peli2);
				gesPelis.alquilaPelicula(peli2, almacen1);
				System.out.println("Pelicula alquilada con éxito");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

		// OCTAVA ACCIÓN. El usuario devuelve la pelicula 1 tras 8 dias
		// (el máximo que tiene es 6). Rental_rate = 0.99€
		System.out.println("\u001B[34m ** OCTAVA ACCION ** \u001B[0m");
		if (alquiler != null) {
			Float coste;
			System.out.println("Devolución del alquiler: " + alquiler);
			try {
				alquiler.setReturn_date(LocalDateTime.now().plusDays(8));
				coste = gesPelis.devuelvePelicula(alquiler);
				System.out.println("Devolución efectuada con éxito. Coste total alquiler: " + coste + " €");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

		} else {
			System.out.println("No hay alquiler para devolver");
		}

		try {
			gesPelis.cerrar();
		} catch (SQLException e) {
			System.out.println("Error cerrando servicio...");
		}

	}

}
