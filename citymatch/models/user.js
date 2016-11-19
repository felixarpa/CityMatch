
var mongoose = require('mongoose');
var Schema = mongoose.Schema;

/**
 * Function to define the model of an User.
 */
module.exports = function() {
    var userSchema = new Schema({
        id: {type: String, required:true, unique: true},
        email: {type: String, required:true, unique: true},
        airport: {type: String, required:true},
        likedPics: [{type: obj, ref: 'Pic'}],
    });

    mongoose.model('User', userSchema, 'users');
};


