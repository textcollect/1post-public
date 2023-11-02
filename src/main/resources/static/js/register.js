/*
When user clicks on 'Save Item':
1. store all the inputs into variables
2. do validation
3. calls a function from the productController.js to access the API to add items to the database
*/

//email validation function
const validateEmail = (email) => {
    // Regular expression pattern for validating email address format
    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    
    // Use the test() method of the regex pattern to check if the email matches the pattern
    return emailPattern.test(email);
}
  

//Add an 'onsubmit' event listener for productform to add a product
newUserRegistration.addEventListener('submit', (event) => {

    // Prevent default action of the Form submission
    event.preventDefault();

    // Select the inputs
    const username = document.querySelector('#username1').value;
    const password1 = document.querySelector('#password1').value;
    const password2 = document.querySelector('#password2').value;
	const email = document.querySelector('#email').value;
    const enabled = true;

    // Browser security will not be able to track/store the actual path of where you choose your image
    //will instead provide a random path
    // Here, we are replacing the fakepath(C:\fakepath\) with empty path("")
    // const imageUrl = document.querySelector('#newItemImageFile').value.replace("C:\\fakepath\\", "");

    /*
        Do the Validation code here
    */

    const isValidEmail = validateEmail(email); // returns true if valid, false if not

	// only add if character length is within limit, else do not even call the function
	if (password1 !== password2) {
		alert("Passwords do not match. Please make sure passwords are the same.");
	}
    else if (!isValidEmail) {
        alert("Invalid email. Please enter a valid email address.");
    }
	else if (password1 === password2 && isValidEmail) {
        
		// 3. calls a function from the userController.js to access the API to add user to the database
		addUser(email, username, password1, enabled);
	}

});


// // select file input
// const input = document.querySelector('#newItemImageFile');

// // add event listener
// input.addEventListener('change', () => {

//     // store inside storeImage variable the files to be uploaded
//     storeImage = input.files[0]; //array of files to access
// });
