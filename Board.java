//public class Board {
//    Tile[][] _tiles;
//    Row[] _rows;
//    Column[] _cols;
//    Square[] _squares;
//    String name = "Aniruddh's Sudoku Board";
//    boolean updated = true;
//    boolean incomplete = true;
//
//    public Board(Tile[][] tiles, int dim) {
//        _rows = new Row[dim];
//        _cols = new Column[dim];
//        _squares = new Square[dim];
//        for(int i = 0; i < tiles.length; i += 1) {
//            for(int j = 0; j < tiles[i].length; j += 1) {
//                tiles[i][j] = new Tile(tiles[i][j]);
////                need to add to a square
//            }
//        }
//        _tiles = tiles;
//        for (int i = 0; i < _tiles.length; i += 1) {
//            Row r = new Row(_tiles[i]);
//            _rows[i] = r;
//        }
//    }
//}

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Board {
    class Row extends Group {
        Row (Tile[] tiles, int dim) {
            super(tiles, dim, 'r');
        }
    }

    class Column extends Group {
        Column (Tile[] tiles, int dim) {
            super(tiles, dim, 'c');
        }
    }

    class Square extends Group {
        Square (int dim) {
            super(dim, 's');
        }
    }
    Tile[][] _tiles = null;
    Row[] _rows;
    Column[] _cols;
    Square[] _squares;
    String name = "Aniruddh's Sudoku Board";
    boolean updated = true;
    boolean incomplete = true;
    public Board(int[][] tiles, int dim) {
        _squares = new Square[dim];
        _rows = new Row[dim];
        _cols = new Column[dim];
        for (int i = 0; i < dim; i += 1) {
            _squares[i] = new Square(dim);
        }
        _tiles = new Tile[tiles.length][];
        for (int i = 0; i < tiles.length; i += 1) {
            _tiles[i] = new Tile[tiles[i].length];
            for (int j = 0; j < tiles[i].length; j += 1) {
                _tiles[i][j] = new Tile(tiles[i][j], this);
                int sqSize = (int) Math.sqrt(dim);
                int sq = (sqSize * (i / sqSize)) + (j / sqSize);
                _squares[sq].addTile(_tiles[i][j]);
                _tiles[i][j]._square = _squares[sq];
            }
            Row myRow = new Row(_tiles[i], dim);
            _rows[i] = myRow;
        }
        for(int j = 0; j < tiles[0].length; j += 1) {
            Tile[] myCol = new Tile[dim];
            for(int i = 0; i < tiles.length; i += 1) {
                myCol[i] = _tiles[i][j];
            }
            _cols[j] = new Column(myCol, dim);
        }
    }
    public boolean isComplete() {
        for (Tile[] row : _tiles) {
            for (Tile t : row) {
                if (t._num == 0) {
                    incomplete = true;
                    return false;
                }
            }
        }
        incomplete = false;
        return true;
    }

    public void checkGroup(Group g) {
        for (Tile t : g.get_tiles()) {
            if (t.candidates.size() > 0 && t._num == 0) {
                t.candidates.removeIf(el -> (g.inGroup.containsKey(el)));
                int dummy = 0;
            }
        }
    }
    public void updateCandidates() {
        for (Row r : _rows) {
            checkGroup(r);
        }
        for (Column c : _cols) {
            checkGroup(c);
        }
        for (Square s : _squares) {
            checkGroup(s);
        }
        int dummy = 0;

    }
    public ArrayList<Integer> uniqueInGroup(Group g) {
        ArrayList<HashSet<Integer>> nested = new ArrayList();
        for (Tile t : g.get_tiles()) {
            if (t._num == 0) {
                nested.add(t.candidates);
            }
        }
        ArrayList<Integer> allCandidates = new ArrayList<>();
        for (HashSet<Integer> nest : nested) {
            allCandidates.addAll(nest);
        }
        HashMap<Integer, Integer> candidateCounts = convertToMap(allCandidates);
        ArrayList<Integer> uniqueCandidates = new ArrayList<>();
        for (Integer candidate : candidateCounts.keySet()) {
            if (candidateCounts.get(candidate) == 1) {
                uniqueCandidates.add(candidate);
            }
        }
        return uniqueCandidates;
    }
    public HashMap<Integer, Integer> convertToMap(ArrayList<Integer> elements) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (Integer element : elements) {
            if(map.containsKey(element)) {
                map.put(element, map.get(element) + 1);
            } else {
                map.put(element, 1);
            }
        }
        return map;
    }

    public void updateTiles() {
        for (Tile[] row : _tiles) {
            for (Tile tile : row) {
                tile.update();
            }
        }
    }
    public Board updateBoard() {
        updated = false;
        for (Tile[] row : _tiles) {
            for (Tile tile : row) {
                tile.resetCandidates();
            }
        }
        updateCandidates();
        updateTiles();
        dump();
        isComplete();
        return this;
    }
    public void dump() {
        String out = "Aniruddh's Sudoku Board \n";
        for (int i = 0; i < _tiles.length; i ++) {
            for (int j = 0; j < _tiles[i].length; j++) {
                if (_tiles[i][j]._num != 0) {
                    out += Integer.toString(_tiles[i][j]._num);
                    out += " ";
                } else {
                    out += "_ ";
                }
            }
            out += "\n";
        }
        System.out.println(out);
    }
    public void solve() {
        int iters = 0;
//        "The smart approach"
        while (this.incomplete &&  this.updated && iters< 25) {
            updateBoard();
            iters += 1;
        }
//        Backtracking - brute force

    }

    public void backtrack() {
        for (Tile[] row : _tiles) {
            for (Tile t : row) {
                if (t._num == 0 && t.candidates != null && t.candidates.size() > 0) {
                    for (int candidate : t.candidates) {
                        t._num = candidate;
                        t.addToGroup();
                    }
                }
            }
        }
    }
}