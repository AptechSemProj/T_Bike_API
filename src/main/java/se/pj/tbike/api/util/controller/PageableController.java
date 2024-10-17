package se.pj.tbike.api.util.controller;

import java.util.function.BiFunction;

import static se.pj.tbike.api.io.Response.badRequest;
import static se.pj.tbike.api.io.Response.ok;
import static se.pj.tbike.api.io.Response.pagination;

import se.pj.tbike.api.io.Arr;
import se.pj.tbike.api.io.Response;
import se.pj.tbike.api.io.ResponseType;

import static se.pj.tbike.api.util.Error.NaN;
import static se.pj.tbike.api.util.Error.NULL;
import static se.pj.tbike.api.util.Error.SMALLER_THAN;
import static se.pj.tbike.api.util.NumberValidator.validateInt;

import se.pj.tbike.api.util.Validated;

import se.pj.tbike.util.result.ResultList;
import se.pj.tbike.util.result.ResultPage;

public interface PageableController {

	default int getBasedPageNumber() {
		return 0;
	}

	default Validated<Integer> validatePageNumber( String pageNumber ) {
		return validateInt( pageNumber )
				.thenTest( p -> p >= getBasedPageNumber(), SMALLER_THAN );
	}

	default Validated<Integer> validatePageSize( String pageSize ) {
		return validateInt( pageSize );
	}

	default <T extends ResponseType>
	Response<Arr<T>> paginated(
			String pageNumber, String pageSize,
			BiFunction<Integer, Integer, ResultList<T>> handler ) {
		if ( handler == null ) {
			throw new NullPointerException( "handler is null" );
		}
		String[] names = new String[] { "pageNumber", "pageSize" };
		Validated<Integer> page, size;
		if ( ( page = validatePageNumber( pageNumber ) ).isFailed() ) {
			return badRequest( switch ( page.getError() ) {
				default -> null;
				case SMALLER_THAN -> SMALLER_THAN
						.getMessage( names[0], getBasedPageNumber() );
				case NaN -> NaN.getMessage( pageNumber );
				case NULL -> NULL.getMessage( names[0] );
			} );
		}
		if ( ( size = validatePageSize( pageSize ) ).isFailed() ) {
			return badRequest( switch ( size.getError() ) {
				default -> null;
				case NaN -> NaN.getMessage( pageSize );
				case NULL -> NULL.getMessage( names[1] );
			} );
		}
		ResultList<T> rl = handler.apply( page.getValue(), size.getValue() );
		return rl instanceof ResultPage<T> rp
				? pagination( rp )
				: ok( Arr.of( rl ) );
	}
}
