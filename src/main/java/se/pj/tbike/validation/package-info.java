/**
 *
 */
package se.pj.tbike.validation;
/*
	define
	{
		Requirement = se.pj.tbike.validation.Requirement
		Requirement.nonNull = func(): Requirement
		Requirement.min = func(byte | short | int | long | float | double): Requirement
		Requirement.notBlank = func(?int): Requirement
		Requirement.in = func(
			byte, byte |
			short, short |
			int, int |
			long, long |
			float, float |
			double, double
		): Requirement
		Validators = se.pj.tbike.validation.Validators
		Validators.bind = func(String, Object): Validators.KeyValue
		Validators.requireNotNull = func(Validators.KeyValue...): Validators
		Validators.validate = func(Object, Requirement...): Validators
		RequestHandler.validateParam = func(Object, Requirement...): RequestHandler
		RequestHandler.validateBody = func(BiConsumer<RequestObject, Validators>): RequestHandler
		RequestHandler.handle = func(RequestObject): RequestHandler
	}

	params = { path: { id }, query: { name } }
	var request = new RequestObject() {
		id: ,
		name: ,
		age: ,
		gender: ,
	};
	var handler = new RequestHandler<>() {};
	handler.validateParam(
		params.id, Requirement.nonNull(), Requirement.min(1)
	).validateParam(
		params.name, Requirement.nonNull(), Requirement.notBlank()
	);
	handler.validateBody((body, validators) -> {
		var boundId = validators.bind("id", body.id);
		validators.requireNotNull(
				boundId, validators.bind("name", body.name)
		);
		boundId.require(Requirement.notBlank(100))
				.require(Requirement.between(1, 120));
	}).handle(request);
 */