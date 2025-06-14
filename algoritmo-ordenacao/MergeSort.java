/**
 * Implementação do Merge Sort - Grupo B
 */
public class MergeSort implements OrdenaAlgoritmo {
    private int swaps;
    private int iterations;
    
    @Override
    public SortResultado sort(int[] arr) {
        long startTime = System.nanoTime();
        swaps = 0;
        iterations = 0;
        
        mergeSort(arr, 0, arr.length - 1);
        
        long endTime = System.nanoTime();
        return new SortResultado(swaps, iterations, endTime - startTime);
    }
    
    private void mergeSort(int[] arr, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }
    
    private void merge(int[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;
        
        int[] leftArr = new int[n1];
        int[] rightArr = new int[n2];
        
        System.arraycopy(arr, left, leftArr, 0, n1);
        System.arraycopy(arr, mid + 1, rightArr, 0, n2);
        
        int i = 0, j = 0, k = left;
        
        while (i < n1 && j < n2) {
            iterations++;
            if (leftArr[i] <= rightArr[j]) {
                arr[k] = leftArr[i];
                i++;
            } else {
                arr[k] = rightArr[j];
                j++;
                swaps++; // Considerando como uma "troca" conceitual
            }
            k++;
        }
        
        while (i < n1) {
            arr[k] = leftArr[i];
            i++;
            k++;
            iterations++;
        }
        
        while (j < n2) {
            arr[k] = rightArr[j];
            j++;
            k++;
            iterations++;
        }
    }
    
    @Override
    public String getName() {
        return "Merge Sort";
    }
    
    @Override
    public String getGroup() {
        return "B";
    }
}
