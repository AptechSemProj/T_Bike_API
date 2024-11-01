package se.pj.tbike.core.util;

import se.pj.tbike.io.Arr;
import se.pj.tbike.io.Pagination;
import se.pj.tbike.io.Response;
import se.pj.tbike.io.ResponseType;
import se.pj.tbike.util.Output;
import se.pj.tbike.validation.ValidatorsChain;
import se.pj.tbike.validation.validator.IntegerValidator;
import se.pj.tbike.validation.ValidationResult;
import se.pj.tbike.validation.validator.NumberValidator;

import java.util.function.BiFunction;

public interface PageableController {

	default int getBasedPageNumber() {
		return 0;
	}

	default ValidationResult validatePageNumber(Object pageNumber) {
		NumberValidator<Integer> numberValidator =
				new IntegerValidator().acceptMinValue( getBasedPageNumber() );

		ValidatorsChain chain = ValidatorsChain
				.createChain()
				.addValidator( numberValidator );

		return chain.handle( pageNumber );
	}

	default ValidationResult validatePageSize(Object pageSize) {
		ValidatorsChain chain = ValidatorsChain
				.createChain()
				.addValidator( new IntegerValidator().acceptOnlyPositive() );

		return chain.handle( pageSize );
	}

	default <T extends ResponseType>
	Response<Arr<T>> paginated(
			Object pageNumber, Object pageSize,
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
