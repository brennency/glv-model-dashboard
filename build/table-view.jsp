<%@ page import="abc.dao.ModelDao" %>

<%
    int paramDimension = dao.model.params.forcingVector.dimension;
    String[] dataKeys = new String[paramDimension];
    for (int populationIndex = 1; populationIndex <= paramDimension; populationIndex++) {
        dataKeys[populationIndex - 1] = "Population " + populationIndex;
    }
%>

<!DOCTYPE html>
<html>
    <div class="view-window table">
        <table id="data-table">
            <tr>
                <th>Time</th>
                <% for (String key : dataKeys ) { %>
                        <th><%= key %></th>
                <% } %>
            </tr>
            <% for (int t = 0; t < dao.model.params.timeSteps; t++) { %>
                <tr>
                    <td><%= t %></td>
                    <% for (String key : dataKeys) { %>
                        <td><%= String.format("%3.2e", dao.model.data.get(key)[t]) %></td>
                    <% } %>
                </tr>
            <% } %>
        </table>
    </div>
</html>