package projetSOA.Projet.models;



public class Order {
	    private int id;
	    private int userId;
	    private int eventId;
	    private int equipmentId;
	    private int quantity;
	    private double totalPrice;

	    public Order() {}

	    public Order(int userId, int eventId, int equipmentId, int quantity, double totalPrice) {
	        this.userId = userId;
	        this.eventId = eventId;
	        this.equipmentId = equipmentId;
	        this.quantity = quantity;
	        this.totalPrice = totalPrice;
	    }

	    public int getId() {
	        return id;
	    }

	    public void setId(int id) {
	        this.id = id;
	    }

	    public int getUserId() {
	        return userId;
	    }

	    public void setUserId(int userId) {
	        this.userId = userId;
	    }

	    public int getEventId() {
	        return eventId;
	    }

	    public void setEventId(int eventId) {
	        this.eventId = eventId;
	    }

	    public int getEquipmentId() {
	        return equipmentId;
	    }

	    public void setEquipmentId(int equipmentId) {
	        this.equipmentId = equipmentId;
	    }

	    public int getQuantity() {
	        return quantity;
	    }

	    public void setQuantity(int quantity) {
	        this.quantity = quantity;
	    }

	    public double getTotalPrice() {
	        return totalPrice;
	    }

	    public void setTotalPrice(double totalPrice) {
	        this.totalPrice = totalPrice;
	    }
}
