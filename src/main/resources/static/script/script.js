function validateRegisterForm()
{
    let username = getUsername();
    if (username.length === 0)
        return alert("Invalid Student ID format!");

    let password = getPassword()
    if (password.length === 0)
        return alert("Password needs to be between " + 8 + " and " + 20 + " characters long!");

    if (!passwordsMatch(password))
        return alert("Passwords do not match!");

    register(username, password).then(r => alert(r))
}

function getUsername()
{
    let username = document.getElementById("username").value;
    const STUDENT_ID_REGEX = /^[a-zA-Z][0-9]{8}$/;

    return matchesRegex(username, STUDENT_ID_REGEX) ? username : "";
}

function getPassword()
{
    let password = document.getElementById("password").value;
    const PASSWORD_REGEX = /^.{8,20}$/;

    return matchesRegex(password, PASSWORD_REGEX) ? password : "";
}

function passwordsMatch(password)
{
    let confirmPassword = document.getElementById("confirmPassword").value;
    return password === confirmPassword;
}

async function register(username, password)
{
    let request = {
        username: username,
        password: password
    }

    try {
        const response = await fetch
            (
                "http://localhost:8080/auth/register",
                {
                    method: "POST",
                    headers: {"Content-Type": "application/json",},
                    body: JSON.stringify(request),
                }
            );

        let result = await response.json();
        return result.response;
    }
    catch (error)
    {
        console.error("Error:", error);
        return "No student found with provided ID!";
    }
}

function matchesRegex(string, regex)
{
    return regex.test(string);
}