window.document.addEventListener("DOMContentLoaded", function() {
    var element;
    element = document.getElementById("login-sign-up");
    if (element !== null)
        element.onclick = function ()
        {
            location.href = "/EE4023/index.php";
        };
    element = document.getElementById("register-cancel");
    if (element !== null)
        element.onclick = function ()
        {
            location.href = "/EE4023/index.php";
        };
    var modal = document.getElementById('id01');
    window.onclick = function(event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }
});