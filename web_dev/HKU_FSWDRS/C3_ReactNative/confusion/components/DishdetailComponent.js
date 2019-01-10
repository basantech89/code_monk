import React, { Component } from 'react';
import {View, Text, ScrollView, FlatList, Modal, StyleSheet, Button, Alert, PanResponder, Share } from 'react-native';
import {Card, Icon, Input, Rating } from 'react-native-elements';
import { connect } from 'react-redux';
import { baseUrl } from "../shared/baseUrl";
import { postFavorite, postComment } from "../redux/ActionCreators";
import * as Animatable from 'react-native-animatable';

// mapping redux states to props
const mapStateToProps = state => {
    return {
        dishes: state.dishes,
        comments: state.comments,
        favorites: state.favorites
    }
};

const mapDispatchToProps = dispatch => ({
    postFavorite: (dishId) => dispatch(postFavorite(dishId)),
    postComment: (comment) => dispatch(postComment(comment))
});

function RenderDish(props) {
    const dish = props.dish;

    // also work // handleViewRef = ref => this.view = ref;
    this.handleViewRef = ref => this.view = ref;

    /*
    * @param dx: distance of gesture alon the X direction
    * */
    const recognizeDrag = ({ moveX, moveY, dx, dy }) => {
        return dx < -200;
    };

    const recognizeLeftToRightDrag = ({ moveX, moveY, dx, dy }) => {
        return dx > 200;
    };

    const panResponder = PanResponder.create({
        // callbacks (pan handler functions)
        // callback function when user's gesture begins on the screen
        onStartShouldSetPanResponder: (event, gestureState) => {
            return true; // return true to indicate that this will pick up the pan gesture and start responding to it
        },
        // will be called when the panResponder has given the access to this panhandler to handle the gesture
        onPanResponderGrant: () => {
            // applying rubberBand animation for 1 second to the view to which we already got the reference
            this.view.rubberBand(1000)
                // endState: state at the end of animation
                // finished property returns true if the animation was performed correctly and false otherwise
                .then(endState => console.log(endState.finished ? 'finished' : 'cancelled'));
        },
        // invoked when the user lift the finger off the screen after performing gesture
        onPanResponderEnd: (event, gestureState) => {
            if (recognizeDrag(gestureState)) {
                Alert.alert(
                    'Add to favorites?',
                    'Are you sure wish to add ' + dish.name + ' to your favorites?',
                    [
                        {
                            text: 'Cancel',
                            onPress: () => console.log('Cancel pressed'),
                            style: 'cancel'
                        },
                        {
                            text: 'OK',
                            onPress: () => props.favorite ? console.log('Already favorite') : props.onPress()
                        }
                    ],
                    { cancelable: false }
                )
            }

            else if (recognizeLeftToRightDrag(gestureState)) {
                props.onComment()
            }

            return true;
        }
    });

    const shareDish = (title, message, url) => {
        Share.share({
            title: title,
            message: title + ': ' + message + ' ' + url,
            url: url
        }, {
            dialogTitle: 'Share ' + title
        });
    };

    if (dish != null) {
        return (
            // all the pan handler functions (callbacks) that we have implemented will be added in to this view
            // ref: get a reference to this view
            <Animatable.View animation="fadeInDown" duration={2000} delay={1000}
                             ref={this.handleViewRef} {...panResponder.panHandlers}
            >
                <Card
                    featuredTitle={dish.name}
                    // image={ require('./images/uthappizza.png') } >
                    // get the image from server if using redux
                    image={{ uri: baseUrl + dish.image }} >
                    {/*Only some of the CSS properties are supported, be careful*/}
                    <Text style={{margin: 10}}>
                        {dish.description}
                    </Text>
                    <View style={{ flex: 1, flexDirection: 'row', alignItems: 'center', justifyContent: 'center'}}>
                        {/* marking this dish as the favorite dish */}
                        {/* raised displays the icon as a button, reverse will reverse the color */}
                        <Icon name={props.favorite ? 'heart' : 'heart-o'} type='font-awesome' color='#F50' raised reverse
                              onPress={() => props.favorite ? console.log('Already favorite') : props.onPress()}
                        />
                        <Icon name='pencil' type='font-awesome' color='#512DA8' raised reverse
                              onPress={() => props.onComment() }
                        />
                        <Icon
                            raised reverse name='share' type="font-awesome" color='#51D2A8'
                            onPress={ () => shareDish(dish.name, dish.description, baseUrl + dish.image )}
                        />
                    </View>
                </Card>
            </Animatable.View>
        );
    }
    else return <View/>
}

function RenderComments(props) {
        const comments = props.comments;

        const renderCommentItem = ({item, index}) => {
            return (
                <View key={index} style={{margin: 10}}>
                    <Text style={{fontSize: 14}}> {item.comment} </Text>
                    <Rating readonly startingValue={item.rating} imageSize={18} style={{ alignItems: 'flex-start'}} />
                    {/*<Text style={{fontSize: 12}}> {item.rating} Stars </Text>*/}
                    <Text style={{fontSize: 12}}> {'-- ' + item.author + ', ' + item.date} </Text>
                </View>
            );
        };

        return (
            <Animatable.View animation="fadeInUp" duration={2000} delay={1000} >
                <Card title="Comments">
                    <FlatList data={comments} renderItem={renderCommentItem} keyExtractor={item => item.id.toString()} />
                </Card>
            </Animatable.View>
        );
}

// function Dishdetail(props) {
class Dishdetail extends Component {
    constructor(props) {
        super(props);
        this.state = {
            // no need dishes and comments, because they are coming in from redux store
            /*dishes: DISHES,
            comments: COMMENTS,*/
            // favorites now is managed by redux store
            // favorites: []
            rating: 1,
            author: '',
            comment: '',
            showModal: false
        };
    }

    toggleModal() {
        this.setState({ showModal: !this.state.showModal })
    }

    resetForm() {
        this.setState({
            showModal: false
        });
    }

    handleComment(dishId) {
        let comments = this.props.comments.comments.filter((comment) => comment.dishId === dishId);
        let newComment = {
            id: comments.length,
            dishId: dishId,
            rating: this.state.rating,
            author: this.state.author,
            comment: this.state.comment,
            date: new Date().toLocaleString()
        };
        this.props.postComment(newComment);
        this.toggleModal();
    }

    markFavorite(dishId) {
        // this.setState({ favorites: this.state.favorites.concat(dishId)})
        // favorite is no longer a local state, being managed by redux store now
        this.props.postFavorite(dishId);
    }

    static navigationOptions = {
        title: 'Dish Details'
    };

    render() {
        // passing the dish information that is clicked
        // navigation property is passed into all the components in navigator
        // getParam allows me to get access to the parameters that are passed in
        // '' (empty string) is the fallback option for dishId
        const dishId = this.props.navigation.getParam('dishId', '');
        return (
            <ScrollView>
                <RenderDish dish={this.props.dishes.dishes[+dishId]}
                            // some will check every item in favorites array and returns true if that item matches dishId
                            favorite={this.props.favorites.some(id => id === dishId)}
                            onPress={ () => this.markFavorite(dishId) }
                            onComment={ () => this.toggleModal() }
                />
                {/* filter returns an array */}
                {/*<RenderComments comments={this.state.comments.filter((comment) => comment.dishId === dishId)} />*/}
                <RenderComments comments={this.props.comments.comments.filter((comment) => comment.dishId === dishId)} />

                <Modal
                    style={styles.modal}
                    animationType={'slide'}
                    transparent={false}
                    visible={this.state.showModal}
                    onDismiss={ () => { this.toggleModal(); this.resetForm() } }
                    onRequestClose={ () => { this.toggleModal(); this.resetForm() } }
                >
                    <View style={styles.row}>
                        <Rating
                            showRating
                            type='star'
                            ratingCount={5}
                            fractions={1}
                            onFinishRating={(rating) => this.setState({rating: rating})}
                            style={{ paddingVertical: 10 }}
                        />
                        <Input
                            style={styles.rowItem}
                            placeholder='Author'
                            leftIcon={ <Icon type={"font-awesome"} name={'user'} size={24} color='white' /> }
                            onChangeText={(author) => this.setState({author: author})}
                        />
                        <Input
                            style={styles.rowItem}
                            placeholder='Comment'
                            leftIcon={ <Icon type={"font-awesome"} name={'comment'} size={24} color='white' /> }
                            onChangeText={(comment) => this.setState({comment: comment})}
                        />
                        <Button
                            style={styles.rowItem}
                            title={'Submit'}
                            color='#512DA8'
                            accessibilityLabel='Submit'
                            onPress={ () => this.handleComment(dishId) }
                        />
                        <Button
                            style={styles.rowItem}
                            title={'Cancel'}
                            accessibilityLabel='Cancel'
                            onPress={ () => this.toggleModal() }
                        />
                    </View>
                </Modal>

            </ScrollView>
        );
    }
}

const styles = StyleSheet.create({
    row: {
        flex: 1,
        margin: 20
    },
    rowItem: {
        flex: 1,
    },
    modal: {
        justifyContent: 'center',
        margin: 20
    },
});

// export default Dishdetail;
// connect Dishdetail component to redux store
export default connect(mapStateToProps, mapDispatchToProps)(Dishdetail);