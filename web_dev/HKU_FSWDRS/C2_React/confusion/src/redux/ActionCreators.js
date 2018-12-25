import * as ActionTypes from './ActionTypes';
import { baseUrl } from '../shared/baseUrl';
import {actions} from "react-redux-form";

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
export const postComment = (dishId, rating, author, comment) => (dispatch) => {
    const newComment = {
        dishId: dishId,
        rating: rating,
        author: author,
        comment: comment
    };
    newComment.date = new Date().toISOString();

    return fetch(baseUrl + 'comments', {
        method: 'POST',
        body: JSON.stringify(newComment),
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
        .then(response => dispatch(addComment(response)))
        .catch(error => { console.log('Post comments', error.message);
        alert('Your comment could not be posted\nError: ' + error.message); })
};

// DISHES
// creating this as a thunk, so it will return a function
export const fetchDishes = () => (dispatch) => {
    dispatch(dishesLoading(true));

    return fetch(baseUrl + 'dishes')
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
    return fetch(baseUrl + 'comments')
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

    return fetch(baseUrl + 'promotions')
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

    return fetch(baseUrl + 'leaders')
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
