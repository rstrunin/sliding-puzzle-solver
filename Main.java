public class Main {

    public static void main(String[] args) {
        // Test matrices
        // {{1, 3, 4}, {2, 0, 5}, {7, 8, 6}}
        // {{1, 2, 0}, {3, 5, 6}, {7, 8, 4}}
        // {{1, 2, 3}, {0, 5, 6}, {7, 8, 4}}
        // {{1, 2, 3}, {7, 0, 4}, {8, 6, 5}}
        // {{1, 2, 3}, {4, 0, 6}, {7, 5, 8}}
        // {{1, 0, 2}, {4, 5, 3}, {7, 8, 6}}
        // {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 0, 15}}
        // {{1, 2, 3, 4}, {6, 5, 8, 7}, {11, 13, 9, 12}, {10, 14, 15, 0}}

        Puzzle puzzle = new Puzzle(new int[][] {{1, 2, 3, 4}, {6, 5, 8, 7}, {11, 13, 9, 12}, {10, 14, 15, 0}});
        Solver solver = new Solver(puzzle);
        solver.run();
    }
}
