'use strict';
/**
 *
 */

const fetch = require('node-fetch');
const url = 'http://localhost:8081';

exports.getRegister = async (req, res, next) => {
  res.render('register.ejs', {
    title: 'Register',
    csrfToken: req.csrfToken(),
    auth: req.session.auth
  });
}

exports.postRegister = async (req, res, next) => {
  const json = {
    'username': req.body.username,
    'password': req.body.password
  }

  const request = fetch(`${url}/register`, {
    method: 'POST',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(json)
  });

  const response = await request;
  res.redirect('/');

  // do post login
}

exports.getLogin = async (req, res, next) => {
  res.render('login.ejs', {
    title: 'Login',
    csrfToken: req.csrfToken(),
    auth: req.session.auth
  });
}

exports.postLogin = async (req, res, next) => {
  const json = {
    'username': req.body.username,
    'password': req.body.password
  }

  const request = fetch(`${url}/login`, {
    method: 'POST',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(json)
  });

  const response = await request;
  if (response.status === 200) {
    req.session.auth = response.headers.get('authorization');
  }

  res.redirect('/');
}

exports.getLogout = async (req, res, next) => {
  if (req.session.auth) {
    delete req.session.auth;
  }

  res.redirect('/');
}

const authenticated = (req, res, next) => {
  if (req.session.auth) {
    return next();
  }

  res.sendFile(path.join(__dirname, 'public', '404.html'));
}