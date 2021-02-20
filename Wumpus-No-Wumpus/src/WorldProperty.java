import java.util.ArrayList;

/* <SUMMARY>
 J WorldProperty is a class that will be passed into the ValueIteration Algorithm/Function
 Y because Java parameters in functions are pass by value, they are not deep copies therefore
 * changes will not be reflected in parameters if they are modified, therefore we want to create
 * an object to return all relevant information back to the main running program
 *
 * More information/variables can be put here depending on what kind of information
 * is needed in an algorithm/depending on it's use
 *
 * Written By: Joshua Yuen, 4/3/2020
 */

public class WorldProperty {
    ArrayList<ArrayList<GridProperty>> gridworld;

    boolean terminationCondition;
    float delta;

    public WorldProperty (ArrayList<ArrayList<GridProperty>> gridworld, boolean terminationCondition, float delta){
        this.gridworld = gridworld;
        this.terminationCondition = terminationCondition;
        this.delta = delta;
    }
}
