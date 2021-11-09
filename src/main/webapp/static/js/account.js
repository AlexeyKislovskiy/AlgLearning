document.addEventListener('DOMContentLoaded', function () {
    setTimeout(setHeight, 100);
    setHeight();
    window.addEventListener(`resize`, event => setHeight(), false);
    sendForm();

    function sendForm() {
        let el = document.getElementsByClassName('form-sender');
        for (let i = 0; i < el.length; i++) {
            el[i].addEventListener('click', evt => {
                el[i].parentNode.submit();
            })
        }
    }

    function setHeight() {
        let new_height;
        if (window.innerWidth >= 768) {
            document.getElementById('col-1-insert').style.display = "none";
            new_height = window.innerHeight - document.getElementById('navigation').offsetHeight
                - document.getElementById('account-header').offsetHeight - document.getElementById('acc-header').offsetHeight - 40;
            document.getElementById('acc-info').style.height = new_height + 'px';
            new_height -= document.getElementById('header-3').offsetHeight;
            document.getElementById('stat-info').style.height = new_height + 'px';
        } else {
            document.getElementById('col-1-insert').style.display = "block";
            new_height = window.innerHeight - document.getElementById('navigation').offsetHeight
                - document.getElementById('account-header').offsetHeight - document.getElementById('acc-header').offsetHeight - 40;
            document.getElementById('acc-info').style.height = new_height / 3 + 'px';
            new_height -= document.getElementById('header-3').offsetHeight + document.getElementById('acc-info').offsetHeight + 30;
            document.getElementById('stat-info').style.height = new_height + 'px';
        }
    }
})
