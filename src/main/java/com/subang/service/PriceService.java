package com.subang.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.subang.bean.SearchArg;
import com.subang.domain.Category;
import com.subang.domain.ClothesType;
import com.subang.domain.Notice.Code;
import com.subang.domain.Price;
import com.subang.tool.SuException;
import com.subang.util.SuUtil;
import com.subang.util.WebConst;

@Service
public class PriceService extends BaseService {
	/**
	 * 类别
	 */
	public void addCategory(Category category, MultipartFile icon) throws SuException {
		if (icon.isEmpty()) {
			throw new SuException("未选择图标文件。");
		}
		try {
			category.calcIcon(icon.getOriginalFilename());
			SuUtil.saveMultipartFile(false, icon, category.getIcon());
			categoryDao.save(category);
		} catch (DuplicateKeyException e) {
			throw new SuException("类别名称不能相同。");
		}
	}

	public void modifyCategory(Category category, MultipartFile icon) throws SuException {
		try {
			if (!icon.isEmpty()) {
				category.calcIcon(icon.getOriginalFilename());
				SuUtil.saveMultipartFile(true, icon, category.getIcon());
			}
			categoryDao.update(category);
		} catch (DuplicateKeyException e) {
			throw new SuException("类别名称不能相同。");
		}
	}

	public void deleteCategorys(List<Integer> categoryids) throws SuException {
		boolean isAll = true;
		for (Integer categoryid : categoryids) {
			try {
				SuUtil.deleteFile(categoryDao.get(categoryid).getIcon());
				categoryDao.delete(categoryid);
			} catch (DataIntegrityViolationException e) {
				isAll = false;
			}
		}
		if (!isAll) {
			throw new SuException("部分类别没有成功删除。可能是用户的优惠券引用了这些类别。");
		}
	}

	/**
	 * 价格
	 */
	public void addPrice(Price price) throws SuException {
		try {
			priceDao.save(price);
		} catch (DuplicateKeyException e) {
			throw new SuException("同一类别下不能有相同的价格。");
		}
	}

	public void modifyPrice(Price price) throws SuException {
		try {
			priceDao.update(price);
		} catch (DuplicateKeyException e) {
			throw new SuException("同一类别下不能有相同的价格。");
		}
	}

	public void deletePrices(List<Integer> priceids) throws SuException {
		for (Integer priceid : priceids) {
			priceDao.delete(priceid);
		}
	}

	/**
	 * 衣服类型
	 */
	public List<ClothesType> searchClothesType(SearchArg searchArg) {
		List<ClothesType> clothesTypes = null;
		switch (searchArg.getType()) {
		case WebConst.SEARCH_ALL:
			clothesTypes = clothesTypeDao.findDetailByCategoryid(searchArg.getUpperid());
			break;

		case WebConst.SEARCH_INCOMPLETE:
			clothesTypes = clothesTypeDao.findIncomplete(searchArg.getUpperid());
			break;
		default:
			clothesTypes = new ArrayList<ClothesType>();
		}
		return clothesTypes;
	}

	public void addClothesType(ClothesType clothesType, MultipartFile icon) throws SuException {
		if (icon.isEmpty()) {
			throw new SuException("未选择图标文件。");
		}
		try {
			clothesType.calcIcon(icon.getOriginalFilename());
			SuUtil.saveMultipartFile(false, icon, clothesType.getIcon());
			clothesTypeDao.save(clothesType);
		} catch (DuplicateKeyException e) {
			throw new SuException("衣服名称不能相同。");
		}
	}

	public void modifyClothesType(ClothesType clothesType, MultipartFile icon) throws SuException {
		try {
			if (!icon.isEmpty()) {
				clothesType.calcIcon(icon.getOriginalFilename());
				SuUtil.saveMultipartFile(true, icon, clothesType.getIcon());
			}
			clothesTypeDao.update(clothesType);
		} catch (DuplicateKeyException e) {
			throw new SuException("衣服名称不能相同。");
		}
	}

	public void deleteClothesTypes(List<Integer> clothesTypeids) throws SuException {
		for (Integer clothesTypeid : clothesTypeids) {
			SuUtil.deleteFile(clothesTypeDao.get(clothesTypeid).getIcon());
			clothesTypeDao.delete(clothesTypeid);
		}
	}

	/**
	 * 数据检查
	 */
	public void check() {
		List<Category> categorys = categoryDao.findAll();
		List<String> categorynames = new ArrayList<String>();
		List<ClothesType> clothesTypes = null;
		for (Category category : categorys) {
			clothesTypes = clothesTypeDao.findIncomplete(category.getId());
			if (!clothesTypes.isEmpty()) {
				categorynames.add(category.getName());
			}
		}
		if (!categorynames.isEmpty()) {
			StringBuffer names = new StringBuffer();
			for (String name : categorynames) {
				names.append(name);
			}
			SuUtil.notice(Code.incomplete, "存在衣物类型没有指定价格。类别名称：" + names.toString());
		}
	}
}
