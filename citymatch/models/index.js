var models = ['./city','./image', './user'];

/**
 * Function to initialize all the models, based on an Array.
 */
exports.initialize = function() {
    models.forEach(function(model){
        require(model)();
    });
};

var mongoose = require('mongoose');

mongoose.connect('mongodb://localhost/citymatch');
var db = mongoose.connection;

db.once('open', function () {
    console.log('Connected to Mongo!');
});

module.exports = db;

