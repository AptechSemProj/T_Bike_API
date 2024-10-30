package se.pj.tbike.core.api.category.conf;

import org.springframework.stereotype.Service;
import se.pj.tbike.core.api.category.dto.CategoryRequest;
import se.pj.tbike.core.api.category.dto.CategoryResponse;
import se.pj.tbike.core.api.category.entity.Category;
import se.pj.tbike.core.api.category.dto.mapper.CategoryRequestMapper;
import se.pj.tbike.core.api.category.dto.mapper.CategoryResponseMapper;

@Service
public class CategoryMapper {

	public Category map(CategoryRequest req) {
		CategoryRequestMapper mapper = creationMapper();
		return mapper.map( req );
	}

	public CategoryResponse map(Category category) {
		CategoryResponseMapper mapper = responseMapper();
		return mapper.map( category );
	}

	public CategoryRequestMapper creationMapper() {
		return Mappers.CREATION_MAPPER;
	}

	public CategoryResponseMapper responseMapper() {
		return Mappers.RESPONSE_MAPPER;
	}

	private static final class Mappers {

		private static final CategoryRequestMapper CREATION_MAPPER;
		private static final CategoryResponseMapper RESPONSE_MAPPER;

		static {
			CREATION_MAPPER = new CategoryRequestMapper();
			RESPONSE_MAPPER = new CategoryResponseMapper();
		}
	}
}
