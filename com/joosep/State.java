package com.joosep;

import java.util.ArrayList;

public class State {
    private State parent;
    private int f;
    private int g;
    private int h;
    private int[][] board;
    private int cost;
    private int spaceX;
    private int spaceY;
    private ArrayList<State> successorsList;

    public int[][] getInitialTable() {
        return initialTable;
    }

    public void setInitialTable(int[][] initialTable) {
        this.initialTable = initialTable;
    }

    public int[][] initialTable = {
            {2,3,7},
            {1,4,8} ,
            {0,5,6}};

    public State(State parent, int[][] board, int f , int cost) {
        this.parent = parent;
        this.board = board;
        this.cost = cost;
        this.board = board;
        this.successorsList = new ArrayList<>();
        //numbers not in place
        this.g = outOfPlace();
        this.h = distance();
        //calculating the heuristics
        this.f = f + this.g + this.h;
        //finds the blank space location
        findBlankSpace();
    }

    private void findBlankSpace(){
        //find empty space
        //rows
        for(int x = 0; x < 3; ++x){
            //columns
            for(int y = 0; y < 3; ++y){
                if(this.board[x][y] == 0){
                    this.spaceX = x;
                    this.spaceY = y;
                }
            }
        }
    }

    public int outOfPlace(){
        int count = 0;

        if(this.board[0][0] != 1){
            ++count;
        }

        if(this.board[0][1] != 2){
            ++count;
        }

        if(this.board[0][2] != 3){
            ++count;
        }

        if(this.board[1][0] != 4){
            ++count;
        }

        if(this.board[1][1] != 5){
            ++count;
        }

        if(this.board[1][2] != 6){
            ++count;
        }

        if(this.board[2][0] != 7){
            ++count;
        }

        if(this.board[2][1] != 8){
            ++count;
        }

        return count;
    }

    public int distance(){
        int count = 0;

        for(int x =0; x < 3; ++x){
            for(int y = 0; y < 3; ++y){

                if(this.board[x][y] == 1){
                    if(x != 0 || y != 0){
                        count = count + Math.abs(0 - x);
                        count = count + Math.abs(0 - y);
                    }
                }

                if(this.board[x][y] == 2){
                    if(x != 0 || y != 1){
                        count = count + Math.abs(0 - x);
                        count = count + Math.abs(1 - y);
                    }
                }

                if(this.board[x][y] == 3){
                    if(x != 0 || y != 2){
                        count = count + Math.abs(0 - x);
                        count = count + Math.abs(2 - y);
                    }
                }

                if(this.board[x][y] == 4){
                    if(x != 1 || y != 0){
                        count = count + Math.abs(1 - x);
                        count = count + Math.abs(0 - y);
                    }
                }

                if(this.board[x][y] == 5){
                    if(x != 1 || y != 1){
                        count = count + Math.abs(1 - x);
                        count = count + Math.abs(1 - y);
                    }
                }

                if(this.board[x][y] == 6){
                    if(x != 1 || y != 2){
                        count = count + Math.abs(1 - x);
                        count = count + Math.abs(2 - y);
                    }
                }

                if(this.board[x][y] == 7){
                    if(x != 2 || y != 0){
                        count = count + Math.abs(2 - x);
                        count = count + Math.abs(0 - y);
                    }
                }

                if(this.board[x][y] == 8){
                    if(x != 2 || y != 1){
                        count = count + Math.abs(2 - x);
                        count = count + Math.abs(1 - y);
                    }
                }
            }
        }

        return count;
    }

    public void genSuccessors(){
        int[][] newBoard;

        //checks if space is on the first line or middle
        if(this.spaceX >= 0 && this.spaceX < 2){
            newBoard = copyBoard(this.board);
            //moves the space to place to the right
            newBoard[this.spaceX][this.spaceY] = newBoard[this.spaceX + 1][this.spaceY];
            newBoard[this.spaceX + 1][this.spaceY] = 0;
            //adds to the successors list
            successorsList.add(new State(this, newBoard, this.f, this.cost +1));
        }

        //checks if space is on the last line or middle
        if(this.spaceX <= 2 && this.spaceX > 0){
            newBoard = copyBoard(this.board);
            //moves the space to left
            newBoard[this.spaceX][this.spaceY] = newBoard[this.spaceX - 1][this.spaceY];
            newBoard[this.spaceX - 1][this.spaceY] = 0;
            //adds the successor to the successors list
            successorsList.add(new State(this, newBoard, this.f, this.cost +1));
        }

        //Checks if you are in the first column or the middle column
        if(this.spaceY >= 0 && this.spaceY < 2){
            newBoard = copyBoard(this.board);
            newBoard[this.spaceX][this.spaceY] = newBoard[this.spaceX][this.spaceY + 1];
            newBoard[this.spaceX][this.spaceY + 1] = 0;
            //adds the successor to the successors list
            successorsList.add(new State(this, newBoard, this.f, this.cost +1));
        }

        //checks if it is in the last or middle column
        if(this.spaceY <= 2 && this.spaceY > 0){
            newBoard = copyBoard(this.board);
            //moves space to the left
            newBoard[this.spaceX][this.spaceY] = newBoard[this.spaceX][this.spaceY - 1];
            newBoard[this.spaceX][this.spaceY - 1] = 0;
            //adds the successor to the successorsList
            successorsList.add(new State(this, newBoard, this.f, this.cost +1));
        }
    }

    public int[][] copyBoard(int[][] board){
        int[][] newBoard = new int[3][3];
        for(int x = 0; x < 3; ++x){
            for(int y = 0; y < 3; ++y){
                newBoard[x][y] = board[x][y];
            }
        }
        return newBoard;
    }

    public State getParent() {
        return parent;
    }

    public void setParent(State parent) {
        this.parent = parent;
    }

    public int getF() {
        return f;
    }

    public void setF(int f) {
        this.f = f;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public ArrayList<State> getSuccessorsList() {
        return successorsList;
    }

    public void setSuccessorsList(ArrayList<State> successorsList) {
        this.successorsList = successorsList;
    }

}