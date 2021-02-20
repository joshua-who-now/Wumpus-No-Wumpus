import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;

/* <SUMMARY>
 J This is the main executable of the program which will be run in order to simulate
 Y the reading in and running of the Markov Decision Process (or MDP) and will also
 * use the value iteration algorithm to solve a grid world that will be read in and
 * populated from a text file
 *
 * Written By: Joshua Yuen, 4/3/2020
 */
public class Main {
    public static void main(String[] args){

        //variables used to store items read in from text file using buffered reader
        int columns = 0; //columns of gridworld
        int rows = 0; //rows of gridworld

        //arrayLists used to store walls and terminal states of gridworld
        ArrayList<xyLocation> walls = new ArrayList<>();
        ArrayList<xyLocation> terminalStates = new ArrayList<>();

        //nonterminal reward value
        float nonTerminalReward = 0;

        //probabilities per transition
        ArrayList<Float> transitionProbabilities = new ArrayList<>();

        //discount rate also known as gamma and epsilon, used for calculation of utility-maximizing action calculation
        float discountRate = 0;
        float epsilon = 0;

        //filePath used to store the path entered by user as a string
        String filePath;

        //first approach using arguments during a java compile using arguments
        //eg. "java Main C:\Users\UserName\Documents\Document_File_Path
        //filePath = args[0]; //take in input from 'java Main arg[0]'

        //second approach prompts user after running 'java Main'
        //allows them to input a filepath after compilation of the java program
        System.out.print("Enter Filepath of MDP Input File: ");
        Scanner inputScanner = new Scanner(System.in);
        filePath = inputScanner.nextLine();

        //string tokenizer to parse line by line for content
        StringTokenizer strtok;

        //Initialize buffered reader to read in input form text file
        BufferedReader br = null;
        try{
            //read in line by line assigning each line to a String 'line'
            br = new BufferedReader(new FileReader(filePath));
            String line;
            //while end of file isn't reached
            while((line = br.readLine()) != null){
                //switch statements to check for input of
                switch(line) {
                    case "#size of the gridworld":
                        //buffered reading to skip unnecessary lines
                        br.readLine();
                        line = br.readLine();

                        //tokenize line to get data needed to store
                        strtok = new StringTokenizer(line, " ");
                        strtok.nextToken();
                        strtok.nextToken();
                        //assigns columns and rows from input of txt file
                        columns = Integer.parseInt(strtok.nextToken());
                        rows = Integer.parseInt(strtok.nextToken());

                        break;

                    case "#list of location of walls":
                        //buffered reading to skip unnecessary lines
                        br.readLine();
                        line = br.readLine();

                        //tokenize line to get data needed to store
                        strtok = new StringTokenizer(line, " ");
                        strtok.nextToken();
                        strtok.nextToken();
                        while(strtok.hasMoreTokens()){
                            String val = strtok.nextToken();
                            if(val.equals(",")){
                                //finds token isn't an integer
                                continue;
                            }
                            else{
                                //stores wall location into an ArrayList
                                xyLocation xy = new xyLocation(Integer.parseInt(val), Integer.parseInt(strtok.nextToken()), 0);
                                walls.add(xy);
                            }
                        }
                        break;

                    case "#list of terminal states (row,column,reward)":
                        //buffered reading to skip unnecessary lines
                        br.readLine();
                        line = br.readLine();

                        //tokenize line to get data needed to store
                        strtok = new StringTokenizer(line, " ");
                        strtok.nextToken();
                        strtok.nextToken();
                        while(strtok.hasMoreTokens()) {
                            String val = strtok.nextToken();
                            if (val.equals(",")) {
                                //finds token isn't an integer
                                continue;
                            } else {
                                //find and store column value, row value, and terminal reward into ArrayList of terminal states
                                xyLocation xy = new xyLocation(Integer.parseInt(val),
                                        Integer.parseInt(strtok.nextToken()),
                                        Integer.parseInt(strtok.nextToken()));
                                terminalStates.add(xy);
                            }
                        }
                        break;

                    case "#reward in non-terminal states":
                        //buffered reading to skip unnecessary lines
                        br.readLine();
                        line = br.readLine();

                        //tokenize line to get data needed to store
                        strtok = new StringTokenizer(line, " ");
                        strtok.nextToken();
                        strtok.nextToken();

                        //assign non-terminal reward
                        nonTerminalReward = Float.parseFloat(strtok.nextToken());
                        break;

                    case "#transition probabilities":
                        //buffered reading to skip unnecessary lines
                        br.readLine();
                        line = br.readLine();

                        //tokenize line to get data needed to store
                        strtok = new StringTokenizer(line, " ");
                        //skip forward after ':'
                        strtok.nextToken();
                        strtok.nextToken();

                        //get the transition probabilities and store them into the array
                        transitionProbabilities.add(Float.parseFloat(strtok.nextToken()));
                        transitionProbabilities.add(Float.parseFloat(strtok.nextToken()));
                        transitionProbabilities.add(Float.parseFloat(strtok.nextToken()));

                        //buffered reading to skip unnecessary lines
                        br.readLine();
                        line = br.readLine();

                        //tokenize line to get data needed to store
                        strtok = new StringTokenizer(line, " ");
                        strtok.nextToken(); strtok.nextToken();

                        //assign discount rate value
                        discountRate = Float.parseFloat(strtok.nextToken());

                        //buffered reading to skip unnecessary lines
                        br.readLine();
                        line = br.readLine();

                        //tokenize line to get data needed to store
                        strtok = new StringTokenizer(line, " ");
                        strtok.nextToken(); strtok.nextToken();

                        //assign epsilon value
                        epsilon = Float.parseFloat(strtok.nextToken());

                        break;
                }
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        finally{
            try{
                if(br != null){
                    br.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }

        //Print Statements to see stored values
/*
        System.out.println("Columns: " + columns + "\nRows: "+rows);
        System.out.println();
        System.out.println("Walls at: ");
        for(int i = 0; i < walls.size(); i++){
            System.out.println(walls.get(i).column + " " + walls.get(i).row);
        }
        System.out.println();
        System.out.println("Terminal States with Rewards: ");
        for(int i = 0; i < terminalStates.size(); i++){
            System.out.println("Location: " + terminalStates.get(i).column + " " + terminalStates.get(i).row + "| Reward: " + terminalStates.get(i).reward);
        }
        System.out.println();
        System.out.println("Non-Terminal Reward: " + nonTerminalReward);
        System.out.println();
        System.out.println("Transition Probabilities: ");
        for(int i = 0; i < transitionProbabilities.size(); i++){
            System.out.print(transitionProbabilities.get(i) + " ");
        }
        System.out.println();

        System.out.println("Discount Rate: " + discountRate);
        System.out.println();
        System.out.println("Epsilon: " + epsilon);
        System.out.println();
*/
        //Declaration of 2D ArrayList gridworld
        ArrayList<ArrayList<GridProperty>> gridworld = new ArrayList<>();

        //Initialization of 2D ArrayList
        for(int i = 0; i < rows; i++){
            gridworld.add(new ArrayList<>());
        }

        //populate gridworld with non-terminal rewards
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++){
                GridProperty pos = new GridProperty(nonTerminalReward, 0);
                gridworld.get(i).add(pos);
            }
        }

        //setup walls
        for(xyLocation wall : walls) {
            gridworld.get(wall.row-1).get(wall.column-1).isWall = true;
        }

        //setup for terminal states
        for(xyLocation terminal : terminalStates) {
            gridworld.get(terminal.row-1).get(terminal.column-1).reward = terminal.reward;
            gridworld.get(terminal.row-1).get(terminal.column-1).isTerminal = true;
        }

        //World Property contains the gridworld, and delta and terminal condition in order to find when stop iterations
        WorldProperty worldInfo = new WorldProperty(gridworld, false, 0);

        //Available actions we can make
        ArrayList<Character> actions = new ArrayList<>(Arrays.asList('N', 'E', 'S', 'W'));

        //Our algorithm for Value Iteration
        ValueIterationAlgorithm via = new ValueIterationAlgorithm();

        //iteration value
        int iteration = 0;

        //while loop to keep iterating until equilibrium state is met and program terminates
        while(true){
            //print out iteration state
            System.out.println("iteration: " + iteration);

            //print our board values
            for(int i = rows-1; i >= 0; i--){
                for(int j = 0; j < columns; j++){
                    if(worldInfo.gridworld.get(i).get(j).isWall){
                        System.out.print("---------  ");
                    }
                    else{
                        System.out.print(worldInfo.gridworld.get(i).get(j).valueIteration + "  ");
                    }
                }
                System.out.println(); System.out.println();
            }

            //increment iteration
            iteration++;

            //run value iteration function
            worldInfo = via.ValueIterationFunction(worldInfo, discountRate, epsilon, transitionProbabilities, actions);

            //if terminal condition that gets sent back from the value iteration function is true
            //equilibrium has been met inside of the algorithm
            if(worldInfo.terminationCondition){
                break;
            }
        }

        //print out final state of the board after the equilibrium condition has been met
        System.out.println("Final Value after Convergence");
        for(int i = rows-1; i >=0; i--){
            for(int j = 0; j < columns; j++){
                //prints out walls if current tile is wall
                if(worldInfo.gridworld.get(i).get(j).isWall){
                    System.out.print("---------  ");
                    continue;
                }
                //print out value of current position of gridworld
                System.out.print(worldInfo.gridworld.get(i).get(j).valueIteration + "  ");
            }
            //print formatting/spacing
            System.out.println(); System.out.println();
        }

        //Utility-Maximizing Action Sequence
        System.out.println("Final Policy");

        //printing out sequence
        for(int i = rows-1; i >=0; i--){
            for(int j = 0; j < columns; j++){
                System.out.print(worldInfo.gridworld.get(i).get(j).utilAction + "  ");
            }
            //formatting new lines
            System.out.println(); System.out.println();
        }
    }//end of public static main
}//end of Main class
