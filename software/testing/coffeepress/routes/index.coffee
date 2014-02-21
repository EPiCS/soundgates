
#
# * GET home page.
# 

#executiondata = require('../model/executions')
mongoose = require('mongoose')
Execution = mongoose.model 'Execution'

exports.index = (req, res) ->
	Execution.find (err, executions, count) ->
		res.render "index", title: "Stachursk.", executions: executions
		console.log "HI"
		console.dir(executions)
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
