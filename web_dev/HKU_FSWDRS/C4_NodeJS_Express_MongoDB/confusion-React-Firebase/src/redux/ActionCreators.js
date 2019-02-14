import * as ActionTypes from './ActionTypes';
import { auth, fireauth, firestore, firebasestore } from "../firebase/firebase";

/*
* a function that creates an action object
* */
export const addFeedback = (feedback) => ({
    // return a plain JS Object, that's what is the action
    type: ActionTypes.ADD_FEEDBACK,
    payload: feedback
});

// a thunk for POST Feedback Function
export const postFeedback = (feedback) => (dispatch) => {

    return firestore.collection('feedback').add(feedback)
        .then(response =>  {
            console.log('Feedback', response);
            alert("Thank you for your feedback!\n");
        })
        .catch(error => {
            console.log('Feedback', error.message);
            alert('Your feedback could not be posted\nError: ' + error.message);
        })
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

    if (!auth.currentUser) {
        console.log('No user logged in!');
        return;
    }

    return firestore.collection('comments').add({
        author: {
            '_id': auth.currentUser.uid,
            'firstname': auth.currentUser.displayName ? auth.currentUser.displayName : auth.currentUser.email
        },
        dish: dishId,
        rating: rating,
        comment: comment,
        createdAt: firebasestore.FieldValue.serverTimestamp(),
        updatedAt: firebasestore.FieldValue.serverTimestamp()
    })
        .then(docRef => {
            firestore.collection('comments').doc(docRef.id).get()
                .then(doc => {
                    if (doc.exists) {
                        const data = doc.data();
                        const _id = doc.id;
                        let comment = {_id, ...data};
                        dispatch(addComment(comment))
                    } else
                    // doc.data() will be undefined in this case
                        console.log("No such document");
                });
        })
        .catch(error => {
            console.log('Post comment ', error.message);
            alert('Your comment could not be posted\nError: ' + error.message);
        });
};

// DISHES
// creating this as a thunk, so it will return a function
export const fetchDishes = () => (dispatch) => {
    dispatch(dishesLoading(true));
    return firestore.collection('dishes').get()
        // snapshot of current state of dishes array
        .then(snapshot => {
            let dishes = [];
            snapshot.forEach(doc => {
                const data = doc.data();
                const _id = doc.id;
                dishes.push({ _id, ...data });
            });
            return dishes;
        })
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
    return firestore.collection('comments').get()
        .then(snapshot => {
                let comments = [];
                snapshot.forEach(doc => {
                    const data = doc.data();
                    const _id = doc.id;
                    comments.push({ _id, ...data });
                });
                return comments;
            })
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

    return firestore.collection('promotions').get()
        .then(snapshot => {
            let promos = [];
            snapshot.forEach(doc => {
                const data = doc.data();
                const _id = doc.id;
                promos.push({ _id, ...data });
            });
            return promos;
        })
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
    dispatch(leadersLoading());

    return firestore.collection('leaders').get()
        .then(snapshot => {
            let leaders = [];
            snapshot.forEach(doc => {
                const data = doc.data();
                const _id = doc.id;
                leaders.push({ _id, ...data });
            });
            return leaders;
        })
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
export const requestLogin = () => {
    return {
        type: ActionTypes.LOGIN_REQUEST,
    }
};

export const receiveLogin = (user) => {
    return {
        type: ActionTypes.LOGIN_SUCCESS,
        user
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
    return auth.signInWithEmailAndPassword(creds.email, creds.password)
        .then(() => {
            var user = auth.currentUser;
            // not explicitly tracking any user info or token, that is done by auth
            localStorage.setItem('user', JSON.stringify(user));
            // Dispatch the success action
            dispatch(fetchFavorites());
            dispatch(receiveLogin(user));
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
    auth.signOut()
        .then(() => {
            // Sign-out successful
        }).catch((error) => {
            // An error happened
    });
    localStorage.removeItem('user');
    dispatch(favoritesFailed("Error 401: Unauthorized"));
    dispatch(receiveLogout())
};


// Handling Favorites
export const postFavorite = (dishId) => (dispatch) => {
    if (!auth.currentUser) {
        console.log('No user logged in!');
        return;
    }

    return firestore.collection('favorites').add({
        user: auth.currentUser.uid,
        dish: dishId
    })
        .then(docRef => {
            firestore.collection('favorites').doc(docRef.id).get()
                .then(doc => {
                    if (doc.exists)
                        dispatch(fetchFavorites());
                    else
                        // doc.data() will be undefined in this case
                        console.log('No such document!');
                })
        })
        .catch(error => dispatch(favoritesFailed(error.message)));
};

export const deleteFavorite = (dishId) => (dispatch) => {

    if (!auth.currentUser) {
        console.log('No user logged in!');
        return;
    }

    var user = auth.currentUser;

    return firestore.collection('favorites')
        .where('user', '==', user.uid)
        .where('dish', '==', dishId).get()
        .then(snapshot => {
            console.log(snapshot);
            snapshot.forEach(doc => {
                console.log(doc.id);
                firestore.collection('favorites').doc(doc.id).delete()
                    .then(() => {
                        dispatch(fetchFavorites());
                    })
            });
        })
        .catch(error => dispatch(favoritesFailed(error.message)));
};

// redux thunk
export const fetchFavorites = () => (dispatch) => {

    if (!auth.currentUser) {
        console.log('No user logged in!');
        return;
    }

    var user = auth.currentUser;
    dispatch(favoritesLoading(true));

    return firestore.collection('favorites').where('user', '==', user.uid).get()
        .then(snapshot => {
            let favorites = { user: user, dishes: [] };
            snapshot.forEach(doc => {
                const data = doc.data();
                favorites.dishes.push(data.dish);
            });
            console.log(favorites);
            return favorites;
        })
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

export const googleLogin = () => (dispatch) => {
    const provider = new fireauth.GoogleAuthProvider();

    auth.signInWithPopup(provider)
        .then(result => {
            var user = result.user;
            localStorage.setItem('user', JSON.stringify(user));
            // Dispatch the success action
            dispatch(fetchFavorites());
            dispatch(receiveLogin(user));
        })
        .catch(error => {
            dispatch(loginError(error.message));
        });
};