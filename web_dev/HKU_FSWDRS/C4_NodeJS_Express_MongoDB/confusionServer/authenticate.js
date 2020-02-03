// this file stores authentication strategies
var passport = require('passport');
var LocalStrategy = require('passport-local').Strategy;
var User = require('./models/user');
const JwtStrategy = require('passport-jwt').Strategy;
const ExtractJwt = require('passport-jwt').ExtractJwt;
const jwt = require('jsonwebtoken');
const config = require('./config');

// a verify function (supported by passport-local-mongoose) should be supplied to new LocalStrategy as a parameter which would verify the user
// will be called when the username and password will be extracted by password from incoming request
// username and password should be supplied in the body of incoming request in the form of a json string (due to body-parser)
// username and password will be added in body of request by body-parser, that username and password will be used as parameters to the verify function
// that will be supplied to local strategy
// the passport-local-mongoose plugin itself adds the verify function called as user.authenticate to user schema and model
exports.local = passport.use(new LocalStrategy(User.authenticate()));

// need to serialize info bcoz we are using sessions, serializeUser and deserializeUser function on User schema and model are provided by
// passport-local-mongoose plugin
passport.serializeUser(User.serializeUser());
passport.deserializeUser(User.deserializeUser());

// user is a json object, getToke is a function which will create the token and give it to us
exports.getToken = user => {
    // user is the payload, valid only for 3600 secs / 1 hour
    return jwt.sign(user, config.secretKey, { expiresIn: 3600 });
};

const opts = {};
// jwt token will be extracted from auth header
opts.jwtFromRequest = ExtractJwt.fromAuthHeaderAsBearerToken();
// secret key to be used in the strategy to sign in
opts.secretOrKey = config.secretKey;

// 2nd arg is verify function
// callback will pass back info to passport, that info will be used to load things onto the request msg
exports.jwtPassport = passport.use(new JwtStrategy(opts, (jwt_payload, callback) => {
    console.log("JWT Payload: ", jwt_payload);
    User.findOne({ _id: jwt_payload._id }, (err, user) => {
        if (err) {
            // 2nd param is optional, the user, false means user doesn't exist
            // 3rd param is optional, any other info
            return callback(err, false);
        } else if (user) {
            return callback(null, user);
        } else {
            return callback(null, false);
        }
    })
}));

// use the token sent by client in the req auth headers and verify the user using the jwt strategy
exports.verifyUser = passport.authenticate('jwt', { session: false });
exports.verifyAdmin = (req, res, next) => {
    if (req.user) {
        if (req.user.admin) {
            next();
        } else {
            const err = new Error('You are not authorized to perform this operation!');
            err.status = 403;
            return next(err);
        }
    } else {
        const err = new Error('Pl login to perform this operation!');
        err.status = 403;
        return next(err);
    }
};
