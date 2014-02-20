mongoose = require("mongoose")
executionSchema = new mongoose.Schema(
  timestamp: String
  components: [
    uid: String
    type: String
    samples: []
    execution_times: []
  ]
)
mongoose.model "Execution", executionSchema
mongoose.connect "mongodb://localhost:27017/soundgates"
