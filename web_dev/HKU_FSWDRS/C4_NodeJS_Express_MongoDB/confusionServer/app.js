var createError = require('http-errors');
var express = require('express');
var path = require('path');
var cookieParser = require('cookie-parser');
var logger = require('morgan');
var session = require('express-session');


var indexRouter = require('./routes/index');
var dishRouter = require('./routes/dishRouter');
var promoRouter = require('./routes/promoRouter');
var leaderRouter = require('./routes/leaderRouter');

const mongoose = require('mongoose');

const url = 'mongodb://localhost:27017/conFusion';
const connect = mongoose.connect(url);

connect.then((db) => {
    console.log('Connected correctly to the server');
}, err => console.log(err) );

var app = express();

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'jade');

app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
// secret key will be used by cookie-parser to encrypt the info and sign the cookie that will be sent to the client
app.use(cookieParser('12345-67890-09876-54321'));

// authorization middleware
function auth(req, res, next) {
    console.log(req.signedCookies);

    // if signed cookies or user property in signed cookies doesn't exist
    if (!req.signedCookies.user) {
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

        if (username === "admin" && password === "password") {
            // set up the cookie for client if authorization is successful
            // parameters for cookie function is cookie name, value and options, below user field in cookie is set to admin
            //signed: true means cookie-parser will ensure that this cookie will be signed and setup
            res.cookie('user', 'admin', { signed: true });
            // from this auth middleware, let the request pass on to the next set of middle wares
            next();
        }
        else {
            var err = new Error('You are not authenticated!');
            res.setHeader('WWW-Authenticate', 'Basic');
            err.status  = 401;
            return next(err); // go to the error handler now
        }
    }
    // else signed cookie already exists
    else {
        if (req.signedCookies.user === "admin") next();
        // else cookie is not valid
        else {
            var err = new Error('You are not authenticated!');
            err.status  = 401;
            return next(err); // go to the error handler now
        }
    }
}

// user must be authenticated before going through any other middleware
app.use(auth);

app.use(express.static(path.join(__dirname, 'public')));
app.use('/', indexRouter);
app.use('/dishes', dishRouter);
app.use('/promotions', promoRouter);
app.use('/leaders', leaderRouter);

// catch 404 and forward to error handler
app.use(function(req, res, next) {
    next(createError(404));
});

// error handler
app.use(function(err, req, res, next) {
    // set locals, only providing error in development
    res.locals.message = err.message;
    res.locals.error = req.app.get('env') === 'development' ? err : {};

    // render the error page
    res.status(err.status || 500);
    res.render('error');
});

module.exports = app;