package org.gonnys.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

//db쪽이 아니어서 VO가 아닌 DTO
@Data
@AllArgsConstructor
public class UploadDTO {

	private String uploadName;
	private String originName;
	private String thumbName;
	private String ext;
}
