import java.util.ArrayList;
import java.util.Collections;

/* <SUMMARY>
 J ValueIterationAlgorithm is a class created for the sole purpose of a single function
 Y to hold the algorithm that will be used for that purpose.
 *
 * The Bellman equation is what will be used and is the fundamental basis for solving MDP's
 * Furthermore, we will be using the Bellman update or the iteration step until an equilibrium
 * is reached and the program terminates.
 *
 * Written By: Joshua Yuen, 4/3/2020
 */


public class ValueIterationAlgorithm {
    public WorldProperty ValueIterationFunction(WorldProperty world, float gamma, float epsilon, ArrayList<Float> transitionProbabilities, ArrayList<Character> actions){
        ArrayList<ArrayList<GridProperty>> gridworld = world.gridworld;
        int rows = gridworld.size();
        int columns = gridworld.get(0).size();

        float forward = transitionProbabilities.get(0);
        float left = transitionProbabilities.get(1);
        float right = transitionProbabilities.get(2);

        float delta = world.delta;
        boolean convergence = false;

        ArrayList<Float> transitionProperty = new ArrayList<>();
        ArrayList<ArrayList<GridProperty>> newWorld = new ArrayList<>(gridworld);

        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++){

                //up transition
                //if going up brings you to a wall or edge of gridworld
                if(i+1 >= rows || gridworld.get(i+1).get(j).isWall){
                    //return stay in origin position for an up move

                    //if left is wall or edge of gridworld
                    if(j-1 < 0 || gridworld.get(i).get(j-1).isWall){
                        //return to origin position for a left move

                        //if right is a wall or edge of gridworld
                        if(j+1 >= columns || gridworld.get(i).get(j+1).isWall){
                            //return to origin position for a right move
                            transitionProperty.add(gridworld.get(i).get(j).valueIteration);
                        }
                        //if right is not a wall or edge of gridworld
                        else{
                            transitionProperty.add(
                                    (forward+left) * gridworld.get(i).get(j).valueIteration
                                            + right * gridworld.get(i).get(j+1).valueIteration
                            );
                        }
                    }
                    //if left is not a wall or edge of gridworld
                    else{
                        //if right is a wall or edge of gridworld
                        if(j+1 >= columns || gridworld.get(i).get(j+1).isWall){
                            //return to origin position for a right move
                            transitionProperty.add(
                                    (forward+right) * gridworld.get(i).get(j).valueIteration
                                            + left * gridworld.get(i).get(j-1).valueIteration
                            );
                        }
                        //if right is not a wall or edge of gridworld
                        else{
                            transitionProperty.add(
                                    forward * gridworld.get(i).get(j).valueIteration
                                            + left * gridworld.get(i).get(j-1).valueIteration
                                            + right * gridworld.get(i).get(j+1).valueIteration
                            );
                        }
                    }
                }
                //if up is not a wall or edge of gridworld
                else{
                    //if left is wall or edge of gridworld
                    if(j-1 < 0 || gridworld.get(i).get(j-1).isWall){
                        //return to origin position for a left move

                        //if right is a wall or edge of gridworld
                        if(j+1 >= columns || gridworld.get(i).get(j+1).isWall){
                            //return to origin position for a right move
                            transitionProperty.add(
                                    forward * gridworld.get(i+1).get(j).valueIteration
                                            +(left+right) * gridworld.get(i).get(j).valueIteration
                            );
                        }
                        //if right is not a wall or edge of gridworld
                        else{
                            transitionProperty.add(
                                    forward * gridworld.get(i+1).get(j).valueIteration
                                            + left * gridworld.get(i).get(j).valueIteration
                                            + right * gridworld.get(i).get(j+1).valueIteration
                            );
                        }
                    }
                    //if left is not a wall or edge of gridworld
                    else{
                        //if right is a wall or edge of gridworld
                        if(j+1 >= columns || gridworld.get(i).get(j+1).isWall){
                            //return to origin position for a right move
                            transitionProperty.add(
                                    forward * gridworld.get(i+1).get(j).valueIteration
                                            + left * gridworld.get(i).get(j-1).valueIteration
                                            + right * gridworld.get(i).get(j).valueIteration
                            );
                        }
                        //if right is not a wall or edge of gridworld
                        else{
                            transitionProperty.add(
                                    forward * gridworld.get(i+1).get(j).valueIteration
                                            + left * gridworld.get(i).get(j-1).valueIteration
                                            + right * gridworld.get(i).get(j+1).valueIteration
                            );
                        }
                    }
                }

                //right transition
                //if going right brings you to a wall or edge of gridworld
                if(j+1 >= columns || gridworld.get(i).get(j+1).isWall){
                    //return stay in origin position for an right move

                    //if up is wall or edge of gridworld
                    if(i+1 >= rows || gridworld.get(i+1).get(j).isWall){
                        //return to origin position for a left move

                        //if down is a wall or edge of gridworld
                        if(i-1 < 0 || gridworld.get(i-1).get(j).isWall){
                            //return to origin position for a right move
                            transitionProperty.add(gridworld.get(i).get(j).valueIteration);
                        }
                        //if down is not a wall or edge of gridworld
                        else{
                            transitionProperty.add(
                                    (forward+left) * gridworld.get(i).get(j).valueIteration
                                            + right * gridworld.get(i-1).get(j).valueIteration
                            );
                        }
                    }
                    //if up is not a wall or edge of gridworld
                    else{
                        //if down is a wall or edge of gridworld
                        if(i-1 < 0 || gridworld.get(i-1).get(j).isWall){
                            //return to origin position for a right move
                            transitionProperty.add(
                                    (forward+right) * gridworld.get(i).get(j).valueIteration
                                            + left * gridworld.get(i+1).get(j).valueIteration
                            );
                        }
                        //if down is not a wall or edge of gridworld
                        else{
                            transitionProperty.add(
                                    forward * gridworld.get(i).get(j).valueIteration
                                            + left * gridworld.get(i+1).get(j).valueIteration
                                            + right * gridworld.get(i-1).get(j).valueIteration
                            );
                        }
                    }
                }
                //if right is not a wall or edge of gridworld
                else{
                    //if up is wall or edge of gridworld
                    if(i+1 >= rows || gridworld.get(i+1).get(j).isWall){
                        //return to origin position for a left move

                        //if down is a wall or edge of gridworld
                        if(i-1 < 0 || gridworld.get(i-1).get(j).isWall){
                            //return to origin position for a right move
                            transitionProperty.add(
                                    forward * gridworld.get(i).get(j+1).valueIteration
                                            +(left+right) * gridworld.get(i).get(j).valueIteration
                            );
                        }
                        //if right is not a wall or edge of gridworld
                        else{
                            transitionProperty.add(
                                    forward * gridworld.get(i).get(j+1).valueIteration
                                            + left * gridworld.get(i).get(j).valueIteration
                                            + right * gridworld.get(i-1).get(j).valueIteration
                            );
                        }
                    }
                    //if up is not a wall or edge of gridworld
                    else{
                        //if down is a wall or edge of gridworld
                        if(i-1 < 0 || gridworld.get(i-1).get(j).isWall){
                            //return to origin position for a right move
                            transitionProperty.add(
                                    forward * gridworld.get(i).get(j+1).valueIteration
                                            + left * gridworld.get(i+1).get(j).valueIteration
                                            + right * gridworld.get(i).get(j).valueIteration
                            );
                        }
                        //if down is not a wall or edge of gridworld
                        else{
                            transitionProperty.add(
                                    forward * gridworld.get(i).get(j+1).valueIteration
                                            + left * gridworld.get(i+1).get(j).valueIteration
                                            + right * gridworld.get(i-1).get(j).valueIteration
                            );
                        }
                    }
                }

                //down transition
                //if going down brings you to a wall or edge of gridworld
                if(i-1 < 0 || gridworld.get(i-1).get(j).isWall){
                    //return stay in origin position for an down move

                    //if left is wall or edge of gridworld
                    if(j-1 < 0 || gridworld.get(i).get(j-1).isWall){
                        //return to origin position for a left move

                        //if right is a wall or edge of gridworld
                        if(j+1 >= columns || gridworld.get(i).get(j+1).isWall){
                            //return to origin position for a right move
                            transitionProperty.add(gridworld.get(i).get(j).valueIteration);
                        }
                        //if right is not a wall or edge of gridworld
                        else{
                            transitionProperty.add(
                                    (forward+left) * gridworld.get(i).get(j).valueIteration
                                            + right * gridworld.get(i).get(j+1).valueIteration
                            );
                        }
                    }
                    //if left is not a wall or edge of gridworld
                    else{
                        //if right is a wall or edge of gridworld
                        if(j+1 >= columns || gridworld.get(i).get(j+1).isWall){
                            //return to origin position for a right move
                            transitionProperty.add(
                                    (forward+right) * gridworld.get(i).get(j).valueIteration
                                            + left * gridworld.get(i).get(j-1).valueIteration
                            );
                        }
                        //if right is not a wall or edge of gridworld
                        else{
                            transitionProperty.add(
                                    forward * gridworld.get(i).get(j).valueIteration
                                            + left * gridworld.get(i).get(j-1).valueIteration
                                            + right * gridworld.get(i).get(j+1).valueIteration
                            );
                        }
                    }
                }
                //if down is not a wall or edge of gridworld
                else{
                    //if left is wall or edge of gridworld
                    if(j-1 < 0 || gridworld.get(i).get(j-1).isWall){
                        //return to origin position for a left move

                        //if right is a wall or edge of gridworld
                        if(j+1 >= columns || gridworld.get(i).get(j+1).isWall){
                            //return to origin position for a right move
                            transitionProperty.add(
                                    forward * gridworld.get(i-1).get(j).valueIteration
                                            +(left+right) * gridworld.get(i).get(j).valueIteration
                            );
                        }
                        //if right is not a wall or edge of gridworld
                        else{
                            transitionProperty.add(
                                    forward * gridworld.get(i-1).get(j).valueIteration
                                            + left * gridworld.get(i).get(j).valueIteration
                                            + right * gridworld.get(i).get(j+1).valueIteration
                            );
                        }
                    }
                    //if left is not a wall or edge of gridworld
                    else{
                        //if right is a wall or edge of gridworld
                        if(j+1 >= columns || gridworld.get(i).get(j+1).isWall){
                            //return to origin position for a right move
                            transitionProperty.add(
                                    forward * gridworld.get(i-1).get(j).valueIteration
                                            + left * gridworld.get(i).get(j-1).valueIteration
                                            + right * gridworld.get(i).get(j).valueIteration
                            );
                        }
                        //if right is not a wall or edge of gridworld
                        else{
                            transitionProperty.add(
                                    forward * gridworld.get(i-1).get(j).valueIteration
                                            + left * gridworld.get(i).get(j-1).valueIteration
                                            + right * gridworld.get(i).get(j+1).valueIteration
                            );
                        }
                    }
                }

                //left transition
                //if going left brings you to a wall or edge of gridworld
                if(j-1 < 0 || gridworld.get(i).get(j-1).isWall){

                    //if down is wall or edge of gridworld
                    if(i-1 < 0 || gridworld.get(i-1).get(j).isWall){

                        //if up is a wall or edge of gridworld
                        if(i+1 >= rows || gridworld.get(i+1).get(j).isWall){
                            //return to origin position for a right move
                            transitionProperty.add(gridworld.get(i).get(j).valueIteration);
                        }
                        //if up is not a wall or edge of gridworld
                        else{
                            transitionProperty.add(
                                    (forward+left) * gridworld.get(i).get(j).valueIteration
                                            + right * gridworld.get(i+1).get(j).valueIteration
                            );
                        }
                    }
                    //if down is not a wall or edge of gridworld
                    else{
                        //if up is a wall or edge of gridworld
                        if(i+1 >= rows || gridworld.get(i+1).get(j).isWall){
                            //return to origin position for a right move
                            transitionProperty.add(
                                    (forward+right) * gridworld.get(i).get(j).valueIteration
                                            + left * gridworld.get(i-1).get(j).valueIteration
                            );
                        }
                        //if up is not a wall or edge of gridworld
                        else{
                            transitionProperty.add(
                                    forward * gridworld.get(i).get(j).valueIteration
                                            + left * gridworld.get(i-1).get(j).valueIteration
                                            + right * gridworld.get(i+1).get(j).valueIteration
                            );
                        }
                    }
                }
                //if left is not a wall or edge of gridworld
                else{
                    //if down is wall or edge of gridworld
                    if(i-1 < 0 || gridworld.get(i-1).get(j).isWall){
                        //return to origin position for a left move

                        //if up is a wall or edge of gridworld
                        if(i+1 >= rows || gridworld.get(i+1).get(j).isWall){
                            //return to origin position for a right move
                            transitionProperty.add(
                                    forward * gridworld.get(i).get(j-1).valueIteration
                                            +(left+right) * gridworld.get(i).get(j).valueIteration
                            );
                        }
                        //if up is not a wall or edge of gridworld
                        else{
                            transitionProperty.add(
                                    forward * gridworld.get(i).get(j-1).valueIteration
                                            + left * gridworld.get(i).get(j).valueIteration
                                            + right * gridworld.get(i+1).get(j).valueIteration
                            );
                        }
                    }
                    //if down is not a wall or edge of gridworld
                    else{
                        //if up is a wall or edge of gridworld
                        if(i+1 >= rows || gridworld.get(i+1).get(j).isWall){
                            //return to origin position for a right move
                            transitionProperty.add(
                                    forward * gridworld.get(i).get(j-1).valueIteration
                                            + left * gridworld.get(i-1).get(j).valueIteration
                                            + right * gridworld.get(i).get(j).valueIteration
                            );
                        }
                        //if down is not a wall or edge of gridworld
                        else{
                            transitionProperty.add(
                                    forward * gridworld.get(i).get(j-1).valueIteration
                                            + left * gridworld.get(i-1).get(j).valueIteration
                                            + right * gridworld.get(i+1).get(j).valueIteration
                            );
                        }
                    }
                }

                //System.out.println(i + " " + j + "  :  ");
                //System.out.println(transitionProperty);
                float originalStateValue = gridworld.get(i).get(j).valueIteration;

                //get max from the 4 utility actions
                float max = Collections.max(transitionProperty);

                //finding the position of the max utility iteration value
                //known to search by Up, Right, Down, Left [N,E,S,W] respectively
                for(int k = 0; k < transitionProperty.size(); k++){
                    if(transitionProperty.get(k) == max){
                        //if the position at gridworld [i,j] is a wall or a terminal state, - or T respectively
                        if(gridworld.get(i).get(j).isWall || gridworld.get(i).get(j).isTerminal){
                            if(gridworld.get(i).get(j).isWall){
                                newWorld.get(i).get(j).utilAction = '-';
                            }
                            else{
                                newWorld.get(i).get(j).utilAction = 'T';
                            }
                        }
                        //else store utility action [N,E,S,W] respectively
                        else{
                            newWorld.get(i).get(j).utilAction = actions.get(k);
                        }
                        break;
                    }
                }

                //if position is terminal or is a wall, store iteration value as reward or 0 respectively
                if(gridworld.get(i).get(j).isTerminal){
                    newWorld.get(i).get(j).valueIteration = gridworld.get(i).get(j).reward;
                }
                else if(gridworld.get(i).get(j).isWall){
                    newWorld.get(i).get(j).valueIteration = 0;
                }
                //otherwise, store new iteration value into the newWorld gridworld which will be sent back
                //to the main program
                else{
                    newWorld.get(i).get(j).valueIteration = gridworld.get(i).get(j).reward + gamma * max;
                }

                //new state value is the iteration value of the tile for it's
                float newStateValue = newWorld.get(i).get(j).valueIteration;

                //our new delta value to compare to the previous iteration's delta value
                float tempDelta = Math.abs(newStateValue - originalStateValue);

                //delta is changed if the absolute value of the new delta value
                //is larger than the previous iteration's delta value
                if(tempDelta > world.delta){
                    world.delta = tempDelta;
                }

                //if the convergence boolean is false, run these statements to see if we've reached equilibrium
                if(!convergence){
                    //if delta is less than or equal to our equilibrium state
                    if(world.delta <= ((epsilon*(1-gamma))/gamma)){
                        //convergence is now equal to true and will not catch anymore as equilibrium will remain true
                        //telling our main program to stop
                        convergence = true;
                    }
                }
                //clear the value iteration values in the transition property arraylist
                transitionProperty.clear();
            }
        }
        //return a new WorldProperty containing the information of the updated gridworld, the convergence boolean,
        //and an updated delta value as it is passed on per iteration of the value iteration algorithm
        return new WorldProperty(newWorld, convergence, delta);
    }
}
