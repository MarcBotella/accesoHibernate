package es.cipfpbatoi.modelo;

public class Inventory {
	private int inventory_id;
	private Film film;
	private Store store;

	public Inventory() {
	}

	public Inventory(Film film, Store store) {
		super();
		this.film = film;
		this.store = store;
	}
	
	public Inventory(int inventory_id, Film film, Store store) {
		super();
		this.inventory_id = inventory_id;
		this.film = film;
		this.store = store;
	}

	public int getInventory_id() {
		return inventory_id;
	}

	public void setInventory_id(int inventory_id) {
		this.inventory_id = inventory_id;
	}

	public Film getFilm() {
		return film;
	}

	public void setFilm(Film film) {
		this.film = film;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	@Override
	public String toString() {
		return "Inventory [inventory_id=" + inventory_id + ", film=" + film.getFilm_id() + " " + film.getTitle() + ", store=" + store + "]";
	}

	

}
