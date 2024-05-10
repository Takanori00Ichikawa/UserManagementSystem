package com.example.demo.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.User;
import com.example.demo.form.UserForm;
import com.example.demo.service.UserService;
import com.example.demo.validation.UserValidation;


/**
 * 実践課題その２のユーザー情報のコントローラークラス
 */
@Controller
@RequestMapping
public class UserController {
	
	/** DI対象 */
	@Autowired
	private UserService service;
	
	/**
	 * UserFormクラスの初期化
	 * @return フォーム
	 */
	@ModelAttribute
	public UserForm setUpForm() {
		UserForm form = new UserForm();
		return form;
	}

	/**
	 * 一覧画面表示メソッド
	 * @param model
	 * @return 一覧画面
	 */
	@GetMapping
	public String showList(Model model) {
		// ユーザー情報を全件検索するメソッドを呼び出す
		List<User> userList = service.findAll();

		// ユーザー情報が空の場合
		if (userList.isEmpty()) {
			// ユーザー情報がない場合のメッセージをモデルに設定
			model.addAttribute("emptyMsg", "ユーザー情報はありません。");
		} 

		// 一覧画面表示のモデルを設定
		model.addAttribute("title", "一覧画面");
		model.addAttribute("userList", userList);

		return "main";
	}
	
	/**
	 * 登録(初期)画面メソッド
	 * @param model
	 * @return 登録画面
	 */
	@GetMapping("ins")
	public String showForm(Model model) {
		
		// 登録画面に表示するモデルを設定
		model.addAttribute("title", "登録画面");
		model.addAttribute("formTitle", "新規登録");
		model.addAttribute("connect", "/ins");
		model.addAttribute("btn", "登録");
		
		return "create_update";
	}
	
	/**
	 * 登録メソッド
	 * @param userForm
	 * @param model
	 * @param redirectAttributes
	 * @return 登録画面(エラーがある場合), 一覧画面(成功)
	 */
	@PostMapping("ins")
	public String insert(UserForm userForm, 
			Model model, 
			RedirectAttributes redirectAttributes) {
		
		// バリデーションクラスをインスタンス化
		UserValidation validate = new UserValidation();
		
		// エラーを検証するメソッドを呼び出す
		Map<String,String> hasError = validate.validate(userForm);
		
		// エラーがある場合
		if (!(hasError.isEmpty())) {
			// 登録画面に表示するモデルを設定
			model.addAttribute("title", "登録画面");
			model.addAttribute("formTitle", "新規登録");
			model.addAttribute("connect", "/ins");
			model.addAttribute("btn", "登録");
			
			// 登録画面に入力された値をモデルに設定
			model.addAttribute("UserForm", userForm);
			
			// エラーメッセージのモデルを設定 
			model.addAttribute("nameErrorMsg1", hasError.get("nameErrorMsg1"));
			model.addAttribute("nameErrorMsg2", hasError.get("nameErrorMsg2"));
			model.addAttribute("mailErrorMsg1", hasError.get("mailErrorMsg1"));
			model.addAttribute("mailErrorMsg2", hasError.get("mailErrorMsg2"));
			model.addAttribute("ageErrorMsg1", hasError.get("ageErrorMsg1"));
			model.addAttribute("ageErrorMsg2", hasError.get("ageErrorMsg2"));
			
			return "create_update";
			
		// エラーがない場合	
		} else {
			// ユーザー情報を登録するメソッドを呼び出す
			int count = service.insert(makeUser(userForm));
			
			// 登録件数が1件の場合(登録成功)
			if (count == 1) {
				// 登録成功メッセージをリダイレクトに設定
				redirectAttributes.addFlashAttribute("complete", "登録は完了しました。");
				
				return "redirect:";
			
			// 登録件数が1件ではない場合(登録失敗)
			} else {
				// 登録失敗メッセージをリダイレクトに設定
				redirectAttributes.addFlashAttribute("faileComp", "登録完了出来ませんでした。");
				
				return "redirect:/ins";
			}
		}
	}
	
	/**
	 * 更新(初期)画面メソッド
	 * @param model
	 * @param userForm
	 * @param id
	 * @return 更新画面
	 */
	@GetMapping("/{id}")
	public String showForm(UserForm userForm, Model model, 
			@PathVariable Integer id) {
		
		// 更新用ユーザー情報の検索メソッドを呼び出す
		Optional<User> userOpt = Optional.ofNullable(service.findOneById(id));
		// UserFormへ詰め直し
		Optional<UserForm> formOpt = userOpt.map(t -> makeUserForm(t));
		
		// ユーザー情報を検索できた場合
		if (formOpt.isPresent()) {
			// UserFormに値を設定
			userForm = formOpt.get();
		}
		// 更新画面に表示するモデルを設定
		model.addAttribute("title", "更新画面");
		model.addAttribute("formTitle", "更新編集");
		model.addAttribute("connect", "/update");
		model.addAttribute("btn", "更新");
		
		// 更新するユーザー情報をモデルに設定
		model.addAttribute("userForm", userForm);
		
		return "create_update";
	}
	
	/**
	 * 更新メソッド
	 * @param userForm
	 * @param model
	 * @param redirectAttributes
	 * @return 更新画面(エラーがある場合), 一覧画面(成功)
	 */
	@PostMapping("/update")
	public String update(UserForm userForm,
			Model model, 
			RedirectAttributes redirectAttributes) {
		
		// バリデーションクラスをインスタンス化
		UserValidation validate = new UserValidation();
		
		// エラーを検証するメソッドを呼び出す
		Map<String,String> hasError = validate.validate(userForm);
		
		// エラーがある場合
		if (!(hasError.isEmpty())) {
			// 更新画面に表示するモデルを設定
			model.addAttribute("title", "更新画面");
			model.addAttribute("formTitle", "更新編集");
			model.addAttribute("connect", "/update");
			model.addAttribute("btn", "更新");
			
			// 更新画面で入力された値をモデルに設定
			model.addAttribute("userFrom", userForm);
			
			// エラーメッセージをモデルに設定
			model.addAttribute("nameErrorMsg1", hasError.get("nameErrorMsg1"));
			model.addAttribute("nameErrorMsg2", hasError.get("nameErrorMsg2"));
			model.addAttribute("mailErrorMsg1", hasError.get("mailErrorMsg1"));
			model.addAttribute("mailErrorMsg2", hasError.get("mailErrorMsg2"));
			model.addAttribute("ageErrorMsg1", hasError.get("ageErrorMsg1"));
			model.addAttribute("ageErrorMsg2", hasError.get("ageErrorMsg2"));
			
			return "create_update";
			
		// エラーがない場合	
		} else {
			// ユーザー情報更新メソッドを呼び出す
			int count = service.update(makeUser(userForm));
			
			// 更新件数が1件の場合(更新成功)
			if (count == 1) {
				// 更新成功メッセージをリダイレクトに設定
				redirectAttributes.addFlashAttribute("complete", "id:" + userForm.getId() + "更新は完了しました。");
	
				return "redirect:"; 
				
			// 更新件数が1件ではない場合(更新失敗)	
			} else {
				// 更新失敗メッセージをリダイレクトに設定
				redirectAttributes.addFlashAttribute("faileComp", "id:" + userForm.getId() + "更新完了出来ませんでした。");
				
				return "redirect:/" + userForm.getId();
			}
			
		}
	}
	
	/**
	 * 削除メソッド
	 * @param model
	 * @param id
	 * @param redirectAttributes
	 * @return 一覧画面
	 */
	@GetMapping("/delete")
	public String delete(Model model, @RequestParam Integer id,
			RedirectAttributes redirectAttributes) {
		
		// ユーザー情報削除メソッドを呼び出す
		int count = service.delete(id);
		
		// 削除件数が1件の場合(削除成功)
		if (count == 1) {
			// 削除成功メッセージをリダイレクトに設定
			redirectAttributes.addFlashAttribute("complete", "id:"+ id + "の削除は完了しました。");
		
		// 削除件数が1件ではない場合(削除失敗)
		} else {
			// 削除失敗メッセージをリダイレクトに設定
			redirectAttributes.addFlashAttribute("complete", "id:"+ id + "の削除は出来ませんでした。");
		}
		
		return "redirect:";
	}
	
	/**
	 * エンティティに値を設定するメソッド
	 * @param userForm
	 * @return user
	 */
	private User makeUser(UserForm userForm) {
		// エンティティをインスタンス化
		User user = new User();
		
		// フォームからエンティティに詰め直す
		user.setId(userForm.getId());
		user.setName(userForm.getName());
		user.setMail(userForm.getMail());
		user.setAge(userForm.getAge());
		
		return user;
	}
	
	/**
	 * フォームに値を設定するメソッド
	 * @param user
	 * @return userForm
	 */
	private UserForm makeUserForm(User user) {
		// フォームをインスタンス化
		UserForm userForm = new UserForm();
		
		// エンティティからフォームに詰め直す
		userForm.setId(user.getId());
		userForm.setName(user.getName());
		userForm.setMail(user.getMail());
		userForm.setAge(user.getAge());
		
		return userForm;
	}
}
