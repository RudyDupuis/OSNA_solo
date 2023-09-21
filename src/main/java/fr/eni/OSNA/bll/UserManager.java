package fr.eni.OSNA.bll;

import fr.eni.OSNA.bo.User;
import fr.eni.OSNA.dal.DAOFactory;
import fr.eni.OSNA.dal.UserDAO;
import fr.eni.OSNA.messages.ErrorCode;
import fr.eni.OSNA.messages.MessageReader;

public class UserManager {
	private static UserManager instance;
	
	public static UserManager getInstance() {
		if(instance == null) {
			instance = new UserManager();
		}
		
		return instance;
	}
	
	private UserDAO dao = DAOFactory.getUserDAO();
	
	
	public User selectById(int id) throws Exception {
		return dao.selectById(id);
	}
	
	public void update(User user, User userSession) throws Exception {
		StringBuilder error = new StringBuilder();
		boolean hasError = false;
		//TODO vérifier les champs.
		
		if(user.getMail() != userSession.getMail() && !dao.checkUniqueMail(user)) {
			hasError = true;
			error.append(MessageReader.getErrorMessage(ErrorCode.ERROR_MAIL_NOTUNIQUE)).append("\n");
		}
		
		if(user.getPseudo() != userSession.getPseudo() && !dao.checkUniquePseudo(user)) {
			hasError = true;
			error.append(MessageReader.getErrorMessage(ErrorCode.ERROR_PSEUDO_NOTUNIQUE)).append("\n");
		}
		
		if(hasError) {
			throw new Exception(error.toString());
		} else {
			dao.update(user);
		}
	}
	
	public void insert(User user) throws Exception {
		StringBuilder error = new StringBuilder();
		boolean hasError = false;
		//TODO vérifier les champs.
		
		if(!dao.checkUniqueMail(user)) {
			hasError = true;
			error.append(MessageReader.getErrorMessage(ErrorCode.ERROR_MAIL_NOTUNIQUE)).append("\n");
		}
		
		if(!dao.checkUniquePseudo(user)) {
			hasError = true;
			error.append(MessageReader.getErrorMessage(ErrorCode.ERROR_PSEUDO_NOTUNIQUE)).append("\n");
		}
		
		if(hasError) {
			throw new Exception(error.toString());
		} else {
			dao.insert(user);
		}
	}
	
	public void delete(int id) throws Exception {
		dao.delete(id);
	}
	
	public User login(String id, String password) throws Exception {
		User user = dao.login(id, password);
		
		if(user != null) {
			return user;
		} else {
			throw new Exception(MessageReader.getErrorMessage(ErrorCode.ERROR_LOGIN));
		}
		
	}
	
	public String getPseudo(int id) throws Exception {
		return dao.getPseudo(id);
	}
}
