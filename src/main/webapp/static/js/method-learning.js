document.addEventListener('DOMContentLoaded', function () {
    setTimeout(cardHeightChange, 100);
    cardHeightChange();
    setTimeout(bottomRowHeightChange, 100);
    bottomRowHeightChange();
    sendForm();

    window.addEventListener(`resize`, event => cardHeightChange(), false);
    window.addEventListener(`resize`, event => bottomRowHeightChange(), false);

    function cardHeightChange() {
        let new_height = document.getElementById('hidden-card').offsetHeight;
        document.getElementById('card-alg').style.height = new_height + 'px';
    }

    function sendForm() {
        let el = document.getElementsByClassName('form-sender');
        for (let i = 0; i < el.length; i++) {
            el[i].addEventListener('click', evt => {
                el[i].parentNode.submit();
            })
        }
    }

    function bottomRowHeightChange() {
        let new_height = window.innerHeight - document.getElementById('navigation').offsetHeight - document.getElementById('top-row').offsetHeight - document.getElementById('row-1').offsetHeight - 50;
        document.getElementById('row-2').style.height = new_height + 'px';
    }
})

