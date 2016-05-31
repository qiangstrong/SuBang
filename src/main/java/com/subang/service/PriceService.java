package com.subang.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.subang.bean.SearchArg;
import com.subang.domain.Article;
import com.subang.domain.Category;
import com.subang.domain.ClothesType;
import com.subang.domain.Color;
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
		category.calcIcon(icon.getOriginalFilename());
		try {
			categoryDao.save(category);
		} catch (DuplicateKeyException e) {
			throw new SuException("类别名称不能相同。");
		}
		SuUtil.saveMultipartFile(icon, category.getIcon());
	}

	public void modifyCategory(Category category, MultipartFile icon) throws SuException {
		String icon_old = category.getIcon();
		if (!icon.isEmpty()) {
			category.calcIcon(icon.getOriginalFilename());
		}
		try {
			categoryDao.update(category);
		} catch (DuplicateKeyException e) {
			category.setIcon(icon_old);
			throw new SuException("类别名称不能相同。");
		}
		if (!icon.isEmpty()) {
			SuUtil.deleteFile(icon_old);
			SuUtil.saveMultipartFile(icon, category.getIcon());
		}
	}

	public void deleteCategorys(List<Integer> categoryids) throws SuException {
		boolean isAll = true;
		for (Integer categoryid : categoryids) {
			try {
				Category category = categoryDao.get(categoryid);
				categoryDao.delete(categoryid);
				if (category != null) {
					SuUtil.deleteFile(category.getIcon());
				}
			} catch (DataIntegrityViolationException e) {
				isAll = false;
			}
		}
		if (!isAll) {
			throw new SuException("部分类别没有成功删除。可能是用户的优惠券或订单引用了这些类别。");
		}
	}

	/**
	 * 小类
	 */
	public void addPrice(Price price) throws SuException {
		try {
			priceDao.save(price);
		} catch (DuplicateKeyException e) {
			throw new SuException("同一类别下不能有相同的小类。");
		}
	}

	public void modifyPrice(Price price) throws SuException {
		try {
			priceDao.update(price);
		} catch (DuplicateKeyException e) {
			throw new SuException("同一类别下不能有相同的小类。");
		}
	}

	public void deletePrices(List<Integer> priceids) throws SuException {
		for (Integer priceid : priceids) {
			priceDao.delete(priceid);
		}
	}

	/**
	 * 衣服类型。和用户价目表有关
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
		clothesType.calcIcon(icon.getOriginalFilename());
		try {
			clothesTypeDao.save(clothesType);
		} catch (DuplicateKeyException e) {
			throw new SuException("衣物名称不能相同。");
		}
		SuUtil.saveMultipartFile(icon, clothesType.getIcon());
	}

	public void modifyClothesType(ClothesType clothesType, MultipartFile icon) throws SuException {
		String icon_old = clothesType.getIcon();
		if (!icon.isEmpty()) {
			clothesType.calcIcon(icon.getOriginalFilename());
		}
		try {
			clothesTypeDao.update(clothesType);
		} catch (DuplicateKeyException e) {
			clothesType.setIcon(icon_old);
			throw new SuException("衣物名称不能相同。");
		}
		if (!icon.isEmpty()) {
			SuUtil.deleteFile(icon_old);
			SuUtil.saveMultipartFile(icon, clothesType.getIcon());
		}
	}

	public void deleteClothesTypes(List<Integer> clothesTypeids) throws SuException {
		for (Integer clothesTypeid : clothesTypeids) {
			ClothesType clothesType = clothesTypeDao.get(clothesTypeid);
			clothesTypeDao.delete(clothesTypeid);
			if (clothesType != null) {
				SuUtil.deleteFile(clothesType.getIcon());
			}
		}
	}

	/**
	 * 物品。物品和订单衣物明细、商户价格有关
	 */
	public void addArticle(Article article) throws SuException {
		try {
			articleDao.save(article);
		} catch (DuplicateKeyException e) {
			throw new SuException("物品名称不能相同。");
		}
	}

	public void modifyArticle(Article article) throws SuException {
		try {
			articleDao.update(article);
		} catch (DuplicateKeyException e) {
			throw new SuException("物品名称不能相同。");
		}
	}

	public void deleteArticles(List<Integer> articleids) throws SuException {
		boolean isAll = true;
		for (Integer articleid : articleids) {
			try {
				articleDao.delete(articleid);
			} catch (DataIntegrityViolationException e) {
				isAll = false;
			}
		}
		if (!isAll) {
			throw new SuException("部分物品没有成功删除。可能是由于这些物品处于被引用的状态。");
		}
	}

	/**
	 * 颜色。颜色和订单衣物明细有关
	 */
	public void addColor(Color color) throws SuException {
		try {
			colorDao.save(color);
		} catch (DuplicateKeyException e) {
			throw new SuException("颜色名称不能相同。");
		}
	}

	public void modifyColor(Color color) throws SuException {
		try {
			colorDao.update(color);
		} catch (DuplicateKeyException e) {
			throw new SuException("颜色名称不能相同。");
		}
	}

	public void deleteColors(List<Integer> colorids) throws SuException {
		boolean isAll = true;
		for (Integer colorid : colorids) {
			try {
				colorDao.delete(colorid);
			} catch (DataIntegrityViolationException e) {
				isAll = false;
			}
		}
		if (!isAll) {
			throw new SuException("部分颜色没有成功删除。可能是由于这些颜色处于被引用的状态。");
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
