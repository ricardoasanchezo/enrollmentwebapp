const domain = "http://localhost:8080";
const STUDENT_ID_REGEX = /\b(?:[A-Za-z]\d{8}|[A-Za-z]{2}\d{7})\b/;
const PASSWORD_REGEX = /^.{1,30}$/;

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

function startAnimation()
{
    let button = document.getElementById("form-button");
    button.disabled = true;
    button.backgroundColor = "black";
    button.textContent = "";
    let div = document.createElement("div");
    div.className = "material-symbols-outlined rotation";
    div.textContent = "progress_activity";
    button.appendChild(div);
}

function endAnimation()
{
    let button = document.getElementById("form-button");
    button.disabled = false;
    button.innerHTML = "Register";
}

function startLoading(button)
{
    button.disabled = true;
    button.backgroundColor = "black";
    button.classList.add("rotation", "material-symbols-outlined");
    button.textContent = "progress_activity";
}

function endLoading(button)
{
    button.disabled = false;
    button.innerHTML = "Register";
}