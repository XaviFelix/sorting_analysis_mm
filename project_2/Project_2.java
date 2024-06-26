import java.util.Random;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileWriter;
import java.util.Arrays;

//NOTE: MAIN  OBJECTIVES:
//TODO: For the Kth position we need to increment this K'th position per size
//      This means I need to generate a Kth depending on the size of the array at random, implement this behavior
//TODO: For what gets print to the console: just add, "The kthSmallest using algorithmX is: "

//NOTE: I am choosing to ignore this EDGE CASE
//TODO: Keep this in mind: if the 2nd smallest in the array is a repeated number then I need to find the one that is not repeated
//      and the one that is actually the second smallest
//      NOTE: one solution i could do is remove any repeated numbers in the array before sorting (happens for the clones)
//      But this changes the size of the array if this is true, so i can't do that!
//      DO I need to consider this edge case??


public class Project_2 {
  
  public static void main(String[] args) throws IOException {

    //TODO: Delete when done testing the other algorithms here
    // NOTE: This is just for testing
//    int[] A = {34, 7, 23, 32, 5, 62, 14, 45, 19, 28, 39, 7, 23, 62, 14, 5, 45, 32, 19, 28, 39};
//    int kth = 2;
//    System.out.println("The " + kth + "th " + "smallest is: " + selection(0, A.length - 1, kth - 1, A));
//    mergeSort(A.length, A);
//    printArray(A);


    int arrayCount = 10;
    int kthSmallest = 2;

    // Creates a txt file containing the average times for the three algorithms
    try (PrintWriter writer = new PrintWriter(new FileWriter("averages.txt"))) {
      beginAnalysis(arrayCount, kthSmallest, writer);
    }
  }



  //NOTE: beginAnalysis() goes here:
  //TODO: Format this using vsCode
  //TODO: Add a randomized kthSmallest element based on the size of the array
 
  public static void beginAnalysis(int arrayCount, int kthSmallest, PrintWriter writer) {
      int[] currentArray;
      Random generator = new Random();
      int numTrials = 10; //This is for getting the average

      for (int i = 2, j = 0; j < arrayCount; i *= 2, j++) {
          currentArray = new int[i];

          //TODO: I'm still testing this
          int kthSmallest = generator.nextInt(i + 1); //This will provide a random kthSmallest

          //TODO: change `a` to something better please
          for(int a = 0; a < i; a++) {
            currentArray[a] =  1 + generator.nextInt(100);
          }

          //TODO: This will be for algorithm 1 -> 3
          //NOTE: For debugging:
//          System.out.println("This is the original array:");
//          printArray(currentArray);
          System.out.println("\nIteration " + (j + 1) + " for 2^" + i + ": ");

          double averageTimeMergeSort = timeMergeSort(currentArray, kthSmallest, numTrials);
          double averageTimeQuickSort = timeQuickSort(currentArray, kthSmallest, numTrials);
          double averageTimeQuickSortMM = timeMedianOfMeans(currentArray, kthSmallest, numTrials);

          // prints to txt file for google sheets or excel
          writer.println(averageTimeMergeSort + "\t" + averageTimeMergeSort + "\t" + averageTimeQuickSortMM);

          System.out.println("Average time for MergeSort (seconds): " + averageTimeMergeSort);
          System.out.println("Average time for QuickSort using partition (seconds): " + averageTimeQuickSort);
          System.out.println("Average time for QuickSort using partition MM (seconds): " + averageTimeQuickSortMM);
          System.out.println();
      }
  }

  //NOTE: timeMergeSort goes here:
  public static double timeMergeSort(int[] currentArray, int kthSmallest, int numTrials) {
    double[] trials = new double[numTrials];
    int m = trials.length;

    // Choosing 10 trials and recording each trial based on start and end times
    for (int i = 0; i < m; ++i) {
        int[] arrayClone = currentArray.clone();
        //System.gc();

        long startTime = System.nanoTime();
        mergeSort(arrayClone.length, arrayClone);
        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;

        //NOTE: Print the array here for debug purposes
        //TODO: Delete this when testing enough times
//        if(i > 5) {
//          printKthSmallest(kthSmallest);
//          System.out.println(arrayClone[kthSmallest - 1]);
//          printArray(arrayClone);
//        }
        if(i == m - 1) {
          printKthSmallest(kthSmallest);
          System.out.println(arrayClone[kthSmallest - 1]);
        }


        // Storing current time, converting to seconds
        trials[i] = elapsedTime / 1_000_000_000.0;
    }
    return findAverageTime(trials);
  }
  
  //NOTE: timeQuickSort kth position goes here:
  public static double timeQuickSort(int[] currentArray, int kthSmallest, int numTrials) {
    double[] trials = new double[numTrials];
    int m = trials.length;

    for (int i = 0; i < m; ++i) {
        int[] arrayClone = currentArray.clone();
        //System.gc();

        long startTime = System.nanoTime();
        selection(0, arrayClone.length - 1, kthSmallest - 1, arrayClone);
        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;

        //NOTE: Print the array here for debug purposes
        //TODO: Delete this when testing enough times
//        if(i > 5) {
//          printKthSmallest(kthSmallest);
//          System.out.println(arrayClone[kthSmallest - 1]);
//          printArray(arrayClone);
//        }
        if(i == m - 1) {
          printKthSmallest(kthSmallest);
          System.out.println(arrayClone[kthSmallest - 1]);
        }

        // Storing current time, converting to seconds
        trials[i] = elapsedTime / 1_000_000_000.0;
    }
    return findAverageTime(trials);
  }

  //NOTE: timeQuickSort kth position using mm goes here:
  public static double timeMedianOfMeans(int[] currentArray, int kthSmallest, int numTrials) {
      double[] trials = new double[numTrials];
      int m = trials.length;
  
      for (int i = 0; i < m; ++i) {
          int[] arrayClone = currentArray.clone();
          //System.gc();
  
          long startTime = System.nanoTime();
          int k = select(arrayClone.length, arrayClone, kthSmallest);
          long endTime = System.nanoTime();
          long elapsedTime = endTime - startTime;
  
          //NOTE: Print the array here for debug purposes
          //TODO: Delete this when testing enough times
  //        if(i > 5) {
  //          printKthSmallest(kthSmallest);
  //          System.out.println(arrayClone[kthSmallest - 1]);
  //          printArray(arrayClone);
  //        }
          if(i == m - 1) {
            printKthSmallest(kthSmallest);
            System.out.println(k);
          }
  
          // Storing current time, converting to seconds
          trials[i] = elapsedTime / 1_000_000_000.0;
      }
      return findAverageTime(trials);
    }
  

  //NOTE: MeregeSort goes here:
  public static void mergeSort(int size, int[] array) {

    if(size > 1) {
      final int h = (int)Math.floor(size / 2);
      final int m = size - h;

      int[] U = new int[h];
      int[] V = new int[m];

      //Copy S[0] -> S[h] into U[]
      for(int i = 0; i < h; ++i) {
        U[i] = array[i];
      }

      //Copy S[h + 1] -> S[size] into V]
      for (int j = 0; j < m; ++j) {
        V[j] = array[j + h];
       
        //NOTE: This is for debugging, leave commented
        //System.out.print(V[j] + (j < m - 1 ? " " : "\n"));
      }

      //NOTE: This is for debugging, leave commented
//      printArray(U);
//      printArray(V);
//      System.out.println();

      mergeSort(h, U);
      mergeSort(m, V);
      merge(h, m, U, V, array);
    }
  }

  public static void merge(int h, int m, int[] U, int[] V, int[] array) {
    int i = 0;
    int j = 0;
    int k = 0;

    while(i < h && j < m) {
      if(U[i] < V[j])
        array[k++] = U[i++];
      else
        array[k++] = V[j++];
    }

    while(i < h)
      array[k++] = U[i++];

    while(j < m)
      array[k++] = V[j++];
  } // NOTE: End of mergeSort

  //NOTE: QuickSort kth position goes here:
  public static int selection(int low, int high, int kthSmallest, int[] array) {
    int pivotpoint; 
    if(low == high)
      return array[low];

    pivotpoint = partition(low, high, array);


    if(kthSmallest == pivotpoint)
      return array[kthSmallest];
    else if(kthSmallest < pivotpoint)
      return selection(low, pivotpoint - 1, kthSmallest, array);
    else
      return selection(pivotpoint + 1, high, kthSmallest, array);
  }

  public static int partition(int low, int high, int[] array) {
    int pivotItem = array[low];
    int j = low;

    for (int i = low + 1; i <= high; i++) {
        if (array[i] < pivotItem) {
            j++;
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }

    int pivotpoint = j;
    int temp = array[low];
    array[low] = array[pivotpoint];
    array[pivotpoint] = temp;

    return pivotpoint;
  } // NOTE: End of QuickSort w/ pivotpoint partitions

  //NOTE: QuickSort kth position goes here using MM:
  public static int select(int size, int[] array, int kthSmallest) {
    return selection2(array, 0, size - 1, kthSmallest - 1);
  }

  public static int selection2(int[] array, int low, int high, int kthSmallest) {
    if (high == low)
      return array[low];
    else {
      int pivotPoint = partition2(array, low, high);

      if (kthSmallest == pivotPoint)
        return array[pivotPoint];
      else if (kthSmallest < pivotPoint)
        return selection2(array, low, pivotPoint - 1, kthSmallest);
      else
        return selection2(array, pivotPoint + 1, high, kthSmallest);
    }
  }

  public static int partition2(int[] array, int low, int high) {
    final int arraySize = high - low + 1;
    final int r = (int) Math.ceil((double) arraySize / 5); 
    int[] T = new int[r];

    // Find the medians from the partitions (groups of 5)
    for (int i = 0; i < r; ++i) {
      int first = low + 5 * i;
      int last = Math.min(low + 5 * i + 4, high);
      T[i] = findMedian(array, first, last);
    }

    int pivotItem = select(r, T, (r + 1) / 2);

    // NOTE: Partition based on pivotItem
    int j = low;
    int mark = low;
    for (int i = low; i <= high; ++i) {
      if (array[i] == pivotItem) {
        swap(array, i, j);
        mark = j;
        j++;
      } else if (array[i] < pivotItem) {
        swap(array, i, j);
        j++;
      }
    }
    swap(array, mark, j - 1);

    return j - 1; // NOTE: Pivotpoint
                  
  } // NOTE: End of QuickSort w/ MM partitions




  //NOTE: Utility Functions:
  public static void printArray(int[] array) {
    int size = array.length;
    for(int i = 0; i < size; ++i) {
      System.out.print(array[i] + ((i < size - 1) ? " " : "\n"));
    }
  }

  // Given m trials, find average (min and max are subtracted)
  public static double findAverageTime(double[] trials) {
      Arrays.sort(trials);
      double sum = 0.0;
      int N = trials.length;
      for (int i = 1; i < N - 1; ++i) {
          sum += trials[i];
      }
      return sum / (N - 2);
  }

  public static void swap(int[] array, int i, int j) {
    int temp = array[i];
    array[i] = array[j];
    array[j] = temp;
  }

  public static int findMedian(int[] array, int low, int high) {
    Arrays.sort(array, low, high + 1);

    return array[low + (high - low) / 2];
  }

  public static void printKthSmallest(int kthSmallest) {
    switch (kthSmallest) {
      case 1:
        System.out.print("The " + kthSmallest + "st smallest is: ");
        break;
      case 2:
        System.out.print("The " + kthSmallest + "nd smallest is: ");
        break;
      case 3:
        System.out.println("The " + kthSmallest + "rd smallest is: ");
        break;
      default:
        System.out.println("The " + kthSmallest + "th smallest is: ");
        break;
    }
  }
}
