
#
# * GET home page.
# 

#executiondata = require('../model/executions')
mongoose = require('mongoose')
util = require('util')


Execution = mongoose.model 'Execution'

exports.index = (req, res) ->
	Execution.find (err, executions, count) ->
		res.render "index", title: "Stachursk.", executions: executions
		console.log "HI"
		#console.log JSON.stringify executions, null, 4
		#console.dir executions[0]
		for element in executions[0].components
			console.dir element
		console.log "STOP"
		return
	return


	#group = "*"
	#executiondata.executionList group, (err, executionlist) ->
  	#	res.render "index",
    #	title: "Wat"
    #	executions: executionlist
  	#return
return
