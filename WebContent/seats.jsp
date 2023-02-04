<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import = "java.util.ArrayList"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="css/seats.css" />
    <title>Simple Ticketing System</title>
  </head>
  <body>

    <ul class="showcase">
      <li>
        <div class="seat"></div>
        <small>Available</small>
      </li>
      <li>
        <div class="seat selected"></div>
        <small>Selected</small>
      </li>
      <li>
        <div class="seat sold"></div>
        <small>Sold</small>
      </li>
    </ul>

    <div class="container">
      <div class="screen">STAGE</div>
      <% ArrayList<Integer> arr = (ArrayList<Integer>) request.getAttribute("sold_seats"); 
      for (int i = 0; i < 6; i++) {%>
      <div class="row">
      	<% for (int j = 0; j < 8; j++) {
      		String cls = "seat";
      		if (arr.indexOf(i*8+j)!=-1) cls = "seat sold";%>
      		<div class="<%= cls %>"></div>
      	<% } %>
      </div>
      <% } %>
    </div>
	
    <p class="text">You have selected <span id="count">0</span> seat.</p>
    
    <form class="validate-form" action="Seats" method="post" >
      <div class="validate-input" data-validate = "Select at least 1 seat.">
	      <input class="input" type="hidden" id="selected_seats" name="selected_seats" value="1"/>
	      <input type="hidden" name="event_id" value=<%= request.getAttribute("event_id") %>>
      </div>
      <button class="button">Reserve!</button>
    </form>	
    <script src="vendor/jquery/jquery-3.2.1.min.js"></script>
	<script src="vendor/bootstrap/js/popper.js"></script>
	<script src="vendor/bootstrap/js/bootstrap.min.js"></script>
	<script src="vendor/select2/select2.min.js"></script>
	<script src="vendor/tilt/tilt.jquery.min.js"></script>
    <script src="js/seats.js"></script>
  </body>
</html>