public enum MoveDirection {
    LEFT (0, -1),
    RIGHT (0, 1),
    UP (-1, 0),
    DOWN (1, 0);

    private final int iDisplacement;
    private final int jDisplacement;

    MoveDirection(int iDisplacement, int jDisplacement) {
        this.iDisplacement = iDisplacement;
        this.jDisplacement = jDisplacement;
    }

    public int getIDisplacement() {
        return iDisplacement;
    }

    public int getJDisplacement() {
        return jDisplacement;
    }
}
