/**
 * 
 */
function myFunction() {
  document.getElementById("user_menu").classList.toggle("show");
}

// Close the dropdown if the user clicks outside of it
window.onclick = function(e) {
  if (!e.target.matches('.menu_button')) {
  var user_menu = document.getElementById("user_menu");
    if (user_menu.classList.contains('show')) {
      user_menu.classList.remove('show');
    }
  }
}