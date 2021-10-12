package bin;



import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Scrapping {
    public static void main(String[] args) throws IOException {


        int pageNo = 0;
        int totalPage = 2251;  //2251
        List<Medicine> list = new ArrayList<>();

        File mainFile = new File( "C:\\Users\\Suman Saurav\\Desktop\\medicineList.txt");
        FileWriter fileWriter = new FileWriter(mainFile);

        for(int i=pageNo;i<=totalPage;i++) {
            String path = "C:\\Users\\Suman Saurav\\Desktop\\medicine\\" + i + ".txt";

            File file = new File(path);
            FileReader fileReader = new FileReader(file);
            BufferedReader inp = new BufferedReader(fileReader);

            String string = "";

            int atLine = 1;


            while ((string = inp.readLine()) != null) {
                boolean info = false;
                if (atLine == 10) {

                    atLine = 1;
                }

                String[] s1 = string.split(">");

                if (s1.length >= 2) {

                    //sr no
                    if (s1[0].equals("      <td height=\"20\" valign=\"top\" class=\"mosttext\"") && atLine == 1) {
                        list.add(new Medicine());
                        String[] s11 = s1[1].split("<");
                        list.get(list.size() - 1).srNo = Integer.parseInt(check1(s11[0]));
                        info = true;
                        atLine++;
                    }
                    //manufacturer
                    if (atLine == 2) {
                        String s11 = inp.readLine();
                        String[] s12 = s11.split(">");
                        String[] s13 = s12[1].split("<");
                        list.get(list.size() - 1).manufacturer = check1(s13[0]);
                        info = true;
                        atLine++;
                    }
                    //brand
                    if (atLine == 3) {

                        //System.out.println(s1[0]);
                        String s11 = inp.readLine();
                        String[] s12 = s11.split("strong>");
                        String[] s13 = s12[1].split("<");
                        list.get(list.size() - 1).brand = check1(s13[0]);
                        info = true;
                        atLine++;
                    }

                    //type
                    if (atLine == 4) {
                        inp.readLine();
                        String s12 = inp.readLine();
                        String[] s13 = s12.split("&nbsp;&nbsp;");
                        list.get(list.size() - 1).type = check1(s13[0]);
                        info = true;
                        atLine++;
                    }

                    //category
                    if (atLine == 5) {
                        inp.readLine();
                        String s12 = inp.readLine();
                        String[] s13 = s12.split("</td>");
                        list.get(list.size() - 1).category = check1(s13[0]);
                        info = true;
                        atLine++;
                    }


                    //unit
                    if (atLine == 6) {
                        String s11 = inp.readLine();
                        String[] s12 = s11.split(">");
                        String[] s13 = s12[1].split("<");
                        list.get(list.size() - 1).unit = check1(s13[0]);
                        info = true;
                        atLine++;
                    }

                    // package unit
                    if (atLine == 7) {
                        String s11 = inp.readLine();
                        String[] s12 = s11.split(">");
                        String[] s13 = s12[1].split("<");
                        list.get(list.size() - 1).packageUnit = check1(s13[0]);
                        info = true;
                        atLine++;
                    }

                    //price
                    if (atLine == 8) {
                        String s11 = inp.readLine();
                        String[] s12 = s11.split(">");
                        String[] s13 = s12[1].split("<");
                        list.get(list.size() - 1).price = Double.parseDouble(check(s13[0]));
                        info = true;
                        atLine++;
                    }

                    // unit price
                    if (atLine == 9) {
                        inp.readLine();
                        inp.readLine();
                        inp.readLine();
                        String s12 = inp.readLine();
                        String[] s13 = s12.split("</td>");
                        list.get(list.size() - 1).unitPrice = Double.parseDouble(check(s13[0]));

                        info = true;
                    }

                    if (info) {
                        atLine++;
                    }
                    //System.out.println(list.get(list.size()-1));
                }

                string = "";
            }
        }
        for(int i=0;i<list.size();i++){
            fileWriter.write(list.get(i).print());
        }
        fileWriter.flush();
        fileWriter.close();
    }


    static String check(String s){
        String[] s1 =s.split(" ");
        s = "";
        for(int i=0;i<s1.length;i++){
            s+=s1[i];
        }
        return s;
    }

    static String check1(String s){
        String[] s1 =s.split("  ");
        s = "";
        for(int i=0;i<s1.length;i++){
            s+=s1[i];
        }
        return s;
    }

}
