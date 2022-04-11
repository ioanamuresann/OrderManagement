package businessLogic;

import businessLogic.validators.QuantityValidator;
import dataAcces.OrderDAO;
import dataAcces.ProductDAO;
import model.Client;
import model.Order;
import model.Product;

import javax.swing.*;
import java.util.NoSuchElementException;
/**
 * Clasa care realizeaza operatiile pe o comanda ,folosind operatiile deja implementate in DAO
 */
public class OrderBLL {
    /**
     * Atributele clasei
     */
    private OrderDAO orderDAO=new OrderDAO();
    private QuantityValidator cantitate=new QuantityValidator();
    public OrderBLL() {

    }

    public Order findOrderById(int id) {
        Order st = orderDAO.findById(id);
        if (st == null) {
            JOptionPane.showMessageDialog(null,"COMANDA INVALIDA!");
            throw new NoSuchElementException("The client with id =" + id + " was not found!");
        }
        return st;
    }
    public void insereazaComanda(Order o)
    {
        cantitate.valideazaCantitateO(o);
        OrderDAO orderDAO = new OrderDAO();
        orderDAO.insertOrder(o);
    }
}
