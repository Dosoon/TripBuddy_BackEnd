package com.tripbuddy.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


public class ReadableRequestWrapper extends HttpServletRequestWrapper {
	
	private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;
	private static final int EOF = -1;

	private final Charset encoding;
	private Map<String, Object> reqBody;
	private byte[] rawData;

	public ReadableRequestWrapper(HttpServletRequest request) {
		super(request);
		String encoding = request.getCharacterEncoding();
		this.encoding = StringUtils.isEmpty(encoding) ? StandardCharsets.UTF_8 : Charset.forName(encoding);
		try {
			InputStream is = request.getInputStream();
			this.rawData = toByteArray(is);
			this.reqBody = new ObjectMapper().readValue(new String(this.rawData), Map.class);
		} catch (IOException e) {
		}
	}
	
	private static byte[] toByteArray(final InputStream input) throws IOException {
		try (final ByteArrayOutputStream output = new ByteArrayOutputStream()) {
			copyLarge(input, output, new byte[DEFAULT_BUFFER_SIZE]);
			return output.toByteArray();
		}
	}

	private static void copyLarge(final InputStream input, final OutputStream output,
	                              final byte[] buffer) throws IOException {
		int n;
		while (EOF != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
		}
	}
	
	@Override
	public ServletInputStream getInputStream() {
		final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.rawData);
		return new ServletInputStream() {
			@Override
			public boolean isFinished() {
				return false;
			}

			@Override
			public boolean isReady() {
				return false;
			}

			@Override
			public void setReadListener(ReadListener readListener) {
				// Do nothing
			}

			public int read() {
				return byteArrayInputStream.read();
			}
		};
	}

	@Override
	public BufferedReader getReader() {
		return new BufferedReader(new InputStreamReader(this.getInputStream(), this.encoding));
	}
	
	public Map<String, Object> getReqBody() {
		return this.reqBody;
	}
}
