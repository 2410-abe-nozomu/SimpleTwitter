package chapter6.service;

import static chapter6.utils.CloseableUtil.*;
import static chapter6.utils.DBUtil.*;

import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;

import chapter6.beans.User;
import chapter6.dao.UserDao;
import chapter6.logging.InitApplication;
import chapter6.utils.CipherUtil;

public class UserService {

	/**
	* ロガーインスタンスの生成
	*/
	Logger log = Logger.getLogger("twitter");

	/**
	* デフォルトコンストラクタ
	* アプリケーションの初期化を実施する。
	*/

	//LoginServletから呼び出される
	public User select(String accountOrEmail, String password) {

		//不明
		log.info(new Object() {
		}.getClass().getEnclosingClass().getName() +
				" : " + new Object() {
				}.getClass().getEnclosingMethod().getName());

		//コネクションの初期化
		Connection connection = null;
		try {
			// パスワード暗号化（詳細不明）
			String encPassword = CipherUtil.encrypt(password);

			//コネクションを取得（詳細不明）
			connection = getConnection();
			//UserDaoを呼び出す
			User user = new UserDao().select(connection, accountOrEmail, encPassword);
			commit(connection);

			//UserDaoの戻り値をLoginServletに返す
			return user;

			//エラー処理
		} catch (RuntimeException e) {
			rollback(connection);
			log.log(Level.SEVERE, new Object() {
			}.getClass().getEnclosingClass().getName() + " : " + e.toString(), e);
			throw e;
		} catch (Error e) {
			rollback(connection);
			log.log(Level.SEVERE, new Object() {
			}.getClass().getEnclosingClass().getName() + " : " + e.toString(), e);
			throw e;
		} finally {
			close(connection);
		}
	}

	public UserService() {
		InitApplication application = InitApplication.getInstance();
		application.init();

	}

	public void insert(User user) {

		log.info(new Object() {
		}.getClass().getEnclosingClass().getName() +
				" : " + new Object() {
				}.getClass().getEnclosingMethod().getName());

		Connection connection = null;
		try {
			// パスワード暗号化
			String encPassword = CipherUtil.encrypt(user.getPassword());
			user.setPassword(encPassword);

			connection = getConnection();
			new UserDao().insert(connection, user);
			commit(connection);
		} catch (RuntimeException e) {
			rollback(connection);
			log.log(Level.SEVERE, new Object() {
			}.getClass().getEnclosingClass().getName() + " : " + e.toString(), e);
			throw e;
		} catch (Error e) {
			rollback(connection);
			log.log(Level.SEVERE, new Object() {
			}.getClass().getEnclosingClass().getName() + " : " + e.toString(), e);
			throw e;
		} finally {
			close(connection);
		}
	}

	public User select(int userId) {

		log.info(new Object() {
		}.getClass().getEnclosingClass().getName() +
				" : " + new Object() {
				}.getClass().getEnclosingMethod().getName());

		Connection connection = null;
		try {
			connection = getConnection();
			User user = new UserDao().select(connection, userId);
			commit(connection);

			return user;
		} catch (RuntimeException e) {
			rollback(connection);
			log.log(Level.SEVERE, new Object() {
			}.getClass().getEnclosingClass().getName() + " : " + e.toString(), e);
			throw e;
		} catch (Error e) {
			rollback(connection);
			log.log(Level.SEVERE, new Object() {
			}.getClass().getEnclosingClass().getName() + " : " + e.toString(), e);
			throw e;
		} finally {
			close(connection);
		}
	}

	public void update(User user) {

		log.info(new Object() {
		}.getClass().getEnclosingClass().getName() +
				" : " + new Object() {
				}.getClass().getEnclosingMethod().getName());

		Connection connection = null;
		try {
			// パスワードの入力があったときは、パスワード暗号化
			if (!StringUtils.isBlank(user.getPassword())) {
				String encPassword = CipherUtil.encrypt(user.getPassword());
				user.setPassword(encPassword);
			}
			connection = getConnection();
			new UserDao().update(connection, user);
			commit(connection);
		} catch (RuntimeException e) {
			rollback(connection);
			log.log(Level.SEVERE, new Object() {
			}.getClass().getEnclosingClass().getName() + " : " + e.toString(), e);
			throw e;
		} catch (Error e) {
			rollback(connection);
			log.log(Level.SEVERE, new Object() {
			}.getClass().getEnclosingClass().getName() + " : " + e.toString(), e);
			throw e;
		} finally {
			close(connection);
		}
	}

	public User select(String account) {

		Connection connection = null;
		try {
			connection = getConnection();
			User user = new UserDao().select(connection, account);
			commit(connection);

			return user;
		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}
}
