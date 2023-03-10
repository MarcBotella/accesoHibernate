package pojos;
// Generated 10 ene 2023 11:23:34 by Hibernate Tools 6.1.5.Final

import java.sql.Timestamp;

/**
 * Rental generated by hbm2java
 */
public class Rental implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	
	private Integer rentalId;
	private Inventory inventory;
	private Timestamp rentalDate;
	private short customerId;
	private Timestamp returnDate;
	private byte staffId;
	private Timestamp lastUpdate;

	public Rental() {
	}

	public Rental(Inventory inventory, Timestamp rentalDate, short customerId, byte staffId, Timestamp lastUpdate) {
		this.inventory = inventory;
		this.rentalDate = rentalDate;
		this.customerId = customerId;
		this.staffId = staffId;
		this.lastUpdate = lastUpdate;
	}

	public Rental(Inventory inventory, Timestamp rentalDate, short customerId, Timestamp returnDate, byte staffId,
			Timestamp lastUpdate) {
		this.inventory = inventory;
		this.rentalDate = rentalDate;
		this.customerId = customerId;
		this.returnDate = returnDate;
		this.staffId = staffId;
		this.lastUpdate = lastUpdate;
	}

	public Integer getRentalId() {
		return this.rentalId;
	}

	public void setRentalId(Integer rentalId) {
		this.rentalId = rentalId;
	}

	public Inventory getInventory() {
		return this.inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public Timestamp getRentalDate() {
		return this.rentalDate;
	}

	public void setRentalDate(Timestamp rentalDate) {
		this.rentalDate = rentalDate;
	}

	public short getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(short customerId) {
		this.customerId = customerId;
	}

	public Timestamp getReturnDate() {
		return this.returnDate;
	}

	public void setReturnDate(Timestamp returnDate) {
		this.returnDate = returnDate;
	}

	public byte getStaffId() {
		return this.staffId;
	}

	public void setStaffId(byte staffId) {
		this.staffId = staffId;
	}

	public Timestamp getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

}
