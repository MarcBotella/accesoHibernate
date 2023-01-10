package es.cipfpbatoi.modelo;

public class Store {
	private int store_id;
	private int manager_staff_id;
	private int address_id;
	
	public Store() {		
	}
	
	public Store(int store_id, int manager_staff_id, int address_id) {
		super();
		this.store_id = store_id;
		this.manager_staff_id = manager_staff_id;
		this.address_id = address_id;
	}

	public int getStore_id() {
		return store_id;
	}

	public int getManager_staff_id() {
		return manager_staff_id;
	}

	public int getAddress_id() {
		return address_id;
	}

	@Override
	public String toString() {
		return "Store [store_id=" + store_id + "]";
	}	
	
}
