package es.cipfpbatoi.modelo;

import java.time.LocalDateTime;

public class Rental {
	private int rental_id;
	private LocalDateTime rental_date;
	private Inventory inventory;
	private int customer_id;
	private LocalDateTime return_date;
	private int staff_id;

	public Rental() {
	}

	public Rental(LocalDateTime rental_date, Inventory inventory, int customer_id, LocalDateTime return_date, int staff_id) {
		super();
		this.rental_date = rental_date;
		this.inventory = inventory;
		this.customer_id = customer_id;
		this.return_date = return_date;
		this.staff_id = staff_id;
	}

	public int getRental_id() {
		return rental_id;
	}

	public void setRental_id(int rental_id) {
		this.rental_id = rental_id;
	}

	public LocalDateTime getRental_date() {
		return rental_date;
	}

	public void setRental_date(LocalDateTime rental_date) {
		this.rental_date = rental_date;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public int getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}

	public LocalDateTime getReturn_date() {
		return return_date;
	}

	public void setReturn_date(LocalDateTime return_date) {
		this.return_date = return_date;
	}

	public int getStaff_id() {
		return staff_id;
	}

	public void setStaff_id(int staff_id) {
		this.staff_id = staff_id;
	}

	@Override
	public String toString() {
		return "Rental [rental_id=" + rental_id + ", rental_date=" + rental_date + ", inventory_id=" + inventory
				+ ", customer_id=" + customer_id + ", return_date=" + return_date + ", staff_id=" + staff_id + "]";
	}

}
