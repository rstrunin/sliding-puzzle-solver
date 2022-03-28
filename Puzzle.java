import java.util.HashMap;
import java.util.TreeSet;

public class Puzzle {

    /* Class field that presents NxN matrix of puzzle field with
    integer values from 0 to N-1, where 0 marks free cell */
    private int[][] matrix;

    /* Creates a target matrix of the given size */
    public Puzzle(int matrixSize) {
        this.matrix = new int[matrixSize][matrixSize];
        int valueToInsert = 1;

        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                this.matrix[i][j] = valueToInsert;
                valueToInsert++;
            }
        }

        matrix[matrixSize - 1][matrixSize - 1] = 0;
    }

    /* Creates a matrix based on the given array */
    public Puzzle(int[][] matrix) {
        try {
            int givenMatrixLength = matrix.length;
            this.matrix = new int[givenMatrixLength][givenMatrixLength];

            for (int i = 0; i < givenMatrixLength; i++) {

                if (matrix[i].length != matrix.length) {
                    throw new ArrayIndexOutOfBoundsException("Not a square NxN matrix");
                }

                for (int j = 0; j < givenMatrixLength; j++) {
                    this.matrix[i][j] = matrix[i][j];
                }
            }
        }
        catch (ArrayIndexOutOfBoundsException e) {
            System.out.print("Initialization error: " + e.getMessage());
        }
    }

    public int[][] getMatrix() {
        return this.matrix;
    }

    /* Checks if the matrix composed correctly */
    public boolean isCorrect() {
        TreeSet<Integer> ts = new TreeSet<>();
        for (int i = 0, parentArrayLength = this.matrix.length; i < parentArrayLength; i++) {
            for (int j = 0, childArrayLength = this.matrix[i].length; j < childArrayLength; j++) {
                if (parentArrayLength != childArrayLength) {
                    return false;
                }
                ts.add(this.matrix[i][j]);
            }
        }
        int nextNumber = 0;
        for (Integer value : ts) {
            if (value != nextNumber) {
                return false;
            }
            else {
                nextNumber++;
            }
        }
        return true;
    }

    /* Heuristic method that estimates how close given matrix to the goal matrix
    Since tiles in a matrix could be moved in only four directions, manhattan distance is the suitable heuristic */
    public int manhattanDistance() {
        int estimationResult = 0;

        for (int i = 0, matrixLength = this.matrix.length; i < matrixLength; i++) {
            for (int j = 0; j < matrixLength; j++) {
                HashMap<String, Integer> goalPosition = findGoalCell(matrix[i][j]);
                int goalIPosition = goalPosition.get("iPosition");
                int goalJPosition = goalPosition.get("jPosition");
                estimationResult += Math.abs(i - goalIPosition) + Math.abs(j - goalJPosition);
            }
        }

        return estimationResult;
    }

    /* Method that returns the position of a tile that it would fill in goal matrix */
    public HashMap<String, Integer> findGoalCell(int givenTile) {
        HashMap<String, Integer> goalPosition = new HashMap<>();
        int matrixLength = this.matrix.length;
        int iPosition = 0;
        int jPosition = 0;

        if (givenTile == 0) {
            iPosition = matrixLength - 1;
            jPosition = matrixLength - 1;
        }
        else {
            int currTile = 0;

            while (currTile + 1 != givenTile) {
                currTile++;
            }

            iPosition = currTile / matrixLength;
            jPosition = currTile % matrixLength;
        }

        goalPosition.put("iPosition", iPosition);
        goalPosition.put("jPosition", jPosition);
        return goalPosition;
    }

    /* Finds 0 in a matrix */
    public HashMap<String, Integer> findFreeCell() {
        HashMap<String, Integer> freeCellPosition = new HashMap<>();

        for (int i = 0; i < this.matrix.length; i++) {
            for (int j = 0; j < this.matrix.length; j++) {
                if (this.matrix[i][j] == 0) {
                    freeCellPosition.put("iPosition", i);
                    freeCellPosition.put("jPosition", j);
                }
            }
        }

        return freeCellPosition;
    }

    /* Tries to move free cell represented by 0 in one of four directions defined in enum file MoveDirections.java */
    public void moveTile (MoveDirection direction) {
        try {
            HashMap<String, Integer> freeCellPosition = findFreeCell();

            int initFreeCellIPosition = freeCellPosition.get("iPosition");
            int initFreeCellJPosition = freeCellPosition.get("jPosition");
            int iDisplacement = direction.getIDisplacement();
            int jDisplacement = direction.getJDisplacement();
            int finalFreeCellIPosition = initFreeCellIPosition + iDisplacement;
            int finalFreeCellJPosition = initFreeCellJPosition + jDisplacement;

            if (finalFreeCellIPosition >= 0 && finalFreeCellIPosition < matrix.length &&
                finalFreeCellJPosition >= 0 && finalFreeCellJPosition < matrix.length) {
                int tmp = this.matrix[finalFreeCellIPosition][finalFreeCellJPosition];
                this.matrix[finalFreeCellIPosition][finalFreeCellJPosition] = 0;
                this.matrix[initFreeCellIPosition][initFreeCellJPosition] = tmp;
            }
        }
        catch (ArrayIndexOutOfBoundsException e) {
            System.out.print("An error occurred while moving a tile: " + e.getMessage());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Puzzle puzzle = (Puzzle) o;

        int[][] matrix = puzzle.getMatrix();

        if (this.matrix.length != matrix.length) {
            return false;
        }

        int matchCounter = 0;
        for (int i = 0, size = this.matrix.length; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matchCounter += this.matrix[i][j] == matrix[i][j] ? 1 : 0;
            }
        }
        return matchCounter == Math.pow(this.matrix.length, 2);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("\n");
        for (int i = 0, length = this.matrix.length ; i < length; i++) {
            for (int j = 0; j < length; j++) {
                sb.append("|\t");
                sb.append(matrix[i][j]);
                sb.append("\t");
            }
            sb.append("|");
            sb.append("\n");
        }

        return sb.toString();
    }
}