package abc.model;

import java.security.InvalidParameterException;
import java.util.HashMap;

import abc.model.LinearAlgebra.*;

public class GeneralizedLotkaVolterra {

    public Parameters params;
    public HashMap<String, double[]> data;

    public static class Parameters {

        public Matrix communityMatrix = new Matrix(new double[][] {{}});
        public Vector forcingVector = new Vector(new double[] {});
        public Vector initConditions = new Vector(new double[] {});
        public int timeSteps = 0;

        public Parameters(Matrix _communityMatrix, Vector _forcingVector, Vector _initConditions, int _timeSteps) {
            
            this.communityMatrix = _communityMatrix;
            this.forcingVector = _forcingVector;
            this.initConditions = _initConditions;
            this.timeSteps = _timeSteps;
            
        }
    }
    
    public void setParameters(Parameters _params) {
        if ((_params.forcingVector.dimension != _params.communityMatrix.dimension[1]) || (_params.forcingVector.dimension != _params.initConditions.dimension)) {
            throw new InvalidParameterException("Dimensions of the tensors are incorrect.");
        }
        if ((_params.timeSteps < 1) || (_params.timeSteps > 250)) {
            throw new InvalidParameterException("The number of time steps must be between 1 and 250.");
        }
        else {
            this.params = _params;
        }
    }

    private Vector dynamicsFunction(Vector x) {
        return (params.communityMatrix.multVec(x)).sum(params.forcingVector);
    };

    public Vector dynamicalRule(Vector x) {
        double[] result = new double[x.dimension];
        Vector dynamicsFunctionValues = dynamicsFunction(x);

        for (int index = 0; index < x.dimension; index++) {
            result[index] = Math.max(x.values[index] + x.values[index] * dynamicsFunctionValues.values[index], 0);
        }

        return new Vector(result);
    }

    // This method runs the model
    public void run() {
        
        int dimension = params.initConditions.dimension;
        HashMap<String, double[]> result = new HashMap<String, double[]>();
        String[] names = new String[dimension];

        // Initialize results hashmap
        for (int populationIndex = 1; populationIndex <= dimension; populationIndex++) {
            String name = "Population " + populationIndex;
            names[populationIndex - 1] = name;
            result.put(name, new double[params.timeSteps]);
            result.get(name)[0] = params.initConditions.values[populationIndex - 1];
        }

        Vector prevValues = params.initConditions;

        // Iterate through timesteps
        for (int t = 1; t < params.timeSteps; t++) {

            Vector nextValues = dynamicalRule(prevValues);

            for (int populationIndex = 1; populationIndex <= dimension; populationIndex++) {
                result.get(names[populationIndex - 1])[t] = nextValues.values[populationIndex - 1];
                prevValues = nextValues;
            }
        }

        this.data = result;
    }
    
}
