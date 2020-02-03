const express = require('express');
const bodyParser = require('body-parser');
const authenticate = require('../authenticate');

const Promotions = require('../models/promotions');

const promoRouter = express.Router();
promoRouter.use(bodyParser.json());

promoRouter.route('/')
.get((req, res, next) => {
    Promotions.find({})
    .then(promos => {
        res.statusCode = 200;
        res.setHeader('Content-Type', 'application/json');
        res.json(promos); // send promos back to client as json response
    }, err => next(err))
    .catch(err => next(err));
})
.post(authenticate.verifyUser, authenticate.verifyAdmin, (req, res, next) => {
    Promotions.create(req.body)
    .then(promo => {
        console.log('Promo Created ', promo);
        res.statusCode = 200;
        res.setHeader('Content-Type', 'application/json');
        res.json(promo);
    }, err => next(err))
    .catch(err => next(err));
})
.put(authenticate.verifyUser, (req, res, next) => {
    res.statusCode = 403; // not supported status code
    res.end('PUT operation not supported on /promotions');
})
.delete(authenticate.verifyUser, authenticate.verifyAdmin, (req, res, next) => {
    Promotions.remove({})
    .then(resp => {
        res.statusCode = 200;
        res.setHeader('Content-Type', 'application/json');
        res.json(resp);
    }, err => next(err))
    .catch(err => next(err));
});

promoRouter.route('/:promoId')
.get((req, res, next) => {
    Promotions.findById(req.params.promoId)
    .then((promo) => {
        res.statusCode = 200;
        res.setHeader('Content-Type', 'application/json');
        res.json(promo); // send promos back to client as json response
    }, err => next(err))
    .catch(err => next(err));

})
.post(authenticate.verifyUser, (req, res, next) => {
    res.statusCode = 403; // not supported status code
    res.end('POST operation not supported on /promotions/' + req.params.promoId);
})
.put(authenticate.verifyUser, authenticate.verifyAdmin, (req, res, next) => {
    Promotions.findByIdAndUpdate(req.params.promoId, { $set: req.body }, { new: true })
    // new: true means return the updated promo as the json string in the reply
    .then((promo) => {
        res.statusCode = 200;
        res.setHeader('Content-Type', 'application/json');
        res.json(promo); // send promo back to client as json response
    }, err => next(err))
    .catch(err => next(err));

})
.delete(authenticate.verifyUser, authenticate.verifyAdmin, (req, res, next) => {
    Promotions.findByIdAndRemove(req.params.promoId)
    .then((promo) => {
        res.statusCode = 200;
        res.setHeader('Content-Type', 'application/json');
        res.json(promo); // send promos back to client as json response
    }, err => next(err))
    .catch(err => next(err));
});


module.exports = promoRouter;