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
  nv.addGraph ->
    console.dir component
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
    selector = __replaceRaute(component.uid)
    selector = '#' + selector
    console.log "DEBUG:"
    data = __prepareSamples (component.input_samples[0].values)
    console.dir data
    d3.select(selector).append('svg').datum(data).call(chart)
    #Update the chart when window resizes.
    #nv.utils.windowResize(chart.update);
    #nv.utils.windowResize ->
    #  chart.update()
    #  return 
    return chart

__prepareSamples = (samples) ->
  data = []
  for sample, i in samples
      data.push {x:i, y: sample} 
  return [{ values: data, key: 'Wave' }]
 
#   //Data is represented as an array of {x,y} pairs.
#   for (var i = 0; i < 100; i++) {
#     sin.push({x: i, y: Math.sin(i/10)});
#     sin2.push({x: i, y: Math.sin(i/10) *0.25 + 0.5});
#     cos.push({x: i, y: .5 * Math.cos(i/10)});
#   }
 
#   //Line chart data should be sent as an array of series objects.
#   return [
#     {
#       values: sin,      //values - represents the array of {x,y} data points
#       key: 'Sine Wave', //key  - the name of the series.
#       color: '#ff7f0e'  //color - optional: choose your own line color.
#     },
#     {
#       values: cos,
#       key: 'Cosine Wave',
#       color: '#2ca02c'
#     },
#     {
#       values: sin2,
#       key: 'Another sine wave',
#       color: '#7777ff',
#       area: true      //area - set to true if you want this line to turn into a filled area chart.
#     }
#   ];
# }
  
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
