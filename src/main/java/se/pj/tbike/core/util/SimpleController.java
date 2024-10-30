package se.pj.tbike.core.util;

import java.util.function.Function;
import java.util.function.Supplier;

import se.pj.tbike.io.Arr;
import se.pj.tbike.io.RequestType;
import se.pj.tbike.io.Response;
import se.pj.tbike.io.ResponseType;
import se.pj.tbike.io.Val;

import se.pj.tbike.util.Output;

import se.pj.tbike.validation.ValidatorsChain;
import se.pj.tbike.validation.ValidationResult;
import se.pj.tbike.validation.validator.NotExistValidator;

public interface SimpleController<K> {

	ResponseMapping getResponseMapping();

	ValidatorsChain validateKey();

	boolean isExists(K key);

	default <REQ extends RequestType, RES> Response<Val<RES>>
	post(REQ req, Function<REQ, RES> handler) {
		checkHandler( handler );

		return Response.created( Val.of( handler.apply( req ) ) );
	}

	default <T extends ResponseType>
	Response<Arr<T>> get(Supplier<Output.Array<T>> handler) {
		checkHandler( handler );

		Output.Array<T> r = handler.get();
		return Response.ok( Arr.of( r.get() ) );
	}

	default <T extends ResponseType>
	Response<T> get(String keyInPath,
	                Function<K, Output.Value<T>> handler) {
		checkHandler( handler );

		ValidationResult result = validateKey()
				.merge( requiredValidator() )
				.handle( keyInPath );

		K k = (K) result.getValue();
		if ( result.isOk() ) {
			Output.Value<T> r = handler.apply( k );
			return r.isNull()
					? Response.internalServerError()
					: Response.ok( r.get() );
		}
		return getResponseMapping()
				.getResponse( result.getError() );
	}

	default <R extends ResponseType>
	Response<R> put(String keyInPath,
	                Supplier<K> keyInBody,
	                Function<K, Boolean> handler) {
		checkHandler( handler );

//		if ( keyInBody == null ) {
//			throw new NullPointerException( "Supplier of keyInBody is null" );
//		}
//
//		K expectedKey = keyInBody.get();
//		if ( expectedKey == null ) {
//			return Response.badRequest();
//		}
//
		ValidatorsChain chain = requiredValidator();
//				.addValidator( new UnexpectedValidator<>( keyInBody.get() ) );

		ValidationResult result = validateKey()
				.merge( chain )
				.handle( keyInPath );

		if ( result.isOk() ) {
			return handler.apply( (K) result.getValue() )
					? Response.noContent()
					: Response.internalServerError();
		}
		return getResponseMapping()
				.getResponse( result.getError() );
	}

	default <R extends ResponseType>
	Response<R> delete(String keyInPath,
	                   Function<K, Boolean> handler) {
		checkHandler( handler );

		ValidationResult result = validateKey()
				.merge( requiredValidator() )
				.handle( keyInPath );

		if ( result.isOk() ) {
			return handler.apply( (K) result.getValue() )
					? Response.noContent()
					: Response.internalServerError();
		}
		return getResponseMapping()
				.getResponse( result.getError() );
	}

	//***************** Helper Methods *******************//

	private ValidatorsChain requiredValidator() {
		return ValidatorsChain
				.createChain()
				.addValidator( new NotExistValidator() {
					@SuppressWarnings("unchecked")
					@Override
					protected boolean isExisted(Object value) {
						return isExists( (K) value );
					}
				} );
	}

	private void checkHandler(Object handler) {
		if ( handler == null ) {
			throw new NullPointerException( "handler function is null" );
		}
	}
}
