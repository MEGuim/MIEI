var express = require('express');
var router = express.Router();
var Evento = require('../../controllers/evento')

router.get('/', function(req, res, next) {
  Evento.listar()
      .then( dados => res.jsonp(dados) )
      .catch( erro => res.status(500).send('Erro na listagem' + erro) )
});

router.get('/:id', function(req, res, next) {
  Evento.consultar(req.params.id)
      .then( dados => res.jsonp(dados) )
      .catch( erro => res.status(500).send('Erro na consulta' + erro) )
});

router.get('/tipo/:t', function(req, res, next) {
  Evento.listarTipo(req.params.t)
      .then( dados => res.jsonp(dados) )
      .catch( erro => res.status(500).send('Erro na listagem por tipo' + erro) )
});

router.get('/data/:d', function(req, res, next) {
  Evento.listarData(req.params.d)
      .then( dados => res.jsonp(dados) )
      .catch( erro => res.status(500).send('Erro na listagem por data' + erro) )
});

router.get('/dataex/:d', function(req, res, next) {
  Evento.listarDataExata(req.params.d)
      .then( dados => res.jsonp(dados) )
      .catch( erro => res.status(500).send('Erro na listagem por data exata' + erro) )
});

router.post('/', function(req, res, next) {
  Evento.inserir(req.body)
      .then( dados => res.jsonp(dados) )
      .catch( erro => res.status(500).send('Erro na inserção' + erro) )
});

module.exports = router;

