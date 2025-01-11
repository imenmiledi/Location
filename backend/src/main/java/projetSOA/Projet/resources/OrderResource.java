package projetSOA.Projet.resources;





import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import projetSOA.Projet.db.DBConnection;
import projetSOA.Projet.models.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Path("orders")
public class OrderResource {

   

    // Obtenir toutes les commandes
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllOrders() {
        try (Connection connection = new DBConnection().getConnection()) {
            String query = "SELECT * FROM orders";
            try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
                List<Order> orders = new ArrayList<>();
                while (rs.next()) {
                    Order order = new Order();
                    order.setId(rs.getInt("id"));
                    order.setUserId(rs.getInt("user_id"));
                    order.setEventId(rs.getInt("event_id"));
                    order.setEquipmentId(rs.getInt("equipment_id"));
                    order.setQuantity(rs.getInt("quantity"));
                    order.setTotalPrice(rs.getDouble("total_price"));
                    orders.add(order);
                }
                return Response.status(Response.Status.OK).entity(orders).build();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error fetching orders").build();
        }
    }

    // Obtenir une commande par ID
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrderById(@PathParam("id") int id) {
        try (Connection connection = new DBConnection().getConnection()) {
            String query = "SELECT * FROM orders WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    Order order = new Order();
                    order.setId(rs.getInt("id"));
                    order.setUserId(rs.getInt("user_id"));
                    order.setEventId(rs.getInt("event_id"));
                    order.setEquipmentId(rs.getInt("equipment_id"));
                    order.setQuantity(rs.getInt("quantity"));
                    order.setTotalPrice(rs.getDouble("total_price"));
                    return Response.status(Response.Status.OK).entity(order).build();
                } else {
                    return Response.status(Response.Status.NOT_FOUND).entity("Order not found").build();
                }
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error fetching order").build();
        }
    }
    // Créer une commande
    @POST
    @Path("create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createOrder(Order order) {
        try (Connection connection = new DBConnection().getConnection()) {
            String query = "INSERT INTO orders (user_id, event_id, equipment_id, quantity, total_price) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, order.getUserId());
                stmt.setInt(2, order.getEventId());
                stmt.setInt(3, order.getEquipmentId());
                stmt.setInt(4, order.getQuantity());
                stmt.setDouble(5, order.getTotalPrice());
                stmt.executeUpdate();
                return Response.status(Response.Status.CREATED).entity(order).build();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error creating order").build();
        }
    }
    // Mettre à jour une commande
    @PUT
    @Path("/update/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateOrder(@PathParam("id") int id, Order order) {
        try (Connection connection = new DBConnection().getConnection()) {
            String query = "UPDATE orders SET user_id = ?, event_id = ?, equipment_id = ?, quantity = ?, total_price = ? WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, order.getUserId());
                stmt.setInt(2, order.getEventId());
                stmt.setInt(3, order.getEquipmentId());
                stmt.setInt(4, order.getQuantity());
                stmt.setDouble(5, order.getTotalPrice());
                stmt.setInt(6, id);
                int rowsUpdated = stmt.executeUpdate();
                if (rowsUpdated > 0) {
                    return Response.status(Response.Status.OK).entity(order).build();
                } else {
                    return Response.status(Response.Status.NOT_FOUND).entity("Order not found").build();
                }
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error updating order").build();
        }
    }

    // Supprimer une commande
    @DELETE
    @Path("/delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteOrder(@PathParam("id") int id) {
        try (Connection connection = new DBConnection().getConnection()) {
            String query = "DELETE FROM orders WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, id);
                int rowsDeleted = stmt.executeUpdate();
                if (rowsDeleted > 0) {
                    return Response.status(Response.Status.NO_CONTENT).build();
                } else {
                    return Response.status(Response.Status.NOT_FOUND).entity("Order not found").build();
                }
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error deleting order").build();
        }
    }
}