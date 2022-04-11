package dataAcces;

import connection.ConnectionFactory;
import model.Client;
import model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clasa care modeleaza operatiile pe Clienti
 */
public class ClientDAO extends AbstractDAO<Client>{
    /**
     * Atributele clasei
     */
    protected final Logger LOGGER = Logger.getLogger(ClientDAO.class.getName());
    private final String findStatementString = "SELECT * FROM client WHERE client_name = ?";
    private String get = "SELECT * from client";
    private static final String insertClient = "INSERT INTO client (idclient, client_name,client_address, email)" + "VALUES (?,?,?,?)";

    /**
     * Aceasta metoda face update pe un client
     * @param c Clientul care trebuie editat
     * @return Clientul editat
     */
    @Override
    public Client update(Client c) {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("UPDATE client SET client_name = ? WHERE idclient = ?");
            statement.setString(1, c.getName());
            statement.setInt(2, c.getId());
            statement.executeUpdate();
            statement = connection.prepareStatement("UPDATE client SET client_address = ? WHERE idclient = ?");
            statement.setString(1, c.getAddress());
            statement.setInt(2, c.getId());
            statement.executeUpdate();
            statement = connection.prepareStatement("UPDATE client SET email = ? WHERE idclient = ?");
            statement.setString(1, c.getEmail());
            statement.setInt(2, c.getId());
            statement.executeUpdate();
        }catch (SQLException e) {
            LOGGER.log(Level.WARNING, "PRODUCT_DATABASE_OPERATIONS : EDIT" + e.getMessage());
        }
        ConnectionFactory.close(statement);
        ConnectionFactory.close(connection);
        return c;
    }
    /**
     * Aceasta metoda face delete pe un client
     * @param id Id-ul clientului care trebuie sters
     */
    public void deleteClient(int id) {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("DELETE FROM client WHERE idclient = ?");
            statement.setInt(1, id);
            statement.executeUpdate();
        }catch (SQLException e) {
            LOGGER.log(Level.WARNING,"CLIENT_DATABASE_OPERATIONS:DELETE " + e.getMessage());
        }
        ConnectionFactory.close(statement);
        ConnectionFactory.close(connection);
    }
    /**
     * Aceasta metoda face o lista din toti clientii
     * @return Lista de clienti
     */
    public List<Client> listAllClients(){
        List<Client> clientsList = new ArrayList<Client>();
        Client c = null;
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = null;
        ResultSet result = null;
        try {
            statement = connection.prepareStatement("SELECT * FROM client");
            result = statement.executeQuery();
            while (result.next()) {
                int idClient = result.getInt("idclient");
                String clientName = result.getString("client_name");
                String clientAddress = result.getString("client_address");
                String email = result.getString("email");
                c = new Client(clientName,clientAddress,email,idClient);
                c.setId(idClient);
                c.setEmail(email);
                clientsList.add(c);
            }
        }catch (SQLException e) {
            LOGGER.log(Level.WARNING,"CLIENT_DATABASE_OPERATIONS: ListAll " + e.getMessage());
        }
        ConnectionFactory.close(result);
        ConnectionFactory.close(statement);
        ConnectionFactory.close(connection);
        return clientsList;
    }
    /**
     * Aceasta metoda gaseste un client pe baza numelui sau
     * @param clientName Numele clientului care trebuie gasit
     * @return Clientul gasit
     */
    public Client findByName(String clientName) {
        Client client = null;
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet result = null;
        try {
            findStatement = dbConnection.prepareStatement(findStatementString);
            findStatement.setString(1, clientName);
            result = findStatement.executeQuery();
            result.next();
            String address = result.getString("client_address");
            String email = result.getString("email");
            client = new Client(clientName,address,email,0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,"ClientDAO: findByName " + e.getMessage());
        } finally {
            ConnectionFactory.close(result);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbConnection);
        }
        return client;
    }
}
