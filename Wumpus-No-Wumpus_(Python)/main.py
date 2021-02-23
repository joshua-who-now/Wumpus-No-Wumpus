from ValueIterationAlgorithm import ValueIterationAlgorithm
from WorldProperty import WorldProperty
from GridProperty import GridProperty
from xyLocation import xyLocation

#  <SUMMARY>
#  J This is the main executable of the program which will be run in order to simulate
#  Y the reading in and running of the Markov Decision Process (or MDP) and will also
#  * use the value iteration algorithm to solve a grid world that will be read in and
#  * populated from a text file
#  *
#  * Written By: Joshua Yuen, 2021

def __main__():
    # variables to store items read in from textfile
    columns = 0
    rows = 0

    # lists used to store walls and terminal states of gridworld
    walls = []
    terminalStates = []

    # nonterminal reward value
    nonTerminalReward = 0

    # list of transition probabilities 
    transitionProbabilities = []

    # discount rate, also known as gamme and epsilon, used for calculation of utility-maximizing action calculation
    discountRate = 0
    epsilon = 0

    print('Enter Filepath of MDP Input File: ')

    filepath = input()

    file = open(filepath,'r')

    lines = file.readlines()
    
    # size of gridworld
    columns = int(lines[2].split()[2])
    rows = int(lines[2].split()[3])

    # location of walls
    wall_line = lines[6].split()
    for i in range(2, len(wall_line)-1, 3):
        walls.append(xyLocation(int(wall_line[i]), int(wall_line[i+1]), 0))

    # terminal states(row,column,reward)
    terminal_states_line = lines[10].split()
    for i in range(2, len(terminal_states_line)-1, 4):
        terminalStates.append(xyLocation(int(terminal_states_line[i]), int(terminal_states_line[i+1]), float(terminal_states_line[i+2])))

    # reward in non-terminal states
    nonTerminalReward = float(lines[14].split()[2])

    # transitional probabilities
    transitionProbabilities.append(float(lines[18].split()[2]))
    transitionProbabilities.append(float(lines[18].split()[3]))
    transitionProbabilities.append(float(lines[18].split()[4]))

    # discount rate
    discountRate = float(lines[20].split()[2])

    # epsilon
    epsilon = float(lines[22].split()[2])

    # Declaration of 2D gridworld
    gridworld = []
    
    for i in range(rows):
        gridworld.append([])
        for j in range(columns):
            pos = GridProperty(nonTerminalReward, 0)
            gridworld[i].append(pos)
    
    # setup walls
    for wall in walls:
        gridworld[wall.row-1][wall.column-1].isWall = True
    
    # setup terminal states
    for terminal in terminalStates:
        gridworld[terminal.row-1][terminal.column-1].reward = terminal.reward
        gridworld[terminal.row-1][terminal.column-1].isTerminal = True
    
    # World Property contains the gridworld, delta, and terminal condition in order to find the terminal iteration
    worldInfo = WorldProperty(gridworld, False, 0)

    # Available actions we can make
    actions = ['N','E','S','W']

    # Our algorithm for Value Iteration
    via = ValueIterationAlgorithm()

    # iteration value
    iteration = 0

    while(True):
        # print current iteration
        print('iteration ' + str(iteration))

        # print board values
        for i in range(rows-1, -1, -1):
            for j in range(columns):
                if(worldInfo.gridworld[i][j].isWall):
                    print('---------  ', end='')
                else:
                    print(str(round(worldInfo.gridworld[i][j].valueIteration,9)) + '  ', end='')
            print()
            print()

        # increment iteration
        iteration += 1

        # run value iteration function
        worldInfo = via.ValueIterationFunction(worldInfo, discountRate, epsilon, transitionProbabilities, actions, rows, columns)

        # if terminal condition that gets sent back from the value iteration function is true
        # equilibrium has been met inside the algorithm
        if(worldInfo.terminationCondition):
            break
        
    # print out final state of the board after equilibrium condition has been met
    print("Final Value after Convergence")
    for i in range(rows-1, -1, -1):
        for j in range(columns):
            if(worldInfo.gridworld[i][j].isWall):
                print('---------  ', end='')
                continue
            print(str(round(worldInfo.gridworld[i][j].valueIteration, 9)) + '  ', end='')
        print()
        print()

    # Utility-Maximizing Action Sequence
    print('Final Policy')
    for i in range(rows-1, -1, -1):
        for j in range(columns):
            print(worldInfo.gridworld[i][j].utilAction + '  ', end='') 
        print()
        print()


__main__()