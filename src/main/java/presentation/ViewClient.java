package presentation;


import businessLogic.ClientBLL;
import connection.ConnectionFactory;
import dataAcces.ClientDAO;
import model.Client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.IntrospectionException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Acesta este GUI-ul specific operatiilor pe clienti
 */

public class ViewClient extends JFrame {
    /**
     * Atributele clasei
     */
    JButton addClient= new JButton("Add new client");
    JButton editClient= new JButton("Edit profile");
    JButton deleteClient= new JButton("Delete profile");
    JButton viewClients=new JButton("View all clients");
    JLabel nameLabel=new JLabel("Name:");
    JLabel adressLabel=new JLabel("Address:");
    JLabel emailLabel=new JLabel("Email:");
    JTextField nameText=new JTextField();
    JTextField adressText=new JTextField();
    JTextField emailText=new JTextField();
    JButton backButton=new JButton("Back");
    JTable clientsTable;
    Connection connection;
    DefaultTableModel tableModel = new DefaultTableModel();
    Object [][] matrice=new Object[99][99];
    JLabel idLabel=new JLabel("ID:");
    JTextField idText=new JTextField();
    JLabel title = new JLabel("CLIENTS:");
    public ViewClient() {

        connection = ConnectionFactory.getConnection();
        final ClientDAO cl = new ClientDAO();
        final ClientBLL clb=new ClientBLL();

        this.setSize(1180, 550);
        this.setTitle("ORDER MANAGEMENT");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
        this.getContentPane().setBackground(new Color(200, 80, 90));


        title.setBounds(380, 10, 300, 70);
        title.setForeground(new Color(100, 0, 0));
        title.setBackground(new Color(200, 80, 90));
        title.setFont(new Font("Arial", Font.ITALIC, 45));
        title.setVisible(true);
        this.add(title);


/**
 * Adaug butonul pentru adaugare client
 */
        addClient.setBounds(870,90,250,60);
        addClient.setBackground(new Color(100, 0, 0));
        addClient.setFont(new Font("Arial",Font.ITALIC,15));
        addClient.setForeground(Color.WHITE);
        addClient.setFocusable(false);

        addClient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==addClient){
                    Client newClient = new Client(nameText.getText(), adressText.getText(), emailText.getText(),Integer.parseInt(idText.getText()));
                   clb.valideaza(newClient);
                   clb.insereazaClient(newClient);
                }
            }
        });


        this.add(addClient);

/**
 * Adaug butonul pentru editare client
 */
        editClient.setBounds(870,200,250,60);
        editClient.setBackground(new Color(100, 0, 0));
        editClient.setFont(new Font("Arial",Font.ITALIC,15));
        editClient.setForeground(Color.WHITE);
        editClient.setFocusable(false);
        editClient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == editClient){
                    Client clientEditat = new Client(nameText.getText(), adressText.getText(), emailText.getText(),Integer.parseInt(idText.getText()));
                    clb.editClient(clientEditat);
                }
            }
        });

        this.add(editClient);
/**
 * Adaug butonul pentru stergere client
 */
        deleteClient.setBounds(870,310,250,60);
        deleteClient.setBackground(new Color(100, 0, 0));
        deleteClient.setFont(new Font("Arial",Font.ITALIC,15));
        deleteClient.setForeground(Color.WHITE);
        deleteClient.setFocusable(false);

        deleteClient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == deleteClient){
                    clb.stergeClient(Integer.parseInt(idText.getText()));
                }
            }
        });
        this.add(deleteClient);


/**
 * Adaug butonul pentru a vedea toti clientii
 */
        viewClients.setBounds(870,420,250,60);
        viewClients.setBackground(new Color(100, 0, 0));
        viewClients.setFont(new Font("Arial",Font.ITALIC,15));
        viewClients.setForeground(Color.WHITE);
        viewClients.setFocusable(false);

        viewClients.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == viewClients) {
                    try {
                        matrice = cl.fields(cl, cl.listAllClients());
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

        this.add(viewClients);
        tableModel.addColumn("ID");
        tableModel.addColumn("NAME");
        tableModel.addColumn("ADDRESS");
        tableModel.addColumn("EMAIL");

        // Initializing the JTable
        clientsTable = new JTable(tableModel);

        clientsTable.setBounds(270, 100, 550, 350);
        clientsTable.setBackground(new Color(100, 0, 0));
        clientsTable.setFont(new Font("Aerial",Font.ITALIC,15));
        clientsTable.setForeground(Color.WHITE);
        this.add(clientsTable);

        nameLabel.setBounds(10,90,100,40);
        nameLabel.setFont(new Font("Aerial",Font.ITALIC,25));
        nameLabel.setForeground(new Color(100, 0, 0));
        this.add(nameLabel);
        nameText.setBounds(50,130,180,30);
        this.add(nameText);

        adressLabel.setBounds(10,170,200,40);
        adressLabel.setFont(new Font("Aerial",Font.ITALIC,25));
        adressLabel.setForeground(new Color(100, 0, 0));
        this.add(adressLabel);
        adressText.setBounds(50,210,180,30);
        this.add(adressText);


        emailLabel.setBounds(10,250,200,40);
        emailLabel.setFont(new Font("Aerial",Font.ITALIC,25));
        emailLabel.setForeground(new Color(100, 0, 0));
        this.add(emailLabel);
        emailText.setBounds(50,290,180,30);
        this.add(emailText);


        idLabel.setBounds(10,330,200,40);
        idLabel.setFont(new Font("Aerial",Font.ITALIC,25));
        idLabel.setForeground(new Color(100, 0, 0));
        this.add(idLabel);
        idText.setBounds(50,370,180,30);
        this.add(idText);


/**
 * Adaug butonul pentru a reveni inapoi la GUI-ul principal
 */
        backButton.setBounds(930,10,150,30);
        backButton.setBackground(new Color(100, 0, 0));
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