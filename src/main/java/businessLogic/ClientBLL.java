package businessLogic;


import businessLogic.validators.EmailValidator;
import dataAcces.ClientDAO;
import model.Client;


import javax.swing.*;
import javax.xml.validation.Validator;
import java.util.NoSuchElementException;

/**
 * Clasa care realizeaza operatiile pe un client,folosind operatiile deja implementate in DAO
 */
public class ClientBLL {
    /**
     * Atributele clasei
     */
    private ClientDAO clientDAO=new ClientDAO();
    private EmailValidator email=new EmailValidator();

    public ClientBLL() {
    }

    /**
     * Aceasta metoda gaseste un client dupa un nume dat
     * @param name numele dupa care se face cautarea
     * @return Clientul gasit
     */
    public Client findClientByName(String name) {
        Client st = clientDAO.findByName(name);
        if (st == null) {
            JOptionPane.showMessageDialog(null,"CLIENT INVALID!");
            throw new NoSuchElementException("The client with name =" + name + " was not found!");
        }
        return st;
    }
    public void valideaza(Client c)
    {

        email.validate(c);
    }
    public void insereazaClient(Client c)
    {
        ClientDAO clientDAO=new ClientDAO();
        clientDAO.insert(c);
    }
    public void stergeClient(int id)
    {
        ClientDAO clientDAO=new ClientDAO();
        clientDAO.deleteClient(id);
    }
    public void editClient(Client c)
    {
        ClientDAO clientDAO=new ClientDAO();
        clientDAO.update(c);
    }
}
