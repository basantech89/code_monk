const mongoose = require('mongoose');
const Schema = mongoose.Schema;
require('mongoose-currency').loadType(mongoose); // load the currency type to mongoose
const Currency = mongoose.Types.Currency;

const leaderSchema = new Schema({
    name: {
        type: String,
        required: true,
        unique: true
    },
    description: {
        type: String,
        required: true
    },
    image: {
        type: String,
        required: true
    },
    abbr: {
        type: String,
        required: true
    },
    designation: {
        type: String,
        required: true
    },
    featured: {
        type: Boolean,
        default: false
    }
}, {
    timestamps: true // will automatically add created at and updated at timestamps
});

var Leaders = mongoose.model('Leader', leaderSchema);

module.exports = Leaders;