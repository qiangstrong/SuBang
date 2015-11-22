package com.subang.bean;

import java.sql.Date;

import com.subang.domain.Order;
import com.subang.domain.Payment.PayType;
import com.subang.domain.face.Filter;
import com.subang.util.ComUtil;

//使用json在网络中传输时，所有没有返回null的getter方法都会被传输
public class OrderDetail extends Order implements Filter {

	private static final long serialVersionUID = 1L;

	private String categoryname;
	private String cellnum;
	private Integer payType;
	private Double moneyTicket;
	private String addrname; // 订单地址中填写的姓名
	private String addrcellnum; // 订单地址中填写的手机号
	private String cityname;
	private String districtname;
	private String regionname;
	private String addrdetail;
	private String workername;
	private String workercellnum;

	public OrderDetail() {
	}

	public OrderDetail(Integer id, String orderno, Integer state, Double money, Double freight,
			Date date, Integer time, String userComment, String workerComment, String remark,
			String barcode, Integer categoryid, Integer userid, Integer addrid, Integer workerid,
			Integer laundryid, String categoryname, String cellnum, Integer payType,
			Double moneyTicket, String addrname, String addrcellnum, String cityname,
			String districtname, String regionname, String addrdetail, String workername,
			String workercellnum) {
		super(id, orderno, state, money, freight, date, time, userComment, workerComment, remark,
				barcode, categoryid, userid, addrid, workerid, laundryid);
		this.categoryname = categoryname;
		this.cellnum = cellnum;
		this.payType = payType;
		this.moneyTicket = moneyTicket;
		this.addrname = addrname;
		this.addrcellnum = addrcellnum;
		this.cityname = cityname;
		this.districtname = districtname;
		this.regionname = regionname;
		this.addrdetail = addrdetail;
		this.workername = workername;
		this.workercellnum = workercellnum;
	}

	public String getCategoryname() {
		return categoryname;
	}

	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}

	public String getCellnum() {
		return cellnum;
	}

	public void setCellnum(String cellnum) {
		this.cellnum = cellnum;
	}

	public Integer getPayType() {
		return payType;
	}

	public PayType getPayTypeEnum() {
		if (payType == null) {
			return null;
		}
		return PayType.values()[payType];
	}

	public String getPayTypeDes() {
		if (payType == null) {
			return "未支付";
		}
		String description = null;
		switch (getPayTypeEnum()) {
		case balance:
			description = "余额";
			break;
		case weixin:
			description = "微信";
			break;
		case alipay:
			description = "支付宝";
			break;
		}
		return description;
	}

	public String getPaymentDes() {
		if (money == null || freight == null) {
			return "未确定";
		}
		String description = "";
		description += "订单￥" + getMoneyDes() + "+";
		description += "运费￥" + getFreightDes() + "-";
		description += "优惠券￥" + getMoneyTicket();
		return description;
	}

	public Double getTotalMoney() {
		Double totalMoney;
		if (money == null || freight == null) {
			totalMoney = null;
		} else {
			totalMoney = money + freight;
		}
		return ComUtil.round(totalMoney);
	}

	public String getTotalMoneyDes() {
		return ComUtil.getDes(getTotalMoney());
	}

	public Double getActualMoney() {
		Double actualMoney;
		if (money == null || freight == null || moneyTicket == null) {
			actualMoney = null;
		} else {
			actualMoney = money + freight - moneyTicket;
		}
		return ComUtil.round(actualMoney);
	}

	public String getActualMoneyDes() {
		return ComUtil.getDes(getActualMoney());
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public Double getMoneyTicket() {
		return moneyTicket;
	}

	public void setMoneyTicket(Double moneyTicket) {
		this.moneyTicket = moneyTicket;
	}

	public String getAddrname() {
		return addrname;
	}

	public void setAddrname(String addrname) {
		this.addrname = addrname;
	}

	public String getAddrcellnum() {
		return addrcellnum;
	}

	public void setAddrcellnum(String addrcellnum) {
		this.addrcellnum = addrcellnum;
	}

	public String getCityname() {
		return cityname;
	}

	public void setCityname(String cityname) {
		this.cityname = cityname;
	}

	public String getDistrictname() {
		return districtname;
	}

	public void setDistrictname(String districtname) {
		this.districtname = districtname;
	}

	public String getRegionname() {
		return regionname;
	}

	public void setRegionname(String regionname) {
		this.regionname = regionname;
	}

	public String getAddrdetail() {
		return addrdetail;
	}

	public void setAddrdetail(String addrdetail) {
		this.addrdetail = addrdetail;
	}

	public String getWorkername() {
		return workername;
	}

	public void setWorkername(String workername) {
		this.workername = workername;
	}

	public String getWorkercellnum() {
		return workercellnum;
	}

	public void setWorkercellnum(String workercellnum) {
		this.workercellnum = workercellnum;
	}

	public void doFilter(Object object) {
		super.doFilter(object);
		OrderDetail orderDetail = (OrderDetail) object;
		if (this.categoryname == null) {
			orderDetail.categoryname = null;
		}
		if (this.cellnum == null) {
			orderDetail.cellnum = null;
		}
		if (this.payType == null) {
			orderDetail.payType = null;
		}
		if (this.moneyTicket == null) {
			orderDetail.moneyTicket = null;
		}
		if (this.addrname == null) {
			orderDetail.addrname = null;
		}
		if (this.addrcellnum == null) {
			orderDetail.addrcellnum = null;
		}
		if (this.cityname == null) {
			orderDetail.cityname = null;
		}
		if (this.districtname == null) {
			orderDetail.districtname = null;
		}
		if (this.regionname == null) {
			orderDetail.regionname = null;
		}
		if (this.addrdetail == null) {
			orderDetail.addrdetail = null;
		}
		if (this.workername == null) {
			orderDetail.workername = null;
		}
		if (this.workercellnum == null) {
			orderDetail.workercellnum = null;
		}
	}
}
