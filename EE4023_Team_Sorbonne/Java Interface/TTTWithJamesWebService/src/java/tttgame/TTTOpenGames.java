package tttgame;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
/**
 *
 * @author Patrick
 */
public class TTTOpenGames extends JFrame{
        
    int uid;
    ttt.james.server.TTTWebService_Service service;
    ttt.james.server.TTTWebService port; 
    private JFrame parent;
    private User user;
    private JTable leaderboardTable;
    JTable gamesTable;

    public TTTOpenGames(User user, JFrame pops) {
        ttt.james.server.TTTWebService_Service service = new ttt.james.server.TTTWebService_Service();
        ttt.james.server.TTTWebService port = service.getTTTWebServicePort();  
        
        parent = pops;
        
        uid = user.getId();
        this.user = user;
        
        setBounds(20,20,320,240);

        this.setupTable();
        
        setVisible(true);
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
}
