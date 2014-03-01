
#Requires
require("./model/db")
express = require("express")
routes = require("./routes")
user = require("./routes/user")
http = require("http")
path = require("path")
app = express()

# all environments
app.set "port", process.env.PORT or 3000
app.set "views", path.join(__dirname, "views")
app.set "view engine", "jade"
app.use express.favicon()
app.use express.logger("dev")
app.use express.json()
app.use express.urlencoded()
app.use express.methodOverride()
app.use app.router
app.use express.static(path.join(__dirname, "public"))

# development only
app.use express.errorHandler()  if "development" is app.get("env")
app.get "/", routes.index
app.get "/users", user.list
app.get "/execution/latest", routes.getLatestExecution
app.get "/execution/:id", routes.getExecution
app.get "/remove/all", routes.removeAlleExecutions
app.get "/remove/:id", routes.removeExecution
http.createServer(app).listen app.get("port"), ->
  console.log "Express server listening on port " + app.get("port")
  return

