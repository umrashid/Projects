<?php 
session_start();
if(isset($_SESSION['Logged_in'])){

}else{
  header("location: index.php");
}

?>

<!DOCTYPE html>
<html>
<title>Inventory Management</title>

<!-- Including all the links -->
<?php include 'links.php'; ?>

<link href="search.css" rel="stylesheet" />

<style>
body,h1,h2,h3,h4,h5,h6 {font-family: "Raleway", sans-serif}
body, html {
    height: 100%;
    line-height: 1.8;
}

.w3-bar .w3-button {
    padding: 16px;
}
</style>

<body>
<!-- Including the Navbar -->
<?php include 'navbar.php'; ?>

<!-- Search Options -->
    <div  class="s003">
      <form id="searchOption" action="" method="GET">
        <div class="inner-form">
          <div class="input-field first-wrap">
            <div class="input-select full">
              <select id="selectData" data-trigger="" name="choices-single-defaul">
                <option value = "0" placeholder="">Search By:</option>
                <option value="byStore.php">Store Number</option>
                <option value="byProductID.php">Product ID</option>
                <option value="byProductName.php">Product Name</option>
              </select>
            </div>
          </div>
          <div class="input-field second-wrap">
            <input id="search" name="data" type="text" placeholder="Search Here?" />
          </div>
          <div class="input-field third-wrap">
            <button id="searchButton" class="btn-search" type="submit">
              <svg class="svg-inline--fa fa-search fa-w-16" aria-hidden="true" data-prefix="fas" data-icon="search" role="img" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512">
                <path fill="currentColor" d="M505 442.7L405.3 343c-4.5-4.5-10.6-7-17-7H372c27.6-35.3 44-79.7 44-128C416 93.1 322.9 0 208 0S0 93.1 0 208s93.1 208 208 208c48.3 0 92.7-16.4 128-44v16.3c0 6.4 2.5 12.5 7 17l99.7 99.7c9.4 9.4 24.6 9.4 33.9 0l28.3-28.3c9.4-9.4 9.4-24.6.1-34zM208 336c-70.7 0-128-57.2-128-128 0-70.7 57.2-128 128-128 70.7 0 128 57.2 128 128 0 70.7-57.2 128-128 128z"></path>
              </svg>
            </button>
          </div>
        </div>
      </form>
    </div>


 <!--Navbar -->
<script src="js/navbar.js"></script>
<!-- choices.js -->
<script src="js/extention/choices.js"></script>

<script>
      const choices = new Choices('[data-trigger]',
      {
        searchEnabled: false,
        itemSelectText: '',
      });

        document.getElementById('selectData').onchange = function(){

            document.getElementById('searchOption').action = this.value;
            var selection = this.options[0].text;
            document.getElementById('search').placeholder = this.options[0].text;
            if(selection == "Search By:")
                document.getElementById('search').placeholder = "Search Here";
            }
</script>

</body>
</html>
