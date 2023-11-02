// Development APIs
const registerAPI = 'http://localhost:8080/users/add';

// Production APIs
// const registerAPI = 'https://onepostweb.azurewebsites.net/users/add';


//4) Add new user to the when admin clicks on the submit button from the register.html
function addUser(email, username, password, role, enabled) {
    
    // formData is an Object provided by the Browser API for us to send the data over to the backend
    // Make sure names in the append matches the names from the UserController java
    // no need to match sequence
    const formData = new FormData();
    formData.append('email', email);
    formData.append('username', username);
    formData.append('password', password);
    formData.append('role', role);
    formData.append('enabled', enabled);

    // Call the registerAPI
    fetch(registerAPI, {
        method: 'POST',
        body: formData
    })
        .then(function (response) {
            console.log(response.status); // Will show you the status - 200 OK, 500, 404
            if (response.ok) {
                alert("User registration success!")

                // Refresh the page
                window.location.reload()
            }
            else {
                alert("Either this email already has an account or another user had just been added (please wait for 30minutes if that is so). If you forgot your account details, please send us an email!")
            }
        })
        .catch((error) => {
            console.error('Error:', error);
            alert("Error adding user. Please try again")
        });
}
