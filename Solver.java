import java.util.*;

public class Solver {

    private Puzzle initialPuzzle;
    private int givenMatrixSize;
    private Puzzle requiredPuzzle;
    Stack<Puzzle> movesToSee;
    ArrayList<Puzzle> seenMoves;
    int solvedMatrixIndexNumber;

    /* Comparator for (id, value) map in descending value order */
    class ValueComparator implements Comparator<Integer> {
        Map<Integer, Integer> map;

        public ValueComparator(Map<Integer, Integer> map) {
            this.map = map;
        }

        public int compare(Integer a, Integer b) {
            return (map.get(a) >= map.get(b)) ? -1 : 1;
        }
    }

    public Solver(Puzzle puzzle) {
        initialPuzzle = puzzle;
        givenMatrixSize = puzzle.getMatrix().length;
        requiredPuzzle = new Puzzle(givenMatrixSize);
        movesToSee = new Stack<>();
        seenMoves = new ArrayList<>();
        solvedMatrixIndexNumber = 0;
    }

    public void run() {
        if (initialPuzzle.isCorrect()) {
            depthFirstSearch();
        }
        else {
            System.out.print("Wrong filled initial matrix");
        }
    }

    private boolean isSeen(List<Puzzle> list, Puzzle puzzle) {
        boolean result = false;
        for (int i = 0, size = list.size(); i < size; i++) {
            if (puzzle.equals(list.get(i))) {
                result = true;
                break;
            }
        }
        return result;
    }

    public int depthFirstSearch() {
        int i = 0;
        System.out.print(initialPuzzle.toString());
        movesToSee.add(initialPuzzle);
        while (!movesToSee.isEmpty()) {
            ArrayList<Puzzle> localMoveList = new ArrayList<>();

            Puzzle parentPuzzle = new Puzzle(movesToSee.pop().getMatrix());
            seenMoves.add(parentPuzzle);

            Map<Integer, Integer> childPuzzlesHeuristicMap = new HashMap<>();
            ValueComparator valueComparator = new ValueComparator(childPuzzlesHeuristicMap);
            TreeMap<Integer, Integer> childPuzzlesHeuristicSortedMap = new TreeMap<>(valueComparator);

            for (int j = 0, length = MoveDirection.values().length; j < length; j++) {
                Puzzle childPuzzle = new Puzzle(parentPuzzle.getMatrix());
                childPuzzle.moveTile(MoveDirection.values()[j]);

                if (childPuzzle.equals(requiredPuzzle)) {
                    System.out.println("Last move index number: " + i);
                    System.out.print(childPuzzle.toString());
                    return 0;
                }
                else if (!childPuzzle.equals(parentPuzzle)) {
                    localMoveList.add(childPuzzle);
                }
            }

            for (int j = 0; j < localMoveList.size(); j++){
                childPuzzlesHeuristicMap.put(j, localMoveList.get(j).manhattanDistance());
            }

            childPuzzlesHeuristicSortedMap.putAll(childPuzzlesHeuristicMap);

            for (Map.Entry<Integer, Integer> entry : childPuzzlesHeuristicSortedMap.entrySet()) {
                Puzzle puzzle = new Puzzle(localMoveList.get(entry.getKey()).getMatrix());
                if (!isSeen(seenMoves, puzzle)) {
                    movesToSee.add(puzzle);
                }
            }

            i++;
        }
        return 0;
    }
}