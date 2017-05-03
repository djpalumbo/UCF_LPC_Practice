import java.io.*;
import java.util.*;

public class texting {
    TreeMap<String, String> map = new TreeMap<String, String>();
    texting() throws Exception {
        Scanner in = new Scanner(new File("texting.in"));
        int t = in.nextInt();
        for(int i = 0; i < t; i++) {
            map.put(in.next(), in.nextLine().trim());
        }
        for(String a : map.keySet()) {
//            System.out.println(a + " " + map.get(a));
        }

        t = in.nextInt();
        in.nextLine();
        for(int i = 0; i < t; i++) {
            String line = in.nextLine();
            Scanner omg = new Scanner(line);
            String out = "";
            while(omg.hasNext()) {
                if(omg.hasNext()) {
                    String n = omg.next();
                    if(map.containsKey(n)) {
                        out = out + map.get(n) + " ";
                    } else {
                        out = out + n + " ";
                    }
                }
            }
            out.trim();
            System.out.println(out);
        }

    }
    public static void main(String args[]) throws Exception {
        new texting();
    }
}
