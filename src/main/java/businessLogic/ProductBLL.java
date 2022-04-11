package businessLogic;

import businessLogic.validators.EmailValidator;
import businessLogic.validators.QuantityValidator;
import dataAcces.ClientDAO;
import dataAcces.ProductDAO;
import model.Client;
import model.Product;

import java.util.NoSuchElementException;
/**
 * Clasa care realizeaza operatiile pe un produs ,folosind operatiile deja implementate in DAO
 */
public class ProductBLL {
    /**
     * Atributele clasei
     */
    private ProductDAO productDAO = new ProductDAO();;
    private QuantityValidator cantitate=new QuantityValidator();
    public ProductBLL() {

    }
    /**
     * Aceasta metoda gaseste un produs dupa un nume dat
     * @param name numele dupa care se face cautarea
     * @return Produsul gasit
     */
    public Product findProductByNameProdus(String name) {
        Product st = productDAO.findByName(name);
        if (st == null) {
            throw new NoSuchElementException("The product with name =" + name + " was not found!");
        }
        return st;
    }
    public void insereazaProdus(Product p)
    {
        cantitate.valideazaCantitateP(p);
        ProductDAO productDAO = new ProductDAO();
        productDAO.insert(p);
    }

    public void stergeProdus(int id)
    {
        ProductDAO productDAO = new ProductDAO();
        productDAO.deleteProduct(id);
    }
    public void editProdus(Product p)
    {
        ProductDAO productDAO = new ProductDAO();
        productDAO.update(p);
    }
}
