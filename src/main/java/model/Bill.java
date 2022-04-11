package model;

import dataAcces.OrderDAO;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.RandomAccess;

/**
 * Clasa Bill contine informatii si genereaza o factura pentru o anumita comanda
 */
public class Bill {
    /**
     *Scrie intr-un document .txt,continutul facturii
     * @param order Order este comanda pentru care se doreste generarea facturii
     */
    public void generateBill(Order order){
        try{
            File myFile = new File("bill.txt");
            FileWriter myWriter = new FileWriter("bill.txt");
            int totalPrice = order.getPrice() * order.getQuantity();
            myWriter.write("------------------FACTURA------------------"+"\n");
            myWriter.write("NUMELE CLIENTULUI: "+order.getClientName()+"\n");
            myWriter.write("NUMELE PRODUSULUI: "+order.getProductName()+"\n");
            myWriter.write("ATI CUMPARAT : "+order.getQuantity()+" produse("+order.getProductName()+")"+"\n");
            myWriter.write("PRETUL UNEI BUCATI ESTE DE : " + order.getPrice()+"\n");
            myWriter.write("TOTAL: "+totalPrice+" lei");
            myWriter.close();
            if (myFile.createNewFile()) {
                System.out.println("File created: " + myFile.getName());
            }
            else {
                JOptionPane.showMessageDialog(null,"Bill created succesfully!");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while processing the bill!");
            e.printStackTrace();
        }
    }
}
