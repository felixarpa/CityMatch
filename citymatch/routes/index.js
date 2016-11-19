var express = require('express');
var request = require('request');
var config = require('../config');
var router = express.Router();

/* GET home page. */
router.get('/', function (req, res, next) {
    res.json('hello world');
});

router.get('/images/:q', function (req, res, next) {
    var Bing = require('node-bing-api')({accKey: config.msft1});
    Bing.images(req.params.q, {
        top: 15,   // Number of results (max 50)
        //skip: 3    // Skip first 3 result
    }, function (error, res, body) {
        console.log(body);
    });
    res.send('OK');
});

router.get('/airports/:q', function (req, res, next) {
    request({
        url: 'http://partners.api.skyscanner.net/apiservices/autosuggest/v1.0/ES/EUR/en-GB?query=' + req.params.q +
        '&apiKey=' + config.skyscanner,
        headers: {
            'Content-Type': 'application/json'
        }
    }, function (err, skycanner_res, body) {
        if (!err && skycanner_res.statusCode == 200) {
            res.json(JSON.parse(body));
        }
    });
});

router.post('/flights/:origin/:dest', function (req, res, next) {
    request({
        url: 'http://partners.api.skyscanner.net/apiservices/browseroutes/v1.0/ES/EUR/en-GB/' + req.params.origin + '/'
        + req.params.dest + '/' + req.body.dateIda + '/' + req.body.dateVuelta + '?apiKey=' + config.skyscanner,
        headers: {
            'Content-Type': 'application/json'
        }
    }, function (err, skyscanner_res, body) {
        if (!err && skyscanner_res.statusCode == 200) {
            res.json(JSON.parse(body));
        }
    })
});


router.get('/list/', function(req, res, next){
    // getting list of data
});


router.post('/like/', function(req, res, next){
   var postId = req.body.postId;
   var userId = req.body.userId;
   // return match or not
});



module.exports = router;
