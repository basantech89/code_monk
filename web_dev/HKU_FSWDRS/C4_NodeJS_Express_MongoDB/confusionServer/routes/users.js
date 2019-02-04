var express = require('express');
const bodyParser = require('body-parser');
var User = require('../models/user');
var passport = require('passport');
var authenticate = require('../authenticate');
const cors = require('./cors');

var router = express.Router();
router.use(bodyParser.json());

/* GET users listing. */
router.options('*', cors.corsWithOptions, (req, res) => {res.sendStatus(200); });
router.get('/', cors.corsWithOptions, authenticate.verifyUser, authenticate.verifyAdmin, function(req, res, next) {
    User.find({})
        .then((users) => {
            res.statusCode = 200;
            res.setHeader('Content-Type', 'application/json');
            res.json(users);
        }, (err) => next(err))
        .catch((err) => next(err));
});

// endpoint --> /users/signup
router.post('/signup', cors.corsWithOptions, function (req, res, next) {
  /*User.findOne({username: req.body.username})
      .then((user) => {
        if (user != null) {
          let err = new Error('User ' + req.body.username + ' already exists!');
          err.status = 403;
          next(err);
        }
        else {
          return User.create({
            username: req.body.username,
            password: req.body.password
          })
        }
      })
      .then((user) => {
        res.statusCode = 200;
        res.setHeader('Content-Type', 'application/json');
        res.json({status: 'Registration Successful!', user: user})
      }, (err) => next(err))
      .catch((err) => next(err));
*/
    //  register method on User schema is supplied by passport local mongoose plugin
    User.register(new User({username: req.body.username}), req.body.password, (err, user) => {
          if (err) {
              res.statusCode = 500;
              res.setHeader('Content-Type', 'application/json');
              res.json({err: err});
          }
          else {
              if (req.body.firstname)
                  user.firstname = req.body.firstname;
              if (req.body.lastname)
                  user.lastname = req.body.lastname;

              user.save((err, user) => {
                  if (err) {
                      res.statusCode = 500;
                      res.setHeader('Content-Type', 'application/json');
                      res.json({err: err});
                      return;
                  }

                  passport.authenticate('local')(req, res, () => {
                      res.statusCode = 200;
                      res.setHeader('Content-Type', 'application/json');
                      res.json({success: true, status: 'Registration Successful!'})
                  })
              });
          }
        })
});

// username and password will be in the body of the post msg unlike earlier where we included this in authorization headers
// if authentication is successful then next function that follows(req, res) will be executed
// if any error comes in authentication then authenticate function will automatically send an error to the client
// on successful authentication, passport authenticate local will automatically adds the user property to the request msg
router.post('/login', cors.corsWithOptions, (req, res, next) => {
    // err will be returned when an error occurs during th authentication process
    // info contain information if the user doesn't exists or username or password is incorrect
    //req, res and next are the parameters to passport.authenticate
    passport.authenticate('local', (err, user, info) => {
        if (err)
            return next(err);
        // not an error in the authentication but the user couldn't be found (null)
        if (!user) {
            res.statusCode = 401; // unauthorized
            res.setHeader('Content-Type', 'application/json');
            res.json({success: false, status: 'Login Unsuccessful!', err: info})
        }

        req.logIn(user, (err) => {
            if (err) {
                res.statusCode = 401; // unauthorized
                res.setHeader('Content-Type', 'application/json');
                res.json({success: false, status: 'Login Unsuccessful!', err: 'Could not log in user!'})
            }

            // user is authenticated, providing a json web token to the client instead of using sessions
            let token = authenticate.getToken({_id: req.user._id});
            res.statusCode = 200;
            res.setHeader('Content-Type', 'application/json');
            res.json({success: true, token: token, status: 'Login Successful!'})
        });
    }) (req, res, next);
});

/*
router.post('/login', (req, res, next) => {
  if (!req.session.user) {
    let authHeader = req.headers.authorization;
    if (!authHeader) {
      let err = new Error('You are not authenticated');

      res.setHeader('WWW-Authenticate', 'Basic');
      err.status = 401;
      return next(err);
    }

    let auth = new Buffer.from(authHeader.split(' ')[1], 'base64').toString().split(':');
    let username = auth[0];
    let password = auth[1];

    User.findOne({username: username})
        .then((user) => {
          if (user === null) {
            let err = new Error('User ' + username + ' does not exist!');
            err.status = 403;
            return next(err);
          }

          else if (user.password !== password) {
            let err = new Error('Your password is incorrect!');
            err.status = 403;
            return next(err);
          }

          else if (user.username === username && user.password === password) {
            //above else if check isn't necessary
            req.session.user = 'authenticated';
            res.statusCode = 200;
            res.setHeader('Content-Type', 'text/plain');
            res.end('You are authenticated');
          }
        })
        .catch((err) => next(err));
  }

  else {
    res.statusCode = 200;
    res.setHeader('Content-Type', 'text/plain');
    res.end('You are already authenticated');
  }
});
*/

router.get('/logout', cors.corsWithOptions, (req, res) => {
  if (req.session) {
    req.session.destroy();
    res.clearCookie('session-id');
    res.redirect('/');
  }

  else {
    let err = new Error('You are not logged in');
    err.status = 403;
    next(err);
  }

});

// user-agent(web/mobile app) returns the fb access token to express server (client app)
// express server then authorize the user using passport and generate the json web token for the user
router.get('/facebook/token', passport.authenticate('facebook-token'), (req, res) => {
//    if we are here, then it means that facebook.strategy is successful
//    and it has loaded the user property on req object
    if (req.user) {
        var token = authenticate.getToken({_id: req.user._id});
        res.statusCode = 200;
        res.setHeader('Content-Type', 'application/json');
        res.json({success: true, token: token, status: 'You are successfully logged in!'})
    }
});

router.get('/checkJWTToken', cors.corsWithOptions, (req, res) => {
    // for json web token authentication
    passport.authenticate('jwt', {session: false}, (err, user, info) => {
        if (err)
            return next(err);

        // if user isn't found from the json web token, means json web token has expired
        if (!user) {
            res.statusCode = 401; // unauthorized
            res.setHeader('Content-Type', 'application/json');
            return res.json({status: 'JWT invalid', success: false, err: info})
        }

        else {
            res.statusCode = 200;
            res.setHeader('Content-Type', 'application/json');
            return res.json({status: 'JWT valid', success: true, user: user})
        }
    }) (req, res)
});

module.exports = router;
