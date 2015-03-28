package com.Yann;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

/**
 * Created by Yanirash on 3/27/2015.
 */
public class TicketGUI extends JFrame {
    private JPanel rootPanel;
    private JTextField problem;
    private JButton addATicket;
    private JTextField reporter;
    private JComboBox Jpriority;
    private JList<Ticket> ticketList;
    private JButton ResolveTicket;

    DefaultListModel<Ticket> ticketListModel;



    public TicketGUI () {
        super("Ticket App");
        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(300,300);

        final String High = "1";
        final String MEDIUM = "2";
        final String LOW = "3";

        Jpriority.addItem(High);
        Jpriority.addItem(MEDIUM);
        Jpriority.addItem(LOW);


        ticketListModel = new DefaultListModel<Ticket>();
        ticketList.setModel(ticketListModel);
        ticketList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);



        problem.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
            }
        });

        reporter.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                String prob = (String)e.toString();
            }
        });



        Jpriority.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        addATicket.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Read in data from Textfields and checkbox and make a new Dog object
                // Add the ticket object to the list.
                String yourProblem = problem.getText();
                String theReporter = reporter.getText();
                String priority = (String)Jpriority.getSelectedItem();
                int newPriority = Integer.parseInt(priority);

                Date date = new Date();

                Ticket myTicket = new Ticket(yourProblem,newPriority,theReporter,date);
                TicketGUI.this.ticketListModel.addElement(myTicket);


                // setting the JList visibility
                ticketList.setVisible(true);


            }
        });

        // a key listener that remove a Ticket that has been resolved

        ResolveTicket.addActionListener(new ActionListener()  {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Fetch and resolve the selected ticket
                Ticket resolved = TicketGUI.this.ticketList.getSelectedValue();
                TicketGUI.this.ticketListModel.removeElement(resolved);


                // need to handle exception otherwise it wont work

                try {
                    fileCreator(resolved);

                } catch (IOException ie) {
                    JOptionPane.showMessageDialog(TicketGUI.this, "Enter a Ticket");
                    return;
                }



            }
        });
    }
    // create a file method that will store resolves and current tickets
    public void fileCreator (Ticket myTicket) throws IOException {

        FileWriter ticketFile = new FileWriter("myTickets.txt", true);
        BufferedWriter bufTicket = new BufferedWriter(ticketFile);
        bufTicket.write(myTicket.toString() + "\n");

        bufTicket.close();





    }

}
