var Evento = require('../models/evento')

// Lista de eventos
module.exports.listar = () => {
    return Evento
        .find()
        .sort({data: -1})
        .exec()
}

// Lista de eventos por Tipo
module.exports.listarTipo = tipo => {
    return Evento
        .find({tipo: tipo})
        .sort({data: -1})
        .exec()
}

// Lista de eventos com Data maior que uma dada
module.exports.listarData = data => {
    return Evento
        .find({data: {$gte: data}})
        .sort({data: -1})
        .exec()
}

// Lista de eventos por Data
module.exports.listarDataExata = data => {
    return Evento
        .find({data: data})
        .sort({data: -1})
        .exec()
}

// Devolve a informação do evento por id
module.exports.consultar = id => {
    return Evento
        .findOne({_id: id})
        .exec()
}


// Insere um evento na agenda
/* module.exports.inserir = evento => {
    var novo = new Evento(evento)
    return new Promise( (fulfill, reject) => {
        novo.save( erro => {
            if (erro) reject({erro: 'Erro no envio à BD'})
            else fulfill({ok: 'Registo inserido na BD'})
        })
    })
} */

module.exports.inserir = evento => {
    return Evento.create(evento)
}