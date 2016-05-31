package com.subang.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.subang.bean.Pagination;
import com.subang.bean.PayArg;
import com.subang.bean.PrepayResult;
import com.subang.bean.RecordDetail;
import com.subang.bean.SearchArg;
import com.subang.domain.Addr;
import com.subang.domain.Goods;
import com.subang.domain.Order.OrderType;
import com.subang.domain.Order.State;
import com.subang.domain.Payment;
import com.subang.domain.Payment.PayType;
import com.subang.domain.Record;
import com.subang.domain.Region;
import com.subang.domain.User;
import com.subang.domain.User.Client;
import com.subang.domain.Worker;
import com.subang.tool.SuException;
import com.subang.util.ComUtil;
import com.subang.util.PushUtil;
import com.subang.util.StratUtil;
import com.subang.util.WebConst;

@Service
public class RecordService extends BaseService {

	@Autowired
	protected UserService userService; // 余额支付，需要生成balance订单

	public List<RecordDetail> searchRecord(SearchArg searchArg, Pagination pagination) {
		List<RecordDetail> recordDetails = null;
		searchArg.pre();
		switch (searchArg.getType()) {
		case WebConst.SEARCH_NULL:
			recordDetails = new ArrayList<RecordDetail>();
			pagination.setRecordnum(0);
			pagination.round();
			break;
		case WebConst.SEARCH_ALL:
		case WebConst.SEARCH_ORDER_STATE:
		case WebConst.SEARCH_ORDER_ORDERNO:
		case WebConst.SEARCH_ORDER_USER_NICKNAME:
		case WebConst.SEARCH_ORDER_USER_CELLNUM:
		case WebConst.SEARCH_ORDER_USERID:
		case WebConst.SEARCH_ORDER_WORKERID:
			recordDetails = recordDao.findDetail(searchArg, pagination);
			break;
		default:
			recordDetails = new ArrayList<RecordDetail>();
		}
		searchArg.after();
		return recordDetails;
	}

	// 用户查找订单
	public List<RecordDetail> searchRecordByUserid(Integer userid) {
		List<RecordDetail> recordDetails = new ArrayList<RecordDetail>();
		recordDetails.addAll(recordDao.findDetailByUseridAndState(userid, State.paid, false));
		recordDetails.addAll(recordDao.findDetailByUseridAndState(userid, State.delivered, true));
		return recordDetails;
	}

	// 取衣员查找订单
	public List<RecordDetail> searchRecordByWorkeridAndState(Integer workerid, int stateType) {
		List<RecordDetail> recordDetails = new ArrayList<RecordDetail>();
		switch (stateType) {
		case WebConst.ORDER_STATE_DELIVER:
			recordDetails.addAll(recordDao
					.findDetailByWorkeridAndState(workerid, State.paid, false));
			break;
		case WebConst.ORDER_STATE_FINISH:
			recordDetails.addAll(recordDao.findDetailByWorkeridAndState(workerid, State.delivered,
					true));
			break;
		}
		return recordDetails;
	}

	// 传入userid,goodsid,addrid
	public Record addRecord(Record record, PayType payType) throws SuException {
		// 检查商品信息
		Goods goods = goodsDao.get(record.getGoodsid());
		if (goods == null || goods.getCount() <= 0) {
			throw new SuException("商品不存在或已经售完。");
		}

		// 检查支付信息
		User user = userDao.get(record.getUserid());
		if (payType == PayType.balance) {
			if (user.getMoney() < goods.getMoney()) {
				throw new SuException("您的余额不足。");
			}
		} else if (payType == PayType.score) {
			if (user.getScore() < goods.getScore()) {
				throw new SuException("您的积分不足。");
			}
		} else {
			throw new SuException("支付方式出错。");
		}

		// 分配取衣员
		Addr addr = addrDao.get(record.getAddrid());
		Region region = regionDao.get(addr.getRegionid());
		Integer workerid = region.getWorkerid();
		if (workerid == null) {
			List<Worker> coreWorkers = workerDao.findByCore();
			workerid = coreWorkers.get(ComUtil.random.nextInt(coreWorkers.size())).getId();
		}
		record.setWorkerid(workerid);

		// 生成订单号,保存订单
		record.setState(State.paid);
		record.setTime(new Timestamp(System.currentTimeMillis()));
		boolean flag;
		do {
			try {
				record.setOrderno(StratUtil.getOrderno(OrderType.record));
				recordDao.save(record);
				flag = false;
			} catch (DuplicateKeyException e) {
				flag = true;
			}
		} while (flag);
		record = recordDao.getByOrderno(record.getOrderno());

		// 支付
		if (payType == PayType.balance) {
			PayArg payArg = new PayArg();
			payArg.setClient(Client.weixin);
			payArg.setPayType(PayType.expense);
			payArg.setMoney(-goods.getMoney());
			PrepayResult result = userService.prepay(payArg, user.getId(), null);
			if (result.getCodeEnum() != PrepayResult.Code.succ) {
				throw new SuException("支付出错。");
			}
		} else if (payType == PayType.score) {
			user.setScore(user.getScore() - goods.getScore());
			userDao.update(user);
		}
		Payment payment = paymentDao.getByOrderno(record.getOrderno());
		payment.setType(payType);
		paymentDao.update(payment);

		// 更改商品信息
		goods.setCount(goods.getCount() - 1);
		goodsDao.update(goods);

		// 向取衣员推送通知
		Worker worker = workerDao.get(workerid);
		PushUtil.send(new String[] { worker.getCellnum() }, OrderType.record);

		return record;
	}

	public void deliverRecord(Integer recordid) {
		Record record = recordDao.get(recordid);
		record.setState(State.delivered);
		recordDao.update(record);
	}

	public void deleteRecords(List<Integer> recordids) {
		for (Integer recordid : recordids) {
			deleteRecord(recordid);
		}
	}

	private void deleteRecord(Integer recordid) {
		Record record = recordDao.get(recordid);
		recordDao.delete(recordid);
		Addr addr = addrDao.get(record.getAddrid());
		if (!addr.getValid() && !userService.isAddrRefed(addr.getId())) {
			addrDao.delete(addr.getId());
		}
	}
}
