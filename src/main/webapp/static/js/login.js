document.addEventListener('DOMContentLoaded', function () {

    nicknameCheck();
    passwordCheck();
    regLoginSwitch();
    document.getElementById('password-1').addEventListener('input', passwordCheck);
    document.getElementById('password-2').addEventListener('input', passwordCheck);

    function nicknameCheck() {
        let el = document.getElementById('nickname');
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

    function passwordCheck() {
        let el1 = document.getElementById('password-1');
        let el2 = document.getElementById('password-2');
        if (el1.value !== el2.value) {
            el2.classList.add('incorrect-password');
            document.getElementById('password-match').style.display = "block";
        } else {
            el2.classList.remove('incorrect-password');
            document.getElementById('password-match').style.display = "none";
        }
        let a = false, b = false, c = false;
        for (let j = 0; j < el1.value.length; j++) {
            let cur = el1.value.charAt(j);
            if (cur >= 'a' && cur <= 'z') a = true;
            else if (cur >= 'A' && cur <= 'Z') b = true;
            else if (cur >= '0' && cur <= '9') c = true;
        }
        if (!(el1.value.length >= 8 && a && b && c)) {
            el1.classList.add('incorrect-password');
            document.getElementById('weak-password').style.display = "block";
        } else {
            el1.classList.remove('incorrect-password');
            document.getElementById('weak-password').style.display = "none";
        }

        if (el1.value !== el2.value) {
            el2.classList.add('incorrect-password');
            document.getElementById('password-match').style.display = "block";
        } else {
            el2.classList.remove('incorrect-password');
            document.getElementById('password-match').style.display = "none";
        }

    }

    function regLoginSwitch() {
        let loginButton = document.getElementById('login-button');
        let regButton = document.getElementById('reg-button');
        let forgotPassword = document.getElementById('forgot-password');
        loginButton.addEventListener('click', event => {
            loginButton.classList.add('active-header');
            regButton.classList.remove('active-header');
            document.getElementById('login-semi-header').style.display = "block";
            document.getElementById('login-semi-body').style.display = "block";
            document.getElementById('reg-semi-header').style.display = "none";
            document.getElementById('reg-semi-body').style.display = "none";
            setNone();
        })
        regButton.addEventListener('click', event => {
            loginButton.classList.remove('active-header');
            regButton.classList.add('active-header');
            document.getElementById('login-semi-header').style.display = "none";
            document.getElementById('login-semi-body').style.display = "none";
            document.getElementById('reg-semi-header').style.display = "block";
            document.getElementById('reg-semi-body').style.display = "block";
            setNone();
        })
        forgotPassword.addEventListener('click', event => {
            document.getElementById('login-semi-header').style.display = "none";
            document.getElementById('login-semi-body').style.display = "none";
            document.getElementById('forgot-semi-header').style.display = "block";
            document.getElementById('forgot-semi-body').style.display = "block";
        })
    }

    function setNone() {
        document.getElementById('forgot-semi-header').style.display = "none";
        document.getElementById('forgot-semi-body').style.display = "none";
        document.getElementById('mail-semi-header').style.display = "none";
        document.getElementById('mail-semi-body').style.display = "none";
        document.getElementById('mail2-semi-header').style.display = "none";
        document.getElementById('mail2-semi-body').style.display = "none";
    }
})