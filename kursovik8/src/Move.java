public class Move {

    public int row1;
    public int col1;

    public int row2;
    public int col2;

    public char val1;
    public char val2;

    public boolean firstMove;

    public int increment;


    Move() {

        row1 = -1;
        row2 = -1;
        col1 = -1;
        col2 = -1;

        val1 = '#';
        val2 = '#';

        firstMove = true;

        increment = 0;
    }

    public void move(int row, int col, char val) {
        if (firstMove)
        {
            firstMove(row, col, val);
            firstMove = false;
        }
        else
        {
            secondMove(row, col, val);
            firstMove = true;
        }
    }

    private void firstMove(int row, int col, char val) {
        row1 = row;
        col1 = col;
        val1 = val;
    }

    private void secondMove(int row, int col, char val) {
        row2 = row;
        col2 = col;
        val2 = val;
    }

    public int deltaX()
    {
        return Math.abs(row2 - row1);
    }

    public int deltaY()
    {
        return Math.abs(col2 - col1);
    }

    public int diffX()
    {
        return row2 - row1;
    }

    public int diffY()
    {
        return col2 - col1;
    }

    public int delta()
    {
        return deltaX() + deltaY();
    }

    public void unmove() {
        firstMove = true;
    }

    public void clearmove() {
        increment = 0;
    }

    public void increase() {
        increment++;
    }

    public char charify(int arg) {
        char a = 'A';

        return (char) (a + arg - 1);
    }
}
