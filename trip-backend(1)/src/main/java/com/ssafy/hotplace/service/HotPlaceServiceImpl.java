package com.ssafy.hotplace.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.hotplace.dao.HotPlaceDao;
import com.ssafy.hotplace.dto.HotPlace;

@Service
public class HotPlaceServiceImpl implements HotPlaceService {

	private HotPlaceDao hotplaceDao;

	public HotPlaceServiceImpl(HotPlaceDao hotplaceDao) {
		super();
		this.hotplaceDao = hotplaceDao;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int writeArticle(HotPlace hotplace) throws SQLException {
		try {
			hotplaceDao.writeArticle(hotplace);
			int result = hotplaceDao.registerFile(hotplace);
			if (result == 0) {
				throw new RuntimeException("Failed to register file");
			}
			return result;
		} catch (Exception e) {
			throw new RuntimeException("Failed to write article with file", e);
		}
	}

	@Override
	public List<HotPlace> listArticle() throws SQLException {
		return hotplaceDao.listArticle();
	}

	@Override
	public void updateHit(int hotNo) throws SQLException {
		hotplaceDao.updateHit(hotNo);
	}

	@Override
	public HotPlace getArticle(int hotNo) throws SQLException {
		return hotplaceDao.getArticle(hotNo);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int modifyArticle(HotPlace hotplace) throws SQLException {
		try {
			hotplaceDao.modifyArticle(hotplace);
			int result = hotplaceDao.modifyFile(hotplace);
			if (result == 0) {
				throw new RuntimeException("Failed to modify file");
			}
			return result;
		} catch (Exception e) {
			throw new RuntimeException("Failed to modify article with file", e);
		}
	}

	@Override
	public int deleteArticle(int hotNo) throws SQLException {
		return hotplaceDao.deleteArticle(hotNo);
	}

	@Override
	public void updateCount(int hotNo) throws SQLException {
		hotplaceDao.updateCount(hotNo);
	}

	@Override
	public void deleteCount(int hotNo) throws SQLException {
		hotplaceDao.deleteCount(hotNo);
	}

	@Override
	public List<HotPlace> listTop() throws SQLException {
		return hotplaceDao.listTop();
	}

}
