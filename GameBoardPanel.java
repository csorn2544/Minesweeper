package th.ac.ku.kps.cpe;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GameBoardPanel extends JPanel {
	   private static final long serialVersionUID = 1L; 

	   public static final int ROWS = 10;     
	   public static final int COLS = 10;

	   public static final int CELL_SIZE = 60;  
	   public static final int CANVAS_WIDTH  = CELL_SIZE * COLS; 
	   public static final int CANVAS_HEIGHT = CELL_SIZE * ROWS;

	   Cell cells[][] = new Cell[ROWS][COLS];
	   int numMines = 10;
	   int flag=10;

	   public GameBoardPanel() {
	      super.setLayout(new GridLayout(ROWS, COLS, 2, 2));  
	      for (int row = 0; row < ROWS; ++row) {
	         for (int col = 0; col < COLS; ++col) {
	            cells[row][col] = new Cell(row, col);
	            super.add(cells[row][col]);
	         }
	      }


	      CellMouseListener listener = new CellMouseListener();
	      // [TODO 4] Every cell adds this common listener
	      for (int row = 0; row < ROWS; ++row) {
	       for (int col = 0; col < COLS; ++col) {
	          cells[row][col].addMouseListener((MouseListener) listener);   
	       }
	      }

	      super.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
	   }

	   public void newGame() {
	      // Get a new mine map
	      MineMap mineMap = new MineMap();
	      mineMap.newMineMap(numMines,ROWS,COLS);

	      for (int row = 0; row < ROWS; row++) {
	         for (int col = 0; col < COLS; col++) {
	            // Initialize each cell with/without mine
	            cells[row][col].newGame(mineMap.isMined[row][col]);
	         }
	      }
	   }

	   private int getSurroundingMines(int srcRow, int srcCol) {
	      int numMines = 0;
	      for (int row = srcRow - 1; row <= srcRow + 1; row++) {
	         for (int col = srcCol - 1; col <= srcCol + 1; col++) {
	            // Need to ensure valid row and column numbers too
	            if (row >= 0 && row < ROWS && col >= 0 && col < COLS) {
	               if (cells[row][col].isMined) numMines++;
	            }
	         }
	      }
	      return numMines;
	   }

	   private void revealCell(int srcRow, int srcCol) {
	      int numMines = getSurroundingMines(srcRow, srcCol);
	     cells[srcRow][srcCol].setText(numMines + "");
	     cells[srcRow][srcCol].isRevealed = true;
	     cells[srcRow][srcCol].paint(); 
	      if (numMines == 0) {
	          for (int row = srcRow - 1; row <= srcRow + 1; row++) {
		            for (int col = srcCol - 1; col <= srcCol + 1; col++) {
		               if (row >= 0 && row < ROWS && col >= 0 && col < COLS) {
		                  if (!cells[row][col].isRevealed) revealCell(row, col);
		               }
	            }
	         }
	      }
	   }

	   public boolean hasWon() {
		   JOptionPane.showMessageDialog(null, "You Won");
	      return true;
	   }

	   // [TODO 2] Define a Listener Inner Class
	   private class CellMouseListener extends MouseAdapter {
		   private int countflag=0;
		  private int correct=0;
	      public void mouseClicked(MouseEvent e) {        
	         Cell sourceCell = (Cell)e.getSource();
	         // For debugging
	         System.out.println("You clicked on (" + sourceCell.row + "," + sourceCell.col + ")");

	         if (e.getButton() == MouseEvent.BUTTON1) {  
	            if (sourceCell.isMined) {
	               for (int row = 0; row < ROWS; row++) {
	      	         for (int col = 0; col < COLS; col++) {
	      	        	if(cells[row][col].isMined)
	      	        	{
	      	        		cells[row][col].setText("*");
	      	        	}
	      	        	else 
	      	        		revealCell(row, col);
	      	         }
	               }
	             JOptionPane.showMessageDialog(null, "Game Over");
	             countflag=0;
	            } else {
	               revealCell(sourceCell.row, sourceCell.col);
	            }
	         } else if (e.getButton() == MouseEvent.BUTTON3) { 
	        	 if(sourceCell.getText()=="F") {
	        		 sourceCell.setText("");
						countflag--;
						if(sourceCell.isMined)
							correct--;
					}	
					else if(sourceCell.isEnabled()&&countflag<flag&&!sourceCell.isRevealed) {
						sourceCell.setText("F");
						countflag++;
						if(sourceCell.isMined) 
							correct++;
						if(correct==10)
							hasWon();
					}
	         }

	      }
	   }
	}
