import java.util.*;

public class DellisteMSS{
private static LinkedList<Integer> lenkeliste; // The main lenkedlist with all numbers
// as i understood from task, from long node with numbers, program must find 4
// numbers that will give the biggest output number

  public static void main(String[] args) {
    lenkeliste = new LinkedList<Integer>();
    ////////////////////////////////
    // everything here only to crate test LinkedList with nodes with numbers in it
    int[] testArray = {-2,-4,3,-1,5,6,-7,-2,4,-3,2};
    for(int i : testArray){
      lenkeliste.add(i);
    }
    //////////////////////////////
    System.out.println(dlmss()); // printing out for test. Must get 13
  }

  // Algorithm that finds part list from the main lenkedlist with biggest sum
  static int dlmss(){
    int sum = 0; // biggest sum
    int shortSum = 0;
    for(int i = 0; i < lenkeliste.size(); i++){ // goes through lenkedlist.
      for(int j = i; j < lenkeliste.size(); j++){ // starts for i and goes up until the end of lenkedlist
        shortSum += lenkeliste.get(j); // adds numbers one by one to the shortSum
        // if new sum is bigger than the old one, we will change old sum to the new one
        if(shortSum > sum){
          sum = shortSum;
        }
      }
      shortSum = 0; // shortSum resets after we go to next i (next "main" number from lenkedlist)
    }
    return sum;
  }
}
