
jQuery(document).ready(() => {
    const key = crypto.randomUUID();
    jQuery('#auth-key').val(key);

    jQuery('#login-button').on('click', () => {
        jQuery('#login-form').submit();
    });
})