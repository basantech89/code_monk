import { View, FlatList } from 'react-native';
import React, { Component } from 'react';
import {Text, Tile} from 'react-native-elements';
import { connect } from 'react-redux';
import { baseUrl } from "../shared/baseUrl";
import {Loading} from "./LoadingComponent";
import * as Animatable from 'react-native-animatable';

// mapping redux states to props
const mapStateToProps = state => {
    return {
        dishes: state.dishes,
    }
};


// function Menu(props) {
class Menu extends Component {

    // no need, because states are coming in from redux store
    /*constructor(props) {
        super(props);
        this.state = {
            dishes: DISHES
        }
    }*/

    static navigationOptions = {
        title: 'Menu'
    };

    render () {

        const { navigate } = this.props.navigation;

        const renderMenuItem = ({item, index}) => {
            return (
                <Animatable.View animation="fadeInRightBig" duration={2000} >

                    {/*<ListItem*/}
                    <Tile
                        key={index}
                        title={item.name}
                        // subtitle={item.description}
                        // caption is used for Tile
                        caption={item.description}
                        // no longer need to hide chevron because we are using Tile now
                        // hideChevron={true} // hide the right arrow used to render lists in IOS
                        featured // added later
                        /* below will go to main component and main component will set the
                            selected dish then the Dishdetail component will be rerendered with
                            the selected dish appropriate */
                        // onPress={() => props.onPress(item.id)}
                        // dishId will be accessed by getParam method of navigation property in Dishdetail component
                        onPress={() => navigate('Dishdetail', { dishId: item.id })}
                        // leftAvatar={{ source: require('./images/uthappizza.png')}} // JS object supplied
                        imageSrc={{ uri: baseUrl + item.image }}
                    />

                </Animatable.View>
            );
        };

        if (this.props.dishes.isLoading) {
            return (
                <Loading/>
            );
        }

        else if (this.props.dishes.errMess) {
            return (
                <View>
                    <Text> {this.props.dishes.errMess} </Text>
                </View>
            );
        }

        else {
            return (
                // flatlist will iterate through each item in dishes array and will be rendered
                // by the view given in renderMenuItem
                <FlatList
                    // states are now mapped to props
                    // data={this.state.dishes}
                    data={this.props.dishes.dishes}
                    renderItem={renderMenuItem}
                    keyExtractor={item => item.id.toString()} // supported by flatlist, accept a string
                />
            );
        }
    };
}

// export default Menu;
// connect Menu component to redux store
export default connect(mapStateToProps)(Menu);