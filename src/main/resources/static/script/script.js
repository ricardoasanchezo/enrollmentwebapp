async function login()
{
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    let request = {
        username: username,
        password: password
    }

    try
    {
        const response =
            await fetch
            (
                "http://localhost:8080/auth/authenticate",
                {
                    method: "POST",
                    headers: {"Content-Type": "application/json",},
                    body: JSON.stringify(request),
                }
            );

        const result = await response.json();
        console.log("Success:", result);
    }
    catch (error)
    {
        console.error("Error:", error);
    }
}

function validateRegisterForm()
{
    const username = document.getElementById("username").value;
    const studentIdRegex = /^[a-zA-Z][0-9]{8}$/;

    if (!matchesRegex(username, studentIdRegex))
    {
        alert("Invalid Student ID format!");
        return false;
    }

    const password = document.getElementById("password").value;
    const confirmPassword = document.getElementById("confirmPassword").value;

    if (password !== confirmPassword)
    {
        alert("Passwords do not match!");
        return false;
    }

    register(username, password).then(()=>
    {
        alert("register successful")
    });
}

async function register(username, password)
{
    let request = {
        username: username,
        password: password
    }

    try {
        const response =
            await fetch
            (
                "http://localhost:8080/auth/register",
                {
                    method: "POST",
                    headers: {"Content-Type": "application/json",},
                    body: JSON.stringify(request),
                }
            );

        const result = await response.json();
        console.log("Success:", result);
    }
    catch (error)
    {
        console.error("Error:", error);
    }
}

function matchesRegex(string, regex)
{
    return regex.test(string);
}