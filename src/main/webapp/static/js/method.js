document.addEventListener('DOMContentLoaded', function () {
    window.addEventListener(`resize`, event => setHeight(), false);
    setTimeout(setHeight, 100);
    setHeight();
    sendForm();

    function setHeight() {
        let new_height = window.innerHeight - document.getElementById('navigation').offsetHeight - document.getElementById('row-1').offsetHeight - 20;
        document.getElementById('methods').style.height = new_height + 'px';
    }

    function sendForm() {
        let el = document.getElementsByClassName('form-sender');
        for (let i = 0; i < el.length; i++) {
            el[i].addEventListener('click', evt => {
                el[i].parentNode.submit();
            })
        }
    }

})