import * as ActionTypes from './ActionTypes';

// dishes reducer
export const Dishes = (state = {
    isLoading: true,
    errMess: null,
    dishes: []
}, action) => {
    switch(action.type) {
        case ActionTypes.ADD_DISHES:
            return {...state, isLoading: false, errMess: null, dishes: action.payload};

        case ActionTypes.DISHES_LOADING:  // return a modified copy of the state
            return {...state, isLoading: true, errMess: null, dishes: []};

        case ActionTypes.DISHES_FAILED:
            return {...state, isLoading: false, errMess: action.payload, dishes: []};

        default:
            return state;
    }
};
