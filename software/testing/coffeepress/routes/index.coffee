
#
# * GET home page.
# 

#executiondata = require('../model/executions')
mongoose = require('mongoose')
util = require('util')

Execution = mongoose.model 'Execution'

getJsDate = (x) ->
    date = new Date( x*1000 )
    return date
    
getFormattedTime = (x) ->
    date = getJsDate(x)
    year = date.getFullYear()
    month = date.getMonth()
    datum = date.getDate()
    hours = date.getHours()
    minutes = date.getMinutes()
    seconds = date.getSeconds()
    return  datum + '.' + month + '.' + year + ' ' + hours + ':' + minutes + ':' + seconds;

exports.index = (req, res) ->
    query = Execution.find()
    query.select '-components'
    query.exec ( err, executions ) ->
        return if err
        for execution in executions
            # Preparing Data
            execution.formattedTime = getFormattedTime(execution.timestamp)
        console.dir executions
        res.render "index", title: "Soundgates Debugger", executions: executions
        return
    return

exports.getExecution = (req, res) ->
    timestamp = req.params.id
    console.log timestamp
    query = Execution.find()
    query.where('timestamp').equals(timestamp)
    query.exec ( err, execution ) ->
        if err
            console.log err
        else
            res.json execution[0]
        return
    return

exports.getLatestExecution = (req, res) ->
    query = Execution.find()
    query.sort({'timestamp':1})
    query.limit(1)
    query.exec ( err, execution ) ->
        if err
            console.log err
        else
            res.json execution[0]
        return
    return
