package se.pj.tbike.reflect;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class GenericType<T> {

	private final Class<?>[] genericTypes;

	public GenericType(T t) {
		ParameterizedType pt = (ParameterizedType) t.getClass()
				.getGenericSuperclass();
		Type[] types = pt.getActualTypeArguments();
		this.genericTypes = new Class[types.length];
		int i = 0;
		for ( Type type : types ) {
			String className = type.getTypeName();
			try {
				this.genericTypes[i] = Class.forName( className );
			} catch ( ClassNotFoundException e ) {
				this.genericTypes[i] = Object.class;
			}
			i++;
		}
	}

	@SuppressWarnings("unchecked")
	public <R> Class<R> getType(int index) {
		return (Class<R>) genericTypes[index];
	}
}
