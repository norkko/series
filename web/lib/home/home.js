'use strict';
/**
 *
 */

exports.home = async (req, res, next) => {
  res.render('home.ejs', { title: 'Home', csrfToken: req.csrfToken(), auth: req.session.auth });
}