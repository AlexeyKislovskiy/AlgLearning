document.addEventListener('DOMContentLoaded', function () {
    setHeight();
    setTimeout(setHeight, 100);
    window.addEventListener(`resize`, event => setHeight(), false);
    changeInputNumber();
    changeInputNumberInt();
    forgotCheck();
    changeNickname();
    correctOldPassword();
    sendForm();
    resetAll();
    reset();

    function setHeight() {
        let new_height = window.innerHeight - document.getElementById('navigation').offsetHeight - 20;
        document.getElementById('setting-scrolling').style.height = new_height + 'px';
    }

    function sendForm() {
        let el = document.getElementsByClassName('form-sender');
        for (let i = 0; i < el.length; i++) {
            el[i].addEventListener('click', evt => {
                el[i].parentNode.submit();
            })
        }
    }

    function resetAll() {
        let el = document.getElementsByClassName('reset-all');
        for (let i = 0; i < el.length; i++) {
            el[i].addEventListener('click', evt => {
                let form = document.createElement('form');
                form.action = '';
                form.method = 'POST';
                form.innerHTML = '<input name="reset-all" value="reset-all">';
                document.body.append(form);
                form.submit();
            })
        }
    }

    function reset() {
        let form = document.createElement('form');
        form.action = '';
        form.method = 'POST';
        document.body.append(form);
        document.getElementById('reset-learning').addEventListener('click', ev => {
            form.innerHTML = '<input name="reset" value="learning">';
            form.submit();
        })
        document.getElementById('reset-training').addEventListener('click', ev => {
            form.innerHTML = '<input name="reset" value="training">';
            form.submit();
        })
        document.getElementById('reset-algorithms').addEventListener('click', ev => {
            form.innerHTML = '<input name="reset" value="algorithms">';
            form.submit();
        })
        document.getElementById('reset-account').addEventListener('click', ev => {
            form.innerHTML = '<input name="reset" value="account">';
            form.submit();
        })
    }

    function changeInputNumber() {
        let el = document.getElementsByClassName('time-multi');
        for (let i = 0; i < el.length; i++) {
            if (el[i].classList.contains('int-multi')) continue;
            el[i].addEventListener('input', event => {
                if (el[i].value.length > 4) el[i].value = el[i].value.substr(0, 4)
                let s = 0;
                for (let j = 0; j < el[i].value.length; j++) {
                    if (!(el[i].value.charAt(j) >= "0" && el[i].value.charAt(j) <= "9" || el[i].value.charAt(j) === "."
                        || el[i].value.charAt(j) === ",")) {
                        el[i].value = el[i].value.substring(0, j) + el[i].value.substring(j + 1);
                    }
                    if (el[i].value.charAt(j) === "." || el[i].value.charAt(j) === ",") {
                        s++;
                    }
                    if (s > 1) {
                        s--;
                        el[i].value = el[i].value.substring(0, j) + el[i].value.substring(j + 1);
                    }
                }
            })
            el[i].addEventListener('change', event => {
                for (let i = 0; i < el.length; i++) {
                    if (el[i].value.length === 0) el[i].value = "1";
                    let s = 0, n = 0;
                    for (let j = 0; j < el[i].value.length; j++) {
                        if (el[i].value.charAt(j) === "." || el[i].value.charAt(j) === ",") {
                            s++;
                            n++;
                        }
                        if (el[i].value.charAt(j) === "0") n++;
                    }
                    if (n === el[i].value.length) el[i].value = "0.1"
                    if (s > 0) {
                        for (let j = el[i].value.length - 1; j >= 0; j--) {
                            if (el[i].value.charAt(j) === "0") el[i].value = el[i].value.substr(0, el[i].value.length - 1);
                            else break;
                        }
                    }
                    if (el[i].value.charAt(el[i].value.length - 1) === "." || el[i].value.charAt(el[i].value.length - 1) === ",")
                        el[i].value = el[i].value.substr(0, el[i].value.length - 1);
                    if (el[i].value.charAt(0) === "." || el[i].value.charAt(0) === ",") {
                        el[i].value = "0" + el[i].value;
                        if (el[i].value.length > 4) el[i].value = el[i].value.substr(0, 4);
                    }
                    while (el[i].value.length > 1 && el[i].value.charAt(0) === "0") el[i].value = el[i].value.substring(1);
                    if (el[i].value.charAt(0) === "." || el[i].value.charAt(0) === ",") el[i].value = "0" + el[i].value;
                    if (el[i].value.length > 4) el[i].value = el[i].value.substr(0, 4);
                    if (el[i].value === "0") el[i].value = "0.1";
                    for (let j = 0; j < el[i].value.length; j++) {
                        if (el[i].value.charAt(j) === ",") {
                            el[i].value = el[i].value.substring(0, j) + "." + el[i].value.substring(j + 1);
                            break;
                        }
                    }
                }
            })
            el[i].addEventListener('paste', e => e.preventDefault());
        }
    }

    function changeInputNumberInt() {
        let el = document.getElementsByClassName('int-multi');
        for (let i = 0; i < el.length; i++) {
            el[i].addEventListener('input', event => {
                if (el[i].value.length > 4) el[i].value = el[i].value.substr(0, 4);
                for (let j = 0; j < el[i].value.length; j++) {
                    if (!(el[i].value.charAt(j) >= "0" && el[i].value.charAt(j) <= "9")) {
                        el[i].value = el[i].value.substring(0, j) + el[i].value.substring(j + 1);
                    }
                }
            })
            el[i].addEventListener('change', event => {
                for (let i = 0; i < el.length; i++) {
                    if (el[i].value.length === 0) el[i].value = "1";
                    while (el[i].value.length > 1 && el[i].value.charAt(0) === "0") el[i].value = el[i].value.substring(1);
                    if (el[i].value === "0") el[i].value = "1";
                }
            })
            el[i].addEventListener('paste', e => e.preventDefault());
        }
    }

    function changeNickname() {
        let el = document.getElementById('login-change');
        el.addEventListener('input', event => {
            if (el.value.length > 16) el.value = el.value.substr(0, 16);
            let flag = 1;
            while (flag) {
                flag = 0;
                for (let i = 0; i < el.value.length; i++) {
                    if (el.value.charAt(i) === " ") {
                        el.value = el.value.substring(0, i) + el.value.substring(i + 1);
                        flag = 1;
                        break;
                    }
                }
            }
        })
    }

    function correctOldPassword() {
        let newPass = document.getElementById("new-password");
        let oldPass = document.getElementById("old-password");
        oldPass.addEventListener('input', event => {
            newPass.disabled = oldPass.value !== "qwerty";
        })
    }

    function forgotCheck() {
        let el1 = document.getElementById('forgot-1')
        let el2 = document.getElementById('forgot-2')
        el1.addEventListener('click', event => {
            document.getElementById("forgot-text").disabled = false;
        })
        el2.addEventListener('click', event => {
            document.getElementById("forgot-text").disabled = true;
        })
    }
})
