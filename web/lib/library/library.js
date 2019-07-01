'use strict';
/**
 *
 */
const fetch = require('node-fetch');
const url = 'http://localhost:8081';
const _ = require('lodash');

exports.getLibrary = async (req, res, next) => {
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
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(seriesIdsResponse)
      }).then(res => res.json());

      seriesDataResponse = await seriesDataRequest;
      req.session.series = seriesDataResponse;
    }

    res.render('library/library.ejs', {
      title: 'Library',
      csrfToken: req.csrfToken(),
      user: req.session.user,
      series: seriesDataResponse
    });
  } catch (err) {
    console.log(err);
  }
}

exports.postLibrary = async (req, res, next) => {
  // todo
}

exports.getLibraryId = async (req, res, next) => {
  const data = await fetch(`${url}/episodes/${req.params.id}`, {
    headers: {
      'Authorization': req.session.auth
    }
  }).then(res => res.json());

  let series = req.session.series,
    found;
  for (let i = 0; i < series.length; i++) {
    if (series[i].id == req.params.id) {
      found = series[i];
      break;
    }
  }

  let l = series[0].number_of_seasons,
    arr = new Array(++l);

  for (let i = 0; i < arr.length; i++) {
    arr[i] = [];
    for (let j = 0; j < data.length; j++) {
      if (data[j].season === i) {
        arr[i].push(data[j].episode);
      }
    }
  }

  res.render('library/series.ejs', {
    title: 'Library',
    csrfToken: req.csrfToken(),
    user: req.session.user,
    series: found,
    watched: arr
  });
}

exports.postLibraryId = async (req, res, next) => {
  let previous = req.body.previous.split(',');
  let update = req.body.episodes;
  if (update === undefined) update = [];
  else if (typeof update === 'string') update = Array.from(update);

  let remove = _.differenceWith(previous, update, _.isEqual);
  update = update.filter(item => !previous.includes(item));


  // Adds episodes
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
      } catch (err) {
        console.log(err);
      }
    }
  }

  // Remove eposides
  if (remove && remove.length > 0) {
    // todo
  }

  res.redirect(req.originalUrl);
}
