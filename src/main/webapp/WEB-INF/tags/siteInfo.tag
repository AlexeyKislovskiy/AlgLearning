<%@tag description="site information" pageEncoding="UTF-16" %>
<div class="modal fade" id="siteInfo" tabindex="-1" aria-labelledby="siteInfoLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content" id="modal-content-site">
            <div class="modal-header" id="siteInfoHeader">
                <img id="logo-modal"
                     src="${pageContext.request.contextPath}/static/img/AlgLearningLogo.png" alt="site-logo">

                <div type="button" id="closeSiteInfoDiv" data-bs-dismiss="modal" aria-label="Закрыть">
                    <img id="closeSiteInfo"
                         src="${pageContext.request.contextPath}/static/img/icons/close_black.png" alt="close">
                </div>
            </div>
            <div class="modal-body">
                <p>Сайт предоставляет доступ к большому числу алгоритмов для различных головоломок,
                    возможности
                    тренировать их и изучать новые с помощью интервальных повторений.</p>
                <p>Есть замечания или предложения? Нашли баг или знаете о методе, которого нет в базе?
                    Хотите
                    помочь с переводом, добавлением новых методов или проверкой пользовательских алгоритмов?
                    По всем вопросам пишите на почту или в телеграм: </p>
                <div><img class="message-img"
                          src="${pageContext.request.contextPath}/static/img/icons/mail.png" alt="mail-icon"><a
                        href="mailto:kislovskijalexey34@gmail.com?subject=AlgLearning">kislovskijalexey34@gmail.com</a>
                </div>
                <div><img class="message-img"
                          src="${pageContext.request.contextPath}/static/img/icons/telegram.png" alt="telegram-icon"><a
                        href="https://t.me/fertdt" target="_blank">@fertdt</a></div>
            </div>
        </div>
    </div>
</div>