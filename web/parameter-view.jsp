<%@page import="abc.model.LabelGenerator" %>

<!DOCTYPE html>
<html>
    <div class="<%= "view-window params " + defaultViewClasses.get("params") %>" >
        <div class="params-container">
            <div class="table-params">
                <div class="param-container">
                    <p>Community matrix</p>
                    <table>
                    <% for (int i = 0; i < paramDimension; i++) { %>
                        <tr>
                        <% for (int j = 0; j < paramDimension; j++)  { %>
                            <td><%=dao.model.params.communityMatrix.values[i].values[j]%></td>
                        <% } %>
                        </tr>
                    <% } %>
                    </table>
                </div>
                <div class="vector-params">
                    <div class="param-container">
                        <p>Forcing vector</p>
                        <table>
                            <% for (int i = 0; i < paramDimension; i++) { %>
                                <tr>
                                    <td><%=dao.model.params.forcingVector.values[i]%> </td>
                                </tr>
                            <% } %>
                        </table>
                    </div>
                    <div class="param-container">
                        <p>Initial conditions</p>
                        <table>
                            <% for (int i = 0; i < paramDimension; i++) { %>
                            <tr>
                                <td><%=dao.model.params.initConditions.values[i]%></td>
                            </tr>
                            <% } %>
                        </table>
                    </div>
                </div>
            </div>
            <div class="non-table-params">Time steps: <%=dao.model.params.timeSteps%></div>
        </div>
        <div id="update-params-toggle-container">
            <p id="update-params-toggle" class="<%=defaultViewClasses.get("toggle-update-params")%>">Change model parameters</p>
        </div>
        <div class="<%="update-params-container " + defaultViewClasses.get("update-params")%>">
            <%@ include file="update-parameter-view.jsp" %>
        </div>
    </div>
</html>