package com.zhou.common.support.excel;

import cn.afterturn.easypoi.cache.manager.IFileLoader;
import cn.afterturn.easypoi.cache.manager.POICacheManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import javax.annotation.PostConstruct;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 配置execl模板加载方式
 * 使用spring ResourceLoader 加载jar里的文件
 * 
 * @author zhouze
 *
 */
@Configuration
@Slf4j
@RequiredArgsConstructor
public class ResourceLoaderImpl implements IFileLoader {

	private final ResourceLoader resourceLoader;

	/**
	 * url 相对于classes路径
	 * 
	 * @see IFileLoader#getFile(String)
	 */
	@Override
	public byte[] getFile(String url) {
		Resource resource = resourceLoader.getResource("classpath:" + url);
		InputStream stream = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			stream = resource.getInputStream();
			return IOUtils.toByteArray(stream);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} finally {
			IOUtils.closeQuietly(stream);
			IOUtils.closeQuietly(baos);
		}
		return null;
	}

	@PostConstruct
	public void init() {
		POICacheManager.setFileLoader(this);
	}

}
