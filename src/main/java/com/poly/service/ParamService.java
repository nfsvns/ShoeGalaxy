package com.poly.service;

import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.val;
import lombok.experimental.var;

@Service
public class ParamService {
	@Autowired
	HttpServletRequest request;
	@Autowired
	ServletContext context;
	
	public String getString (String name, String defaulValue) {
		String result = request.getParameter(name);
		return result != null ? result : defaulValue;
	}
	public int getInt (String name, int defaulValue) {
		String result = request.getParameter(name);
		return result != null ? Integer.parseInt(result) : defaulValue;
	}
	public double getDouble (String name, double defaulValue) {
		String result = request.getParameter(name);
		return result != null ? Double.parseDouble(result) : defaulValue;
	}
	public boolean getBoolean (String name, boolean defaulValue) {
		String result = request.getParameter(name);
		return result != null ? Boolean.parseBoolean(result) : defaulValue;
	}
	public Date getDate(String name, String pattern) {
		try {
			SimpleDateFormat formater = new SimpleDateFormat(pattern);
			return formater.parse(request.getParameter(name));
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	public File save(MultipartFile file, String path) throws Exception {
		File dir = new File(context.getRealPath(path));
		if (!dir.exists())
			dir.mkdirs();

		try {
			File saveFile = new File(dir, file.getOriginalFilename());
			file.transferTo(saveFile);
			return saveFile;
		} catch (Exception e) {
			throw new Exception();
		}
	}
}
