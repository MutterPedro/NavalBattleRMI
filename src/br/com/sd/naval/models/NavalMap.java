package br.com.sd.naval.models;

public class NavalMap {
	
	private int numRow;
	private int numCol;
	private int[][] map;
	
	public NavalMap(int numRow, int numCol) {
		super();
		this.numRow = numRow;
		this.numCol = numCol;
		this.map = new int[numRow][numCol];
	}
	
	public int getNumRow() {
		return numRow;
	}
	public void setNumRow(int numRow) {
		this.numRow = numRow;
	}
	public int getNumCol() {
		return numCol;
	}
	public void setNumCol(int numCol) {
		this.numCol = numCol;
	}
	public int[][] getMap() {
		return map;
	}
	public void setMap(int[][] map) {
		this.map = map;
	}
	public void showMap(){
		
		char alfa = 'A';
		System.out.print("  ");
		for(int i=0;i<this.numRow;i++){
			System.out.print(" " + (i+1) + "  ");
			System.out.print(" ");
		}
		
		System.out.println();
		for(int i=0;i<this.numRow;i++){
			
			System.out.print(alfa);
			alfa++;
			for(int j=0;j<this.numCol;j++){
				System.out.print("|_");
				switch(this.map[i][j]){
					case 0:
						System.out.print("___");
						break;
					case 1:
						System.out.print("_N_");
						break;
					case 2:
						System.out.print("_*_");
						break;
					case 3:
						System.out.print("_O_");
						break;
				}
			}
			System.out.print("|");
			System.out.println();
		}
	}
	
	public void setMapPoint(int row, int col, int type){
		this.map[row][col] = type;
	}
}
