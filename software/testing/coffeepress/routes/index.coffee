
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
        res.render "index", title: "Soundgates Debugger", executions: executions
        return
    return

exports.getExecutionList = (req, res) ->
    query = Execution.find()
    query.select '-components'
    query.sort({ timestamp: -1})
    query.exec ( err, executions ) ->
        return if err
        console.dir executions
        for execution in executions
            # Preparing Data
            execution.formattedTime = getFormattedTime(execution.timestamp)
        console.dir executions
        res.json executions
        return
    return

exports.getComponentList = (req, res) ->
    timestamp = req.params.id
    console.log "Info: Getting Components with timestamp: " + timestamp
    query = Execution.find()
    query.where('timestamp').equals(timestamp)
    query.select('components.uid')
    query.exec ( err, component ) ->
        if err
            console.log err
        else
            res.json component[0].components
        return
    return

exports.getExecution = (req, res) ->
    timestamp = req.params.id
    console.log "Info: Getting Execution with timestamp: " + timestamp
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

exports.removeExecution = (req, res) ->
    ts = req.params.id
    Execution.findOnce({ timestamp: ts }).remove().exec( err, result ) ->
        if err
            console.log err
        return
    return

exports.removeAllExecutions = (req, res) ->
    query = Execution.find()
    query.remove()
    query.exec ( err ) ->
        res.send "Every execution has been deleted"
        return
    return

exports.generateData = (req, res) ->
    execution_arr = []
    data_1 = {
            timestamp: 1392916791
            components: [
                {
                    uid: "sine#0"
                    type: "SW"
                    control_ports: [ { name: 'Control 1', values: [440, 300] } ]
                    input_samples: [ ]
                    output_samples: [ { name: 'Output_Port', values: createSineSamples 0, 500 } ]
                    execution_times: [500, 450, 700]
                },
                {
                    uid: "cosine#0"
                    type: "SW"
                    control_ports: [ { name: 'Control 1', values: [440, 300] } ]
                    input_samples: [ { name: 'Input_Port', values: createSineSamples 0, 500 } ]
                    output_samples: [ { name: 'Output_Port', values: createCosineSamples 0, 500 } ]
                    execution_times: [500, 450, 700]
                },
                {
                    uid: "saw#0"
                    type: "HW"
                    control_ports: [ { name: 'Control 1', values: [440, 300] } ]
                    input_samples: [ ]
                    output_samples: [ { name: 'Output_Port', values: createSawtoothSamples 0, 500 } ]
                    execution_times: [500, 450, 700]
                }
            ]
       }
    data_2 = {
            timestamp: (Math.round(new Date().getTime() / 1000))
            components: [
                {
                    uid: "cosine#0"
                    type: "SW"
                    control_ports: [ { name: 'Control 1', values: [440, 300] } ]
                    input_samples: [ { name: 'Input_Port', values: createSineSamples 0, 10 } ]
                    output_samples: [ { name: 'Output_Port', values: createCosineSamples 0, 10 } ]
                    execution_times: [500, 450, 700]
                }
            ]
       }
    execution_arr.push data_1
    execution_arr.push data_2

    for exec in execution_arr      
        execution = new Execution(exec)
        execution.save ( err ) ->
            console.log "Info: Created testdata"
            return
    exports.getExecutionList(req, res)

createSineSamples = ( begin, end ) ->
    data = []
    for i in [ begin...end ]
        data.push Math.sin(i)
    return data

createCosineSamples = ( begin, end ) ->
    data = []
    for i in [ begin...end ]
        data.push Math.cos(i)
    return data

createSawtoothSamples = ( begin, end, max_value = 100 ) ->
    data = []
    for i in [ begin...end ]
        data.push i % max_value
    return data