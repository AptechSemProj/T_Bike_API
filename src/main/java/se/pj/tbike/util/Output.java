package se.pj.tbike.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import java.util.stream.Stream;

/**
 * @param <V> type of value
 */
public interface Output<V> {

	static <V> Value<V> value(V value) {
		return Value.of( value );
	}

	@SafeVarargs
	static <E> Array<E> array(E... elms) {
		return Array.of( elms );
	}

	static <E> Array<E> array(Collection<E> c) {
		return Array.of( c );
	}

	static <E> Pagination<E> pagination(E[] data,
	                                    int current, int size,
	                                    long elements, int pages) {
		return Pagination.of( data, current, size, elements, pages );
	}

	static <E> Pagination<E> pagination(E[] data,
	                                    int current, int size,
	                                    long elements) {
		return Pagination.of( data, current, size, elements );
	}

	static <E> Pagination<E> pagination(Collection<E> data,
	                                    int current, int size,
	                                    long elements, int pages) {
		return Pagination.of( data, current, size, elements, pages );
	}

	static <E> Pagination<E> pagination(Collection<E> data,
	                                    int current, int size,
	                                    long elements) {
		return Pagination.of( data, current, size, elements );
	}

	V get();

	default boolean isNull() {
		return get() == null;
	}

	interface Value<T> extends Output<T> {

		static <T> Value<T> of(T value) {
			return new OutputValue<>( value );
		}

		<R> Value<R> map(Function<? super T, ? extends R> mapper);

		Value<T> or(Supplier<? extends T> supplier);

		Value<T> or(T other);

		default T orElse(Supplier<? extends T> supplier) {
			T t = get();
			return t == null ? supplier.get() : t;
		}

		default T orElse(T other) {
			T t = get();
			return t == null ? other : t;
		}

		default boolean is(Object other) {
			return Objects.equals( get(), other );
		}

		default Optional<T> asOptional() {
			return Optional.ofNullable( get() );
		}
	}

	interface Array<E> extends Output<Collection<E>> {

		@SafeVarargs
		static <E> Array<E> of(E... elms) {
			return new OutputArray<>( elms );
		}

		static <E> Array<E> of(Collection<E> c) {
			return new OutputArray<>( c );
		}

		<R> Array<R> map(Function<? super E, ? extends R> mapper);

		E get(int index);

		int indexOf(E e);

		default boolean isEmpty() {
			return get().isEmpty();
		}

		default int size() {
			return get().size();
		}

		default void forEach(Consumer<? super E> action) {
			for ( E e : get() ) {
				action.accept( e );
			}
		}

		default void forEach(BiConsumer<? super E, Integer> action) {
			int i = 0;
			for ( E e : get() ) {
				action.accept( e, i++ );
			}
		}

		default List<E> asList(Supplier<? extends List<? extends E>> instance,
		                       boolean allowNull) {
			@SuppressWarnings("unchecked")
			List<E> list = (List<E>) instance.get();
			forEach( (e, i) -> {
				if ( !allowNull && e == null )
					throw new NullPointerException(
							"value at index " + i + " is null" );
				list.add( e );
			} );
			return list;
		}

		default List<E> asList(boolean allowNull) {
			return asList( ArrayList::new, allowNull );
		}

		default List<E> asList(Supplier<? extends List<? extends E>> instance) {
			return asList( instance, false );
		}

		default List<E> asList() {
			return asList( ArrayList::new, false );
		}

		default Map<Integer, E> asMap(
				Supplier<? extends Map<Integer, ? extends E>> instance,
				boolean allowNullValue) {
			@SuppressWarnings("unchecked")
			Map<Integer, E> map = (Map<Integer, E>) instance.get();
			forEach( (e, i) -> {
				if ( !allowNullValue && e == null ) {
					throw new NullPointerException(
							"value at index " + i + " is null" );
				}
				map.put( i, e );
			} );
			return map;
		}

		default Map<Integer, E> asMap(boolean allowNullValue) {
			return asMap( HashMap::new, allowNullValue );
		}

		default Map<Integer, E> asMap(
				Supplier<? extends Map<Integer, ? extends E>> instance) {
			return asMap( instance, false );
		}

		default Map<Integer, E> asMap() {
			return asMap( HashMap::new, false );
		}

		default <K> Map<K, E> asMap(
				Supplier<? extends Map<? extends K, ? extends E>> instance,
				Function<? super E, ? extends K> keyMapper) {
			@SuppressWarnings("unchecked")
			Map<K, E> map = (Map<K, E>) instance.get();
			for ( E e : get() ) {
				K k = keyMapper.apply( e );
				map.put( k, e );
			}
			return map;
		}

		default <K> Map<K, E> asMap(
				Function<? super E, ? extends K> keyMapper) {
			return asMap( HashMap::new, keyMapper );
		}

		default Stream<E> stream() {
			return get().stream();
		}
	}

	interface Pagination<E> extends Array<E> {

		static <E> Pagination<E> of(E[] data, int current, int size,
		                            long elements, int pages) {
			return new OutputPagination<>( data, current, size, elements, pages );
		}

		static <E> Pagination<E> of(E[] data, int current, int size,
		                            long elements) {
			return new OutputPagination<>( data, current, size, elements );
		}

		static <E> Pagination<E> of(Collection<E> data,
		                            int current, int size,
		                            long elements, int pages) {
			return new OutputPagination<>( data, current, size, elements, pages );
		}

		static <E> Pagination<E> of(Collection<E> data,
		                            int current, int size,
		                            long elements) {
			return new OutputPagination<>( data, current, size, elements );
		}

		<R> Pagination<R> map(Function<? super E, ? extends R> mapper);

		Integer next();

		Integer previous();

		int getNumber();

		int getSize();

		int getTotalPages();

		long getTotalElements();
	}
}
