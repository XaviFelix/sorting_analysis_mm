import java.util.Random;

public class RandomIndex {
  public static void main(String[] args) {

    int[] array = new int[10];
    randomizeArray(array);
    printArray(array);
    randomElement(array);
  }

  public static void printArray(int[] array) {
    int size = array.length;
    for(int i = 0; i < size; ++i) {
      System.out.print(array[i] + ((i < size - 1) ? " " : "\n"));
    }
  }

  public static void randomizeArray(int[] array) {
    int size = array.length;
    Random rand = new Random();

    for(int i = 0; i < size; ++i) {
      array[i] = 1 + rand.nextInt(20);
    }
  }

  public static void randomElement(int[] array) {
    Random randomIndex = new Random();
    int size = array.length;

    System.out.println("This is the random element in your array: " 
                        + array[randomIndex.nextInt(size)]);
  }
}
