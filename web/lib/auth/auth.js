'use strict';
/**
 *
 */

const fetch = require('node-fetch');
const url = 'http://localhost:8081';

exports.getRegister = async (req, res, next) => {
  res.render('auth/register.ejs', {
    title: 'Register',
    csrfToken: req.csrfToken(),
    user: req.session.user
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
  res.render('auth/login.ejs', {
    title: 'Login',
    csrfToken: req.csrfToken(),
    user: req.session.user
  });
}

exports.postLogin = async (req, res, next) => {
  const json = {
    'username': req.body.username,
    'password': req.body.password
  }

  try {
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
  
      const current = await fetch(`${url}/current`, {
        method: 'GET',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
          'Authorization': req.session.auth
        }
      }).then(res => res.json());
  
      console.log(current)
      req.session.user = current;
    }
  
    res.redirect('/');
  } catch (err) {
    console.log(err);
  }
}

exports.getLogout = async (req, res, next) => {
  if (req.session.auth || req.session.auth) {
    delete req.session.auth;
    delete req.session.user;
  }

  res.redirect('/');
}

const authenticated = (req, res, next) => {
  if (req.session.auth) {
    return next();
  }

  res.sendFile(path.join(__dirname, 'public', '404.html'));
}
