package backend;

import java.util.Arrays;

public class GameTile implements Comparable<Object>{
	private Integer index;
	private Integer  sameRow1;
	private Integer  sameRow2;
	private Integer nextRow1;
	private Integer nextRow2;
	private Integer nextRow3;
	private int player;
	private int row;
	
	public GameTile(int i, Integer r1, Integer r2, Integer nx1, Integer nx2, Integer nx3){
		this.index = i;
		this.sameRow1 = r1;
		this.sameRow2 = r2;
		this.nextRow1 = nx1;
		this.nextRow2 = nx2;
		this.nextRow3 = nx3;
		this.setPlayer(0);
		this.setRow();
	}
	public GameTile(int i){
		this.index = i;
	}
	
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public Integer getSameRow1() {
		return sameRow1;
	}
	public void setSameRow1(Integer sameRow) {
		this.sameRow1 = sameRow;
	}
	public Integer getSameRow2() {
		return sameRow2;
	}
	public void setSameRow2(Integer sameRow) {
		this.sameRow2 = sameRow;
	}
	public Integer getNextRow1() {
		return nextRow1;
	}
	public void setNextRow1(Integer nextRow) {
		this.nextRow1 = nextRow;
	}
	public Integer getNextRow2() {
		return nextRow2;
	}
	public void setNextRow2(Integer nextRow) {
		this.nextRow2 = nextRow;
	}
	public Integer getNextRow3() {
		return nextRow3;
	}
	public void setNextRow3(Integer nextRow) {
		this.nextRow3 = nextRow;
	}
	public int getPlayer() {
		return player;
	}
	public void setPlayer(int player) {
		this.player = player;
	}
	@Override
	public int compareTo(Object tile2) {
		if(this.index < ((GameTile)tile2).getIndex()){
			return -1;
		}
		else if(this.index > ((GameTile)tile2).getIndex()){
			return 1;
		}
		else{
			return 0;
		}
	}
	public int getRow() {
		return row;
	}
	public void setRow() {
		for(int i = 0; i < GameBoard.diagRowsFromLeft.size(); i++){
			if (GameBoard.diagRowsFromLeft.get(i).contains(this.index)){
				this.row = i;
				break;
			}
		}
	}
}
