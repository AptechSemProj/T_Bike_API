package se.pj.tbike.core.api.attribute.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import com.ank.japi.Request;
import se.pj.tbike.io.RequestType;

/*
@Request(
    responseType = Long.class,
    mapper = @ClassMapper(
        targetClass = Attribute.class,
        constructorParameters = {},
        methodName? = "map",
        reverseMethodName? = "map",
        (
            staticClass? = true,
            finalClass = true,
            finalMethod? = false,
            strategy? = GENERATE,
            className? = "AttributeRequestMapper"
        )
        <or/>
        (
            strategy? =  DECLARED,
            declaredClass? = AttributeRequestMapper.class
        )
    )
)
*/

@Getter
@AllArgsConstructor
public class AttributeRequest
        /* implements Request<Long> */
        implements RequestType,
                   Request<Long> {

    /*
        public static final class AttributeRequestMapper
            implements RequestMapper<AttributeRequest, Attribute> {
            @Override
            public Attribute map(AttributeRequest request) {
                Attribute target = new Attribute();
                if (request.getId() == null) {
                    throw new NullPointerException("id is null");
                }
                target.setId(AttributeTransform.transformId(request.getId()));
                target.setName(request.getName());
                ...
                return target;
            }
        }
     */
    /*
        @Mapping(
            ( value? = "id", )
            <or/>
            ( targetField? = "id", )
            getterMethod? = "getId",
            setterMethod? = "setId",
            transform? = @Mapping.Transform(
                declaredClass? = AttributeTransform.class,
                staticMethod? = true,
                methodName? = "transformId"
            )
        )
        @NotNull
     */
    private Long id;

    private String name;

    private String imageUrl;

    private long price;

    private int quantity;

    private Boolean represent;

    public boolean getRepresent() {
        return represent != null && represent;
    }
}
