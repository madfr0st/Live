package leetcode;


import javax.imageio.IIOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.Set;

public class LC37 {
    static BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
    public static void main(String[] args) throws IIOException {
        char[][] board = {
                {'.','.','9','7','4','8','.','.','.'},
                {'7','.','.','.','.','.','.','.','.'},
                {'.','2','.','1','.','9','.','.','.'},
                {'.','.','7','.','.','.','2','4','.'},
                {'.','6','4','.','1','.','5','9','.'},
                {'.','9','8','.','.','.','3','.','.'},
                {'.','.','.','8','.','3','.','2','.'},
                {'.','.','.','.','.','.','.','.','6'},
                {'.','.','.','2','7','5','9','.','.'}
        };

        solveSudoku(board);


        printBoard(board);

    }

    private static void printBoard(char[][] board) {
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                System.out.print(board[i][j]+" ");
            }
            System.out.println();
        }
    }

    public static void solveSudoku(char[][] board) {
        int empty = 0;

        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                if(board[i][j]=='.'){
                   empty++;
                }
            }
        }
//        System.out.println(empty);
//        checkValue(board,5,2);

        while (empty>0){
            for(int i=0;i<9;i++){
                for(int j=0;j<9;j++){
                    if(board[i][j]=='.'){
                       if(checkValue(board,i,j)){
                           empty--;
                           System.out.println(empty);
                           printBoard(board);
                       }
                    }
                }
            }
        }

    }
    public static boolean checkValue(char[][] board, int x, int y){
        Set<String> set = new HashSet<>();
        for(int i=0;i<9;i++){
             board[x][y] = ((i+1)+"").charAt(0);
//            System.out.println(x+" "+y);
//             printBoard(board);
            if(isValidSudoku(board)){
                set.add((i+1)+"");
//                System.out.println(i+1);
            }
//            System.out.println(i);
        }
//        System.out.println(set);
        if(set.size()==1){
//            System.out.println("adada"+set);
            board[x][y] = set.iterator().next().charAt(0);
//            printBoard(board);
            return true;
        }
        else{
            board[x][y] = '.';
        }
        return false;
    }

    public static boolean isValidSudoku(char[][] board) {

        boolean ans = true;


        for(int i=0;i<9;i+=3){
            for (int j=0;j<9;j+=3){
                Set<String> local = new HashSet<>();
                for(int k=0;k<3;k++){
                    for(int l=0;l<3;l++) {
                        if (board[i+k][j+l]!='.' && local.contains(board[i+k][j+l]+"")){
                            ans = false;
                            break;
                        }
                        else if (board[i+k][j+l]!='.') {
                            local.add(board[i+k][j+l]+"");
                        }
                    }
                }
            }
        }

        for(int i=0;i<9;i++){
            Set<String> local = new HashSet<>();
            for (int j=0;j<9;j++){

                if (board[i][j]!='.' && local.contains(board[i][j]+"")){
                    ans = false;
                    break;
                }
                else if (board[i][j]!='.') {
                    local.add(board[i][j]+"");
                }
            }
        }


        for (int j=0;j<9;j++){
            Set<String> local = new HashSet<>();
                for(int i=0;i<9;i++){
                if (board[i][j]!='.' && local.contains(board[i][j]+"")){
                    ans = false;
                    break;
                }
                else if (board[i][j]!='.') {
                    local.add(board[i][j]+"");
                }
            }
        }

        return ans;
    }
}
