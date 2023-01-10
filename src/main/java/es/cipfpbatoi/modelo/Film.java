package es.cipfpbatoi.modelo;

import java.util.HashSet;
import java.util.Set;

public class Film {
	private int film_id;
	private String title;
	private int language_id;
	private byte rental_duration;
	private float rental_rate;
	private short length;
	private float replacement_cost;
	private Rating rating; 
	private String special_features;
	private Set<Category> categories;
	// private boolean[] available;		// disponibilidad en cada almacén (posición 0 = almacén 1, posición 1 = almacén 2, ...)

	public Film() {
	}

	public Film(String title, int language_id, byte rental_duration, float rental_rate, short length, 
			float replacement_cost, Rating rating, String special_features) {
		super();
		this.title = title;
		this.language_id = language_id;
		this.rental_duration = rental_duration;
		this.rental_rate = rental_rate;
		this.length = length;
		this.replacement_cost = replacement_cost;
		this.rating = rating;
		this.special_features = special_features;
	}
	
	public Film(int film_id, String title, int language_id, byte rental_duration, float rental_rate, short length,
			float replacement_cost, Rating rating, String special_features) {
		super();
		this.film_id = film_id;
		this.title = title;
		this.language_id = language_id;
		this.rental_duration = rental_duration;
		this.rental_rate = rental_rate;
		this.length = length;
		this.replacement_cost = replacement_cost;
		this.rating = rating;
		this.special_features = special_features;
	}

	public int getFilm_id() {
		return film_id;
	}

	public void setFilm_id(int film_id) {
		this.film_id = film_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getLanguage_id() {
		return language_id;
	}

	public void setLanguage_id(int language_id) {
		this.language_id = language_id;
	}

	public byte getRental_duration() {
		return rental_duration;
	}

	public void setRental_duration(byte rental_duration) {
		this.rental_duration = rental_duration;
	}

	public float getRental_rate() {
		return rental_rate;
	}

	public void setRental_rate(float rental_rate) {
		this.rental_rate = rental_rate;
	}

	public short getLength() {
		return length;
	}

	public void setLength(short length) {
		this.length = length;
	}

	public float getReplacement_cost() {
		return replacement_cost;
	}

	public void setReplacement_cost(float replacement_cost) {
		this.replacement_cost = replacement_cost;
	}

	public Rating getRating() {
		return rating;
	}

	public void setRating(Rating rating) {
		this.rating = rating;
	}

	public String getSpecial_features() {
		return special_features;
	}

	public void setSpecial_features(String special_features) {
		this.special_features = special_features;
	}

	public Set<Category> getCategories() {
		return categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}
	
	public void addCategory(Category categoria) {
		if (this.categories==null) {
			this.categories = new HashSet<Category>();
		}
		this.categories.add(categoria);
	}
	
	public void deleteCategory(Category categoria) {
		if (this.categories!=null) {
			this.categories.remove(categoria);
		}
	}

//	public boolean[] getAvailable() {
//		return available;
//	}
//
//	public void setAvailable(boolean[] available) {
//		this.available = available;
//	}

	@Override
	public String toString() {
		String cad = "Film [film_id=" + film_id + ", title=" + title + ", language_id=" + language_id + ", rental_duration="
				+ rental_duration + ", rental_rate=" + rental_rate + ", length=" + length + ", replacement_cost="
				+ replacement_cost + ", rating=" + rating + ", special_features=" + special_features;
		cad = cad + ", categories=";
		for(Category cat: categories) {
			cad = cad + cat.getName() + " ";
		}
		cad = cad + "]";
		return cad;
	}

	
	
	
	

}
