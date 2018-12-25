import React, { Component } from 'react';
import {
    Card,
    CardImg,
    CardText,
    CardBody,
    CardTitle,
    Breadcrumb,
    BreadcrumbItem,
    Button,
    Modal,
    ModalHeader, ModalBody, Row, Label, Col
} from 'reactstrap';
import { Link } from 'react-router-dom';
import {Control, Errors, LocalForm} from "react-redux-form";
import { Loading } from './LoadingComponent';
import { baseUrl } from "../shared/baseUrl";
import { FadeTransform, Fade, Stagger } from 'react-animation-components';

function RenderDish ({dish}) {
        return (
            <FadeTransform in
                           transformProps={{
                               exitTransform: 'scale(0.5) translateY(-50%)'
                           }}>
                <Card>
                    <CardImg width="100%" src={baseUrl + dish.image} alt={dish.name} />
                    <CardBody>
                        <CardTitle> {dish.name} </CardTitle>
                        <CardText> {dish.description} </CardText>
                    </CardBody>
                </Card>
            </FadeTransform>
        );
    }

    function RenderComments({comments, postComment, dishId}) {
        const comms = comments.map((com) => {
            return (
                <Fade in>
                <div key={com.id}>
                    <p> {com.comment} </p>
                    <p>
                        --{com.author + ", "}
                        {new Intl.DateTimeFormat('en-US', {year: 'numeric',
                            month: 'short', day: '2-digit'}).format(new Date(Date.parse(com.date)))}
                    </p>
                </div>
                </Fade>
            );
        });

        return(
            <div>
                <Stagger in >
                    {comms}
                </Stagger>
                {<CommentForm dishId={dishId} postComment={postComment} />}
            </div>
        );
    }

const required = (val) => val && val.length;
const maxLength = (len) => (val) => !(val) || (val.length <= len);
const minLength = (len) => (val) => (val) && (val.length >= len);

class CommentForm extends Component {
    constructor(props) {
        super(props);
        this.state = {isModalOpen: false};
        this.toggleModal = this.toggleModal.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    toggleModal() {
        this.setState({isModalOpen: !this.state.isModalOpen})
    }

    handleSubmit(values) {
        this.props.postComment(this.props.dishId, values.rating, values.author, values.comment)
    }

    render() {
        return (
            <div>
            <Button color={ "primary" } onClick={ this.toggleModal } >
                <span className="fa fa-pencil-square-o fa-lg"/> Submit Comment
            </Button>
            <Modal isOpen={this.state.isModalOpen} toggle={this.toggleModal} >
                <ModalHeader toggle={this.toggleModal}> Submit Comment </ModalHeader>
                <ModalBody>
                    <div className={"col-12"}>
                        <LocalForm onSubmit={ (values) => this.handleSubmit(values) }>
                        <Row className={"form-group"}>
                            <Label htmlFor={"rating"}> Rating </Label>
                            <Control.select model=".rating" name="rating" className={"form-control"} id={"rating"} md={12}>
                                <option> 1 </option>
                                <option> 2 </option>
                                <option> 3 </option>
                                <option> 4 </option>
                                <option> 5 </option>
                            </Control.select>
                        </Row>
                        <Row className="form-group">
                            <Label htmlFor="name"> Your Name </Label>
                            <Control.text model=".author" className="form-control" id="name" name="author"
                                          placeholder="Your Name"
                                          validators={{
                                              required, minLength: minLength(2), maxLength: maxLength(15)
                                          }}
                            />
                            <Errors model=".author" className="text-danger" show="touched"
                                    messages={{
                                        required: 'Required',
                                        minLength: 'Must be greater than 2 characters',
                                        maxLength: 'Must be 15 characters or less'
                                    }}
                            />
                        </Row>
                        <Row className="form-group">
                            <Label htmlFor="message"> Comment </Label>
                            <Control.textarea model=".comment" id="message" name="comment" rows="8"
                                              className="form-control"/>
                        </Row>
                        <Row className="form-group">
                            <Col>
                                <Button type="submit" color="primary">
                                    Submit
                                </Button>
                            </Col>
                        </Row>
                        </LocalForm>
                    </div>
                </ModalBody>
            </Modal>
            </div>
        )
    }
}

const DishDetails = (props) => {
        if (props.isLoading) {
            return (
                <div className="container">
                    <div className="row">
                        <Loading />
                    </div>
                </div>
            );
        }

        else if (props.errMess) {
            return (
                <div className="container">
                    <div className="row">
                        <h4> {props.errMess} </h4>
                    </div>
                </div>
            );
        }

        else if (props.dish != null) {
            return (
                <div className="container">
                    <div className="row">
                        <Breadcrumb>
                            <BreadcrumbItem> <Link to="/menu"> Menu </Link> </BreadcrumbItem>
                            <BreadcrumbItem active> {props.dish.name} </BreadcrumbItem>
                        </Breadcrumb>
                        <div className="col-12">
                            <h3> {props.dish.name} </h3>
                            <hr/>
                        </div>

                        <div className="col-12 col-md-5 m-1">
                            <RenderDish dish={props.dish} />
                        </div>
                        <div className="col-12 col-md-5 m-1">
                            <h4> Comments </h4>
                            <RenderComments comments={props.comments} postComment={props.postComment} dishId={props.dish.id} />
                        </div>
                    </div>
                </div>
            );
        }

        else {
            return (
                <div></div>
            );
        }
    };


export default DishDetails;