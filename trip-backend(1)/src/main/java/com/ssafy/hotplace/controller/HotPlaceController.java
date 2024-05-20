package com.ssafy.hotplace.controller;

import java.io.File;
import java.nio.file.Path;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.algorithm.service.Kmp;
import com.ssafy.algorithm.service.KmpImpl;
import com.ssafy.algorithm.service.SlangService;
import com.ssafy.favorite.dto.Favorite;
import com.ssafy.hotplace.dto.FileInfo;
import com.ssafy.hotplace.dto.HotPlace;
import com.ssafy.hotplace.service.HotPlaceService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.ServletContext;

@RestController
@RequestMapping("/hotplaceapi")
public class HotPlaceController {

	private HotPlaceService hotplaceService;
	private Kmp kmp;
	private SlangService slangservice;
	private List<String> slangList;
	private ServletContext servletContext;
	private final String realPath = "C:\\SSAFY\\PJT\\final_buk04_11_11\\trip-frontend\\src\\assets\\upload"; // 절대경로 , 환경에 맞춰서 사용

	public HotPlaceController(HotPlaceService hotplaceService, SlangService slangservice,
			ServletContext servletContext) {
		super();
		this.hotplaceService = hotplaceService;
		this.slangservice = slangservice;
		this.servletContext = servletContext;
		kmp = KmpImpl.getKmp();
		try {
			slangList = this.slangservice.getList(); // 등록된 비속어 리스트 가져오기
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Operation(summary = "핫플레이스 글작성 ")
	@PostMapping("/hotplace")
	public ResponseEntity<?> writeArticle(@RequestPart(value="hotplace") HotPlace hotplace, @RequestPart(value="file") MultipartFile file) {
		try {
			boolean flag = false;
			int cnt = 0;
			for (String str : slangList) {
				boolean subjectCheck = kmp.check(hotplace.getPlaceName(), str);
				boolean contentCheck = kmp.check(hotplace.getContent(), str);
				if (subjectCheck || contentCheck) {
					flag = true;
					break;
				}
			}
			
			if (!flag && file != null) {
				String today = new SimpleDateFormat("yyMMdd").format(new Date());
				String saveFolder = realPath + File.separator + today;
				File folder = new File(saveFolder);
				if (!folder.exists())
					folder.mkdirs();

				FileInfo fileInfo = new FileInfo();
				String originalFileName = file.getOriginalFilename();
				String extension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1).toLowerCase();
				List<String> allowedExtensions = Arrays.asList("jpg", "png", "jpeg");
				if (allowedExtensions.contains(extension)) {
					String saveFileName = UUID.randomUUID().toString() + "." + extension;
					fileInfo.setSaveFolder(today);
					fileInfo.setOriginalFile(originalFileName);
					fileInfo.setSaveFile(saveFileName);
					file.transferTo(new File(folder, saveFileName));

					hotplace.setFileInfo(fileInfo);
					cnt = hotplaceService.writeArticle(hotplace);
					return ResponseEntity.ok(cnt);
				} else {
					// 허용되지 않는 파일 형식일 경우
					cnt = -2;
					return ResponseEntity.ok(cnt);
				}
			} else {
				cnt = -1;
				return ResponseEntity.ok(cnt);
			}
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@Operation(summary = "핫플레이스 글 목록 반환")
	@GetMapping("/hotplace")
	public ResponseEntity<?> listArticle() {
		try {
			List<HotPlace> hotplaces = hotplaceService.listArticle();
			return ResponseEntity.ok(hotplaces);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@Operation(summary = "{hotNo}에 해당하는 핫플레이스 글 정보를 반환한다.")
	@GetMapping("/hotplace/{hotNo}")
	public ResponseEntity<?> getArticle(@PathVariable(value = "hotNo") int hotNo) {
		try {
			hotplaceService.updateHit(hotNo);
			HotPlace hotplace = hotplaceService.getArticle(hotNo);
			if (hotplace != null && hotplace.getPlaceName().length() > 0) {
				return ResponseEntity.ok(hotplace);
			} else {
				return ResponseEntity.noContent().build();
			}
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@Operation(summary = "핫플레이스 글수정")
	@PutMapping("/hotplace")
	public ResponseEntity<?> modifyArticle(@RequestPart HotPlace hotplace, @RequestPart("file") MultipartFile file)
			throws Exception {
		try {
			boolean flag = false;
			int cnt = 0;
			for (String str : slangList) {
				boolean subjectCheck = kmp.check(hotplace.getPlaceName(), str);
				boolean contentCheck = kmp.check(hotplace.getContent(), str);
				if (subjectCheck || contentCheck) {
					flag = true;
					break;
				}
			}
			if (!flag && file != null) {
				String today = new SimpleDateFormat("yyMMdd").format(new Date());
				String saveFolder = realPath + File.separator + today;
				File folder = new File(saveFolder);
				if (!folder.exists())
					folder.mkdirs();

				FileInfo fileInfo = new FileInfo();
				String originalFileName = file.getOriginalFilename();
				String extension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1).toLowerCase();
				List<String> allowedExtensions = Arrays.asList("jpg", "png", "jpeg");
				
				if (allowedExtensions.contains(extension)) {
					// 기존 파일 삭제
					HotPlace preHotplace = hotplaceService.getArticle(hotplace.getHotNo());
					String presaveFolder = realPath + File.separator + preHotplace.getFileInfo().getSaveFolder();
					File prefolder = new File(presaveFolder);
					String presaveFileName = preHotplace.getFileInfo().getSaveFile();
					File fileToDelete = new File(prefolder, presaveFileName);
					fileToDelete.delete();
					
					String saveFileName = UUID.randomUUID().toString() + "." + extension;
					fileInfo.setSaveFolder(today);
					fileInfo.setOriginalFile(originalFileName);
					fileInfo.setSaveFile(saveFileName);
					file.transferTo(new File(folder, saveFileName));
					hotplace.setFileInfo(fileInfo);
					cnt = hotplaceService.modifyArticle(hotplace);
					return ResponseEntity.ok(cnt);
				} else {
					// 허용되지 않는 파일 형식일 경우
					cnt = -2;
					return ResponseEntity.ok(cnt);
				}
			} else {
				cnt = -1;
				return ResponseEntity.ok(cnt);
			}
		} catch (Exception e) {
			// 실패시 다시 파일 등록?
			return exceptionHandling(e);
		}
	}

	@Operation(summary = "핫플레이스  글삭제")
	@DeleteMapping("/hotplace/{hotNo}")
	public ResponseEntity<?> deleteArticle(@PathVariable("hotNo") int hotNo) throws Exception {
		try {
			// 기존 파일 삭제
			HotPlace preHotplace = hotplaceService.getArticle(hotNo);
			String presaveFolder = realPath + File.separator + preHotplace.getFileInfo().getSaveFolder();
			File prefolder = new File(presaveFolder);
			String presaveFileName = preHotplace.getFileInfo().getSaveFile();
			File fileToDelete = new File(prefolder, presaveFileName);
			fileToDelete.delete();
			
			int cnt = hotplaceService.deleteArticle(hotNo);
			return ResponseEntity.ok(cnt);
		} catch (Exception e) {
			// 삭제 실패시 다시 파일 등록?
			return exceptionHandling(e);
		}
	}
	
	@Operation(summary = "핫플레이스 인기글 목록 반환")
	@GetMapping("/hotplace/top")
	public ResponseEntity<?> listTop() {
		try {
			List<HotPlace> hotplaces = hotplaceService.listTop();
			return ResponseEntity.ok(hotplaces);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	

	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
