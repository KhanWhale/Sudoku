//import java.util.ArrayList;
//
//public class Tile {
//    private int _row;
//    private int _col;
//    private int _square;
//    private int _val;
//    private ArrayList<Integer> candidates = new ArrayList<>();
//    public Tile(int row, int col, int dim) {
//        _col = col;
//        _row = row;
//        int sqSize = (int) Math.sqrt(dim);
//        int sqRow = (row % sqSize) * sqSize;
//        int sqCol = (col % sqSize);
//        _square = sqRow + sqCol;
//        _val = 0;
//    }
//
//
//    public int getCol() {
//        return _col;
//    }
//    public int getRow() {
//        return _row;
//    }
//    public int getSquare() {
//        return _square;
//    }
//    public int getVal() {
//        return _val;
//    }
//    public void setVal(int val) {
//        _val = val;
//    }
//
//}

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Tile {
    int _num = 0;
    HashSet<Integer> candidates = new HashSet<>();
    Board.Row _row;
    Board.Column _column;
    Board.Square _square;
    Board _board;
    public Tile(int num, Board b) {
        _board = b;
        if (num == 0) {
            for (int i = 1; i < 10; i += 1) {
                candidates.add(i);
            }
        }
        this._num = num;
    }
    public void resetCandidates() {
        if (_num == 0) {
            for (int i = 1; i < 10; i += 1) {
                candidates.add(i);
            }
        }
    }
    public void update() {
        if (candidates != null && candidates.size() == 1) {
            this._num = candidates.iterator().next();
            addToGroup();
        } else if (candidates != null) {
            for (int candidate : candidates) {
                if (_board.uniqueInGroup(_row).contains(candidate) || _board.uniqueInGroup(_column).contains(candidate) || _board.uniqueInGroup(_square).contains(candidate)) {
                    _num = candidate;
                    addToGroup();
                    return;
                }
            }
        }
    }

    public void addToGroup() {
        candidates = new HashSet<>();
        this._row.inGroup.put(_num, _num);
        this._column.inGroup.put(_num, _num);
        this._square.inGroup.put(_num, _num);
        _board.updateCandidates();
        _board.updated = true;
    }
}