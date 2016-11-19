
var mongoose = require('mongoose');
var Schema = mongoose.Schema;

/**
 * Function to define the model of an User.
 */
module.exports = function() {
    var picSchema = new Schema({
        id: {type: String, required:true, unique: true},
        url: {type: String, required:true}
    });

    mongoose.model('Pic', picSchema, 'pics');
};


