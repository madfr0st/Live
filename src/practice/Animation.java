package practice;

public class Animation {
    static String[] perct;
    public static void main(String[] args){
        perct = new String[]{"0","12","12.5","24.5","25","37","37.5","49.5","50","62","62.5","74.5","75","87","87.5","99.5","100"};

        roatate(6);



        pos4(perct[6]);
        pos4(perct[7]);
        pos5(perct[8]);
        pos5(perct[9]);
        pos6(perct[10]);
        pos6(perct[11]);
        pos7(perct[12]);
        pos7(perct[13]);
        pos8(perct[14]);
        pos8(perct[15]);
        pos1(perct[16]);
        pos1(perct[0]);
        pos2(perct[1]);
        pos2(perct[2]);
        pos3(perct[3]);
        pos3(perct[4]);
        pos4(perct[5]);




    }
    static void pos1(String percent){
        System.out.println(
        percent+"% {\n" +
        "\t\ttop: 0%;\n" +
        "\t\tleft: 0%;\n" +
        "\t\twidth: 75%;\n" +
        "\t\theight: 75%;\n" +
        "\t}"
        );
    }

    static void pos2(String percent){
        System.out.println(
                percent+"%{\n" +
                        "\t\ttop: 75%;\n" +
                        "\t\tleft: 0%;\n" +
                        "\t\twidth: 25%;\n" +
                        "\t\theight: 25%;\n" +
                        "\t}\n"
        );
    }

    static void pos3(String percent){
        System.out.println(
                percent+"%{\n" +
                        "\t\ttop: 75%;\n" +
                        "\t\tleft: 25%;\n" +
                        "\t\twidth: 25%;\n" +
                        "\t\theight: 25%;\n" +
                        "\t}\n"
        );
    }

    static void pos4(String percent){
        System.out.println(
                percent+"%{\n" +
                        "\t\ttop: 75%;\n" +
                        "\t\tleft: 50%;\n" +
                        "\t\twidth: 25%;\n" +
                        "\t\theight: 25%;\n" +
                        "\t}"
        );
    }


    static void pos5(String percent){
        System.out.println(
                percent+"% {\n" +
                        "\t\ttop: 75%;\n" +
                        "\t\tleft: 75%;\n" +
                        "\t\twidth: 25%;\n" +
                        "\t\theight: 25%;\n" +
                        "\t}"
        );
    }

    static void pos6(String percent){
        System.out.println(
                percent+"%{\n" +
                        "\t\ttop: 50%;\n" +
                        "\t\tleft: 75%;\n" +
                        "\t\twidth: 25%;\n" +
                        "\t\theight: 25%;\n" +
                        "\t}"
        );
    }

    static void pos7(String percent){
        System.out.println(
                percent+"%{\n" +
                        "\t\ttop: 25%;\n" +
                        "\t\tleft: 75%;\n" +
                        "\t\twidth: 25%;\n" +
                        "\t\theight: 25%;\n" +
                        "\t}"
        );
    }

    static void pos8(String percent){
        System.out.println(
                percent+"%{\n" +
                        "\t\ttop: 0%;\n" +
                        "\t\tleft: 75%;\n" +
                        "\t\twidth: 25%;\n" +
                        "\t\theight: 25%;\n" +
                        "\t}"
        );
    }

    static void roatate(int a){
        for(int j=0;j<a;j++) {
            for (int i = 0; i < perct.length-1; i++) {
                String s1 = perct[perct.length-1-i];
                String s2 = perct[perct.length-2-i];
                perct[perct.length-1-i] = s2;
                perct[perct.length-2-i] = s1;

            }
        }
    }
}
