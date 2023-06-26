package com.example.demo.video;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

@Controller
public class VideoController {

    private static final String VIDEO_DIRECTORY = "/path/to/videos/"; // Replace with the actual path to your video files

    @GetMapping("/video/{filename}")
    public ResponseEntity<FileSystemResource> streamVideo(@PathVariable("filename") String filename) throws IOException {
        File videoFile = new File(VIDEO_DIRECTORY + filename);

        if (videoFile.exists()) {
            Path path = Path.of(videoFile.getAbsolutePath());
            MediaType mediaType = MediaType.parseMediaType("video/mp4"); // Replace with the appropriate media type for your video files

            return ResponseEntity.ok()
                    .contentType(mediaType)
                    .contentLength(videoFile.length())
                    .body(new FileSystemResource(videoFile));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/video")
    public ResponseEntity<String> uploadVideo(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }

        try {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            File videoFile = new File(VIDEO_DIRECTORY + fileName);

            // Save the video file
            file.transferTo(videoFile);

            return ResponseEntity.ok("Video uploaded successfully");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to upload video");
        }
    }

    @DeleteMapping("/video/{filename}")
    public ResponseEntity<String> deleteVideo(@PathVariable("filename") String filename) {
        File videoFile = new File(VIDEO_DIRECTORY + filename);

        if (videoFile.exists()) {
            if (videoFile.delete()) {
                return ResponseEntity.ok("Video deleted successfully");
            } else {
                return ResponseEntity.status(500).body("Failed to delete video");
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

    }
}
