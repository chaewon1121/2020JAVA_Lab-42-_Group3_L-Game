import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


public class InfoUi extends JFrame { 
    // Declaration of objects of JLabel class. 
    JLabel description;
  
    // Constructor of Example class. 
    public InfoUi() throws IOException 
    { 
        // Creating Object of "FlowLayout" class 
        FlowLayout layout = new FlowLayout(); 
  
        JPanel mainPanel = new JPanel(new BorderLayout());
        // this Keyword refers to current object. 
        // Function to set Layout of JFrame. 
        setLayout(layout); 
        // Initialization of object "l1" of JLabel class. 
        description = new JLabel("<html>L-Game is a two-player game played on a board of 4x4 squares.<br/>Each player has a 3x2 L-shaped piece, and there are two 1x1 neutral piece.<br/>"
        		+"The game will start on default position<br/>" + 
        		"On each turn, a player must first move their L piece, and then may optionally move one of the neutral pieces.<br/>" + 
        		"The game is won by leaving the opponent unable to move their L piece to a new position.<br/>" + 
        		"Each pieces cannot be overlapped.<br/>" + 
        		"To move the block, simply select the area that you want to move with your mouse, and click next.<br/>"+
        		"The status message will tell you how the game is going on<br/>"+
        		"If you want to end the game early, click surrender.<br/>"+
        		"<br/>May the odds be ever in your favour.<html>"
        		); 
        
        description.setFont(new Font("Arial", Font.PLAIN, 20));
        
        JButton backButton = new JButton("Back");
        backButton.setActionCommand("Back");
        backButton.addActionListener(new ButtonClickListener());

        // The panel with the buttons should fill the remaining space
        mainPanel.add(description, BorderLayout.NORTH);
        
        BufferedImage myPicture = ImageIO.read(new File("gameBoard.png"));
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        add(picLabel);
        mainPanel.add(picLabel, BorderLayout.CENTER);
        mainPanel.add(backButton, BorderLayout.SOUTH);
        getContentPane().add(mainPanel);
  
        setTitle("Info"); 
         
        setSize(1000, 500); 
        setVisible(true); 

    }
    class ButtonClickListener implements ActionListener {
    	public void actionPerformed (ActionEvent e) {
    		String command = e.getActionCommand ();
    		
     		if (command.equals("Back")){
    			JComponent comp = (JComponent) e.getSource();
    			  Window win = SwingUtilities.getWindowAncestor(comp);
    			  win.dispose();
    		}
    		}
    	}
} 