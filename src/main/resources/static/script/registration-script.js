function register()
{
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;
    const confirmPassword = document.getElementById("confirmPassword").value;

    let isValid =
        username.matches(STUDENT_ID_REGEX) &&
        password.matches(PASSWORD_REGEX) &&
        password === confirmPassword;

    if (!isValid)
    {
        modal("Error registering!", "There was an error in the information you entered. Try again.")
        return;
    }

    const registrationRequest =
        {
            'username': username,
            'password': password
        }

    sendRequest(registrationRequest)
        .then(r => handle(r))
        .catch(e => failure(e));
}

async function sendRequest(registrationRequest)
{
    startAnimation();
    const url = domain + "/auth/register";
    return await fetch(url, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(registrationRequest)
    });
}

function handle(r)
{
    if (r.ok)
    {
        alert("Registration successful, redirecting to login")
        // modal("Registration successful", "The registration was successful, redirecting to login");
        window.location.href = domain + "/auth/login";
    }
    else
    {
        endAnimation();
        modal("Registration failed", "The registration process has failed, try again.");
    }
}

function failure(e)
{
    endAnimation();
    console.log(e)
    modal("Registration failed", "The registration process has failed, try again.");
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
