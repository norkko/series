'use strict';

const express = require('express');
const cookieParser = require('cookie-parser');
const bodyParser = require('body-parser');
const session = require('express-session');
const helmet = require('helmet');
const csrf = require('csurf');
const path = require('path');
const port = 8080;

const csrfProtection = csrf({ cookie: true })
const parseForm = bodyParser.urlencoded({ extended: false })

const app = express();

app.set('views', path.join(__dirname, 'views/pages'));
app.set('view engine', 'ejs');

app.use(helmet());
app.use(cookieParser());
app.use(session({
  key: 'cookie',
  secret: 'secret',
  resave: false,
  saveUninitialized: false,
  cookie: {
  	secure: false,
    maxAge: 120000000,
    sameSite: 'strict'
  }
}));

app.get('/', csrfProtection, (req, res) => {
	res.render('home.ejs', { title: 'Home', csrfToken: req.csrfToken() });
});


const fetch = require('node-fetch');

app.get('/register', csrfProtection, (req, res) => {
	console.log(req.session.auth);
	res.render('register.ejs', { title: 'Register', csrfToken: req.csrfToken() });
});

app.post('/register', parseForm, csrfProtection, async (req, res) => {
	const json = {
		'username': req.body.username,
		'password': req.body.password
	}

	const request = fetch('http://localhost:8081/register', {
		method: 'POST',
		headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        },
		body: JSON.stringify(json)
	});

	const response = await request;

	if (response.status == 200) {
		console.log('success..');
	}

	res.redirect('/');

});

app.get('/login', csrfProtection, (req, res) => {
	console.log(req.session.auth);
	res.render('login.ejs', { title: 'Login', csrfToken: req.csrfToken() });
});

app.post('/', parseForm, csrfProtection, async (req, res) => {
	const json = {
		'username': req.body.username,
		'password': req.body.password
	}
	
	const request = fetch('http://localhost:8081/login', {
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
});

app.listen(port);
