package abc.model;

public class LabelGenerator {
    int dimension;

    public LabelGenerator(int dimension) {
        this.dimension = dimension;
    }

    public String[] generateVectorLabels(String symbol) {
        String[] result = new String[dimension];

        for (int i = 1; i <= dimension; i++) {
            result[i-1] = symbol + i;
        }

        return result;
    }

    public String[][] generateMatrixLabels(String symbol) {
        String[][] result = new String[dimension][dimension];

        for (int i = 1; i <= dimension; i++) {
            for (int j = 1; j <= dimension; j++) {
                result[i-1][j-1] = symbol + i + j;
            }
        }
        
        return result;
    }
}
