package timestamp;

import java.sql.Date;
import java.text.SimpleDateFormat;


public class check {
    public static void main(String[] args) {
        SimpleDateFormat sfd = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        System.out.println(sfd.format(new Date(1633990038010L)));
    }
}
