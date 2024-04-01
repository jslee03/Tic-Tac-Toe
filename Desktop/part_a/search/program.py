# COMP30024 Artificial Intelligence, Semester 1 2024
# Project Part A: Single Player Tetress

from .core import PlayerColor, Coord, PlaceAction, Direction
from .utils import render_board
import math


def search(
    board: dict[Coord, PlayerColor], 
    target: Coord
) -> list[PlaceAction] | None:
    """
    This is the entry point for your submission. You should modify this
    function to solve the search problem discussed in the Part A specification.
    See `core.py` for information on the types being used here.

    Parameters:
        `board`: a dictionary representing the initial board state, mapping
            coordinates to "player colours". The keys are `Coord` instances,
            and the values are `PlayerColor` instances.  
        `target`: the target BLUE coordinate to remove from the board.
    
    Returns:
        A list of "place actions" as PlaceAction instances, or `None` if no
        solution is possible.
    """

    # The render_board() function is handy for debugging. It will print out a
    # board state in a human-readable format. If your terminal supports ANSI
    # codes, set the `ansi` flag to True to print a colour-coded version!
    print(render_board(board, target, ansi=False))

    # Search through state to locate all red nodes and then find which node
    # is the closest to target to thus make it our origin
    red_coords = []
    
    for coord, playerColor in board.items():
        if playerColor == PlayerColor.RED:
            red_coords.append(coord)
   
    min_dist = h(red_coords[0], target)
    origin = red_coords[0]
    for r in red_coords:
        dist = h(r, target)
        if dist < min_dist:
            min_dist = dist
            origin = r
    print(origin)

    #visited = {}

    # Search through all adjacent directional nodes (up, down, left, right) from node with shortest distance
    while (origin.r != target.r or origin.c != target.c):
        MAX_BLOCKS = 4
        block_num = 0
        curr_block = origin
        # actions = []
        
        

        while (block_num < MAX_BLOCKS):
            #f = h(curr_block, target) + g (curr_block, origin)
            min_dist = h(origin, target)
            for direction in Direction:
                curr_block += direction
                if h(curr_block, target) < min_dist: #and is_valid(curr_block)
                    min_dist = h(curr_block, target)
                    min_block = curr_block
                curr_block = origin
            block_num += 1
            # actions.append(min_block)
            # append to visited dict

        




    # Here we're returning "hardcoded" actions as an example of the expected
    # output format. Of course, you should instead return the result of your
    # search algorithm. Remember: if no solution is possible for a given input,
    # return `None` instead of a list.
    return [
        PlaceAction(Coord(2, 5), Coord(2, 6), Coord(3, 6), Coord(3, 7)),
        PlaceAction(Coord(1, 8), Coord(2, 8), Coord(3, 8), Coord(4, 8)),
        PlaceAction(Coord(5, 8), Coord(6, 8), Coord(7, 8), Coord(8, 8)),
    ]

def h(n, target):
    x1 = n.c
    y1 = n.r
    x2 = target.c
    y2 = target.r

    dist = math.sqrt((x2-x1) * (x2-x1) + (y2-y1) * (y2-y1))

    return dist


def g(n, origin):
    x1 = n.c
    y1 = n.r
    x2 = origin.c
    y2 = origin.r

    dist = math.sqrt((x2-x1) * (x2-x1) + (y2-y1) * (y2-y1))

    return dist

def valid(n):
    pass