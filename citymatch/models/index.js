var mongoose = require('mongoose');

mongoose.connect('mongodb://localhost/hacktrack');
var db = mongoose.connection;

db.once('open', function () {
    console.log('Connected to Mongo!');
});

module.exports = db;