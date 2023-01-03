package abc.dao;

import java.util.HashMap;

import abc.model.GeneralizedLotkaVolterra;
import abc.model.GeneralizedLotkaVolterra.Parameters;
import abc.model.LinearAlgebra.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ModelDao {

    public GeneralizedLotkaVolterra.Parameters[] sampleParams = new GeneralizedLotkaVolterra.Parameters[1]; 
    public GeneralizedLotkaVolterra model;
    
    public ModelDao() {

        // Create sample model
        double[][] rows = {{-0.13, 0.0, 0.023, 0.1}, {-0.01, -0.1, 0.0, -0.07}, {0.01, 0.12, -0.015, 0.012}, {-0.05, -0.06, 0.0, -0.1}};
        Matrix A = new Matrix(rows);
        double[] vector = {0d, 0d, 0d, 0d};
        Vector r = new Vector(vector);
        Vector initConditions = new Vector(new double[] {.5, .4, .3, .2});
        int timeSteps = 200;
    
        // Set parameters of model
        Parameters sampleParam = new Parameters(A, r, initConditions, timeSteps);
        sampleParams[0] = sampleParam;
    }

    public void createModel() {
        this.model = new GeneralizedLotkaVolterra();
    } 

    public void setModelParameters(Parameters params) {
        model.setParameters(params);
    }

    public HashMap<String, double[]> getModelData() {
        if (model.data== null) {
            model.run();
    }    
        return model.data;
    }

    public String getJSON() {
        HashMap<String, double[]> modelData = getModelData();
        JSONObject results = new JSONObject();

        for (String name : modelData.keySet()) {
            JSONArray list = new JSONArray();
            double[] values = modelData.get(name);
            
            for (double value : values) {
                list.add(value);
            }

            results.put(name, list);
        }

        return results.toJSONString();
    }
    
}
