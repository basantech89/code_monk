import * as ActionTypes from './ActionTypes';

export const Comments = (state = {
    errMess: null,
    comments: []
}, action) => {
    switch(action.type) {
        case ActionTypes.ADD_COMMENTS:
            return {...state, isLoading: false, errMess: null, comments: action.payload};

        case ActionTypes.COMMENTS_FAILED:
            return {...state, isLoading: false, errMess: action.payload, comments: []};

        case ActionTypes.ADD_COMMENT:
            var comment = action.payload;
            console.log("Comment: ", comment);
            return { ...state, comments: state.comments.concat(comment) };  // concat return a new object, so we aren't mutating the state, we are adding
                                            // comment in memory, so when u restart the app, any comment hat you add
                                            // by filling in the form will be lost, changes aren't persistent yet
        default:
            return state;
    }
};