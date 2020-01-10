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
    while not board.check_complete() and iters < 50:
        update_board(board)
        iters+= 1
    return board
