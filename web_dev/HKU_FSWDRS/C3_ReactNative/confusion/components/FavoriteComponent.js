import React, { Component } from 'react';
import { View, FlatList, Text, Alert } from 'react-native';
import { ListItem } from 'react-native-elements';
import { connect } from 'react-redux';
import { baseUrl } from "../shared/baseUrl";
import { Loading } from "./LoadingComponent";
import Swipeout from 'react-native-swipeout';
import { deleteFavorite } from "../redux/ActionCreators";
import * as Animatable from 'react-native-animatable';

// mapping redux states to props
const mapStateToProps = state => {
    return {
        dishes: state.dishes,
        favorites: state.favorites
    }
};

const mapDispatchToProps = dispatch => ({
    deleteFavorite: (dishId) => dispatch(deleteFavorite(dishId))
});

class Favorites extends Component {

    static navigationOptions = {
        title: 'My Favorites'
    };

    render() {
        const { navigate } = this.props.navigation;

        const renderMenuItem = ({item, index}) => {

            const rightButton = [
                {
                    text: 'Delete',
                    type: 'delete',
                    onPress: () => {
                        Alert.alert(
                            'Delete Favorite?',
                            'Are you sure you wish to delete the favorite dish ' + item.name + '?',
                            [
                                {
                                    // cancel button
                                    text: 'Cancel',
                                    onPress: () => console.log(item.name + ' Not Deleted'),
                                    style: 'cancel'
                                },
                                {
                                    // OK button
                                    text: 'OK',
                                    onPress: () => this.props.deleteFavorite(item.id)
                                }
                            ],
                            // can't just dismiss the dialog, user will have to press wither on cancel or ok button
                            { cancelable: false }
                        );
                    }
                }
            ];

            return (
                <Swipeout right={rightButton} autoClose={true}>
                    <Animatable.View animation="fadeInRightBig" duration={2000}>

                        <ListItem
                            key={index}
                            title={item.name}
                            subtitle={item.description}
                            hideChevron={true}
                            // will navigate to Dishdetail component where the parameter is dishId
                            onPress={() => navigate('Dishdetail', { dishId: item.id })}
                            leftAvatar={{ source: { uri: baseUrl + item.image }}}
                        />

                    </Animatable.View>
                </Swipeout>
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
                <FlatList
                    // from list of dishes, get each dish and for each dish, examine the favorites array to see if
                    // that dish id exists in the favorites array, some returns true if exists
                    data={this.props.dishes.dishes.filter(dish => this.props.favorites.some(el => el === dish.id))}
                    renderItem={renderMenuItem}
                    keyExtractor={item => item.id.toString()}
                />
            );
        }
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(Favorites);