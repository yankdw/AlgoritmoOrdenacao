/**
 * Interface para algoritmos de ordenação
 */
public interface OrdenaAlgoritmo {
    /**
     * Ordena o array e retorna as estatísticas
     * @param arr Array a ser ordenado
     * @return Resultado com estatísticas da ordenação
     */
    SortResultado sort(int[] arr);
    
    /**
     * Retorna o nome do algoritmo
     * @return Nome do algoritmo
     */
    String getName();
    
    /**
     * Retorna o grupo do algoritmo (A, B ou C)
     * @return Grupo do algoritmo
     */
    String getGroup();
}
