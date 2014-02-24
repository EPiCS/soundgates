
#
# * GET home page.
# 

#executiondata = require('../model/executions')
mongoose = require('mongoose')
util = require('util')

Execution = mongoose.model 'Execution'
ExecutionList = mongoose.model 'ExecutionList'

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
  		console.log executions
  		for execution in executions
  			# Preparing Data
  			console.log "HAHALHADLDAH"
  			execution.formattedTime = getFormattedTime(execution.timestamp)
	    res.render "index", title: "Soundgates Debugger", executions: executions
		return
	return

exports.getExecution = (req, res) ->
    timestamp = req.params.id
    console.log timestamp
    query = Execution.find()
    query.where('timestamp').equals(timestamp)
    query.exec ( err, execution ) ->
    	console.log err if err
    	res.json execution
    	return
    return