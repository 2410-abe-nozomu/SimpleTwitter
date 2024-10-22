package chapter6.beans;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {

	private int id;
	private String account;
	private String name;
	private String email;
	private String password;
	private String description;
	private Date createdDate;
	private Date updatedDate;

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

	//emailのゲッター・セッター
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	//passwordのゲッター・セッター
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	//descriptionのゲッター・セッター
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	//createdDateのゲッター・セッター
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	//updatedDateeのゲッター・セッター
	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
}