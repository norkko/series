'use strict';
/**
 *
 */
const fetch = require('node-fetch');
const url = 'http://localhost:8081';
const _ = require('lodash');

const dm = require('dundermifflin');

exports.home = async (req, res, next) => {
  console.log(req.session.user)
  res.render('home/home.ejs', {
    title: 'Home',
    csrfToken: req.csrfToken(),
    user: req.session.user
  });
}

exports.help = async (req, res, next) => {
  res.render('home/help.ejs', {
    title: 'Help',
    csrfToken: req.csrfToken(),
    user: req.session.user
  });
}

exports.getBrowse = async (req, res, next) => {
  try {
    if (req.query.series) {
      // query
      console.log(req.query.series);
      let search = await fetch(`${url}/api/search?query=${req.query.series}`, { // spaces needa be %20
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        },
        method: 'GET'
      }).then(res => res.json());

      res.render('library/search.ejs', {
        title: 'Browse',
        csrfToken: req.csrfToken(),
        user: req.session.user,
        query: req.query.series,
        results: search.results
      });
    } else {
      // browse
      let popular = await fetch(`${url}/api/series/popular`, {
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        },
        method: 'GET'
      }).then(res => res.json());

      res.render('library/browse.ejs', {
        title: 'Browse',
        csrfToken: req.csrfToken(),
        user: req.session.user,
        popular: popular.results
      });
    }
  } catch (err) {
    console.log(err);
  }
}

exports.getBrowseId = async (req, res, next) => {
  try {
    const results = await fetch(`${url}/api/series/${req.params.id}`, {
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      method: 'GET'
    }).then(res => res.json());

    res.render('library/browse.ejs', {
      title: 'Browse',
      csrfToken: req.csrfToken(),
      user: req.session.user,
      popular: null,
      results: results
    });
  } catch (err) {
    console.log(err);
  }
}

exports.postBrowseId = async (req, res, next) => {
  try {
    const response = await fetch(`${url}/series`, {
      method: 'PUT',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': req.session.auth
      },
      body: JSON.stringify({
        'series': req.params.id
      })
    });

    console.log(response);

    if (response.status === 200) {
      // ok
      console.log('added');

    } else {
      // shit hit the fan
    }

    res.redirect(req.originalUrl);
  } catch (err) {
    console.log(err);
  }
}
