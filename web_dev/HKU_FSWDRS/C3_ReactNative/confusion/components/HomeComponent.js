import React, { Component } from 'react';
import { Text, View, Animated, Easing } from 'react-native';
import { Card } from 'react-native-elements';
import { connect } from 'react-redux';
import { baseUrl } from "../shared/baseUrl";
import {Loading} from "./LoadingComponent";

// mapping redux states to props
const mapStateToProps = state => {
    return {
        dishes: state.dishes,
        promotions: state.promotions,
        leaders: state.leaders
    }
};


function RenderItem(props) {
    const item = props.item;

    if (props.isLoading) {
        return (
            <Loading/>
        );
    }

    else if (props.errMess) {
        return (
            <View>
                <Text> {props.errMess} </Text>
            </View>
        );
    }

    else {
        if ( item != null ) {
            return (
                <Card
                    featuredTitle={item.name}
                    featuredSubtitle={item.designation}
                    // image={ require('./images/uthappizza.png') }
                    // get the image from server if using redux
                    image={{ uri: baseUrl + item.image }}
                >
                    <Text style={{margin: 10}}>
                        {item.description}
                    </Text>
                </Card>
            );
        }

        else return <View/>;
    }

}

class Home extends Component {

    // no need to define dishes, promotions and leaders here because these states are coming in from redux store
    constructor(props) {
        super(props);
        this.animatedValue = new Animated.Value(0); // Animated Value type object which is used by animated API
        /*this.state = {
            dishes: DISHES,
            promotions: PROMOTIONS,
            leaders: LEADERS
        }*/
    }

    static navigationOptions = {
        title: 'Home'
    };

    componentDidMount() {
        this.animate();
    }

    animate() {
        this.animatedValue.setValue(0);
        // timing method take the animatedValue and change that as a function of time
        Animated.timing(
            this.animatedValue,
            {
                toValue: 8,
                duration: 8000, // 8 seconds
                easing: Easing.linear
            }
        ).start(() => this.animate()) // end of this animation, repeat it again and again (recursion)
    // we change the value like this, we can use that value to control certain properties
    }

    render() {
        // interpolate means as the animatedValue changes, map that value to a corresponding different value here
        // we can use xpos1 as a way to control the X position of an item
        // will us transform on an object to control this
        const xpos1 = this.animatedValue.interpolate({
            inputRange: [0, 1, 3, 5, 8], // as the animatedValue changes 0, 1, 3, 5 8
            outputRange: [1200, 600, 0, -600, -1200] // change xpos1 to 1200, 600...
        });
        const xpos2 = this.animatedValue.interpolate({
            inputRange: [0, 2, 4, 6, 8],
            outputRange: [1200, 600, 0, -600, -1200]
        });
        const xpos3 = this.animatedValue.interpolate({
            inputRange: [0, 3, 5, 7, 8], // as the animatedValue changes 0, 1, 3, 5 8
            outputRange: [1200, 600, 0, -600, -1200] // change xpos1 to 1200, 600...
        });

        return (
            <View style={{ flex: 1, flexDirection: 'row', justifyContent: 'center' }}>

                <Animated.View style={{ width: '100%', transform: [{ translateX: xpos1 }] }}>
                    <RenderItem
                        // item={ this.state.dishes.filter((dish) => dish.featured)[0] }
                        /* states are now mapped to props */
                        item={this.props.dishes.dishes.filter((dish) => dish.featured)[0]}
                        isLoading={this.props.dishes.isLoading}
                        errMess={this.props.dishes.errMess}
                    />
                </Animated.View>

                <Animated.View style={{ width: '100%', transform: [{ translateX: xpos2 }] }}>
                    <RenderItem
                        // item={ this.state.promotions.filter((promo) => promo.featured)[0] }
                        item={this.props.promotions.promotions.filter((promo) => promo.featured)[0]}
                        isLoading={this.props.promotions.isLoading}
                        errMess={this.props.promotions.errMess}
                    />
                </Animated.View>

                <Animated.View style={{ width: '100%', transform: [{ translateX: xpos3 }] }}>
                    <RenderItem
                        item={this.props.leaders.leaders.filter((leader) => leader.featured)[0]}
                        isLoading={this.props.leaders.isLoading}
                        errMess={this.props.leaders.errMess}
                    />
                </Animated.View>

            </View>
        );
    }
}

// export default Home;
// connect Home component to redux store
export default connect(mapStateToProps)(Home);