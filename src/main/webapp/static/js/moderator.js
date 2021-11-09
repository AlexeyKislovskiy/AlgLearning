document.addEventListener('DOMContentLoaded', function () {
    window.addEventListener(`resize`, event => setHeight(), false);
    setTimeout(setHeight, 100);
    setHeight();

    function setHeight() {
        let new_height = window.innerHeight - document.getElementById('header-row').offsetHeight - 20;
        document.getElementById('content-row').style.maxHeight = new_height + 'px';
    }

})