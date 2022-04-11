package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

/**
 *  Aceasta clasa este GUI-ul principal
 *
 */
public class View {
    /**
     * Atributele clasei
     */
    JFrame frame=new JFrame();
    JPanel panel=new JPanel();
    JButton client= new JButton("CLIENTS");
    JButton product=new JButton("PRODUCTS");;
    JButton order= new JButton("ORDERS");;
    JLabel title = new JLabel("Bine ati venit! Alegeti..");
    public View() {

        frame.setSize(700, 500);
        frame.setTitle("Order management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        title.setBounds(250, 10, 300, 90);
        title.setForeground( new Color(100, 0, 0));
        title.setFont(new Font("Calibri", Font.ITALIC, 30));
        title.setVisible(true);
        frame.add(title);

        panel.setSize(900,300);
        panel.setBounds(0,0,800,500);
        panel.setBackground(new Color(200, 80, 90));
        panel.setLayout(null);
        frame.add(panel);


        client.setBounds(100,100,300,50);
        client.setBackground(new Color(100, 0, 0));
        client.setFont(new Font("Calibri",Font.BOLD,30));
        client.setForeground(Color.white);
        client.setFocusable(false);
        client.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource()==client)
                {
                    frame.dispose();
                        ViewClient clFrame = new ViewClient();
                    }
                }
        });
        panel.add(client);


        product.setBounds(100,200,300,50);
        product.setBackground(new Color(100, 0, 0));
        product.setFont(new Font("Calibri",Font.BOLD,30));
        product.setForeground(Color.white);
        product.setFocusable(false);
        product.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource()==product)
                {
                    frame.dispose();

                        ViewProduct prFrame = new ViewProduct();
                }
            }
        });
        panel.add(product);


        order.setBounds(100,300,300,50);
        order.setBackground(new Color(100, 0, 0));
        order.setFont(new Font("Calibri",Font.BOLD,30));
        order.setForeground(Color.white);
        order.setFocusable(false);
        order.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource()==order)
                {
                    frame.dispose();
                    ViewOrder orFrame = new ViewOrder();
                }
            }
        });
        panel.add(order);

        frame.setVisible(true);
    }
}