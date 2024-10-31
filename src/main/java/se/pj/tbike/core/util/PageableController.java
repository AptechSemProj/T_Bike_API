package se.pj.tbike.core.util;

import java.util.function.BiFunction;

import se.pj.tbike.io.Arr;
import se.pj.tbike.io.Pagination;
import se.pj.tbike.io.Response;
import se.pj.tbike.io.ResponseType;

import se.pj.tbike.util.Output;

import se.pj.tbike.validation.ValidatorsChain;
import se.pj.tbike.validation.validator.IntegerValidator;
import se.pj.tbike.validation.validator.NumberValidator;
import se.pj.tbike.validation.ValidationResult;

public interface PageableController {

	default int getBasedPageNumber() {
		return 0;
	}

	default ValidationResult validatePageNumber(String pageNumber) {
		NumberValidator<Integer> numberValidator =
				new IntegerValidator().setMin( getBasedPageNumber() );

		ValidatorsChain chain = ValidatorsChain
				.createChain()
				.addValidator( numberValidator );

		return chain.handle( pageNumber );
	}

	default ValidationResult validatePageSize(String pageSize) {
		ValidatorsChain chain = ValidatorsChain
				.createChain()
				.addValidator( new IntegerValidator().setMin( 1 ) );

		return chain.handle( pageSize );
	}

	default <T extends ResponseType>
	Response<Arr<T>> paginated(
			String pageNumber, String pageSize,
			BiFunction<Integer, Integer, Output.Array<T>> handler) {
		if ( handler == null ) {
			throw new NullPointerException( "handler is null" );
		}
		ValidationResult page, size;
		page = validatePageNumber( pageNumber );
		size = validatePageSize( pageSize );
		if ( page.isFailed() ) {
			return Response.badRequest( page.getError().getReason() );
		}
		if ( size.isFailed() ) {
			return Response.badRequest( size.getError().getReason() );
		}
		Output.Array<T> r = handler.apply(
				page.getValue( Integer.class ),
				size.getValue( Integer.class )
		);
		if ( r instanceof Output.Pagination<T> p ) {
			Pagination.Metadata metadata = new Pagination.Metadata(
					p.getTotalElements(), p.getTotalPages(),
					p.getSize(), p.getNumber(),
					p.next(), p.previous()
			);
			return new Pagination<>( p.get(), metadata );
		}
		return Response.ok( Arr.of( r.get() ) );
	}
}
