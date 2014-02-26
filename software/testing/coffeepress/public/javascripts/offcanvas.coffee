# + ---------------------- +
# |   Doc Starting         |
# + ---------------------- +

readyFn = (jQuery) ->
  console.log "Ready."
  $("[data-toggle=offcanvas]").click ->
    console.log "Click on button "
    $(".row-offcanvas").toggleClass "active"
    return
  # Get the latest execution (expand is defined in expand.coffee)
  getLastExecution().done(expand)

  return

# + ---------------------- +
# |   Creating Functions   |
# + ---------------------- +

# Gets an execution and expands it inside of the document
expand = (execution) ->
  # Preprocessing
  execution.formattedTime = getFormattedTime execution.timestamp
  # ####################################
  # -   Header
  # ####################################
  row_1 = $('<div class="row"></div>').appendTo('#execution')
  $('<div class="col-md-4"/>').appendTo(row_1).text("Execution date:")
  $('<div class="col-md-8"/>').appendTo(row_1).text(execution.formattedTime)

  row_2 = $('<div class="row"></div>').appendTo('#execution')
  $('<div class="col-md-4"/>').appendTo(row_2).text("Turnaround time:")
  $('<div class="col-md-8"/>').appendTo(row_2).text(execution.turnaround)

  row_3 = $('<div class="row"></div>').appendTo('#execution')
  $('<div class="col-md-4"/>').appendTo(row_3).text("Number of Components:")
  $('<div class="col-md-8"/>').appendTo(row_3).text(execution.components.length)

  row_3 = $('<br>').appendTo('#execution')

  # ####################################
  # -   Graphs
  # ####################################

  for c in execution.components
    expandComponent(c)

  return

expandComponent = (component) ->
  # Create Component Header
  panel = $('<div class="panel panel-primary"></div>').appendTo('#execution')
  $('<div class="panel-heading"/>').appendTo(panel).text('UID: ' + component.uid) 
  $('<div class="panel-body"/>').appendTo(panel).text('Type: ' + component.type)
  $('<div/>').appendTo(panel).attr('id', __replaceRaute(component.uid) )
  # Create diagram
  #d3.select('#execution').append('svg') 
  __createDiagram(component)
  return

__createDiagram = (component) ->
  console.log "Info: Creating compnent diagram for " + component.uid
  console.dir component
  chart = nv.addGraph ->
    chart = nv.models.lineChart()
            .margin(left: 20)
            .useInteractiveGuideline(true)
            .transitionDuration(350)
            .showLegend(true)
            .showYAxis(true)
            .showXAxis(true) #Show the x-axis
    #Chart x-axis settings
    chart.xAxis.axisLabel("Samples").tickFormat d3.format(",r")
    #Chart y-axis settings
    chart.yAxis.axisLabel("Amplitude").tickFormat d3.format(".02f")
    
    #Select the <svg> element you want to render the chart in.   
    #Populate the <svg> element with chart data...
    #d3.select("#chart svg").datum(myData).call chart #Finally, render the chart!
    
    #Update the chart when window resizes.
    nv.utils.windowResize ->
      chart.update()
      return 
    return chart
  selector = __replaceRaute(component.uid)
  selector = '#' + selector
  console.dir component.samples
  d3.select(selector).append('svg').datum(component.samples).call chart #Finally, render the chart
  chart.update()

__replaceRaute = (text) ->
  return text.replace /#/g, '_'

# + ---------------------- +
# |   AJAX Functions       |
# + ---------------------- +

getLastExecution = () ->
  return $.ajax({
    url : '/execution/latest',
    type : "GET"
    })

getExecution = (timestamp) ->
  if isInt timestamp
    return $.ajax({
      url : '/execution/' + timestamp,
      type : "GET"
    })

# Deprecated
getLastExecutionDeprecated = () ->
  $.get "execution/latest", (data, status) ->
    console.log status
    console.dir data
    return

  return

getExecutionDeprecated = (timestamp) ->
  if isInt(timestamp)
    $.get "execution/" + timestamp, (data, status) ->
      console.log status
      console.dir data
      return
  else
    console.log timestamp + " is not a number."
  return

# + ---------------------- +
# |   Helper Funtctions    |
# + ---------------------- +

print = (data) ->
  console.dir data

isInt = (number) ->
  return typeof n is 'number' and n % 1 == 0
    
getFormattedTime = (x) ->
    date = __getJsDate(x)
    year = date.getFullYear()
    month = date.getMonth()
    datum = date.getDate()
    hours = date.getHours()
    minutes = date.getMinutes()
    seconds = date.getSeconds()
    return  datum + '.' + month + '.' + year + ' ' + hours + ':' + minutes + ':' + seconds;

__getJsDate = (x) ->
    date = new Date( x*1000 )
    return date

$(document).ready readyFn
