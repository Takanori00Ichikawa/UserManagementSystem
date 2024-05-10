package com.example.demo.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  入力値を格納する為のクラス
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserForm {
	/** ユーザーID */
	private Integer id;
	
	/** ユーザーネーム */
	private String name;
	
	/** ユーザーのメールアドレス */
	private String mail;
	
	/** ユーザーの年齢 */
	private Integer age;
}
