function resetRequest()
{
    const username = document.getElementById("username").value;

    if (!username.match(STUDENT_ID_REGEX))
    {
        modal("Invalid Student Id", "There was an error in the information you entered. Try again.")
        return;
    }

    sendResetRequest(username)
        .then(r => handle(r))
        .catch(e => failure(e));
}

async function sendResetRequest(username)
{
    startAnimation();
    const url = domain + "/auth/reset-request?username=" + username;
    return await fetch(url, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' }
    });
}

function handle(r)
{
    if (r.ok)
    {
        modal("Request sent", "The password reset request was received. Check your institutional email for further instructions");
    }
    else
    {
        endAnimation();
        modal("Error", "Your request could not be processed, try again later.");
    }
}

function failure(e)
{
    endAnimation();
    console.log(e)
    modal("Error", "Your request could not be processed, try again later.");
}


