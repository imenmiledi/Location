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
import projetSOA.Projet.models.Event;

@Path("events")
public class EventResource {


    // Obtenir tous les événements
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllEvents() {
        try (Connection connection = new DBConnection().getConnection()) {
            String query = "SELECT * FROM events";
            try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
                List<Event> events = new ArrayList<>();
                while (rs.next()) {
                    Event event = new Event();
                    event.setId(rs.getInt("id"));
                    event.setName(rs.getString("name"));
                    event.setDescription(rs.getString("description"));
                    event.setPrice(rs.getDouble("price"));
                    event.setLocation(rs.getString("location"));
                    event.setDate(rs.getDate("date").toLocalDate());  // Convert java.sql.Date to LocalDate
                    event.setTheme(rs.getString("theme"));
                    events.add(event);
                }
                return Response.status(Response.Status.OK).entity(events).build();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error fetching events").build();
        }
    }

    // Obtenir un événement par ID
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEventById(@PathParam("id") int id) {
        try (Connection connection = new DBConnection().getConnection()) {
            String query = "SELECT * FROM events WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    Event event = new Event();
                    event.setId(rs.getInt("id"));
                    event.setName(rs.getString("name"));
                    event.setDescription(rs.getString("description"));
                    event.setPrice(rs.getDouble("price"));
                    event.setLocation(rs.getString("location"));
                    event.setDate(rs.getDate("date").toLocalDate());  // Convert java.sql.Date to LocalDate
                    event.setTheme(rs.getString("theme"));
                    return Response.status(Response.Status.OK).entity(event).build();
                } else {
                    return Response.status(Response.Status.NOT_FOUND).entity("Event not found").build();
                }
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error fetching event").build();
        }
    }

    // Créer un événement
    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createEvent(Event event) {
        try (Connection connection = new DBConnection().getConnection()) {
            String query = "INSERT INTO events (name, description, price, location, date, theme) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, event.getName());
                stmt.setString(2, event.getDescription());
                stmt.setDouble(3, event.getPrice());
                stmt.setString(4, event.getLocation());
                stmt.setDate(5, java.sql.Date.valueOf(event.getDate()));  // Convert LocalDate to java.sql.Date
                stmt.setString(6, event.getTheme());
                stmt.executeUpdate();
                return Response.status(Response.Status.CREATED).entity(event).build();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error creating event").build();
        }
    }
    // Mettre à jour un événement
    @PUT
    @Path("/update/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateEvent(@PathParam("id") int id, Event event) {
        try (Connection connection = new DBConnection().getConnection()) {
            String query = "UPDATE events SET name = ?, description = ?, price = ?, location = ?, date = ?, theme = ? WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, event.getName());
                stmt.setString(2, event.getDescription());
                stmt.setDouble(3, event.getPrice());
                stmt.setString(4, event.getLocation());
                stmt.setDate(5, java.sql.Date.valueOf(event.getDate()));  // Convert LocalDate to java.sql.Date
                stmt.setString(6, event.getTheme());
                stmt.setInt(7, id);
                int rowsUpdated = stmt.executeUpdate();
                if (rowsUpdated > 0) {
                    return Response.status(Response.Status.OK).entity(event).build();
                } else {
                    return Response.status(Response.Status.NOT_FOUND).entity("Event not found").build();
                }
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error updating event").build();
        }
    }

    // Supprimer un événement
    @DELETE
    @Path("/delete/{id}")
    public Response deleteEvent(@PathParam("id") int id) {
        try (Connection connection = new DBConnection().getConnection()) {
            String query = "DELETE FROM events WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, id);
                int rowsDeleted = stmt.executeUpdate();
                if (rowsDeleted > 0) {
                    return Response.status(Response.Status.NO_CONTENT).build();
                } else {
                    return Response.status(Response.Status.NOT_FOUND).entity("Event not found").build();
                }
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error deleting event").build();
        }
    }
}