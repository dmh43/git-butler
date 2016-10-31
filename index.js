var _ = require('lodash')
var express = require('express');
var request = require('requestretry');
var app = express();



commitToWatch = function() {
  return 'https://api.github.com/repos/dmh43/git-butler/commits/a8a7c90186888412b955b797a63268e6bb90378f/status'
}

commitToWatchUrl = function() {
  return 'https://api.github.com/repos/dmh43/special-train/commits/e60a9dd7579fa692312213b97cc9136ac3b85f64/status';
};

//setInterval(function() {
  request({
    url: commitToWatchUrl(),
    headers: {
      'User-Agent': 'git-butler'
    }
  }, function(err, resp, body) {
    if (!err && resp.statusCode == 200) {
      if (JSON.parse(body).state === 'success') {
        console.log('Ready to merge!');
      }
    }
  });
//}, 1000);

app.post('/repos/:repoOwner/:repoName/:commit/:action', function (req, res) {
  var repoOwner, repoName, commit, action;
  {repoOwner, repoName, commit, action} = _.pick(req.params, ['repoOwner', 'repoName', 'commit', 'action'])
  res.send(`Attempting to merge #{repoName}`)
  console.log('asd')
  res.send(JSON.stringify(res.body));
});

app.listen(3000, function () {
  console.log('Listening on port 3000');
});
