import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;


public class UI extends JFrame {

    UI() {//

        super("L-GAME");

        // The Main Panel where the 2 other panels will be on
        JPanel mainPanel = new JPanel(new BorderLayout());

        // The textarea will be inside this panel
        JPanel areaPanel = new JPanel(new BorderLayout());

        JLabel title = new JLabel("L-GAME");
        title.setFont(new Font("Arial", Font.PLAIN, 40));
        title.setHorizontalAlignment(JLabel.CENTER);
        
        // Fill the whole space of the panel with the area
        areaPanel.add(title, BorderLayout.CENTER);

        // The buttons will be inside this panel
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 30, 30));
        // Adding buttons
    	JButton startButton = new JButton("Start");
    	startButton.setFont(new Font("Arial", Font.PLAIN, 40));
        buttonPanel.add(startButton);
        
        JButton infoButton = new JButton("Info");
    	infoButton.setFont(new Font("Arial", Font.PLAIN, 40));
        buttonPanel.add(infoButton);
        
        JButton quitButton = new JButton("Quit");
    	quitButton.setFont(new Font("Arial", Font.PLAIN, 40));
        buttonPanel.add(quitButton);
        
        startButton.setActionCommand("start");
        startButton.addActionListener(new ButtonClickListener());
        infoButton.setActionCommand("info");
        infoButton.addActionListener(new ButtonClickListener());
        quitButton.setActionCommand("quit");
        quitButton.addActionListener(new ButtonClickListener());
        

        // The textarea-panel should be on top of the main panel
        mainPanel.add(areaPanel, BorderLayout.NORTH);

        // The panel with the buttons should fill the remaining space
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        getContentPane().add(mainPanel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setResizable(false);
        setVisible(true);
    }
    class ButtonClickListener implements ActionListener {
    	public void actionPerformed (ActionEvent e) {
    		String command = e.getActionCommand ();
    		if (command.equals ("start")) {
    			setLayout(null);
    			new GameUi();//open gameui window
    		}
    		else if (command.equals("info")) {
    			try {
					new InfoUi();//open infoui window
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
    		}
    		else {
    			System.exit(1);
    		}
    		}
    	}


    public static void main(String[] args) {
        new UI();
    }

}