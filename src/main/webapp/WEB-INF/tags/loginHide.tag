<%@tag description="change login elements" pageEncoding="UTF-16" %>
<%@attribute name="login" required="true" %>
<%@attribute name="forgot" required="true" %>
let loginButton = document.getElementById('login-button');
let regButton = document.getElementById('reg-button');
loginButton.classList.add('active-header');
regButton.classList.remove('active-header');
document.getElementById('login-semi-header').style.display = "${login}";
document.getElementById('login-semi-body').style.display = "${login}";
document.getElementById('forgot-semi-header').style.display = "${forgot}";
document.getElementById('forgot-semi-body').style.display = "${forgot}";
hideElements();