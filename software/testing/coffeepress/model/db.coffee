###
This file is responsible for configuring MongoDB and our schemas.
###

mongoose = require("mongoose")
Schema = mongoose.Schema

# Create Schema
executionSchema = new Schema(
  timestamp: {type: Number},
  components: [{
    uid: String,
    type: { type: String },
    control_ports: [ { name: String, values: [Number] } ]
    input_samples: [ { name: String, values: [Number] } ]
    output_samples: [ { name: String, values: [Number] } ]
    execution_times: [Number]
  }]
)

# Create model out of our schema
# At runtime it serves as a constructor to build instances
mongoose.model 'Execution', executionSchema

# Connect to the DB
mongoose.connect "mongodb://localhost:27017/soundgates"

