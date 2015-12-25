package com.subang.service;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.subang.domain.Banner;
import com.subang.domain.Rebate;
import com.subang.domain.TicketType;
import com.subang.tool.SuException;
import com.subang.util.SuUtil;

@Service
public class ActivityService extends BaseService {

	/**
	 * 优惠券类型
	 */
	public void addTicketType(TicketType ticketType, MultipartFile icon) throws SuException {
		if (icon.isEmpty()) {
			throw new SuException("未选择图标文件。");
		}
		ticketType.calcIcon(icon.getOriginalFilename());
		if (SuUtil.imageExists(ticketType.getIcon())) {
			throw new SuException("文件重名。");
		}
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
			ticketType.calcIcon(icon.getOriginalFilename());
			if (!icon_old.equals(ticketType.getIcon())) {
				if (SuUtil.imageExists(ticketType.getIcon())) {
					ticketType.setIcon(icon_old);
					throw new SuException("文件重名。");
				}
			}
		}
		try {
			ticketTypeDao.update(ticketType);
		} catch (DuplicateKeyException e) {
			throw new SuException("优惠券名称不能相同。");
		}
		if (!icon.isEmpty()) {
			if (!icon_old.equals(ticketType.getIcon())) {
				SuUtil.deleteFile(icon_old);
			}
			SuUtil.saveMultipartFile(icon, ticketType.getIcon());
		}
	}

	public void deleteTicketTypes(List<Integer> ticketTypeids) throws SuException {
		boolean isAll = true;
		for (Integer ticketTypeid : ticketTypeids) {
			try {
				ticketTypeDao.delete(ticketTypeid);
				SuUtil.deleteFile(ticketTypeDao.get(ticketTypeid).getIcon());
			} catch (DataIntegrityViolationException e) {
				isAll = false;
			}
		}
		if (!isAll) {
			throw new SuException("部分优惠券类型没有成功删除。可能是用户的优惠券引用了这些类型。");
		}
	}

	/**
	 * 横幅
	 */
	public void addBanner(Banner banner, MultipartFile icon) throws SuException {
		if (icon.isEmpty()) {
			throw new SuException("未选择图标文件。");
		}

		banner.calcIcon(icon.getOriginalFilename());
		if (SuUtil.imageExists(banner.getIcon())) {
			throw new SuException("文件重名。");
		}
		bannerDao.save(banner);
		SuUtil.saveMultipartFile(icon, banner.getIcon());
	}

	public void modifyBanner(Banner banner, MultipartFile icon) throws SuException {
		String icon_old = banner.getIcon();
		if (!icon.isEmpty()) {
			banner.calcIcon(icon.getOriginalFilename());
			if (!icon_old.equals(banner.getIcon())) {
				if (SuUtil.imageExists(banner.getIcon())) {
					banner.setIcon(icon_old);
					throw new SuException("文件重名。");
				}
			}
		}
		bannerDao.update(banner);
		if (!icon.isEmpty()) {
			if (!icon_old.equals(banner.getIcon())) {
				SuUtil.deleteFile(icon_old);
			}
			SuUtil.saveMultipartFile(icon, banner.getIcon());
		}
	}

	public void deleteBanners(List<Integer> bannerids) {
		for (Integer bannerid : bannerids) {
			bannerDao.delete(bannerid);
			SuUtil.deleteFile(bannerDao.get(bannerid).getIcon());
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
		ticketTypeDao.deleteInvalid();
	}
}
