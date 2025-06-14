public class ResultadoMedio {
    private final String algorithm;
    private final String group;
    private final int size;
    private final double avgTime;
    private final double avgSwaps;
    private final double avgIterations;
    
    public ResultadoMedio(String algorithm, String group, int size,
                          double avgTime, double avgSwaps, double avgIterations) {
        this.algorithm = algorithm;
        this.group = group;
        this.size = size;
        this.avgTime = avgTime;
        this.avgSwaps = avgSwaps;
        this.avgIterations = avgIterations;
    }

    public String getAlgorithm() { return algorithm; }
    public String getGroup() { return group; }
    public int getSize() { return size; }
    public double getAvgTime() { return avgTime; }
    public double getAvgSwaps() { return avgSwaps; }
    public double getAvgIterations() { return avgIterations; }
    
    @Override
    public String toString() {
        return String.format("%s (Grupo %s) - Tamanho: %d, Tempo Médio: %.2fms, Trocas Médias: %.0f, Iterações Médias: %.0f",
                algorithm, group, size, avgTime / 1_000_000.0, avgSwaps, avgIterations);
    }
}
