#  <SUMMARY>
#  J xyLocation is a class used to store content on an individual location on a board
#  Y more elements could be added to store or parse more information but for my case
#  * I am only going to store the terminal states and locations of walls
#  *
#  * After setting up the board entirely with non-terminal states, the values of walls
#  * and terminal states can be overlapped on top of the original states made by a
#  * nonterminal setup
#  *
#  * More variables for information about a location could be created here for use/implementation in later use
#  *
#  * Written By: Joshua Yuen, 2021

class xyLocation:
    column = 0
    row = 0
    reward = 0.0

    def __init__(self, col, row, reward):
        self.column = col
        self.row = row
        self.reward = reward
