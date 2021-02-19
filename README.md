# Wumpus-No-Wumpus
Implementing Markov's Decision Process into a generic gridworld environment similar to Hunt the Wumpus, a text-based gridworld game from the 1970s.

# Description
Originally a homework assignment for CS411 (Artificial Intelligence I) at UIC.

I took what I had previously submitted as a Java project for my Artificial Intelligence class at UIC and reformatted the project into the Python Programming language as well.
This project takes a textfile input containing the description of a Wumpus-like World and factors used for the Markov Decision Process algorithm (size of world, terminal states, walls, reward values, transition probabilities, discount rate, epsilon). After taking in valid input, the program will run and display the value iterations at each step until a final policy is reached. Once the final policy is reached, the program will also show the optimal route one must take (in terms of the cardinal directions [N,E,S,W]) at any starting position to maximize reward.

# Implementation Details: 
In this generic grid environment, each tile had the property of having a normal tile, being a terminal state, being a wall, or being a pitfall.
Each each tile (except for the walls) has a "reward" value that is used in the calculation of the 

The actions are defined in probabilistic movements in which the outcomes of your movements are partially random in this case, the movements allowed are move forward, move right, or move left.

By default the movement probabilities are as follows:

• Move Forward .8 or 80%

• Move Right: .1 or 10%

• Move Left: .1 or 10%


The input should be given in the form of a textfile, similar to the examples provided in the Github Repository containing the following information used for the properties of the world and the algorithm:
• size of world
• terminal states
• walls
• reward values
• transition probabilities
• discount rate
• epsilon


The resulting output should contain the following:
• A record of the values of each tile of the world during each iteration
• A final policy containing the termination values of the MDP's evaluation.
• A board containing the movements/path that one would for the highest reward.


# Closing Regards
Implementation is my own work, however, references to the AIMA database can be found here: http://aima.cs.berkeley.edu/ with reference to the textbook "Artificial Intelligence: A Modern Approach 3rd Edition."

All initial assignment submissions were checked for plagerism using MOSS (Measure of Software Similarity).
All credit is due where necessary.
