package leetcode;

public class LC1768 {
    public static void main(String[] args){
        String ans = mergeAlternately("abcqqqqq","qrtaaa");
        System.out.println(ans);
    }

     static public String mergeAlternately(String word1, String word2) {
            String[] s1 = word1.split("");
            String[] s2 = word2.split("");
            String ans = "";
            if(s1.length>s2.length){
                for(int i=0;i< s2.length;i++){
                    ans+=s1[i];
                    ans+=s2[i];
                }
                for(int i=s2.length;i<s1.length;i++){
                    ans+=s1[i];
                }
            }
            else{
                for(int i=0;i< s1.length;i++){
                    ans+=s1[i];
                    ans+=s2[i];
                }
                for(int i=s1.length;i<s2.length;i++){
                    ans+=s2[i];
                }
            }
            return ans;
        }

}
