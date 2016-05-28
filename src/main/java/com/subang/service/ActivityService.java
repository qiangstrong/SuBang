package com.subang.service;

import java.io.FileInputStream;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.subang.domain.Banner;
import com.subang.domain.Rebate;
import com.subang.domain.TicketCode;
import com.subang.domain.TicketType;
import com.subang.tool.SuException;
import com.subang.util.SuUtil;
import com.subang.util.Validator;
import com.subang.util.WebConst;

@Service
public class ActivityService extends BaseService {

	/**
	 * 优惠券类型
	 */
	public void addTicketType(TicketType ticketType, MultipartFile icon) throws SuException {
		if (icon.isEmpty()) {
			throw new SuException("未选择图标文件。");
		}
		do {
			ticketType.calcIcon(SuUtil.getFilename(icon.getOriginalFilename()));
		} while (SuUtil.fileExist(ticketType.getIcon()));
		try {
			ticketTypeDao.save(ticketType);
		} catch (DuplicateKeyException e) {
			throw new SuException("优惠券名称不能相同。");
		}
		SuUtil.saveMultipartFile(icon, ticketType.getIcon());
	}

	public void modifyTicketType(TicketType ticketType, MultipartFile icon) throws SuException {
		String icon_old = ticketType.getIcon();
		if (!icon.isEmpty()) {
			do {
				ticketType.calcIcon(SuUtil.getFilename(icon.getOriginalFilename()));
			} while (SuUtil.fileExist(ticketType.getIcon()));
		}
		try {
			ticketTypeDao.update(ticketType);
		} catch (DuplicateKeyException e) {
			ticketType.setIcon(icon_old);
			throw new SuException("优惠券名称不能相同。");
		}
		if (!icon.isEmpty()) {
			SuUtil.deleteFile(icon_old);
			SuUtil.saveMultipartFile(icon, ticketType.getIcon());
		}
	}

	public void deleteTicketTypes(List<Integer> ticketTypeids) throws SuException {
		boolean isAll = true;
		for (Integer ticketTypeid : ticketTypeids) {
			try {
				TicketType ticketType = ticketTypeDao.get(ticketTypeid);
				ticketTypeDao.delete(ticketTypeid);
				if (ticketType != null) {
					SuUtil.deleteFile(ticketType.getIcon());
				}
			} catch (DataIntegrityViolationException e) {
				isAll = false;
			}
		}
		if (!isAll) {
			throw new SuException("部分优惠券类型没有成功删除。可能是用户的优惠券引用了这些类型。");
		}
	}

	/**
	 * 优惠码
	 */
	public void addTicketCode(TicketCode ticketCode, MultipartFile codenoFile) throws SuException,
			Exception {
		// 检查截止期
		TicketType ticketType = ticketTypeDao.get(ticketCode.getTicketTypeid());
		if (ticketType == null) {
			throw new SuException("优惠券类型不存在。");
		}
		if (ticketType.getDeadline() != null) {
			if (ticketCode.getEnd().after(ticketType.getDeadline())) {
				throw new SuException("优惠码的截止期不能晚于优惠券的截止期。");
			}
		}
		if (ticketCode.getStart().after(ticketCode.getEnd())) {
			throw new SuException("起始日期不能晚于截止日期。");
		}

		// 临时保存上传的文件
		if (codenoFile.isEmpty()) {
			throw new SuException("未选择优惠码文件。");
		}
		String codenoPath = null;
		do {
			codenoPath = WebConst.TEMP_PATH + SuUtil.getFilename(codenoFile.getOriginalFilename());
		} while (SuUtil.fileExist(codenoPath));
		SuUtil.saveMultipartFile(codenoFile, codenoPath);

		// 依次添加优惠码
		boolean isLenErr = false;
		boolean isKeyErr = false;
		FileInputStream fis = null;
		Workbook workbook = null;
		ticketCode.setValid(true);
		try {
			fis = new FileInputStream(SuUtil.getRealPath(codenoPath));
			workbook = Workbook.getWorkbook(fis);
			Sheet sheet = workbook.getSheet(0);
			int rowNum = sheet.getRows();
			for (int i = 0; i < rowNum; i++) {
				String codeno = sheet.getCell(0, i).getContents();
				if (codeno.length() == 0) {
					continue;
				}
				if (codeno.length() > Validator.CODENO_LENGTH) {
					isLenErr = true;
					continue;
				}
				ticketCode.setCodeno(codeno);
				try {
					ticketCodeDao.save(ticketCode);
				} catch (DuplicateKeyException e) {
					isKeyErr = true;
				}
			}
		} finally {
			if (fis != null) {
				fis.close();
			}
			if (workbook != null) {
				workbook.close();
			}
		}

		// 如果出错，抛出异常
		String msg = "";
		if (isLenErr) {
			msg = "优惠码长度不能超过" + Validator.CELLNUM_LENGTH + "位。";
		}
		if (isKeyErr) {
			msg = msg + "优惠码不能重复。";
		}
		if (isLenErr | isKeyErr) {
			throw new SuException("部分优惠码没有成功添加。" + msg);
		}
	}

	public void deleteTicketCodes(List<Integer> ticketCodeids) {
		for (Integer ticketCodeid : ticketCodeids) {
			ticketCodeDao.delete(ticketCodeid);
		}
	}

	/**
	 * 横幅
	 */
	public void addBanner(Banner banner, MultipartFile icon) throws SuException {
		if (icon.isEmpty()) {
			throw new SuException("未选择图标文件。");
		}
		do {
			banner.calcIcon(SuUtil.getFilename(icon.getOriginalFilename()));
		} while (SuUtil.fileExist(banner.getIcon()));
		bannerDao.save(banner);
		SuUtil.saveMultipartFile(icon, banner.getIcon());
	}

	public void modifyBanner(Banner banner, MultipartFile icon) throws SuException {
		String icon_old = banner.getIcon();
		if (!icon.isEmpty()) {
			do {
				banner.calcIcon(SuUtil.getFilename(icon.getOriginalFilename()));
			} while (SuUtil.fileExist(banner.getIcon()));
		}
		bannerDao.update(banner);
		if (!icon.isEmpty()) {
			SuUtil.deleteFile(icon_old);
			SuUtil.saveMultipartFile(icon, banner.getIcon());
		}
	}

	public void deleteBanners(List<Integer> bannerids) {
		for (Integer bannerid : bannerids) {
			Banner banner = bannerDao.get(bannerid);
			bannerDao.delete(bannerid);
			if (banner != null) {
				SuUtil.deleteFile(banner.getIcon());
			}
		}
	}

	/**
	 * 折扣
	 */
	public void addRebate(Rebate rebate) throws SuException {
		try {
			rebateDao.save(rebate);
		} catch (DuplicateKeyException e) {
			throw new SuException("金额不能相同。");
		}
	}

	public void modifyRebate(Rebate rebate) throws SuException {
		try {
			rebateDao.update(rebate);
		} catch (DuplicateKeyException e) {
			throw new SuException("金额不能相同。");
		}
	}

	public void deleteRebates(List<Integer> rebateids) {
		for (Integer rebateid : rebateids) {
			rebateDao.delete(rebateid);
		}
	}

	/**
	 * 数据清理
	 */
	public void clear() {
		ticketDao.deleteInvalid();
		ticketCodeDao.deleteInvalid();
		ticketTypeDao.deleteInvalid();
	}
}
