from WorldProperty import WorldProperty
import copy

#  <SUMMARY>
#  J ValueIterationAlgorithm is a class created for the sole purpose of a single function
#  Y to hold the algorithm that will be used for that purpose.
#  *
#  * The Bellman equation is what will be used and is the fundamental basis for solving MDP's
#  * Furthermore, we will be using the Bellman update or the iteration step until an equilibrium
#  * is reached and the program terminates.
#  *
#  * Written By: Joshua Yuen, 2021
#  */

class ValueIterationAlgorithm:
    def ValueIterationFunction(self, world, gamma, epsilon, transitionProbabilities, actions, rows, cols):
        gridworld = world.gridworld
        rows = rows
        columns = cols

        forward = transitionProbabilities[0]
        left = transitionProbabilities[1]
        right = transitionProbabilities[2]

        delta = world.delta
        convergence = False

        transitionProperty = []
        newWorld = copy.deepcopy(gridworld)

        for i in range(rows):
            for j in range(columns):
                # up transition
                if(i+1 >= rows or gridworld[i+1][j].isWall):
                    if(j-1 < 0 or gridworld[i][j-1].isWall):
                        if(j+1 >= columns or gridworld[i][j+1].isWall):
                            transitionProperty.append(gridworld[i][j].valueIteration)
                        else:
                            transitionProperty.append(
                                (forward+left) * gridworld[i][j].valueIteration 
                                        + right * gridworld[i][j+1].valueIteration
                            )
                    else:
                        if(j+1>= columns or gridworld[i][j+1].isWall):
                            transitionProperty.append(
                                (forward+right) * gridworld[i][j].valueIteration
                                        + left * gridworld[i][j-1].valueIteration
                            )
                        else:
                            transitionProperty.append(
                                forward * gridworld[i][j].valueIteration
                                        + (left) * gridworld[i][j-1].valueIteration
                                        + (right) * gridworld[i][j+1].valueIteration
                            )
                else:
                    if(j-1 < 0 or gridworld[i][j-1].isWall):
                        if(j+1 >= columns or gridworld[i][j+1].isWall):
                            transitionProperty.append(
                                forward * gridworld[i+1][j].valueIteration
                                        + (left + right) * gridworld[i][j].valueIteration
                            )
                        else:
                            transitionProperty.append(
                                forward * gridworld[i+1][j].valueIteration 
                                        + left * gridworld[i][j].valueIteration
                                        + right * gridworld[i][j+1].valueIteration
                            )
                    else:
                        if(j+1>= columns or gridworld[i][j+1].isWall):
                            transitionProperty.append(
                                (forward) * gridworld[i+1][j].valueIteration
                                        + left * gridworld[i][j-1].valueIteration
                                        + right * gridworld[i][j].valueIteration
                            )
                        else:
                            transitionProperty.append(
                                forward * gridworld[i+1][j].valueIteration
                                        + (left) * gridworld[i][j-1].valueIteration
                                        + (right) * gridworld[i][j+1].valueIteration
                            )
                
                # right transition
                if(j+1 >= columns or gridworld[i][j+1].isWall):
                    if(i+1 >= rows or gridworld[i+1][j].isWall):
                        if(i-1 < 0 or gridworld[i-1][j].isWall):
                            transitionProperty.append(gridworld[i][j].valueIteration)
                        else:
                            transitionProperty.append(
                                (forward+left) * gridworld[i][j].valueIteration 
                                        + right * gridworld[i-1][j].valueIteration
                            )
                    else:
                        if(i-1 < 0 or gridworld[i-1][j].isWall):
                            transitionProperty.append(
                                (forward+right) * gridworld[i][j].valueIteration
                                        + left * gridworld[i+1][j].valueIteration
                            )
                        else:
                            transitionProperty.append(
                                forward * gridworld[i][j].valueIteration
                                        + (left) * gridworld[i+1][j].valueIteration
                                        + (right) * gridworld[i-1][j].valueIteration
                            )
                else:
                    if(i+1 >= rows or gridworld[i+1][j].isWall):
                        if(i-1 < 0 or gridworld[i-1][j].isWall):
                            transitionProperty.append(
                                forward * gridworld[i][j+1].valueIteration
                                        + (left + right) * gridworld[i][j].valueIteration
                            )
                        else:
                            transitionProperty.append(
                                forward * gridworld[i][j+1].valueIteration 
                                        + left * gridworld[i][j].valueIteration
                                        + right * gridworld[i-1][j].valueIteration
                            )
                    else:
                        if(i-1 < 0 or gridworld[i-1][j].isWall):
                            transitionProperty.append(
                                (forward) * gridworld[i][j+1].valueIteration
                                        + left * gridworld[i+1][j].valueIteration
                                        + right * gridworld[i][j].valueIteration
                            )
                        else:
                            transitionProperty.append(
                                forward * gridworld[i][j+1].valueIteration
                                        + (left) * gridworld[i+1][j].valueIteration
                                        + (right) * gridworld[i-1][j].valueIteration
                            )
                
                # down transition
                if(i-1 < 0 or gridworld[i-1][j].isWall):
                    if(j+1 >= columns or gridworld[i][j+1].isWall):
                        if(j-1 < 0 or gridworld[i][j-1].isWall):
                            transitionProperty.append(gridworld[i][j].valueIteration)
                        else:
                            transitionProperty.append(
                                (forward+left) * gridworld[i][j].valueIteration 
                                        + right * gridworld[i][j-1].valueIteration
                            )
                    else:
                        if(j-1 < 0 or gridworld[i][j-1].isWall):
                            transitionProperty.append(
                                (forward+right) * gridworld[i][j].valueIteration
                                        + left * gridworld[i][j+1].valueIteration
                            )
                        else:
                            transitionProperty.append(
                                forward * gridworld[i][j].valueIteration
                                        + (left) * gridworld[i][j+1].valueIteration
                                        + (right) * gridworld[i][j-1].valueIteration
                            )
                else:
                    if(j+1 >= columns or gridworld[i][j+1].isWall):
                        if(j-1 < 0 or gridworld[i][j-1].isWall):
                            transitionProperty.append(
                                forward * gridworld[i-1][j].valueIteration
                                        + (left + right) * gridworld[i][j].valueIteration
                            )
                        else:
                            transitionProperty.append(
                                forward * gridworld[i-1][j].valueIteration 
                                        + left * gridworld[i][j].valueIteration
                                        + right * gridworld[i][j-1].valueIteration
                            )
                    else:
                        if(j-1 < 0 or gridworld[i][j-1].isWall):
                            transitionProperty.append(
                                (forward) * gridworld[i-1][j].valueIteration
                                        + left * gridworld[i][j+1].valueIteration
                                        + right * gridworld[i][j].valueIteration
                            )
                        else:
                            transitionProperty.append(
                                forward * gridworld[i-1][j].valueIteration
                                        + (left) * gridworld[i][j+1].valueIteration
                                        + (right) * gridworld[i][j-1].valueIteration
                            )

                # left transition
                if(j-1 < 0 or gridworld[i][j-1].isWall):
                    if(i-1 < 0 or gridworld[i-1][j].isWall):
                        if(i+1 >= rows or gridworld[i+1][j].isWall):
                            transitionProperty.append(gridworld[i][j].valueIteration)
                        else:
                            transitionProperty.append(
                                (forward+left) * gridworld[i][j].valueIteration 
                                        + right * gridworld[i+1][j].valueIteration
                            )
                    else:
                        if(i+1 >= rows or gridworld[i+1][j].isWall):
                            transitionProperty.append(
                                (forward+right) * gridworld[i][j].valueIteration
                                        + left * gridworld[i-1][j].valueIteration
                            )
                        else:
                            transitionProperty.append(
                                forward * gridworld[i][j].valueIteration
                                        + (left) * gridworld[i-1][j].valueIteration
                                        + (right) * gridworld[i+1][j].valueIteration
                            )
                else:
                    if(i-1 < 0 or gridworld[i-1][j].isWall):
                        if(i+1 >= rows or gridworld[i+1][j].isWall):
                            transitionProperty.append(
                                forward * gridworld[i][j-1].valueIteration
                                        + (left + right) * gridworld[i][j].valueIteration
                            )
                        else:
                            transitionProperty.append(
                                forward * gridworld[i][j-1].valueIteration 
                                        + left * gridworld[i][j].valueIteration
                                        + right * gridworld[i+1][j].valueIteration
                            )
                    else:
                        if(i+1 >= rows or gridworld[i+1][j].isWall):
                            transitionProperty.append(
                                (forward) * gridworld[i][j-1].valueIteration
                                        + left * gridworld[i-1][j].valueIteration
                                        + right * gridworld[i][j].valueIteration
                            )
                        else:
                            transitionProperty.append(
                                forward * gridworld[i][j-1].valueIteration
                                        + (left) * gridworld[i-1][j].valueIteration
                                        + (right) * gridworld[i+1][j].valueIteration
                            )
                
                originalStateValue = gridworld[i][j].valueIteration
                
                # get max from four utility functions
                max_util = max(transitionProperty)

                # finding the position of the max utility iteration value
                # known to search by Up, Right, Down, Left [N,E,S,W] respectively
                for k in range(len(transitionProperty)):
                    if(transitionProperty[k] == max_util):
                        # if position at gridworld [i,j] is a wall or terminal state, - or T respectively
                        if(gridworld[i][j].isWall or gridworld[i][j].isTerminal):
                            if(gridworld[i][j].isWall):
                                newWorld[i][j].utilAction = '-'
                            else:
                                newWorld[i][j].utilAction = 'T'
                        # else store utility action [N,E,S,W] respectively
                        else:
                            newWorld[i][j].utilAction = actions[k]
                        break
                
                # if position is terminal or a wall, store iteration value as rewared or 0 respectively
                if(gridworld[i][j].isTerminal):
                    newWorld[i][j].valueIteration = gridworld[i][j].reward
                elif(gridworld[i][j].isWall):
                    gridworld[i][j].valueIteration = 0
                # otherwise store new iteration value into the newWorld gridworld which will be sent back to the main program
                else:
                    newWorld[i][j].valueIteration = gridworld[i][j].reward + gamma * max_util
                
                # new state value is the iteration value of the tile for it's next iteration
                newStateValue = newWorld[i][j].valueIteration

                # our new delta value to compare to the previous iteration's delta value
                tempDelta = abs(newStateValue - originalStateValue)

                # delta is changed if the absolute value of the new delta value is larger than the previous iteration's delta value
                if(tempDelta > world.delta):
                    world.delta = tempDelta

                # if the convergence boolean is false, run these statements to see if we've reached equilibrium
                if not convergence:
                    # if delta is less than or equal to our equilibrium state
                    if(world.delta <= ((epsilon * (1-gamma))/gamma)):
                        # convergence is now equal to true and will not catch anymore as equilibrium will remain true to tell our main program to stop
                        convergence = True
                
                # clear iteration values in the transition property list
                transitionProperty.clear()

        # return a new WorldProperty containing the information of the updated gridworld, convergence boolean, and an updated delta value as it is passed on per iteration of the value iteration algorithm
        return WorldProperty(newWorld, convergence, delta)
