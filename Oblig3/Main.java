import java.util.Random;
import java.util.Arrays;
import java.util.ArrayList;

class Main{
  public static SortAlgorithms sortAl = new SortAlgorithms();
  public static void main(String[] args) {

    //
    // // Selection Sort
    // int[] numArr = randomArr(10);
    // System.out.println("Original: ");
    // printArray(numArr);
    // sortAl.selectionSort(numArr);
    // System.out.println();
    // System.out.println("Selection Sort: ");
    // printArray(numArr);
    // System.out.println("\n");




    // Insertion Sort
    // int[] numArr1 = makeArray(1000, false);
    // int[] numArr1 = randomArr(1000);

    // returns average
    runAllTests(10, Integer.parseInt(args[0])); // 10 time for each sort algorithm



    // I use use 3 last, because 2 first is not normaly big

    // System.out.println("Original: ");
    // printArray(numArr1);
    // System.out.println("\n");
    // long t = System.nanoTime(); //nanoseconds
    // sortAl.insertionSort(numArr1);
    // double tid = ( System.nanoTime() - t ) / 10000000.0; //milliseconds
    // System.out.println("Tid : " + tid);
    // // System.out.println("\n");
    // // System.out.println("Sorted: ");
    // printArray(numArr1);
    // System.out.println("\n");
    // System.out.println("\n");

    // Quick Sort
    int[] numArr = {9,8,7,6,5,4,3,2,1,0};
    // System.out.println("Original: ");
    // printArray(numArr);
    // System.out.println("\n");
    // sortAl.correctInPlaceQuickSort(numArr, 0, 9);
    // System.out.println("\n");
    // System.out.println("Sorted: ");
    // printArray(numArr);
    // System.out.println("\n");
    //
    //
    // // Merge Sort
    // int[] numArr = {9,8,7,6,5,4,3,2,1,0};
    // System.out.println("Original: ");
    // printArray(numArr);
    // sortAl.mergeSort(numArr);
    // // System.out.println();
    // System.out.println("Sorted: ");
    // printArray(numArr);
    // System.out.println("\n");
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
  }

  // n is lenght of array
  public static int[] randomArr(int n){
    Random rand = new Random();
    int[] num = new int[n];
    for(int i = 0; i < num.length; i++){
      num[i] = rand.nextInt(n);
    }
    return num;
  }


  public static void printArray(int[] arr){
    System.out.println(Arrays.toString(arr));
  }

  public static int[] makeArray(int numElements, boolean reverse){
    int[] numArr = new int[numElements];
    int max_number = numElements - 1;
    if(reverse){
      for(int i = 0; i < numElements; i++){
        numArr[i] = max_number - i;
      }
    }else{ // sorted
      for(int i = 0; i < numElements; i ++){
        numArr[i] = i;
      }
    }
    return numArr;
  }

  // because i need to use same array on all sort algorithms so i can get better
  // time difference, because every algorith will work with the same arrays
  public static void runAllTests(int numberOfTimes, int elem){
    int[] numArr = null;
    System.out.println("\n----Random:----");
    ArrayList<int[]> array = new ArrayList<int[]>(); // to save same arrays
    // for different sorting Algorithms
    for(int i = 0; i < numberOfTimes; i++){
      numArr = randomArr(elem); // random array
      array.add(numArr);
    }
    insertSortTest(array);
    quickSortTest(array);
    mergeSortTest(array);
    arraysSort(array);

    System.out.println("\n----Reverse:----");
    array = new ArrayList<int[]>();
    for(int i = 0; i < numberOfTimes; i++){
      numArr = makeArray(elem, true); // reverse array
      array.add(numArr);
    }
    insertSortTest(array);
    quickSortTest(array);
    mergeSortTest(array);
    arraysSort(array);

    System.out.println("\n----Sorted:----");
    array = new ArrayList<int[]>();
    for(int i = 0; i < numberOfTimes; i++){
      numArr = makeArray(elem, false); // sorted array
      array.add(numArr);
    }
    insertSortTest(array);
    quickSortTest(array);
    mergeSortTest(array);
    arraysSort(array);
  }



  public static void arraysSort(ArrayList<int[]> array){
    System.out.println("\n Arrays Sort: ");
    long t; double tid;
    double average = 0;
    for(int[] numArr : array){
      t = System.nanoTime(); //nanoseconds
      Arrays.sort(numArr);
      tid = ( System.nanoTime() - t ) / 10000000.0; //millisekunder
      average += tid;
      System.out.println(tid);
    }
    System.out.println("Average : " + (average/array.size()));
  }

  public static void insertSortTest(ArrayList<int[]> array){
    System.out.println("\nInsert Sort: ");
    long t; double tid;
    double average = 0;
    for(int[] numArr : array){
      t = System.nanoTime(); //nanoseconds
      sortAl.insertionSort(numArr);
      tid = ( System.nanoTime() - t ) / 10000000.0; //millisekunder
      average += tid;
      System.out.println(tid);
    }
    System.out.println("Average : " + (average/array.size()));
  }

  public static void quickSortTest(ArrayList<int[]> array){
    System.out.println("\nQuick Sort: ");
    long t; double tid;
    double average = 0;
    for(int[] numArr : array){
      t = System.nanoTime(); //nanoseconds
      sortAl.correctInPlaceQuickSort(numArr, 0, numArr.length-1);
      tid = ( System.nanoTime() - t ) / 10000000.0; //millisekunder
      average += tid;
      System.out.println(tid);
    }
    System.out.println("Average : " + (average/array.size()));
  }

  public static void mergeSortTest(ArrayList<int[]> array){
    System.out.println("\nMerge Sort: ");
    long t; double tid;
    double average = 0;
    for(int[] numArr : array){
      t = System.nanoTime(); //nanoseconds
      sortAl.mergeSort(numArr);
      tid = ( System.nanoTime() - t ) / 10000000.0; //millisekunder
      average += tid;
      System.out.println(tid);
    }
    System.out.println("Average : " + (average/array.size()));

  }

}
