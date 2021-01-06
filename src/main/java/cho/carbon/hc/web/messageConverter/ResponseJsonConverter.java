package cho.carbon.hc.web.messageConverter;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StreamUtils;

import cho.carbon.hc.web.dto.ajax.ResponseJSON;
import cho.carbon.hc.spring.properties.PropertyPlaceholder;

public class ResponseJsonConverter implements HttpMessageConverter<ResponseJSON>{

	private Charset defaultCharset = Charset.forName(PropertyPlaceholder.getProperty("charset"));
	
	@Override
	public boolean canRead(Class<?> clazz, MediaType mediaType) {
		return ResponseJSON.class.isAssignableFrom(clazz);
	}

	@Override
	public boolean canWrite(Class<?> clazz, MediaType mediaType) {
		return ResponseJSON.class.isAssignableFrom(clazz);
	}

	@Override
	public List<MediaType> getSupportedMediaTypes() {
		return Arrays.asList(MediaType.APPLICATION_JSON);
	}

	@Override
	public ResponseJSON read(Class<? extends ResponseJSON> clazz,
			HttpInputMessage inputMessage) throws IOException,
			HttpMessageNotReadableException {
		return null;
	}

	@Override
	public void write(ResponseJSON t, MediaType contentType,
			HttpOutputMessage outputMessage) throws IOException,
			HttpMessageNotWritableException {
		outputMessage.getHeaders().set(HttpHeaders.CONTENT_TYPE, "text/json");
		OutputStream body = outputMessage.getBody();
		StreamUtils.copy(t.getJson(), defaultCharset, body);
		body.close();
	}
}
