var createError = require('http-errors')
var express = require('express');
var path = require('path');
var cookieParser = require('cookie-parser');
var logger = require('morgan');
var bodyParser = require('body-parser')
var mongoose = require('mongoose')

var eventosAPIRouter = require('./routes/api/eventos')
var eventosRouter = require('./routes/eventos')

// Ligação à BD
mongoose.connect('mongodb://127.0.0.1:27017/agenda', {useNewUrlParser: true})
  .then(() => console.log('Mongo ready: ' + mongoose.connection.readyState))
  .catch(() => console.log('Erro na conexão à BD'))


var app = express();

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'pug');

app.use(logger('tiny'));
app.use(bodyParser.json())
app.use(bodyParser.urlencoded())
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));

app.use('/api/eventos', eventosAPIRouter)
app.use('/eventos', eventosRouter)

// catch 404 and forward to error handler
app.use(function(req, res, next) {
  next(createError(404));
});

// error handler
app.use(function(err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};

  // render the error page
  res.status(err.status || 500);
  res.render('error');
});

module.exports = app;
