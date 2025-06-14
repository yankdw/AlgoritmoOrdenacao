import java.util.Random;

public class GeradorAleatorio {
    
    /**
     * Gera um array de inteiros aleatórios com seed específica
     * @param size Tamanho do array
     * @param seed Seed para reprodutibilidade
     * @return Array de inteiros aleatórios
     */
    public static int[] generateRandomArray(int size, long seed) {
        Random random = new Random(seed);
        int[] array = new int[size];
        
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(100000); // Números de 0 a 99999
        }
        
        return array;
    }
    
    /**
     * Cria uma cópia do array
     * @param original Array original
     * @return Cópia do array
     */
    public static int[] copyArray(int[] original) {
        int[] copy = new int[original.length];
        System.arraycopy(original, 0, copy, 0, original.length);
        return copy;
    }
}
