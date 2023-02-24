import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class Minotaur {

    public static void main(String[] args) {

      Showroom room = new Showroom(Integer.parseInt(args[0]));

      long start = System.currentTimeMillis();

      room.partyTime();

      long end = System.currentTimeMillis();
      float timeElapsed = (end - start) / 1000F;
      System.out.println("Time taken for all guests to visit room at least once: " + timeElapsed + " seconds");
      System.exit(0);

    }




}
