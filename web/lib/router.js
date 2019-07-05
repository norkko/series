'use strict';
/**
 *
 */

const express = require('express');
const router = express.Router();

const csrf = require('csurf');
const path = require('path');
const bodyParser = require('body-parser');

const csrfProtection = csrf({
  cookie: true
});

const parseForm = bodyParser.urlencoded({
  extended: false
});

const home = require('./home/home');
const auth = require('./auth/auth');
const library = require('./library/library');

/**
 *  
 */
const authenticated = (req, res, next) => {
  if (req.session.auth) {
    return next();
  }

  res.sendFile(path.join(__dirname, '../public/', '404.html'));
}

/**
 *  
 */
router.route('/')
  .get(csrfProtection, home.home)

/**
 *  
 */
router.route('/browse')
  .get(csrfProtection, home.getBrowse)

/**
 *  
 */
router.route('/browse/:id')
  .get(csrfProtection, home.getBrowseId)
  .post(parseForm, csrfProtection, home.postBrowseId)

/**
 *  
 */
router.route('/register')
  .get(csrfProtection, auth.getRegister)
  .post(parseForm, csrfProtection, auth.postRegister)

/**
 *  
 */
router.route('/login')
  .get(csrfProtection, auth.getLogin)
  .post(parseForm, csrfProtection, auth.postLogin)

/**
 *  
 */
router.route('/logout')
  .get(auth.getLogout)

/**
 *  
 */
router.route('/library')
  .get(authenticated, csrfProtection, library.getLibrary)

/**
 *  
 */
router.route('/library/:id')
  .get(authenticated, csrfProtection, library.getLibraryId)
  .post(authenticated, parseForm, csrfProtection, library.postLibraryId)

module.exports = router;