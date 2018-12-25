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
import { postComment, postFeedback, fetchDishes, fetchComments, fetchPromos, fetchLeaders } from "../redux/ActionCreators";
import { TransitionGroup, CSSTransition } from 'react-transition-group';


// map redux states to props to available in main component
const mapStateToProps = state => {
    return {
        dishes: state.dishes,
        comments: state.comments,
        promotions: state.promotions,
        leaders: state.leaders,
    }
};

const mapDispatchToProps = (dispatch) => ({                 // below action creator addComment will return an action object for adding a comment, that action object is given as the parameter to the dispatch function and that we are supplying as a function which we can use within our component and when we connect mapDispatchtoProps in connect function at bottom, addComment becomes available to use in mainComponent
    postComment: (dishId, rating, author, comment) => dispatch(postComment(dishId, rating, author, comment)),
    postFeedback: (firstname, lastname, telnum, email, agree, contactType, message) => dispatch(postFeedback(firstname, lastname, telnum, email, agree, contactType, message)),
    fetchDishes: () => { dispatch(fetchDishes()) },
    fetchComments: () => { dispatch(fetchComments()) },
    fetchPromos: () => { dispatch(fetchPromos()) },
    fetchLeaders: () => dispatch(fetchLeaders())
});


class Main extends Component {

    componentDidMount() {
        this.props.fetchDishes();
        this.props.fetchComments();
        this.props.fetchPromos();
        this.props.fetchLeaders();
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
                <DishDetails dish={this.props.dishes.dishes.filter((dish) => dish.id === parseInt(match.params.dishId, 10))[0]}
                             isLoading={this.props.dishes.isLoading}
                             errMess={this.props.dishes.errMess}
                             comments={this.props.comments.comments.filter((comment) => comment.dishId === parseInt(match.params.dishId, 10))}
                             commentsErrMess={this.props.comments.errMess}
                             postComment={this.props.postComment}
                />
            );
        };


        return (
            <div>
                <Header />
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
                            <Route exact path={"/contactus"} component={() => <Contact postFeedback={this.props.postFeedback} /> } />
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