import React, { Component } from 'react';
import Home from './HomeComponent';
import Menu from './MenuComponent';
import Dishdetail from "./DishdetailComponent";
import Reservation from './ReservationComponent';
import Favorites from './FavoriteComponent';
import Login from './LoginComponent';
import { View, Platform, Image, StyleSheet, ScrollView, Text, NetInfo, ToastAndroid } from 'react-native';
import { createStackNavigator, createDrawerNavigator, DrawerItems, SafeAreaView } from 'react-navigation';
import Contact from "./ContactComponent";
import About from './AboutComponent';
import { Icon } from 'react-native-elements';
import { connect } from 'react-redux';
import { fetchDishes, fetchComments, fetchPromos, fetchLeaders } from "../redux/ActionCreators";

// mapping redux states to props
const mapStateToProps = state => {
    return {
    }
};

const mapDispatchToProps = dispatch => ({
    fetchDishes: () => dispatch(fetchDishes()),
    fetchComments: () => dispatch(fetchComments()),
    fetchPromos: () => dispatch(fetchPromos()),
    fetchLeaders: () => dispatch(fetchLeaders())
});

// StackNavigator component
const MenuNavigator = createStackNavigator({
    // Menu is a JS object with parameter as screen which specifies the component
    // to which you navigate when you make this choice
    Menu: { screen: Menu,
        // navigationOptions defined as a function
        // navigation prop
    navigationOptions: ({ navigation }) => ({
        // three horizontal parallel lines
        headerLeft: <Icon name={'menu'} size={24} color={'white'} onPress={() => navigation.toggleDrawer()} />
    })
    },
    Dishdetail: { screen: Dishdetail }
}, {
    initialRouteName: 'Menu',
    // common configuration for all the screens can be specified as below
    // navigation option defined as a JS object, can also be defined as a function
    navigationOptions: {
        headerStyle: {
            backgroundColor: '#512DA8'
        },
        headerTintColor: '#FFF', // icon color
        headerTitleStyle: {
            color: '#FFF'
        }
    }
});

// creating below to provide the status bar
const HomeNavigator = createStackNavigator({
    Home: { screen: Home }
}, {
    navigationOptions: ({ navigation }) => ({
        headerStyle: {
            backgroundColor: '#512DA8'
        },
        headerTintColor: '#FFF', // icon color
        headerTitleStyle: {
            color: '#FFF'
        },
        headerLeft: <Icon name={'menu'} size={24} color={'white'} onPress={() => navigation.toggleDrawer()} />
    })
});

const ContactNavigator = createStackNavigator({
    Contact: { screen: Contact }
}, {
    navigationOptions: ({ navigation }) => ({
        headerStyle: {
            backgroundColor: '#512DA8'
        },
        headerTintColor: '#FFF',
        headerTitleStyle: {
            color: '#FFF'
        },
        headerLeft: <Icon name={'menu'} size={24} color={'white'} onPress={() => navigation.toggleDrawer()} />
    })
});

const AboutNavigator = createStackNavigator({
    About: { screen: About }
}, {
    navigationOptions: ({ navigation }) => ({
        headerStyle: {
            backgroundColor: '#512DA8'
        },
        headerTintColor: '#FFF',
        headerTitleStyle: {
            color: '#FFF'
        },
        headerLeft: <Icon name={'menu'} size={24} color={'white'} onPress={() => navigation.toggleDrawer()} />
    })
});

const ReservationNavigator = createStackNavigator({
    Reservation: { screen: Reservation }
}, {
    navigationOptions: ({ navigation }) => ({
        headerStyle: {
            backgroundColor: '#512DA8'
        },
        headerTintColor: '#FFF',
        headerTitleStyle: {
            color: '#FFF'
        },
        headerLeft: <Icon name={'menu'} size={24} color={'white'} onPress={() => navigation.toggleDrawer()} />
    })
});

const FavoritesNavigator = createStackNavigator({
    Favorites: { screen: Favorites }
}, {
    navigationOptions: ({ navigation }) => ({
        headerStyle: {
            backgroundColor: '#512DA8'
        },
        headerTintColor: '#FFF',
        headerTitleStyle: {
            color: '#FFF'
        },
        headerLeft: <Icon name={'menu'} size={24} color={'white'} onPress={() => navigation.toggleDrawer()} />
    })
});

const LoginNavigator = createStackNavigator({
    Login: { screen: Login }
}, {
    navigationOptions: ({ navigation }) => ({
        headerStyle: {
            backgroundColor: '#512DA8'
        },
        headerTintColor: '#FFF',
        headerTitleStyle: {
            color: '#FFF'
        },
        headerLeft: <Icon name={'menu'} size={24} color={'white'} onPress={() => navigation.toggleDrawer()} />
    })
});


const CustomDrawerContentComponent = (props) => {
    return (
        <ScrollView>
            {/*SafeArea is specifically for iPhoneX, defines part of the area as a safe area where nothing else will be laid out
            always put this drawer on top even if it covers status bar on top*/}

            <SafeAreaView style={styles.container} forceInset={{ top: 'always', horizontal: 'never' }}>
                <View style={styles.drawerHeader}>
                    <View style={{flex: 1}}>
                        <Image source={ require('./images/logo.png') } style={styles.drawerImage}/>
                    </View>

                    <View style={{flex: 2}}>
                        <Text style={styles.drawerHeaderText}> Ristorante Con Fusion </Text>
                    </View>
                </View>

                {/* using ...props, the menu items(Home, About Us, Contact Us..) are drawn here */}
                <DrawerItems {...props} />
            </SafeAreaView>

        </ScrollView>
    );

};

// DrawerNavigator
const MainNavigator = createDrawerNavigator({
    Login: {
        screen: LoginNavigator,
        navigationOptions: {
            title: 'Login',
            drawerLabel: 'Login',
            drawerIcon: ({ tintColor }) => ( <Icon name={'sign-in'} type={'font-awesome'} size={24} color={tintColor} />)
        }
    },
    Home: {
        screen: HomeNavigator,
        navigationOptions: {
            title: 'Home',
            drawerLabel: 'Home',
            drawerIcon: ({ tintColor }) => ( <Icon name={'home'} type={'font-awesome'} size={24} color={tintColor} />)
        }
    },
    Menu: {
        screen: MenuNavigator,
        navigationOptions: {
            title: 'Menu',
            drawerLabel: 'Menu',
            drawerIcon: ({ tintColor }) => ( <Icon name={'list'} type={'font-awesome'} size={24} color={tintColor} />)
        }
    },
    Contact: {
        screen: ContactNavigator,
        navigationOptions: {
            title: 'Contact Us',
            drawerLabel: 'Contact Us',
            drawerIcon: ({ tintColor }) => ( <Icon name={'address-card'} type={'font-awesome'} size={22} color={tintColor} />)
        }
    },
    About: {
        screen: AboutNavigator,
        navigationOptions: {
            title: 'About Us',
            drawerLabel: 'About Us',
            drawerIcon: ({ tintColor }) => ( <Icon name={'info-circle'} type={'font-awesome'} size={24} color={tintColor} />)
        }
    },
    Favorites: {
        screen: FavoritesNavigator,
        navigationOptions: {
            title: 'My Favorites',
            drawerLabel: 'My Favorites',
            drawerIcon: ({ tintColor }) => ( <Icon name={'heart'} type={'font-awesome'} size={24} color={tintColor} />)
        }
    },
    Reservation: {
        screen: ReservationNavigator,
        navigationOptions: {
            title: 'Reserve Table',
            drawerLabel: 'Reserve Table',
            drawerIcon: ({ tintColor }) => ( <Icon name={'cutlery'} type={'font-awesome'} size={24} color={tintColor} />)
        }
    }
}, {
    initialRouteName: 'Home',
    drawerBackgroundColor: '#D1C4E9',
    contentComponent: CustomDrawerContentComponent // layout of the drawer will be as specified in CustomDrawerContentComponent
});

class Main extends Component {
    // moving the state to their own components
    /*constructor(props) {
        super(props);
        this.state = {
            dishes: DISHES,
            selectedDish: null
        };
    }

    onDishSelect(dishId) {
        this.setState({
            selectedDish: dishId
        });
    }*/

    componentDidMount() {
        // each of below statements issue a fetch to the server using fetch API
        // once the data is obtained, that will be loaded into redux store
        this.props.fetchDishes();
        this.props.fetchComments();
        this.props.fetchPromos();
        this.props.fetchLeaders();

        NetInfo.getConnectionInfo()
            .then((connectionInfo) => {
                ToastAndroid.show('Initial Network Connectivity Type: ' + connectionInfo.type +
                    ', effectiveType: ' + connectionInfo.effectiveType, ToastAndroid.LONG)
            });

        NetInfo.addEventListener('connectionChange', this.handleConnectivityChange);
    }

    componentWillUnmount() {
        NetInfo.removeEventListener('connectionChange', this.handleConnectivityChange)
    }

    handleConnectivityChange = (connectionInfo) => {
        switch (connectionInfo.type) {
            case 'none':
                ToastAndroid.show('You are now offline!', ToastAndroid.LONG);
                break;
            case 'wifi':
                ToastAndroid.show('You are now connected to WiFi!', ToastAndroid.LONG);
                break;
            case 'cellular':
                ToastAndroid.show('You are now connected to Cellular!', ToastAndroid.LONG);
                break;
            case 'unknown':
                ToastAndroid.show('You now have an unknown connection!', ToastAndroid.LONG);
                break;
            default:
                break;
        }
    };

    render () {
        return (
            <View style={{ flex: 1, paddingTop: Platform.OS === 'ios' ? 0 : Expo.Constants.statusBarHeight }}>
                <MainNavigator />
                {/* below lines are commented to implement react navigation
                <Menu dishes={this.state.dishes}
                    // dishId will be accepted from Menu component's onPress -- item.id
                      onPress={(dishId) => this.onDishSelect(dishId)}
                />
                <Dishdetail dish={this.state.dishes.filter((dish) => dish.id === this.state.selectedDish)[0]} />*/}
            </View>
        );
    }
}

// Inline JS based styles
const styles = StyleSheet.create({
    container: {
        flex: 1
    },
    drawerHeader: {
        backgroundColor: '#512DA8',
        height: 140,
        alignItems: 'center',
        justifyContent: 'center',
        flex: 1,
        flexDirection: 'row'
    },
    drawerHeaderText: {
        color: 'white',
        fontSize: 24,
        fontWeight: 'bold'
    },
    drawerImage: {
        margin: 10,
        width: 80,
        height: 60
    }
});

// export default Main;
// connect Main component to redux store
export default connect(mapStateToProps, mapDispatchToProps)(Main);