#  <SUMMARY>
#  J WorldProperty is a class that will be passed into the ValueIteration Algorithm/Function
#  Y because Java parameters in functions are pass by value, they are not deep copies therefore
#  * changes will not be reflected in parameters if they are modified, therefore we want to create
#  * an object to return all relevant information back to the main running program
#  *
#  * More information/variables can be put here depending on what kind of information
#  * is needed in an algorithm/depending on it's use
#  *
#  * Written By: Joshua Yuen, 2021

class WorldProperty:
    gridworld = [[]]

    terminationCondition = False
    delta = 0.0

    def __init__(self, gridworld, terminationCondition, delta):
        self.gridworld = gridworld
        self.terminationCondition = terminationCondition
        self.delta = delta