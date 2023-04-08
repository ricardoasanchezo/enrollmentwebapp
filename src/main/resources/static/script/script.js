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
