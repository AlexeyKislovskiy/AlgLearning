document.addEventListener('DOMContentLoaded', function () {
    window.addEventListener(`resize`, event => setHeight(), false);
    setTimeout(setHeight, 100);
    setHeight();
    cubeChoose();

    function setHeight() {
        let new_height = window.innerHeight - document.getElementById('navigation').offsetHeight - document.getElementById('icons').offsetHeight - 20;
        document.getElementById('methods').style.height = new_height + 'px';
    }

    function cubeChoose() {
        let el = document.getElementsByClassName('cubeRadio');
        for (let i = 0; i < el.length; i++) {
            el[i].addEventListener('click', evt => {
                el[i].checked = "true";
                document.getElementById('cubeIconsForm').submit();
            })
        }
    }
})


