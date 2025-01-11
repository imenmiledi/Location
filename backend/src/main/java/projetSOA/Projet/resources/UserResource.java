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
import projetSOA.Projet.models.User;
@Path("users")
public class UserResource {

    // Obtenir tous les utilisateurs
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllUsers() {
	    try (Connection connection = new DBConnection().getConnection()) {
	        String query = "SELECT * FROM users";
	        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
	            List<User> userList = new ArrayList<>();
	            while (rs.next()) {
	                User user = new User();
	                user.setId(rs.getInt("id"));
	                user.setUsername(rs.getString("username"));
	                user.setPassword(rs.getString("password"));
	                user.setEmail(rs.getString("email"));
	                user.setFirstName(rs.getString("first_name"));
	                user.setLastName(rs.getString("last_name"));
	                user.setPhoneNumber(rs.getInt("phone_number"));
	                user.setAddress(rs.getString("address"));
	                userList.add(user);
	            }
	            return Response.status(Response.Status.OK).entity(userList).build();
	        }
	    } catch (SQLException e) {
	        // Loggez l'exception pour plus de détails
	        e.printStackTrace();
	        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error fetching users: " + e.getMessage()).build();
	    }
	}


    // Obtenir un utilisateur par ID
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserById(@PathParam("id") int id) {
        try (Connection connection = new DBConnection().getConnection()) {
            String query = "SELECT * FROM users WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setEmail(rs.getString("email"));
                    user.setFirstName(rs.getString("first_name"));
                    user.setLastName(rs.getString("last_name"));
                    user.setPhoneNumber(rs.getInt("phone_number"));
                    user.setAddress(rs.getString("address"));
                    return Response.status(Response.Status.OK).entity(user).build();
                } else {
                    return Response.status(Response.Status.NOT_FOUND).entity("User not found").build();
                }
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error fetching user").build();
        }
    }
 // Créer un utilisateur
    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(User user) {
        try (Connection connection = new DBConnection().getConnection()) {
            String query = "INSERT INTO users (username, password, email, first_name, last_name, phone_number, address) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, user.getUsername());
                stmt.setString(2, user.getPassword());
                stmt.setString(3, user.getEmail());
                stmt.setString(4, user.getFirstName());
                stmt.setString(5, user.getLastName());
                stmt.setInt(6, user.getPhoneNumber());
                stmt.setString(7, user.getAddress());
                stmt.executeUpdate();
                return Response.status(Response.Status.CREATED).entity(user).build();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error creating user").build();
        }
    }

    // Mettre à jour un utilisateur
    @PUT
    @Path("/update/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(@PathParam("id") int id, User user) {
        try (Connection connection = new DBConnection().getConnection()) {
            String query = "UPDATE users SET username = ?, password = ?, email = ?, first_name = ?, last_name = ?, phone_number = ?, address = ? WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, user.getUsername());
                stmt.setString(2, user.getPassword());
                stmt.setString(3, user.getEmail());
                stmt.setString(4, user.getFirstName());
                stmt.setString(5, user.getLastName());
                stmt.setInt(6, user.getPhoneNumber());
                stmt.setString(7, user.getAddress());
                stmt.setInt(8, id);
                int rowsUpdated = stmt.executeUpdate();
                if (rowsUpdated > 0) {
                    return Response.status(Response.Status.OK).entity(user).build();
                } else {
                    return Response.status(Response.Status.NOT_FOUND).entity("User not found").build();
                }
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error updating user").build();
        }
    }

    // Supprimer un utilisateur
    @DELETE
    @Path("/delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser(@PathParam("id") int id) {
        try (Connection connection = new DBConnection().getConnection()) {
            String query = "DELETE FROM users WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, id);
                int rowsDeleted = stmt.executeUpdate();
                if (rowsDeleted > 0) {
                    return Response.status(Response.Status.NO_CONTENT).build();
                } else {
                    return Response.status(Response.Status.NOT_FOUND).entity("User not found").build();
                }
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error deleting user").build();
        }
    }
    
    @GET
    @Path("/search/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchUsersByUsername(@PathParam("username") String username) {
        try (Connection connection = new DBConnection().getConnection()) {
            String query = "SELECT * FROM users WHERE username LIKE ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, "%" + username + "%");
                ResultSet rs = stmt.executeQuery();
                List<User> userList = new ArrayList<>();
                while (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setEmail(rs.getString("email"));
                    user.setFirstName(rs.getString("first_name"));
                    user.setLastName(rs.getString("last_name"));
                    user.setPhoneNumber(rs.getInt("phone_number"));
                    user.setAddress(rs.getString("address"));
                    userList.add(user);
                }
                if (userList.isEmpty()) {
                    return Response.status(Response.Status.NOT_FOUND).entity("No users found").build();
                }
                return Response.status(Response.Status.OK).entity(userList).build();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error searching users").build();
        }
    }
    
    @GET
    @Path("/count")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getUserCount() {
        try (Connection connection = new DBConnection().getConnection()) {
            String query = "SELECT COUNT(*) AS user_count FROM users";
            try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
                if (rs.next()) {
                    int count = rs.getInt("user_count");
                    return Response.status(Response.Status.OK).entity("Total users: " + count).build();
                } else {
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error counting users").build();
                }
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error counting users").build();
        }
    }
}