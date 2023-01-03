<%@ page import="abc.dao.ModelDao" %>
<%@ page import="java.util.HashMap" %>

<% ModelDao dao = (ModelDao) application.getAttribute("dao"); %>
<% 
    HashMap<String, String> defaultViewClasses = new HashMap();
    if ("params".equals(request.getAttribute("default-view"))) {
        defaultViewClasses.put("params", "active");
        defaultViewClasses.put("update-params", "active");
        defaultViewClasses.put("toggle-update-params", "active");
        defaultViewClasses.put("plot", "");
    }
    else {
        defaultViewClasses.put("params", "");
        defaultViewClasses.put("update-params", "");
        defaultViewClasses.put("toggle-update-params", "");
        defaultViewClasses.put("plot", "active");
    }
%>

<!DOCTYPE html>
<html>
    <head>
        <title>Simple Data Display</title>
        <meta name="viewport" content="width=device-width, height=device-height, initial-scale=1.0, user-scalable=yes">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
        <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
        <script src="javascript/select-view.js"></script>
        <link rel="stylesheet" href="css/home.css">
    </head>
    <body>
        <%@ include file="main-nav.jsp"%>
        <div id="main-content">
            <div id="view-nav">
                <div class="<%= "view-selector plot " + defaultViewClasses.get("plot") %>">Plot</div>
                <div class="view-selector table">Table Data</div>
                <div class="<%= "view-selector params " + defaultViewClasses.get("params") %>">Model Parameters</div>
            </div> 
            <%@ include file="plot-view.jsp" %>
            <%@ include file="table-view.jsp" %>
            <%@ include file="parameter-view.jsp" %>
        </div>
    </body>
</html>