var http = require('http')
var url = require('url')
var fs = require('fs')

http.createServer((req, res) => {
    res.writeHead(200, {'Content-Type' : 'text/html'})
    var purl = url.parse(req.url, true),
        q = purl.query;

    if (purl.pathname === '/obra')
    {
        if (q.id != undefined)
        {
            path = './website/html/obra' + q.id + '.html'
            fs.exists(path, (exists) => {
                if (exists){
                    goToPage(res, './website/html/obra' + q.id + '.html')
                } else {
                    goToPage(res)
                }
            })
        } else {
            goToPage(res)
        }
    }
}).listen(4002, ()=> {
    console.log("Servidor à escuta na porta 4002...")
})

function goToPage(res, path)
{
    if (path === undefined) path = './website/index.html'
    fs.readFile(path, (erro, dados) => {
        res.writeHead(200, {'Content-Type' : 'text/html'})
        if (!erro)
            res.write(dados)
        else
            res.write(erro)
        res.end()
    })
}