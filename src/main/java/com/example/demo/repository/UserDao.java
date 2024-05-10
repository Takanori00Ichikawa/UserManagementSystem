package com.example.demo.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.User;

/**
 * 実践課題その２のユーザー情報のリポジトリークラス
 */
@Repository
public class UserDao implements IUserDao {
	
	/** DI対象 */
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	/**
	 * 全ユーザー情報の検索メソッド
	 * @return 全ユーザー情報
	 */
	@Override
	public List<User> findAll() {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("SELECT * FROM user");
		
		// ユーザー情報を全件検索するSQLを作成
		String sql = sqlBuilder.toString();
		
		// ユーザー情報のパラメータを設定するマップをインスタンス化
		Map<String, String> param = new HashMap<>();
		// ユーザー情報を全件検索
		List<Map<String, Object>> resultList = 
				jdbcTemplate.queryForList(sql, param);
		// 戻り値のエンティティリストをインスタンス化
		List<User> list = new ArrayList<User>();
		
		// エンティティリスト作成
		for(Map<String, Object> result : resultList) {
			// エンティティをインスタンス化
			User user = new User();
			
			// エンティティに値を設定
			user.setId((Integer)result.get("id"));
			user.setName((String)result.get("name"));
			user.setMail((String)result.get("mail"));
			user.setAge((Integer)result.get("age"));
			
			// エンティティをエンティティリストに挿入
			list.add(user);
		}
		// 全ユーザー情報を返却し、終了する
		return list;
	}
	
	/**
	 * 更新用ユーザーの検索メソッド
	 * @param id
	 * @return 更新するユーザー情報
	 */
	@Override
	public User findOneById(Integer id) 
			throws IncorrectResultSizeDataAccessException {
		
		// 更新用に検索するSQLを作成
		String sql = "SELECT * FROM user WHERE id=:id";
		
		// ユーザー情報のパラメータ設定するマップをインスタンス化
		Map<String, Object> param = new HashMap<>();
		// ユーザー情報の検索条件を設定
		param.put("id", id);
		
		// 更新用のユーザー情報を検索
		Map<String, Object> result = 
				jdbcTemplate.queryForMap(sql, param);

		// 戻り値のエンティティをインスタンス化
		User user = new User();
		
		// エンティティに値を設定
		user.setId((Integer)result.get("id"));
		user.setName((String)result.get("name"));
		user.setMail((String)result.get("mail"));
		user.setAge((Integer)result.get("age"));
		
		// 更新用のユーザー情報を返却し、終了する
		return user;
	}
	
	/**
	 * ユーザー情報登録メソッド
	 * param user
	 * return 登録件数
	 */
	@Override
	public int insert(User user) {
		
		// 登録件数を定義
		int count = 0;
		
		// 登録用SQLを作成
		String sql = "INSERT INTO user(name, mail, age) "
					+ "VALUES(:name, :mail, :age)";
		
		// ユーザー情報のパラメータ設定するマップをインスタンス化
		Map<String, Object> param = new HashMap<>();
		
		// パラメータに登録する値を設定
		param.put("name", user.getName());
		param.put("mail", user.getMail());
		param.put("age", user.getAge());
		
		// 登録を実行
		count = jdbcTemplate.update(sql, param);
		
		// 登録件数を返却し、終了する
		return count;
	}
	
	/**
	 * ユーザー情報更新メソッド
	 * param user
	 * return 更新件数
	 */
	@Override
	public int update(User user) {
		
		// 更新件数を定義
		int count = 0;
		
		// 更新用のSQLを作成
		String sql = "UPDATE user "
					+ "SET name=:name, mail=:mail, age=:age "
					+ "WHERE id=:id";
		
		// ユーザー情報のパラメータ設定するマップをインスタンス化
		Map<String, Object> param = new HashMap<>();
		
		// ユーザー情報の更新条件を設定
		param.put("id", user.getId());
		
		// パラメータに更新する値を設定
		param.put("name", user.getName());
		param.put("mail", user.getMail());
		param.put("age", user.getAge());
		
		// 更新を実行
		count = jdbcTemplate.update(sql, param);
		
		// 更新件数を返却し、終了する
		return count;
	}
	
	/**
	 * ユーザー情報削除メソッド
	 * param id
	 * return 削除件数
	 */
	@Override
	public int delete(Integer id) {
		
		// 削除件数を定義
		int count = 0;
		
		// 削除用のSQLを作成
		String sql = "DELETE FROM user WHERE id=:id";
		
		// ユーザー情報のパラメータを設定するマップをインスタンス化
		Map<String, Object> param = new HashMap<>();
		// パラメータに削除条件を設定
		param.put("id", id);
		
		// 削除を実行
		count = jdbcTemplate.update(sql, param);
		
		// 削除件数を返却し、終了する
		return count;
	}
}