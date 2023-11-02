// Global variable - to store the image object
let storeImage = ""

/*
When user clicks on 'Save Item':
1. store all the inputs into variables
2. do validation
3. calls a function from the productController.js to access the API to add items to the database
*/

//Add an 'onsubmit' event listener for productform to add a product
newReplyForm.addEventListener('submit', (event) => {

    // Prevent default action of the Form submission
    event.preventDefault();

    // Select the inputs
    const replyText = document.querySelector('#inputComment1').value;

    // Browser security will not be able to track/store the actual path of where you choose your image
    //will instead provide a random path
    // Here, we are replacing the fakepath(C:\fakepath\) with empty path("")
    // const imageUrl = document.querySelector('#newItemImageFile').value.replace("C:\\fakepath\\", "");

    // postId = postController2[postIndex].idPost; from replyController
    const idPost = postId;

    /*
        Do the Validation code here
    */

    // only add if character length is within limit, else do not even call the function
    if (replyText.length > 500) {
        alert("Unable to add your reply. Please keep to the specified character limits.");
    }
    else {
        // 3. calls a function from the ReplyController.js to access the API to add reply to the database
        addReply(replyText, idPost);
    }

});


// // select file input
// const input = document.querySelector('#newItemImageFile');

// // add event listener
// input.addEventListener('change', () => {

//     // store inside storeImage variable the files to be uploaded
//     storeImage = input.files[0]; //array of files to access
// });
