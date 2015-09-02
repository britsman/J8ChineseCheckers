package backend;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game {
	
	private static Optional<JFrame> frame = Optional.empty();

	public static void main(String[] args) {
		GameBoard.constructBoard();
//		testConnections(GameBoard.board.get(107));//Array index vs board index
		Runnable r1 = () -> createAndShowGUI();
        javax.swing.SwingUtilities.invokeLater(r1);
	}
	
//	private static void testConnections(GameTile gameTile) {
//		for(Integer i : GameBoard.getPossibleMoves(true,gameTile.getIndex(), new HashSet<Integer>())){
//		//for(Integer i : GameBoard.getConnections(gameTile.getIndex())){
//			System.out.println(i);
//		}
//	}
//	
    private static void createAndShowGUI() {
    	if (!frame.isPresent()){
        frame = Optional.of(new JFrame("Chinese Checkers"));
        frame.get().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.get().setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
    	}
    	frame.get().getContentPane().removeAll();
    	frame.get().setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
        //frame.getContentPane().setPreferredSize(new Dimension(1280, 720));
    	ArrayList<Box> rows = new ArrayList<Box>();
    	ArrayList<JPanel> panes = new ArrayList<JPanel>();
    	
    	IntStream.rangeClosed(1, 17)
    		.forEachOrdered(i -> rows.add(new Box(BoxLayout.X_AXIS)));
		
    	IntStream.rangeClosed(1, 121)
		.forEachOrdered(i -> {
			Box currentRow = null; 
			JButton button = new JButton(i + "");
			button.addActionListener(showDestinations());
			button.setPreferredSize(new Dimension(40, 20));
			button.setIconTextGap(0);
			//button.setVisible(true);
			button.setOpaque(true);
			if(i < 2){
				currentRow = rows.get(0); 
			}
			else if(i < 4){
				currentRow = rows.get(1);  
			}
			else if(i < 7){
				currentRow = rows.get(2); 
			}
			else if(i < 11){
				currentRow = rows.get(3); 
			}
			else if(i < 24){
				currentRow = rows.get(4); 
			}
			else if(i < 36){
				currentRow = rows.get(5); 
			}
			else if(i < 47){
				currentRow = rows.get(6);  
			}
			else if(i < 57){
				currentRow = rows.get(7); 
			}
			else if(i < 66){
				currentRow = rows.get(8); 
			}
			else if(i < 76){
				currentRow = rows.get(9);  
			}
			else if(i < 87){
				currentRow = rows.get(10); 
			}
			else if(i < 99){
				currentRow = rows.get(11); 
			}
			else if(i < 112){
				currentRow = rows.get(12);  
			}
			else if(i < 116){
				currentRow = rows.get(13);  
			}
			else if(i < 119){
				currentRow = rows.get(14); 
			}
			else if(i < 121){
				currentRow = rows.get(15);  
			}
			else{
				currentRow = rows.get(16);
			}
			currentRow.add(button);
			switch (GameBoard.board.get(i-1).getPlayer()) {
			case 1:
				button.setBackground(Color.red);
				break;
			case 2:
				button.setBackground(Color.blue);
				break;
			case 3:
				button.setBackground(Color.yellow);
				break;
			case 4:
				button.setBackground(Color.green);
				break;
			case 5:
				button.setBackground(Color.BLACK);
				break;
			case 6:
				button.setBackground(Color.white);
				break;
			case 7:
				button.setBackground(Color.gray);
				GameBoard.board.get(i-1).setPlayer(0);
				break;
			default:
				button.setBackground(Color.LIGHT_GRAY);
				break;
			}
			
		});

    	IntStream.rangeClosed(1, 17)
		.forEachOrdered(i -> {panes.add((new JPanel(new FlowLayout(FlowLayout.CENTER,0,0))));
    				   panes.get(i-1).setPreferredSize(new Dimension(1280, 20));});
    	
    	panes.parallelStream()
    		.forEach(p -> p.add(rows.get(panes.indexOf(p))));
    	panes.stream()
		.forEachOrdered(p -> frame.get().add(p));
        frame.get().setPreferredSize(new Dimension(1280, 720));
        frame.get().setResizable(false);
        frame.get().pack();
        frame.get().setVisible(true);
        frame.get().validate();
        frame.get().repaint();
        
    }
	private static ActionListener showDestinations(){
		ActionListener a1 = (e) -> {
			int index = Integer.parseInt(((JButton)e.getSource()).getText());
			System.out.println(index);
			GameBoard.getPossibleMoves(true, index, new HashSet<Integer>()).parallelStream()
			.forEach(i -> GameBoard.board.get(i-1).setPlayer(7));
			createAndShowGUI();
		};
		return a1;
	}
}
