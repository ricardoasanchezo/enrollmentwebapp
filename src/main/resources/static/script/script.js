const domain = "http://localhost:8080";
const STUDENT_ID_REGEX = /\b(?:[A-Za-z]\d{8}|[A-Za-z]{2}\d{7})\b/;
const PASSWORD_REGEX = /^.{8,20}$/;

function modal(title, message)
{
    document.getElementById("modal-title").innerText = title;
    document.getElementById("modal-message").innerText = message;

    document.getElementById("modal-button").onclick = () =>
    {
        document.getElementById("modal").style.display = "none";
    }

    document.getElementById("modal").style.display = "block";
}