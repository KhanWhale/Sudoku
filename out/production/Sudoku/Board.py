
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
            self.row.in_group.append(self.num)
            self.column.in_group.append(self.num)
            self.square.in_group.append(self.num)
            update_candidates(self.board)
            self.board.updated = True
        else:
            for cand in self.candidates:
                if cand in unique_in_group(self.row) or cand in unique_in_group(self.column) or cand in unique_in_group(self.square):
                     self.num = cand
                     self.candidates = []
                     self.row.in_group.append(self.num)
                     self.column.in_group.append(self.num)
                     self.square.in_group.append(self.num)
                     update_candidates(self.board)
                     self.board.updated = True
                     return

class Board:
    tiles = None
    rows =[]
    cols = []
    name = 'Aniruddh\'s Sudoku Board'
    updated = True
    incomplete = True
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
        self.cols = [Column([tiles[i][j] for i in range(len(tiles))])for j in range(len(tiles))]
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
                    self.incomplete = True
                    return True
        self.incomplete = False
        return False
class Row:
    tiles = []
    def __init__(self, tiles):
        self.in_group = []
        for tile in tiles:
            tile.row = self
            if tile.num:
                self.in_group.append(tile.num)
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
        self.in_group = []
        for tile in tiles:
            tile.column = self
            if tile.num:
                self.in_group.append(tile.num)
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
        self.in_group = []
        self.tiles = []
    def add(self, tile):
        self.tiles.append(tile)
        tile.square = self
        if tile.num:
            self.in_group.append(tile.num)
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
def check_group(group):
    def check(el):
        return el not in group.in_group
    for tile in group.tiles:
        if tile.candidates:
            tile.candidates = list(filter(check, tile.candidates))
def update_candidates(board):
    for row in board.rows:
        check_group(row)
    for col in board.cols:
        check_group(col)
    for square in board.squares:
        check_group(square)
def unique_in_group(group):
    nested = [tile.candidates for tile in group.tiles if not tile.num]
    all_candidates = [item for sublist in nested for item in sublist]
    all_candidates = convert_to_dict(all_candidates)
    unique_candidates = []
    for cand, freq in all_candidates.items():
        if freq == 1:
            unique_candidates.append(cand)
    return unique_candidates
def convert_to_dict(my_list):
    freq = {}
    for items in my_list:
        freq[items] = my_list.count(items)
    return freq
def common_candidates(group):
    return [tile for tile in group.tiles if tile.num == None]
def update_tiles(board):
    for row in board.tiles:
        for tile in row:
            tile.update()
def update_board(board):
    board.updated = False
    update_candidates(board)
    update_tiles(board)
    board.check_complete()
    return board
def solve(board):
    iters = 0
    while board.incomplete and board.updated and iters < 20:
        update_board(board)
        iters+= 1
    if board.incomplete:
        print(f'Sorry, I couldn\'t solve the board in {iters} tries. Here is the partially solved version:')
    else:
        print(f'I solved the board in {iters} tries. Here it is:')
    return board
def play():
    my_tiles = []
    row = []
    while True:
        num = input("Row: ")
        for n in num:
            row.append(int(n))
        my_tiles.append(row)
        row = []
        if len(my_tiles) == 9:
            my_board = Board(my_tiles)
            return my_board
my_tiles = [
[0,0,0,0,0,9,0,0,3],
[3,0,0,0,0,0,0,2,0],
[6,0,2,0,1,0,0,0,0],
[0,0,6,0,0,7,3,8,9],
[9,7,0,4,0,0,0,1,2],
[0,0,0,2,0,0,0,0,7],
[7,2,0,0,0,0,0,6,1],
[0,0,0,0,0,0,0,0,8],
[0,0,8,7,0,0,0,9,0]]
my_board = Board(my_tiles)
