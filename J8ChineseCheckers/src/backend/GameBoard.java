package backend;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.*;

public class GameBoard {
	public static ArrayList <GameTile> board = new ArrayList<GameTile>();

	private static final BiFunction<Integer, Integer, Integer> plus = (a, b) -> {return a + b;};
	private static final BiFunction<Integer, Integer, Integer> minus = (a, b) -> {return a - b;};

	public static final int[] rowLengthsMid = {13,12,11,10,9};
	public static final int[] rowLengthsTri = {4,3,2,1};
	public static final int[] triIndex = {112,113,114,115,10,9,8,7};
	public static List<List<Integer>> diagRowsFromLeft = new ArrayList<List<Integer>>();

	public static void constructBoard(){

		diagRowsFromLeft.add(Arrays.asList(new Integer[] {99}));
		diagRowsFromLeft.add(Arrays.asList(new Integer[] {87,100}));
		diagRowsFromLeft.add(Arrays.asList(new Integer[] {76,88,101}));
		diagRowsFromLeft.add(Arrays.asList(new Integer[] {66,77,89,102}));
		diagRowsFromLeft.add(Arrays.asList(new Integer[] {11,24,36,47,57,67,78,90,103,112,116,119,121}));
		diagRowsFromLeft.add(Arrays.asList(new Integer[] {12,25,37,48,58,68,79,91,104,113,117,120}));
		diagRowsFromLeft.add(Arrays.asList(new Integer[] {13,26,38,49,59,69,80,92,105,114,118}));
		diagRowsFromLeft.add(Arrays.asList(new Integer[] {14,27,39,50,60,70,81,93,106,115}));
		diagRowsFromLeft.add(Arrays.asList(new Integer[] {15,28,40,51,61,71,82,94,107}));
		diagRowsFromLeft.add(Arrays.asList(new Integer[] {7,16,29,41,52,62,72,83,95,108}));
		diagRowsFromLeft.add(Arrays.asList(new Integer[] {4,8,17,30,42,53,63,73,84,96,109}));
		diagRowsFromLeft.add(Arrays.asList(new Integer[] {2,5,9,18,31,43,54,64,74,85,97,110}));
		diagRowsFromLeft.add(Arrays.asList(new Integer[] {1,3,6,10,19,32,44,55,65,75,86,98,111}));
		diagRowsFromLeft.add(Arrays.asList(new Integer[] {20,33,45,56}));
		diagRowsFromLeft.add(Arrays.asList(new Integer[] {21,34,46}));
		diagRowsFromLeft.add(Arrays.asList(new Integer[] {22,35}));
		diagRowsFromLeft.add(Arrays.asList(new Integer[] {23}));

		constructMid(11, rowLengthsMid, plus); 
		constructMid(111, rowLengthsMid, minus);

		constructTri(10, minus);
		constructTri(112, plus);

		//++ (57,58) (58,57) (59,58) (60,59) (61,60) (62,61) (63,62) (64,63) (65,64)
		board.add(new GameTile(57, null, 58, 66, 67, null));
		board.add(new GameTile(58, 57, 59, 67, 68, null));
		board.add(new GameTile(59, 58, 60, 68, 69, null));
		board.add(new GameTile(60, 59, 61, 69, 70, null));
		board.add(new GameTile(61, 60, 62, 70, 71, null));
		board.add(new GameTile(62, 61, 63, 71, 72, null));
		board.add(new GameTile(63, 62, 64, 72, 73, null));
		board.add(new GameTile(64, 63, 65, 73, 74, null));
		board.add(new GameTile(65, 64, null, 74, 75, null));
		try{
			Collections.sort(board);
			for (int i : triIndex){
				if (i > 100){
					board.get(i-1).setNextRow2(plus.apply(i, (-9)));
					board.get(i-1).setNextRow3(plus.apply(i, (-8)));
				}
				else{
					board.get(i-1).setNextRow2(minus.apply(i, (-9)));
					board.get(i-1).setNextRow3(minus.apply(i, (-8)));
				}
			}
			Players.initPlayers(6);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public static void constructMid(int start, int[] rowLengths, BiFunction<Integer, Integer, Integer> op){
		for (int i = 0; i < rowLengths.length-1; i++){
			constructRow(0, start, rowLengths[i], rowLengths[i+1], op);
			start = op.apply(start, rowLengths[i]);
		}
	}
	private static void constructRow(int rowPos, int boardPos, int rowLength, 
			int nextRowLength, BiFunction<Integer, Integer, Integer> op) {
		for(int i = rowLength; i > 0; i--){

			Integer sameRow1 = null;
			Integer sameRow2 = null;
			Integer nextRow1 = null;
			Integer nextRow2 = null;
			if (rowPos == 0){
				rowPos++;
			}
			else{
				sameRow1 = op.apply(boardPos, (-1));
				nextRow1 = op.apply(boardPos, nextRowLength);
			}
			if(i == 1){
				board.add(new GameTile(boardPos, sameRow1, sameRow2, nextRow1, nextRow2, null));
			}
			else{
				sameRow2 = op.apply((op.apply(boardPos, nextRowLength)), 1);
				board.add(new GameTile(boardPos, sameRow1, sameRow2, nextRow1, nextRow2, null));
			}
			boardPos = op.apply(boardPos, 1);
		}
	}

	public static void constructTri(int start, BiFunction<Integer, Integer, Integer> op){

		Integer index = op.apply(start, 9);
		Integer nextRow1 = op.apply(index, (-2));
		Integer nextRow2 = op.apply(index, (-1));
		board.add(new GameTile(index, null, null, nextRow1, nextRow2, null));
		constructMid(start, rowLengthsTri, op);
		//connect 10 9 8 7 to op -8 & -9
		//connect 112 113 114 115 to op -8 & -9
	}

	public static Set<Integer> getConnections(int index) {
		Set<Integer> neighbours = new HashSet<Integer>();
		if(board.get(index-1).getSameRow1() != null){
			synchronized(GameBoard.class){
			neighbours.add(board.get(index-1).getSameRow1());
			}
		}
		if(board.get(index-1).getSameRow2() != null){
			synchronized(GameBoard.class){
			neighbours.add(board.get(index-1).getSameRow2());
			}
		}
		if(board.get(index-1).getNextRow1() != null){
			synchronized(GameBoard.class){
			neighbours.add(board.get(index-1).getNextRow1());
			}
		}
		if(board.get(index-1).getNextRow2() != null){
			synchronized(GameBoard.class){
			neighbours.add(board.get(index-1).getNextRow2());
			}
		}
		if(board.get(index-1).getNextRow3() != null){
			synchronized(GameBoard.class){
			neighbours.add(board.get(index-1).getNextRow3());
			}
		}

		for(GameTile tile : board){
			if (tile.getSameRow1() != null && tile.getSameRow1().equals(index)){
				synchronized(GameBoard.class){
				neighbours.add(tile.getIndex());
				}
			}
			else if (tile.getSameRow2() != null && tile.getSameRow2().equals(index)){
				synchronized(GameBoard.class){
				neighbours.add(tile.getIndex());
				}
			}
			else if (tile.getNextRow1() != null && tile.getNextRow1().equals(index)){
				synchronized(GameBoard.class){
				neighbours.add(tile.getIndex());
				}
			}
			else if (tile.getNextRow2() != null && tile.getNextRow2().equals(index)){
				synchronized(GameBoard.class){
				neighbours.add(tile.getIndex());
				}
			}
			else if (tile.getNextRow3() != null && tile.getNextRow3().equals(index)){
				synchronized(GameBoard.class){
				neighbours.add(tile.getIndex());
				}
			}
		}
		return neighbours;
	}
	public static void makeMove(int oldPos, int newPos, int player){
		board.get(oldPos-1).setPlayer(0);
		board.get(newPos-1).setPlayer(player);
	}
	public static Set<Integer> getPossibleMoves(boolean firstStep, int start, Set<Integer> possibleMoves){
		for(Integer i : getConnections(start)){
			if(board.get(i-1).getPlayer() == 0){
				if(firstStep){
					possibleMoves.add(i);
				}
			}
			else if(i == start-1){
				for(Integer tile : getConnections(i)){
					if(tile == i-1 && board.get(tile-1).getPlayer() == 0 &&
							!possibleMoves.contains(board.get(tile-1).getIndex())){
						possibleMoves.add(tile);
						possibleMoves.addAll(getPossibleMoves(false, tile, possibleMoves));
					}
				}
			}
			else if(i == start+1){
				for(Integer tile : getConnections(i)){
					if(tile == i+1 && board.get(tile-1).getPlayer() == 0 &&
							!possibleMoves.contains(board.get(tile-1).getIndex())){
						possibleMoves.add(tile);
						possibleMoves.addAll(getPossibleMoves(false, tile, possibleMoves));
					}
				}
			}
			//Possibly change contains(start) to contains(i)
			else if(i > start){
				for(Integer tile : getConnections(i)){
					//System.out.println(start + "   " + i + "   " + tile);
					if(tile > i+1 && 
					   (board.get(start-1).getRow() == board.get(tile-1).getRow() ||
					   board.get(start-1).getRow() - board.get(tile-1).getRow() == 2 ||
					   board.get(start-1).getRow() - board.get(tile-1).getRow() == -2) && 
					   board.get(tile-1).getPlayer() == 0){
						//System.out.println(start + "   " + i + "   " + tile);
						if(!possibleMoves.contains(board.get(tile-1).getIndex())){
							possibleMoves.add(tile);
							possibleMoves.addAll(getPossibleMoves(false, tile, possibleMoves));
						}
						else{
							//possibleMoves.remove(board.get(tile-1).getIndex());
						}
					}
				}
			}
			else if(i < start){
				for(Integer tile : getConnections(i)){
					if(tile < i-1 && 
					   (board.get(start-1).getRow() == board.get(tile-1).getRow() ||
					   board.get(start-1).getRow() - board.get(tile-1).getRow() == 2 ||
					   board.get(start-1).getRow() - board.get(tile-1).getRow() == -2) && 
					   board.get(tile-1).getPlayer() == 0){
						//System.out.println(start + "   " + i + "   " + tile);
						if(!possibleMoves.contains(board.get(tile-1).getIndex())){
							possibleMoves.add(tile);
							possibleMoves.addAll(getPossibleMoves(false, tile, possibleMoves));
						}
						else{
							//possibleMoves.remove(board.get(tile-1).getIndex());
						}
					}
				}
			}
		}
		return possibleMoves;
	}
}
