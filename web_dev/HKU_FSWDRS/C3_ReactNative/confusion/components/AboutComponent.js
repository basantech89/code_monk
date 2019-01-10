import React, { Component } from 'react';
import {Card, ListItem, Text} from "react-native-elements";
import { ScrollView, FlatList } from 'react-native';
import { connect } from 'react-redux';
import { baseUrl } from "../shared/baseUrl";
import {Loading} from "./LoadingComponent";
import * as Animatable from 'react-native-animatable';

// mapping redux states to props
const mapStateToProps = state => {
    return {
        leaders: state.leaders
    }
};

const RenderHistory = () => {
    return (
        <Card title={"Our History"}>
            <Text style={{margin: 10}}>
                Started in 2010, Ristorante con Fusion quickly established itself as a culinary icon par excellence in Hong Kong. With its unique brand of world fusion cuisine that can be found nowhere else, it enjoys patronage from the A-list clientele in Hong Kong.  Featuring four of the best three-star Michelin chefs in the world, you never know what will arrive on your plate the next time you visit us.
            </Text>
            <Text style={{margin: 10}}>
                The restaurant traces its humble beginnings to The Frying Pan, a successful chain started by our CEO, Mr. Peter Pan, that featured for the first time the world's best cuisines in a pan.
            </Text>
        </Card>
    );
};


const RenderLeaders = ({item, index}) => {
    return (
        <ListItem
            key={index}
            title={item.name}
            subtitle={item.description}
            hideChevron={true}
            // leftAvatar={{ source: require('./images/alberto.png') }}
            // get the image from server if using redux
            leftAvatar={{ source: { uri: baseUrl + item.image } }}
        />
    );
};

class About extends Component {

    // no need, because states are coming in from redux store
    /*constructor(props) {
        super(props);
        this.state = {
            leaders: LEADERS
        }
    }*/

    static navigationOptions = {
        title: 'About Us'
    };

    render() {

        if (this.props.leaders.isLoading) {
            return (
                <ScrollView>
                    <RenderHistory/>
                    <Card title={"Corporate Leadership"}>
                        <Loading />
                    </Card>
                </ScrollView>
            );
        }

        else if (this.props.leaders.errMess) {
            return (
                <ScrollView>
                    {/* 1 sec delay after the component is mounted */}
                    <Animatable.View animation="fadeInDown" duration={2000} delay={1000} >
                        <RenderHistory/>
                        <Card title={"Corporate Leadership"}>
                            <Text> {this.props.leaders.errMess} </Text>
                        </Card>
                    </Animatable.View>
                </ScrollView>
            );
        }

        else {
            return (
                <ScrollView>
                    <Animatable.View animation="fadeInDown" duration={2000} delay={1000} >
                        <RenderHistory/>
                        <Card title={"Corporate Leadership"}>
                            <FlatList
                                // data={ this.state.leaders }
                                // after using redux, states are mapped to props
                                data={this.props.leaders.leaders}
                                renderItem={ RenderLeaders }
                                keyExtractor={ leader => leader.id.toString() }
                            />
                        </Card>
                    </Animatable.View>
                </ScrollView>
            );
        }
    }
}

// export default About;
// connect About component to redux store
export default connect(mapStateToProps)(About);