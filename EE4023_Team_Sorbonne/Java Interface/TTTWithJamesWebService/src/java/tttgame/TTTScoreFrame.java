/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tttgame;

import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
/**
 *
 * @author Patrick
 */
public class TTTScoreFrame extends JFrame {
        
    private int uid;
    private String username;
    ttt.james.server.TTTWebService_Service service;
    ttt.james.server.TTTWebService port;  
    private JFrame parent;
    int winsCount,lossesCount,drawsCount = 0;

    
    public TTTScoreFrame(User user, JFrame pops) {
        ttt.james.server.TTTWebService_Service service = new ttt.james.server.TTTWebService_Service();
        ttt.james.server.TTTWebService port = service.getTTTWebServicePort(); 
        
        parent = pops;
        
        uid = user.getId();
        username = user.getUsername();
        
        calculateUserScore();
        
        setTitle("Score");
        setBounds(20,20,240,240);
        setLayout(new GridLayout(2,1));
        JPanel banner = new JPanel();

        banner.setLayout(new GridLayout(3,1));
        JLabel wins = new JLabel("Wins: " + String.valueOf(winsCount));
        JLabel losses = new JLabel("Losses: " + String.valueOf(lossesCount));
        JLabel draws = new JLabel("Draws: " + String.valueOf(drawsCount));
        
        banner.add(wins);
        banner.add(losses);
        banner.add(draws);

        
        add(banner);
        
        pack();
        setVisible(true);
        
        //funcion for calculating values        
    }
    
    private void calculateUserScore(){
        ttt.james.server.TTTWebService_Service service = new ttt.james.server.TTTWebService_Service();
        ttt.james.server.TTTWebService port = service.getTTTWebServicePort(); 
        String myGames = port.showAllMyGames(uid);
        String[] lines = myGames.split("\n");
        for(int i = 0;i < lines.length;i++){
            String[] tokens = lines[i].split(",");
            String gid = tokens[0];
            String playerOne = tokens[1];
            String playerTwo = tokens[2];
            
            boolean loggedUserPOne = this.checkUser(playerOne,playerTwo);
                        
            String result = port.getGameState(Integer.parseInt(gid));
            
            //error checking
            
            if(loggedUserPOne){
                if(result.equals("1")){
                    winsCount++;
                }
                else if(result.equals("2")){
                    lossesCount++;
                }
                else if(result.equals("3")){
                    drawsCount++;
                }
            } 
            else{
                if(result.equals("1")){
                    lossesCount++;
                }
                else if(result.equals("2")){
                    winsCount++;
                }
                else if(result.equals("3")){
                    drawsCount++;
                }
            }
        }
    }

    private boolean checkUser(String playerOne, String playerTwo) {
        if(username.equals(playerOne)){
            return true;
        }
        else{
            return false;
        }
    }
}