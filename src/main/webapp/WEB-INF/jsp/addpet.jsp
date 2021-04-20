<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ tablib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Pet</title>
</head>
<body>
   <form:form modelAttribute="pet">
       <table>
           <tr>
               <td><spring:message code="name"/>:</td>
               <td>
                   <form:input path="name"/>
               </td>
           </tr>
           <tr>
               <td>Species:</td>
               <td>
                   <form:input path="species"/>
               </td>
           </tr>
           <tr>
               <td>Gender:</td>
               <td>
                   <form:input path="gender"/>
               </td>
           </tr>
           <tr>
            <td colspan="2">
                <input type="submit" value="Add"> 
            </td>
           </tr>
       </table>
   </form:form> 
</body>
</html>