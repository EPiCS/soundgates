
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
	return  datum + '.' + month + ' ' + hours + ':' + minutes + ':' + seconds;

exports.index = (req, res) ->
	Execution.find (err, executions, count) ->
		for execution in executions
			console.log "STARASRAAS"
			execution.formattedTime = getFormattedTime(execution.timestamp)
		res.render "index", title: "Stachursk.", executions: executions
		console.log "HI"
		for element in executions[0].components
			console.dir element
		console.log "STOP"
		return
	return