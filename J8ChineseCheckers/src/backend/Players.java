package backend;

public class Players {
	private static final int[] player1Start = {1,2,3,4,5,6,7,8,9,10};
	private static final int[] player2Start = {112,113,114,115,116,117,118,119,120,121};
	private static final int[] player3Start = {11,12,13,14,24,25,26,36,37,47};
	private static final int[] player4Start = {75,85,86,96,97,98,108,109,110,111};
	private static final int[] player5Start = {20,21,22,23,33,34,35,45,46,56};
	private static final int[] player6Start = {66,76,77,87,88,89,99,100,101,102};

	public static void initPlayers(int playerCount){
		for (int i : player1Start){
			GameBoard.board.get(i-1).setPlayer(1);
		}
		if (playerCount != 3){
			for (int i : player2Start){
				GameBoard.board.get(i-1).setPlayer(2);
			}
		}
		if (playerCount > 2){
			for (int i : player3Start){
				GameBoard.board.get(i-1).setPlayer(3);
			}
			if (playerCount != 3){
				for (int i : player4Start){
					GameBoard.board.get(i-1).setPlayer(4);
				}
			}
			if(playerCount > 4 || playerCount == 3){
				for (int i : player5Start){
					GameBoard.board.get(i-1).setPlayer(5);
				}
			}
			if(playerCount > 4){
				for (int i : player6Start){
					GameBoard.board.get(i-1).setPlayer(6);
				}
			}
		}

	}
}
