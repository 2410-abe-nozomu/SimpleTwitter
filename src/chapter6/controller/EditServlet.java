package chapter6.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import chapter6.beans.Message;
import chapter6.logging.InitApplication;
import chapter6.service.MessageService;

@WebServlet(urlPatterns = { "/edit" })
public class EditServlet extends HttpServlet {

	/**
	* ロガーインスタンスの生成
	*/
	Logger log = Logger.getLogger("twitter");

	/**
	* デフォルトコンストラクタ
	* アプリケーションの初期化を実施する。
	*/
	public EditServlet() {
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

		//エラーメッセージの準備
		HttpSession session = request.getSession();
		List<String> errorMessages = new ArrayList<String>();

		//top.jspから更新するつぶやきのidを取得
		String editMessageId = request.getParameter("editMessageId");

		//つぶやきのidが数字以外だったときトップ画面に遷移し、エラーメッセージを表示
		if (StringUtils.isBlank(editMessageId)) {
			errorMessages.add("不正なパラメータが入力されました");
			session.setAttribute("errorMessages", errorMessages);
			response.sendRedirect("./");
			return;
		}

		//つぶやきのidが数字以外だったときトップ画面に遷移し、エラーメッセージを表示
		if (!editMessageId.matches("^[0-9]+$")) {
			errorMessages.add("不正なパラメータが入力されました");
			session.setAttribute("errorMessages", errorMessages);
			response.sendRedirect("./");
			return;
		}

		//editMessageIdをint型にして引数とし、MessageServiceのeditSelectを呼び出す
		List<Message> message = new MessageService().select(Integer.parseInt(editMessageId));

		//つぶやきのidが存在しないときトップ画面に遷移し、エラーメッセージを表示
		if (message == null) {
			errorMessages.add("不正なパラメータが入力されました");
			session.setAttribute("errorMessages", errorMessages);
			response.sendRedirect("./");
			return;
		}

		//出力するデータと出力するJSPを指定して、フォワード
		request.setAttribute("message", message.get(0));
		RequestDispatcher dispatcher = request.getRequestDispatcher("edit.jsp");
		dispatcher.forward(request, response);

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.info(new Object() {
		}.getClass().getEnclosingClass().getName() +
				" : " + new Object() {
				}.getClass().getEnclosingMethod().getName());

		//JSPから更新したつぶやきを受け取る
		String editedText = request.getParameter("editedText");
		String id = request.getParameter("messageId");
		int messageId = Integer.parseInt(id);

		//エラーメッセージの準備
		List<String> errorMessages = new ArrayList<String>();

		//isValidでエラーを検証し、エラーがあった場合はエラーメッセージを表示
		if (!isValid(editedText, errorMessages)) {
			//エラーメッセージ表示
			request.setAttribute("errorMessages", errorMessages);

			//更新されたテキストとidをbeansにセットして、jspに表示
			Message message = new Message();
			message.setText(editedText);
			message.setId(messageId);

			request.setAttribute("message", message);

			RequestDispatcher dispatcher = request.getRequestDispatcher("edit.jsp");
			dispatcher.forward(request, response);

			return;

		}

		//更新されたテキストとidをbeansにセットして、jspに表示
		Message message = new Message();
		message.setText(editedText);
		message.setId(messageId);

		new MessageService().update(message);

		response.sendRedirect("./");
	}

	private boolean isValid(String text, List<String> errorMessages) {

		log.info(new Object() {
		}.getClass().getEnclosingClass().getName() +
				" : " + new Object() {
				}.getClass().getEnclosingMethod().getName());

		if (StringUtils.isBlank(text)) {
			errorMessages.add("入力してください");
		} else if (140 < text.length()) {
			errorMessages.add("140文字以下で入力してください");
		}

		if (errorMessages.size() != 0) {
			return false;
		}
		return true;
	}
}
