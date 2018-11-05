const express = require('express');
const http = require('http');
const formidable = require('formidable');
const pug = require('pug');
const logger = require('morgan');
const fs = require('fs');
const mv = require('mv');

var app = express()

app.use(logger('combined'))

app.all('*', (req, res, next) => {
    if(req.url != '/w3.css') res.writeHead(200, {'Content-Type' : 'text/html; charset=utf-8'})
    next()    
})

app.get('/', (req, res) => {
    var jsonContent = fs.readFileSync('./json/ficheiros.json')
    var ficheiros = JSON.parse(jsonContent)

    res.write(pug.renderFile('layout.pug', {lista : ficheiros}))
    res.end()  
})

app.get('/w3.css', (req, res) => {
    res.writeHead(200, {'Content-Type' : 'text/css'})
    fs.readFile('stylesheet/w3.css', (erro, dados) => {
        if(!erro) res.write(dados)
        else res.write(pug.renderFile('erro.pug'), {erro: erro})
        res.end()
    })
})

app.post('/processaForm', (req, res) => {
    var form = new formidable.IncomingForm()
    form.parse(req, (erro, fields, files) => {
        var fenviado = files.ficheiro.path
        var fnovo = './uploaded/' + files.ficheiro.name

        mv(fenviado, fnovo, (erro) => {
            fields.ficheiro = files.ficheiro.name 
            if(!erro){
                var jsonContent = fs.readFileSync('./json/ficheiros.json')
                var ficheiros = JSON.parse(jsonContent)
                var result = {"nome" : files.ficheiro.name, "descricao" : fields.descricao}
                ficheiros.push(result)
                fs.writeFile('./json/ficheiros.json', JSON.stringify(ficheiros), (err) => {
                    if (err) {
                        res.end(pug.renderFile('erro.pug', {erro : "Erro ao gravar ficheiro recebido." + erro}))
                    }
                })

                res.end(pug.renderFile('layout.pug', {lista : ficheiros, status : "Ficheiro recebido e guardado com sucesso."}))
            } else {
                res.end(pug.renderFile('erro.pug', {erro : "Erro ao gravar ficheiro recebido." + erro}))
            }
        })
    })
})

http.createServer(app).listen(4007)