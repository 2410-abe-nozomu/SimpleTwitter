package chapter6.beans;

import java.io.Serializable;

public class Comment implements Serializable {

    private String text;  //返信のテキスト
    private int userId;  //返信する人のid
    private int messageId;  //返信する対象のつぶやきのid

    // getter/setterは省略されているので、自分で記述しましょう。

	//返信のテキストのゲッター・セッター
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	//返信する人のidのゲッター・セッター
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	//返信する対象のつぶやきのidのゲッター・セッター
	public int getMessageId() {
		return messageId;
	}

	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}
}