<%@ page import="abc.model.LabelGenerator" %>
<%@ page import="abc.dao.ModelDao" %>
<%@ page import="abc.model.GeneralizedLotkaVolterra.Parameters" %>
<%@ page import="abc.model.LinearAlgebra.*" %>

<% 
Integer _updatedParamDimension= (Integer) request.getAttribute("param-dimension");
int updatedParamDimension = _updatedParamDimension == null ? paramDimension : (int) _updatedParamDimension;

LabelGenerator labelGenerator = new LabelGenerator(updatedParamDimension);

String[][] matrixLabels = labelGenerator.generateMatrixLabels("a");
String[] initConditionLabels = labelGenerator.generateVectorLabels("ic");
String[] forcingVectorLabels = labelGenerator.generateVectorLabels("r");

Parameters defaultParams = (Parameters) request.getAttribute("default-params");
Matrix defaultCommunityMatrix;
Vector defaultForcingVector;
Vector defaultInitConditions;
int defaultTimeSteps;

if (defaultParams == null) {
    defaultCommunityMatrix = dao.model.params.communityMatrix;
    defaultForcingVector = dao.model.params.forcingVector;
    defaultInitConditions = dao.model.params.initConditions;
    defaultTimeSteps = dao.model.params.timeSteps;
}
else {
    defaultCommunityMatrix = defaultParams.communityMatrix;
    defaultForcingVector = defaultParams.forcingVector;
    defaultInitConditions = defaultParams.initConditions;
    defaultTimeSteps = defaultParams.timeSteps;
}

%>

<!DOCTYPE html>
<html>
    <head>
        <script src="javascript/form-handler.js"></script>
    </head>
    <div class="update-params-view">
        <form id="update-dimension-form" action="Params" method="POST">
            <input type="hidden" name="timesteps" value="<%= defaultTimeSteps %>"/>
            <input type="hidden" name="param-modification" value="update-dimension"/>
            <div id="num-populations-selector">
                <p>Number of populations:</p>
                <select form="update-dimension-form" onchange="if(this.value != <%= updatedParamDimension %>) {this.form.submit();}"
                        name="param-dimension">
                <% for (int dimOption = 1; dimOption <= 8; dimOption++) { %>
                    <% if (dimOption == updatedParamDimension) { %>
                        <option value="<%=dimOption%>" selected>
                            <%=dimOption%>
                        </option>
                    <% } else { %>
                        <option value="<%=dimOption%>">
                            <%=dimOption%>
                        </option>
                    <% } %>
                <% } %>
                </select>
            </div>
        </form>
        <form id="update-parameter-form" action="Params" method="POST">
            <input id="param-modification" type="hidden" name="param-modification"/>
            <input type="hidden" name="param-dimension" value="<%= updatedParamDimension %>" />
            <div id="param-modification-options">
                <div id="randomize-params"class="param-action-button">
                    <p>Randomize parameters</p>
                </div>
                <div id="perturb-params"class="param-action-button">
                    <p>Perturb parameters</p>
                </div>
                <div id="restore-params"class="param-action-button">
                    <p>Restore default parameters</p>
                </div>
            </div>
            <div class="params-container">
                <div class="table-params">
                    <div class="param-container">
                        <p>Community matrix</p>
                        <table>
                        <% for (int i = 0; i < updatedParamDimension; i++) { %>
                            <tr>
                            <% for (int j = 0; j < updatedParamDimension; j++)  { %>
                                <td>
                                    <input type="text" 
                                            name="<%= matrixLabels[i][j] %>"
                                            value="<%= defaultCommunityMatrix.values[i].values[j] %>" />
                                </td>
                            <% } %>
                            </tr>
                        <% } %>
                        </table>
                    </div>
                    <div class="vector-params">
                        <div class="param-container">
                            <p>Forcing vector</p>
                            <table>
                            <% for (int i = 0; i < updatedParamDimension; i++) { %>
                                <tr>
                                    <td>
                                        <input type="text" 
                                                name="<%= forcingVectorLabels[i] %>"
                                                value="<%= defaultForcingVector.values[i] %>" />
                                    </td>
                                </tr>
                            <% } %>
                            </table>
                        </div>
                        <div class="param-container">
                            <p>Initial conditions</p>
                            <table>
                            <% for (int i = 0; i < updatedParamDimension; i++) { %>
                                <tr>
                                    <td>
                                        <input type="text"
                                                name="<%= initConditionLabels[i] %>"
                                                value="<%= defaultInitConditions.values[i] %>" />
                                    </td>
                                </tr>
                            <% } %>
                            </table>
                        </div>
                    </div>
                </div>
                <div class="non-table-params">
                    <div class="timesteps-display">
                        <p>Time steps</p>
                        <input id="timesteps-slider" name="timesteps" type="range" min="1" max="250" value="<%= defaultTimeSteps %>" />
                        <div id="timsteps-value"></div>
                    </div>
                </div>
                <div id="submit-params"class="param-action-button">
                    <p>Update parameters and run model</p>
                </div>
            </div>
        </form>
    </div>
</html>