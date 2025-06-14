import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Classe para exportar resultados para CSV
 */
public class ExportarResultado {
    
    /**
     * Exporta os resultados detalhados e médias para CSV
     * @param detalhesResultado Lista de resultados detalhados
     * @param resultadoMedios Lista de resultados médios
     * @param filename Nome do arquivo
     */
    public static void exportToCSV(List<TesteResultado> detalhesResultado,
                                  List<ResultadoMedio> resultadoMedios,
                                  String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            // Cabeçalho dos resultados detalhados
            writer.println("=== RESULTADOS DETALHADOS ===");
            writer.println("Algoritmo,Grupo,Tamanho,Rodada,Tempo(ns),Tempo(ms),Trocas,Iteracoes");
            
            // Dados detalhados
            for (TesteResultado result : detalhesResultado) {
                writer.printf("%s,%s,%d,%d,%d,%.2f,%d,%d%n",
                    result.getAlgorithm(),
                    result.getGroup(),
                    result.getSize(),
                    result.getRound(),
                    result.getExecutionTime(),
                    result.getExecutionTime() / 1_000_000.0,
                    result.getSwaps(),
                    result.getIterations()
                );
            }
            
            // Separador
            writer.println();
            writer.println("=== RESULTADOS MEDIOS ===");
            writer.println("Algoritmo,Grupo,Tamanho,Tempo_Medio(ns),Tempo_Medio(ms),Trocas_Medias,Iteracoes_Medias");
            
            // Dados médios
            for (ResultadoMedio result : resultadoMedios) {
                writer.printf("%s,%s,%d,%.0f,%.2f,%.0f,%.0f%n",
                    result.getAlgorithm(),
                    result.getGroup(),
                    result.getSize(),
                    result.getAvgTime(),
                    result.getAvgTime() / 1_000_000.0,
                    result.getAvgSwaps(),
                    result.getAvgIterations()
                );
            }
            
            System.out.println("Dados exportados para: " + filename);
            
        } catch (IOException e) {
            System.err.println("Erro ao exportar dados: " + e.getMessage());
        }
    }
    
    /**
     * Exporta tabela comparativa por métrica
     * @param resultadoMedios Lista de resultados médios
     * @param filename Nome do arquivo
     */
    public static void exportComparativeTable(List<ResultadoMedio> resultadoMedios, String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            int[] sizes = {1000, 10000, 100000, 500000, 1000000};
            String[] algorithms = {"Selection Sort", "Merge Sort", "Shell Sort", "Gnome Sort"};
            
            // Tabela de Tempo de Execução
            writer.println("=== TEMPO DE EXECUCAO (ms) ===");
            writer.print("Tamanho,");
            for (String algo : algorithms) {
                writer.print(algo + ",");
            }
            writer.println();
            
            for (int size : sizes) {
                writer.print(size + ",");
                for (String algo : algorithms) {
                    ResultadoMedio result = findResult(resultadoMedios, algo, size);
                    if (result != null) {
                        writer.printf("%.2f,", result.getAvgTime() / 1_000_000.0);
                    } else {
                        writer.print("-,");
                    }
                }
                writer.println();
            }
            
            // Tabela de Trocas
            writer.println();
            writer.println("=== NUMERO DE TROCAS ===");
            writer.print("Tamanho,");
            for (String algo : algorithms) {
                writer.print(algo + ",");
            }
            writer.println();
            
            for (int size : sizes) {
                writer.print(size + ",");
                for (String algo : algorithms) {
                    ResultadoMedio result = findResult(resultadoMedios, algo, size);
                    if (result != null) {
                        writer.printf("%.0f,", result.getAvgSwaps());
                    } else {
                        writer.print("-,");
                    }
                }
                writer.println();
            }
            
            // Tabela de Iterações
            writer.println();
            writer.println("=== NUMERO DE ITERACOES ===");
            writer.print("Tamanho,");
            for (String algo : algorithms) {
                writer.print(algo + ",");
            }
            writer.println();
            
            for (int size : sizes) {
                writer.print(size + ",");
                for (String algo : algorithms) {
                    ResultadoMedio result = findResult(resultadoMedios, algo, size);
                    if (result != null) {
                        writer.printf("%.0f,", result.getAvgIterations());
                    } else {
                        writer.print("-,");
                    }
                }
                writer.println();
            }
            
            System.out.println("Tabela comparativa exportada para: " + filename);
            
        } catch (IOException e) {
            System.err.println("Erro ao exportar tabela comparativa: " + e.getMessage());
        }
    }
    
    private static ResultadoMedio findResult(List<ResultadoMedio> results, String algorithm, int size) {
        return results.stream()
                .filter(r -> r.getAlgorithm().equals(algorithm) && r.getSize() == size)
                .findFirst()
                .orElse(null);
    }
}
