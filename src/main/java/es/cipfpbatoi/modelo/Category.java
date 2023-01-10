package es.cipfpbatoi.modelo;

public class Category {
	private int category_id;
	private String name;
	
	public Category(String name) {
		super();
		this.name = name;
	}	
	public Category(int category_id, String name) {
		super();
		this.category_id = category_id;
		this.name = name;
	}	
	
	public int getCategory_id() {
		return category_id;
	}

	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}

	public String getName() {
		return name;
	}
		
	@Override
	public String toString() {
		return "Category [name=" + name + "]";
	}


}
