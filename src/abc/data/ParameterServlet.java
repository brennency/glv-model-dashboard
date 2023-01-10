package abc.data;

import java.io.IOException;
import java.util.Random;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import abc.model.GeneralizedLotkaVolterra.Parameters;
import abc.model.LinearAlgebra.*;
import abc.dao.ModelDao;
import abc.data.ParamParser;

@WebServlet("/Params")
public class ParameterServlet extends HttpServlet {

    //  Helper method for truncating generated parameters to only 3 decimal places
    private double truncateDigits(double value) {
        return Double.parseDouble(String.format("%.3f", value));
    }

    /*  Helper method for generating random model parameters
     *  Uses param-dimension and timestep attributes from the incoming servlet request    
     *  Note: Internally scoped parameters which generate the random entries are 
     *       highly arbitrary 
    */ 
    private Parameters randomizeParams(HttpServletRequest req) throws IOException {
        int paramDimension = Integer.parseInt(req.getParameter("param-dimension"));
        int timeSteps = Integer.parseInt(req.getParameter("timesteps"));

        Random r = new Random();

        double[][] randomCommunityMatrix = new double[paramDimension][paramDimension];
        double[] randomForcingVector = new double[paramDimension];
        double[] randomInitConditions = new double[paramDimension]; 
        
        for (int i = 0; i < paramDimension; i++) {
            randomForcingVector[i] = truncateDigits(2*r.nextDouble() - 1);
            randomInitConditions[i] = truncateDigits(r.nextDouble());

            for (int j = 0; j < paramDimension; j++) {

                /*  restrict diagonal matrix values to negative numbers
                 *       for reasons relating to the stability of the resultant model
                */
                if (i == j) {
                    randomCommunityMatrix[i][j] = truncateDigits(-0.5*r.nextDouble());
                }
                else {
                    randomCommunityMatrix[i][j] = truncateDigits(0.125*(2*r.nextDouble() - 1));
                }
            }
        }

        return new Parameters(
            new Matrix(randomCommunityMatrix), new Vector(randomForcingVector), 
            new Vector(randomInitConditions), timeSteps);

    }

    /*  Helper method for perturbing the tensor parameters sent by client
     *  Uses param-dimension and timestep attributes from the incoming servlet request
     *  Utilizes ParamParser object to read current tensor parameters from incoming request
     *  Note: Internally scoped parameters which generate the random entries are 
     *      highly arbitrary 
    */ 
    private Parameters perturbParams(HttpServletRequest req) throws IOException {
        int paramDimension = Integer.parseInt(req.getParameter("param-dimension"));
        int timeSteps = Integer.parseInt(req.getParameter("timesteps"));

        Random r = new Random();
        ParamParser parser = new ParamParser();

        Matrix currentCommunityMatrix = parser.parseMatrixRequest(req);
        Vector currentForcingVector = parser.parseVectorRequest(req, "r");
        Vector currentInitConditions = parser.parseVectorRequest(req, "ic");

        double[][] perturbedCommunityMatrix = new double[paramDimension][paramDimension];
        double[] perturbedForcingVector = new double[paramDimension];
        double[] perturbedInitConditions = new double[paramDimension]; 

        for (int i = 0; i < paramDimension; i++) {
            
            /*  The second factor in each perturbed expression guarantees that 0 values 
             *      will be perturbed away from 0
            */
            perturbedForcingVector[i] = truncateDigits(
                (1 + 0.05*(2*r.nextDouble() - 1))*(0.01*(2*r.nextDouble() - 1) + currentForcingVector.values[i]));
            perturbedInitConditions[i] = truncateDigits(
                Math.max(0, (1 + 0.05*(2*r.nextDouble() - 1))*(0.01*(2*r.nextDouble() - 1) + currentInitConditions.values[i])));

            for (int j = 0; j < paramDimension; j++) {
                perturbedCommunityMatrix[i][j] =  truncateDigits(
                    (1 + 0.05*(2*r.nextDouble() - 1))*(0.01*(2*r.nextDouble() - 1) + currentCommunityMatrix.values[i].values[j]));
            }
        }

        return new Parameters(
            new Matrix(perturbedCommunityMatrix), new Vector(perturbedForcingVector), 
            new Vector(perturbedInitConditions), timeSteps);
    }

    /*  A GET request is on this route is erroneous and may be executed
     *    by trying to navigate to /Params.
     *  Simply redirects to home.jsp. 
     */ 
    public void doGet(HttpServletRequest req, HttpServletResponse res) 
    throws ServletException, IOException {
        res.sendRedirect("home.jsp");
    }

    /*
     *  Servlet method for handling POST requests on route /Params
     *  Processes param-modification parameter sent by update-parameter form
     *      to handle request accordingly  
    */
    public void doPost(HttpServletRequest req, HttpServletResponse res) 
        throws ServletException, IOException {

        String paramModification = req.getParameter("param-modification");
        int paramDimension = Integer.parseInt(req.getParameter("param-dimension"));
        
        req.setAttribute("param-dimension", paramDimension);
        
        // Updates the model parameters in the dao. Passes to /Model
        if ("update-params".equals(paramModification)) {
            int timeSteps = Integer.parseInt(req.getParameter("timesteps"));
            
            ParamParser parser = new ParamParser(); 
            
            Matrix communityMatrix = parser.parseMatrixRequest(req);
            Vector forcingVector = parser.parseVectorRequest(req, "r");
            Vector initConditions = parser.parseVectorRequest(req, "ic");
            
            Parameters defaultParams = new Parameters(communityMatrix, forcingVector, initConditions, timeSteps);
            req.setAttribute("default-params", defaultParams);
            
            req.getRequestDispatcher("/Model").forward(req, res);
            return;
        }
        
        // Sends new default model parameters generated by randomizeParams
        if ("randomize".equals(paramModification)) {
            Parameters randomizedParams = randomizeParams(req);
            req.setAttribute("default-params", randomizedParams);
        }
        
        // Sends new default model parameters generated by perturbParams
        if ("perturb".equals(paramModification)) {
            Parameters perturbedParams = perturbParams(req);
            req.setAttribute("default-params", perturbedParams);
        }
        
        // Restores default parameters to the sample values provided by the ModelDao class 
        if ("restore".equals(paramModification)) {
            ModelDao dao = (ModelDao) req.getServletContext().getAttribute("dao");
            
            req.setAttribute("default-params", dao.sampleParams);
            req.setAttribute("param-dimension", dao.sampleParams.forcingVector.dimension);
        }
        
        // Modifies the dimension of the parameter update view
        if("update-dimension".equals(paramModification)) {
            int timeSteps = Integer.parseInt(req.getParameter("timesteps"));
            
            req.setAttribute("param-dimension", paramDimension);
            
            double[][] defaultMatrixValues = new double[paramDimension][paramDimension];
            double[] defaultForcingVectorValues = new double[paramDimension];
            double[] defaultInitConditionValues = new double[paramDimension];
            
            for (int i = 0; i < paramDimension; i++) {
                defaultInitConditionValues[i] = 0d;
                defaultForcingVectorValues[i] = 0d;
                
                for (int j = 0; j < paramDimension; j++) {
                    defaultMatrixValues[i][j] = 0d;
                }
            }

            Parameters defaultParams = new Parameters(
                new Matrix(defaultMatrixValues), new Vector(defaultForcingVectorValues),
                new Vector(defaultInitConditionValues), timeSteps);

            req.setAttribute("default-params", defaultParams);
        }

        // specifies default view as params and redirects to home
        req.setAttribute("default-view", "params");
        req.getRequestDispatcher("home.jsp").forward(req, res);
    }
}
