import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class Minotaur {

    public static void main(String[] args) {

      Labyrinth maze = new Labyrinth(100);

      long start = System.currentTimeMillis();

      maze.startMaze();

      long end = System.currentTimeMillis();
      float timeElapsed = (end - start) / 1000F;
      System.out.println(timeElapsed);
      return;

    }




}
