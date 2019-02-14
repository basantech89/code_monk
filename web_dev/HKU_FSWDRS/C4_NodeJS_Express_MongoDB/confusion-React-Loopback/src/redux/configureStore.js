import { createStore, combineReducers, applyMiddleware } from 'redux';
import { createForms } from "react-redux-form";
import { Dishes } from "./dishes";
import { Comments } from "./comments";
import { Promotions } from "./promotions";
import { Leaders } from "./leaders";
import { favorites } from "./favorites";
import { Auth } from "./auth";
import thunk from 'redux-thunk';
import logger from 'redux-logger';
import { InitialFeedback } from "./forms";


export const ConfigureStore = () => {
    // createStore takes an enhancer as the second parameter
    // applyMiddleware returns a store enhancer
    const store = createStore(
        combineReducers({
            dishes: Dishes,
            comments: Comments,
            promotions: Promotions,
            leaders: Leaders,
            auth: Auth,
            favorites,
            // ...createForms will add in necessary reducer functions and also the state info into create store
            // react-redux-form bring its own set of reducers and action creators etc so we won't need to write these
            ...createForms({
                feedback: InitialFeedback
            })
        }),
        applyMiddleware(thunk, logger)
    );

    return store;
};