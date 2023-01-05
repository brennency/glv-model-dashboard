jQuery(document).ready(() => {

    // Toggle active view-window
    jQuery(".view-selector").click(function () {
        const classes = jQuery(this).attr('class').split(" ").filter(cl => cl != "view-selector");
        const identifier = classes.filter(cl => cl != "active")[0];
        
        if (!classes.includes("active")) {
            jQuery(".view-window.active, .view-selector.active").removeClass("active");
            jQuery(`.view-window.${identifier}, .view-selector.${identifier}`).addClass("active");
        }
    })

    //Toggle update-param view
    jQuery("#update-params-toggle").click(() => {
        jQuery(".update-params-container").toggleClass("active");
        jQuery("#update-params-toggle").toggleClass("active");
    })
    
})