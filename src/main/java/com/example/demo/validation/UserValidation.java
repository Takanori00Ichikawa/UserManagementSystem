package com.example.demo.validation;

import java.util.HashMap;
import java.util.Map;

import com.example.demo.form.UserForm;

/**
 * バリデーションクラスクラス
 */
public class UserValidation {
	
	/**
	 * エラーチェック
	 * @param userForm
	 * @return エラーメッセージを設定するマップ
	 */
	public Map<String, String> validate(UserForm userForm) {
		
		// エラーマップをインスタンス化
		Map<String, String> hasError = new HashMap<>();
		
		// メールの整合性を確認
		String mailRegex = "^[a-zA-Z0-9_.+-]+@([a-zA-Z0-9][a-zA-Z0-9-]*[a-zA-Z0-9]*\\.)+[a-zA-Z]{2,}$"; 
		
		// 名前が未入力の場合
		if (userForm.getName().isEmpty()) {
			// エラーメッセージを設定
			hasError.put("nameErrorMsg1", "名前が未入力です。");
		}
		
		// 名前が50文字を超えた場合
		if (!(userForm.getName().length() > 0 && userForm.getName().length() <= 50)) {
			// エラーメッセージを設定
			hasError.put("nameErrorMsg2", "名前は50文字以内にして下さい。");
		}
		
		// メールアドレスが未入力の場合
		if (userForm.getMail().isEmpty()) {
			// エラーメッセージを設定
			hasError.put("mailErrorMsg1", "メールアドレスが未入力です。");
		}
		
		// メールアドレスの形式に合っていない場合
		if (!(userForm.getMail().matches(mailRegex))) {
			// エラーメッセージを設定
			hasError.put("mailErrorMsg2", "メールアドレスの形式に合っていません。");
		}
		
		// 年齢が未入力の場合
		if (userForm.getAge() == null) {
			// エラーメッセージを設定
			hasError.put("ageErrorMsg1", "年齢が未入力です。");
		}
		
		// 年齢が0～110の間で入力されない場合
		if (!(userForm.getAge() != null && userForm.getAge() >= 0 && userForm.getAge() <= 110)) {
			// エラーメッセージを設定
			hasError.put("ageErrorMsg2", "年齢は0～110の間で設定して下さい。");
		}

		return hasError;
	}
	
}
