package chapter6.controller;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import chapter6.beans.User;
import chapter6.beans.UserComment;
import chapter6.beans.UserMessage;
import chapter6.logging.InitApplication;
import chapter6.service.CommentService;
import chapter6.service.MessageService;

@WebServlet(urlPatterns = { "/index.jsp" })
public class TopServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	* ロガーインスタンスの生成
	*/
	Logger log = Logger.getLogger("twitter");

	/**
	* デフォルトコンストラクタ
	* アプリケーションの初期化を実施する。
	*/
	public TopServlet() {
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

		//isShowMessageFormにfalseをセット
		boolean isShowMessageForm = false;
		//top.jspからloginUser（ログインしているユーザーのデータ）を取得
		User user = (User) request.getSession().getAttribute("loginUser");
		//loginUserがnull（ログインしていない）ときはisShowMessageFormにtureをセット
		if (user != null) {
			isShowMessageForm = true;
		}

		//JSPからuserId、絞り込みの情報を受け取り、MessageServiceに引数として渡す
		String userId = request.getParameter("user_id");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		List<UserMessage> messages = new MessageService().select(userId, startDate, endDate);

		//返信全件を取得する
		List<UserComment> userComments = new CommentService().select();

		//top.jspに出力
		request.setAttribute("startDate", startDate);
		request.setAttribute("endDate", endDate);
		request.setAttribute("messages", messages);
		request.setAttribute("isShowMessageForm", isShowMessageForm);
		request.setAttribute("userComments", userComments);
		request.getRequestDispatcher("/top.jsp").forward(request, response);
	}
}
