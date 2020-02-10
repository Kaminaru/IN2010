class ArrayMax{

  public static void main(String[] args) {
    int[] array = {1,3,5,6,10,23,125,2,3,1,0,12315,3,5,7,342,66}; // array with integers
    System.out.println(ArrayMax(array));
  }

  static int ArrayMax(int[] ar){
    int currentMax = ar[0]; // First number in array will take "max value"
    for(int i = 1; i < ar.length; i++){ // i starts from 1 because we already
      // used 0 spot from array, so we dont need to check it twice
      if(currentMax < ar[i]) currentMax = ar[i];
    }
    return currentMax;
  }
}
