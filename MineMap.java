package th.ac.ku.kps.cpe;

import java.util.Random;

public class MineMap {
	   // package access
	   int numMines;
	   boolean[][] isMined = new boolean[GameBoardPanel.ROWS][GameBoardPanel.COLS];
	         // default is false

	   // Constructor
	   public MineMap() {
	      super();
	   }

	   public void newMineMap(int numMines,int ROWS,int COLS) {
	      this.numMines = numMines;
	      Random r = new Random();
	      int countbomb=0;
	      while(countbomb<10) {
				int row = r.nextInt(ROWS);   
				int col = r.nextInt(COLS);
				if(!isMined[row][col]) {
					isMined[row][col]=true; 
					countbomb++;
				}
			}
	      /*isMined[0][0] = true;
	      isMined[5][2] = true;
	      isMined[9][5] = true;
	      isMined[6][7] = true;
	      isMined[8][2] = true;
	      isMined[2][4] = true;
	      isMined[5][7] = true;
	      isMined[7][7] = true;
	      isMined[3][6] = true;
	      isMined[4][8] = true;*/
	   }
}