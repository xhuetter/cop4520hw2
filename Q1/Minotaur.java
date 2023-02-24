import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class Minotaur {

    public static void main(String[] args) {

      Labyrinth maze = new Labyrinth(Integer.parseInt(args[0]));

      long start = System.currentTimeMillis();

      maze.startMaze();

      long end = System.currentTimeMillis();
      float timeElapsed = (end - start) / 1000F;
      System.out.println("All guests have gone through the Labyrinth in " + timeElapsed + " seconds");
      return;

    }




}
