package se.pj.tbike.api.core.brand.controller;

import java.util.Optional;

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
import se.pj.tbike.api.io.Arr;
import se.pj.tbike.api.io.Response;
import se.pj.tbike.api.io.Pagination;
import se.pj.tbike.api.io.RequestMapper;
import se.pj.tbike.api.io.ResponseMapper;

import se.pj.tbike.api.io.Val;
import se.pj.tbike.util.result.Result;
import se.pj.tbike.util.result.ResultList;
import se.pj.tbike.util.result.ResultPage;

@RequiredArgsConstructor
@RestController
@RequestMapping( path = { API_PREFIX + BRAND_API } )
public class BrandController {

	private final BrandService brandService;
	private final ResponseMapper<Brand, BrandResponse> brandResponseMapper;
	private final RequestMapper<Brand, BrandCreation> brandCreationMapper;

	@GetMapping( path = { URL_LIST_1, URL_LIST_2 } )
	public Response<Arr<BrandResponse>> getAll(
			@RequestParam( name = "page", defaultValue = "0" )
			String page,
			@RequestParam( name = "size", defaultValue = "-1" )
			String size ) {
		int p = Integer.parseInt( page ), s = Integer.parseInt( size );
		if ( p < 0 ) throw new RuntimeException();
		if ( s > 0 ) {
			ResultPage<Brand> r = brandService.findPage( p, s );
			return new Pagination<>( r.map( brandResponseMapper::map ) );
		}
		ResultList<Brand> r = brandService.findAll();
		ResultList<BrandResponse> l = r.map( brandResponseMapper::map );
		return Response.ok( Arr.of( l.toList() ) );
	}

	@GetMapping( path = { URL_INFO } )
	public Response<BrandResponse> getById(
			@PathVariable( name = "id" )
			String id ) {
		long key = Long.parseLong( id );
		Result<Brand> r = brandService.findByKey( key );
		Optional<BrandResponse> o = r.map( brandResponseMapper::map )
				.toOptional();
		return o.map( Response::ok )
				.orElseGet( Response::notFound );
	}

	@PostMapping( path = { URL_LIST_1, URL_LIST_2 } )
	public Response<Val<Long>> create( @RequestBody BrandCreation req ) {
		Brand b = brandCreationMapper.map( req );
		Brand created = brandService.create( b );
		return Response.created( Val.of( created.getId() ) );
	}

	// TODO: update
	@PutMapping( path = { URL_INFO } )
	public void update( @RequestBody BrandModification req ) {

	}


	// TODO: delete
}
