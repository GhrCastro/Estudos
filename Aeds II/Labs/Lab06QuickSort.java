package Labs;
import java.util.*;

public class Lab06QuickSort{
    
    public static void swap (int [] array, int i, int j){
        int tmp = array[j];
        array[j] = array[i];
        array[i] = tmp;
    }

    public static void QuickSortFirstPivot(int [] array, int left, int right){
        int i = left, j = right;
        int pivot = left;

        while(i<=j){
            while(array[i] < pivot) i++;
            while(array[j] > pivot) j--;
            if(i <= j){
                swap(array,i,j);
                i++;
                j--;
            }
        }
        if(left < j) QuickSortFirstPivot(array,left,j);
        if(i < right) QuickSortFirstPivot(array,i,right);
    }

    public static void QuickSortLastPivot(int [] array, int left, int right){
        int i = left, j = right;
        int pivot = right;

        while(i<=j){
            while(array[i] < pivot) i++;
            while(array[j] > pivot) j--;
            if(i <= j){
                swap(array,i,j);
                i++;
                j--;
            }
        }
        if(left < j) QuickSortLastPivot(array,left,j);
        if(i < right) QuickSortLastPivot(array,i,right);
    }
    
    public static void QuickSortRandomPivot(int [] array, int left, int right){
        Random gerador = new Random();
        int i = left, j = right;
        int pivot = gerador.nextInt();
        

        while(i<=j){
            while(array[i] < pivot) i++;
            while(array[j] > pivot) j--;
            if(i <= j){
                swap(array,i,j);
                i++;
                j--;
            }
        }
        if(left < j) QuickSortRandomPivot(array,left,j);
        if(i < right) QuickSortRandomPivot(array,i,right);
    }

    public static void QuickSortMedianOfThree(int [] array, int left, int right){
        Random gerador = new Random();
        int i = left, j = right;
        int pivot = gerador.nextInt();
        

        while(i<=j){
            while(array[i] < pivot) i++;
            while(array[j] > pivot) j--;
            if(i <= j){
                swap(array,i,j);
                i++;
                j--;
            }
        }
        if(left < j) QuickSortRandomPivot(array,left,j);
        if(i < right) QuickSortRandomPivot(array,i,right);
    }


    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        sc.close();
    }
}