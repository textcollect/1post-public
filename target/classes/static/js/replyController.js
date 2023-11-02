const postIndex = localStorage.getItem("postIndex");
const postController1 = sessionStorage.getItem("posts");
const postController2 = JSON.parse(postController1);
const postId = postController2[postIndex].idPost; //id of this post

// Development APIs
const addReplyAPI = 'http://localhost:8080/reply/add';
const thisPostReplyAPI = `http://localhost:8080/reply/thisPostReply/${postId}`;

// Production APIs
// const addReplyAPI = 'https://onepostweb.azurewebsites.net/reply/add';
// const thisPostReplyAPI = `https://onepostweb.azurewebsites.net/reply/thisPostReply/${postId}`;


// Initialise an empty array that will be used to store the data received from displayAPI ('/allpost' api)
let replyController = [];

function displayReply() {

    //fetch all replies for this post from database using the REST API endpoint from Spring Boot
    // GET http method is the default, so no need to specify the specifics for GET
    return fetch(thisPostReplyAPI)
        .then((resp) => resp.json())
        .then(function (data) {
            console.log("3. receive reply data")
            console.log(data);

            data.forEach(function (reply, index) {
                const replyObj = {
                    idReply: reply.idReply,
                    replyText: reply.replyText,
                    replyTime: reply.replyTime,
                    user_id: reply.user_id,
                    idPost: reply.idPost
                };
                // this array contains replies from the db(received via the '/allreply' API)
                replyController.push(replyObj);
            });

            // replyController[idpost]

			// calls the function to display all the 4 objects from the postController array
            // function is declared below
            // renderReply();
        })
        .catch(function (error) {
            console.log(error);
        });
}

async function renderReply() {
    await displayReply();
    await fetchUsers(); // use await to wait for fetchUsers() to complete

    let display = "";
    let thisReplyUserId = 0;
    let thisUserObject = {};

    let i = 0;
    while (i < replyController.length)
    {
        thisReplyUserId = replyController[i].user_id;
        console.log(thisReplyUserId.toString());

        // Use the `find` method to search for the first object that matches the condition
        thisUserObject = userController.find(obj => obj.user_id === thisReplyUserId);
        
        display += `
            <div class="card col-12 my-2 col-sm-auto" style="width: fit-content;">
                <div class="card-body">
                    <h6 id="replyUsername" class="card-title">${thisUserObject.username}</h6>
                    <pre id="replyContent" class="card-text">${replyController[i].replyText}</pre>
                </div>
            </div>`;
        i++;
    }
    // if want to display the replyTime, can just include it in the while loop; e.g.
    // <p id="replyMetadata" style="font-size: small; font-style: italic; font-weight: lighter;">Replied on: ${replyController[i].replyTime}</p>
    
    // display all replies
    document.querySelector("#replyContainer").innerHTML = display;
}


//4) Add new product to the product list when user clicks on the submit button from the productform.html
function addReply(replyText, idPost) {
    
    // formData is an Object provided by the Browser API for us to send the data over to the backend
    // Make sure names in the append matches the names from the ItemController java
    // no need to match sequence
    const formData = new FormData();
    formData.append('replyText', replyText);
    formData.append('idPost', idPost);
    
    // Call the addAPI
    fetch(addReplyAPI, {
        method: 'POST',
        body: formData
    })
        .then(function (response) {
            console.log(response.status); // Will show you the status - 200 OK, 500, 404
            if (response.ok) {
                alert("Successfully Added Reply!")

                // Refresh the page
                window.location.reload()
            }
            else {
                alert("It has not been more than 24 hours since your last reply.")

                // Refresh the page
                window.location.reload()
            }
        })
        .catch((error) => {
            console.error('Error:', error);
            alert("Error adding your reply. Please try again")
        });
}