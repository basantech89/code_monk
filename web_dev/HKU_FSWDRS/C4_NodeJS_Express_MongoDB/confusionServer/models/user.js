var mongoose = require('mongoose');
var Schema = mongoose.Schema;
var passportLocalMongoose = require('passport-local-mongoose');

/*var User = new Schema({
    username: {
        type: String,
        required: true,
        unique: true
    },
    password: {
        type: String,
        required: true,
    },
    admin: {
        type: Boolean,
        default: false
    }
});*/

var User = new Schema({
    // no need for username and password bcoz that will be automatically added by passport-local-mongoose plugin here
    firstname: {
        type: String,
        default: '',
    },
    lastname: {
        type: String,
        default: '',
    },
    admin: {
        type: Boolean,
        default: false
    }
});

// username and hashed storage of password using hash and salt and additional methods on user schema and the model
// will be automatically added by passport-local-mongoose plugin as below
User.plugin(passportLocalMongoose);

module.exports = mongoose.model('User', User);