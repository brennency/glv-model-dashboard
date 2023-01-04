# glv-model-dashboard

A toy application for exploring the dynamics of the Generalized Lotka-Volterra equations. The backend is written in Java/Java Servlet and the 
frontend is done with jsp, css and jQuery. 

The architecture is a little peculiar - it is an MVC application with a model layer that is rather *too* thin - 
  authentication and "data access object" classes that don't seem to be doing much. 
  This is due to the fact that this application serves as a sort of "proof of concept" or "MVP" version of a
  graph dynamical systems web application of a similar flavour that I have been envisioning.
  
Please visit *web/about.jsp* to read a thorough description of the application and its functionality.
  
To deploy the application, download **dist/GLV-model-dashboard.war** and install it in the appropriate directory of a Tomcat installation.
