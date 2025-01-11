package projetSOA.Projet.resources;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
import projetSOA.Projet.models.Equipment;

@Path("equipments")
public class EquipmentResource {

    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllEquipment() {
        try (Connection connection = new DBConnection().getConnection()) {
            String query = "SELECT * FROM equipment";
            try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
                List<Equipment> equipmentList = new ArrayList<>();
                while (rs.next()) {
                    Equipment equipment = new Equipment();
                    equipment.setId(rs.getInt("id"));
                    equipment.setName(rs.getString("name")); 
                    equipment.setDescription(rs.getString("description"));
                    equipment.setPrice(rs.getDouble("price"));
                    equipment.setCategory(rs.getString("category"));
                    equipment.setQuantity(rs.getInt("quantity"));
                    equipmentList.add(equipment);
                }
                return Response.status(Response.Status.OK).entity(equipmentList).build();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error fetching equipment").build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEquipmentById(@PathParam("id") int id) {
        try (Connection connection = new DBConnection().getConnection()) {
            String query = "SELECT * FROM equipment WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    Equipment equipment = new Equipment();
                    equipment.setId(rs.getInt("id"));
                    equipment.setName(rs.getString("name"));  
                    equipment.setDescription(rs.getString("description"));
                    equipment.setPrice(rs.getDouble("price"));
                    equipment.setCategory(rs.getString("category"));
                    equipment.setQuantity(rs.getInt("quantity"));
                    return Response.status(Response.Status.OK).entity(equipment).build();
                } else {
                    return Response.status(Response.Status.NOT_FOUND).entity("Equipment not found").build();
                }
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error fetching equipment").build();
        }
    }
    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createEquipment(Equipment equipment) {
        try (Connection connection = new DBConnection().getConnection()) {
            String query = "INSERT INTO equipment (name, description, price, category, quantity) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, equipment.getName());  
                stmt.setString(2, equipment.getDescription());  
                stmt.setDouble(3, equipment.getPrice());
                stmt.setString(4, equipment.getCategory());  
                stmt.setInt(5, equipment.getQuantity());  
                stmt.executeUpdate();
                return Response.status(Response.Status.CREATED).entity(equipment).build();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error creating equipment").build();
        }
    }


    @PUT
    @Path("/update/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateEquipment(@PathParam("id") int id, Equipment equipment) {
        try (Connection connection = new DBConnection().getConnection()) {
            String query = "UPDATE equipment SET name = ?, description = ?, price = ?, category = ?, quantity = ? WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, equipment.getName());  
                stmt.setString(2, equipment.getDescription());  
                stmt.setDouble(3, equipment.getPrice());
                stmt.setString(4, equipment.getCategory());  
                stmt.setInt(5, equipment.getQuantity());  
                stmt.setInt(6, id);
                int rowsUpdated = stmt.executeUpdate();
                if (rowsUpdated > 0) {
                    return Response.status(Response.Status.OK).entity(equipment).build();
                } else {
                    return Response.status(Response.Status.NOT_FOUND).entity("Equipment not found").build();
                }
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error updating equipment").build();
        }
    }

    @DELETE
    @Path("/delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteEquipment(@PathParam("id") int id) {
        try (Connection connection = new DBConnection().getConnection()) {
            String query = "DELETE FROM equipment WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, id);
                int rowsDeleted = stmt.executeUpdate();
                if (rowsDeleted > 0) {
                    return Response.status(Response.Status.NO_CONTENT).build();
                } else {
                    return Response.status(Response.Status.NOT_FOUND).entity("Equipment not found").build();
                }
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error deleting equipment").build();
        }
    }
}