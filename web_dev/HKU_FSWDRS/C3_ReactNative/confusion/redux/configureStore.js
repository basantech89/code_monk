// Redux Store = Dispatch + Reducer + State
// Action -> View -> Store -> Action
// actions are handled appropriately using reducers in redux store
// any changes made to store would be done through pure functions (reducers)
// reducers take previous state and action and return next state (doesn't mutate previous state)
// any state changes will be then reflected to the views and the views may rerender based on this
// Redux data flow is unidirectional
// Redux Middleware(example - redux thunk) allows you to write action creators that returns a function instead of the action

import { createStore, applyMiddleware } from "redux";
import thunk from 'redux-thunk';
import logger from 'redux-logger';
import { dishes } from "./dishes";
import { comments } from "./comments";
import { promotions } from "./promotions";
import { leaders } from "./leaders";
import { favorites } from "./favorites";
import { persistStore, persistCombineReducers } from "redux-persist";
import storage from 'redux-persist/es/storage';

export const ConfigureStore = () => {

    const config = {
        key: 'root',
        storage,
        debug: true
    };

    const store = createStore(
        // combineReducers({
        persistCombineReducers(config, {
            dishes,
            comments,
            promotions,
            leaders,
            favorites
        }),
        applyMiddleware(thunk, logger)
    );

    const persistor = persistStore(store);

    // return store;
    return {persistor, store}
};