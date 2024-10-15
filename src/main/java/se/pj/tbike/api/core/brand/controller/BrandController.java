package se.pj.tbike.api.core.brand.controller;

import java.util.Optional;

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

import static se.pj.tbike.api.io.Response.badRequest;
import static se.pj.tbike.api.io.Response.created;
import static se.pj.tbike.api.io.Response.noContent;
import static se.pj.tbike.api.io.Response.ok;
import static se.pj.tbike.api.io.Response.pagination;

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

import se.pj.tbike.util.result.Result;
import se.pj.tbike.util.result.ResultList;
import se.pj.tbike.util.result.ResultPage;

@RequiredArgsConstructor
@RestController
@RequestMapping( path = { API_PREFIX + BRAND_API } )
public class BrandController {

	private final BrandService service;
	private final BrandReqMapper<BrandCreation> creationMapper;
	private final BrandReqMapper<BrandModification> modificationMapper;
	private final BrandResMapper<BrandResponse> responseMapper;

	@GetMapping( path = { URL_LIST_1, URL_LIST_2 } )
	public Response<Arr<BrandResponse>> getList(
			@RequestParam( name = "page", defaultValue = "0" ) String page,
			@RequestParam( name = "size", defaultValue = "-1" ) String size ) {
		int p = Integer.parseInt( page ), s = Integer.parseInt( size );
		if ( p < 0 ) throw new RuntimeException();
		if ( s > 0 ) {
			ResultPage<Brand> r = service.findPage( p, s );
			return pagination( r.map( responseMapper::map ) );
		}
		ResultList<Brand> r = service.findAll();
		ResultList<BrandResponse> l = r.map( responseMapper::map );
		return ok( Arr.of( l.toList() ) );
	}

	@GetMapping( path = { URL_INFO } )
	public Response<BrandResponse> getById( @PathVariable( "id" ) String id ) {
		long key = Long.parseLong( id );
		Result<Brand> r = service.findByKey( key );
		Optional<BrandResponse> o = r.map( responseMapper::map )
				.toOptional();
		return o.map( Response::ok ).orElseGet( Response::notFound );
	}

	@PostMapping( path = { URL_LIST_1, URL_LIST_2 } )
	public Response<Val<Long>> create( @RequestBody BrandCreation req ) {
		Brand b = creationMapper.map( req );
		Brand created = service.create( b );
		return created( Val.of( created.getId() ) );
	}

	@PutMapping( path = { URL_INFO } )
	public Response<ResponseType> update( @PathVariable( "id" ) String id,
	                                      @RequestBody BrandModification req ) {
		if ( Long.parseLong( id ) != req.getId() )
			return badRequest();
		Brand b = modificationMapper.map( req );
		service.update( b );
		return noContent();
	}

	@DeleteMapping( path = { URL_INFO } )
	public Response<ResponseType> delete( @PathVariable( "id" ) String id ) {
		long key = Long.parseLong( id );
		service.remove( key );
		return noContent();
	}
}
