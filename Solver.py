from Board import *

def check_row(row):
    def check(el):
        return el not in row.in_row
    for tile in row.tiles:
        if tile.candidates:
            tile.candidates = list(filter(check, tile.candidates))

def check_col(col):
    def check(el):
        return el not in col.in_col
    for tile in col.tiles:
        if tile.candidates:
            tile.candidates = list(filter(check, tile.candidates))
def check_square(square):
    def check(el):
        return el not in square.in_square
    for tile in square.tiles:
        if tile.candidates:
            tile.candidates = list(filter(check, tile.candidates))
def update_candidates(board):
    for row in board.rows:
        check_row(row)
    for col in board.cols:
        check_col(col)
    for square in board.squares:
        check_square(square)
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
