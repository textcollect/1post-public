/*
    ProductController perform action to the products to be displayed

    (1) Display all products to be retrieved from the back-end
    (2) Add product to the product list (send the new project to the back-end)
    --- edit an existing product detail
    -- remove an existing product from the product list
*/

//(1) Hardcode 2 product items - replace it later to be retrieved from the back-end
//Product Details: name, description, imageURL, style, price
//image URL: http://www.jennybeaumont.com/wp-content/uploads/2015/03/placeholder.gif

//(1) Create the objects for the Products - replace it later to be retrieved from the back-end

//const product1 = {
//    name: "Dark T-Shirt 1",
//    description: "This is a cat print",
//    imageURL: "http://www.jennybeaumont.com/wp-content/uploads/2015/03/placeholder.gif",
//    style: "Cat pattern",
//    price: 20.50
//}
//
//const product2 = {
//    name: "Light T-Shirt 1",
//    description: "This is a puppy print",
//    imageURL: "http://www.jennybeaumont.com/wp-content/uploads/2015/03/placeholder.gif",
//    style: "Puppy pattern",
//    price: 20.50
//}
//
////(2) Push the product objects into an array
//const productController = [];
//productController.push(product1, product2);

// Development APIs
const addAPI = 'http://localhost:8080/post/add';
const displayAPI = 'http://localhost:8080/post/allpost';
const userAPI = 'http://localhost:8080/users/allusers';

// Production APIs
// 'https://onepostweb.azurewebsites.net/post/add';
// post-387605.appspot.com
// const addAPI = 'https://onepostweb.azurewebsites.net/post/add';
// const displayAPI = 'https://onepostweb.azurewebsites.net/post/allpost';
// const userAPI = 'https://onepostweb.azurewebsites.net/users/allusers';


// Initialise an empty array that will be used to store the data received from displayAPI ('/allpost' api)
let postController = [];

// Initialise empty array to store userdata from userAPI('users/allusers')
let userController = [];

function displayPost() {

    //fetch data from database using the REST API endpoint from Spring Boot
    // GET http method is the default, so no need to specify the specifics for GET
    fetch(displayAPI)
        .then((resp) => resp.json())
        .then(function (data) {
            console.log("2. receive post data")
            console.log(data);

            data.forEach(function (post, index) {
                const postObj = {
                    idPost: post.idPost,
                    postTitle: post.postTitle,
                    postDescribe: post.postDescribe,
                    postFull: post.postFull,
                    postTime: post.postTime,
                    user_id: post.user_id
                };
                // this array contains 4 posts from the db(received via the '/allpost' API)
                postController.push(postObj);
            });

			sessionStorage.removeItem("posts");
			sessionStorage.setItem("posts", JSON.stringify(postController));
            
			// calls the function to display all the 4 objects from the postController array
            // function is declared below
            renderPostPage();
            
            
        })
        .catch(function (error) {
            console.log(error);
        });
}

function fetchUsers() {

    //fetch data from database using the REST API endpoint from Spring Boot
    // GET http method is the default, so no need to specify the specifics for GET
    return fetch(userAPI)
        .then((resp) => resp.json())
        .then(function (data) {
            console.log("1. receive user data")
            console.log(data);

            data.forEach(function (user, index) {
                const userObj = {
                    user_id: user.user_id,
                    username: user.username                    
                };
                // this array contains all users from the db(received via the '/allusers' API)
                userController.push(userObj);
            });

			// calls the function to display all the 4 objects from the postController array
            // function is declared below
            // renderUsers();
        })
        .catch(function (error) {
            console.log(error);
        });
}



function displayDetails(postIndex) {

	//Store the selected user information into the localstorage
	localStorage.removeItem("postIndex");

	//Local Storage needs to store info as a string
	localStorage.setItem("postIndex", postIndex);
	
    //When user clicks on any "More" button, the details of the selected product will be displayed
    // document.querySelector("#modalName").innerHTML = productController[postIndex].name;
    // document.querySelector("#modalStyle").innerHTML = productController[postIndex].style;
    // document.querySelector("#modalPrice").innerHTML = productController[postIndex].price;
    // document.querySelector("#modalImg").src = productController[postIndex].imageUrl;


    //launch the productDetail page
    //productController[index] pass it to the productDetail page to display
    //session storage
}


//(3)  Display all products when user launch the product.html page
//const displayProduct = () => {
function renderPostPage() {

    let display = "";

	for (let i = postController.length -1; i >= 0; i--) {
		display += `
			<div class="col-lg-4 col-md-4 col-4" style="width: 20rem; margin-top: 1rem; margin-bottom: 1rem;">
                <div class="card">
                    <div class="card-header text-bg-light">
                        <h5 class="card-title">${postController[i].postTitle}</h5>
                    </div>
                    <div class="card-body">
                        <p class="card-text">${postController[i].postDescribe}</p>
                        <a id="posts${i}" href="showPostDetail" class="btn btn-primary" onClick="displayDetails(${i})">Read</a>
                    </div>
                </div>
            </div>`;
	}

    document.querySelector("#post-container").innerHTML = display;

} //End of renderPostPage function


async function getDetails()
{
	const postIndex1 = localStorage.getItem("postIndex");
	const postIndex = postIndex1;

	const postController1 = sessionStorage.getItem("posts");
	const postController2 = JSON.parse(postController1);

    // Convert date and time format to more readable format
    const dateString = postController2[postIndex].postTime;
    const date = new Date(dateString);
    const formattedDate = date.toLocaleString("en-CA", {
    dateStyle: "long",
    timeStyle: "short",
    hour12: "true"
    });

	await fetchUsers(); // use await to wait for fetchUsers() to complete

	const thisPostUserId = postController2[postIndex].user_id; //get user_id of this post
	
    // Use the `find` method to search for the first object that matches the condition
    const thisUserObject = userController.find(obj => obj.user_id === thisPostUserId); //this is the user Object that has the user_id of this post
	
    // localstorage.setItem("thisPostUser", userController);
	
	// const postUsername1 = localstorage.getItem("thisPostUser");
	// const postUsername2 = postUsername1[thisPostUserId].username;

    document.querySelector("#postTitle").innerHTML = postController2[postIndex].postTitle;
    document.querySelector("#postContent").innerHTML = postController2[postIndex].postFull;
    document.querySelector("#postMetadata").innerHTML = `${thisUserObject.username} </br>${formattedDate}`;
}



//4) Add new post to the postlist when user clicks on the submit button from the postform.html
function addPost(postTitle, postDescribe, postFull) {
    
    // formData is an Object provided by the Browser API for us to send the data over to the backend
    // Make sure names in the append matches the names from the ItemController java
    // no need to match sequence
    const formData = new FormData();
    formData.append('postTitle', postTitle);
    formData.append('postDescribe', postDescribe);
    formData.append('postFull', postFull);
    // formData.append('user_id', user_id);

    // Call the addAPI
    fetch(addAPI, {
        method: 'POST',
        body: formData
    })
        .then(function (response) {
            console.log(response.status); // Will show you the status - 200 OK, 500, 404
            if (response.ok) {
                alert("Successfully Added Post!")

                // Refresh the page
                window.location.reload()
            }
            else {
                alert("It has not been more than 24 hours since your last post.")
            }
        })
        .catch((error) => {
            console.error('Error:', error);
            alert("Error adding your post. Please try again")
        });
}

/*
To make it so the API can only be called once in 20 hours, you can use the following steps:

1. Create a function to make the API call.
2. In the function, add a check to see if the last API call was made within 20 hours.
3. If the last API call was made within 20 hours, throw an error.
4. If the last API call was not made within 20 hours, make the API call and return the response.

Here is an example of how to make it so the API can only be called once in 20 hours:
*/

// async function addPost(postTitle, postDescribe, postFull) {
// 	// Get the current time.
// 	const now = new Date();

// 	// Get the time of the last API call.
// 	const lastApiCallTime = localStorage.getItem("lastApiCallTime");
  
// 	// If the last API call was made within 20 hours, throw an error.
// 	if (lastApiCallTime && new Date(lastApiCallTime) > now - 20 * 60 * 60 * 1000) {
// 	  throw new Error("API call too soon");
// 	}

// 	// Make the API call.
// 	await fetch(addAPI);

// 	// Set the last API call time.
// 	localStorage.setItem("lastApiCallTime", now);
  
// 	// Return the response.
// 	return response;
// }


/*
```javascript
function makeApiCall() {
  // Get the current time.
  const now = new Date();

  // Get the time of the last API call.
  const lastApiCallTime = localStorage.getItem("lastApiCallTime");

  // If the last API call was made within 20 hours, throw an error.
  if (lastApiCallTime && new Date(lastApiCallTime) > now - 20 * 60 * 60 * 1000) {
    throw new Error("API call too soon");
  }

  // Make the API call.
  const response = await fetch("/api/users");

  // Set the last API call time.
  localStorage.setItem("lastApiCallTime", now);

  // Return the response.
  return response;
}

// Call the function from the frontend.
const response = await makeApiCall();
```
*/

/*
In this example, the API call will only be made if the last API call was made 
more than 20 hours ago. If the last API call was made within 20 hours, an error will be thrown.

You can adjust the 20 hour value to fit your needs. 
For example, if you want to limit API calls to once per day, 
you could set the value to 86400 seconds (24 hours).
*/


