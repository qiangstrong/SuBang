package com.subang.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.subang.bean.SearchArg;
import com.subang.domain.Faq;
import com.subang.domain.Feedback;
import com.subang.domain.Info;
import com.subang.exception.SuException;
import com.subang.util.WebConst;

@Service
public class InfoService extends BaseService {
	/**
	 * 与产品运营相关的操作
	 */
	public Info getInfo() {
		return infoDao.findALL().get(0);
	}

	public void modifyInfo(Info info) {
		infoDao.update(info);
	}

	/**
	 * faq
	 */
	public List<Faq> searchFaq(SearchArg searchArg) {
		List<Faq> faqs = null;
		switch (searchArg.getType()) {
		case WebConst.SEARCH_NULL:
			faqs = new ArrayList<Faq>();
			break;
		case WebConst.SEARCH_ALL:
			faqs = faqDao.findAll();
			break;
		case WebConst.SEARCH_NAME:
			faqs = faqDao.findByQuestion(searchArg.getArg());
			break;
		}
		return faqs;
	}

	public void addFaq(Faq faq) throws SuException {
		try {
			faqDao.save(faq);
		} catch (DuplicateKeyException e) {
			throw new SuException("问题不能相同。");
		}
	}

	public void modifyFaq(Faq faq) throws SuException {
		try {
			faqDao.update(faq);
		} catch (DuplicateKeyException e) {
			throw new SuException("问题不能相同。");
		}
	}

	public void deleteFaqs(List<Integer> faqids) {
		for (Integer faqid : faqids) {
			faqDao.delete(faqid);
		}
	}

	/**
	 * 反馈
	 */
	public void addFeedback(String comment) {
		Feedback feedback = new Feedback();
		feedback.setComment(comment);
		feedbackDao.save(feedback);
	}

	public void deleteFeedbacks(List<Integer> feedbackids) {
		for (Integer feedbackid : feedbackids) {
			feedbackDao.delete(feedbackid);
		}
	}

	/**
	 * 通知
	 */
	public void deleteNotices(List<Integer> noticeids) {
		for (Integer noticeid : noticeids) {
			noticeDao.delete(noticeid);
		}
	}
}
