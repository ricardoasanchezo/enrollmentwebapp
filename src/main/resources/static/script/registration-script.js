function register()
{
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;
    const confirmPassword = document.getElementById("confirmPassword").value;

    let isValid =
        username.match(STUDENT_ID_REGEX) &&
        password.match(PASSWORD_REGEX) &&
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
        .then(r => r.json())
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
    modal(r.title, r.message);
    endAnimation();
}

function failure(e)
{
    endAnimation();
    console.log(e)
    modal("Registration failed", "The registration process has failed, try again.");
}
