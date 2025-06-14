import java.util.*;

/**
 * Classe principal para análise dos algoritmos de ordenação
 */
public class AnalisaOrdem {
    private static final int[] VECTOR_SIZES = {1000, 10000, 100000, 500000, 1000000};
    private static final int ROUNDS = 5;
    
    private final List<OrdenaAlgoritmo> algorithms;
    private final List<TesteResultado> detailedResults;
    private final List<ResultadoMedio> resultadoMedios;
    
    public AnalisaOrdem() {
        this.algorithms = Arrays.asList(
            new SelectionSort(),
            new MergeSort(),
            new ShellSort(),
            new GnomeSort()
        );
        this.detailedResults = new ArrayList<>();
        this.resultadoMedios = new ArrayList<>();
    }
    
    public static void main(String[] args) {
        AnalisaOrdem analyzer = new AnalisaOrdem();
        analyzer.runAnalysis();
    }
    
    /**
     * Executa a análise completa dos algoritmos
     */
    public void runAnalysis() {
        System.out.println("=".repeat(80));
        System.out.println("           ANÁLISE DE ALGORITMOS DE ORDENAÇÃO");
        System.out.println("=".repeat(80));
        System.out.println();
        
        printConfiguracao();
        
        System.out.println("Iniciando testes...\n");
        
        int totalTests = algorithms.size() * VECTOR_SIZES.length * ROUNDS;
        int completedTests = 0;
        
        for (OrdenaAlgoritmo algorithm : algorithms) {
            System.out.println("Testando: " + algorithm.getName() + " (Grupo " + algorithm.getGroup() + ")");
            
            for (int size : VECTOR_SIZES) {
                System.out.printf("  Tamanho %s: ", formatNumber(size));
                
                for (int round = 1; round <= ROUNDS; round++) {
                    // Gerar seed baseada no algoritmo, tamanho e rodada
                    long seed = algorithm.getName().hashCode() + size + round;
                    int[] vector = GeradorAleatorio.generateRandomArray(size, seed);
                    
                    // Executar teste
                    TesteResultado result = runSingleTest(algorithm, vector, size, round);
                    detailedResults.add(result);
                    
                    completedTests++;
                    System.out.print(".");
                    
                    // Mostrar progresso a cada 25%
                    if (completedTests % (totalTests / 4) == 0) {
                        int progress = (completedTests * 100) / totalTests;
                        System.out.printf(" [%d%%]", progress);
                    }
                }
                System.out.println(" ✓");
            }
            System.out.println();
        }
        
        // Calcular médias
        calculaMedias();
        
        // Mostrar resultados
        mostraResultados();
        
        // Exportar dados
        exportaResultados();
        
        System.out.println("\nAnálise concluída!");
    }
    
    /**
     * Executa um teste individual
     */
    private TesteResultado runSingleTest(OrdenaAlgoritmo algorithm, int[] vector, int size, int round) {
        int[] vectorCopy = GeradorAleatorio.copyArray(vector);
        SortResultado result = algorithm.sort(vectorCopy);
        
        return new TesteResultado(
            algorithm.getName(),
            algorithm.getGroup(),
            size,
            round,
            result.getExecutionTime(),
            result.getSwaps(),
            result.getIterations()
        );
    }
    
    /**
     * Calcula as médias dos resultados
     */
    private void calculaMedias() {
        for (OrdenaAlgoritmo algorithm : algorithms) {
            for (int size : VECTOR_SIZES) {
                List<TesteResultado> algorithmResults = detailedResults.stream()
                    .filter(r -> r.getAlgorithm().equals(algorithm.getName()) && r.getSize() == size)
                    .toList();
                
                double avgTime = algorithmResults.stream()
                    .mapToLong(TesteResultado::getExecutionTime)
                    .average()
                    .orElse(0.0);
                
                double avgSwaps = algorithmResults.stream()
                    .mapToInt(TesteResultado::getSwaps)
                    .average()
                    .orElse(0.0);
                
                double avgIterations = algorithmResults.stream()
                    .mapToInt(TesteResultado::getIterations)
                    .average()
                    .orElse(0.0);
                
                resultadoMedios.add(new ResultadoMedio(
                    algorithm.getName(),
                    algorithm.getGroup(),
                    size,
                    avgTime,
                    avgSwaps,
                    avgIterations
                ));
            }
        }
    }
    
    /**
     * Exibe a configuração dos testes
     */
    private void printConfiguracao() {
        System.out.println("CONFIGURAÇÃO DOS TESTES:");
        System.out.println("• Algoritmos:");
        for (OrdenaAlgoritmo algo : algorithms) {
            System.out.printf("  - %s (Grupo %s)%n", algo.getName(), algo.getGroup());
        }
        
        System.out.print("• Tamanhos dos vetores: ");
        for (int i = 0; i < VECTOR_SIZES.length; i++) {
            System.out.print(formatNumber(VECTOR_SIZES[i]));
            if (i < VECTOR_SIZES.length - 1) System.out.print(", ");
        }
        System.out.println();
        
        System.out.println("• Rodadas por configuração: " + ROUNDS);
        System.out.println("• Seeds diferentes para cada rodada (reprodutibilidade garantida)");
        System.out.println("• Métricas: Tempo de execução, Número de trocas, Número de iterações");
        System.out.println();
    }
    
    /**
     * Exibe os resultados da análise
     */
    private void mostraResultados() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("                           RESULTADOS");
        System.out.println("=".repeat(80));
        
        // Tabela de tempo de execução
        mostraTimeTable();
        
        // Tabela de trocas
        mostraSwapsTable();
        
        // Tabela de iterações
        mostraIterationsTable();
        
        // Resumo por algoritmo
        mostraSumario();
    }
    
    /**
     * Exibe tabela de tempo de execução
     */
    private void mostraTimeTable() {
        System.out.println("\nTEMPO DE EXECUÇÃO (ms):");
        System.out.println("-".repeat(80));
        
        System.out.printf("%-12s", "Tamanho");
        for (OrdenaAlgoritmo algo : algorithms) {
            System.out.printf("%-15s", algo.getName());
        }
        System.out.println();
        System.out.println("-".repeat(80));
        
        for (int size : VECTOR_SIZES) {
            System.out.printf("%-12s", formatNumber(size));
            for (OrdenaAlgoritmo algo : algorithms) {
                ResultadoMedio result = findAverageResult(algo.getName(), size);
                if (result != null) {
                    double timeMs = result.getAvgTime() / 1_000_000.0;
                    if (timeMs < 1000) {
                        System.out.printf("%-15.2f", timeMs);
                    } else {
                        System.out.printf("%-15.1f", timeMs);
                    }
                } else {
                    System.out.printf("%-15s", "-");
                }
            }
            System.out.println();
        }
    }
    
    /**
     * Exibe tabela de número de trocas
     */
    private void mostraSwapsTable() {
        System.out.println("\nNÚMERO DE TROCAS:");
        System.out.println("-".repeat(80));
        
        System.out.printf("%-12s", "Tamanho");
        for (OrdenaAlgoritmo algo : algorithms) {
            System.out.printf("%-15s", algo.getName());
        }
        System.out.println();
        System.out.println("-".repeat(80));
        
        for (int size : VECTOR_SIZES) {
            System.out.printf("%-12s", formatNumber(size));
            for (OrdenaAlgoritmo algo : algorithms) {
                ResultadoMedio result = findAverageResult(algo.getName(), size);
                if (result != null) {
                    System.out.printf("%-15s", formatNumber((int) result.getAvgSwaps()));
                } else {
                    System.out.printf("%-15s", "-");
                }
            }
            System.out.println();
        }
    }
    
    /**
     * Exibe tabela de número de iterações
     */
    private void mostraIterationsTable() {
        System.out.println("\nNÚMERO DE ITERAÇÕES:");
        System.out.println("-".repeat(80));
        
        System.out.printf("%-12s", "Tamanho");
        for (OrdenaAlgoritmo algo : algorithms) {
            System.out.printf("%-15s", algo.getName());
        }
        System.out.println();
        System.out.println("-".repeat(80));
        
        for (int size : VECTOR_SIZES) {
            System.out.printf("%-12s", formatNumber(size));
            for (OrdenaAlgoritmo algo : algorithms) {
                ResultadoMedio result = findAverageResult(algo.getName(), size);
                if (result != null) {
                    System.out.printf("%-15s", formatNumber((int) result.getAvgIterations()));
                } else {
                    System.out.printf("%-15s", "-");
                }
            }
            System.out.println();
        }
    }
    
    /**
     * Exibe resumo por algoritmo
     */
    private void mostraSumario() {
        System.out.println("\nRESUMO POR ALGORITMO:");
        System.out.println("-".repeat(80));
        
        for (OrdenaAlgoritmo algo : algorithms) {
            System.out.printf("\n%s (Grupo %s):%n", algo.getName(), algo.getGroup());
            
            List<ResultadoMedio> algoResults = resultadoMedios.stream()
                .filter(r -> r.getAlgorithm().equals(algo.getName()))
                .toList();
            
            for (ResultadoMedio result : algoResults) {
                System.out.printf("  Tamanho %s: %.2fms, %s trocas, %s iterações%n",
                    formatNumber(result.getSize()),
                    result.getAvgTime() / 1_000_000.0,
                    formatNumber((int) result.getAvgSwaps()),
                    formatNumber((int) result.getAvgIterations())
                );
            }
        }
    }
    
    /**
     * Exporta os resultados para arquivos CSV
     */
    private void exportaResultados() {
        System.out.println("\nExportando resultados...");
        
        // Exportar dados completos
        ExportarResultado.exportToCSV(detailedResults, resultadoMedios, "analise_algoritmos_completa.csv");
        
        // Exportar tabela comparativa
        ExportarResultado.exportComparativeTable(resultadoMedios, "tabela_comparativa.csv");
    }
    
    /**
     * Encontra resultado médio por algoritmo e tamanho
     */
    private ResultadoMedio findAverageResult(String algorithm, int size) {
        return resultadoMedios.stream()
            .filter(r -> r.getAlgorithm().equals(algorithm) && r.getSize() == size)
            .findFirst()
            .orElse(null);
    }
    
    /**
     * Formata números com separadores de milhares
     */
    private String formatNumber(int number) {
        return String.format("%,d", number).replace(',', '.');
    }
}
