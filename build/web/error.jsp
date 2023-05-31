<!DOCTYPE html>
<html>
<head>
    <title>Error</title>
</head>
<body>
    <h1>Error</h1>
    <p>An error occurred during login:</p>
    <p><%= request.getAttribute("exception").toString() %></p>
</body>
</html>