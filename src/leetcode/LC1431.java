package leetcode;

public class LC1431 {
    public static void main(String[] args){
        String ans = gcdOfStrings("ABABAB","ABABABABABABABAB");
        System.out.println(ans);
    }

     static public String gcdOfStrings(String str1, String str2) {
        String ans = "";

        if(str1.length()>str2.length()){
            String div = "";
            for(int i=0;i<str2.length();i++){
                div+=str2.charAt(i);
                if(divisor(str1,div) && divisor(str2,div)){
                    ans = div;
                }
            }
        }
        else{
            String div = "";
            for(int i=0;i<str1.length();i++){
                div+=str1.charAt(i);
                if(divisor(str2,div) && divisor(str1,div)){
                    ans = div;
                }
            }
        }

        return ans;
     }

     static public boolean divisor(String s1,String s2){

        if(s1.length()%s2.length()!=0){
            return false;
        }

        for(int i=0;i<s1.length();i++){
            if(s1.charAt(i)!=s2.charAt(i%s2.length())){
                return false;
            }
        }
        return true;
     }

}
