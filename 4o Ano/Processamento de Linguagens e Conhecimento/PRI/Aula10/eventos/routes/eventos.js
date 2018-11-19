var express = require('express');
var router = express.Router();
var axios = require('axios')

router.get('/', function(req, res, next) {
    axios.get('http://localhost:4010/api/eventos/')
        .then( eventos => res.render('index', {eventos: eventos.data}) )
        .catch( erro => {
            console.log('Erro na listagem de eventos: ' + erro)
            res.render('index')
        })
});

router.get('/:id', function(req, res, next) {
    axios.get('http://localhost:4010/api/eventos/' + req.params.id)
        .then( evento => res.render('evento', {evento: evento.data}) )
        .catch( erro => {
            console.log('Erro na consulta do evento: ' + erro)
            res.render('error', {error: erro, message: 'My bad...'})
        })
});


/*router.get('/tipo/:t', function(req, res, next) {
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
}); */

router.post('/', (req, res) => {
    axios.post('http://localhost:4010/api/eventos', req.body)
        .then( () => res.redirect('http://localhost:4010/eventos'))
        .catch( erro => {
            console.log('Erro na inserção do evento: ' + erro)
            res.render('error', {error: erro, message: 'My bad insertion...'})
        })
})

module.exports = router;

