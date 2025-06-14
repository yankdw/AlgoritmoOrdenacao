/**
 * Implementação do Shell Sort - Grupo B
 */
public class ShellSort implements OrdenaAlgoritmo {
    
    @Override
    public SortResultado sort(int[] arr) {
        long startTime = System.nanoTime();
        int swaps = 0;
        int iterations = 0;
        int n = arr.length;
        
        // Sequência de gaps (Knuth)
        int gap = 1;
        while (gap < n / 3) {
            gap = gap * 3 + 1;
        }
        
        while (gap >= 1) {
            for (int i = gap; i < n; i++) {
                int temp = arr[i];
                int j = i;
                
                while (j >= gap && arr[j - gap] > temp) {
                    iterations++;
                    arr[j] = arr[j - gap];
                    j -= gap;
                    swaps++;
                }
                
                if (j >= gap) iterations++; // Conta a comparação que falhou
                arr[j] = temp;
            }
            gap = gap / 3;
        }
        
        long endTime = System.nanoTime();
        return new SortResultado(swaps, iterations, endTime - startTime);
    }
    
    @Override
    public String getName() {
        return "Shell Sort";
    }
    
    @Override
    public String getGroup() {
        return "B";
    }
}
