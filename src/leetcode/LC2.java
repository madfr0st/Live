package leetcode;



import practice.A;

import javax.imageio.IIOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

public class LC2 {
    static BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
    public static void main(String[] args) throws IIOException{
        ListNode listNode1 = new ListNode(2);
        listNode1.next = new ListNode(4);
        listNode1.next.next = new ListNode(3);

        ListNode listNode2 = new ListNode(5);
        listNode2.next = new ListNode(6);
        listNode2.next.next = new ListNode(4);

        System.out.println(addTwoNumbers(listNode1,listNode2));
    }

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode currentL1 = l1;
        ListNode currentL2 = l2;
        ArrayList<Integer> list = new ArrayList<>();

        int c = 0;
        while (currentL1!=null || currentL2!=null){
            int l = 0;
            int r = 0;
            if(currentL1!=null){
                l = currentL1.val;
                currentL1 = currentL1.next;
            }
            if(currentL2!=null){
                r = currentL2.val;
                currentL2 = currentL2.next;
            }


                list.add((l+r+c)%10);
                c = (l+r+c)/10;
        }

        if(c>0){
            list.add(c);
        }

        if(list.size()>0){
            ListNode ans = new ListNode(list.get(0));
            currentL1  = ans;
            for(int i=1;i<list.size();i++){
                currentL1.next = new ListNode(list.get(i));
                currentL1 = currentL1.next;
            }
            return ans;
        }

        return null;
    }



      public static class ListNode {
          int val;
          ListNode next;
          ListNode() {}
          ListNode(int val) { this.val = val; }
          ListNode(int val, ListNode next) { this.val = val; this.next = next; }

          @Override
          public String toString() {
              return "ListNode{" +
                      "val=" + val +
                      ", next=" + next +
                      '}';
          }
      }

}
