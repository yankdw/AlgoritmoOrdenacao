public class SortResultado {
    private final int swaps;
    private final int iterations;
    private final long executionTime;
    
    public SortResultado(int swaps, int iterations, long executionTime) {
        this.swaps = swaps;
        this.iterations = iterations;
        this.executionTime = executionTime;
    }
    
    public int getSwaps() {
        return swaps;
    }
    
    public int getIterations() {
        return iterations;
    }
    
    public long getExecutionTime() {
        return executionTime;
    }
}
