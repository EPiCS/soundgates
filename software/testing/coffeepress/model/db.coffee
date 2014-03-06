###
This file is responsible for configuring MongoDB and our schemas.
###

mongoose = require("mongoose")
Schema = mongoose.Schema

# Create Schema
executionSchema = new Schema(
  timestamp: {type: Number},
  components: [{
    uid: { type: String },
    type: { type: String },
    control_ports: [ { name: String, values: [type: Number] } ]
    input_samples: [ { name: String, values: [type: Number] } ]
    output_samples: [ { name: String, values: [type: Number] } ]
    execution_times: [type: Number]
  }]
)

# Create model out of our schema
# At runtime it serves as a constructor to build instances
mongoose.model 'Execution', executionSchema

# Connect to the DB
mongoose.connect "mongodb://localhost:27017/soundgates"

