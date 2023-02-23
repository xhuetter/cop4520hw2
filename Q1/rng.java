import java.util.*;

public class rng {

  public static void main(String[] args)
  {
    Random random = new Random();
    boolean[] array = new boolean[1000];
    int i = 0;

    while (i < 1000)
    {
      int nextNum = random.nextInt(1000);
      if (!array[nextNum])
      {
        array[nextNum] = true;
        i += 1;
      }
    }
  }


}
