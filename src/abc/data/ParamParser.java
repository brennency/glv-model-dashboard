package abc.data;
import abc.model.LabelGenerator;
import abc.model.LinearAlgebra.*;

import java.io.IOException;
import jakarta.servlet.http.*;

public class ParamParser {
    
    public Matrix parseMatrixRequest(HttpServletRequest req) throws IOException {

        int paramDimension = Integer.parseInt(req.getParameter("param-dimension"));
        LabelGenerator labelGenerator = new LabelGenerator(paramDimension);

        String[][] matrixLabels = labelGenerator.generateMatrixLabels("a");
        double[][] matrixValues = new double[paramDimension][paramDimension];

        try {

            // Get parameter values from request
            for (int i = 0; i < paramDimension; i++) {
                for (int j = 0; j < paramDimension; j++) {
                    matrixValues[i][j] = Double.parseDouble(
                        req.getParameter(matrixLabels[i][j]));
                }
            }
            return new Matrix(matrixValues);
        } 
        catch (Exception e) {
            throw new IOException("An error occured processing matrix parameters.");
        }
    } 

    public Vector parseVectorRequest(HttpServletRequest req, String symbol) 
        throws IOException{

        int paramDimension = Integer.parseInt(req.getParameter("param-dimension"));
        LabelGenerator labelGenerator = new LabelGenerator(paramDimension);

        String[] vectorLabels = labelGenerator.generateVectorLabels(symbol);
        double[] vectorValues = new double[paramDimension];

        try {

            // Get parameter values from request
            for (int i = 0; i < paramDimension; i++) {
                double parsedValue = Double.parseDouble(req.getParameter(vectorLabels[i]));

                // initial condition values must be positve
                vectorValues[i] = symbol == "ic" ? Math.max(0, parsedValue) : parsedValue;
            }
            return new Vector(vectorValues);
        } 
        catch (Exception e) {
            throw new IOException("An error occurred processing vector parameters.");
        }
    }
}
