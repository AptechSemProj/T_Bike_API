package se.pj.tbike.api.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import se.pj.tbike.api.util.ResponseTemplate.Replaceable;
import se.pj.tbike.validation.error.Error;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public interface Response<T> {

	T getBody();

	int getCode();

	interface Builder<H extends Handleable, T> {

		HttpHeaders headers();

		/* 1xx */

		/* 2xx */

		void ok(Function<H, T> func);

		void ok(T data);

		void ok();

		void created(Function<H, T> func);

		void created(T data);

		void created();

	}

	final class Configuration {

		private ResponseTemplate template = new SimpleResponseTemplate();

		public Configuration applyTemplate(Set<String> fields) {
			Map<String, Replaceable> template = new HashMap<>();
			fields.forEach( f -> template.put( f, new Replaceable( f ) ) );
			return applyTemplate( template );
		}

		public Configuration applyTemplate(Map<String, Replaceable> template) {
			ResponseTemplate rt = new ResponseTemplate() {
				@Override
				protected Map<String, Replaceable> configureTemplate() {
					return template;
				}
			};
			return applyTemplate( rt );
		}

		public Configuration applyTemplate(ResponseTemplate template) {
			if ( template != null && template.isConfigured() ) {
				this.template = template;
			}
			return this;
		}

		public Configuration bind(Class<? extends Error> err,
		                          HttpStatus status) {
			return this;
		}

		ResponseTemplate getTemplate() {
			return template;
		}
	}
}
