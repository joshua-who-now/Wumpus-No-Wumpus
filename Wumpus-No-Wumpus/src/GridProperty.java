
/* <SUMMARY>
 J GridProperty is a class that is used to store information about a single tile
 Y on the actual board that we will be using for gridworld. These are the properties
 * of the actual tile whether it is a terminal state or a wall and the reward value
 * of each tile.
 *
 * Meanwhile, it will also hold it's valueIteration and utility action for use in the
 * value iteration algorithm.
 *
 * More values for the state of the grid can be added here.
 *
 * Written By: Joshua Yuen, 4/3/2020
 */

public class GridProperty {
    float reward;
    boolean isWall;
    boolean isTerminal;

    float valueIteration;
    char utilAction;

    public GridProperty(float reward, float valueIteration){
        this.reward = reward;
        this.valueIteration = valueIteration;
    }
}