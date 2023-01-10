package es.cipfpbatoi.modelo;

public enum Rating {
	G("G"), PG("PG"), PG13("PG-13"), R("R"), NC17("NC-17");
	
	private String nombre;

	Rating(String nombre) {
		this.nombre = nombre;
	}
	
	public String toString() {
		return nombre;
	}
	
}
