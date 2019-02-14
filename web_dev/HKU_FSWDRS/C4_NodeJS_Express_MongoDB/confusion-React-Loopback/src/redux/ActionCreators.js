import * as ActionTypes from './ActionTypes';
import { baseUrl } from '../shared/baseUrl';
import { actions } from "react-redux-form";

/*
* a function that creates an action object
* */
export const addFeedback = (feedback) => ({
    // return a plain JS Object, that's what is the action
    type: ActionTypes.ADD_FEEDBACK,
    payload: feedback
});

// a thunk for POST Feedback Function
export const postFeedback = (firstname, lastname, telnum, email, agree, contactType, message) => (dispatch) => {
    const newFeedback = {
        firstname: firstname,
        lastname: lastname,
        telnum: telnum,
        email: email,
        agree: agree,
        contactType: contactType,
        message: message,
    };
    newFeedback.date = new Date().toISOString();

    return fetch(baseUrl + 'feedback', {
        method: 'POST',
        body: JSON.stringify(newFeedback),
        headers: {
            'Content-Type': 'application/json'
        },
        credentials: 'same-origin'
    })
        .then(response => {
                if (response.ok) {
                    return response;
                }

                else {
                    var error = new Error('Error ' + response.status + ': ' + response.statusText);
                    error.response = response;
                    throw error; // server is responding with an error response code, will be handled by catch
                }
            },
            error => {
                var errmess = new Error(error.message); // if client unable to connect to the server
                throw errmess;
            })
        .then(response => response.json())  // server will include an id into the comment and then send back the updated comment
        .then(response =>  {
            dispatch(addFeedback(response));
            alert("Thank you for your feedback!\n" + JSON.stringify(response));
            dispatch(actions.reset('feedback'));
        })
        .catch(error => { console.log('Post feedback', error.message);
        alert('Your feedback could not be posted\nError: ' + error.message); })
};


/*
* a function that creates an action object
* */
export const addComment = (comment) => ({
    // return a plain JS Object, that's what is the action
    type: ActionTypes.ADD_COMMENT,
    payload: comment
});

// a thunk for posting a new comment
export const postComment = (dishId, rating, comment) => (dispatch) => {

    let token = JSON.parse(localStorage.getItem('token'));
    if (!token)
        return;

    const newComment = {
        dishesId: dishId,
        customerId: token.userId,
        rating: rating,
        comment: comment
    };

    return fetch(baseUrl + 'api/comments', {
        method: 'POST',
        body: JSON.stringify(newComment),
        headers: {
            'Content-Type': 'application/json',
            'Authorization': token.id
        },
        credentials: 'same-origin'
    })
        .then(response => {
                if (response.ok) {
                    return response;
                }

                else {
                    var error = new Error('Error ' + response.status + ': ' + response.statusText);
                    error.response = response;
                    throw error; // server is responding with an error response code, will be handled by catch
                }
            },
            error => {
                var errmess = new Error(error.message); // if client unable to connect to the server
                throw errmess;
            })
        .then(response => response.json())  // server will include an id into the comment and then send back the updated comment
        .then(response => dispatch(fetchComments()))
        .catch(error => { console.log('Post comments', error.message);
        alert('Your comment could not be posted\nError: ' + error.message); })
};

// DISHES
// creating this as a thunk, so it will return a function
export const fetchDishes = () => (dispatch) => {
    dispatch(dishesLoading(true));

    return fetch(baseUrl + 'api/dishes')
        .then(response => {
                if (response.ok) {
                    return response;
                }

                else {
                    var error = new Error('Error ' + response.status + ': ' + response.statusText);
                    error.response = response;
                    throw error; // server is responding with an error response code, will be handled by catch
                }
            },
            error => {
                var errmess = new Error(error.message); // if client unable to connect to the server
                throw errmess;
            })
        .then(response => response.json())
        .then(dishes => dispatch(addDishes(dishes)))
        .catch(error => dispatch(dishesFailed(error.message)));

};

// action object - returns an action
export const dishesLoading = () => ({
    type: ActionTypes.DISHES_LOADING
});

// action object - returns an action
export const dishesFailed = (errmess) => ({
    type: ActionTypes.DISHES_FAILED,
    payload: errmess
});

// action object - returns an action
export const addDishes = (dishes) => ({
    type: ActionTypes.ADD_DISHES,
    payload: dishes
});


// COMMENTS
export const fetchComments = () => (dispatch) => {
    return fetch(baseUrl + 'api/comments?filter={"include":["customer"]}')
        .then(response => {
                if (response.ok) {
                    return response;
                }

                else {
                    var error = new Error('Error ' + response.status + ': ' + response.statusText);
                    error.response = response;
                    throw error; // server is responding with an error response code, will be handled by catch
                }
            },
            error => {
                var errmess = new Error(error.message); // if client unable to connect to the server
                throw errmess;
            })
        .then(response => response.json())
        .then(comments => dispatch(addComments(comments)))
        .catch(error => dispatch(commentsFailed(error.message)));
};


// action object - returns an action
export const commentsFailed = (errmess) => ({
    type: ActionTypes.COMMENTS_FAILED,
    payload: errmess
});

// action object - returns an action
export const addComments = (comments) => ({
    type: ActionTypes.ADD_COMMENTS,
    payload: comments
});


// PROMOS
// creating this as a thunk, so it will return a function
export const fetchPromos = () => (dispatch) => {
    dispatch(promosLoading(true));

    return fetch(baseUrl + 'api/promotions')
        .then(response => {
                if (response.ok) {
                    return response;
                }

                else {
                    var error = new Error('Error ' + response.status + ': ' + response.statusText);
                    error.response = response;
                    throw error; // server is responding with an error response code, will be handled by catch
                }
            },
            error => {
                var errmess = new Error(error.message); // if client unable to connect to the server
                throw errmess;
            })
        .then(response => response.json())
        .then(promos => dispatch(addPromos(promos)))
        .catch(error => dispatch(promosFailed(error.message)));
};

// action object - returns an action
export const promosLoading = () => ({
    type: ActionTypes.PROMOS_LOADING
});

// action object - returns an action
export const promosFailed = (errmess) => ({
    type: ActionTypes.PROMOS_FAILED,
    payload: errmess
});

// action object - returns an action
export const addPromos = (promos) => ({
    type: ActionTypes.ADD_PROMOS,
    payload: promos
});


// LEADERS
// creating this as a thunk, so it will return a function
export const fetchLeaders = () => (dispatch) => {
    dispatch(leadersLoading(true));

    return fetch(baseUrl + 'api/leaders')
        .then(response => {
                if (response.ok) {
                    return response;
                }

                else {
                    var error = new Error('Error ' + response.status + ': ' + response.statusText);
                    error.response = response;
                    throw error; // server is responding with an error response code, will be handled by catch
                }
            },
            error => {
                var errmess = new Error(error.message); // if client unable to connect to the server
                throw errmess;
            })
        .then(response => response.json())
        .then(leaders => dispatch(addLeaders(leaders)))
        .catch(error => dispatch(leadersFailed(error.message)));

};

// action object - returns an action
export const leadersLoading = () => ({
    type: ActionTypes.LEADERS_LOADING
});

// action object - returns an action
export const leadersFailed = (errmess) => ({
    type: ActionTypes.LEADERS_FAILED,
    payload: errmess
});

// action object - returns an action
export const addLeaders = (leaders) => ({
    type: ActionTypes.ADD_LEADERS,
    payload: leaders
});

// Handling Login and Logout
export const requestLogin = (creds) => {
    return {
        type: ActionTypes.LOGIN_REQUEST,
        creds
    }
};

export const receiveLogin = (response) => {
    return {
        type: ActionTypes.LOGIN_SUCCESS,
        token: response.id
    }
};

export const loginError = (message) => {
    return {
        type: ActionTypes.LOGIN_FAILURE,
        message
    }
};

export const loginUser = (creds) => (dispatch) => {
    // we dispatch requestLogin to kickoff the call to the API
    dispatch(requestLogin(creds));
    return fetch(baseUrl + 'api/Customers/login', {
        method: 'POST',
        headers: { 'Content-Type':'application/json' },
        body: JSON.stringify(creds)
    })
        .then(response => {
            if (response.status === 200)
                return response;
            else {
                var error = new Error('Error ' + response.status + ': ' + response.statusText);
                error.response = response;
                throw error;
            }
        },
            error => {
                throw error;
            })
        .then(response => response.json())
        .then(response => {
            if (response.id) {
                // If login was successful, set the token in local storage
                localStorage.setItem('token', JSON.stringify(response));
                localStorage.setItem('creds', JSON.stringify(creds));
                // Dispatch the success action
                dispatch(fetchFavorites());
                dispatch(receiveLogin(response));
            }
            else {
                var error = new Error('Error ' + response.status);
                error.response = response;
                throw error;
            }
        })
        .catch(error => dispatch(loginError(error.message)))
};

export const requestLogout = () => {
    return {
        type: ActionTypes.LOGOUT_REQUEST
    }
};

export const receiveLogout = () => {
    return {
        type: ActionTypes.LOGOUT_SUCCESS
    }
};

// Logs the user out
export const logoutUser = () => (dispatch) => {
    dispatch(requestLogout());

    let token = JSON.parse(localStorage.getItem('token'));
    return fetch(baseUrl + 'api/Customers/logout?access_token=' + token.id, {
        method: 'POST'
    })
        .then(response => {
                if (response.status === 204) {
                    localStorage.removeItem('token');
                    localStorage.removeItem('creds');
                    dispatch(favoritesFailed("Error 401: Unauthorized"));
                    dispatch(receiveLogout())
                }

                else {
                    var error = new Error('Error ' + response.status + ': ' + response.statusText);
                    error.response = response;
                    throw error;
                }
            },
            error => {
                throw error;
            })
        .catch(error => console.log('Logout Error ' + error.message));
};


// Handling Favorites
export const postFavorite = (dishId) => (dispatch) => {
    let token = JSON.parse(localStorage.getItem('token'));
    if (!token)
        return;

    return fetch(baseUrl + 'api/favorites/',{
        method: "POST",
        body: JSON.stringify({ dishesId: dishId, customerId: token.userId }),
        headers: {
            "Content-Type": "application/json",
            'Authorization': token.id
        },
        credentials: "same-origin"
    })
        .then(response => {
            if (response.ok)
                return response;
            else {
                var error = new Error('Error ' + response.status + ': ' + response.statusText);
                error.response = response;
                throw error;
            }
        },
            error => {
                throw error;
            })
        .then(response => response.json())
        // adding the favorites to our redux store
        .then(favorites => { console.log('Favorites Added', favorites); dispatch(fetchFavorites()); })
        .catch(error => dispatch(favoritesFailed(error.message)));
};

export const deleteFavorite = (id) => (dispatch) => {
    let token = JSON.parse(localStorage.getItem('token'));
    if (!token)
        return;

    return fetch(baseUrl + 'api/favorites/' + id, {
        method: "DELETE",
        headers: {'Authorization': token.id},
        credentials: "same-origin"
    })
        .then(response => {
            if (response.ok)
                return response;
            else {
                var error = new Error('Error ' + response.status + ': ' + response.statusText);
                error.response = response;
                throw error;
            }
        },
            error => {
                throw error;
            })
        .then(response => response.json())
        // adding the favorites to our redux store
        .then(favorites => { console.log('Favorites Deleted', favorites); dispatch(fetchFavorites()); })
        .catch(error => dispatch(favoritesFailed(error.message)));
};

// redux thunk
export const fetchFavorites = () => (dispatch) => {
    dispatch(favoritesLoading(true));

    let token = JSON.parse(localStorage.getItem('token'));
    if (!token)
        return dispatch(favoritesFailed('No User Logged in!'));

    return fetch(baseUrl + 'api/Customers/' + token.userId + '/favorites?filter={"include":["dishes"]}', {
        headers: { 'Authorization': token.id }
    })
        .then((response) => {
            console.log(response);
            if (response.ok)
                return response;
            else {
                let error = new Error('Error ' + response.status + ': ' + response.statusText);
                error.response = response;
                throw error;
            }
        },
            error => {
                var errmess = new Error(error.message);
                throw errmess;
            })
        .then(response => response.json())
        // adding the favorites to our redux store
        .then(favorites => dispatch(addFavorites(favorites)))
        .catch(error => dispatch(favoritesFailed(error.message)));
};

export const favoritesLoading = () => ({
    type: ActionTypes.FAVORITES_LOADING
});

export const favoritesFailed = (errmess) => ({
    type: ActionTypes.FAVORITES_FAILED,
    payload: errmess
});

export const addFavorites = (favorites) => ({
    type: ActionTypes.ADD_FAVORITES,
    payload: favorites
});