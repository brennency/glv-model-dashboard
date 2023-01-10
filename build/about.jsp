<!DOCTYPE html>

<html>
    <head>
        <link rel="stylesheet" href="css/about.css">
    </head>
    <%@ include file="main-nav.jsp"%>
    <body>
        <div id="main-content">
            <h1>About</h1>
            <p>
                This is a small application for probing the dynamics of an ecological model
                called the <a href="https://en.wikipedia.org/wiki/Generalized_Lotka%E2%80%93Volterra_equation">Generalized Lotka-Volterra model</a>.
                It is a feedback model with matrix and vector-valued parameters representing the relationships between a 
                set of species in an ecosystem. This application allows you to enter various parameters into the model and subsequently
                see a plot of the resultant ecosystem dynamics as well as a table of the population data over time.
            </p>
            <h2>Parameters</h2>
            <p>
                The matrix-valued parameter is referred to as the <i>community matrix</i>. It defines the mutual
                relationships between the populations in the model. The value in the <i>i<sup>th</sup></i> row and
                <i>j<sup>th</sup></i> column represents the effect that species <i>i</i> has on species <i>j</i>.
                If this value is positive (negative), then the population of species <i>i</i> has a positive (negative) impact on species <i>j</i>.
                It is in this way that the model represents predatory or symbiotic relationships between the species being modeled.
            </p>

            <p>
                The two vector-valued parameters are known as the <i>forcing vector</i> and <i>initial conditions</i>, respectively.
                The <i>i<sup>th</sup></i> component of the forcing vector represents an external forcing that 
                compels species <i>i</i> to either increase or decrease in population, depending on the sign.
                The initial conditions simply refer to the the initial population of each species, with 
                the <i>i<sup>th</sup></i> component defining the initial population of species <i>i</i>.
            </p>

            <p>
                You might notice that the model parameters and output can be of a small magnitude, typically less than one 
                (though you may well enter parameters freely). This may seem especially strange with the population numbers
                having a value less than one. However, one could interpret the quantities as being measured in the thousands, or some other larger base unit.
                For example, a population value of 0.63 could represent 630 members of a population. All that matters for the
                model dynamics is the relative size of different populations and model parameters.
                Similarly, there is no assumed unit for the time scales in the model.
            </p>

            <p>
                You may notice that this model can generate runaway populations that grow exponentially. 
                In practice, this is avoided by using a parameter set that better represents real ecosystems.
                You may also notice that very slight changes to the model parameters can result in very different dynamics. Indeed, the
                Generalized Lotka-Volterra equations can result in <i>chaotic dynamics</i>, wherein a very small change to 
                the parameter set results in drastically different outputs. 
                You can play around with this fact using the "perturb parameters" option, described further below. 
            </p>
            <p>
                There are a few constraints on the parameters.
                One is that the population of any species cannot be negative, which is to impose 
                a realistic constraint on the model itself.
                The others are that the number of species must be between 2 and 8, and there is a maximum of
                250 time steps. These latter constraints are so that the nothing gets out of hand computationally
                for this little toy application. 
            </p>
            <h2>Interface</h2>
            <p>
                The interface is very simple. The login functionality is purely cosmetic.
                Most of the fun is under the "Model parameters" window. There, you can see
                the current model parameters for the data that is currently displayed under the "Plot" and "Table data"
                windows. If you click "Change model parameters", you are free to change the parameters of the model and re-render the application.
                You may select the number of species, and the form will update. 
                You may also randomly generate parameter values or perturb (slightly change in a random fashion) the 
                parameter values currenly displayed. Lastly, you may also return the parameters to the default settings.
                Click "Update parameters and run model" to rerender the application with the results of the model using the
                new parameters.
            </p>
        </div>
    </body> 
</html>