package presentation;

import businessLogic.ProductBLL;
import connection.ConnectionFactory;
import dataAcces.ProductDAO;
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
public class ViewProduct extends JFrame{
    /**
     * Atributele clasei
     */
    JButton backButton=new JButton("Back");
    JButton addProduct= new JButton("Add new product");
    JButton editProduct= new JButton("Edit product");
    JButton deleteProduct= new JButton("Delete product");
    JButton viewProducts=new JButton("View all products");
    JLabel nameLabel=new JLabel("Name:");
    JLabel quantityLabel=new JLabel("Quantity:");
    JLabel priceLabel=new JLabel("Price:");
    JTextField nameText=new JTextField();
    JTextField quantityText=new JTextField();
    JTextField priceText=new JTextField();
    JTable productsTable;
    DefaultTableModel tableModel=new DefaultTableModel();
    JLabel idLabel=new JLabel("ID:");
    JTextField idText=new JTextField();
    JLabel title = new JLabel("Products:");
    Object [][] matrice=new Object[99][99];
    public ViewProduct() {

       Connection connection = ConnectionFactory.getConnection();
        final ProductDAO p = new ProductDAO();
        final ProductBLL pl=new ProductBLL();

        this.setSize(1190, 550);
        this.setTitle("ORDER MANAGEMENT");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
        this.getContentPane().setBackground(new Color(200, 80, 90));


        title.setBounds(390, 10, 300, 80);
        title.setForeground(  new Color(100, 0, 0));
        title.setFont(new Font("Arial", Font.ITALIC, 45));
        title.setVisible(true);
        this.add(title);

/**
 * Adaug butonul pentru a adauga un produs
 */
        addProduct.setBounds(870,90,250,60);
        addProduct.setBackground( new Color(100, 0, 0));
        addProduct.setFont(new Font("Arial",Font.ITALIC,15));
        addProduct.setForeground(Color.WHITE);
        addProduct.setFocusable(false);
        addProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == addProduct){
                    Product newProduct = new Product(nameText.getText(),Integer.parseInt(quantityText.getText()),Integer.parseInt(priceText.getText()),Integer.parseInt(idText.getText()));
                    pl.insereazaProdus(newProduct);
                }
            }
        });
        this.add(addProduct);

/**
 * Adaug butonul pentru a edita un produs
 */
        editProduct.setBounds(870,200,250,60);
        editProduct.setBackground( new Color(100, 0, 0));
        editProduct.setFont(new Font("Arial",Font.ITALIC,15));
        editProduct.setForeground(Color.WHITE);
        editProduct.setFocusable(false);
        editProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == editProduct){
                    Product newProduct = new Product(nameText.getText(),Integer.parseInt(quantityText.getText()),Integer.parseInt(priceText.getText()),Integer.parseInt(idText.getText()));
                    pl.editProdus(newProduct);
                }
            }
        });
        this.add(editProduct);

/**
 * Adaug butonul pentru a sterge un produs
 */
        deleteProduct.setBounds(870,310,250,60);
        deleteProduct.setBackground( new Color(100, 0, 0));
        deleteProduct.setFont(new Font("Arial",Font.ITALIC,15));
        deleteProduct.setForeground(Color.WHITE);
        deleteProduct.setFocusable(false);
        deleteProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == deleteProduct){
                    pl.stergeProdus(Integer.parseInt(idText.getText()));
                }
            }
        });
        this.add(deleteProduct);


/**
 * Adaug butonul pentru a vedea toate produsele
 */
        viewProducts.setBounds(870,420,250,60);
        viewProducts.setBackground( new Color(100, 0, 0));
        viewProducts.setFont(new Font("Arial",Font.ITALIC,15));
        viewProducts.setForeground(Color.WHITE);
        viewProducts.setFocusable(false);
        viewProducts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == viewProducts) {
                    try {
                        matrice = p.fields(p, p.listAllProducts());
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
                        for (int i = 0; i < 4; i++)
                            v.add(matrice[j][i]);
                        tableModel.addRow(v);
                    }
                }
            }
        });

        this.add(viewProducts);


        nameLabel.setBounds(10,90,100,40);
        nameLabel.setFont(new Font("Aerial",Font.ITALIC,30));
        nameLabel.setForeground( new Color(100, 0, 0));
        this.add(nameLabel);
        nameText.setBounds(50,130,180,30);
        this.add(nameText);

        quantityLabel.setBounds(10,170,200,40);
        quantityLabel.setFont(new Font("Aerial",Font.ITALIC,30));
        quantityLabel.setForeground( new Color(100, 0, 0));
        this.add(quantityLabel);
        quantityText.setBounds(50,210,180,30);
        this.add(quantityText);

        priceLabel.setBounds(10,250,200,40);
        priceLabel.setFont(new Font("Aerial",Font.ITALIC,30));
        priceLabel.setForeground( new Color(100, 0, 0));
        this.add(priceLabel);
        priceText.setBounds(50,290,180,30);
        this.add(priceText);


        idLabel.setBounds(10,330,200,40);
        idLabel.setFont(new Font("Aerial",Font.ITALIC,30));
        idLabel.setForeground( new Color(100, 0, 0));
        this.add(idLabel);
        idText=new JTextField();
        idText.setBounds(50,370,180,30);
        this.add(idText);

        tableModel.addColumn("PRODUCT NAME");
        tableModel.addColumn("QUANTITY");
        tableModel.addColumn("PRICE");
        tableModel.addColumn("ID");

        // Initializing the JTable
        productsTable = new JTable(tableModel);

        // Initializing the JTable
        productsTable.setBounds(270, 100, 550, 350);
        productsTable.setBackground( new Color(100, 0, 0));
        productsTable.setFont(new Font("Aerial",Font.ITALIC,15));
        productsTable.setForeground(Color.WHITE);
        this.add(productsTable);
/**
 * Adaug butonul pentru a reveni la GUI-ul principal
 */
        backButton.setBounds(930,10,150,30);
        backButton.setBackground( new Color(100, 0, 0));
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