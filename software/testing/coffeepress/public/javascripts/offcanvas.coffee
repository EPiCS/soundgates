# + ---------------------- +
# |   Doc Starting         |
# + ---------------------- +

# Method is executed as soon document becomes ready
readyFn = (jQuery) ->
  console.log "Ready."

  initializeDocument()
  initializeButtons()

  return

initializeDocument = () ->
  # Resize the Navigation Sidebar ( Bootstrap Bug )
  $("[data-clampedwidth]").each ->
    elem = $(this)
    parentPanel = elem.data("clampedwidth")
    console.dir parentPanel
    resizeFn = ->
      sideBarNavWidth = $(parentPanel).width()
      elem.css "width", sideBarNavWidth
      console.log "Setting width to: " + sideBarNavWidth
      return
    resizeFn()
    $(window).resize resizeFn
    return
  $("#component_average_execution").parent().parent().css("margin-left", "0px")


  # Initialize Navigation Sidebar with every available execution
  getExecutionList().done(initExecutionNavigation)
  # Get the latest execution and draw it
  getLastExecution().done(expand)

# Initialize Navigation Sidebar with every available execution
initExecutionNavigation = ( executions ) ->
  nav = $("#nav_executions").empty()
  $('<li class="nav-header">').appendTo(nav).text("Executions")
  for exec in executions
    exec.formattedTime = getFormattedTime exec.timestamp
    li = $('<li>').appendTo(nav)
    $('<a href="#">').appendTo(li).text(exec.formattedTime).fadeIn()
  # $("#list-executions").children(".li").each (index, element) =>
  #     # formValues = (elem.value for elem in $('.input'))
  #     value = $(element).attr("value")
  #     return

# Adds every used componend of the selected Execution to the sidebar
initComponentNavigation = ( components ) ->
  nav = $("#nav_components").empty()
  $('<li class="nav-header">').appendTo(nav).text("Components")
  for c in components
    li = $('<li>').appendTo(nav)
    el = $('<a href="#">').appendTo(li).text(c.uid).fadeIn()
    li.click ->
      target = '#' + __replaceRaute $(this).children('a').text()
      console.log "Scrolling to " + target
      $("html, body").animate
        scrollTop: $(target).position().top
        , "slow"

initializeButtons = () ->
  # Add methods to buttons
  $("#refresh_test").click ->
    $("html, body").animate
        scrollTop: $('home').position().top
        , "slow"
    return
  $("#refresh_test").click(getExecutionList)
  $("#generate_test").click(generateTestdata)
  $("#remove_test").click(removeEveryExecution)
  return



# / ----------------------------------------------------------------------
# Execution expander
# / ----------------------------------------------------------------------

expand = (execution) ->
# / ----------------------------------------------------------------------
# Preprocessing

  getComponentList(execution.timestamp).done(initComponentNavigation)

# / ----------------------------------------------------------------------
# Define expanding methods before calling

  expand_addDate = ( execution ) ->
    date = __getJsDate execution.timestamp
    _getHour = ( d ) ->
      return d.getHours() + ":" + d.getMinutes()
    _getDate = ( d ) ->
      monthNames = [ "January", "February", "March", "April", "May", "June",
      "July", "August", "September", "October", "November", "December" ]
      return d.getDate() + '/' +  monthNames[d.getMonth()] + '/' + d.getFullYear()
    div = $('#execution_date')
    $('<h1 class="text-right"/>').text(_getHour date).css("font-weight","Bold").appendTo(div)
    $('<p class="text-right"/>').text(_getDate date).appendTo(div)
    return

  expand_addComponentCount = ( execution ) ->
    count = execution.components.length
    div = $('#component_count')
    $('<h1/>').text('#' + count).css("font-weight","Bold").appendTo(div)
    $('<p/>').text('have been found').appendTo(div)
    return

  expand_addImplementationDistribution = ( execution ) ->  
    # DONUT Chart
    nv.addGraph ->
      pie = nv.models.pieChart()
        .x (d) -> d.label
        .y (d) -> d.value
        .showLabels(false)
        .labelThreshold(.05)
        .labelType("percent")
        .donut(true)
        .donutRatio(0.5) #Configure how big you want the donut hole size to be.
        .color(['steelblue','lightgreen'])

      data = __calcTypeImplementationDistribution execution.components
      div = '#component_implementations'
      d3.select(div).append('svg')
          .datum(data)
          .transition().duration(350)
          .call(pie);
      return pie
    return

  expand_addAverageExuction = () ->
    # Adding AVG execution time
    # BAR Chart
    nv.addGraph ->
      bar = nv.models.discreteBarChart()
        .x (d) -> d.label
        .y (d) -> d.value
        .staggerLabels(true)
        .tooltips(false)
        .showValues(true)
        .transitionDuration(350)
      div = '#component_average_execution'
      data = __calcAverageExecutionTimeList execution.components
      d3.select(div).append('svg')
          .attr('height',200)
          .datum(data)
          .transition().duration(350)
          .call(bar);
      return bar
    return

# / ----------------------------------------------------------------------
# Call expanding methods

  expand_addDate execution
  expand_addComponentCount execution
  expand_addImplementationDistribution execution
  expand_addAverageExuction execution

# / ----------------------------------------------------------------------
# Draw graphs
  for c in execution.components
    expandComponent(c)

  return

expandComponent = (component) ->
  # Create Component Header
  row = $('<div class="span12"/>').attr("id", __replaceRaute(component.uid)).css("margin-left","0px").appendTo('#execution')
  card = $('<div class="card"/>').appendTo(row)
  title = $('<h2 class="card-heading"/>').appendTo(card).text( 'UID: ' + component.uid )
  body = $('<div class="card-body"/>').appendTo(card)
  typ = $('<p/>').appendTo(body).text('Type: ' + component.type)
  avg = $('<p/>').appendTo(body).text('Average Execution time: ' + __calcAverageExecutionTime component.execution_times )
  $('<div/>').appendTo(body).attr('id', __replaceRaute(component.uid) + "_graphic" )
  # Create diagram
  #d3.select('#execution').append('svg') 
  __createDiagram(component)
  return

__calcTypeImplementationDistribution = ( components ) ->
  result = []
  sw_components = 0
  for c in components
    sw_components += 1 if c.type is 'SW' or c.type is 'sw' or c.type is 'Sw'
  #sw_percentage = (components.length / sw_components) if sw_components is not 0
  sw_percentage = if sw_components > 0 then ( sw_components * 100 / components.length) else 0
  hw_percentage = 100 - sw_percentage

  sw = 
    label: "SW"
    value: sw_percentage
  result.push sw
  hw =
    label: "HW"
    value: hw_percentage
  result.push hw

  return result

__calcAverageExecutionTime = (execution_times) ->
  return "No data available" if execution_times.length == 0
  result = 0
  for time in execution_times
    result = result + time
  return result / execution_times.length

__calcAverageExecutionTimeList = ( components ) ->
  result = [ { key: "Average Execution Time", values:[] }]
  for c in components
    data = 
      label: __replaceRaute c.uid
      value: __calcAverageExecutionTime c.execution_times
    result[0].values.push data
  return result

# ## ---- NVD3

__createDiagram = (component) ->
  console.log "Info: Creating compnent diagram for " + component.uid
  # Prepare data
  draw_samples = __prepareSamples component

  nv.addGraph ->
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
    selector = '#' + selector + '_graphic'
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

getComponentList = ( timestamp ) ->
  return $.ajax({
    url : '/componentlist/' + timestamp,
    type : "GET"
  })

getExecution = (timestamp) ->
  if isInt timestamp
    return $.ajax({
      url : '/execution/' + timestamp,
      type : "GET"
    })

getExecutionList = () ->
  return $.ajax({
      url : '/execution/list',
      type : "GET"
    })

# Debugging methods
generateTestdata = () ->
  return $.ajax({
    url : '/generate',
    type : "GET"
  })

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


# __createDiagram = (component) ->
#   console.log "Info: Creating compnent diagram for " + component.uid
#   # Prepare data
#   dataAscolumns = __prepareSamples component
#   if dataAscolumns.length > 0
#     selector = __replaceRaute(component.uid)
#     selector = '#' + selector
#     datarr = { columns: dataAscolumns }

#     chart = c3.generate({
#       bindto : selector,
#       data   : datarr
#       zoom   : { enabled: true} }) 
#   return

# __prepareSamples = (component) ->
#   columns = []
#   input_length = component.input_samples.length
#   for port, i in component.input_samples
#       c = []
#       c.push 'Input_' + i 
#       for sample in component.input_samples[i].values
#         c.push sample
#       columns.push c

#   for port, i in component.output_samples
#       c = []
#       c.push 'Output_' + i
#       for sample in component.output_samples[i].values
#         c.push sample
#       columns.push c

#   return columns