from Board import *

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

def update_tiles(board):
    for row in board.tiles:
        for tile in row:
            tile.update()
def update_board(board):
    update_candidates(board)
    update_tiles(board)
    return board
def solve(board):
    iters = 0
    while not board.check_complete() and iters < 25:
        update_board(board)
        iters+= 1
    return board
