package com.mycompany.crudpostgres.repository;

import static com.mycompany.crudpostgres.Astat.counter;

import java.sql.ResultSet;
import java.sql.Types;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mycompany.crudpostgres.LongProcess;
import com.mycompany.crudpostgres.model.Client;

/**
 *
 * @author Leonid Ivanov
 */
@Repository
public class ClientDao_Impl implements ClientDao {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public void createClients() {
		jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS clients (" + "id_client serial PRIMARY KEY, " + "snils varchar(255) NOT NULL, " + "fio varchar(255) NOT NULL, " + "birthday date);");

		System.out.println("CREATE TABLE clients ...");
	}

	@Override
	public Client getClient(int i) {
		List<Client> list = jdbcTemplate.query("SELECT id_client, snils, fio, birthday FROM clients WHERE id_client=?", new Object[] { i },
				(ResultSet rs, int x) -> new Client(rs.getInt("id_client"), rs.getString("snils"), rs.getString("fio"), rs.getDate("birthday")));

		if (list.isEmpty()) {
			return new Client();
		} else {
			return list.get(0);
		}
	}

	@Override
	public List<Client> getClients() {
		return jdbcTemplate.query("SELECT id_client, snils, fio, birthday FROM clients ORDER BY snils", (ResultSet rs, int i) -> new Client(rs.getInt("id_client"), rs.getString("snils"), rs.getString("fio"), rs.getDate("birthday")));
	}

	@Override
	public List<Client> getClientsForSnils(String snils) {
		return jdbcTemplate.query("SELECT id_client, snils, fio, birthday FROM clients WHERE snils LIKE ? ORDER BY snils", new String[] { "%" + snils + "%" },
				(ResultSet rs, int i) -> new Client(rs.getInt("id_client"), rs.getString("snils"), rs.getString("fio"), rs.getDate("birthday")));
	}

	@Override
	public List<Client> getClientsForUniqSnils(String snils) {
		return jdbcTemplate.query("SELECT id_client, snils, fio, birthday FROM clients WHERE snils=? ORDER BY snils", new String[] { snils },
				(ResultSet rs, int i) -> new Client(rs.getInt("id_client"), rs.getString("snils"), rs.getString("fio"), rs.getDate("birthday")));
	}

	@Override
	public List<Client> addClient(Client client) {
		int rows = jdbcTemplate.update("INSERT INTO clients(snils, fio, birthday) VALUES(?, ?, ?)", new Object[] { client.getSnils(), client.getFio(), client.getBirthday() }, new int[] { Types.VARCHAR, Types.VARCHAR, Types.DATE });

		appendCounter(rows);

		System.out.println(rows + " row(s) inserted.");
		return getClients();
	}

	@Override
	public List<Client> editClient(Client client) {
		int rows = jdbcTemplate.update("UPDATE clients SET snils = ?, fio = ?, birthday = ? WHERE id_client=?", new Object[] { client.getSnils(), client.getFio(), client.getBirthday(), client.getId_client() },
				new int[] { Types.VARCHAR, Types.VARCHAR, Types.DATE, Types.INTEGER });
		System.out.println(rows + " row(s) updated.");

		appendCounter(rows);

		return getClients();
	}

	@Override
	public List<Client> deleteClient(int i) {
		Object[] params = { i };
		int[] types = { Types.INTEGER };
		int rows = jdbcTemplate.update("DELETE FROM clients WHERE id_client = ?", params, types);
		System.out.println(rows + " row(s) deleted.");

		appendCounter(rows);

		return getClients();
	}

	private void appendCounter(int rows) {
		if (rows > 0) {
			counter = counter + 1;
			LongProcess.checkLongProcess();
		}
	}

}
