var express = require('express');
const bodyParser = require('body-parser');
var User = require("../models/user");
var passport = require('passport');
const authenticate = require('../authenticate');

var router = express.Router();
router.use(bodyParser.json());

router.get('/', authenticate.verifyUser, authenticate.verifyAdmin, function (req, res, next) {
    User.find({})
      .then(users => {
          res.statusCode = 200;
          res.setHeader('Content-Type', 'application/json');
          res.json(users); // send promos back to client as json response
      }, err => next(err))
      .catch(err => next(err));
});

/*router.post('/signup', function (req, res, next) {
    // username and password will be provided as a JSON string inside the body of the incoming request
    // the body would have been already parsed by the body-parser
    User.findOne({username: req.body.username})
        .then((user) => {
            // user already exists
            if (user != null) {
                var err = new Error("User " + req.body.username + " already exists");
                err.status = 403;
                next(err)
            }
            else {
                // user doesn't exist, this else will return a promise which is being handled in the next then
                return User.create({
                    username: req.body.username,
                    password: req.body.password
                })
            }
        })
        .then(user => {
            res.statusCode = 200;
            res.setHeader('Content-Type', 'application/json');
            res.json({ status: 'Registration Successful!', user: user });
        }, err => next(err))
        .catch(err => next(err));
});*/

router.post('/signup', function (req, res, next) {
    // username and password will be provided as a JSON string inside the body of the incoming request
    // the body would have been already parsed by the body-parser
    // register method is provided by passport-local-mongoose
    // passport-local-mongoose will turn password into hash and salt automatically
    User.register(new User({username: req.body.username}), req.body.password, (err, user) => {
            if (err) {
                res.statusCode = 500;
                res.setHeader('Content-Type', 'application/json');
                res.json({ err: err });
            }
            else {
                if (req.body.firstname) {
                    user.firstname = req.body.firstname
                }
                if (req.body.lastname) {
                    user.lastname = req.body.lastname
                }
                user.save((err, user) => {
                    if (err) {
                        res.statusCode = 500;
                        res.setHeader('Content-Type', 'application/json');
                        res.json({ err: err });
                        return;
                    }
                    // authenticating the just signed up user to make sure registration was successful
                    passport.authenticate('local')(req, res, () => {
                        res.statusCode = 200;
                        res.setHeader('Content-Type', 'application/json');
                        res.json({ success: true, status: 'Registration Successful!' });
                    })
                });
            }
        })
});

/*router.post('/login', (req, res, next) => {
    // if the user has not yet authenticated himself
    if (!req.session.user) {
        var authHeader = req.headers.authorization;
        // if client did not include the username and password in the authentication header, so we need to challenge out client to supply this info
        if (!authHeader) {
            var err = new Error('You are not authenticated!');
            res.setHeader('WWW-Authenticate', 'Basic');
            err.status  = 401;
            return next(err); // go to the error handler now
        }

        // convert base64 encoded value to a string (extracting username and password) which gives us username and password
        // joined together with a colon then we split that string, auth will be an array containing username and password
        // var auth = new Buffer(authHeader.split(' ')[1], 'base64').toString().split(':');
        // to deal with some security issues in new version of nodejs, above line should be written as below
        var auth = new Buffer.from(authHeader.split(' ')[1], 'base64').toString().split(':');

        var username = auth[0];
        var password = auth[1];

        User.findOne({ username: username })
            .then(user => {
                if (user === null) {
                    var err = new Error('User ' + username + ' does not exists');
                    err.status  = 403;
                    return next(err); // go to the error handler now
                }
                else if (user.password !== password) {
                    var err = new Error('Your password is incorrect');
                    err.status  = 403;
                    return next(err); // go to the error handler now
                }
                else if (user.username === username && user.password === password) {
                    req.session.user = "authenticated";
                    res.statusCode = 200;
                    res.setHeader('Content-Type', 'text/plain');
                    res.end('You are authenticated');
                }
            })
            .catch(err => next(err));
    }
    else {
        // else the user is already logged in
        res.statusCode = 200;
        res.setHeader('Content-Type', 'text/plain');
        res.end('You are already authenticated');
    }
});*/

// passport.authenticate will verify the user, send back an error to client if occurs,
// add user property to request msg if successful
router.post('/login', passport.authenticate('local'), (req, res) => {
    // generate a token and send it back to client if login is successful
    // client will include the token in every subsequent request
    const token = authenticate.getToken({ _id: req.user._id });
    res.statusCode = 200;
    res.setHeader('Content-Type', 'application/json');
    res.json({ success: true, token: token, status: 'You sre successfully logged in!' });
});

router.get('/logout', (req, res, next) => {
    if (req.session) {
        // the session is destroyed and info is removed from server side
        req.session.destroy();
        // asking client to remove the cookie, the cookie name is session-id
        res.clearCookie('session-id');
        res.redirect('/');
    }
    else {
        var err = new Error('You are not logged in!');
        err.status = 403;
        next(err);
    }
});

module.exports = router;