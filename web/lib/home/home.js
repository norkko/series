'use strict';
/**
 *
 */
const fetch = require('node-fetch');
const url = 'http://localhost:8081';
const _ = require('lodash');

exports.home = async (req, res, next) => {
  res.render('home.ejs', {
    title: 'Home',
    csrfToken: req.csrfToken(),
    auth: req.session.auth
  });
}

exports.help = async (req, res, next) => {
  res.render('help.ejs', {
    title: 'Help',
    csrfToken: req.csrfToken(),
    auth: req.session.auth
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

      res.render('search.ejs', {
        title: 'Browse',
        csrfToken: req.csrfToken(),
        auth: req.session.auth,
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

      res.render('browse.ejs', {
        title: 'Browse',
        csrfToken: req.csrfToken(),
        auth: req.session.auth,
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

    res.render('browse.ejs', {
      title: 'Browse',
      csrfToken: req.csrfToken(),
      auth: req.session.auth,
      popular: null,
      results: results
    });
  } catch (err) {
    console.log(err);
  }
}

exports.postBrowseId = async (req, res, next) => {
  try {
    const data = await fetch(`${url}/series`, {
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

    console.log(data.status);  
  
    res.redirect('browse');
  } catch (err) {
    console.log(err);
  }
}
