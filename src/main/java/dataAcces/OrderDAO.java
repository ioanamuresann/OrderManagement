package dataAcces;

import connection.ConnectionFactory;
import model.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clasa care modeleaza operatiile pe Order
 */
public class OrderDAO extends AbstractDAO<Order>{
    /**
     * Atributele clasei
     */
    protected final Logger LOGGER = Logger.getLogger(OrderDAO.class.getName());
    private final String findStatementString = "SELECT * FROM `order` WHERE idorder = ?";
    private static final String insertOrder = "INSERT INTO `order` (idorder,client_name,product_name,quantity,price)" + "VALUES (?,?,?,?,?)";

    public Order findById(int idComanda) {
        Order comanda = null;
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet result = null;
        try {
            findStatement = dbConnection.prepareStatement(findStatementString);
            findStatement.setLong(1, idComanda);
            result = findStatement.executeQuery();
            result.next();
            String clName = result.getString("client_name");
            String prName = result.getString("product_name");
            int qu = result.getInt("quantity");
            int price = result.getInt("price");
            comanda = new Order(qu,clName,prName,price);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,"OrderDAO: findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(result);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbConnection);
        }
        return comanda;
    }

    public int insertOrder(Order o) {
        Connection connection=ConnectionFactory.getConnection();
        PreparedStatement statement =null;
        int idOrderInsert = o.getOrderId();
        try {
            statement = connection.prepareStatement(insertOrder,Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, o.getOrderId());
            statement.setString(2, o.getClientName());
            statement.setString(3, o.getProductName());
            statement.setInt(4, o.getQuantity());
            statement.setInt(5,o.getPrice());
            statement.execute();
        }catch(SQLException e){
            LOGGER.log(Level.WARNING ,"ORDER_DATABASE_OPERATIONS insert " + e.getMessage());
        }
        ConnectionFactory.close(statement);
        ConnectionFactory.close(connection);
        return idOrderInsert;
    }


    public List<Order> listAllOrders(){
        List<Order> ordersList = new ArrayList<Order>();
        Order o = null;
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = null;
        ResultSet result = null;
        try {
            statement = connection.prepareStatement("SELECT * FROM `order`");
            result = statement.executeQuery();
            while (result.next()) {
                int idOrder = result.getInt("idorder");
                String clientName = result.getString("client_name");
                String productName = result.getString("product_name");
                int quantity = result.getInt("quantity");
                int price = result.getInt("price");
                o = new Order(quantity,clientName, productName);
                o.setOrderId(idOrder);
                o.setPrice(price);
                ordersList.add(o);
            }
        }catch (SQLException e) {
            LOGGER.log(Level.WARNING,"ORDER_DATABASE_OPERATIONS:ListAll " + e.getMessage());
        }
        ConnectionFactory.close(result);
        ConnectionFactory.close(statement);
        ConnectionFactory.close(connection);
        return ordersList;
    }

}
