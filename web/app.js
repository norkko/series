'use strict'

const express = require('express')
const app = express()

const path = require('path')
const session = require('express-session')
const helmet = require('helmet')
const csrf = require('csurf')
const port = 8080

app.use(helmet())
app.set('views', path.join(__dirname, 'view'));
app.set('view engine', 'ejs');

const sessionOptions = {
  name: 'token',
  secret: 'ao5o8GD0Wdf984jDktj44ktDLlj545i9',
  resave: false,
  saveUninitialized: false,
  cookie: {
    maxAge: 1000 * 60 * 60 * 24,
    sameSite: 'strict'
  }
};

app.get('/', (req, res) => {
	res.send('hi');
});

app.listen(port);
