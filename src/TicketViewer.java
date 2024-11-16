/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */

/**
 *
 * @author HP
 */

import java.io.*;

public class TicketViewer {
    public static void main(String args[]) {
        try{
        FileInputStream file = new FileInputStream("ticket.tkt");
        ObjectInputStream ois = new ObjectInputStream(file);
        TicketConfirmation tkt = (TicketConfirmation)ois.readObject();
        tkt.setVisible(true);
        }
        catch(IOException | ClassNotFoundException e){
            System.out.println(e);
        }
    }
}
