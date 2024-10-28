package chapter6.beans;

import java.io.Serializable;
import java.util.Date;

public class UserComment implements Serializable {

    private int id;
    private String account;
    private String name;
    private int messageId;
    private String text;
    private Date createdDate;

    // getter/setterは省略されているので、自分で記述しましょう。

  //idのゲッター・セッター
  	public int getId() {
  		return id;
  	}

  	public void setId(int id) {
  		this.id = id;
  	}

  	//accountのゲッター・セッター
  	public String getAccount() {
  		return account;
  	}

  	public void setAccount(String account) {
  		this.account = account;
  	}

  	//nameのゲッター・セッター
  	public String getName() {
  		return name;
  	}

  	public void setName(String name) {
  		this.name = name;
  	}

  //userIdのゲッター・セッター
  	public int getMessageId() {
  		return messageId;
  	}

  	public void setMessageId(int messageId) {
  		this.messageId = messageId;
  	}

  //textのゲッター・セッター
  	public String getText() {
  		return text;
  	}

  	public void setText(String text) {
  		this.text = text;
  	}

  	//createdDateのゲッター・セッター
  	public Date getCreatedDate() {
  		return createdDate;
  	}

  	public void setCreatedDate(Date createdDate) {
  		this.createdDate = createdDate;
  	}
}

