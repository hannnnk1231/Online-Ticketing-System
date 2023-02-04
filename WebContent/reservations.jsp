<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import = "org.json.*"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />

    <!-- Material Icons -->

    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet" />
    <!-- CSS File -->
    <link rel="stylesheet" href="css/event.css" />
    <title>Simple Ticketing System</title>
  </head>
  <body>
    <!-- Header Starts -->
    <div class="header">
      <div class="header__left">
        <h1>Hi! <%= request.getAttribute("myUsername") %>, there are <%=request.getAttribute("cont") %> people online!!</h1>
      </div>

      <div class="menu">
        <i class="material-icons menu_button" onclick="myFunction()" id="account_icon">account_circle</i>
        <div id="user_menu" class="menu_content">
          <form action="Events" method="get">
          	<button>Events</button>
          </form>
          <form action="Logout" method="post">
          	<button>Logout</button>
          </form>
        </div>
      </div>
    </div>
    <!-- Header Ends -->

    <!-- Main Body Starts -->
    <div class="mainBody">

      <!-- Feed Section -->
      <div class="events">
      <%JSONObject jo = (JSONObject) request.getAttribute("reservations");
        JSONArray arr = jo.getJSONArray("reservations");%>
        <div class="event_category_title">
          <h1>Your Reservations</h1>
        </div>
        <%for (int i = 0; i < arr.length(); i++) {%>
        <div class="event_all">
          <div class="event_container">
            <div class="event_header">
              <h1><%= arr.getJSONObject(i).getString("name") %></h1>
            </div>
            <div class="event_content">
              <img src=<%= arr.getJSONObject(i).getString("img") %>>
              <div class="event_detail">
                <h2>Date/Time</h2>
                <p><%= arr.getJSONObject(i).getString("date") %></p>
                <h2>Location</h2>
                <p ><%= arr.getJSONObject(i).getString("loc") %></p>
                <h2>Your Seat</h2>
                <p ><%= arr.getJSONObject(i).getString("seat") %></p>
              </div>
            </div>
          </div>
        </div>
      <%} %>
      </div>
    <!-- Main Body Ends -->
    </div>
  <script src="js/events.js"></script>
  </body>
</html>