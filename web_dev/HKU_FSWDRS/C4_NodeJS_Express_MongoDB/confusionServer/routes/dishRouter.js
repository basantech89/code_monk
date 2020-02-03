// mini express app
const express = require('express');
const bodyParser = require('body-parser');
const authenticate = require('../authenticate');

const Dishes = require('../models/dishes');

const dishRouter = express.Router();
dishRouter.use(bodyParser.json());

dishRouter.route('/')
    // .all((req, res, next) => {
    //     res.statusCode = 200;
    //     res.setHeader('Content-Type', 'text/plain');
    //     next(); // means continue on to look for other /dishes specifications
    //     // since we have modified the res param, modified res will be passed in to .get or .post or ..
    //     // bcoz of next(), .get or .post will be executed as per the request (get or post or..)
    // })
    .get((req, res, next) => {
        // res.end('Will send all the dishes to you');
        Dishes.find({})
          .populate('comments.author')
          .then((dishes) => {
            res.statusCode = 200;
            res.setHeader('Content-Type', 'application/json');
            res.json(dishes); // send dishes back to client as json response
        }, err => next(err))
        .catch(err => next(err));
    })
    // 1st execute verifyUser middleware, on success, move on to the next middleware
    // if authentication fails, then passport will reply to the client with an appropriate msg
    .post(authenticate.verifyUser, authenticate.verifyAdmin, (req, res, next) => {
        // res.end('Will add the dish: ' + req.body.name + ' with details: ' + req.body.description);
        // body-parser has already parsed the body message and loaded it on to the body property of the request
        Dishes.create(req.body)
        .then(dish => {
            console.log('Dish Created ', dish);
            res.statusCode = 200;
            res.setHeader('Content-Type', 'application/json');
            res.json(dish);
        }, err => next(err))
        .catch(err => next(err));
    })
    .put(authenticate.verifyUser, (req, res, next) => {
        res.statusCode = 403; // not supported status code
        res.end('PUT operation not supported on /dishes');
    })
    .delete(authenticate.verifyUser, authenticate.verifyAdmin, (req, res, next) => {
        // res.end('Deleting all the dishes');
        Dishes.remove({})
        .then(resp => {
            res.statusCode = 200;
            res.setHeader('Content-Type', 'application/json');
            res.json(resp);
        }, err => next(err))
        .catch(err => next(err));
    });

dishRouter.route('/:dishId')
    // .all((req, res, next) => {
    //     res.statusCode = 200;
    //     res.setHeader('Content-Type', 'text/plain');
    //     next();
    // })
    .get((req, res, next) => {
        // res.end('Will send details of the dish: ' + req.params.dishId + ' to you!');
        Dishes.findById(req.params.dishId)
          .populate('comments.author')
          .then((dish) => {
            res.statusCode = 200;
            res.setHeader('Content-Type', 'application/json');
            res.json(dish); // send dishes back to client as json response
        }, err => next(err))
        .catch(err => next(err));

    })
    .post(authenticate.verifyUser, (req, res, next) => {
        res.statusCode = 403; // not supported status code
        res.end('POST operation not supported on /dishes/' + req.params.dishId);
    })
    .put(authenticate.verifyUser, authenticate.verifyAdmin, (req, res, next) => {
        // res.write('Updating the dish: ' + req.params.dishId + '\n');
        // res.end('Will update the dish: ' + req.body.name + ' with details ' + req.body.description);
        Dishes.findByIdAndUpdate(req.params.dishId, { $set: req.body }, { new: true })
        // new: true means return the updated dish as the json string in the reply
        .then((dish) => {
            res.statusCode = 200;
            res.setHeader('Content-Type', 'application/json');
            res.json(dish); // send dishes back to client as json response
        }, err => next(err))
        .catch(err => next(err));

    })
    .delete(authenticate.verifyUser, authenticate.verifyAdmin, (req, res, next) => {
        // res.end('Deleting dish: ' +req.params.dishId);
        Dishes.findByIdAndRemove(req.params.dishId)
        .then((dish) => {
            res.statusCode = 200;
            res.setHeader('Content-Type', 'application/json');
            res.json(dish); // send dishes back to client as json response
        }, err => next(err))
        .catch(err => next(err));
    });


/*
.get('/dishes/:dishId', (req, res, next) => {
    res.end('Will send details of the dish: ' + req.params.dishId + ' to you!');
});

app.post('/dishes/:dishId', (req, res, next) => {
    res.statusCode = 403; // not supported status code
    res.end('POST operation not supported on /dishes/' + req.params.dishId);

});

app.put('/dishes/:dishId', (req, res, next) => {
    res.write('Updating the dish: ' + req.params.dishId + '\n');
    res.end('Will update the dish: ' + req.body.name + ' with details ' + req.body.description);
});

app.delete('/dishes/:dishId', (req, res, next) => {
    res.end('Deleting dish: ' +req.params.dishId);
});
*/

dishRouter.route('/:dishId/comments')
    .get((req, res, next) => {
        Dishes.findById(req.params.dishId)
          .populate('comments.author')
          .then(dish => {
            if (dish != null) {
                res.statusCode = 200;
                res.setHeader('Content-Type', 'application/json');
                res.json(dish.comments);    
            }
            else {
                err = new Error('Dish ' + req.params.dishId + ' not found');
                err.statusCode = 404; // 404 not found
                return next(err);
            }
        }, err => next(err))
        .catch(err => next(err));
    })
    .post(authenticate.verifyUser, (req, res, next) => {
        Dishes.findById(req.params.dishId)
        .then(dish => {
            if (dish != null) {
                req.body.author = req.user._id;
                dish.comments.push(req.body);
                dish.save()
                .then(dish => {
                    Dishes.findById(dish._id)
                      .populate('comments.author')
                      .then(dish => {
                          res.statusCode = 200;
                          res.setHeader('Content-Type', 'application/json');
                          res.json(dish)
                      });
                }, err => next(err)) // error handler code inside then
            }
            else {
                err = new Error('Dish ' + req.params.dishId + ' not found');
                err.statusCode = 404; // 404 not found
                return next(err);
            }
        }, err => next(err)) // error handler for if
        .catch(err => next(err)); // error handler for findById
    })
    .put(authenticate.verifyUser, (req, res, next) => {
        res.statusCode = 403; // not supported status code
        res.end('PUT operation not supported on /dishes/' + req.params.dishId + '/comments');
    })
    .delete(authenticate.verifyUser, authenticate.verifyAdmin, (req, res, next) => {
        Dishes.findById(req.params.dishId)
        .then(dish => {
            if (dish != null) {
                for (var i = (dish.comments.length - 1); i >= 0; i--) {
                    dish.comments.id(dish.comments[i]._id).remove();
                }
                dish.save()
                .then(dish => {
                    res.statusCode = 200;
                    res.setHeader('Content-Type', 'application/json');
                    res.json(dish)
                }, err => next(err)) // error handler code inside then
            }
            else {
                err = new Error('Dish ' + req.params.dishId + ' not found');
                err.statusCode = 404; // 404 not found
                return next(err);
            }
        }, err => next(err)) // error handler for if
        .catch(err => next(err)); // error handler for findById

    });

dishRouter.route('/:dishId/comments/:commentId')
    .get((req, res, next) => {
        Dishes.findById(req.params.dishId)
          .populate('comments.author')
          .then((dish) => {
            if (dish != null && dish.comments.id(req.params.commentId) != null) {
                res.statusCode = 200;
                res.setHeader('Content-Type', 'application/json');
                res.json(dish.comments.id(req.params.commentId));    
            }
            else if (dish == null) {
                err = new Error('Dish ' + req.params.dishId + ' not found');
                err.statusCode = 404; // 404 not found
                return next(err);
            }
            else {
                err = new Error('Comment  ' + req.params.commentId + ' not found');
                err.statusCode = 404; // 404 not found
                return next(err);
            }
        }, err => next(err))
        .catch(err => next(err));
    })
    .post(authenticate.verifyUser, (req, res, next) => {
        res.statusCode = 403; // not supported status code
        res.end('POST operation not supported on /dishes/' + req.params.dishId + '/comments/' + req.params.commentId);
    })
    .put(authenticate.verifyUser, (req, res, next) => {
        Dishes.findById(req.params.dishId)
        .then((dish) => {
            if (dish != null && dish.comments.id(req.params.commentId) != null) {
                if (dish.comments.id(req.params.commentId).author.toString() === req.user._id.toString()) {
                    if (req.body.rating)
                        dish.comments.id(req.params.commentId).rating = req.body.rating;

                    if (req.body.comment)
                        dish.comments.id(req.params.commentId).comment = req.body.comment;

                    dish.save()
                      .then(dish => {
                          Dishes.findById(dish._id)
                            .populate('comments.author')
                            .then(dish => {
                                res.statusCode = 200;
                                res.setHeader('Content-Type', 'application/json');
                                res.json(dish)
                            });
                      }, err => next(err)) // error handler code inside then
                } else {
                    const err = new Error('You are not authorized to update this comment!');
                    err.status = 403;
                    return next(err);
                }
            }
            else if (dish == null) {
                const err = new Error('Dish ' + req.params.dishId + ' not found');
                err.statusCode = 404; // 404 not found
                return next(err);
            }
            else {
                const err = new Error('Comment  ' + req.params.commentId + ' not found');
                err.statusCode = 404; // 404 not found
                return next(err);
            }
        }, err => next(err))
        .catch(err => next(err));
    })
    .delete(authenticate.verifyUser, (req, res, next) => {
        Dishes.findById(req.params.dishId)
        .then(dish => {
            if (dish != null && dish.comments.id(req.params.commentId) != null) {
                if (dish.comments.id(req.params.commentId).author.toString() === req.user._id.toString()) {
                    dish.comments.id(req.params.commentId).remove();
                    dish.save()
                      .then(dish => {
                          Dishes.findById(dish._id)
                            .populate('comments.author')
                            .then(dish => {
                                res.statusCode = 200;
                                res.setHeader('Content-Type', 'application/json');
                                res.json(dish)
                            });
                      }, err => next(err)) // error handler code inside then
                } else {
                    const err = new Error('You are not authorized to delete this comment!');
                    err.status = 403;
                    return next(err);
                }
            }
            else if (dish == null) {
                err = new Error('Dish ' + req.params.dishId + ' not found');
                err.statusCode = 404; // 404 not found
                return next(err);
            }
            else {
                err = new Error('Comment  ' + req.params.commentId + ' not found');
                err.statusCode = 404; // 404 not found
                return next(err);
            }
        }, err => next(err)) // error handler for if
        .catch(err => next(err));
    });


module.exports = dishRouter;
