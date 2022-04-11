package businessLogic.validators;

import model.Order;
import model.Product;

import javax.swing.*;

/**
 * Clasa care verifica daca o cantitate este valida
 */
public class QuantityValidator {

    public void valideazaCantitateP(Product p)
    {
        if(p.getQuantity()<0) {
         JOptionPane.showMessageDialog(null,"Cantitate nevalida!");
            throw new IllegalArgumentException("Cantitate nevalida!");
        }
    }
    public void valideazaCantitateO(Order o)
    {
        if(o.getQuantity()<0) {
            JOptionPane.showMessageDialog(null,"Cantitate nevalida!");
            throw new IllegalArgumentException("Cantitate nevalida!");
        }
    }
}
