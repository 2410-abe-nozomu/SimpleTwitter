package chapter6.service;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.SQLException;

import org.dbunit.Assertion;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.CompositeTable;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import chapter6.beans.Message;
import chapter6.utils.DBUtil;


// 1. テストクラス名は任意のものに変更してください。
// 2. L.23~86は雛形として使用してください。
// 3．L.44のファイル名は各自作成したファイル名に書き換えてください。
public class MessageServiceTest {

	private File file;

	@Before
	public void setUp() throws Exception {

		IDatabaseConnection connection = null;
		try {
			Connection conn = DBUtil.getConnection();
			connection = new DatabaseConnection(conn);

			//(2)現状のバックアップを取得
			QueryDataSet partialDataSet = new QueryDataSet(connection);
			partialDataSet.addTable("users");
			partialDataSet.addTable("messages");

			file = File.createTempFile("temp", ".xml");
			FlatXmlDataSet.write(partialDataSet,new FileOutputStream(file));

			//(3)テストデータを投入する
			IDataSet dataSetMessage = new FlatXmlDataSet(new File("insert_test_data.xml"));
			DatabaseOperation.CLEAN_INSERT.execute(connection, dataSetMessage);

			DBUtil.commit(conn);


		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
			}
		}
	}

	@After
	public void tearDown() throws Exception {
		IDatabaseConnection connection = null;
		try {
			Connection conn = DBUtil.getConnection();
			connection = new DatabaseConnection(conn);

			IDataSet dataSet = new FlatXmlDataSet(file);
			DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);

			DBUtil.commit(conn);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			//一時ファイルの削除
			if (file != null) {
				file.delete();
			}
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
			}

		}
	}

	// 以下に課題のテストメソッドを作成
	/**
	* insertメソッドのテスト
	*/
	@Test
	public void testInsertMessage() throws Exception {
	//テスト対象となる、storeメソッドを実行
	//テストのインスタンスを生成
	Message message = new Message();
	message.setUserId(2);
	message.setText("テスト完了");

	MessageService ms = new MessageService();

	ms.insert(message);


	//テスト結果として期待されるべきテーブルデータを表すITableインスタンスを取得
	IDatabaseConnection connection = null;
	try {
	Connection conn = DBUtil.getConnection();
	connection = new DatabaseConnection(conn);

	//メソッド実行した実際のテーブル
	IDataSet databaseDataSet = connection.createDataSet();
	ITable actualTable = databaseDataSet.getTable("messages");

	// テスト結果として期待されるべきテーブルデータを表すITableインスタンスを取得
	IDataSet expectedDataSet = new FlatXmlDataSet(new File("insert_test_data2.xml"));
	ITable expectedTable = expectedDataSet.getTable("messages");

	//期待されるITableと実際のITableの比較
	Assertion.assertEquals(expectedTable, new CompositeTable(expectedTable.getTableMetaData(), actualTable));
	} finally {
		if (connection != null)
			connection.close();
		}
	}
}
