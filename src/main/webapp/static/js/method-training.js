document.addEventListener('DOMContentLoaded', function () {
    document.getElementById('chooseAll-div').addEventListener('click', ev => {
        let el = document.getElementsByClassName('using-check');
        for (let i = 0; i < el.length; i++) {
            el[i].checked = "true";
        }
    })

    document.getElementById('unchooseAll-div').addEventListener('click', ev => {
        let el = document.getElementsByClassName('using-check');
        for (let i = 0; i < el.length; i++) {
            el[i].checked = null;
        }
    })
})
