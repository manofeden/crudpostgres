<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Редактирование Информации о клиенте</title>
</head>
<body>

	<form action="edit_client" method="POST">
		<table>
			<tr>
				<td>ID:</td>
				<td><input name="id_client" type="text" class="field left"
					value=${client.id_client } readonly></td>
			</tr>
			<tr>
				<td>СНИЛС:</td>
				<td><input name="snils" type="text"
					value="<c:out value="${client.snils}" />"></td>
			</tr>

			<tr>
				<td>ФИО:</td>
				<td><input name="fio" type="text"
					value="<c:out value="${client.fio}" />"></td>
			</tr>

			<tr>
				<td>Дата рождения:</td>
				<td><input name="birthday" type="date"
					value="<c:out value="${client.birthday}" />"></td>
			</tr>

			<tr>
				<td><input name="save" value="Сохранить изменения"
					type="submit"></td>
				<td><input name="cancel" value="Отмена" type="submit">
				</td>
			</tr>
		</table>
	</form>


</body>
</html>
