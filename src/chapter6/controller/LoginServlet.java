package chapter6.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import chapter6.beans.User;
import chapter6.logging.InitApplication;
import chapter6.service.UserService;

@WebServlet(urlPatterns = { "/login" })
public class LoginServlet extends HttpServlet {

	/**
	* ロガーインスタンスの生成
	*/
	Logger log = Logger.getLogger("twitter");

	/**
	* デフォルトコンストラクタ
	* アプリケーションの初期化を実施する。
	*/
	public LoginServlet() {
		InitApplication application = InitApplication.getInstance();
		application.init();

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.info(new Object() {
		}.getClass().getEnclosingClass().getName() +
				" : " + new Object() {
				}.getClass().getEnclosingMethod().getName());

		request.getRequestDispatcher("login.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.info(new Object() {
		}.getClass().getEnclosingClass().getName() +
				" : " + new Object() {
				}.getClass().getEnclosingMethod().getName());

		//login.jspで入力されたaccountOrEmailとpasswordを取得
		String accountOrEmail = request.getParameter("accountOrEmail");
		String password = request.getParameter("password");

		//UserServiceに引数としてaccountOrEmailとpasswordを渡し該当するデータを受け取る
		User user = new UserService().select(accountOrEmail, password);
		//データの取得ができなかった場合はエラーメッセージを表示して、ログイン画面へ
		if (user == null) {
			List<String> errorMessages = new ArrayList<String>();
			errorMessages.add("ログインに失敗しました");
			request.setAttribute("errorMessages", errorMessages);
			request.getRequestDispatcher("login.jsp").forward(request, response);
			return;
		}

        //セッションオブジェクトを生成（セッションを操作するための準備）
		HttpSession session = request.getSession();

		//セッションにloginUserとして、取得したaccountOrEmailとpasswordを保持
		//トップページにリダイレクト
		session.setAttribute("loginUser", user);
		response.sendRedirect("./");
	}
}
