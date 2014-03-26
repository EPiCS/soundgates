# + ---------------------- +
# |   Doc Starting         |
# + ---------------------- +

# Method is executed as soon document becomes ready

global_timestamp = 0
global_components = null
global_component_charts = null

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
      return
    resizeFn()
    $(window).resize resizeFn
    return
  
  # Margin hardcoded Bugfix
  $("#component_average_execution").parent().parent().css("margin-left", "0px")

  # Every time the window is scrolled ... 
  $(window).scroll ->
    
    # Check the location of each desired element 
    $(".hideme").each (i) ->
      bottom_of_object = $(this).position().top + $(this).outerHeight()
      bottom_of_window = $(window).scrollTop() + $(window).height()
      top_of_window = $(window).scrollTop() - $(window).height()
      
      # If the object is completely visible in the window, fade it it 
      offset = 150
      if bottom_of_window > bottom_of_object - offset
        $(this).animate
          opacity: "1"
        , 500
      return

    return

  # Initialize Navigation Sidebar with every available execution
  getExecutionList().done(initExecutionNavigation)
  # Get the latest execution and draw it
  getLastExecution().done(expand)

# Initialize Navigation Sidebar with every available execution
initExecutionNavigation = ( executions ) ->
  nav = $("#nav_executions").empty()
  $('<li class="nav-header">').appendTo(nav).text("Executions")
  if executions?
    for exec in executions
      exec.formattedTime = getFormattedTime exec.timestamp
      li = $('<li>').appendTo(nav)
      el = $('<a>').hide().appendTo(li).text(exec.formattedTime).data("timestamp",exec.timestamp).fadeIn("fast")
      el.click ->
        $('html, body').animate({scrollTop:0}, 'slow')
        timestamp = $(this).data("timestamp")
        getExecution(timestamp).done(expand)
        return
  else
    li = $('<li>').appendTo(nav)
    el = $('<a>').hide().appendTo(li).text("No data").fadeIn("fast")


# Adds every used componend of the selected Execution to the sidebar
initComponentNavigation = ( components ) ->
  global_components = components
  nav = $("#nav_components").empty()
  $('<li class="nav-header">').appendTo(nav).text("Components")
  for c in components
    li = $('<li>').appendTo(nav)
    el = $('<a>').hide().appendTo(li).text(c.uid).fadeIn("fast")
    li.click ->
      target = '#' + __replaceRaute $(this).children('a').text()
      console.log "Scrolling to " + target
      $("html, body").animate
        scrollTop: $(target).position().top
        , "slow"

initializeButtons = () ->
  $("#home").click ->
    $('html, body').animate({scrollTop:0}, 'slow')
    return false

  $("#refresh_test").click ->
    getExecutionList().done (executions )->
      initExecutionNavigation executions
  
  $("#generate_test").click ->
    generateTestdata().done ->
      getExecutionList().done (executions )->
        initExecutionNavigation executions

  $("#remove_test").click ->
    removeEveryExecution().done ->
      initExecutionNavigation null
  return



# / ----------------------------------------------------------------------
# Execution expander
# / ----------------------------------------------------------------------

expand = (execution) ->
# / ----------------------------------------------------------------------
# Define expanding methods before calling
  expand_clean = ( ) ->
    console.log "Cleaning."
    $("#execution_date").fadeOut("fast").empty()
    $("#component_count").fadeOut("fast").empty()
    $("#turnaround").fadeOut("fast").empty()
    $("#component_implementations").fadeOut("fast").empty()
    $("#component_average_execution").fadeOut("fast").empty()
    $("#execution").fadeOut("fast").empty()
    # Clean global variables
    global_component_charts = []
    return

  expand_addDate = ( execution ) ->
    date = __getJsDate execution.timestamp
    _getHour = ( d ) ->
      return d.getHours() + ":" + d.getMinutes()
    _getDate = ( d ) ->
      monthNames = [ "January", "February", "March", "April", "May", "June",
      "July", "August", "September", "October", "November", "December" ]
      return d.getDate() + '/' +  monthNames[d.getMonth()] + '/' + d.getFullYear()
    div = $('#execution_date').fadeIn("fast")
    $('<br/>').appendTo(div)
    $('<h1 class="text-right"/>').text(_getHour date).css("font-weight","Bold").appendTo(div)
    $('<p class="text-right"/>').text(_getDate date).appendTo(div)
    return

  expand_addComponentCount = ( execution ) ->
    count = execution.components.length
    div = $('#component_count').fadeIn("fast")
    $('<br/>').appendTo(div)
    $('<h1/>').text('#' + count).css("font-weight","Bold").appendTo(div)
    $('<p/>').text('have been found').appendTo(div)
    return

  expand_addTurnaround = ( execution ) ->
    turn = 0
    for c in execution.components
      turn = turn + __calcAverageExecutionTime c.execution_times
    turn = turn.toFixed 2
    div = $('#turnaround').fadeIn("fast")
    $('<br/>').appendTo(div)
    $('<h1/>').html(turn + ' &micros').css("font-weight","Bold").appendTo(div)
    $('<p/>').text('does it take for one cycle').appendTo(div)
    return

  expand_addImplementationDistribution = ( execution ) ->  
    $("#component_implementations").fadeIn("fast")
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
    $("#component_average_execution").fadeIn("fast")
    # Adding AVG execution time
    # BAR Chart
    nv.addGraph ->
      bar = nv.models.discreteBarChart()
        .x (d) -> d.label
        .y (d) -> d.value
        .staggerLabels(true)
        .tooltips(true)
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
# Preprocessing
  global_timestamp = execution.timestamp
  getComponentList(execution.timestamp).done(initComponentNavigation)

# / ----------------------------------------------------------------------
# Call expanding methods
  expand_clean()
  expand_addDate execution
  expand_addComponentCount execution
  expand_addImplementationDistribution execution
  expand_addTurnaround execution
  expand_addAverageExuction execution

# / ----------------------------------------------------------------------
# Draw graphs
  for c in execution.components
    expandComponent(c)

  return

expandComponent = (component) ->
  # Create Card
  div = $("#execution").fadeIn("fast")
  row = $('<div class="span12"/>').addClass("hideme").attr("id", __replaceRaute(component.uid)).css("margin-left","0px").appendTo(div)
  card = $('<div class="card"/>').appendTo(row)
  title = $('<h2 class="card-heading"/>').appendTo(card).text( 'UID: ' + component.uid )
  # Create Table
  body = $('<div class="card-body"/>').appendTo(card)
  table = $ "<table>"
  table.addClass("table table-condensed table-hover table-striped").appendTo(body)
  # Create Table Body
  tbody = $ "<tbody>"
  tbody.appendTo(table)
  # Add line to Table body
  tr = $ "<tr>"
  tr.appendTo(tbody)
  td = $ "<td>"
  td.appendTo(tr).addClass("span3").text('Implementation type:')
  td = $ "<td>"
  td.appendTo(tr).addClass("span9").text(component.type)
  # Add line to Table Body
  tr = $ "<tr>"
  tr.appendTo(tbody)
  td = $ '<td>'
  td.appendTo(tr).addClass("span3").text('Average execution time:')
  avgtime = __calcAverageExecutionTime component.execution_times
  avgtime = avgtime.toFixed 2
  td = $ '<td>'
  td.appendTo(tr).addClass("span9").html(avgtime + ' &micros')
  # Add information about samples
  __addSampleInformation component,tbody
  # Add diagram
  $('<div/>').appendTo(body).attr('id', __replaceRaute(component.uid) + "_graphic" )
  # Add Brush div
  $('<div/>').appendTo(body).attr('id', __replaceRaute(component.uid) + "_brush" )
  # Create diagram
  if component.input_samples[0]? or component.output_samples[0]?
    __createDiagram(component)
    __createBrush(component)
  return

__addSampleInformation = ( component, tbody ) ->
  for input, i in component.input_samples
    tr = $ "<tr>"
    tr.appendTo(tbody)
    td = $ "<td>"
    title = "Input samples port #" + i + ":"
    samples = input.values.length
    td.appendTo(tr).addClass("span3").text(title)
    td = $ "<td>"
    td.appendTo(tr).addClass("span9").text(samples)
  for output, i in component.output_samples
    tr = $ "<tr>"
    tr.appendTo(tbody)
    td = $ "<td>"
    title = "Output samples port #" + i + ":"
    samples = output.values.length
    td.appendTo(tr).addClass("span3").text(title)
    td = $ "<td>"
    td.appendTo(tr).addClass("span9").text(samples)
  return


__calcTypeImplementationDistribution = ( components ) ->
  result = []
  sw_components = 0
  for c in components
    sw_components += 1 if c.type is 'SW' or c.type is 'sw' or c.type is 'Sw'
  #sw_percentage = (components.length / sw_components) if sw_components is not 0
  sw_percentage = if sw_components > 0 then ( sw_components * 100 / components.length) else 0
  sw_percentage = Math.floor sw_percentage
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
            .margin(left: 90)
            .useInteractiveGuideline(true)
            .transitionDuration(350)
            .showLegend(true)
            .showYAxis(true)
            .showXAxis(true) #Show the x-axis
    #Chart x-axis settings
    chart.xAxis.axisLabel("Samples").tickFormat d3.format(",r")
    #Chart y-axis settings
    chart.yAxis.tickFormat d3.format("+,d")
    
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

    global_chart =
      "uid" : component.uid
      "chart" : chart
    global_component_charts.push global_chart 
    return chart

__createBrush = (component) ->

  # Get corresponding chart
  __getChart = (component) ->
      for c in global_component_charts
        if c.uid = component.uid
          return c.chart
      console.log "Error: Could not find chart: " + component.uid
      return null

  brushed = () ->
    # Get selected samples
    b = if not brush.empty() then brush.extent() else x_scale.domain()
    start = Math.floor b[0]
    end = Math.ceil b[1]
    sample_amount = end - start
    if sample_amount < 1 then return
    # Get chart
    chart = __getChart component
    # Get data from DB
    ob = 
      timestamp : global_timestamp
      uid : component.uid
    $.post("/samples", ob).done (data) ->
      console.log "Ajax Success:"
      data = __prepareSamples data.components[0], start, end
      # Update Chart
      #chart.xDomain(data)
      selector = __replaceRaute(component.uid)
      selector = '#' + selector + '_graphic'
      #$(selector).find("svg").remove()
      d3.select(selector + " svg").datum(data).call(chart)
      __updateGlobalCharts()
      #chart.update()
    return

  # Preprocessing
  selector = __replaceRaute(component.uid)
  selector = '#' + selector + '_brush'
  width = $(selector).parent().css("width")
  width = parseInt(width, 10);
  height = 60

  values = component.output_samples[0].values
  length = values.length
  draw_data = 
        x : (i for e,i in values)
        y : (e for e in values)
  
  x_domain = [0, length]
  y_domain = d3.extent(draw_data.y.map( (d) -> return d))
  
  x_scale = d3.scale.linear().range([0, width]).domain(x_domain).clamp(true)
  y_scale = d3.scale.linear().range([height, 0]).domain(y_domain).clamp(true)
  
  brush = d3.svg.brush()
    .x( x_scale )
    .on( "brushend", brushed );

  line = d3.svg.line()
    .x( (d) -> return x_scale d.x )
    .y( (d) -> return y_scale d.y );

  svg = d3.select(selector).append("svg")
    .attr("width", width)
    .attr("height", height)
#    .append("g")
#    .attr("transform", "translate(" + margin.left + "," + margin.top + ")")

  svg.append("path")
    .datum(draw_data)
    .attr("class", "line")
    .attr("d", line)

  # area = d3.svg.area().interpolate("linear").x((d) ->
  #   console.log x_scale(d.x)
  #   return x_scale(d.x)
  # ).y0(height).y1((d) ->
  #   console.log y_scale(d.y)
  #   return y_scale(d.y)
  # )

  context = svg.append("g")
    .attr("class", "context")
    .attr("width", width)
    .attr("height", height);

  svg.append("g")
      .attr("class", "x brush")
      .call(brush)
    .selectAll("rect")
      .attr("y", -6)
      .attr("height", height);
  return

__prepareSamples = (component, start=0, end=0) ->
  console.log component
  data = []
  input_length = component.input_samples.length
  console.dir component
  for port, i in component.input_samples
      data.push { key: 'Input port ' + i, values: [] } 
      uend = if end == 0 then component.input_samples[i].values.length else end
      subset = component.input_samples[i].values[start..uend]
      for sample,j in subset
        data[i].values.push {x:j, y: sample}

  for port, i in component.output_samples
      data.push { key: 'Output port ' + i, values: [] }
      uend = if end == 0 then component.output_samples[i].values.length else end
      subset = component.output_samples[i].values[start..uend]
      for sample,j in subset
        data[input_length + i].values.push {x:j, y: sample} 
  return data

__updateGlobalCharts = () ->
  for c in global_component_charts
    chart = c.chart
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

getComponentList = ( timestamp ) ->
  return $.ajax({
    url : '/componentlist/' + timestamp,
    type : "GET"
  })

getExecution = ( timestamp ) ->
  if not isInt timestamp
    parseInt timestamp  
  console.log "Ajax: Retrieving Execution " + timestamp
  return $.get '/execution/' + timestamp

getExecutionList = () ->
  return $.ajax({
      url : '/execution/list',
      type : "GET"
    })

getSamples = ( param ) ->
  console.log "AJAX FOLLOWING-"
  processData = (data, textStatux, jqXHR) ->
      console.log "HI was geht"
  $.post(
    '/samples'
    param
    processData
    )
  # timestamp = param.timestamp
  # uid = param.uid
  # amount = param.amount
  # skip = param.skip
  # $.ajax '/samples',
  #   type : "POST"
  #   dataType : 'json'
  #   data : param
  #   (data, textStatus, jqXHR) ->
  #     console.log "Stachursk."
  #     console.dir data
  # return $.post '/samples',
  #   timestamp: timestamp
  #   uid: uid
  #   amount: amount
  #   skip: skip

# Debugging methods
generateTestdata = () ->
  $.get '/generate', (data) ->
    console.log "AJAX: Generate Testdata Successful"
    return
  # return $.ajax({
  #   url : '/generate',
  #   type : "GET"
  # })

removeExecution = (timestamp) ->
  if isInt timestamp
    return $.ajax({
      url : '/remove/' + timestamp,
      type : "GET"
    })

removeEveryExecution = () ->
  $.get '/remove/all', (data) ->
    console.log "AJAX: Cleared Database"
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
    if datum < 10
      datum = "0" + datum
    if month < 10
      month = "0" + month
    if hours < 10
      hours = "0" + hours
    if minutes < 10
      minutes = "0" + minutes
    if seconds < 10
      seconds = "0" + seconds
    return  datum + '/' + month + '/' + year + ' - ' + hours + ':' + minutes + ':' + seconds;

__getJsDate = (x) ->
    date = new Date( x*1000 )
    return date

$(document).ready readyFn
