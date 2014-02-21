###
This file is responsible for configuring MongoDB and our schemas.
###

mongoose = require("mongoose")
Schema = mongoose.Schema

# Create Schema
executionSchema = new Schema(
  timestamp: Number,
  components: [{
    uid: String,
    type: { type: String },
    samples: [Number],
    execution_times: [Number]
  }]
)

# Create model out of our schema
# At runtime it serves as a constructor to build instances
mongoose.model 'Execution', executionSchema

# Connect to the DB
mongoose.connect "mongodb://localhost:27017/soundgates"

#db = mongoose.connection
#db.on 'error', console.error
