package se.pj.tbike.api.util.controller;

import static java.util.function.Predicate.isEqual;

import java.util.function.Function;
import java.util.function.Supplier;

import static se.pj.tbike.api.io.Response.badRequest;
import static se.pj.tbike.api.io.Response.created;
import static se.pj.tbike.api.io.Response.internalServerError;
import static se.pj.tbike.api.io.Response.noContent;
import static se.pj.tbike.api.io.Response.notFound;

import se.pj.tbike.api.io.Arr;
import se.pj.tbike.api.io.Response;
import se.pj.tbike.api.io.ResponseType;
import se.pj.tbike.api.io.Val;

import static se.pj.tbike.api.io.Response.ok;
import static se.pj.tbike.api.util.Error.INVALID;
import static se.pj.tbike.api.util.Error.NOT_EQUAL;
import static se.pj.tbike.api.util.Error.NOT_FOUND;
import static se.pj.tbike.api.util.Error.NaN;
import static se.pj.tbike.api.util.Error.NULL;

import se.pj.tbike.api.util.Error;
import se.pj.tbike.api.util.Validated;

import se.pj.tbike.util.result.Result;
import se.pj.tbike.util.result.ResultList;

public interface StdController<K> {

	Validated<K> validateKey( String keyInPath );

	default boolean isExists( K key ) {
		return key != null;
	}

	default String getKeyName() {
		return "id";
	}

	default <R>
	Response<Val<R>> post( Supplier<R> handler ) {
		checkHandler( handler );

		return created( Val.of( handler.get() ) );
	}

	default <T extends ResponseType>
	Response<Arr<T>> get( Supplier<ResultList<T>> handler ) {
		checkHandler( handler );

		ResultList<T> rl = handler.get();
		return ok( Arr.of( rl ) );
	}

	default <T extends ResponseType>
	Response<T> get( String keyInPath,
	                 Function<K, Result<T>> handler ) {
		checkHandler( handler );

		Validated<K> key = validateKey( keyInPath )
				.thenTest( this::isExists, NOT_FOUND );
		String keyName = getKeyName();
		K k = key.getValue();
		if ( key.isOk() ) {
			Result<T> r = handler.apply( k );
			return r.isEmpty()
					? responseErrorNotFound( keyName, k )
					: ok( r.get() );
		}
		return responseError( key.getError(), keyName, k, k );
	}

	default <R extends ResponseType>
	Response<R> put( String keyInPath,
	                 Supplier<K> keyInBody,
	                 Function<K, Boolean> handler ) {
		checkHandler( handler );
		if ( keyInBody == null )
			throw new NullPointerException( "Supplier of keyInBody is null" );

		K expected = keyInBody.get();
		Validated<K> key = validateKey( keyInPath )
				.thenTest( isEqual( expected ), NOT_EQUAL )
				.thenTest( this::isExists, NOT_FOUND );
		if ( key.isOk() ) {
			return handler.apply( key.getValue() )
					? noContent()
					: internalServerError();
		}
		return responseError(
				key.getError(), getKeyName(), expected, key.getValue() );
	}

	default <R extends ResponseType>
	Response<R> delete( String keyInPath,
	                    Function<K, Boolean> handler ) {
		checkHandler( handler );
		Validated<K> key = validateKey( keyInPath )
				.thenTest( this::isExists, NOT_FOUND );
		if ( key.isOk() ) {
			return handler.apply( key.getValue() )
					? noContent()
					: internalServerError();
		}
		K k = key.getValue();
		return responseError( key.getError(), getKeyName(), k, k );
	}

	//********************** Response Methods *******************//

	@SuppressWarnings( "unchecked" )
	default <R extends ResponseType>
	Response<R> responseError( Error err, String keyName,
	                           Object current, K expected ) {
		return switch ( err ) {
			case INVALID -> responseErrorInvalid( keyName );
			case NOT_EQUAL -> responseErrorNotEqual( expected, (K) current );
			case NOT_FOUND -> responseErrorNotFound( keyName, expected );
			case NULL -> responseErrorNull( keyName );
			case NaN -> responseErrorNaN( String.valueOf( current ) );
			default -> internalServerError();
		};
	}

	default <R extends ResponseType>
	Response<R> responseErrorInvalid( String keyName ) {
		return badRequest( INVALID.getMessage( keyName ) );
	}

	default <R extends ResponseType>
	Response<R> responseErrorNotEqual( K expected, K current ) {
		return badRequest( NOT_EQUAL.getMessage( expected, current ) );
	}

	default <R extends ResponseType>
	Response<R> responseErrorNotFound( String keyName, K key ) {
		return notFound( NOT_FOUND.getMessage( keyName, key ) );
	}

	default <R extends ResponseType>
	Response<R> responseErrorNull( String keyName ) {
		return badRequest( NULL.getMessage( keyName ) );
	}

	default <R extends ResponseType>
	Response<R> responseErrorNaN( String value ) {
		return badRequest( NaN.getMessage( value ) );
	}

	//***************** Helper Methods *******************//

	private void checkHandler( Object handler ) {
		if ( handler == null )
			throw new NullPointerException( "handler function is null" );
	}
}
