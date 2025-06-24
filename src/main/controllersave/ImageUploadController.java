package controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ImageUploadController {

    @PostMapping("/image/upload")
    public Map<String, Object> upload(@RequestParam("upload") MultipartFile file) throws IOException {
    	log.warn("image upload함수 호출");
        String uploadDir = "C:/upload/editor/";
        
        // 업로드 파일 이름 중복 방지
        String originalFilename = file.getOriginalFilename();
        System.out.println("originalFilename: " + originalFilename);
        String uuid = UUID.randomUUID().toString();
        String newFilename = uuid + "_" + originalFilename;

        // 파일 저장
        File dest = new File(uploadDir + newFilename);
        file.transferTo(dest);

        // JSON 응답 (CKEditor5에서 요구하는 형식)
        Map<String, Object> response = new HashMap<>();
        response.put("uploaded", true);
        response.put("url", "/upload/editor/" + newFilename);
        return response;
    }
}

