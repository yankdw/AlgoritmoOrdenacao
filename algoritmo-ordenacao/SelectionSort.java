/**
 * Implementação do Selection Sort - Grupo A
 */
public class SelectionSort implements OrdenaAlgoritmo {
    
    @Override
    public SortResultado sort(int[] arr) {
        long startTime = System.nanoTime();
        int swaps = 0;
        int iterations = 0;
        int n = arr.length;
        
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            
            for (int j = i + 1; j < n; j++) {
                iterations++;
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            
            if (minIndex != i) {
                // Troca
                int temp = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = temp;
                swaps++;
            }
        }
        
        long endTime = System.nanoTime();
        return new SortResultado(swaps, iterations, endTime - startTime);
    }
    
    @Override
    public String getName() {
        return "Selection Sort";
    }
    
    @Override
    public String getGroup() {
        return "A";
    }
}
