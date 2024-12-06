package f24c2c1.projektkalkulering.repository;

import f24c2c1.projektkalkulering.model.Client;
import f24c2c1.projektkalkulering.model.ClientImpl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ClientRepository {

    private final JdbcTemplate jdbcTemplate;

    public ClientRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // RowMapper to map SQL results to the Client object
    private static final RowMapper<Client> CLIENT_ROW_MAPPER = new RowMapper<>() {
        @Override
        public Client mapRow(ResultSet rs, int rowNum) throws SQLException {
            ClientImpl client = new ClientImpl();
            client.setId(rs.getLong("id"));
            client.setName(rs.getString("name"));
            client.setContactName(rs.getString("contact_name"));
            client.setAddress(rs.getString("address"));
            client.setCity(rs.getString("city"));
            client.setZip(rs.getString("zip"));
            client.setPhone(rs.getString("phone"));
            client.setEmail(rs.getString("email"));
            return client;
        }
    };

    /**
     * Fetches all clients from the database.
     *
     * @return List of all clients.
     */
    public List<Client> findAll() {
        String sql = "SELECT * FROM clients";
        return jdbcTemplate.query(sql, CLIENT_ROW_MAPPER);
    }

    /**
     * Finds a client by ID.
     *
     * @param id The ID of the client.
     * @return The client, or null if not found.
     */
    public Client findById(long id) {
        String sql = "SELECT * FROM clients WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, CLIENT_ROW_MAPPER, id);
    }

    /**
     * Saves a new client to the database.
     *
     * @param client The client to save.
     * @return The number of rows affected.
     */
    public int save(Client client) {
        String sql = "INSERT INTO clients (name, contact_name, address, city, zip, phone, email) VALUES (?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(
                sql,
                client.getName(),
                client.getContactName(),
                client.getAddress(),
                client.getCity(),
                client.getZip(),
                client.getPhone(),
                client.getEmail()
        );
    }

    /**
     * Updates an existing client in the database.
     *
     * @param client The client to update.
     * @return The number of rows affected.
     */
    public int update(Client client) {
        String sql = "UPDATE clients SET name = ?, contact_name = ?, address = ?, city = ?, zip = ?, phone = ?, email = ? WHERE id = ?";
        return jdbcTemplate.update(
                sql,
                client.getName(),
                client.getContactName(),
                client.getAddress(),
                client.getCity(),
                client.getZip(),
                client.getPhone(),
                client.getEmail(),
                client.getId()
        );
    }

    /**
     * Deletes a client by ID.
     *
     * @param id The ID of the client to delete.
     * @return The number of rows affected.
     */
    public int deleteById(long id) {
        String sql = "DELETE FROM clients WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
