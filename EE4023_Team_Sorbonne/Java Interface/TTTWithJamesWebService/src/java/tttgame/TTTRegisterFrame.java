/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tttgame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Patrick
 */
public class TTTRegisterFrame extends JFrame implements ActionListener {

    private TTTMain parent;
    ttt.james.server.TTTWebService_Service service;
    ttt.james.server.TTTWebService port;
    private JTextField name,surname,username,password;
    private JLabel nameLbl, surnameLbl, usernameLbl, passwordLbl;
    private JButton submit, clear;
//    private TTTMenu menu;
    
    public TTTRegisterFrame(TTTMain pops) {
        parent = pops;
        ttt.james.server.TTTWebService_Service service = new ttt.james.server.TTTWebService_Service();
        ttt.james.server.TTTWebService port = service.getTTTWebServicePort();   
        setBounds(20,20,300,450);
        setLayout(new GridLayout(6,2));
        
        nameLbl = new JLabel("Name:");
        name = new JTextField();
        add(nameLbl);
        add(name);
        
        surnameLbl = new JLabel("Surname:");
        surname = new JTextField();
        add(surnameLbl);
        add(surname);
        
        usernameLbl = new JLabel("Username:");
        username = new JTextField();
        add(usernameLbl);
        add(username);
        
        passwordLbl = new JLabel("Password:");
        password = new JTextField();
        add(passwordLbl);
        add(password);
        
        clear = new JButton("Clear");
        clear.addActionListener(this);
        add(clear);
        
        submit = new JButton("Submit");
        submit.addActionListener(this);
        add(submit);
        
        //pack();
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        WindowListener exitListener = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                parent.setVisible(true);
                dispose();
            }
        };
        addWindowListener(exitListener);
        
        setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        ttt.james.server.TTTWebService_Service service = new ttt.james.server.TTTWebService_Service();
        ttt.james.server.TTTWebService port = service.getTTTWebServicePort();   
        
        Object source = e.getSource();
        
        if(source == clear) {
            username.setText("");
            password.setText("");
        }
        
        if(source == submit) {
            String n = name.getText();
            String s = surname.getText();
            String u = username.getText();
            String p = password.getText();
            
            String value = port.register(u,p,n,s);
            switch(value) {
                case "ERROR-REPEAT":
                    JOptionPane.showMessageDialog(null, "User details alraedy exists.\nTry again.");
                    username.setText("");
                    password.setText("");
                    username.requestFocusInWindow();
                break;
                
                case "ERROR-INSERT":
                    JOptionPane.showMessageDialog(null, "Couldn't add to the database.\nTry again later");
                    username.setText("");
                    password.setText("");
                    username.requestFocusInWindow();
                break;
                
                case "ERROR-RETRIEVE":
                    JOptionPane.showMessageDialog(null, "Couldn't retrieve newly created details.\nTry again later");
                    username.setText("");
                    password.setText("");
                    username.requestFocusInWindow();
                break;
                
                case "ERROR-DB":
                    JOptionPane.showMessageDialog(null, "Couldn't find the databse.\nTry again later");
                    username.setText("");
                    password.setText("");
                    username.requestFocusInWindow();
                break;
                
                default:
                    name.setText("");
                    surname.setText("");
                    username.setText("");
                    password.setText("");
                    username.requestFocusInWindow();
                    User user = new User(Integer.parseInt(value),u);
//                    menu = new TTTMenu(user, parent);
                    setVisible(false);
            }
        }
    }
}
