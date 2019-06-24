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
	console.log(req.session.auth);
	res.render('home.ejs', { title: 'Home', csrfToken: req.csrfToken(), auth: req.session.auth });
});

const fetch = require('node-fetch');
const url = 'http://localhost:8081'; // temp whilst not containerized

app.get('/register', csrfProtection, (req, res) => {
	res.render('register.ejs', { title: 'Register', csrfToken: req.csrfToken(), auth: req.session.auth });
});

app.post('/register', parseForm, csrfProtection, async (req, res) => {
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

});

app.get('/login', csrfProtection, (req, res) => {
	res.render('login.ejs', { title: 'Login', csrfToken: req.csrfToken(), auth: req.session.auth });
});

app.post('/login', parseForm, csrfProtection, async (req, res) => {
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
});

app.get('/logout', (req, res) => {
	delete req.session.auth;
	res.redirect('/');
});

app.get('/library', csrfProtection, async (req, res) => {
	try {
    const request = fetch(`${url}/series`, {
	  	headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': req.session.auth
      },
      method: 'GET'
    }).then(res => res.json());

    const response = await request;
    let arr = [];
    for (let i = 0; i < response.length; i++) {
    	arr.push(response[i].series);
    }
    console.log(arr.join(","));

	  res.render('library.ejs', { title: 'Library', csrfToken: req.csrfToken(), auth: req.session.auth, series: response });
	} catch (err) {
		console.log(err);
	}
});

app.get('*', (req, res) => {
	res.redirect('/');
});

app.listen(port);
