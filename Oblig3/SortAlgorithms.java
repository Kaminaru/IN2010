import java.util.Arrays;
import java.util.Random;


class SortAlgorithms{
  public SortAlgorithms(){  }

  public void selectionSort(int[] arr){
    // for(int i = 1; i < arr.length-1; i++)  in prekode
    for(int i = 0; i < arr.length-1; i++){
      // Find the index, s , of the smallest element in arr[i...n]
      int s = i;
      for(int j = i+1; j < arr.length; j++){
        if(arr[j] < arr[s]){
          s = j;
        }
      }
      if(i != s){
        // Swap arr[i] and arr[s]
        int t = arr[s];
        arr[s] = arr[i];
        arr[i] = t;
      }
    }
  }


  public void insertionSort(int[] arr){
    for(int i = 1; i < arr.length; i++){
      int x = arr[i];
      // Put x in the right place in arr[1..i], moving larger elements up as needed
      int j = i;
      while(j > 0 && x < arr[j-1]){
        arr[j] = arr[j-1];  // move arr[j - 1] up one cell
        j--;
      }
      arr[j] = x;
    }
  }


  // quick sort
  public int inPlacePartition(int[] arr, int from, int to){
    Random random = new Random();
    int r = random.nextInt((to-from)+1) + from;
    int t = arr[to];
    arr[to] = arr[r];
    arr[r] = t;

    int p = arr[to]; // the pivot
    int l = from; // l will scan rightward
    r = to-1; // r will scan leftward
    while(l <= r){ // find an element smaller than the pivot
      while(l <= r && arr[l]<= p){
        l++;
      }
      while(r >= l && arr[r]>=p){ // find an element smaller than the pivot
        r--;
      }
      if(l<r){
        // Swap arr[l] and arr[r]
        t = arr[r];
        arr[r] = arr[l];
        arr[l] = t;
      }
    }
    // Swap arr[l] and arr[to]
    t = arr[to];
    arr[to] = arr[l];
    arr[l] = t;
    return l;
  }

  public void correctInPlaceQuickSort(int[] arr, int from, int to){
    int l;
    while(from < to){
      l = inPlacePartition(arr,from,to);
      if(l-from < to-l){ // first subarray is smaller
        correctInPlaceQuickSort(arr,from,l-1);
        from = l+1;
      }else{
        correctInPlaceQuickSort(arr,l+1,to);
        to = l-1;
      }
    }
  }


  // merges to sorted arrays together
  public int[] merge(int[] arr_1, int[] arr_2, int[] notReallyEmptyArray){
    // arr_1 and arr_2 have size of n1 and n2, and are respectivly, sorted in
    // non decresing order.
    int[] emptyArray = new int[arr_1.length + arr_2.length];
    // starting from 0 so i will also sort index 0
    int i = 0; // for arr_1
    int j = 0; // for arr_2
    while(i < arr_1.length && j < arr_2.length){
      if(arr_1[i] <= arr_2[j]){
        emptyArray[i+j] = arr_1[i];
        notReallyEmptyArray[i+j] = arr_1[i]; // to do it "in-place"
        i++;
      }else{ // arr_1[i] > arr_2[j]
        emptyArray[i+j] = arr_2[j];
        notReallyEmptyArray[i+j] = arr_2[j]; // to do it "in-place"
        j++;
      }
    }
    while(i < arr_1.length){
      emptyArray[i+j] = arr_1[i];
      notReallyEmptyArray[i+j] = arr_1[i]; // to do it "in-place"
      i++;
    }
    while(j < arr_2.length){
      emptyArray[i+j] = arr_2[j];
      notReallyEmptyArray[i+j] = arr_2[j]; // to do it "in-place"
      j++;
    }
    return emptyArray;
  }

  public int[] mergeSort(int[] arr){
    // System.out.println(Arrays.toString(arr));
    if(arr.length == 1){
      return arr;
    }
    int n = arr.length;
    int[] arrayOne = Arrays.copyOfRange(arr, 0, (n + 1)/2); // for 0 too half
    int[] arrayTwo = Arrays.copyOfRange(arr, (n + 1)/2, n); // from half+1 to end
    // recursion
    arrayOne = mergeSort(arrayOne);
    arrayTwo = mergeSort(arrayTwo);
    return merge(arrayOne, arrayTwo, arr);
  }




}
