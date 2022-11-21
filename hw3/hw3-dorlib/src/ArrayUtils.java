public class ArrayUtils {

	public static int[] shiftArrayCyclic(int[] array, int move, char direction) {
		int arrayLength = array.length;

		// if array has less than 2 items, nothing will change.
		// if move == 0 nothing will change.
		if (arrayLength < 2 || move == 0) {
			return array;
		}

		// if direction is not valid, we return the array as is.
		if (direction != 'R' && direction != 'L') {
			return array;
		}

		// handle negative number of moves.
		if (direction == 'R' && move < 0) {
			move = -1 * move;
			direction = 'L';
		} else if (direction == 'L' && move < 0) {
			move = -1 * move;
			direction = 'R';
		}

		int[] resultArr = new int[arrayLength];

		if (direction == 'R') {
			for (int i = 0; i < arrayLength; i++) {
				resultArr[(i + move) % (arrayLength)] = array[i];
			}
		} else {
			for (int i = 0; i < arrayLength; i++) {
				resultArr[Math.abs(arrayLength + (i -move) % arrayLength) % (arrayLength)] = array[i];
			}
		}

		return resultArr;
	}

	public static int findShortestPath(int[][] m, int i, int j) {
		if (m.length < 2)
			return 0;

		if (i == j)
			return 0;

		int[] visited = new int[m.length];
		int counter = 0;

		return findShortestPathRec(m, i, j, visited, counter);
	}

	private static int findShortestPathRec(int[][] m, int i, int j, int[] visited, int counter) {
		int k = 0;

		if (m[i][j] == 1) {
			return counter + 1;
		}

		int[] results = new int[m.length];

		for (k = 0; k < m.length; k++) {
			int result = 0;

			if (m[i][k] == 1) {
				if (visited[k] == 0) {
					visited[i] = 1;
					result = findShortestPathRec(m, k, j, visited, counter + 1);

					results[k] = result;
				}
			}
		}

		return findMinimum(results);
	}

	private static int findMinimum(int[] lst) {
		int smallest = -1;

		for (int j : lst) {
			if (j > 0) {
				smallest = j;
				break;
			}
		}

		for (int j : lst) {
			if (smallest > j && j > 0) {
				smallest = j;
			}
		}

		return smallest;
	}
}

