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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
/**
 *
 * @author Patrick
 */
public class TTTGameCreatedWaitingFrame extends JFrame{
        
    int uid;
    User user;
    ttt.james.server.TTTWebService_Service service;
    ttt.james.server.TTTWebService port; 
    private JFrame parent;
    String gid;
    boolean joinedGame;
    ScheduledExecutorService executor;
    
    public TTTGameCreatedWaitingFrame(User user, JFrame pops) {
        ttt.james.server.TTTWebService_Service service = new ttt.james.server.TTTWebService_Service();
        ttt.james.server.TTTWebService port = service.getTTTWebServicePort();  
        
        parent = pops;
        
        joinedGame = false;
        this.user = user;
        uid = user.getId();
        
        setTitle("Lobby");
        setBounds(20,20,240,240);
        setLayout(new GridLayout(2,1));
        JPanel banner = new JPanel();
        banner.setLayout(new GridLayout(3,1));
        JLabel waiting = new JLabel("Waiting for a player 2 to join the game");
        
        banner.add(waiting);

        
        add(banner);
        
        pack();
        setVisible(true);
        
        this.createGame();
        
        this.checkGameStatus();
        
        //periodically check whether someone has joined or not
        
    }

    private void createGame() {
        ttt.james.server.TTTWebService_Service service = new ttt.james.server.TTTWebService_Service();
        ttt.james.server.TTTWebService port = service.getTTTWebServicePort();  
        gid = port.newGame(uid);
        
    }

    private void checkGameStatus() {
        Runnable checkStatus = new Runnable() {
            public void run() {
                ttt.james.server.TTTWebService_Service service = new ttt.james.server.TTTWebService_Service();
                ttt.james.server.TTTWebService port = service.getTTTWebServicePort(); 
                String result = port.getGameState(Integer.parseInt(gid));
                if(result.equals("0")){
                    setVisible(false);
                    GameFrame gameFrame = new GameFrame(user,gid,1);
                    TTTGameCreatedWaitingFrame.this.executor.shutdown();
                }
            }
        };
        
        executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(checkStatus, 0, 3, TimeUnit.SECONDS);
        

    }
}
