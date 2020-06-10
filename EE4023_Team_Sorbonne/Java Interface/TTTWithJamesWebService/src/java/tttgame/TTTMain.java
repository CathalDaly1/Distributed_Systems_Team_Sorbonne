/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tttgame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
/**
 * @author Patrick
 */
public class TTTMain extends JFrame implements ActionListener{
    
    private JButton login;
    private JButton register;
    ttt.james.server.TTTWebService_Service service;
    ttt.james.server.TTTWebService port;
    
    public TTTMain(){
        
        ttt.james.server.TTTWebService_Service service = new ttt.james.server.TTTWebService_Service();
        ttt.james.server.TTTWebService port = service.getTTTWebServicePort();   
        
        setTitle("Tic Tac Toe Game");
        setBounds(30,30,300,300);
        setLayout(new GridLayout(1,2));

        
        JPanel menu = new JPanel();
        menu.setLayout(new GridLayout(2,1));
        login = new JButton("Login");
        login.addActionListener(this);
        
        register = new JButton("Register");
        register.addActionListener(this);
        
        menu.add(login);
        menu.add(register);
        
        add(menu);
        
//        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    
    public static void main(String[] args) {
        TTTMain myGame = new TTTMain();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            Object source = e.getSource();
            if(source == login) {
                LoginFrame myLogin = new LoginFrame(this);
            }
            if(source == register) {
                TTTRegisterFrame myRegister = new TTTRegisterFrame(this);
            }
        } catch(Exception ee) {
            System.err.println(ee.getMessage());
        }
    }
}