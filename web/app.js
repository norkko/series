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

const csrfProtection = csrf({ cookie: true });
const parseForm = bodyParser.urlencoded({ extended: false });

app.use(express.static(path.join(__dirname, 'public')));
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

/* --------- REDIS ---------- */

// use redis later

const redis = require('redis');
const client = redis.createClient();
const { promisify } = require('util');

const getAsync = promisify(client.get).bind(client);
const setAsync = promisify(client.set).bind(client);
const keysAsync = promisify(client.keys).bind(client);

client.on('connect', function() {
	console.log('Redis client connected');
});

client.on('error', function (err) {
	console.log('Something went wrong ' + err);
});


const _ = require('lodash');

/* --------- ------- ---------- */


/* --------- GENERAL ---------- */


// is auth
const auth = (req, res, next) => {
  if (req.session.auth) {
    return next();
  }

  res.sendFile(path.join(__dirname, 'public', '404.html'));
}

app.get('/', csrfProtection, async (req, res) => {
	try {
		console.log(req.session.auth);
		await setAsync('key', 'data');
		console.log(await getAsync('key'));
		res.render('home.ejs', { title: 'Home', csrfToken: req.csrfToken(), auth: req.session.auth });
	} catch (err) {
		console.log(err);
	}
});


/* --------- ---- ---------- */

/* --------- AUTH ---------- */


const fetch = require('node-fetch');

const url = 'http://localhost:8081'; // not containerized


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
	if (req.session.auth) delete req.session.auth;
	res.redirect('/');
});

/* --------- ------- ---------- */

/* --------- LIBRARY ---------- */

app.get('/library', auth, csrfProtection, async (req, res) => {
  try {
		const seriesIdsRequest = fetch(`${url}/series`, {
			headers: {
		    'Accept': 'application/json',
		    'Content-Type': 'application/json',
		    'Authorization': req.session.auth
		  },
		  method: 'GET'
		}).then(res => res.json());

		const seriesIdsResponse = await seriesIdsRequest;
		seriesIdsResponse.forEach(item => {
		  delete item.id;
		});

		let seriesDataResponse;
		if (seriesIdsResponse.length > 0) {
			const seriesDataRequest = fetch(`${url}/api/series`, {
			  headers: {
		      'Accept': 'application/json',
		      'Content-Type': 'application/json'
		    },
		    body: JSON.stringify(seriesIdsResponse),
		    method: 'POST'
		  }).then(res => res.json());

		  seriesDataResponse = await seriesDataRequest;
		  req.session.series = seriesDataResponse;
		}
	  
	  res.render('library.ejs', { title: 'Library', csrfToken: req.csrfToken(), auth: req.session.auth, series: seriesDataResponse });
	} catch (err) {
		console.log(err);
	}
});

app.get('/library/:id', auth, csrfProtection, async (req, res) => {
  const data = await fetch(`${url}/episodes/${req.params.id}`, // series id hardcoded
  	{ headers: { 'Authorization': req.session.auth }})
    .then(res => res.json());

	let series = req.session.series, found;
	for (let i = 0; i < series.length; i++) {
		if (series[i].id == req.params.id) {
			found = series[i];
			break;
		}
	}

	let l = series[0].number_of_seasons,
	  arr = new Array(++l);

  for (let i = 1; i < arr.length; i++) {
  	arr[i] = [];
  	for (let j = 0; j < data.length; j++) {
  		if (data[j].season === i) {
  		  arr[i].push(data[j].episode);
  		}
  	}
  }

	res.render('series.ejs', { title: 'Library', csrfToken: req.csrfToken(), auth: req.session.auth, series: found, watched: arr });
});

app.post('/library/:id', auth, parseForm, csrfProtection, async (req, res) => {
	let previous = req.body.previous.split(',');
	let update = req.body.episodes;
	if (update === undefined)	update = [];
	else if (typeof update === 'string') update = Array.from(update);

	let remove = _.differenceWith(previous, update, _.isEqual);
	update = update.filter(item => !previous.includes(item)); 
	
	// Adds episodes to db.
	if (update && update.length > 0) {
	    for (let i = 0; i < update.length; i++) {
	      let body = {
	        'series': req.body.id,
	        'season': req.body.season,
	        'episode': update[i]
	      };

	      try {
	        const request = await fetch(`${url}/episodes`, {
	            headers: {
	              'Accept': 'application/json',
	              'Content-Type': 'application/json',
	              'Authorization': req.session.auth
	            },
	            body: JSON.stringify(body),
	            method: 'POST'
	          });
	      }	catch (err) {
	        console.log(err);
	      }
	    } 
	}

  res.redirect(req.originalUrl);
});

app.post('/library/series/:id', auth, parseForm, csrfProtection, async (req, res) => {
  try {
    const data = await fetch(`${url}/series`,
    	{ headers: {
	  	  'Accept': 'application/json',
	  	  'Content-Type': 'application/json',
    		'Authorization': req.session.auth
    	},
    	method: 'PUT',
    	body: JSON.stringify({
    		'series': req.params.id
    	})
    });

    console.log(data.status);  
	
	  res.status(204).send();
  } catch (err) {
		console.log(err);
	}

});

/* --------- ------- ---------- */

app.listen(port, () => {
	console.log('Running on localhost:8080');
});
