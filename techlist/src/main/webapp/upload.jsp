<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

<!-- jQuery library -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<!-- Latest compiled JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<script>
	function uploadFile() {
		var x = document.getElementById("file");
		var txt = "";
		var path = "";

		if('files' in x) {
			if(x.files.length == 0) {
				txt = "Select a file";
			} else {
				for (var i = 0; i < x.files.length; i++) {
					var file = x.files[i];
					if('name' in file) {
						txt += "<br><strong>" + file.name + "</strong><br>";
						path += file.name;
					}
				}
			}
		} else {
			if(x.value == "") {
				txt += "Select one or more files";
			} else {
				txt += "The files property is not supported by your browser";
				txt += "<br>The path of the selected file: " + x.value;
			}
		}

		document.getElementById("demo").innerHTML = txt;
		document.getElementById("filePath").value = path;
	}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>TechList</title>
</head>
<body style="background-color: #E9EFEA">
<div class="jumbotron" style="background-color: #272C3C;">
	<h1 style="color: #E9EFEA"><img src="images/techlist.jpg" class="img-rounded" alt="tech list" style="width: 300px; height: 128px;"> Tech List</h1>
	<h2 style="color: #E9EFEA">Business Intelligence for Management</h2>
</div>
<div class="container well pull-left" style="background-color: #272C3C; color: #E9EFEA;">
	<div class="page-header">
	<h1>Let's upload some reports!</h1>
	</div>
	<h3>Select the report type that you would like to upload, then choose the file.</h3>
	<form name="frmUpload" method="post" action="Upload" enctype="multipart/form-data">
		<select name="lstReport" style="color: #272C3C">
			<option value="techSales">Technician Sales Analysis</option>
			<option value="redo">Redo by Date</option>
		</select>
		<input type="file" id="file" name="uploadFile" multiple="true"
			onchange="uploadFile()">
		<p id="demo"></p>
		<button type="submit" class="btn-default" name="btnSubmit">
			<span class="glyphicon glyphicon-cloud-upload"></span> Upload
		</button>
		<c:set var="results" value="${results }" />
		<c:if test="${results != null }">
			<div class="page-header">
				<h2>We're done! Let's see some results</h2>
			</div>
			<div class="row">
				<div class="col-lg-2 text-right">
					<strong>Total time for upload:</strong>
				</div>
				<div class="col-lg-10">${results[0] }</div>
			</div>
			<div class="row">
				<div class="col-lg-2 text-right">
					<strong>Total Records:</strong>
				</div>
				<div class="col-lg-10">${results[1] }</div>
			</div>
			<div class="row">
				<div class="col-lg-2 text-right">
					<strong>Records per second:</strong>
				</div>
				<div class="col-lg-10">${results[2] }</div>
			</div>
		</c:if>
	</form>
</div>
</body>
</html>