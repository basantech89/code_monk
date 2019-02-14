import React, { Component } from 'react';
import Home from "./HomeComponent";
import Menu from './MenuComponent';
import Contact from './ContactComponent';
import About from "./AboutComponent";
import DishDetails from './DishDetailsComponent'
import Header from './HeaderComponent';
import Footer from './FooterComponent';
import { Switch, Route, Redirect, withRouter } from "react-router-dom";
import { connect } from 'react-redux';
import { postComment, postFeedback, fetchDishes, fetchComments, fetchPromos, fetchLeaders,
    fetchFavorites, postFavorite, deleteFavorite, loginUser, logoutUser, googleLogin } from "../redux/ActionCreators";
import { TransitionGroup, CSSTransition } from 'react-transition-group';
import Favorites from "./FavoriteComponent";
import { actions } from "react-redux-form";


// map redux states to props to available in main component
const mapStateToProps = state => {
    return {
        dishes: state.dishes,
        comments: state.comments,
        promotions: state.promotions,
        leaders: state.leaders,
        favorites: state.favorites,
        auth: state.auth
    }
};

const mapDispatchToProps = (dispatch) => ({                 // below action creator addComment will return an action object for adding a comment, that action object is given as the parameter to the dispatch function and that we are supplying as a function which we can use within our component and when we connect mapDispatchtoProps in connect function at bottom, addComment becomes available to use in mainComponent
    postComment: (dishId, rating, comment) => dispatch(postComment(dishId, rating, comment)),
    postFeedback: (feedback) => dispatch(postFeedback(feedback)),
    resetFeedbackForm: () => dispatch(actions.reset('feedback')),
    fetchDishes: () => { dispatch(fetchDishes()) },
    fetchComments: () => { dispatch(fetchComments()) },
    fetchPromos: () => { dispatch(fetchPromos()) },
    fetchLeaders: () => dispatch(fetchLeaders()),
    loginUser: (creds) => dispatch(loginUser(creds)),
    logoutUser: () => dispatch(logoutUser()),
    googleLogin: () => dispatch(googleLogin()),
    fetchFavorites: () => dispatch(fetchFavorites()),
    postFavorite: (dishId) => dispatch(postFavorite(dishId)),
    deleteFavorite: (dishId) => dispatch(deleteFavorite(dishId))
});


class Main extends Component {

    componentDidMount() {
        this.props.fetchDishes();
        this.props.fetchPromos();
        this.props.fetchComments();
        this.props.fetchLeaders();
        this.props.fetchFavorites();
    }

    componentWillUnmount() {
        this.props.logoutUser();
    }

    /*onDishSelect(dishId) {
        this.setState({ selectedDish: dishId });
    }*/

    render() {

        //External functional component
        const HomePage = () => {
            return (
                // all those dishes for which dish.featured is true, filter will return that as an array, so selecting the first item from that array
                <Home dish={this.props.dishes.dishes.filter((dish) => dish.featured)[0]}
                      dishesLoading={this.props.dishes.isLoading}
                      dishesErrMess={this.props.dishes.errMess}
                      promotion={this.props.promotions.promotions.filter((promo) => promo.featured)[0]}
                      promosLoading={this.props.promotions.isLoading}
                      promosErrMess={this.props.promotions.errMess}
                      leader={this.props.leaders.leaders.filter((leader) => leader.featured)[0]}
                      leadersLoading={this.props.leaders.isLoading}
                      leadersErrMess={this.props.leaders.errMess}
                />

            );
        };

        const DishWithId = ({match}) => {
            return (
                this.props.auth.isAuthenticated && this.props.favorites.favorites ?
                <DishDetails dish={this.props.dishes.dishes.filter((dish) => dish._id === match.params.dishId)[0]}
                             isLoading={this.props.dishes.isLoading}
                             errMess={this.props.dishes.errMess}
                             comments={this.props.comments.comments.filter((comment) => comment.dish === match.params.dishId)}
                             commentsErrMess={this.props.comments.errMess}
                             postComment={this.props.postComment}
                             favorite={
                                 this.props.favorites.favorites ?
                                 this.props.favorites.favorites.dishes.some((dish) => dish === match.params.dishId)
                                     : false
                             }
                             postFavorite={this.props.postFavorite}
                />
                :
                <DishDetails dish={this.props.dishes.dishes.filter((dish) => dish._id === match.params.dishId)[0]}
                             isLoading={this.props.dishes.isLoading}
                             errMess={this.props.dishes.errMess}
                             comments={this.props.comments.comments.filter((comment) => comment.dish === match.params.dishId)}
                             commentsErrMess={this.props.comments.errMess}
                             postComment={this.props.postComment}
                             favorite={false}
                             postFavorite={this.props.postFavorite}
                />
            );
        };

        const PrivateRoute = ({ component: Component, ...rest }) => (
            <Route {...rest} render={(props) => (
                this.props.auth.isAuthenticated
                    ? <Component {...props} />
                    : <Redirect to={{
                        pathname: '/home',
                        state: { from: props.location }
                    }}/>
            )}/>
        );

        return (
            <div>
                <Header
                    auth={this.props.auth}
                    loginUser={this.props.loginUser}
                    logoutUser={this.props.logoutUser}
                    googleLogin={this.props.googleLogin}
                />
                {/*<Menu dishes={ this.state.dishes }
                      onClick={ (dishId) => this.onDishSelect(dishId) } />
                <DishDetails
                    dish={ this.state.dishes.filter((dish) => dish.id === this.state.selectedDish)[0]} />
                */}
                <TransitionGroup>
                    <CSSTransition key={this.props.location.key} classNames={"page"} timeout={300} >
                        <Switch>
                            <Route path="/home" component={HomePage} />
                            <Route exact path="/menu" component={() => <Menu dishes={this.props.dishes} />} /> {/*Inline functional component*/}
                            <Route path="/menu/:dishId" component={DishWithId} />
                            <Route path="/aboutus" component={() => <About leaders={this.props.leaders.leaders} />} />
                            <PrivateRoute
                                exact path="/favorites" component={ () =>
                                <Favorites favorites={this.props.favorites}
                                           deleteFavorite={this.props.deleteFavorite}
                                           dishes={this.props.dishes}
                                /> }
                            />
                            <Route exact path={"/contactus"} component={() =>
                                <Contact resetFeedbackForm={this.props.resetFeedbackForm} postFeedback={this.props.postFeedback} /> }
                            />
                            <Redirect to="/home" />
                        </Switch>
                    </CSSTransition>
                </TransitionGroup>
                <Footer />
            </div>
        );
    }
}

export default withRouter(connect(mapStateToProps, mapDispatchToProps)(Main));