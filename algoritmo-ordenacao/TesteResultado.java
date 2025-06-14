/**
 * Classe para armazenar o resultado de um teste individual
 */
public class TesteResultado {
    private final String algorithm;
    private final String group;
    private final int size;
    private final int round;
    private final long executionTime;
    private final int swaps;
    private final int iterations;
    
    public TesteResultado(String algorithm, String group, int size, int round,
                          long executionTime, int swaps, int iterations) {
        this.algorithm = algorithm;
        this.group = group;
        this.size = size;
        this.round = round;
        this.executionTime = executionTime;
        this.swaps = swaps;
        this.iterations = iterations;
    }
    
    // Getters
    public String getAlgorithm() { return algorithm; }
    public String getGroup() { return group; }
    public int getSize() { return size; }
    public int getRound() { return round; }
    public long getExecutionTime() { return executionTime; }
    public int getSwaps() { return swaps; }
    public int getIterations() { return iterations; }
    
    @Override
    public String toString() {
        return String.format("%s (Grupo %s) - Tamanho: %d, Rodada: %d, Tempo: %.2fms, Trocas: %d, Iterações: %d",
                algorithm, group, size, round, executionTime / 1_000_000.0, swaps, iterations);
    }
}
