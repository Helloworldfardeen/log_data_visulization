package com.graph.graph.services;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface FileServices {
 String uploadImage(String path, MultipartFile file, String graphName) throws IOException;
 InputStream getResource(String path, String file);
}