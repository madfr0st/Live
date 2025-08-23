package leetcode;

import javax.imageio.IIOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.Set;

public class LC36 {
    static BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
    public static void main(String[] args) throws IIOException {
        char[][] board = {
                {'5','3','.','.','7','.','.','.','.'},
                {'6','.','.','1','9','5','.','.','.'},
                {'.','9','8','.','.','.','.','6','.'},
                {'8','.','.','.','6','.','.','.','3'},
                {'4','.','.','8','.','3','.','.','1'},
                {'7','.','.','.','2','.','.','.','6'},
                {'.','6','.','.','.','.','2','8','.'},
                {'.','.','.','4','1','9','.','.','5'},
                {'.','.','.','.','8','.','.','7','9'}
        };

        System.out.println(isValidSudoku(board));

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
