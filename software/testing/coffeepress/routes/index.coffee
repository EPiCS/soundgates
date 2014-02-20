
#
# * GET home page.
# 

executiondata = require('../model/executions')

exports.index = (req, res) ->
	console.log "HI"
	group = "*"
	executiondata.executionList group, (err, executionlist) ->
  		res.render "index",
    	title: "Wat"
    	executions: executionlist
  	return
return
