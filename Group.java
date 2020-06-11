import java.util.ArrayList;
import java.util.HashMap;

public class Group {
    private int tileIndex = 0;
    private Tile[] _tiles;
    public HashMap<Integer, Integer> inGroup = new HashMap<>();
    private boolean _solved;
    public char _grpType;
    public Group(int dim, char type) {
        _grpType = type;
        _tiles = new Tile[dim];
        _solved = false;
    }
    public Group(Tile[] tiles, int dim, char type) {
        _grpType = type;
        _tiles = new Tile[dim];
        for (Tile t : tiles) {
            this.addTile(t);
        }
        _solved = false;
    }

    public boolean isValid() {
        HashMap<Integer, Integer> occurrences = new HashMap<>();
        if (_tiles != null){
            for (Tile t : _tiles) {
               if (t._num != 0) {
                   if (occurrences.containsKey(t._num)){
                      return false;
                   } else {
                       occurrences.put(t._num, 1);
                   }
               }
            }
        }
        return true;
    }

    public boolean checkSolved() {
       if (!isValid()) {
           return false;
       } else {
           HashMap<Integer, Integer> occurrences = new HashMap<>();
           if (_tiles != null){
               for (Tile t : _tiles) {
                   if (t._num != 0) {
                       return false;
                   } else if(occurrences.containsKey(t._num)) {
                       return false;
                   } else {
                       occurrences.put(t._num, 1);
                   }
               }
           }
           return occurrences.size() == 9;
       }
    }
    public Tile[] get_tiles() {
        return _tiles;
    }


    public void addTile(Tile tile) {
        if (tileIndex >= _tiles.length) {
            System.exit(1);
        } else {
            if (_grpType == 'c') {
                tile._column = (Board.Column) this;
            } else if (_grpType == 'r') {
                tile._row = (Board.Row) this;
            } else if (_grpType == 's') {
                tile._square = (Board.Square) this;
            }
            if (tile._num != 0) {
                this.inGroup.put(tile._num, tile._num);
            }
            _tiles[tileIndex] = tile;
            this.tileIndex += 1;
        }
    }
    public void setTile(Tile t, int index) {
        _tiles[index] = t;
    }
    public Tile getTile(int index) {
        return _tiles[index];
    }
}

