package presentation;

import businessLogic.ClientBLL;
import businessLogic.OrderBLL;
import businessLogic.ProductBLL;
import connection.ConnectionFactory;
import dataAcces.ClientDAO;
import dataAcces.OrderDAO;
import dataAcces.ProductDAO;
import model.Bill;
import model.Client;
import model.Order;
import model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.util.Vector;
/**
 * Acesta este GUI-ul specific operatiilor pe comenzi
 */

public class ViewOrder extends JFrame{
    /**
     * Atributele clasei
     */
    JButton backButton=new JButton("Back");
    JButton addOrder= new JButton("Add order");
    JButton generateBill= new JButton("Create bill");
    JLabel productLabel=new JLabel("Product:");
    JLabel clientLabel=new JLabel("Client:");
    JLabel quantityLabel=new JLabel("Quantity:");
    JTextField productText=new JTextField();
    JTextField clientText=new JTextField();
    JTextField quantityText=new JTextField();
    JLabel idLabel=new JLabel("ID:");
    JTextField idText=new JTextField();
    JLabel priceLabel=new JLabel("Price:");
    JTextField priceText=new JTextField();
    JTable ordersTable;
    Connection connection;
    JButton viewOrders=new JButton("View orders");
    DefaultTableModel tableModel=new DefaultTableModel();
    JLabel title = new JLabel("Orders:");
    Object [][] matrice=new Object[99][99];
    public ViewOrder(){

        connection = ConnectionFactory.getConnection();
        final OrderDAO or = new OrderDAO();
        final ProductDAO pr = new ProductDAO();
        final ClientDAO cl = new ClientDAO();
        final OrderBLL bl=new OrderBLL();
        final ProductBLL pl=new ProductBLL();
        final ClientBLL cll=new ClientBLL();

        this.setSize(1180, 550);
        this.setTitle("ORDER MANAGEMENT");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
        this.getContentPane().setBackground(new Color(200, 80, 90));


        title.setBounds(430, 10, 400, 70);
        title.setForeground( new Color(100, 0, 0));
        title.setBackground(new Color(200, 80, 90));
        title.setFont(new Font("Arial", Font.ITALIC, 45));
        title.setVisible(true);
        this.add(title);


/**
 * Adaug butonul pentru a vizualiza toate comenziile
 */
        viewOrders.setBounds(870,100,250,60);
        viewOrders.setBackground(  new Color(100, 0, 0));
        viewOrders.setFont(new Font("Arial",Font.ITALIC,15));
        viewOrders.setForeground(Color.WHITE);
        viewOrders.setFocusable(false);

        viewOrders.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == viewOrders) {

                    try {
                        matrice = or.fields(or, or.listAllOrders());
                    } catch (IntrospectionException ex) {
                        ex.printStackTrace();
                    } catch (InvocationTargetException ex) {
                        ex.printStackTrace();
                    } catch (IllegalAccessException ex) {
                        ex.printStackTrace();
                    }
                    int x=matrice.length;
                    for (int j = 0; j < x; j++) {
                        Vector v = new Vector<>(90);
                        for (int i = 0; i < 5; i++)
                            v.add(matrice[j][i]);
                        tableModel.addRow(v);
                    }
                }
            }
        });


        this.add(viewOrders);
        tableModel.addColumn("ID");
        tableModel.addColumn("QUANTITY");
        tableModel.addColumn("CLIENT NAME");
        tableModel.addColumn("PRODUCT NAME");
        tableModel.addColumn("PRICE");

        // Initializing the JTable
        ordersTable = new JTable(tableModel);

        // Initializing the JTable
        ordersTable.setBounds(270, 100, 550, 350);
        ordersTable.setBackground(  new Color(100, 0, 0));
        ordersTable.setFont(new Font("Aerial",Font.ITALIC,15));
        ordersTable.setForeground(Color.WHITE);
        this.add(ordersTable);
/**
 * Adaug butonul pentru adaugare comanda
 */
        addOrder.setBounds(870,210,250,60);
        addOrder.setBackground(  new Color(100, 0, 0));
        addOrder.setFont(new Font("Arial",Font.ITALIC,15));
        addOrder.setForeground(Color.WHITE);
        addOrder.setFocusable(false);

        addOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == addOrder){
                    String productNameAdd = productText.getText();
                    Integer quantityAdd = Integer.parseInt(quantityText.getText());
                    Product product = pl.findProductByNameProdus(productNameAdd);
                    Client client=cll.findClientByName(clientText.getText());
                    int ok=0;
                    if(product == null){
                        JOptionPane.showMessageDialog(null, "NU EXISTA ACEST PRODUS!");
                        ok=1;
                    }
                    if(client==null)
                    {
                        JOptionPane.showMessageDialog(null,"NU EXISTA ACEST CLIENT VALID!");
                        ok=1;
                    }
                    if(product.getQuantity() < quantityAdd){
                        JOptionPane.showMessageDialog(null, "UNAVAILABLE STOCK!");
                        ok=1;
                    }
                   else if(product.getQuantity() >= quantityAdd){
                        product.setQuantity(product.getQuantity() - quantityAdd);
                    }
                   if(ok==0)
                   {Order insertAnOrder = new Order(Integer.parseInt(idText.getText()),Integer.parseInt(quantityText.getText()), clientText.getText(), productText.getText(), Integer.parseInt(priceText.getText()));
                      bl.insereazaComanda(insertAnOrder);
                   JOptionPane.showMessageDialog(null,"COMANDA REALIZATA CU SUCCES!");
                       pl.editProdus(product);
                   }}
            }
        });
        this.add(addOrder);
/**
 * Adaug butonul pentru a genera factura
 */
        generateBill.setBounds(870,320,250,60);
        generateBill.setBackground(  new Color(100, 0, 0));
        generateBill.setFont(new Font("Arial",Font.ITALIC,15));
        generateBill.setForeground(Color.WHITE);
        generateBill.setFocusable(false);
        generateBill.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == generateBill){
                    Order generateBillOrder = bl.findOrderById(Integer.parseInt(idText.getText()));
                    if(generateBillOrder==null)
                        JOptionPane.showMessageDialog(null,"ID comanda invalid");
                    Bill newBill = new Bill();
                    newBill.generateBill(generateBillOrder);
                }
            }
        });

        this.add(generateBill);


        productLabel.setBounds(10,90,100,40);
        productLabel.setFont(new Font("Aerial",Font.ITALIC,15));
        productLabel.setForeground(  new Color(100, 0, 0));
        this.add(productLabel);
        productText.setBounds(50,130,180,30);
        this.add(productText);


        clientLabel.setBounds(10,170,200,40);
        clientLabel.setFont(new Font("Aerial",Font.ITALIC,15));
        clientLabel.setForeground(  new Color(100, 0, 0));
        this.add(clientLabel);
        clientText.setBounds(50,210,180,30);
        this.add(clientText);


        quantityLabel.setBounds(10,250,200,40);
        quantityLabel.setFont(new Font("Aerial",Font.ITALIC,15));
        quantityLabel.setForeground(  new Color(100, 0, 0));
        this.add(quantityLabel);
        quantityText.setBounds(50,290,180,30);
        this.add(quantityText);


        idLabel.setBounds(10,330,200,40);
        idLabel.setFont(new Font("Aerial",Font.ITALIC,15));
        idLabel.setForeground(  new Color(100, 0, 0));
        this.add(idLabel);
        idText.setBounds(50,370,180,30);
        this.add(idText);


        priceLabel.setBounds(10,410,200,40);
        priceLabel.setFont(new Font("Aerial",Font.ITALIC,15));
        priceLabel.setForeground(  new Color(100, 0, 0));
        this.add(priceLabel);
        priceText.setBounds(50,450,180,30);
        this.add(priceText);
/**
 * Adaug butonul pentru a reveni la GUI-ul principal
 */
        backButton.setBounds(930,10,150,30);
        backButton.setBackground(  new Color(100, 0, 0));
        backButton.setFont(new Font("Aerial", Font.ITALIC,20));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusable(false);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==backButton)
                {
                    dispose();
                    View v=new View();
                }
            }
        });
        this.add(backButton);

        this.setVisible(true);
    }
}