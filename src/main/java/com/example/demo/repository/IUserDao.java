package com.example.demo.repository;

import java.util.List;

import com.example.demo.entity.User;

/**
 * リポジトリークラスのインターフェース
 */
public interface IUserDao {

	/*
	 * ユーザー情報の全件取得メソッド
	 */
	List<User> findAll();
	
	// 更新用ユーザー情報の検索メソッド
	User findOneById(Integer id);
	
	// ユーザー情報登録メソッド
	int insert(User user);
	
	// ユーザー情報更新メソッド
	int update(User user);
	
	// ユーザー情報削除メソッド
	int delete(Integer id);
}
