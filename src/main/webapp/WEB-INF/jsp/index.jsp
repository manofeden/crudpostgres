<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Поликлинника</title>
</head>
<body>

	<form action="add" method="POST">
		<table>
			<tr>
				<td colspan="2"><input name="snils" type="text"
					placeholder="СНИЛС"></td>
				<td colspan="2"><input name="fio" type="text" placeholder="ФИО">
				</td>
				<td colspan="2"><input name="birthday" type="date"
					placeholder="Дата рождения"></td>
				<td><input value="Добавить" type="submit"></td>
			</tr>
		</table>
	</form>


	<br>

	<form action="search" method="POST">
		<table>
			<tr>
				<td>Укажите СНИЛС:</td>
				<td><input name="snils" type="text" placeholder="СНИЛС">
				</td>

				<td><input name="search" value="Найти Клиентов по СНИЛС"
					type="submit"></td>
			</tr>
		</table>
	</form>

	<br>

	<table border="1">
		<tr>
			<th>Идентификатор</th>
			<th>СНИЛС</th>
			<th>ФИО</th>
			<th>Дата рождения</th>
		</tr>

		<c:forEach var="client" items="${clients}">
			<tr>
				<td>${client.id_client}</td>
				<td><c:out value="${client.snils}" /></td>
				<td><c:out value="${client.fio}" /></td>
				<td><fmt:formatDate value="${client.birthday}"
						pattern="dd.MM.yyyy" /></td>

				<td><a href="edit?id=${client.id_client}"> <img
						src="edit.png" alt="" />
				</a></td>
				<td><a href="delete?id=${client.id_client}"> <img
						src="delete24.png" alt="" />
				</a></td>
			</tr>
		</c:forEach>



	</table>
</body>
</html>

