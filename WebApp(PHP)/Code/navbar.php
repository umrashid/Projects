
<!-- Navbar (sit on top) -->
<div class="w3-top">
  <div class="w3-bar w3-white w3-card" id="myNavbar">
    <a href="login.php" class="w3-bar-item w3-button w3-wide">INVENTORY MANAGEMENT FOR <?php echo "<b>Store:</b> ". $_SESSION["storeNumber"];?></a>
    <!-- Right-sided navbar links -->
    <div class="w3-right w3-hide-medium w3-hide-small">
      <a href="update.php" class="w3-bar-item w3-button"><i class="fa fa-edit"></i> UPDATE</a>
      <a href="login.php" class="w3-bar-item w3-button"><i class="fa fa-search"></i> SEARCH</a>
      <a href="changePassword.php" class="w3-bar-item w3-button"><i class="fa fa-key"></i> CHANGE PASSWORD</a>
      <a href="signout.php" class="w3-bar-item w3-button"><i class="fa fa-sign-out"></i> SIGN OUT</a>
    </div>
    <!-- Hide right-floated links on small screens and replace them with a menu icon -->

    <a href="javascript:void(0)" class="w3-bar-item w3-button w3-right  w3-hide-large " onclick="w3_open()">
      <i class="fa fa-bars"></i>
    </a>
  </div>
</div>

<!-- Sidebar on small screens when clicking the menu icon -->
<nav class="w3-sidebar w3-bar-block w3-black w3-card w3-animate-left  w3-hide-large" style="display:none" id="mySidebar">
  <a href="javascript:void(0)" onclick="w3_close()" class="w3-bar-item w3-button w3-large w3-padding-16">Close ×</a>
  <a href="update.php" onclick="w3_close()" class="w3-bar-item w3-button">UPDATE</a>
  <a href="login.php" onclick="w3_close()" class="w3-bar-item w3-button">SEARCH</a>
  <a href="changePassword.php" onclick="w3_close()" class="w3-bar-item w3-button">CHANGE PASSWORD</a>
  <a href="signout.php" onclick="w3_close()" class="w3-bar-item w3-button">SIGN OUT</a>
</nav>