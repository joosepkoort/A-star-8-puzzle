package com.joosep;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    private static ArrayList<State> successorsList;

    public static void main(String[] args) {
        boolean hasSolution = false;
        //list creation
        successorsList = new ArrayList<>();

        //board game initialization
        int[][] initialTable = {
                {2,3,7},
                {1,4,8},
                {0,5,6}};

        //adding the initial table
        successorsList.add(new State(null, initialTable, 0, 0));

        //get the first state
        State state = successorsList.get(0);


        while(state != null && hasSolution == false){
            if(state.outOfPlace() == 0){
                hasSolution = true;
            } else {
                //generating the successors
                state.genSuccessors();
                //removing the first item
                successorsList.remove(0);
                addSuccessors(state);
                //gets the first state
                state = successorsList.get(0);
            }
        }

        if(hasSolution){
            System.out.println("The solution to 8-puzzle" + Arrays.deepToString(initialTable)+ "is: \n");
            printSolution(state);
        } else {
            System.out.println("Didn't find a solution!");
        }
    }

    private static void addSuccessors(State state){
        ArrayList<State> successors = state.getSuccessorsList();

        for(State eachState : successors){
            if(successorsList.size() == 0){
                successorsList.add(eachState);
            } else if(eachState.getF() < successorsList.get(0).getF()){
                successorsList.add(0, eachState);
            } else {
                int pos = 1;
                while(pos < successorsList.size() && eachState.getF() > successorsList.get(pos).getF()  ){
                    ++pos;
                }

                successorsList.add(pos, eachState);
            }
        }
    }

    private static void printSolution(State state){

        if(state != null && state.getParent() != null){

            printSolution(state.getParent());
        }

        String solution ="";
        int[][] board = state.getBoard();

        for(int x = 0; x < 3; ++x){
            solution = solution + "[";
            for(int y = 0; y < 3; ++y){
                if (y==2){
                    solution = solution + board[x][y];
                } else{
                    solution = solution + board[x][y] + ",";
                }

            }
            solution = solution + "]\n";

        }

        System.out.println(solution);
    }
}
