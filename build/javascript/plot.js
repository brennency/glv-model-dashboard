jQuery(document).ready(() => {
    console.log("jQuery is on.");

    document.getElementById("plot");

    jQuery.get("/glv-model-dashboard/Model", (data) => {
        Plotly.newPlot(
            "plot", 
            processData(data), 
            {margin: {t:20, b:30, l:30, r:10}},
            {responsive: true}
        );
    })
});

// function for parsing JSON response and loading it into
//  plotly
function processData(data) {
    let formattedData = [];
    let length = data[Object.keys(data)[0]].length;
    let xAxis = Array.from({length: length}, (x,i) => i + 1);
    
    for (key of Object.keys(data).sort()) {
        formattedData.push({
            name: key,
            x: xAxis,
            y: data[key],
            mode: "lines"
        })
    }
    
    $("#plot").removeClass("loading");
    return formattedData;
}