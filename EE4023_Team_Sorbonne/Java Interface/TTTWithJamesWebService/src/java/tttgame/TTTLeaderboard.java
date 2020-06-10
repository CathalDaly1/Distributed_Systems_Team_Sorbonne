/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tttgame;

import javax.swing.JFrame;
import java.util.ArrayList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
/**
 *
 * @author Patrick
 */
public class TTTLeaderboard extends JFrame{
        
    int uid;
    ttt.james.server.TTTWebService_Service service;
    ttt.james.server.TTTWebService port; 
    private JFrame parent;
    private User user;
    private JTable leaderboardTable;

    
    public TTTLeaderboard(User user, JFrame pops) {
        ttt.james.server.TTTWebService_Service service = new ttt.james.server.TTTWebService_Service();
        ttt.james.server.TTTWebService port = service.getTTTWebServicePort();  
        
        parent = pops;
        
        uid = user.getId();
        this.user = user;
        
        setBounds(20,20,320,240);

        
        ArrayList<String> users = new ArrayList<>();
        ArrayList<Integer> wins = new ArrayList<>();
        ArrayList<Integer> losses = new ArrayList<>();
        ArrayList<Integer> draws = new ArrayList<>();
        
        String league = port.leagueTable();
        String[] games = league.split("\n");
        
        for(int i = 0;i < games.length; i++){
            String[] details = games[i].split(",");
            if(!users.contains(details[1])){
                users.add(details[1]);
            }
            if(!users.contains(details[2])){
                users.add(details[2]);
            }
        }
        
        for(int i = 0;i < users.size();i++){
            wins.add(i,0);
            losses.add(i,0);
            draws.add(i,0);
        }

        for(int i = 0;i<games.length;i++){
            String[] details = games[i].split(",");
            String pOne = details[1];
            String pTwo = details[2];
            String gameState = details[3];
            int indexPOne = users.indexOf(pOne);
            int indexPTwo = users.indexOf(pTwo);

            if(gameState.equals(Data.GAME_STATE_PLAYER_1_WIN)){
                wins.set(indexPOne,wins.get(indexPOne)+1);
                losses.set(indexPTwo,losses.get(indexPTwo) + 1);
            }
            else if(gameState.equals(Data.GAME_STATE_PLAYER_2_WIN)){
                wins.set(indexPTwo,wins.get(indexPTwo)+1);
                losses.set(indexPOne,losses.get(indexPOne) + 1);
            }            
            else if(gameState.equals(Data.GAME_STATE_DRAW)){
                draws.set(indexPOne,draws.get(indexPOne)+1);
                draws.set(indexPTwo,draws.get(indexPTwo)+1);
            }
        }
        
        String[][] tableData = new String[users.size()][4];

        for(int i =0;i<users.size();i++){
            String username,winsCount,lossesCount,drawsCount;
            username = users.get(i);
            winsCount = String.valueOf(wins.get(i));
            lossesCount = String.valueOf(losses.get(i));
            drawsCount = String.valueOf(draws.get(i));
            String[] arr = {username,winsCount,lossesCount,drawsCount};
            tableData[i] = arr;
        }
        String[] columnNames = {"user","wins","losses","draws"};

        leaderboardTable = new JTable(tableData,columnNames);
        leaderboardTable.setBounds(30, 40, 200, 300); 

    
        JScrollPane sp = new JScrollPane(leaderboardTable); 
        add(sp);
        
        setVisible(true);
    }
}
