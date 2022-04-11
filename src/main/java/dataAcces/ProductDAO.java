package dataAcces;

import connection.ConnectionFactory;
import model.Client;
import model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * Clasa care modeleaza operatiile pe Product
 */
public class ProductDAO extends AbstractDAO<Product>{
    /**
     * Atributele clasei
     */
    protected final Logger LOGGER = Logger.getLogger(ClientDAO.class.getName());
    private final String findStatementString = "SELECT * FROM product WHERE name = ?";
    private String get = "SELECT * from product";
    private static final String insertProduct = "INSERT INTO product (name, quantity,price,id)" + "VALUES (?,?,?,?)";
    /**
     * Aceasta metoda face update pe un produs
     * @param p Produsul care trebuie editat
     * @return Produsul editat
     */
    @Override
    public Product update(Product p) {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("UPDATE product SET name = ? WHERE id = ?");
            statement.setString(1, p.getName());
            statement.setInt(2, p.getId());
            statement.executeUpdate();
            statement = connection.prepareStatement("UPDATE product SET quantity = ? WHERE id = ?");
            statement.setInt(1, p.getQuantity());
            statement.setInt(2, p.getId());
            statement.executeUpdate();
            statement = connection.prepareStatement("UPDATE product SET price = ? WHERE id = ?");
            statement.setInt(1, p.getPrice());
            statement.setInt(2, p.getId());
            statement.executeUpdate();
        }catch (SQLException e) {
            LOGGER.log(Level.WARNING, "PRODUCT_DATABASE_OPERATIONS : EDIT" + e.getMessage());
        }
        ConnectionFactory.close(statement);
        ConnectionFactory.close(connection);
        return p;
    }
    /**
     * Aceasta metoda face delete pe un produs
     * @param id Id-ul produsului care trebuie sters
     */
    public void deleteProduct(int id) {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("DELETE FROM product WHERE id = ?");
            statement.setInt(1, id);
            statement.executeUpdate();
        }catch (SQLException e) {
            LOGGER.log(Level.WARNING,"PRODUCT_DATABASE_OPERATIONS:DELETE " + e.getMessage());
        }
        ConnectionFactory.close(statement);
        ConnectionFactory.close(connection);
    }
    public Product findByName(String productName) {
        Product produs = null;
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet result = null;
        try {
            findStatement = dbConnection.prepareStatement(findStatementString);
            findStatement.setString(1, productName);
            result = findStatement.executeQuery();
            result.next();
            int price = result.getInt("price");
            int qu = result.getInt("quantity");
            int id=result.getInt("id");
            produs = new Product(productName, qu,price,id);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,"ProductDAO: findByName " + e.getMessage());
        } finally {
            ConnectionFactory.close(result);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbConnection);
        }
        return produs;
    }
    public List<Product> listAllProducts(){
        List<Product> productsList = new ArrayList<Product>();
        Product p = null;
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = null;
        ResultSet result = null;
        try {
            statement = connection.prepareStatement("SELECT * FROM product");
            result = statement.executeQuery();
            while (result.next()) {
                int idProduct = result.getInt("id");
                String name = result.getString("name");
                int quantity = result.getInt("quantity");
                int price = result.getInt("price");
                p = new Product(name, quantity, price,idProduct);
                p.setId(idProduct);
                productsList.add(p);
            }
        }catch (SQLException e) {
            LOGGER.log(Level.WARNING,"CLIENT_DATABASE_OPERATIONS:LISTALL " + e.getMessage());
        }
        ConnectionFactory.close(result);
        ConnectionFactory.close(statement);
        ConnectionFactory.close(connection);
        return productsList;
    }
}
