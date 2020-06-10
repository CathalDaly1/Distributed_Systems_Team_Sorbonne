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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.image.BufferedImage;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Patrick
 */
public class TTTMenu extends JFrame implements ActionListener {
    ttt.james.server.TTTWebService_Service service;
    ttt.james.server.TTTWebService port; 
    private JFrame parent;
    private int uid, gid;
    private User user;
    private JLabel pic;
    private JButton score, leaderboard, createGame, refreshGames;
    private JButton[] buttons;
    private JTextField entry;
    private BufferedImage current;
    JTable gamesTable;
    
    public TTTMenu(User user, JFrame pops) {
        ttt.james.server.TTTWebService_Service service = new ttt.james.server.TTTWebService_Service();
        ttt.james.server.TTTWebService port = service.getTTTWebServicePort();
        
        parent = pops;
        this.user = user;
        
        uid = user.getId();
        setTitle("Tic Tac Toe Menu");
        setBounds(20,20,320,240);
        setLayout(new GridLayout(2,1));

        JPanel menu = new JPanel();
        menu.setLayout(new GridLayout(4,1));
        score = new JButton("Score");
        score.addActionListener(this);
        
        leaderboard = new JButton("Leaderboard");
        leaderboard.addActionListener(this);
        
        createGame = new JButton("Create Game");
        createGame.addActionListener(this);
        
        refreshGames = new JButton("Show open Games");
        refreshGames.addActionListener(this);
        
        menu.add(score);
        menu.add(leaderboard);
        menu.add(createGame);
        menu.add(refreshGames);
        
        add(menu);
        
        this.setupTable();
        
        
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            Object source = e.getSource();
            if(source == score) {
                TTTScoreFrame myScore = new TTTScoreFrame(user,this);
            }
            if(source == leaderboard) {
                TTTLeaderboard myLeaderboard = new TTTLeaderboard(user,this);
            }
            if(source == createGame) {
                TTTGameCreatedWaitingFrame myGameWaiting = new TTTGameCreatedWaitingFrame(user,this);
            }
            if(source == refreshGames) {
                TTTOpenGames openGames = new TTTOpenGames(user,this);
            }
        } catch(Exception ee) {
            System.err.println(ee.getMessage());
        }
    }

    private void setupTable() {
        ttt.james.server.TTTWebService_Service service = new ttt.james.server.TTTWebService_Service();
        ttt.james.server.TTTWebService port = service.getTTTWebServicePort();   

        
        String openGames = port.showOpenGames();
        if(!openGames.equals("ERROR-NOGAMES")){
            String[] games = openGames.split("\n");
            String[][] tableData = new String[games.length][3];
            for(int i =0;i<games.length;i++){
                tableData[i] = (games[i].split(","));
            }
            String[] columnNames = {"gId","username","started"};

            gamesTable = new JTable(tableData,columnNames);
            gamesTable.setBounds(30, 40, 200, 300); 

            gamesTable.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
                public void valueChanged(ListSelectionEvent event) {
                    ttt.james.server.TTTWebService_Service service = new ttt.james.server.TTTWebService_Service();
                    ttt.james.server.TTTWebService port = service.getTTTWebServicePort();  

                    String gid = gamesTable.getValueAt(gamesTable.getSelectedRow(), 0).toString();
                    String joinResult = port.joinGame(uid, Integer.parseInt(gid));
                    if(joinResult.equals("1")){
                        GameFrame gameFrame = new GameFrame(user,gid,2);
                    }
                    else if(false){
                        //some error checking
                    }
                }
            });

            JScrollPane sp = new JScrollPane(gamesTable); 
            add(sp);
        }
    }
    
//    private JTable getJTable() {
//        String openGames = port.showOpenGames();
//        if(!openGames.equals("ERROR-NOGAMES")){
//            String[] games = openGames.split("\n");
//            String[][] tableData = new String[games.length][3];
//            for(int i =0;i<games.length;i++){
//                tableData[i] = (games[i].split(","));
//            }
//            String[] columnNames = {"gId","username","started"};
//    
//        }
//    }
//    
    private void updateTable(){
        remove(gamesTable);
        
        revalidate();
        repaint();
        
        ttt.james.server.TTTWebService_Service service = new ttt.james.server.TTTWebService_Service();
        ttt.james.server.TTTWebService port = service.getTTTWebServicePort();   
        
        String openGames = port.showOpenGames();
        if(!openGames.equals("ERROR-NOGAMES")){
            String[] games = openGames.split("\n");
            String[][] tableData = new String[games.length][3];
            for(int i =0;i<games.length;i++){
                tableData[i] = (games[i].split(","));
            }
            String[] columnNames = {"gId","username","started"};
            
            gamesTable.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
                public void valueChanged(ListSelectionEvent event) {
                    ttt.james.server.TTTWebService_Service service = new ttt.james.server.TTTWebService_Service();
                    ttt.james.server.TTTWebService port = service.getTTTWebServicePort();  

                    String gid = gamesTable.getValueAt(gamesTable.getSelectedRow(), 0).toString();
                    String joinResult = port.joinGame(uid, Integer.parseInt(gid));
                    if(joinResult.equals("1")){
                        GameFrame gameFrame = new GameFrame(user,gid,2);
                    }
                    else if(false){
                        //some error checking
                    }
                }
            });
        }
        JScrollPane sp = new JScrollPane(gamesTable); 
        add(sp);
        
        revalidate();
        repaint();
    }
}
