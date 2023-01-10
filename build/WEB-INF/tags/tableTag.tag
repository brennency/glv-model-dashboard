<%@ variable name-given="dog" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ variable name-given="modelDimension" %>
<%
    modelDimension = application.getAttribute("modelDimension");
%>

<c:out value="horse" default="oops"/>
<c:forEach var="x" begin="0" end="${modelDimension}">
    <c:out value="${x}" />
</c:forEach>

<jsp:doBody />