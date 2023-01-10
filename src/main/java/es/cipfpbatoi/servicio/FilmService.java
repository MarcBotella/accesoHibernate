package es.cipfpbatoi.servicio;

import es.cipfpbatoi.dao.*;
import es.cipfpbatoi.modelo.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FilmService {

    private final FilmDAO filmDAO;
    private final CategoryDAO categoryDAO;
    private final StoreDAO storeDAO;
    private final InventoryDAO inventoryDAO;
    private final RentalDAO rentalDAO;

    public FilmService() throws SQLException {
        filmDAO = new FilmDAO();
        categoryDAO = new CategoryDAO();
        storeDAO = new StoreDAO();
        inventoryDAO = new InventoryDAO();
        rentalDAO = new RentalDAO();
    }

    public void cerrar() throws SQLException {
        filmDAO.cerrar();
        categoryDAO.cerrar();
        storeDAO.cerrar();
        inventoryDAO.cerrar();
        rentalDAO.cerrar();
    }



    public void altaInventarioPelicula(Film peli, Store almacen) throws  Exception{
        if (filmDAO.find(peli.getFilm_id()) != null){
            inventoryDAO.insertarPeliculaInventario(peli,almacen);
        }else {
            boolean seaInsertado = filmDAO.insert(peli);
            if (seaInsertado){
                inventoryDAO.insertarPeliculaInventario(peli,almacen);
            }
        }
    }

    public void modificaPelicula(Film peli) throws Exception {
        Film film = filmDAO.find(peli.getFilm_id());
        if (film != null){
            filmDAO.update(film);
        }
    }

    public void anyadeCategoria(Film peli, Category cat) throws Exception{
        if (peli != null){
            if (categoryDAO.find(cat.getCategory_id())!= null){
                peli.addCategory(cat);
                boolean noHayAsiciacion = categoryDAO.anyadirCategoriaAPelicula(peli, cat);
                if (noHayAsiciacion){
                    throw new Exception("Error al asociar la categoria a la pelicula, porque ya la tiene");
                }
            }else {
                throw new Exception("Error, la categoria no existe");
            }
        }else {
            throw new Exception("Error, la pelicula no existe");
        }

    }

    public void eliminaCartegoria(Film peli, Category cat)throws Exception{
        if (filmDAO.find(peli.getFilm_id()) != null){
            if (categoryDAO.find(cat.getCategory_id())!= null){
                boolean seaEliminado = categoryDAO.deleteCategory(peli,cat);
                if (!seaEliminado){
                    throw new Exception("Error al eliminar la categoria de la pelicula");
                }
            }else {
                throw new Exception("Error, la categoria no existe");
            }
        }else {
            throw new Exception("Error, la pelicula no existe");
        }
    }

    public boolean compruebaDisponibilidad(Film peli, Store almacen) {
        try {
            Film pelicula = filmDAO.find(peli.getFilm_id());
            if (pelicula != null){
                return filmDAO.estaDisponible(peli,almacen);
            }
            else {
                return false;
            }
        }catch (SQLException s){
            s.printStackTrace();
        }
        return false;
    }

    public Rental alquilaPelicula(Film peli, Store almacen) throws Exception {
        if (compruebaDisponibilidad(peli,almacen)) {
            Inventory inventory = inventoryDAO.insert(peli,almacen);
            if (inventory != null) {
                Rental rental = new Rental(LocalDateTime.now(),inventory,1,null,1);
                if (rentalDAO.insert(rental)) {
                    return rental;
                }
                throw new Exception("No se ha podido alquilar la pelicula debido a algún error");
            }
            throw new Exception("No se ha podido insertar el inventario");
        }
        throw new Exception("No se puede alquilar la pelicula ya que esta no esta disponible en el almacen");
    }

    public float devuelvePelicula(Rental alquiler) throws Exception {
        int diasMaximoAlquilados = alquiler.getInventory().getFilm().getRental_duration();
        int diasAlquilados = alquiler.getReturn_date().compareTo(alquiler.getRental_date());
        float coste = alquiler.getInventory().getFilm().getRental_rate();
        float costeTotal;

        if (diasMaximoAlquilados >= diasAlquilados) {
            costeTotal = diasAlquilados * coste;
        } else {
            int diff = diasAlquilados-diasMaximoAlquilados;
            costeTotal = diasMaximoAlquilados * coste + diff * coste * 1.1f;
        }
        if (!rentalDAO.update(alquiler)) {
            throw new Exception("No se ha podido devolver la pelicula");
        }
        return costeTotal;
    }

    public List<Category> dameCategorias() throws Exception{
        List<Category> categories = categoryDAO.findAll();
        if (categories != null){
            return categories;
        }else {
            throw new Exception("Error al cargar las categorias");
        }
    }

    public List<Store> dameAlmacenes() throws Exception{
        List<Store> stores = storeDAO.findAll();
        if (stores != null){
            return stores;
        }else {
            throw new Exception("Error al cargar los almacenes");
        }
    }

    public List<Film> buscaPeliculasPorCategoria(Category category)throws Exception{

        if (categoryDAO.find(category.getCategory_id())!=null){
            List<Film> listaPeliculas = filmDAO.findFilmByCategory(category);
            if (listaPeliculas.isEmpty()){
                throw new Exception("Error al cargar las peliculas por una categoria");
            }else {
                return listaPeliculas;
            }
        }else {
            throw new Exception("Error, la categoria no existe");
        }

    }

    public void refrescaPelicula(Film peli) throws Exception{
        Film film = filmDAO.find(peli.getFilm_id());

        if (film != null){
            filmDAO.update(film);
        }else {
            throw new Exception("Error al refrescar la pelicula");
        }
    }
	

}
