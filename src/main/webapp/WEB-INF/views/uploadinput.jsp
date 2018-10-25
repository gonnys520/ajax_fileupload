<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<title>Gonnys Upload files!</title>
</head>
<body>
  <h1>upload test page</h1>
  <form action="/upload" method="post" enctype="multipart/form-data">
  <input type="file" name="files" multiple="multiple">
  <button>Upload</button>
  </form>
</body>
</html>
