package tttgame;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import tttgame.User;
/**
 *
 * @author Patrick
 */
public class GameFrame extends JFrame implements ActionListener{
        
    private int uid,gid;
    private String username;
    ttt.james.server.TTTWebService_Service service;
    ttt.james.server.TTTWebService port;
    private JFrame parent;
    private JButton[][] buttons;
    private JLabel[][] labels;
    private Runnable mainThread;
    private String boardState;
    private boolean myTurn, gameOver;
    private int playernum;
    JPanel grid;
    JLabel turnLabel;

    
    public GameFrame(User user,String gid, int playernum) {
               
        ttt.james.server.TTTWebService_Service service = new ttt.james.server.TTTWebService_Service();
        ttt.james.server.TTTWebService port = service.getTTTWebServicePort();  
                
        uid = user.getId();
        this.gid = Integer.parseInt(gid);
        username = user.getUsername();
        this.playernum = playernum;
        boardState = port.getBoard(Integer.parseInt(gid));
        gameOver = false;
        myTurn = true;
        
        port.setGameState(Integer.parseInt(gid),Integer.parseInt(Data.GAME_STATE_IN_PROGRESS));
        
        setTitle("Tic Tac Toe Game");
        setBounds(20,20,320,320);
        setLayout(new GridLayout(3,3));
        JPanel banner = new JPanel();
        banner.setLayout(new GridLayout(3,3));
        JLabel instructions = new JLabel("Click on the grid to place");
        turnLabel = new JLabel("");

        banner.add(instructions);
        banner.add(turnLabel);
        
        grid = new JPanel();
        grid.setLayout(new GridLayout(3,3));
        
        buttons = new JButton[3][3];
        labels = new JLabel[3][3];
        for(int i=0;i<3;i++) {
            for(int j=0;j<3;j++) {
                labels[i][j] = new JLabel("");
                buttons[i][j] = new JButton("");
                buttons[i][j].setBackground(Color.WHITE);
                buttons[i][j].addActionListener(this);
                buttons[i][j].add(labels[i][j]);
                grid.add(buttons[i][j]);
            }
        }
        
        add(grid);
        add(banner);
        
        setVisible(true);
                
        mainThread = new Runnable(){
            public void run(){
                ttt.james.server.TTTWebService_Service service = new ttt.james.server.TTTWebService_Service();
                ttt.james.server.TTTWebService port = service.getTTTWebServicePort();  
                                
                String gameState = port.getGameState(Integer.parseInt(gid));
                while(gameState.equals(Data.GAME_STATE_IN_PROGRESS) && !gameOver){
                    try {
                       Thread.currentThread().sleep(1000);
                    } catch( Exception e){
                        System.out.println(e);
                    }
                    
                    String latestBoard = port.getBoard(Integer.parseInt(gid));


                    //board has changed state
                    if(!latestBoard.equals(boardState)){
                        boardState = latestBoard;
                        updateUI();
                        checkWin();
                        decideTurn();
                    }
                    
                    if(gameOver){
                        turnLabel.setText("");
                    }
                }
            }
        };        
        
        new Thread(this.mainThread).start();

    }
    
    public void updateUI() {
        ttt.james.server.TTTWebService_Service service = new ttt.james.server.TTTWebService_Service();
        ttt.james.server.TTTWebService port = service.getTTTWebServicePort();  
        
        String[] moves = boardState.split("\n");
        for(int i = 0; i < moves.length; i++){
            String[] move = moves[i].split(",");
            if(move.length>1){
                int x = Integer.parseInt(move[1]);
                int y = Integer.parseInt(move[2]);
                if(i%2 == 0){
                    labels[x][y].setText("X");
                }
                else{
                    labels[x][y].setText("O");
                }
            }
        }
    }
    
    public void checkWin() {
        ttt.james.server.TTTWebService_Service service = new ttt.james.server.TTTWebService_Service();
        ttt.james.server.TTTWebService port = service.getTTTWebServicePort();  
        
            
        String result = port.checkWin(gid);
        //TODO error checking
        
        if(result.equals("1")){
            turnLabel.setText("Game Over");

            gameOver = true;
            if(playernum == 1){
                JOptionPane.showMessageDialog(null, "Congratulations you have won");
                 port.setGameState(gid,Integer.parseInt(Data.GAME_STATE_PLAYER_1_WIN));
            }
            else{
                JOptionPane.showMessageDialog(null, "Unfortunately you have lost");
                port.setGameState(gid,Integer.parseInt(Data.GAME_STATE_PLAYER_2_WIN));
            }
        }
        else if(result.equals("2")){
            turnLabel.setText("Game Over");

            gameOver = true;
            if(playernum == 1){
                JOptionPane.showMessageDialog(null, "Unfortunately you have lost");
                port.setGameState(gid,Integer.parseInt(Data.GAME_STATE_PLAYER_1_WIN));
            }
            else{
                JOptionPane.showMessageDialog(null, "Congratulations you have won");
                port.setGameState(gid,Integer.parseInt(Data.GAME_STATE_PLAYER_2_WIN));
            }
        }
        else if(result.equals("3")){
            turnLabel.setText("Game Over");

            gameOver = true;
            JOptionPane.showMessageDialog(null, "Its a draw!");
            port.setGameState(gid,Integer.parseInt(Data.GAME_STATE_DRAW));

        }
        
    }
    
    public void decideTurn() {
        ttt.james.server.TTTWebService_Service service = new ttt.james.server.TTTWebService_Service();
        ttt.james.server.TTTWebService port = service.getTTTWebServicePort();  
        
        String[] moves = boardState.split("\n");
        if(moves.length > 1){
            String[] secondLastMove = moves[moves.length-2].split(",");

            String uid = secondLastMove[0];

            if(this.uid == Integer.parseInt(uid)){
                myTurn = true;
                turnLabel.setText("It is your turn");
            }
            else{
                myTurn = false;
                turnLabel.setText("It is not your turn");
            }
        }
        else {
            String[] move = moves[0].split(",");
            String uid = move[0];
            if(this.uid == Integer.parseInt(uid)){
                myTurn = false;
                turnLabel.setText("It is not your turn");
            }
            else{
                myTurn = true;
                turnLabel.setText("It is your turn");

            }
        }
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        
        for(int i=0;i<3;i++) {
            for(int j = 0;j < 3;j++){
                if(source == buttons[i][j]){
                    if(myTurn){
                        this.takeSquare(i,j);
                    }
                    else if(gameOver){
                        JOptionPane.showMessageDialog(null, "Sorry looks like the game is over.. ");
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Sorry it's not your turn!");
                    }
                }
            }
        }
    }

    private void takeSquare(int x, int y) {
        ttt.james.server.TTTWebService_Service service = new ttt.james.server.TTTWebService_Service();
        ttt.james.server.TTTWebService port = service.getTTTWebServicePort();  
        
        String squareAvailability = port.checkSquare(x, y, gid);
        if(squareAvailability.equals(Data.CHECK_SQUARE_AVAILABLE)){
            String result = port.takeSquare(x,y,gid,uid);
            updateUI();
            myTurn = false;
        }
        else if(squareAvailability.equals(Data.CHECK_SQUARE_TAKEN)){
            JOptionPane.showMessageDialog(null, "Sorry this square is already taken");
        }
    }

    private void updateUIGrid(int x, int y) {
        if(playernum == 1){
            labels[x][y].setText("X");
        }
        if(playernum == 2){
            labels[x][y].setText("O");
        }
    }
}