# + ---------------------- +
# |   Doc Starting         |
# + ---------------------- +

readyFn = (jQuery) ->
  console.log "Ready."
  $("[data-toggle=offcanvas]").click ->
    console.log "Click on button "
    $(".row-offcanvas").toggleClass "active"
    return
  # Get the latest execution
  getLastExecution().done(expand)

  # TODO: Add click methods for list group items
  initializeDocument()

  return

initializeDocument = () ->
  $("#list-group").children(".list-group-item").each (index, element) =>
      value = $(element).attr("value")
      return
  # Add methods to buttons
  $("#generate_test").click(generateTestdata)
  $("#remove_test").click(removeEveryExecution)
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

  console.log execution.components
  for c in execution.components
    expandComponent(c)

  return

expandComponent = (component) ->
  # Create Component Header
  panel = $('<div class="panel panel-primary"></div>').appendTo('#execution')
  $('<div class="panel-heading"/>').appendTo(panel).text('UID: ' + component.uid) 
  typ = $('<div class="panel-body"/>').appendTo(panel).text('Type: ' + component.type)
  $('<p/>').appendTo(typ).text('Average Execution time: ' + __calcAverageExecutionTime component.execution_times )
  $('<div/>').appendTo(panel).attr('id', __replaceRaute(component.uid) )
  # Create diagram
  #d3.select('#execution').append('svg') 
  __createDiagram(component)
  return

__createDiagram = (component) ->
  console.log "Info: Creating compnent diagram for " + component.uid
  # Prepare data
  draw_samples = __prepareSamples component

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
    chart.yAxis.axisLabel("Amplitude").tickFormat d3.format("5.00f")
    
    #Select the <svg> element you want to render the chart in.   
    #Populate the <svg> element with chart data...
    #d3.select("#chart svg").datum(myData).call chart #Finally, render the chart!
    selector = __replaceRaute(component.uid)
    selector = '#' + selector
    d3.select(selector).append('svg').datum(draw_samples).call(chart)
    #Update the chart when window resizes.
    nv.utils.windowResize(chart.update);
    nv.utils.windowResize ->
      chart.update()
      return 
    return chart

__prepareSamples = (component) ->
  data = []
  console.dir component.input_samples
  input_length = component.input_samples.length
  for port, i in component.input_samples
      data.push { key: 'Input port ' + i, values: [] } 
      for sample, j in component.input_samples[i].values
        data[i].values.push {x:j, y: sample} 

  for port, i in component.output_samples
      data.push { key: 'Output port ' + i, values: [] } 
      for sample, j in component.output_samples[i].values
        data[input_length + i].values.push {x:j, y: sample} 

  return data

__calcAverageExecutionTime = (execution_times) ->
  return "No data available" if execution_times.length == 0
  result = 0
  for time in execution_times
    result = result + time
  return result / execution_times.length
   
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

# Debugging methods
generateTestdata = () ->
  return $.ajax({
    url : '/generate'
    type : "GET"
  })

# TODO: add refresh method
removeExecution = (timestamp) ->
  if isInt timestamp
    return $.ajax({
      url : '/remove/' + timestamp,
      type : "GET"
    })

removeEveryExecution = () ->
  # TODO: insert warning
    return $.ajax({
      url : '/remove/all',
      type : "GET"
    })


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
