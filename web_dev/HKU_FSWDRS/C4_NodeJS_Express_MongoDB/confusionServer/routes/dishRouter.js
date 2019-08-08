// mini express app
const express = require('express');
const bodyParser = require('body-parser');

const dishRouter = express.Router();
dishRouter.use(bodyParser.json());

dishRouter.route('/')
    .all((req, res, next) => {
        res.statusCode = 200;
        res.setHeader('Content-Type', 'text/plain');
        next(); // means continue on to look for otcher /dishes specifications
        // since we have modified the res param, modified res will be passed in to .get or .post or ..
        // bcoz of next(), .get or .post will be executed as per the request (get or post or..)
    })
    .get((req, res, next) => {
        res.end('Will send all the dishes to you');
    })
    .post((req, res, next) => {
        res.end('Will add the dish: ' + req.body.name + ' with details: ' + req.body.description);
    })
    .put((req, res, next) => {
        res.statusCode = 403; // not supported status code
        res.end('PUT operation not supported on /dishes');
    })
    .delete((req, res, next) => {
        res.end('Deleting all the dishes');
    });

dishRouter.route('/:dishId')
    .all((req, res, next) => {
        res.statusCode = 200;
        res.setHeader('Content-Type', 'text/plain');
        next();
    })
    .get((req, res, next) => {
        res.end('Will send details of the dish: ' + req.params.dishId + ' to you!');
    })
    .post((req, res, next) => {
        res.statusCode = 403; // not supported status code
        res.end('POST operation not supported on /dishes/' + req.params.dishId);
    })
    .put((req, res, next) => {
        res.write('Updating the dish: ' + req.params.dishId + '\n');
        res.end('Will update the dish: ' + req.body.name + ' with details ' + req.body.description);
    })
    .delete((req, res, next) => {
        res.end('Deleting dish: ' +req.params.dishId);
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

module.exports = dishRouter;