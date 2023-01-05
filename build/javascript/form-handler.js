jQuery(document).ready(() => {
    jQuery("#randomize-params").click(() => {
        modifyAndSubmitParameterForm("randomize");
    });
    jQuery("#perturb-params").click(() => {
        modifyAndSubmitParameterForm("perturb");
    });
    jQuery("#restore-params").click(() => {
        modifyAndSubmitParameterForm("restore");
    });
    jQuery("#submit-params").click(() => {
        modifyAndSubmitParameterForm("update-params");
    });
    
    const modifyAndSubmitParameterForm = (paramModificationValue) => {
        jQuery("#param-modification").val(paramModificationValue);
        jQuery("#update-parameter-form").submit();
    }
})