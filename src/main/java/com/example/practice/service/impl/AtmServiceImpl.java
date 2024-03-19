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
//			System.out.println("�b���αK�X�榡���~!!");
//			return;
//		}
		RtnCode res = checkParams(account, pwd, amount);//����k��W�z���ˬd���ð_��
		if (res != null) {
			return new AtmRes(res, new Atm(account));
		}
		if(atmDao.existsById(account)) {
			return new AtmRes(RtnCode.ACCOUNT_EXISTS, new Atm(account));
		}
		// �s�إX�� Atm �̭��� pwd �n�[�K��A�s�iDB
		atmDao.save(new Atm(account, encoder.encode(pwd), amount));
		return new AtmRes(RtnCode.SUCCESS, new Atm(account));
	}

	@Override
	public AtmRes getAmountByAccount(String account, String pwd) {
		RtnCode res = checkParams(account, pwd);//����k��W�z���ˬd���ð_��
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
		RtnCode res = checkParams(account, oldPwd, newPwd); //����k��W�z���ˬd���ð_��
		if (res != null) {
			return new AtmRes(res, new Atm(account));
		}
		AtmRes checkRes = checkAccountAndPwd(account, oldPwd);
		if (checkRes.getRtnCode().getCode() != 200) {
			return checkRes;
		}
		Atm atm = checkRes.getAtm();
		// 3. �󴫷s�K�X�A�n�N�s�K�X�[�K
		atm.setPwd(encoder.encode(newPwd));
		// 4. �N��Ƨ�s�^ DB
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
//		// 3. �s��(�쥻���l�B+�n�s�����B)
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
//		// 3. �T�{�l�B�O�_����
//		if(atm.getAmount() < amount) {
//			return new AtmRes(RtnCode.INSUFFICIENT_BALANCE, new Atm(account));
//		}
//		// 4. ����(�쥻�����B - ���ڪ��B)
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
			//3. �ˬd�l�B�O�_����
			if(atm.getAmount() < amount) {
				return new AtmRes(RtnCode.INSUFFICIENT_BALANCE, new Atm(account));
			}
			// 4. ����(�쥻�����B - ���ڪ��B)
			atm.setAmount(atm.getAmount() - amount);
		} else {
			atm.setAmount(atm.getAmount() + amount);
		}
		atmDao.save(atm);
		return new AtmRes(RtnCode.SUCCESS, new Atm(account, null, atm.getAmount()));
	}
	
	/**
	 * 1. �ˬd account, pwd �O�_�� null �άO�Ŧr��άO���ťզr��<br>
	 * 1.1 ���A���̭��� br ����_��Ahtml ���һy�k<br>
	 * 2. �ˬd amount �O�_���t��
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
		return null; // return null ��ܰѼƮ榡�T�{�L�~
	}
	
	/**
	 * 1. �T�{�b���O�_�s�b<br>
	 * 2. �b���s�b�A�T�{�K�X�O�_���T
	 */
	private AtmRes checkAccountAndPwd(String account, String pwd) {
		Optional<Atm> op = atmDao.findById(account);
		if(op.isEmpty()) {
			return new AtmRes(RtnCode.ACCOUNT_NOT_FOUND, new Atm(account));
		}
		Atm atm = op.get(); // atm �O�q��Ʈw���X����ơA�ҥH�K�X�|�O�K��
		// 2. �ˬd�K�X
		// encoder.matches(����, �[�K�᪺�K��)
		if(!encoder.matches(pwd, atm.getPwd())) {
			return new AtmRes(RtnCode.PASSWORD_ERROR, new Atm(account));
		}
		return new AtmRes(RtnCode.SUCCESS, atm);
	}

}
