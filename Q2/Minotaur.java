import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class Minotaur {

    public static void main(String[] args) {

      Showroom room = new Showroom();

      long start = System.currentTimeMillis();

      room.partyTime();

      long end = System.currentTimeMillis();
      float timeElapsed = (end - start) / 1000F;
      System.out.println(timeElapsed);
      System.exit(0);

    }




}
