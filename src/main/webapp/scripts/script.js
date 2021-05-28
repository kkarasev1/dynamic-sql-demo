$(document).ready(function() {
  var currentSorts = $("#mainForm input[name=sort]");
  if (currentSorts.length > 0) {
    var split = currentSorts.val().split(",");
    $(".sort_col").each(function(i, element) {
         var id = getSortColumn(this);
         if (id == split[0]) {
           $(this).addClass(split[1] == "ASC" ? "down" : "up");
         }
    });
  }
  $(".sort_col").click(function(){
    var id = getSortColumn(this);
    var direction =  ($(this).hasClass("down")) ? "desc" : "asc";
    $("#mainForm input[name=sort]").val(id + "," + direction);
    $("#mainForm").submit();
  });

  $("#prevPageLink").click(function(){
    var pageNumber = parseInt($("#mainForm input[name=page]").val());
    pageNumber--;
    $("#mainForm input[name=page]").val(pageNumber);
    $("#mainForm").submit();
  });

  $("#nextPageLink").click(function(){
    var pageNumber = parseInt($("#mainForm input[name=page]").val());
    pageNumber++;
    $("#mainForm input[name=page]").val(pageNumber);
    $("#mainForm").submit();
  });

});

function getSortColumn(that) {
  return $(that).attr("id").split("-")[1];
}