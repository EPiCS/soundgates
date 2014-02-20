mongoose = require("mongoose")
exports.executionList = executionlist = (gname, callback) ->
  Execution = mongoose.model("Execution")
  Execution.find
    timestamp: gname
  , (err, executions) ->
    if err
      console.log err
    else
      console.log executions
      callback "", executions
    return

  return
# end Team.find
# end exports.teamlist
