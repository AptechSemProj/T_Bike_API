package se.pj.tbike.api.core.brand.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import static se.pj.tbike.api.common.Urls.API_PREFIX;
import static se.pj.tbike.api.common.Urls.BRAND_API;
import static se.pj.tbike.api.common.Urls.URL_INFO;
import static se.pj.tbike.api.common.Urls.URL_LIST_1;
import static se.pj.tbike.api.common.Urls.URL_LIST_2;

import se.pj.tbike.api.core.brand.Brand;
import se.pj.tbike.api.core.brand.data.BrandService;
import se.pj.tbike.api.core.brand.dto.BrandCreation;
import se.pj.tbike.api.core.brand.dto.BrandModification;
import se.pj.tbike.api.core.brand.dto.BrandResponse;
import se.pj.tbike.api.core.brand.mapper.BrandReqMapper;
import se.pj.tbike.api.core.brand.mapper.BrandResMapper;

import se.pj.tbike.api.io.Arr;
import se.pj.tbike.api.io.Response;
import se.pj.tbike.api.io.ResponseType;
import se.pj.tbike.api.io.Val;

import static se.pj.tbike.api.util.NumberValidator.validateLong;
import static se.pj.tbike.api.util.Error.INVALID;

import se.pj.tbike.api.util.Validated;
import se.pj.tbike.api.util.controller.StdController;
import se.pj.tbike.api.util.controller.PageableController;

@RequiredArgsConstructor
@RestController
@RequestMapping( path = { API_PREFIX + BRAND_API } )
public class BrandController
		implements StdController<Long>,
		PageableController {

	private final BrandService service;
	private final BrandReqMapper<BrandCreation> creationMapper;
	private final BrandReqMapper<BrandModification> modificationMapper;
	private final BrandResMapper<BrandResponse> responseMapper;

	@GetMapping( path = { URL_LIST_1, URL_LIST_2 } )
	public Response<Arr<BrandResponse>> getList(
			@RequestParam( name = "page", defaultValue = "0" ) String page,
			@RequestParam( name = "size", defaultValue = "-1" ) String size ) {
		return paginated( page, size, ( p, s ) -> (
				s >= 0 ? service.findPage( p, s ) : service.findAll()
		).map( responseMapper::map ) );
	}

	@GetMapping( path = { URL_INFO } )
	public Response<BrandResponse> getById(
			@PathVariable( "id" ) String id ) {
		return get( id, k ->
				service.findByKey( k ).map( responseMapper::map ) );
	}

	@PostMapping( path = { URL_LIST_1, URL_LIST_2 } )
	public Response<Val<Long>> create(
			@RequestBody BrandCreation req ) {
		return post( () -> {
			Brand b = creationMapper.map( req );
			return service.create( b ).getId();
		} );
	}

	@PutMapping( path = { URL_INFO } )
	public Response<ResponseType> update(
			@PathVariable( "id" ) String id,
			@RequestBody BrandModification req ) {
		return put( id, req::getId, k -> {
			Brand b = modificationMapper.map( req );
			return service.update( b );
		} );
	}

	@DeleteMapping( path = { URL_INFO } )
	public Response<ResponseType> delete(
			@PathVariable( "id" ) String id ) {
		return delete( id, service::remove );
	}

	@Override
	public boolean isExists( Long key ) {
		return service.exists( key );
	}

	@Override
	public Validated<Long> validateKey( String keyInPath ) {
		return validateLong( keyInPath )
				.thenTest( k -> k > 0, INVALID );
	}
}
