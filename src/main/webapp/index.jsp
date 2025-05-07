<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>HIT DevOps Task Manager</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #f5f5f5;
        }
        .container {
            background-color: white;
            border-radius: 5px;
            padding: 20px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
            max-width: 600px;
            margin: 0 auto;
        }
        h1 {
            color: #333;
            text-align: center;
        }
        form {
            display: flex;
            margin-bottom: 20px;
        }
        input[type="text"] {
            flex: 1;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px 0 0 4px;
        }
        button {
            padding: 10px 15px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 0 4px 4px 0;
            cursor: pointer;
        }
        button:hover {
            background-color: #45a049;
        }
        ul {
            list-style-type: none;
            padding: 0;
        }
        li {
            padding: 10px;
            border-bottom: 1px solid #ddd;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        li:last-child {
            border-bottom: none;
        }
        .empty-list {
            text-align: center;
            color: #777;
            padding: 20px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>HIT DevOps Task Manager</h1>
        
        <%
        // Initialize the tasks list if it doesn't exist
        List<String> tasks = (List<String>) session.getAttribute("tasks");
        if (tasks == null) {
            tasks = new ArrayList<>();
            session.setAttribute("tasks", tasks);
        }
        
        // Handle form submission
        String newTask = request.getParameter("task");
if (newTask != null && !newTask.trim().isEmpty()) {
    tasks.add(newTask.trim());
    session.setAttribute("tasks", tasks);
    // Redirect after processing the form
    response.sendRedirect("index.jsp");
    return; // Important to stop further processing
}
        
        // Handle clear tasks
        String clearTasks = request.getParameter("clear");
        if (clearTasks != null && clearTasks.equals("true")) {
            tasks.clear();
            session.setAttribute("tasks", tasks);
        }
        %>
        
        <form method="post" action="index.jsp">
            <input type="text" name="task" placeholder="Enter a new task..." required>
            <button type="submit">Add Task</button>
        </form>
        
        <% if (!tasks.isEmpty()) { %>
            <ul>
                <% for (String task : tasks) { %>
                    <li><%= task %></li>
                <% } %>
            </ul>
            <p style="text-align: center;">
                <a href="index.jsp?clear=true">Clear All Tasks</a>
            </p>
        <% } else { %>
            <div class="empty-list">No tasks yet. Add one above!</div>
        <% } %>
    </div>
</body>
</html>