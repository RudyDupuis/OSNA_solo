package fr.eni.OSNA.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import fr.eni.OSNA.bo.User;
import fr.eni.OSNA.messages.ErrorCode;
import fr.eni.OSNA.messages.MessageReader;

public class UserDaoJdbcImpl implements UserDAO{
	
	@Override
	/** SELECT id, firstName, lastName, pseudo, mail, phone and address WHERE id */
	public User selectById(int id) throws Exception {
		String Sql = "SELECT * FROM users WHERE id = ?";
		User userData = null;
		
		try(Connection cnx = ConnectionProvider.getConnection(); PreparedStatement ps = cnx.prepareStatement(Sql)) {
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				userData = new User(
								rs.getInt("id"),
								rs.getString("firstName"),
								rs.getString("lastName"),
								rs.getString("pseudo"),
								rs.getString("mail"),
								rs.getString("phone"),
								rs.getString("street"),
								rs.getInt("postalCode"),
								rs.getString("city"),
								rs.getInt("points")
							);
			} 
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception(MessageReader.getErrorMessage(ErrorCode.ERROR_SELECT));
		}
		
		return userData;
	}

	@Override
	/** Method not used */
	public List<User> selectAll() throws Exception {
		return null;
	}

	@Override
	/** UPDATE firstName, lastName, pseudo, mail, phone, address and password WHERE id */
	public void update(User user) throws Exception {
		String Sql = "UPDATE users SET firstName = ?, lastName = ?, pseudo = ?, mail = ?, phone = ?, street = ?, postalCode = ?, city = ?, password = ? WHERE id = ?";
		
		try(Connection cnx = ConnectionProvider.getConnection(); PreparedStatement ps = cnx.prepareStatement(Sql)) {
			 ps.setString(1, user.getFirstName());
			 ps.setString(2, user.getLastName());
			 ps.setString(3, user.getPseudo());
			 ps.setString(4, user.getMail());
			 ps.setString(5, user.getPhone());
			 ps.setString(6, user.getStreet());
			 ps.setInt(7, user.getPostalCode());
			 ps.setString(8, user.getCity());
			 ps.setString(9, user.getPassword());
			 ps.setInt(10, user.getId());
			 ps.executeUpdate();
			 
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception(MessageReader.getErrorMessage(ErrorCode.ERROR_UPDATE));
		}
		
	}

	@Override
	/** INSERT firstName, lastName, pseudo, mail, phone, address, password and set points at 0 */
	public void insert(User user) throws Exception {
		String Sql = "INSERT INTO users(firstName, lastName, pseudo, mail, phone, street, postalCode, city, password, points) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		try(Connection cnx = ConnectionProvider.getConnection(); PreparedStatement ps = cnx.prepareStatement(Sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
			 ps.setString(1, user.getFirstName());
			 ps.setString(2, user.getLastName());
			 ps.setString(3, user.getPseudo());
			 ps.setString(4, user.getMail());
			 ps.setString(5, user.getPhone());
			 ps.setString(6, user.getStreet());
			 ps.setInt(7, user.getPostalCode());
			 ps.setString(8, user.getCity());
			 ps.setString(9, user.getPassword());
			 //initialization of points to 0
			 ps.setInt(10, 0);
			 ps.executeUpdate();
			 
			 ResultSet generatedKeys = ps.getGeneratedKeys();
				if (generatedKeys.next()) {
					user.setId(generatedKeys.getInt(1));
				}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception(MessageReader.getErrorMessage(ErrorCode.ERROR_INSERT));
		}
		
	}
	
	@Override
	/** DELETE WHERE id */
	public void delete(int id) throws Exception {
		String Sql = "DELETE FROM users WHERE id = ?";
		
		try(Connection cnx = ConnectionProvider.getConnection(); PreparedStatement ps = cnx.prepareStatement(Sql)) {
			 ps.setInt(1, id);
			 ps.executeUpdate();
			 
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception(MessageReader.getErrorMessage(ErrorCode.ERROR_DELETE));
		}
	}

	@Override
	/** SELECT firstName, lastName, pseudo, mail, phone, address, password and points WHERE mail and password OR pseudo and password */
	public User login(String id, String password) throws Exception {
		String Sql = "SELECT * FROM users WHERE (mail = ? AND password = ?) OR (pseudo = ? AND password = ?)";
		User userData = null;
		
		try(Connection cnx = ConnectionProvider.getConnection(); PreparedStatement ps = cnx.prepareStatement(Sql)) {
			ps.setString(1, id);
			ps.setString(2, password);
			ps.setString(3, id);
			ps.setString(4, password);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				userData = new User(
								rs.getInt("id"),
								rs.getString("firstName"),
								rs.getString("lastName"),
								rs.getString("pseudo"),
								rs.getString("mail"),
								rs.getString("phone"),
								rs.getString("street"),
								rs.getInt("postalCode"),
								rs.getString("city"),
								rs.getString("password"),
								rs.getInt("points")
							);
			} 
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception(MessageReader.getErrorMessage(ErrorCode.ERROR_SELECT));
		}
		
		return userData;
	}
	
	@Override
	/** UPDATE points WHERE id */
	public void updatePoints(User user) throws Exception {
		String Sql = "UPDATE users SET points = ? WHERE id = ?";
		
		try(Connection cnx = ConnectionProvider.getConnection(); PreparedStatement ps = cnx.prepareStatement(Sql)) {
			 ps.setInt(1, user.getPoints());
			 ps.setInt(2, user.getId());
			 ps.executeUpdate();
			 
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception(MessageReader.getErrorMessage(ErrorCode.ERROR_UPDATE));
		}
	}

	@Override
	/** Return true if mail is unique */
	public Boolean checkUniqueMail(User user) throws Exception {
		String Sql = "SELECT id FROM users WHERE mail = ?";
		
		try(Connection cnx = ConnectionProvider.getConnection(); PreparedStatement ps = cnx.prepareStatement(Sql)) {
			ps.setString(1, user.getMail());
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				return false;
			} else {
				return true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception(MessageReader.getErrorMessage(ErrorCode.ERROR_SELECT));
		}
	}

	@Override
	/** Return true if pseudo is unique */
	public Boolean checkUniquePseudo(User user) throws Exception {
		String Sql = "SELECT id FROM users WHERE pseudo = ?";
		
		try(Connection cnx = ConnectionProvider.getConnection(); PreparedStatement ps = cnx.prepareStatement(Sql)) {
			ps.setString(1, user.getPseudo());
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				return false;
			} else {
				return true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception(MessageReader.getErrorMessage(ErrorCode.ERROR_SELECT));
		}
	}

	@Override
	public String getPseudo(int id) throws Exception {
		String Sql = "SELECT pseudo FROM users WHERE id = ?";
		String pseudo = null;
		
		try(Connection cnx = ConnectionProvider.getConnection(); PreparedStatement ps = cnx.prepareStatement(Sql)) {
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				pseudo = rs.getString("pseudo");
			} 
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception(MessageReader.getErrorMessage(ErrorCode.ERROR_SELECT));
		}
		
		return pseudo;
	}
}
