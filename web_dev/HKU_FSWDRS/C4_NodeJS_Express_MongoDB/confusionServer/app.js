var createError = require('http-errors');
var express = require('express');
var path = require('path');
var cookieParser = require('cookie-parser');
var logger = require('morgan');
var session = require('express-session');
var FileStore = require('session-file-store')(session);
var passport = require('passport');
var authenticate = require('./authenticate');
var config = require('./config');

var indexRouter = require('./routes/index');
var usersRouter = require('./routes/users');
var dishRouter = require('./routes/dishRouter');
var promoRouter = require('./routes/promoRouter');
var leaderRouter = require('./routes/leaderRouter');
var uploadRouter = require('./routes/uploadRouter');
var favoritesRouter = require('./routes/favoritesRouter');
var commentRouter = require('./routes/commentRouter');

const mongoose = require('mongoose');

const url = config.mongoUrl;
const connect = mongoose.connect(url);

connect.then((db) => {
    console.log('Connect correctly to the server');
}, (err) => { console.log(err); });

var app = express();

// redirecting http traffic to https(port 3000 to 3443)
app.all('*', (req, res, next) => {
    //if this is a secure request, then it will carry a secure flag set to true
    if (req.secure)
        return next();

    else
        // secPort property to app is set in bin/www
        res.redirect(307, 'https://' + req.hostname + ':' + app.get('secPort') + req.url);
});

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'jade');

app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
// secret key can be anything, to encrypt the info and sign the cookie
// app.use(cookieParser('12345-67890-09876-54321'));

/*
// using json web tokens instead of sessions, so removing it
// session middleware, using sessions instead of cookieParser
app.use(session({
    name: 'session-id',
    secret: '12345-67890-09876-54321',
    saveUninitialized: false,
    resave: false,
    store: new FileStore()
}));
*/

app.use(passport.initialize());
// passport authenticate local will automatically adds the user property to the request msg (users route line no 58)
// passport session will automatically serialize that user info and store it in the session
// when a client request with a session cookie comes in then this will automatically load req.user on incoming request
// app.use(passport.session());

// an incoming user can access the / and /users without being authenticated
app.use('/', indexRouter);
app.use('/users', usersRouter);

/*
function auth(req, res, next) {
    if (!req.user) {
        let err = new Error('You are not authenticated');
        err.status = 403;
        return next(err);
    }
    // if req.user is present, that means passport has done authentication and req.user is loaded on the request msg
    else next();

/!*
    // console.log(req.headers);
    // console.log(req.signedCookies);
    // the session middleware adds session property to request object
    console.log(req.session);
    /!*!// setup cookie on client side from server so the client won't have to explicitly keep sending basic authentication info
    if (!req.signedCookies.user) {*!/
    if (!req.session.user) {
        let err = new Error('You are not authenticated');
        err.status = 403; // Forbidden
        // err.status = 401; // unauthorized status code
        return next(err); // skip over all the below code and go to the error handler
        /!*let authHeader = req.headers.authorization;
        if (!authHeader) {
          let err = new Error('You are not authenticated');

          res.setHeader('WWW-Authenticate', 'Basic');
          err.status = 401; // unauthorized status code
          return next(err); // skip over all the below code and go to the error handler
        }

        // below code is only for authenticated users
        // buffer enables us to split a string
        // authHeader example: Basic base64EncodedString
        //colon (:) separates the username:password
        let auth = new Buffer.from(authHeader.split(' ')[1], 'base64').toString().split(':');
        let username = auth[0];
        let password = auth[1];

        if (username === 'admin' && password === 'password') {
          //setup the cookie if user is authenticated in the outgoing server response
          // res.cookie('user', 'admin', { signed: true });
          req.session.user = 'admin';
          next(); // allow client request to pass through the next middleware
        }

        else {
          let err = new Error('You are not authenticated!');
          res.setHeader('WWW-Authenticate', 'Basic');
          err.status = 401;
          return next(err);
        }*!/
    }

    else {
        // if (req.signedCookies.user === 'admin')
        // if (req.session.user === 'admin')
        if (req.session.user === 'authenticated')
            next(); // allow the request to pass through

        else {
            let err = new Error('You are not authenticated!');
            err.status = 403;  // Forbidden
            return next(err);
        }
    }
*!/
}

// don't need authentication on all the routes
app.use(auth);
*/

// everything that comes after from this point, all the middleware that is mounted
// will have to go through the authorization phase before that middleware can be accessed
app.use(express.static(path.join(__dirname, 'public')));

app.use('/dishes', dishRouter);
app.use('/promotions', promoRouter);
app.use('/leaders', leaderRouter);
app.use('/imageUpload', uploadRouter);
app.use('/favorites', favoritesRouter);
app.use('/comments', commentRouter);

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
