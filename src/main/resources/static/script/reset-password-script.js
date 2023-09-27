function passwordRequest()
{
    let urlString = window.location.search;
    let urlParams = new URLSearchParams(urlString);
    let token = urlParams.get("token");

    let password = document.getElementById("password").value;
    let confirmPassword = document.getElementById("confirmPassword").value;

    if (!password.match(PASSWORD_REGEX) || password !== confirmPassword)
    {
        modal("Password error", "Password is too short, or passwords don't match");
        return;
    }

    let passwordRequest =
        {
            token: token,
            password: password
        }

    sendPasswordRequest(passwordRequest)
        .then(r => handle(r))
        .catch(e => failure(e))
}

async function sendPasswordRequest(request)
{
    startAnimation();
    return await fetch
    (
        "http://localhost:8080/auth/reset-password",
        {
            method: "POST",
            headers: {"Content-Type": "application/json",},
            body: JSON.stringify(request),
        }
    );
}

function handle(r)
{
    if (r.ok)
    {
        modal("Password updated", "Your password was updated. You can login!");
    }
    else
    {
        endAnimation();
        modal("Error", "Your password could not be updated. Try again later.");
    }
}

function failure(e)
{
    endAnimation();
    console.log(e)
    modal("Error", "Your password could not be updated. Try again later.");
}