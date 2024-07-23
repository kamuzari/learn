package com.example.designpattern.decorator.pojo.datasource;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

public class FileDataSource implements DataSource {
	private String name;

	public FileDataSource(String name) {
		this.name = name;
	}

	@Override
	public void writeData(String data) {
		ResourceLoader loader = new DefaultResourceLoader();
		Resource resource = loader.getResource("classpath:/static/" + name);
		try (OutputStream fos = new FileOutputStream(resource.getFile())) {
			fos.write(data.getBytes(), 0, data.length());
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
	}

	@Override
	public String readData() {
		char[] buffer = null;
		ResourceLoader loader = new DefaultResourceLoader();
		Resource resource = loader.getResource("classpath:/static/" + name);
		try (FileReader reader = new FileReader(resource.getFile())) {
			buffer = new char[(int)resource.getFile().length()];
			reader.read(buffer);
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
		return new String(buffer);
	}
}
