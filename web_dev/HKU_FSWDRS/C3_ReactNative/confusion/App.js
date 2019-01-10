import React from 'react';
import Main from './components/MainComponent';
import { Provider } from 'react-redux';
import { ConfigureStore } from "./redux/configureStore";
import { PersistGate } from 'redux-persist/es/integration/react';
import { Loading } from "./components/LoadingComponent";

// const store = ConfigureStore();
const { persistor, store } = ConfigureStore();

export default class App extends React.Component {
  render() {
    return (
      //  connect to redux store
        <Provider store={store}>
            {/* update app to persist the info */}
            <PersistGate persistor={persistor} loading={<Loading/>}>
                <Main />
            </PersistGate>
        </Provider>
    );
  }
}
