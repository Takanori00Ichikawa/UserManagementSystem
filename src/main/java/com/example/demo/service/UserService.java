package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.User;
import com.example.demo.repository.IUserDao;

/**
 * 実践課題その２のユーザー情報のサービスクラス
 */
@Service
@Transactional
public class UserService {
	
	/** DI対象 */
	@Autowired
	private IUserDao dao;
	
	/**
	 * ユーザー情報の全件検索メソッド
	 * @return 全ユーザー情報
	 */
	public List<User> findAll() {
		// リポジトリークラスの全件検索メソッドを呼び出す
		// 取得したユーザー情報を返却し、終了する
		return dao.findAll();
	}
	
	/**
	 * 更新用ユーザー情報の検索メソッド
	 * @param id
	 * @return 更新対象のユーザー情報
	 */
	public User findOneById(Integer id) {
		try {
			// リポジトリークラスの更新用の検索メソッドを呼び出す
			// 検索したユーザー情報を返却し、終了する
			return dao.findOneById(id);
		
		// 例外処理	
		} catch(IncorrectResultSizeDataAccessException e) {
			// nullを返却し、終了する
			return null;
		}	
	}
	
	/**
	 * ユーザー情報登録メソッド
	 * @param user
	 * @return 登録件数
	 */
	public int insert(User user) {
		// リポジトリークラスの登録用メソッドを呼び出す
		// ユーザー情報の登録件数を返却し、終了する
		return dao.insert(user);
	}
	
	/**
	 * ユーザー情報更新メソッド
	 * @param user
	 * @return 更新件数
	 */
	public int update(User user) {
		// リポジトリークラスの更新用メソッドを呼び出す
		// ユーザー情報の更新件数を返却し、終了する
		return dao.update(user);
	}
	
	/**
	 * ユーザー情報削除メソッド
	 * @param id
	 * @return 削除件数
	 */
	public int delete(Integer id) {
		// リポジトリークラスの削除用メソッドを呼び出す
		// ユーザー情報の削除件数を返却し、終了する
		return dao.delete(id);
	}
}
