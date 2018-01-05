<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
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
<title>Insert title here</title>
</head>
<body>
	<h1>Upload</h1>
	<form name="frmUpload" method="post" action="Upload" enctype="multipart/form-data">
		<select name="lstReport">
			<option value="techSales">Technician Sales Analysis</option>
			<option value="redo">Redo by Date</option>
		</select>
		<input type="file" id="file" name="uploadFile" multiple="true"
			onchange="uploadFile()">
		<p id="demo"></p>
		<input type="submit" name="btnSubmit" value="Upload">
	</form>
</body>
</html>