'use strict';

const express = require('express');
const cookieParser = require('cookie-parser');
const bodyParser = require('body-parser');
const session = require('express-session');
const helmet = require('helmet');
const csrf = require('csurf');
const path = require('path');
const port = 8080;

const app = express();

const csrfProtection = csrf({
  cookie: true
});

const parseForm = bodyParser.urlencoded({
  extended: false
});

app.use(express.static(path.join(__dirname, 'public')));
app.set('views', path.join(__dirname, 'views/pages'));
app.set('view engine', 'ejs');

app.use(helmet());
app.use(cookieParser());
app.use(session({
  key: 'cookie',
  secret: 'secret', // process.env.COOKIE_SECRET
  resave: false,
  saveUninitialized: false,
  cookie: {
    secure: false,
    maxAge: 120000000,
    sameSite: 'strict'
  }
}));

app.use('/', require('./lib/router'));

app.use('*', (req, res) => {
  res.redirect('/');
});

app.listen(port, () => {
  console.log('Running on localhost:8080');
});
