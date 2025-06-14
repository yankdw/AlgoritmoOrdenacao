public class GnomeSort implements OrdenaAlgoritmo {
    
    @Override
    public SortResultado sort(int[] arr) {
        long startTime = System.nanoTime();
        int swaps = 0;
        int iterations = 0;
        int index = 0;
        int n = arr.length;
        
        while (index < n) {
            if (index == 0) {
                index++;
            } else {
                iterations++;
                if (arr[index] >= arr[index - 1]) {
                    index++;
                } else {
                    // Troca
                    int temp = arr[index];
                    arr[index] = arr[index - 1];
                    arr[index - 1] = temp;
                    swaps++;
                    index--;
                }
            }
        }
        
        long endTime = System.nanoTime();
        return new SortResultado(swaps, iterations, endTime - startTime);
    }
    
    @Override
    public String getName() {
        return "Gnome Sort";
    }
    
    @Override
    public String getGroup() {
        return "C";
    }
}
