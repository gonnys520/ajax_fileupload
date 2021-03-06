package org.gonnys.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.gonnys.domain.UploadDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnailator;

@Controller
@Log4j
public class UploadController {
	

	@GetMapping("/uploadinput")
	public void uploadInput() {
	}
	
	@GetMapping("/uploadajax")
	public void uploadAjax() {
	}
	
	@PostMapping(value = "/upload", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public List<UploadDTO> upload(MultipartFile[] files) {

		List<UploadDTO> result = new ArrayList<>();

		for (MultipartFile file : files) {

			log.info(file.getOriginalFilename());
			log.info(file.getContentType());
			log.info(file.getSize());

			UUID uuid = UUID.randomUUID();

			String saveFileName = uuid.toString() + "_" + file.getOriginalFilename();
			String thumbFileName = "s_" + saveFileName;

			File saveFile = new File("C:\\upload\\" + saveFileName);
			FileOutputStream thumbFile = null;

			try {
				thumbFile = new FileOutputStream("C:\\upload\\" + thumbFileName);

				Thumbnailator.createThumbnail(file.getInputStream(), thumbFile, 100, 100);
				
				thumbFile.close();

				result.add(new UploadDTO(
						saveFileName,
						file.getOriginalFilename(),
						thumbFileName.substring(0,thumbFileName.lastIndexOf(".")),
						thumbFileName.substring(thumbFileName.lastIndexOf(".")+ 1)));
				
				file.transferTo(saveFile);

			} catch (Exception e) {
				e.printStackTrace();
			}

		} // for close

		return result;
	}// upload close
	
	@GetMapping("/viewfile/{fileName}")
	@ResponseBody
	public ResponseEntity <byte[]> viewFile(@PathVariable("fileName") String fileName) {
		
		
		log.info("FileName: " + fileName);
		
		//파일이름 컷팅
		String fName = fileName.substring(0, fileName.lastIndexOf("_"));
		log.info("Fname :" + fName);
		
		//확장자 컷팅
		String ext = fileName.substring(fileName.lastIndexOf("_") +1);
		log.info("ext: " + ext);
		
		String total = fName + "." + ext;
		
		ResponseEntity<byte[]> result = null;
		
		try {
			
			File target = new File("C:\\upload\\" + total);
			
			HttpHeaders header = new HttpHeaders();
			header.add("Content-type", Files.probeContentType(target.toPath()));
			
			byte[] arr = FileCopyUtils.copyToByteArray(target);
			result = new ResponseEntity<>(arr,header,HttpStatus.OK);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		return result;
	}
	

}// controller close
