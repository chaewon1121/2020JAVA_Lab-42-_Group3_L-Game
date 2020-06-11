import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;


class Player{
	String id;
	int color;
}

public class GameUi extends JFrame {

    GameUi() {//

        super("Game");
        Player player = new Player();
        player.id = "L-game master";
        player.color = 1;

        // The Main Panel where the 2 other panels will be on
        JPanel mainPanel = new JPanel(new BorderLayout());

        // The textarea will be inside this panel
        JPanel areaPanel = new JPanel(new BorderLayout());

        JLabel playerinfo = new JLabel("<html>Player id: "+player.id+"<br/>Player color: " + ((player.color==1) ? "Red" : "Blue")+"<html>" );
        playerinfo.setBorder(new LineBorder(Color.BLACK));
        
        JButton surrenderButton = new JButton("Surrender");
        surrenderButton.setActionCommand("Surrender");
        surrenderButton.addActionListener(new ButtonClickListener());
        
        JButton nextButton = new JButton("Next"); 
        // Fill the whole space of the panel with the area
        areaPanel.add(nextButton, BorderLayout.EAST);
        areaPanel.add(playerinfo, BorderLayout.CENTER);
        
        int[][] board = {{3,1,1,0},{0,2,1,0},{0,2,1,0},{0,2,2,3}};

        // The buttons will be inside this panel
        JPanel buttonPanel = new JPanel(new GridLayout(4, 4, 0, 0));
        for (int i = 0; i < 16; i++) { // Adding buttons
        	JButton button = new JButton();
        	if(board[i/4][i%4]==1)
        		button.setBackground(Color.RED);
        	else if(board[i/4][i%4]==2)
        		button.setBackground(Color.BLUE);
        	else if(board[i/4][i%4]==3)
        		button.setBackground(Color.BLACK);
        	button.setOpaque(true);
            buttonPanel.add(button);
            button.setActionCommand( Integer.toString(i) );
            button.addActionListener(new ButtonClickListener());
        }

        // The textarea-panel should be on top of the main panel
        mainPanel.add(areaPanel, BorderLayout.NORTH);

        // The panel with the buttons should fill the remaining space
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        
        mainPanel.add(surrenderButton, BorderLayout.SOUTH);

        getContentPane().add(mainPanel);

//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setResizable(false);
        setVisible(true);
    }
    class ButtonClickListener implements ActionListener {
    	public void actionPerformed (ActionEvent e) {
    		String command = e.getActionCommand ();
    		if (command.equals ("")) {
    			
    		}
    		else if (command.equals("")) {
    			
    		}
    		else if (command.equals("Surrender")){
    			JComponent comp = (JComponent) e.getSource();
    			  Window win = SwingUtilities.getWindowAncestor(comp);
    			  win.dispose();
    		}
    		}
    	}
}
