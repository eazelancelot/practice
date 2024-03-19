package com.example.practice.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.practice.constants.RtnCode;
import com.example.practice.entity.Atm;
import com.example.practice.repository.AtmDao;
import com.example.practice.service.ifs.AtmService;
import com.example.practice.vo.AtmRes;

@Service
public class AtmServiceImpl implements AtmService {
	
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
	@Autowired
	private AtmDao atmDao;

	@Override
	public AtmRes addInfo(String account, String pwd, int amount) {
//		if(!StringUtils.hasText(account) || !StringUtils.hasText(pwd) || amount < 0) {
//			System.out.println("帳號或密碼格式錯誤!!");
//			return;
//		}
		RtnCode res = checkParams(account, pwd, amount);//此方法把上述的檢查隱藏起來
		if (res != null) {
			return new AtmRes(res, new Atm(account));
		}
		if(atmDao.existsById(account)) {
			return new AtmRes(RtnCode.ACCOUNT_EXISTS, new Atm(account));
		}
		// 新建出的 Atm 裡面的 pwd 要加密後再存進DB
		atmDao.save(new Atm(account, encoder.encode(pwd), amount));
		return new AtmRes(RtnCode.SUCCESS, new Atm(account));
	}

	@Override
	public AtmRes getAmountByAccount(String account, String pwd) {
		RtnCode res = checkParams(account, pwd);//此方法把上述的檢查隱藏起來
		if (res != null) {
			return new AtmRes(res, new Atm(account));
		}
		AtmRes checkRes = checkAccountAndPwd(account, pwd);
		if (checkRes.getRtnCode().getCode() != 200) {
			return checkRes;
		}
		Atm atm = checkRes.getAtm();
		return new AtmRes(RtnCode.SUCCESS, new Atm(account, null, atm.getAmount()));
	}

	@Override
	public AtmRes updatePassword(String account, String oldPwd, String newPwd) {
		RtnCode res = checkParams(account, oldPwd, newPwd); //此方法把上述的檢查隱藏起來
		if (res != null) {
			return new AtmRes(res, new Atm(account));
		}
		AtmRes checkRes = checkAccountAndPwd(account, oldPwd);
		if (checkRes.getRtnCode().getCode() != 200) {
			return checkRes;
		}
		Atm atm = checkRes.getAtm();
		// 3. 更換新密碼，要將新密碼加密
		atm.setPwd(encoder.encode(newPwd));
		// 4. 將資料更新回 DB
		atmDao.save(atm);
		return new AtmRes(RtnCode.SUCCESS, new Atm(account));
	}

	@Override
	public AtmRes deposit(String account, String pwd, int amount) {
//		RtnCode res = checkParams(account, pwd, amount);
//		if (res != null) {
//			return new AtmRes(res, new Atm(account));
//		}
//		AtmRes checkRes = checkAccountAndPwd(account, pwd);
//		if (checkRes.getRtnCode().getCode() != 200) {
//			return checkRes;
//		}
//		Atm atm = checkRes.getAtm();
//		// 3. 存錢(原本的餘額+要存的金額)
//		atm.setAmount(atm.getAmount() + amount);
//		atmDao.save(atm);
//		return new AtmRes(RtnCode.SUCCESS, new Atm(account, null, atm.getAmount()));
		return operation(account, pwd, amount, false);
	}

	@Override
	public AtmRes withdraw(String account, String pwd, int amount) {
//		RtnCode res = checkParams(account, pwd, amount);
//		if (res != null) {
//			return new AtmRes(res, new Atm(account));
//		}
//		AtmRes checkRes = checkAccountAndPwd(account, pwd);
//		if (checkRes.getRtnCode().getCode() != 200) {
//			return checkRes;
//		}
//		Atm atm = checkRes.getAtm();
//		// 3. 確認餘額是否足夠
//		if(atm.getAmount() < amount) {
//			return new AtmRes(RtnCode.INSUFFICIENT_BALANCE, new Atm(account));
//		}
//		// 4. 提款(原本的金額 - 提款金額)
//		atm.setAmount(atm.getAmount() - amount);
//		atmDao.save(atm);
//		return new AtmRes(RtnCode.SUCCESS, new Atm(account, null, atm.getAmount()));
		return operation(account, pwd, amount, true);
	}
	
	private AtmRes operation(String account, String pwd, int amount, boolean isWithdraw) {
		RtnCode res = checkParams(account, pwd, amount);
		if (res != null) {
			return new AtmRes(res, new Atm(account));
		}
		AtmRes checkRes = checkAccountAndPwd(account, pwd);
		if (checkRes.getRtnCode().getCode() != 200) {
			return checkRes;
		}
		Atm atm = checkRes.getAtm();
		if (isWithdraw) {
			//3. 檢查餘額是否足夠
			if(atm.getAmount() < amount) {
				return new AtmRes(RtnCode.INSUFFICIENT_BALANCE, new Atm(account));
			}
			// 4. 提款(原本的金額 - 提款金額)
			atm.setAmount(atm.getAmount() - amount);
		} else {
			atm.setAmount(atm.getAmount() + amount);
		}
		atmDao.save(atm);
		return new AtmRes(RtnCode.SUCCESS, new Atm(account, null, atm.getAmount()));
	}
	
	/**
	 * 1. 檢查 account, pwd 是否為 null 或是空字串或是全空白字串<br>
	 * 1.1 角括號裡面放 br 表示斷行，html 標籤語法<br>
	 * 2. 檢查 amount 是否為負數
	 **/
	private RtnCode checkParams(String account, String pwd, int amount) {			
		if(amount < 0) {
			return RtnCode.INPUT_INFO_ERROR;
		}
		return checkParams(account, pwd);
	}
	
	private RtnCode checkParams(String ...strs) {
		for(String item : strs) {
			if (!StringUtils.hasText(item)) {
				return RtnCode.INPUT_INFO_ERROR;
			}
		}
		return null; // return null 表示參數格式確認無誤
	}
	
	/**
	 * 1. 確認帳號是否存在<br>
	 * 2. 帳號存在再確認密碼是否正確
	 */
	private AtmRes checkAccountAndPwd(String account, String pwd) {
		Optional<Atm> op = atmDao.findById(account);
		if(op.isEmpty()) {
			return new AtmRes(RtnCode.ACCOUNT_NOT_FOUND, new Atm(account));
		}
		Atm atm = op.get(); // atm 是從資料庫取出的資料，所以密碼會是密文
		// 2. 檢查密碼
		// encoder.matches(明文, 加密後的密文)
		if(!encoder.matches(pwd, atm.getPwd())) {
			return new AtmRes(RtnCode.PASSWORD_ERROR, new Atm(account));
		}
		return new AtmRes(RtnCode.SUCCESS, atm);
	}

}
