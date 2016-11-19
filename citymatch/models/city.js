
var mongoose = require('mongoose');
var Schema = mongoose.Schema;

/**
 * Function to define the model of an User.
 */
module.exports = function() {
    var citySchema = new Schema({
        id: {type: String, required:true, unique: true},
        name: {type: String, required:true, unique: true}
    });

    mongoose.model('City', citySchema, 'cities');
};


