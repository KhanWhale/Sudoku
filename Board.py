class Tile:
    num = None
    candidates = []
    row = 1
    column = 5
    square = 3
    def __init__(self, val, board=None):
        self.board = board
        if val not in range(1,10):
            self.num = None
            self.candidates = list(range(1,10))
        else:
            self.num = val
    def __repr__(self):
        if self.board:
            return f"{self.board.name}: {str(self.num)}"
        else:
            return str(self.num)
    def update(self):
        if len(self.candidates) == 1:
            self.num = self.candidates[0]
            self.candidates = []
            self.row.in_row.append(self.num)
            self.column.in_col.append(self.num)
            self.square.in_square.append(self.num)
class Board:
    tiles = None
    rows =[]
    cols = []
    name = 'Aniruddh\'s Sudoku Board'
    def __init__(self, tiles):
        self.squares = [Square() for _ in range(9)]
        for i in range(0,len(tiles)):
            for j in range(0,len(tiles[i])):
                tiles[i][j] = Tile(tiles[i][j], self)
                if i < 3:
                    if j < 3:
                        self.squares[0].add(tiles[i][j])
                    elif j < 6:
                        self.squares[1].add(tiles[i][j])
                    else:
                        self.squares[2].add(tiles[i][j])
                elif i < 6:
                    if j < 3:
                        self.squares[3].add(tiles[i][j])
                    elif j < 6:
                        self.squares[4].add(tiles[i][j])
                    else:
                        self.squares[5].add(tiles[i][j])
                else:
                    if j < 3:
                        self.squares[6].add(tiles[i][j])
                    elif j < 6:
                        self.squares[7].add(tiles[i][j])
                    else:
                        self.squares[8].add(tiles[i][j])
        self.tiles = tiles
        for i in range(0, len(self.tiles)):
            r = Row(tiles[i])
            self.rows.append(r)
        self.cols = [Column([my_tiles[i][j] for i in range(len(my_tiles))])for j in range(len(my_tiles))]
    def __repr__(self):
        out = 'Aniruddh\'s Sudoku Board \n'
        for i in range(0, len(self.tiles)):
            for j in range(0, len(self.tiles[i])):
                if self.tiles[i][j].num:
                    out += str(self.tiles[i][j].num)
                    out += ' '
                else:
                    out += '_ '
            out += '\n'
        return out
    def check_complete(self):
        for r in self.tiles:
            for t in r:
                if t.num == None:
                    return False
        return True
class Row:
    tiles = []
    def __init__(self, tiles):
        self.in_row = []
        for tile in tiles:
            tile.row = self
            if tile.num:
                self.in_row.append(tile.num)
        self.tiles = tiles
    def __repr__(self):
        out = ''
        for tile in self.tiles:
            if tile.num:
                out += str(tile.num)
                out += ' '
            else:
                out += '_ '
        return out
class Column:
    tiles = []
    def __init__(self, tiles):
        self.in_col = []
        for tile in tiles:
            tile.column = self
            if tile.num:
                self.in_col.append(tile.num)
        self.tiles = tiles
    def __repr__(self):
        out = ''
        for tile in self.tiles:
            if tile.num:
                out += str(tile.num)
                out += '\n'
            else:
                out += '_\n'
        return out
class Square:
    def __init__(self):
        self.in_square = []
        self.tiles = []
    def add(self, tile):
        self.tiles.append(tile)
        tile.square = self
        if tile.num:
            self.in_square.append(tile.num)
    def __repr__(self):
        out = ''
        for i in range(len(self.tiles)):
            if self.tiles[i].num:
                out += str(self.tiles[i].num)
            else:
                out += '_'
            if (i+1) % 3 == 0:
                out += '\n'
            else:
                out += ' '
        return out

# 31 none
my_tiles = [
[1,0,0,0,0,0,6,0,5],
[0,0,2,0,1,7,0,0,9],
[4,0,0,2,0,0,7,0,8],
[7,0,0,4,0,0,1,0,3],
[8,0,4,0,0,0,0,0,0],
[0,2,5,0,0,9,0,0,0],
[0,6,0,0,3,0,2,0,0],
[0,0,1,5,0,0,3,0,0],
[0,0,0,6,9,8,0,7,0]]
my_board = Board(my_tiles)
